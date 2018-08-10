package com.base.function;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.security.Provider;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Set;

import javax.lang.model.SourceVersion;
import javax.tools.DiagnosticCollector;
import javax.tools.DiagnosticListener;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import javax.tools.JavaCompiler.CompilationTask;

import com.base.database.ClassUtil;
import com.base.database.OracleDB;
import com.base.database.Table;
 
/**
 * 根据数据库表结构生成相应的JavaBean
 *
 * 思路
 * 1、创建数据库连接
 * 2、获取数据库表、表注释
 * 3、获取数据库表中字段名、字段类型、字段注释
 * 4、构建StringBuffer缓存
 * 5、写入文件
 * 6、关闭连接、输入流等等
 *
 * @author ella_li
 *
 */
public class GenDatabaseTableEntity {
    private String packageOutPath = "com.soft.ms.test";// 指定实体生成所在包的路径
    private String authorName = "huangyuanwei";// 作者名字
    private String tableName;
    private String[] tablePre = { "TB_" };
    private String[] colsPre = { "F_NB_", "F_VC_", "F_CR_", "F_DT_"};
    private String[] colnames; // 列名数组
    private String[] colTypes; // 列名类型数组
    private int[] colSizes; // 列名大小数组
    private boolean f_util = false; // 是否需要导入包java.util.*
    private boolean f_sql = false; // 是否需要导入包java.sql.*
 
    // 数据库连接
    private Connection con = null;
    private Statement pStemt = null;
    private ResultSet rs = null;
    private ResultSetMetaData rsmd = null;
 
    public GenDatabaseTableEntity(Connection con, String tableName, String packagePath) {
    	this.con = con;
    	this.tableName = tableName;
    	this.packageOutPath = packagePath;
    	getTableInfo(tableName);
    }
    
    public GenDatabaseTableEntity(Connection con, String tableName) {
    	StringBuffer javasrc = null;
    	this.con = con;
    	this.tableName = tableName;
    }

