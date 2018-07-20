package com.base.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OracleDB {
	public static String env = "DEV";
	public static DatabaseInfo databaseInfo = null;

	public static Connection con;
	
	//构造方法
/*	public OracleDB(String DatabaseEnv){
		try{
			String driver = ReadXMLFile.getXmlFileInfo("./config/DataBase.xml","DataBase","DBName","Loan","DriverName");
            Class.forName(driver).newInstance();  //oracle.jdbc.driver.OracleDriver  ojdbc14.jar
            
            con= DriverManager.getConnection(
                ReadXMLFile.getXmlFileInfo2("./config/DataBase.xml","DataBase","Env",DatabaseEnv,"DBName","Loan","URL"),
                ReadXMLFile.getXmlFileInfo2("./config/DataBase.xml","DataBase","Env",DatabaseEnv,"DBName","Loan","UserName"),
                ReadXMLFile.getXmlFileInfo2("./config/DataBase.xml","DataBase","Env",DatabaseEnv,"DBName","Loan","Password"));
        }catch (Exception e){
            e.printStackTrace();
        }
	}*/
	
	//构造方法
	public OracleDB(DatabaseInfo databaseInfo){
		
		try{
			if(databaseInfo == null){
				throw new Exception("数据库信息不能为空！");
			}
			
			//关闭原有连接
			if(OracleDB.databaseInfo != null && OracleDB.databaseInfo != databaseInfo){
				if(OracleDB.con != null) OracleDB.con.close();
			}
			
			Class.forName(databaseInfo.getDriver()).newInstance();
			con= DriverManager.getConnection("jdbc:oracle:thin:@"+databaseInfo.getIp()+":"+databaseInfo.getPort()+":"+databaseInfo.getLabel(),
					databaseInfo.getUser(),
					databaseInfo.getPassword());
        }catch (Exception e){
            e.printStackTrace();
        }
	}
	
	public static Connection getConnection(){
		return con;
	}
	
	public static ResultSet getSqlResultSet(String sql){
		if(con == null)
			new OracleDB(databaseInfo);
		
		try{
			PreparedStatement pra = con.prepareStatement(sql);
			ResultSet ret = pra.executeQuery();
			return ret;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public static void exeSql(String sql) throws Exception{
		if(con == null)
			new OracleDB(databaseInfo);
		
		PreparedStatement pra = con.prepareStatement(sql);
		pra.executeUpdate();
		pra.close();
	}
	
	public static void closeCon(){
		if(con != null){
			try{
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
/*	public static void setEnv(String env) {
		OracleDB.env = env;
		new OracleDB(DatabaseInfo.getDatabaseInfo(sqliteConn, env));
	}*/
	
	public static void setDatabaseInfo(DatabaseInfo databaseInfo){
		OracleDB.databaseInfo = databaseInfo;
	}
}
