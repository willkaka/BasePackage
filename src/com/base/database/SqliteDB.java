package com.base.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.base.database.SqliteDB;

public class SqliteDB {
	//数据库地址
	//private String dataBasePath ="./data/TQDatabase.db";
	private String dataBasePath ="./data/Database.db";
	public static Connection con;
	//构造方法
	public SqliteDB(){
		try{
			Class.forName("org.sqlite.JDBC");
			//SQLite连接数据，如果不存在，则会自动创建。
			con = DriverManager.getConnection("jdbc:sqlite:" + dataBasePath);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//构造方法
	public SqliteDB(String dataBasePath){
		try{
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection("jdbc:sqlite:" + dataBasePath);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//构造方法
	public SqliteDB(DatabaseInfo databaseInfo){
		try{
			Class.forName(databaseInfo.getDriver());
			con = DriverManager.getConnection("jdbc:sqlite:" + databaseInfo.getIp());
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 连接SQLite数据库
	 */
	public static Connection getConnection(){
		if(con == null)
		    new SqliteDB();
		return con;
	}
	
	/**
	 * 连接SQLite数据库
	 */
	public static Connection getConnection(String databasePath){
		if(con == null)
		    new SqliteDB(databasePath);
		return con;
	}
	
	/**
	 * 关闭SQLite数据库连接
	 */
	public static void closeCon(){
		if(con != null){
			try{
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/*
	 * 执行无返回值的SQL语句
	 */
	public static void exeSql(String sql) throws Exception{
		if(con == null)
		    new SqliteDB();
		
		PreparedStatement pra = con.prepareStatement(sql);
		pra.executeUpdate();
		pra.close();
	}
	
	/*
	 * 执行查询类SQL语句，返回结果集
	 */
	public static ResultSet exeSqlStmGetResultset(String sql) throws Exception{
		if(con == null)
		    new SqliteDB();
		
		PreparedStatement pra = con.prepareStatement(sql);
		ResultSet set = pra.executeQuery();
		return set;
	}
	
	/*
	 * 在SQLite数据库中创建表格
	 * @author HYW
	 * @param 
	 */
	public static void createTable(String table,Vector<TableField> fields) throws Exception{
		String createSqlStm = "CREATE TABLE " + table.trim() + "(";
		
		int fieldCount = 0;
		for(TableField field:fields){
			fieldCount ++;
			createSqlStm = fieldCount==1?"":"," +
					       createSqlStm + field.getFieldName().trim() + " " + 
						   field.getFieldType() + "(" + field.getFieldLen() +
						   (field.getFieldType().equals("NUMBER")?","+field.getFieldDec():"") +
						   ")";
		}
		System.out.println("sql:"+createSqlStm);
		exeSql(createSqlStm);
	}
	
	/*
	 * 删除SQLite数据库中对应的表格
	 * @author HYW
	 * @param table
	 * @return void
	 * @throw Exception
	 */
	public static void deleteTable(String table) throws Exception{
		String deleteSqlStm = "DROP TABLE " + table.trim();
		exeSql(deleteSqlStm);
	}
	
	/**
	 * 检查表格是否存在
	 * @param table数据表名称
	 * @return 存在返回true,不存在返回false
	 * @throws Exception
	 */
	public static boolean isExistTable(String table) throws Exception{
		boolean isExist = false;
		
		String sqlstm = "select count(*) as count  from sqlite_master where type='table' and name = '"+table.trim() + "'";
		ResultSet set = exeSqlStmGetResultset(sqlstm);
		if (set.next()){
			if(Integer.parseInt(set.getString("count"))>0) isExist = true;
		}
		return isExist;
	}
	
	/*
	 * 检查SELECT语句是否存在记录
	 */
	public static boolean isExistRecord(String sqlstm) throws Exception{
		boolean isExist = false;
		
		ResultSet set = exeSqlStmGetResultset(sqlstm);
		if (set.next()){
			if(Integer.parseInt(set.getString("count"))>0) isExist = true;
		}
		return isExist;
	}
	
	/*
	 * 返回数据表的所有字段信息
	 */
	public static Vector<TableField> getTableFields(String table){
		Vector<TableField> fields = new Vector<TableField>();
		
		try{
			String sqlstm = "PRAGMA table_info("+table.trim() + ")";
			ResultSet set = exeSqlStmGetResultset(sqlstm);
			while (set.next()) {
				TableField field = new TableField();
				field.setFieldName(set.getString(2));
				String typestring = set.getString(3);  //eg: number(6,0)
				String type = typestring.substring(0,typestring.indexOf('('));
				field.setFieldType(type);
				int len = 0,dec = 0;
				if (type.toUpperCase().equals("NUMBER")){
					len = Integer.parseInt(typestring.substring(typestring.indexOf('(')+1,typestring.indexOf(',')));
					dec = Integer.parseInt(typestring.substring(typestring.indexOf(',')+1,typestring.indexOf(')')));
				}else{
					len = Integer.parseInt(typestring.substring(typestring.indexOf('(')+1,typestring.indexOf(')')));
				}
				field.setFieldLen(len);
				field.setFieldDec(dec);
				fields.addElement(field);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return fields;
	}
	
	//test
/*	public static void main(String[] args) {
		String databasePath = "test02.db";
		try{
			//SqliteDB db = new SqliteDB(databasePath);
			//db.exeSql("create table tab1 (f1 varchar(10), f2 number(6,0), f3 number(6,2))");
			//createDatabase(databasePath);
		}catch(Exception exception){
			exception.printStackTrace();
		}
	}*/
}