    /**
     * 获取单个数据库表信息
     */
    public StringBuffer getTableInfo2() {
        int size = 0;
        String sql = "SELECT * FROM " + tableName;
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            rsmd = rs.getMetaData();
            size = rsmd.getColumnCount();
            colnames = new String[size];
            colTypes = new String[size];
            colSizes = new int[size];
            for (int i = 0; i < size; i++) {
                colnames[i] = rsmd.getColumnName(i + 1);
                colTypes[i] = rsmd.getColumnTypeName(i + 1);
 
                if (colTypes[i].equalsIgnoreCase("date")
                        || colTypes[i].equalsIgnoreCase("timestamp")) {
                    f_util = true;
                }
                if (colTypes[i].equalsIgnoreCase("blob")
                        || colTypes[i].equalsIgnoreCase("char")) {
                    f_sql = true;
                }
                colSizes[i] = rsmd.getColumnDisplaySize(i + 1);
            }
            //规范化表名
            String normTableName = normTableName(tableName);
            //获取单张数据库表注释
            String tableComment = getTableComment(tableName);
            //获取单张数据库表的所有列信息
            StringBuffer tempSb = getColsInfo(tableName);
            //生成JavaBean文件
            return getSb(normTableName, tableComment, tempSb);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
			e.printStackTrace();
		}
        return null;
    }
    
    /**
     * 获取单个数据库表信息
     */
    private void getTableInfo(String tableName) {
        int size = 0;
        String sql = "SELECT * FROM " + tableName;
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            rsmd = rs.getMetaData();
            size = rsmd.getColumnCount();
            colnames = new String[size];
            colTypes = new String[size];
            colSizes = new int[size];
            for (int i = 0; i < size; i++) {
                colnames[i] = rsmd.getColumnName(i + 1);
                colTypes[i] = rsmd.getColumnTypeName(i + 1);
 
                if (colTypes[i].equalsIgnoreCase("date")
                        || colTypes[i].equalsIgnoreCase("timestamp")) {
                    f_util = true;
                }
                if (colTypes[i].equalsIgnoreCase("blob")
                        || colTypes[i].equalsIgnoreCase("char")) {
                    f_sql = true;
                }
                colSizes[i] = rsmd.getColumnDisplaySize(i + 1);
            }
            //规范化表名
            String normTableName = normTableName(tableName);
            //获取单张数据库表注释
            String tableComment = getTableComment(tableName);
            //获取单张数据库表的所有列信息
            StringBuffer tempSb = getColsInfo(tableName);
            //生成JavaBean文件
            genFile(normTableName, getSb(normTableName, tableComment, tempSb));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
			e.printStackTrace();
		}
    }
 
    /**
     * 规范类名
     *
     * @param tableName
     * @return
     */
    public String normTableName(String tableName) {
        String result = "";
        for (String temp : tablePre) {
            int index = tableName.indexOf(temp);
            if (index >= 0) {
                tableName = tableName.substring(index + temp.length());
                String[] names = tableName.split("_");
                if (null != names && names.length > 0) {
                    for (String name : names) {
                        result += captureName(name.toLowerCase());
                    }
                }
            }else{
            	result += captureName(tableName.toLowerCase());
            }
        }
        return result;
    }
 
    /**
     * 获取单张数据库表注释
     *
     * @param tableName
     * @return
     */
    private String getTableComment(String tableName) {
    	
		try {
			DatabaseMetaData md = con.getMetaData();
			if(md.getDriverName().equals("SQLite JDBC")){
				return "";
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
        String str = "";
        String sql = "select * from user_tab_comments where table_name = '"
                + tableName.toUpperCase() + "'";
        try {
        	PreparedStatement preparedStatement = con.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                str = rs.getString("comments");
                if (null != str && str.indexOf("\r\n") != -1) {
                    str = str.replace("\r\n", "");
                }
                if (null != str && str.indexOf("\n") != -1) {
                    str = str.replace("\n", "");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
			e.printStackTrace();
		}
        return str;
    }
 
    /**
     * 获取单张数据库表的所有列信息
     *
     * @param tableName
     */
    private StringBuffer getColsInfo(String tableName) {
        StringBuffer temp = new StringBuffer();
        for (int i = 0; i < colnames.length; i++) {
        	temp.append("\t//" + getColComment(tableName, colnames[i]) + "\r\n");
            temp.append("\tprivate " + getColsType(colTypes[i]) + " "
                    + getColsName(colnames[i]) + ";\r\n");
            String colname = getColsName(colnames[i]);
            String colnameUp = captureName(colname);
            temp.append("\tpublic void set" + colnameUp + "("
                    + getColsType(colTypes[i]) + " " + colname + "){\r\n");
            temp.append("\t\tthis." + colname + " = " + colname + ";\r\n");
            temp.append("\t}\r\n");
            temp.append("\tpublic " + getColsType(colTypes[i]) + " get"
                    + colnameUp + "(){\r\n");
            temp.append("\t\treturn " + colname + ";\r\n");
            temp.append("\t}\r\n\r\n");
        }
        return temp;
    }
 
    /**
     * 获取列类型
     *
     * @param sqlType
     * @return
     */
    private String getColsType(String sqlType) {
        if (sqlType.equalsIgnoreCase("binary_double")) {
            return "double";
        } else if (sqlType.equalsIgnoreCase("binary_float")) {
            return "float";
        } else if (sqlType.equalsIgnoreCase("blob")) {
            return "byte[]";
        } else if (sqlType.equalsIgnoreCase("blob")) {
            return "byte[]";
        } else if (sqlType.equalsIgnoreCase("char")
                || sqlType.equalsIgnoreCase("nvarchar2")
                || sqlType.equalsIgnoreCase("varchar2")) {
            return "String";
        } else if (sqlType.equalsIgnoreCase("date")
                || sqlType.equalsIgnoreCase("timestamp")
                || sqlType.equalsIgnoreCase("timestamp with local time zone")
                || sqlType.equalsIgnoreCase("timestamp with time zone")) {
            return "Date";
        } else if (sqlType.equalsIgnoreCase("number")) {
            return "Long";
        }
        return "String";
    }
 
    /**
     * 获取列名
     *
     * @param str
     * @return
     */
    private String getColsName(String str) {
        for (String temp : colsPre) {
            int preIndex = str.indexOf(temp);
            if (preIndex >= 0) {
                str = str.substring(preIndex + temp.length());
                str = str.replace("_", "").toLowerCase();
            }
        }
        return str;
    }
    
    /**
     * 获取列注释
     *
     * @param tableName
     * @param columnName
     * @return
     */
    private String getColComment(String tableName, String columnName) {
    	
		try {
			DatabaseMetaData md = con.getMetaData();
			if(md.getDriverName().equals("SQLite JDBC")){
				return columnName;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
    	
        String str = "";
        String sql = "select comments from USER_COL_COMMENTS where table_name= '"
                + tableName.toUpperCase() + "' and column_name= '" + columnName.toUpperCase() + "'";
        try {
        	PreparedStatement preparedStatement = con.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                str = rs.getString("comments");
                if (null != str && str.indexOf("\r\n") != -1) {
                    str = str.replace("\r\n", "");
                }
                if (null != str && str.indexOf("\n") != -1) {
                    str = str.replace("\n", "");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
			e.printStackTrace();
		}
        return (str==""||str==null)?columnName:str;
    }
 
    /**
     * 构建StringBuffer缓存
     *
     * @param tableName
     * @param tableComment
     * @param colSb
     * @return
     */
    private StringBuffer getSb(String tableName, String tableComment,
            StringBuffer colSb) {
        StringBuffer sb = new StringBuffer();
        sb.append("package " + this.packageOutPath + ";\r\n");
        // 判断是否导入工具包
        if (f_util) {
            sb.append("import java.util.Date;\r\n");
        }
        if (f_sql) {
            sb.append("import java.sql.*;\r\n");
        }
        sb.append("\r\n");
        // 注释部分
        sb.append("   /**\r\n");
        sb.append("    * " + tableName + (tableComment==null?"":("(" + tableComment + ")")) + "实体类" + "\r\n");
        sb.append("    * " + new Date() + "\r\n");
        sb.append("    * @author " + this.authorName + "\r\n");
        sb.append("    */ \r\n");
        // 实体部分
        sb.append("\r\npublic class " + tableName + "{\r\n");
        sb.append("\tprivate static final long serialVersionUID = 1L;\r\n\r\n");
        
        // 固定增加一个保存数据表名称的字段 tableName
        sb.append("\t//" + "数据表名称" + "\r\n");
        sb.append("\tpublic " + "static final String" + " "
                + "DATABASE_TABLE_NAME = \"" + tableName.toLowerCase() +"\";\r\n");
        sb.append("\r\n\r\n");
        
        sb.append(colSb);
        sb.append("}\r\n");
        return sb;
    }
 
    /**
     * 生成JavaBean文件
     *
     * @param content
     */
    private void genFile(String tableName, StringBuffer content) {
        try {
            File directory = new File("");
            String outputPath = directory.getAbsolutePath() + "/src/"
                    + this.packageOutPath.replace(".", "/") + "/" + tableName
                    + ".java";
            FileWriter fw = new FileWriter(outputPath);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(content);
            pw.flush();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 首字母大写
     * @param name
     * @return
     */
    public static String captureName(String name) {
        String string = name.substring(0,1).toUpperCase();
        String string2 = name.substring(1).toLowerCase();
        return string+string2;
    }
    
    /**
     * Determine whether the Class identified by the supplied name is present. 
     * 判断由提供的类名(类的全限定名)标识的类是否存在并可以加载
     * 如果类或其中一个依赖关系不存在或无法加载，则返回false
     * @param className 要检查的类的名称
     * @return 指定的类是否存在
     */
    public static boolean isPresent(String className) {
        try {
            Class.forName(className);
            return true;
        }
        catch (Throwable ex) {
            // Class or one of its dependencies is not present...
            return false;
        }
    }
    
    public static void main(String[] args){
    	
    	//数据库连接
    	OracleDB oracleDB = new OracleDB(OracleDB.databaseInfo);
    	Connection connection = oracleDB.getConnection();
    	
    	try {
	    	/*if(null == Class.forName("com.base.bean.Trans_entry")){
	    		
	    	}*/
	    	//生成代码
	    	new GenDatabaseTableEntity(connection, "trans_entry", "com.base.bean");
	    	new GenDatabaseTableEntity(connection, "trans_action", "com.base.bean");
	    	new GenDatabaseTableEntity(connection, "hywtest01", "com.base.bean");
	    	new GenDatabaseTableEntity(connection, "loanback_status", "com.base.bean");
	    	
	    	//编译代码
	    	JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
	    	StandardJavaFileManager manager = compiler.getStandardFileManager(null, null, null);
	        Iterable<? extends JavaFileObject> it = manager.getJavaFileObjects("D://Java/HywSource/BasePackage/src/com/base/bean/Trans_entry.java");
	        ArrayList<String> ops = new ArrayList<String>();
			ops.add("-Xlint:unchecked");
	        JavaCompiler.CompilationTask task = compiler.getTask(null, manager, null, ops, null, it);
	        task.call();
	
	        String filePath = "D://Java/HywSource/BasePackage/src/com/base/bean/Trans_entry.java";  //编译单元,目录则编译目录下所有.java
			String sourceDir = "D://Java/HywSource/BasePackage/src/com/base/bean/";  //.java源文件的目录
			String targetDir = "D://Java/HywSource/BasePackage/bin/";   //.class文件的保存位置
			DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
			
			boolean compilerResult = DynamicCompilerUtil.compiler(filePath, sourceDir, targetDir, diagnostics);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
        
		//加载类
		try {
			ClassLoader classLoader = ClassUtil.class.getClassLoader();
			Class<?> cls = classLoader.loadClass("com.base.bean.Trans_action");
			System.out.println(cls.getName());
			
			HashMap<String,Object> keyAndValues = new HashMap<String,Object>();
			keyAndValues.put("transid", "200311");
			Object Tcls = Table.getOneTableObject("Trans_action", "com.base.bean.Trans_action", keyAndValues, connection);
			System.out.println(Tcls);
			
			/*Trans_action ta = (Trans_action) Tcls;
			System.out.println("transid="+ta.getTransid());
			
			Method[] methods = cls.getMethods();
			for(Method method:methods){
				if(method.getName().toUpperCase().indexOf("GET") != -1 ){
					System.out.println(method.invoke(ta));
				}
			}	*/	
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(connection != null) OracleDB.closeCon();
    	
    }
}
