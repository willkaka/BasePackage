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
 * �������ݿ��ṹ������Ӧ��JavaBean
 *
 * ˼·
 * 1���������ݿ�����
 * 2����ȡ���ݿ����ע��
 * 3����ȡ���ݿ�����ֶ������ֶ����͡��ֶ�ע��
 * 4������StringBuffer����
 * 5��д���ļ�
 * 6���ر����ӡ��������ȵ�
 *
 * @author ella_li
 *
 */
public class GenDatabaseTableEntity {
    private String packageOutPath = "com.soft.ms.test";// ָ��ʵ���������ڰ���·��
    private String authorName = "huangyuanwei";// ��������
    private String tableName;
    private String[] tablePre = { "TB_" };
    private String[] colsPre = { "F_NB_", "F_VC_", "F_CR_", "F_DT_"};
    private String[] colnames; // ��������
    private String[] colTypes; // ������������
    private int[] colSizes; // ������С����
    private boolean f_util = false; // �Ƿ���Ҫ�����java.util.*
    private boolean f_sql = false; // �Ƿ���Ҫ�����java.sql.*
 
    // ���ݿ�����
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
     * ��ȡ�������ݿ����Ϣ
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
            //�淶������
            String normTableName = normTableName(tableName);
            //��ȡ�������ݿ��ע��
            String tableComment = getTableComment(tableName);
            //��ȡ�������ݿ�����������Ϣ
            StringBuffer tempSb = getColsInfo(tableName);
            //����JavaBean�ļ�
            return getSb(normTableName, tableComment, tempSb);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
			e.printStackTrace();
		}
        return null;
    }
    
    /**
     * ��ȡ�������ݿ����Ϣ
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
            //�淶������
            String normTableName = normTableName(tableName);
            //��ȡ�������ݿ��ע��
            String tableComment = getTableComment(tableName);
            //��ȡ�������ݿ�����������Ϣ
            StringBuffer tempSb = getColsInfo(tableName);
            //����JavaBean�ļ�
            genFile(normTableName, getSb(normTableName, tableComment, tempSb));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
			e.printStackTrace();
		}
    }
 
    /**
     * �淶����
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
     * ��ȡ�������ݿ��ע��
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
     * ��ȡ�������ݿ�����������Ϣ
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
     * ��ȡ������
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
     * ��ȡ����
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
     * ��ȡ��ע��
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
     * ����StringBuffer����
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
        // �ж��Ƿ��빤�߰�
        if (f_util) {
            sb.append("import java.util.Date;\r\n");
        }
        if (f_sql) {
            sb.append("import java.sql.*;\r\n");
        }
        sb.append("\r\n");
        // ע�Ͳ���
        sb.append("   /**\r\n");
        sb.append("    * " + tableName + (tableComment==null?"":("(" + tableComment + ")")) + "ʵ����" + "\r\n");
        sb.append("    * " + new Date() + "\r\n");
        sb.append("    * @author " + this.authorName + "\r\n");
        sb.append("    */ \r\n");
        // ʵ�岿��
        sb.append("\r\npublic class " + tableName + "{\r\n");
        sb.append("\tprivate static final long serialVersionUID = 1L;\r\n\r\n");
        
        // �̶�����һ���������ݱ����Ƶ��ֶ� tableName
        sb.append("\t//" + "���ݱ�����" + "\r\n");
        sb.append("\tpublic " + "static final String" + " "
                + "DATABASE_TABLE_NAME = \"" + tableName.toLowerCase() +"\";\r\n");
        sb.append("\r\n\r\n");
        
        sb.append(colSb);
        sb.append("}\r\n");
        return sb;
    }
 
    /**
     * ����JavaBean�ļ�
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
     * ����ĸ��д
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
     * �ж����ṩ������(���ȫ�޶���)��ʶ�����Ƿ���ڲ����Լ���
     * ����������һ��������ϵ�����ڻ��޷����أ��򷵻�false
     * @param className Ҫ�����������
     * @return ָ�������Ƿ����
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
    	
    	//���ݿ�����
    	OracleDB oracleDB = new OracleDB(OracleDB.databaseInfo);
    	Connection connection = oracleDB.getConnection();
    	
    	try {
	    	/*if(null == Class.forName("com.base.bean.Trans_entry")){
	    		
	    	}*/
	    	//���ɴ���
	    	new GenDatabaseTableEntity(connection, "trans_entry", "com.base.bean");
	    	new GenDatabaseTableEntity(connection, "trans_action", "com.base.bean");
	    	new GenDatabaseTableEntity(connection, "hywtest01", "com.base.bean");
	    	new GenDatabaseTableEntity(connection, "loanback_status", "com.base.bean");
	    	
	    	//�������
	    	JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
	    	StandardJavaFileManager manager = compiler.getStandardFileManager(null, null, null);
	        Iterable<? extends JavaFileObject> it = manager.getJavaFileObjects("D://Java/HywSource/BasePackage/src/com/base/bean/Trans_entry.java");
	        ArrayList<String> ops = new ArrayList<String>();
			ops.add("-Xlint:unchecked");
	        JavaCompiler.CompilationTask task = compiler.getTask(null, manager, null, ops, null, it);
	        task.call();
	
	        String filePath = "D://Java/HywSource/BasePackage/src/com/base/bean/Trans_entry.java";  //���뵥Ԫ,Ŀ¼�����Ŀ¼������.java
			String sourceDir = "D://Java/HywSource/BasePackage/src/com/base/bean/";  //.javaԴ�ļ���Ŀ¼
			String targetDir = "D://Java/HywSource/BasePackage/bin/";   //.class�ļ��ı���λ��
			DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
			
			boolean compilerResult = DynamicCompilerUtil.compiler(filePath, sourceDir, targetDir, diagnostics);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
        
		//������
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
