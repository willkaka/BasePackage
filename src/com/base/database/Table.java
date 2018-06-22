package com.base.database;

import java.io.File;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import java.util.Vector;

import com.base.function.DynamicCompilerUtil;
import com.base.function.GenDatabaseTableEntity;

public class Table {
	private static int seq_max=0;
	private int seq = 0;
	private String tableName = null;
	private String tableShortName = null;
	
	public Table(){
		setSeq(seq_max++);
	}
	
	/**
	 * 在数据库中删除数据表
	 * @param tableName表名
	 * @param connection数据库连接
	 */
	public static void deleteTable(String tableName, Connection connection){
		String sqlStm = "DROP TABLE " + tableName ;
		
		System.out.println("[sql] " + sqlStm);
		
		try{
			PreparedStatement psql = connection.prepareStatement(sqlStm);
			psql.execute();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 在数据库中自动Create数据表
	 * @param tableName表名
	 * @param Vector<TableField> fields数据表字段
	 * @param connection数据库连接
	 */
	public static void createTable(String tableName, Vector<TableField> fields, Connection connection){
		String sqlStm = "CREATE TABLE " + tableName + "(";
		
		int fieldNum = 1;
		for(TableField field:fields){
			if(fieldNum != 1) sqlStm += ","; //字段定义间分隔符
			
			sqlStm += field.getFieldName() + " " + field.getFieldType() ;  //字段名称 + 字段类型
			
			if(field.getFieldType().toUpperCase().equals("DATE") || 
			   field.getFieldType().toUpperCase().equals("TIME") ||
			   field.getFieldType().toUpperCase().equals("TIMESTAMP")) {
			   //DATE,TIME,TIMESTAMP三种类型不需要指定长度
			}else {
				sqlStm += "(" + field.getFieldLen();                      //左括号 + 字段长度 ( 999
				if(field.getFieldType().toUpperCase().equals("NUMBER") || 
				   field.getFieldType().toUpperCase().equals("FLOAT")  ||
				   field.getFieldType().toUpperCase().equals("DOUBLE") ){
					sqlStm += "," + field.getFieldDec();  //字段小数位
				}
				sqlStm += ")";  //右括号
			}
			addTableFieldComment(tableName, field.getFieldName(), field.getFieldDsc(), connection);
			fieldNum ++;
		}
		sqlStm += " )";
		System.out.println("[sql] " + sqlStm);
		
		try{
			PreparedStatement psql = connection.prepareStatement(sqlStm);
			psql.execute();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 增加/修改字段描述
	 * @param tableName
	 * @param fieldName
	 * @param fieldComment
	 * @param connection
	 * @throws Exception
	 */
	public static void addTableFieldComment(String tableName, String fieldName, String fieldComment, Connection connection) {
		String sqlStm = "";
		String sqlStm0 = "";
		try{
			DatabaseMetaData md = connection.getMetaData();
			//if(md.getDriverName().equals("SQLite JDBC")){
				sqlStm0 = "select 1 from user_col_comments where table_name = '"+tableName+"' and column_name ='"+fieldName+"'";
				PreparedStatement psql0 = connection.prepareStatement(sqlStm0);
				ResultSet set = psql0.executeQuery();
				if(set.next()){
					sqlStm = "update user_col_comments set comments = '"+fieldComment+"where table_name = '"+tableName+"' and column_name ='"+fieldName+"'";
				}else{
					sqlStm = "insert into user_col_comments (table_name,column_name,comments) values('"+tableName+"','"+fieldName+"','"+fieldComment+"')";
				}
			//}else{
			//	sqlStm = "";
			//}
			PreparedStatement psql = connection.prepareStatement(sqlStm);
			psql.execute();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 自动创建数据表对应的类
	 * @param tableName表名
	 * @param connection数据库连接
	 */
	public static void createTableClass(String tableName, Connection connection){
		//创建.java代码
		try {
			String packagePath = "com.base.bean";
			String sourceDir = System.getProperty("user.dir") + "\\src\\" + packagePath.replace('.', '\\');
	    	sourceDir = sourceDir.replace('\\', '/') + "/";
	    	String sroucePath = sourceDir + tableName.substring(0,1).toUpperCase() + tableName.substring(1).toLowerCase() + ".java";
	    	System.out.println("path:"+sroucePath);
	    	
	    	File javaSrcFile = new File(sroucePath);
	    	if(javaSrcFile.exists()) return;
	    	
			new GenDatabaseTableEntity(connection, tableName, "com.base.bean");
			//编译代码
	    	JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
	    	StandardJavaFileManager manager = compiler.getStandardFileManager(null, null, null);
	    	
	    	/*//静态方法通过匿名内部类的方式获取路径
	    	String path = new Object() {
	            public String getPath() {
	                return this.getClass().getResource("/").getPath();
	            }
	    	}.getPath().substring(1);
	    	System.out.println("path:"+path);*/
	    	
	    	
	        Iterable<? extends JavaFileObject> it = manager.getJavaFileObjects(sroucePath);
	        ArrayList<String> ops = new ArrayList<String>();
			ops.add("-Xlint:unchecked");
	        JavaCompiler.CompilationTask task = compiler.getTask(null, manager, null, ops, null, it);
	        task.call();
		        
			String targetDir = System.getProperty("user.dir") + "\\bin\\";   //.class文件的保存位置
			targetDir = targetDir.replace('\\', '/');
			DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
			
			boolean compilerResult = DynamicCompilerUtil.compiler(sroucePath, sourceDir, targetDir, diagnostics);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * 创建Table对象并赋值
	 * @param tableName 数据表名称
	 * @param className 数据表对应的类 eg: tableObject
	 * @param keyAndValues 查找记录条件的键值对
	 * @param Conn 数据库连接
	 * @return 返回Table对象
	 * @throws Exception
	 */
	public static Object getOneTableObject(String tableName, String className, HashMap<String,Object> keyAndValues,Connection Conn) throws Exception{
		Object tableObject = null;
		Class<?> tableClass = null;
		String sqlStm = " select * from " + tableName +" WHERE ";
		
		int countNum = 0;
		for (Entry<String, Object> entry : keyAndValues.entrySet()) { 
			  	if(countNum != 0) sqlStm += " AND ";
				sqlStm += entry.getKey() + " = " + entry.getValue();
				countNum++;
			}
		
		PreparedStatement preparedStatement = Conn.prepareStatement(sqlStm);
		ResultSet rs = preparedStatement.executeQuery();
		if(rs.next()){
			
			tableClass = Class.forName(className);
			tableObject = tableClass.newInstance();
			
			ResultSetMetaData metaData = rs.getMetaData();
			for (int fieldNum = 1; fieldNum <= metaData.getColumnCount(); fieldNum++){
				if(metaData.getColumnName(fieldNum) != null && !"".equals(metaData.getColumnName(fieldNum))){
					String fieldName = metaData.getColumnName(fieldNum);
					int fieldType = metaData.getColumnType(fieldNum);
					Object fieldValue = null;
					if(fieldType == java.sql.Types.ARRAY){
						fieldValue = rs.getArray(fieldName);
					}else if(fieldType == java.sql.Types.BIGINT){
						fieldValue = rs.getInt(fieldName);
					}else if(fieldType == java.sql.Types.BINARY){
						fieldValue = rs.getBinaryStream(fieldName);								
					}else if(fieldType == java.sql.Types.BIT){
						fieldValue = rs.getByte(fieldName);
					}else if(fieldType == java.sql.Types.BLOB){
						fieldValue = rs.getBlob(fieldName);
					}else if(fieldType == java.sql.Types.BOOLEAN){
						fieldValue = rs.getBoolean(fieldName);
					}else if(fieldType == java.sql.Types.CHAR){
						fieldValue = rs.getCharacterStream(fieldName);
					}else if(fieldType == java.sql.Types.CLOB){
						fieldValue = rs.getString(fieldName);
					}else if(fieldType == java.sql.Types.DATALINK){
						fieldValue = rs.getDate(fieldName);
					}else if(fieldType == java.sql.Types.DATE){
						fieldValue = rs.getDate(fieldName);
					}else if(fieldType == java.sql.Types.DECIMAL){
						fieldValue = rs.getFloat(fieldName);
					}else if(fieldType == java.sql.Types.DISTINCT){
						fieldValue = rs.getString(fieldName);
					}else if(fieldType == java.sql.Types.DOUBLE){
						fieldValue = rs.getDouble(fieldName);
					}else if(fieldType == java.sql.Types.FLOAT){
						fieldValue = rs.getFloat(fieldName);
					}else if(fieldType == java.sql.Types.INTEGER){
						fieldValue = rs.getInt(fieldName);
					}else if(fieldType == java.sql.Types.JAVA_OBJECT){
						fieldValue = rs.getString(fieldName);
					}else if(fieldType == java.sql.Types.LONGNVARCHAR){
						fieldValue = rs.getString(fieldName);
					}else if(fieldType == java.sql.Types.LONGVARBINARY){
						fieldValue = rs.getString(fieldName);
					}else if(fieldType == java.sql.Types.LONGVARCHAR){
						fieldValue = rs.getString(fieldName);
					}else if(fieldType == java.sql.Types.NCHAR){
						fieldValue = rs.getNCharacterStream(fieldName);
					}else if(fieldType == java.sql.Types.NCLOB){
						fieldValue = rs.getString(fieldName);
					}else if(fieldType == java.sql.Types.NULL){
						fieldValue = rs.getString(fieldName);
					}else if(fieldType == java.sql.Types.NUMERIC){
						fieldValue = rs.getDouble(fieldName);
					}else if(fieldType == java.sql.Types.NVARCHAR){
						fieldValue = rs.getString(fieldName);
					}else if(fieldType == java.sql.Types.OTHER){
						fieldValue = rs.getString(fieldName);
					}else if(fieldType == java.sql.Types.REAL){
						fieldValue = rs.getString(fieldName);
					}else if(fieldType == java.sql.Types.REF){
						fieldValue = rs.getString(fieldName);
					}else if(fieldType == java.sql.Types.ROWID){
						fieldValue = rs.getString(fieldName);
					}else if(fieldType == java.sql.Types.SMALLINT){
						fieldValue = rs.getString(fieldName);
					}else if(fieldType == java.sql.Types.SQLXML){
						fieldValue = rs.getString(fieldName);
					}else if(fieldType == java.sql.Types.STRUCT){
						fieldValue = rs.getString(fieldName);
					}else if(fieldType == java.sql.Types.TIME){
						fieldValue = rs.getTime(fieldName);
					}else if(fieldType == java.sql.Types.TIMESTAMP){
						fieldValue = rs.getTimestamp(fieldName);
					}else if(fieldType == java.sql.Types.TINYINT){
						fieldValue = rs.getString(fieldName);
					}else if(fieldType == java.sql.Types.VARBINARY){
						fieldValue = rs.getBinaryStream(fieldName);
					}else if(fieldType == java.sql.Types.VARCHAR){
						fieldValue = rs.getString(fieldName);
					}else {
						//数据类型未知
					}
					
					//找到字段set方法，并调用赋值。
					Method[] methods = tableClass.getMethods();
					for(Method method:methods){
						if(method.getName().toUpperCase().equals("SET" + fieldName.toUpperCase()) ){
							method.invoke(tableObject,fieldValue);
						}
					}				
				}
			}
		}
		rs.close();
		return tableObject;
	}
	
	/**
	 * 创建Table对象并赋值
	 * @param tableName 数据表名称
	 * @param className 数据表对应的类 eg: tableObject
	 * @param keyAndValues 查找记录条件的键值对
	 * @param Conn 数据库连接
	 * @return 返回Table对象
	 * @throws Exception
	 */
	public static Vector getTableRecords(String tableName, HashMap<String,Object> keyAndValues, Connection Conn) throws Exception{
		Vector tableRecords = new Vector();
		
		String sqlStm = " select * from " + tableName +" WHERE ";
		
		int countNum = 0;
		if(keyAndValues != null){
			for (Entry<String, Object> entry : keyAndValues.entrySet()) { 
				  	if(countNum != 0) sqlStm += " AND ";
					sqlStm += entry.getKey() + " = " + entry.getValue();
					countNum++;
			}
		}
		if (countNum == 0) sqlStm = " select * from " + tableName;
		
		PreparedStatement preparedStatement = Conn.prepareStatement(sqlStm);
		ResultSet rs = preparedStatement.executeQuery();
		while(rs.next()){
			Vector<String> tableRecord = new Vector<String>();
			ResultSetMetaData metaData = rs.getMetaData();
			for (int fieldNum = 1; fieldNum <= metaData.getColumnCount(); fieldNum++){
				if(metaData.getColumnName(fieldNum) != null && !"".equals(metaData.getColumnName(fieldNum))){
					String fieldName = metaData.getColumnName(fieldNum);
					int fieldType = metaData.getColumnType(fieldNum);
					Object fieldValue = null;
					if(fieldType == java.sql.Types.VARCHAR){
					fieldValue = rs.getString(fieldName);
					}else if(fieldType == java.sql.Types.ARRAY){
						fieldValue = rs.getArray(fieldName);
					}else if(fieldType == java.sql.Types.BIGINT){
						fieldValue = rs.getInt(fieldName);
					}else if(fieldType == java.sql.Types.BINARY){
						fieldValue = rs.getBinaryStream(fieldName);								
					}else if(fieldType == java.sql.Types.BIT){
						fieldValue = rs.getByte(fieldName);
					}else if(fieldType == java.sql.Types.BLOB){
						fieldValue = rs.getBlob(fieldName);
					}else if(fieldType == java.sql.Types.BOOLEAN){
						fieldValue = rs.getBoolean(fieldName);
					}else if(fieldType == java.sql.Types.CHAR){
						fieldValue = rs.getCharacterStream(fieldName);
					}else if(fieldType == java.sql.Types.CLOB){
						fieldValue = rs.getString(fieldName);
					}else if(fieldType == java.sql.Types.DATALINK){
						fieldValue = rs.getDate(fieldName);
					}else if(fieldType == java.sql.Types.DATE){
						fieldValue = rs.getDate(fieldName);
					}else if(fieldType == java.sql.Types.DECIMAL){
						fieldValue = rs.getFloat(fieldName);
					}else if(fieldType == java.sql.Types.DISTINCT){
						fieldValue = rs.getString(fieldName);
					}else if(fieldType == java.sql.Types.DOUBLE){
						fieldValue = rs.getDouble(fieldName);
					}else if(fieldType == java.sql.Types.FLOAT){
						fieldValue = rs.getFloat(fieldName);
					}else if(fieldType == java.sql.Types.INTEGER){
						fieldValue = rs.getInt(fieldName);
					}else if(fieldType == java.sql.Types.JAVA_OBJECT){
						fieldValue = rs.getString(fieldName);
					}else if(fieldType == java.sql.Types.LONGNVARCHAR){
						fieldValue = rs.getString(fieldName);
					}else if(fieldType == java.sql.Types.LONGVARBINARY){
						fieldValue = rs.getString(fieldName);
					}else if(fieldType == java.sql.Types.LONGVARCHAR){
						fieldValue = rs.getString(fieldName);
					}else if(fieldType == java.sql.Types.NCHAR){
						fieldValue = rs.getNCharacterStream(fieldName);
					}else if(fieldType == java.sql.Types.NCLOB){
						fieldValue = rs.getString(fieldName);
					}else if(fieldType == java.sql.Types.NULL){
						fieldValue = rs.getString(fieldName);
					}else if(fieldType == java.sql.Types.NUMERIC){
						fieldValue = rs.getDouble(fieldName);
					}else if(fieldType == java.sql.Types.NVARCHAR){
						fieldValue = rs.getString(fieldName);
					}else if(fieldType == java.sql.Types.OTHER){
						fieldValue = rs.getString(fieldName);
					}else if(fieldType == java.sql.Types.REAL){
						fieldValue = rs.getString(fieldName);
					}else if(fieldType == java.sql.Types.REF){
						fieldValue = rs.getString(fieldName);
					}else if(fieldType == java.sql.Types.ROWID){
						fieldValue = rs.getString(fieldName);
					}else if(fieldType == java.sql.Types.SMALLINT){
						fieldValue = rs.getString(fieldName);
					}else if(fieldType == java.sql.Types.SQLXML){
						fieldValue = rs.getString(fieldName);
					}else if(fieldType == java.sql.Types.STRUCT){
						fieldValue = rs.getString(fieldName);
					}else if(fieldType == java.sql.Types.TIME){
						fieldValue = rs.getTime(fieldName);
					}else if(fieldType == java.sql.Types.TIMESTAMP){
						fieldValue = rs.getTimestamp(fieldName);
					}else if(fieldType == java.sql.Types.TINYINT){
						fieldValue = rs.getString(fieldName);
					}else if(fieldType == java.sql.Types.VARBINARY){
						fieldValue = rs.getBinaryStream(fieldName);
					}else {
						//数据类型未知
						System.out.println("数据字段类型未知，类型："+fieldType);
					}
					
					//找到字段set方法，并调用赋值。
					if(fieldValue == null){
						tableRecord.add("");
					}else{
						tableRecord.add(fieldValue.toString());
					}
				}
			}
			tableRecords.add(tableRecord);
		}
		rs.close();
		return tableRecords;
	}

	
	/**
	 * 创建Table对象并赋值
	 * @param tableName 数据表名称
	 * @param className 数据表对应的类 eg: tableObject
	 * @param keyAndValues 查找记录条件的键值对
	 * @param Conn 数据库连接
	 * @return 返回Table对象
	 * @throws Exception
	 */
	public static Vector<Object> getAllTableRecordsObject(String tableName, String className, HashMap<String,Object> keyAndValues,Connection Conn) throws Exception{
		Vector<Object> tableObjects = new Vector<Object>();
		Class<?> tableClass = null;
		String sqlStm = " select * from " + tableName +" WHERE ";
		
		int countNum = 0;
		if(keyAndValues != null){
			for (Entry<String, Object> entry : keyAndValues.entrySet()) { 
				  	if(countNum != 0) sqlStm += " AND ";
					sqlStm += entry.getKey() + " = " + entry.getValue();
					countNum++;
			}
		}
		if (countNum == 0) sqlStm = " select * from " + tableName;
		
		PreparedStatement preparedStatement = Conn.prepareStatement(sqlStm);
		ResultSet rs = preparedStatement.executeQuery();
		while(rs.next()){
			
			tableClass = Class.forName(className);
			Object tableObject = tableClass.newInstance();
			
			ResultSetMetaData metaData = rs.getMetaData();
			for (int fieldNum = 1; fieldNum <= metaData.getColumnCount(); fieldNum++){
				if(metaData.getColumnName(fieldNum) != null && !"".equals(metaData.getColumnName(fieldNum))){
					String fieldName = metaData.getColumnName(fieldNum);
					int fieldType = metaData.getColumnType(fieldNum);
					Object fieldValue = null;
					if(fieldType == java.sql.Types.VARCHAR){
					fieldValue = rs.getString(fieldName);
					}else if(fieldType == java.sql.Types.ARRAY){
						fieldValue = rs.getArray(fieldName);
					}else if(fieldType == java.sql.Types.BIGINT){
						fieldValue = rs.getInt(fieldName);
					}else if(fieldType == java.sql.Types.BINARY){
						fieldValue = rs.getBinaryStream(fieldName);								
					}else if(fieldType == java.sql.Types.BIT){
						fieldValue = rs.getByte(fieldName);
					}else if(fieldType == java.sql.Types.BLOB){
						fieldValue = rs.getBlob(fieldName);
					}else if(fieldType == java.sql.Types.BOOLEAN){
						fieldValue = rs.getBoolean(fieldName);
					}else if(fieldType == java.sql.Types.CHAR){
						fieldValue = rs.getCharacterStream(fieldName);
					}else if(fieldType == java.sql.Types.CLOB){
						fieldValue = rs.getString(fieldName);
					}else if(fieldType == java.sql.Types.DATALINK){
						fieldValue = rs.getDate(fieldName);
					}else if(fieldType == java.sql.Types.DATE){
						fieldValue = rs.getDate(fieldName);
					}else if(fieldType == java.sql.Types.DECIMAL){
						fieldValue = rs.getFloat(fieldName);
					}else if(fieldType == java.sql.Types.DISTINCT){
						fieldValue = rs.getString(fieldName);
					}else if(fieldType == java.sql.Types.DOUBLE){
						fieldValue = rs.getDouble(fieldName);
					}else if(fieldType == java.sql.Types.FLOAT){
						fieldValue = rs.getFloat(fieldName);
					}else if(fieldType == java.sql.Types.INTEGER){
						fieldValue = rs.getInt(fieldName);
					}else if(fieldType == java.sql.Types.JAVA_OBJECT){
						fieldValue = rs.getString(fieldName);
					}else if(fieldType == java.sql.Types.LONGNVARCHAR){
						fieldValue = rs.getString(fieldName);
					}else if(fieldType == java.sql.Types.LONGVARBINARY){
						fieldValue = rs.getString(fieldName);
					}else if(fieldType == java.sql.Types.LONGVARCHAR){
						fieldValue = rs.getString(fieldName);
					}else if(fieldType == java.sql.Types.NCHAR){
						fieldValue = rs.getNCharacterStream(fieldName);
					}else if(fieldType == java.sql.Types.NCLOB){
						fieldValue = rs.getString(fieldName);
					}else if(fieldType == java.sql.Types.NULL){
						fieldValue = rs.getString(fieldName);
					}else if(fieldType == java.sql.Types.NUMERIC){
						fieldValue = rs.getDouble(fieldName);
					}else if(fieldType == java.sql.Types.NVARCHAR){
						fieldValue = rs.getString(fieldName);
					}else if(fieldType == java.sql.Types.OTHER){
						fieldValue = rs.getString(fieldName);
					}else if(fieldType == java.sql.Types.REAL){
						fieldValue = rs.getString(fieldName);
					}else if(fieldType == java.sql.Types.REF){
						fieldValue = rs.getString(fieldName);
					}else if(fieldType == java.sql.Types.ROWID){
						fieldValue = rs.getString(fieldName);
					}else if(fieldType == java.sql.Types.SMALLINT){
						fieldValue = rs.getString(fieldName);
					}else if(fieldType == java.sql.Types.SQLXML){
						fieldValue = rs.getString(fieldName);
					}else if(fieldType == java.sql.Types.STRUCT){
						fieldValue = rs.getString(fieldName);
					}else if(fieldType == java.sql.Types.TIME){
						fieldValue = rs.getTime(fieldName);
					}else if(fieldType == java.sql.Types.TIMESTAMP){
						fieldValue = rs.getTimestamp(fieldName);
					}else if(fieldType == java.sql.Types.TINYINT){
						fieldValue = rs.getString(fieldName);
					}else if(fieldType == java.sql.Types.VARBINARY){
						fieldValue = rs.getBinaryStream(fieldName);
					}else {
						//数据类型未知
						System.out.println("数据字段类型未知，类型："+fieldType);
					}
					
					//找到字段set方法，并调用赋值。
					Method[] methods = tableClass.getMethods();
					for(Method method:methods){
						if(method.getName().toUpperCase().equals("SET" + fieldName.toUpperCase()) ){
							method.invoke(tableObject,fieldValue);
						}
					}				
				}
			}
			tableObjects.add(tableObject);
		}
		rs.close();
		return tableObjects;
	}
	
	public static void insertOneRecord(String tableName, Object tableObject, Connection Conn) throws Exception{
	}
	
	/**
	 * 返回数据表字段的名称
	 * @param tableName数据表名称
	 * @param Conn数据库连接
	 * @return 返回TableField的Vector数组
	 * @throws Exception
	 */
	public static Vector<String> geTableFieldsComment(String tableName, Connection Conn) throws Exception{
		Vector<String> fieldscomment = new Vector<String>();
		Vector<TableField> fields = new Vector<TableField>();
		DatabaseMetaData md = Conn.getMetaData();
		if(md.getDriverName().equals("SQLite JDBC")){
			fields = geTableFieldsSqlite(tableName, Conn);
		}else{
			fields = geTableFieldsOracle(tableName, Conn);
		}
		
		for(TableField field:fields){
			String comment = field.getFieldDsc();
			fieldscomment.add(comment);
		}
		return fieldscomment;
	}
	
	/**
	 * 返回数据表字段信息
	 * @param tableName数据表名称
	 * @param Conn数据库连接
	 * @return 返回TableField的Vector数组
	 * @throws Exception
	 */
	public static Vector<TableField> geTableFields(String tableName, Connection Conn) throws Exception{
		DatabaseMetaData md = Conn.getMetaData();
		if(md.getDriverName().equals("SQLite JDBC")){
			return geTableFieldsSqlite(tableName, Conn);
		}else{
			return geTableFieldsOracle(tableName, Conn);
		}
	}
	
	/**
	 * 返回数据表字段信息
	 * @param tableName数据表名称
	 * @param Conn数据库连接
	 * @return 返回TableField的Vector数组
	 * @throws Exception
	 */
	public static Vector<TableField> geTableFieldsOracle(String tableName, Connection Conn) throws Exception{
		Vector<TableField> fields = new Vector<TableField>();
		String sqlStm = "select a.Column_Name,a.Data_Type,a.Data_Length,a.Data_Precision,a.Data_Scale,a.Nullable,a.Column_id,b.Comments "
			+ "from user_tab_columns a,user_col_comments b where a.table_name =UPPER('" + tableName 
			+ "') and a.Table_Name = b.Table_Name and a.Column_Name = b.Column_Name ORDER by a.Column_ID";
		
		PreparedStatement preparedStatement = Conn.prepareStatement(sqlStm);
		ResultSet rs = preparedStatement.executeQuery();
		while (rs.next()) {
			TableField field = new TableField();
			field.setFieldName(rs.getString("Column_Name"));
			field.setFieldType(rs.getString("Data_Type"));
			field.setFieldLen(rs.getInt("Data_Length"));
			field.setFieldDec(rs.getInt("Data_Scale"));
			field.setFieldDsc(rs.getString("Comments"));
			fields.addElement(field);
		}
		return fields;
	}
	
	/**
	 * 返回数据表字段信息
	 * @param tableName数据表名称
	 * @param Conn数据库连接
	 * @return 返回TableField的Vector数组
	 * @throws Exception
	 */
	public static Vector<TableField> geTableFieldsSqlite(String tableName, Connection Conn) throws Exception{
		Vector<TableField> fields = new Vector<TableField>();
		String sqlStm = "pragma table_info ('" + tableName + "')";
		
		PreparedStatement preparedStatement = Conn.prepareStatement(sqlStm);
		ResultSet rs = preparedStatement.executeQuery();
		while (rs.next()) {
			TableField field = new TableField();
			field.setFieldName(rs.getString("name"));
			String typeString = rs.getString("type");
			
			field.setFieldType(typeString.substring(0, typeString.indexOf('(')));
			
			String lengString = "0";
			String decString = "0";
			if(typeString.indexOf(',') > 0){
				lengString = typeString.substring(typeString.indexOf('(')+1,typeString.indexOf(','));
				decString = typeString.substring(typeString.indexOf(',')+1,typeString.indexOf(')'));
			}else{
				lengString = typeString.substring(typeString.indexOf('(')+1,typeString.indexOf(')'));
			}
			field.setFieldLen(Integer.parseInt(lengString));
			field.setFieldDec(Integer.parseInt(decString));
			String comments = getTableFieldComments(tableName,field.getFieldName(),Conn);
			if(comments == null || "".equals(comments)){
				field.setFieldDsc(rs.getString("name"));
			}else{
				field.setFieldDsc(comments);
			}
			
			fields.addElement(field);
			
		}
		return fields;
	}
	
	public static String getTableFieldComments(String tableName, String fieldName,Connection connection){
		String comments = "";
		String sqlStm = "select comments from user_col_comments where table_name = '"+tableName+"' and column_name ='"+fieldName+"'";
		
		try{
			PreparedStatement preparedStatement = connection.prepareStatement(sqlStm);
			ResultSet rs = preparedStatement.executeQuery();
			if(rs.next()){
				comments = rs.getString("comments");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return comments;
	}
	
	
	public static boolean isExist(String tableName,Connection connection){
		String sqlStm = "select 1 from "+tableName;
		
		try{
			PreparedStatement preparedStatement = connection.prepareStatement(sqlStm);
			ResultSet rs = preparedStatement.executeQuery();
			if(rs.next()){
				return true;
			}
		}catch(Exception e){
			return false;
		}
		return true;
	}
	
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableShortName() {
		return tableShortName;
	}
	public void setTableShortName(String tableShortName) {
		this.tableShortName = tableShortName;
	}
}
