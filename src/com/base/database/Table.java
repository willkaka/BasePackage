package com.base.database;

import java.io.File;
import java.lang.reflect.Field;
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

import org.apache.commons.beanutils.ConvertUtils;

import java.util.Vector;

import com.base.function.DynamicCompilerUtil;
import com.base.function.GenDatabaseTableEntity;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

public class Table {
	private static int seq_max=0;
	private int seq = 0;
	private String tableName = null;
	private String tableShortName = "";
	private String tableDsc = "";
	private String tableDataBase = "";
	private int tableRows = 0;
	
	public int getTableRows() {
		return tableRows;
	}

	public void setTableRows(int tableRows) {
		this.tableRows = tableRows;
	}

	public String getTableDataBase() {
		return tableDataBase;
	}

	public void setTableDataBase(String tableDataBase) {
		this.tableDataBase = tableDataBase;
	}

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
	
	public String getTableDsc() {
		return tableDsc;
	}

	public void setTableDsc(String tableDsc) {
		this.tableDsc = tableDsc;
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
			tableObject = Table.parseResultSet(rs, tableObject);
			
			/*ResultSetMetaData metaData = rs.getMetaData();
			for (int fieldNum = 1; fieldNum <= metaData.getColumnCount(); fieldNum++){
				if(metaData.getColumnName(fieldNum) != null && !"".equals(metaData.getColumnName(fieldNum))){
					String fieldName = metaData.getColumnName(fieldNum);
					int fieldType = metaData.getColumnType(fieldNum);
					Object fieldValue = rs.getObject(fieldName);
										
					//找到字段set方法，并调用赋值。
					Method[] methods = tableClass.getMethods();
					for(Method method:methods){
						if(method.getName().toUpperCase().equals("SET" + fieldName.toUpperCase()) ){
							method.invoke(tableObject,fieldValue);
						}
					}				
				}
			}*/
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
		
		String sSelectFields = " * ";
		if(keyAndValues != null){
			for (Entry<String, Object> entry : keyAndValues.entrySet()) {
				if("select".equals(entry.getKey().toString()) ) {
					sSelectFields = " " + entry.getValue().toString() + " ";
					break;
				}
			}
		}
		String sqlStm = " SELECT " + sSelectFields + " FROM " + tableName ;
		
		int countNum = 0;
		if(keyAndValues != null){
			for (Entry<String, Object> entry : keyAndValues.entrySet()) {
				if("select".equals(entry.getKey().toString().toLowerCase()) ||
				   "order".equals(entry.getKey().toString().toLowerCase()) ||
				   "group".equals(entry.getKey().toString().toLowerCase())	) continue;
				
				if(countNum == 0) sqlStm += " WHERE ";
			  	if(countNum != 0) sqlStm += " AND ";
			  	
				sqlStm += entry.getKey() + " = '" + entry.getValue() +"'";
				countNum++;
			}
		}
		if (countNum == 0) sqlStm = " select * from " + tableName;
		
		if(keyAndValues != null){
			for (Entry<String, Object> entry : keyAndValues.entrySet()) { 
			  	if("order".equals(entry.getKey().toString().toLowerCase()) ){
			  		sqlStm += " " + entry.getValue() +" ";
			  	}
			}
		}
		
		if(keyAndValues != null){
			for (Entry<String, Object> entry : keyAndValues.entrySet()) { 
			  	if("group".equals(entry.getKey().toString().toLowerCase()) ){
			  		sqlStm += " " + entry.getValue() +" ";
			  		break;
			  	}
			}
		}
		System.out.println(sqlStm);
		PreparedStatement preparedStatement = Conn.prepareStatement(sqlStm);
		ResultSet rs = preparedStatement.executeQuery();
		while(rs.next()){
			Vector<String> tableRecord = new Vector<String>();
			ResultSetMetaData metaData = rs.getMetaData();
			for (int fieldNum = 1; fieldNum <= metaData.getColumnCount(); fieldNum++){
				if(metaData.getColumnName(fieldNum) != null && !"".equals(metaData.getColumnName(fieldNum))){
					String fieldName = metaData.getColumnName(fieldNum);
					//int fieldType = metaData.getColumnType(fieldNum);
					Object fieldValue = rs.getObject(fieldName);
					
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
			tableObject = Table.parseResultSet(rs, tableObject);
			
		/*	ResultSetMetaData metaData = rs.getMetaData();
			for (int fieldNum = 1; fieldNum <= metaData.getColumnCount(); fieldNum++){
				if(metaData.getColumnName(fieldNum) != null && !"".equals(metaData.getColumnName(fieldNum))){
					String fieldName = metaData.getColumnName(fieldNum);
					int fieldType = metaData.getColumnType(fieldNum);
					Object fieldValue = rs.getObject(fieldName);
					
					//找到字段set方法，并调用赋值。
					Method[] methods = tableClass.getMethods();
					for(Method method:methods){
						if(method.getName().toUpperCase().equals("SET" + fieldName.toUpperCase()) ){
							method.invoke(tableObject,fieldValue);
						}
					}				
				}
			}*/
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
	public static Vector<String> getTableFieldsComment(String tableName, HashMap<String,Object> keyAndValues, Connection Conn) throws Exception{
		Vector<String> fieldscomment = new Vector<String>();
		Vector<TableField> fields = new Vector<TableField>();
		DatabaseMetaData md = Conn.getMetaData();
		
		if(md.getDriverName().equals("SQLite JDBC")){
			fields = geTableFieldsSqlite(tableName, keyAndValues, Conn);
		}else if(md.getDriverName().equals("Oracle JDBC driver")){
			fields = geTableFieldsOracle(tableName, keyAndValues, Conn);
		}else if(md.getDriverName().equals("MySQL Connector Java") || md.getDriverName().equals("MySQL Connector/J")){
			fields = geTableFieldsMysql(tableName, keyAndValues, Conn);
		}else{
			return null;
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
	public static Vector<TableField> geTableFields(String tableName, HashMap<String,Object> keyAndValues, Connection Conn) throws Exception{
		DatabaseMetaData md = Conn.getMetaData();
		System.out.println(md.getDriverName());
		if(md.getDriverName().equals("SQLite JDBC")){
			return geTableFieldsSqlite(tableName, keyAndValues, Conn);
		}else if(md.getDriverName().equals("Oracle JDBC driver")){
			return geTableFieldsOracle(tableName, keyAndValues, Conn);
		}else if(md.getDriverName().equals("MySQL Connector Java") || md.getDriverName().equals("MySQL Connector/J")){
			return geTableFieldsMysql(tableName, keyAndValues, Conn);
		}else{
			return null;
		}
	}
	
	/**
	 * 返回数据表字段信息
	 * @param tableName数据表名称
	 * @param Conn数据库连接
	 * @return 返回TableField的Vector数组
	 * @throws Exception
	 */
	public static Vector<TableField> geTableFieldsMysql(String tableName, HashMap<String,Object> keyAndValues, Connection Conn) throws Exception{
		Vector<TableField> fields = new Vector<TableField>();
		
		String sqlStm = "select * from information_schema.COLUMNS where TABLE_SCHEMA = (select database()) and table_name = '" + tableName	+ "'";
		
		String[] aSelectFields = null; 
		if(keyAndValues != null){
			for (Entry<String, Object> entry : keyAndValues.entrySet()) {
				if("select".equals(entry.getKey().toString()) ) {
					if(!"*".equals(entry.getValue().toString().trim())){
						aSelectFields = entry.getValue().toString().split(",");
						break;
					}
				}
			}
		}
		
		PreparedStatement preparedStatement = Conn.prepareStatement(sqlStm);
		ResultSet rs = preparedStatement.executeQuery();
		while (rs.next()) {
			TableField field = new TableField();
			field.setFieldName(rs.getString("Column_Name"));
			field.setFieldType(rs.getString("Data_Type"));
			if(rs.getObject("Character_Maximum_Length") != null){
				field.setFieldLen(rs.getInt("Character_Maximum_Length"));
			}else if(rs.getObject("Numeric_Precision") != null){
				field.setFieldLen(rs.getInt("Numeric_Precision"));
			}else{
				field.setFieldLen( 0 );
			}
			field.setFieldDec(rs.getInt("Numeric_Scale"));
			field.setFieldDsc(rs.getString("Column_Comment"));
			
			if(aSelectFields != null){
				if(Arrays.asList(aSelectFields).contains(field.getFieldName().toLowerCase())){
					fields.addElement(field);
				}
			}else{
				fields.addElement(field);
			}
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
	public static Vector<TableField> geTableFieldsOracle(String tableName, HashMap<String,Object> keyAndValues, Connection Conn) throws Exception{
		Vector<TableField> fields = new Vector<TableField>();
		String sqlStm = "select a.Column_Name,a.Data_Type,a.Data_Length,a.Data_Precision,a.Data_Scale,a.Nullable,a.Column_id,b.Comments "
			+ "from user_tab_columns a,user_col_comments b where a.table_name =UPPER('" + tableName 
			+ "') and a.Table_Name = b.Table_Name and a.Column_Name = b.Column_Name ORDER by a.Column_ID";
		
		String[] aSelectFields = null; 
		if(keyAndValues != null){
			for (Entry<String, Object> entry : keyAndValues.entrySet()) {
				if("select".equals(entry.getKey().toString()) ) {
					if(!"*".equals(entry.getValue().toString().trim())){
						aSelectFields = entry.getValue().toString().split(",");
						break;
					}
				}
			}
		}
		
		PreparedStatement preparedStatement = Conn.prepareStatement(sqlStm);
		ResultSet rs = preparedStatement.executeQuery();
		while (rs.next()) {
			TableField field = new TableField();
			field.setFieldName(rs.getString("Column_Name"));
			field.setFieldType(rs.getString("Data_Type"));
			field.setFieldLen(rs.getInt("Data_Length"));
			field.setFieldDec(rs.getInt("Data_Scale"));
			field.setFieldDsc(rs.getString("Comments"));
			
			if(aSelectFields != null){
				if(Arrays.asList(aSelectFields).contains(field.getFieldName().toLowerCase())){
					fields.addElement(field);
				}
			}else{
				fields.addElement(field);
			}
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
	public static Vector<TableField> geTableFieldsSqlite(String tableName, HashMap<String,Object> keyAndValues, Connection Conn) throws Exception{
		Vector<TableField> fields = new Vector<TableField>();
		String sqlStm = "pragma table_info ('" + tableName + "')";
		
		String[] aSelectFields = null; 
		if(keyAndValues != null){
			for (Entry<String, Object> entry : keyAndValues.entrySet()) {
				if("select".equals(entry.getKey().toString()) ) {
					if(!"*".equals(entry.getValue().toString().trim())){
						aSelectFields = entry.getValue().toString().split(",");
						break;
					}
				}
			}
		}
		
		PreparedStatement preparedStatement = Conn.prepareStatement(sqlStm);
		ResultSet rs = preparedStatement.executeQuery();
		while (rs.next()) {
			TableField field = new TableField();
			field.setFieldName(rs.getString("name"));
			String typeString = rs.getString("type");
			
			if(typeString.indexOf('(') > 0){
				field.setFieldType(typeString.substring(0, typeString.indexOf('(')));
			}else{
				field.setFieldType(typeString);
			}
			
			String lengString = "0";
			String decString = "0";
			if(typeString.indexOf(',') > 0){
				lengString = typeString.substring(typeString.indexOf('(')+1,typeString.indexOf(','));
				decString = typeString.substring(typeString.indexOf(',')+1,typeString.indexOf(')'));
			}else{
				if(typeString.indexOf('(')>0){
					lengString = typeString.substring(typeString.indexOf('(')+1,typeString.indexOf(')'));
				}
			}
			field.setFieldLen(Integer.parseInt(lengString));
			field.setFieldDec(Integer.parseInt(decString));
			String comments = getTableFieldComments(tableName,field.getFieldName(),Conn);
			if(comments == null || "".equals(comments)){
				field.setFieldDsc(rs.getString("name"));
			}else{
				field.setFieldDsc(comments);
			}
			
			if(aSelectFields != null){
				if(Arrays.asList(aSelectFields).contains(field.getFieldName().toLowerCase())){
					fields.addElement(field);
				}
			}else{
				fields.addElement(field);
			}
			
		}
		return fields;
	}
	
	/**
	 * 取指定数据表字段的描述
	 * @param tableName
	 * @param fieldName
	 * @param connection
	 * @return
	 */
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
	
	/**
	 * 取指定数据表字段的描述
	 * @param tableName
	 * @param fieldName
	 * @param connection
	 * @return
	 */
	public static String getTableFieldCommentsMysql(String tableName, String fieldName,Connection connection){
		try{
			Vector<TableField> fields = geTableFieldsMysql(tableName, null, connection);
			for(TableField tableField:fields){
				if(tableField.getFieldName().toLowerCase().equals(fieldName.toLowerCase())){
					return tableField.getFieldDsc();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 判断数据表是否存在
	 * @param tableName
	 * @param connection
	 * @return true存在 false不存在
	 */
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
	
	/**
	 * 返回数据表字段信息
	 * @param tableName数据表名称
	 * @param Conn数据库连接
	 * @return 返回TableField的Vector数组
	 * @throws Exception
	 */
	public static ArrayList<Table> getMysqlTableList(Connection Conn) throws Exception{
		ArrayList<Table> aTables = new ArrayList<Table>();
		String sqlStm = "select * from information_schema.tables where table_schema=(select database()) and table_type='base table'";
		
		PreparedStatement preparedStatement = Conn.prepareStatement(sqlStm);
		ResultSet rs = preparedStatement.executeQuery();
		while (rs.next()) {
			Table table = new Table();
			table.setTableName(rs.getString("table_name"));
			table.setTableDsc(rs.getString("table_comment"));
			table.setTableDataBase(rs.getString("table_schema"));
			table.setTableRows(rs.getInt("table_rows"));
			aTables.add(table);
		}
		return aTables;
	}
	
	/**
	 * 将ResultSet数据自动填充到类对象中
	 * @param rs
	 * @param entry
	 * @return
	 * @throws Exception
	 */
	public static <T> T parseResultSet(ResultSet rs, T entry) throws Exception{
		if(rs != null){	
            //获取类中所有的属性
            Field[] arrf=entry.getClass().getDeclaredFields();
            //遍历属性
            for(Field f:arrf){
                //设置忽略访问校验
                f.setAccessible(true);
                
                //检查数据列是否存在
                try{
                	rs.findColumn(f.getName());
                }catch (SQLException e) {
					//不存在该列，则跳过
                	continue;
				}
                
            	//为属性设置内容，使用ConvertUtils强转数据类型
                f.set(entry, ConvertUtils.convert(rs.getObject(f.getName()), f.getType()));
            }
		}
		return entry;
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
