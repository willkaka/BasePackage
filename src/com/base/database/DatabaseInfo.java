package com.base.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;

public class DatabaseInfo {
	private int seq;
	private String envname;
	private String envdsc;
	private String dbtype;
	private String ip;
	private int port;
	private String label;
	private String user;
	private String password;
	private String driver;
	
	public static List<DatabaseInfo> getEnvList(Connection sqliteConn){
		ArrayList<DatabaseInfo> envList = new ArrayList<DatabaseInfo>();
		
		try {
			String sqlStm = "SELECT * FROM envdatabaseinfo";
			PreparedStatement preparedStatement = sqliteConn.prepareStatement(sqlStm);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()){
				DatabaseInfo dbinfo = new DatabaseInfo();
				dbinfo.setSeq(rs.getInt("seq"));
				dbinfo.setEnvname(rs.getString("envname"));
				dbinfo.setEnvdsc(rs.getString("envdsc"));
				dbinfo.setDbtype(rs.getString("dbtype"));
				dbinfo.setIp(rs.getString("ip"));
				dbinfo.setPort(rs.getInt("port"));
				dbinfo.setLabel(rs.getString("label"));
				dbinfo.setUser(rs.getString("user"));
				dbinfo.setPassword(rs.getString("password"));
				dbinfo.setDriver(rs.getString("driver"));
				
				envList.add( dbinfo );
			}
			rs.close();
			preparedStatement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return envList;
	}
	
	public static DatabaseInfo getDatabaseInfo(Connection sqliteConn, String env){
		DatabaseInfo dbinfo = new DatabaseInfo();
		
		try {
			String sqlStm = "SELECT * FROM envdatabaseinfo WHERE envname = '" + env + "'";
			PreparedStatement preparedStatement = sqliteConn.prepareStatement(sqlStm);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()){
				dbinfo.setSeq(rs.getInt("seq"));
				dbinfo.setEnvname(rs.getString("envname"));
				dbinfo.setEnvdsc(rs.getString("envdsc"));
				dbinfo.setDbtype(rs.getString("dbtype"));
				dbinfo.setIp(rs.getString("ip"));
				dbinfo.setPort(rs.getInt("port"));
				dbinfo.setLabel(rs.getString("label"));
				dbinfo.setUser(rs.getString("user"));
				dbinfo.setPassword(rs.getString("password"));
				dbinfo.setDriver(rs.getString("driver"));
			}
			rs.close();
			preparedStatement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return dbinfo;
	}
	
	public static Connection getDBConnection(DatabaseInfo databaseInfo){
		Connection connection = null;
		
		if(databaseInfo.getDbtype() == null) {
			return null;
		}
		
		if("Oracle".equals(databaseInfo.getDbtype())){
			OracleDB db = new OracleDB(databaseInfo);
			connection = db.getConnection();
		}
		
		if("MySql".equals(databaseInfo.getDbtype())){
			MysqlDB db = new MysqlDB(databaseInfo);
			connection = db.getConnection();
		}
		
		if("Sqlite".equals(databaseInfo.getDbtype())){
			SqliteDB db = new SqliteDB(databaseInfo);
			connection = db.getConnection();
		}
		
		return connection;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}
	

	public String getEnvname() {
		return envname;
	}

	public void setEnvname(String envname) {
		this.envname = envname;
	}

	public String getEnvdsc() {
		return envdsc;
	}

	public void setEnvdsc(String envdsc) {
		this.envdsc = envdsc;
	}
	
	public String getDbtype() {
		return dbtype;
	}

	public void setDbtype(String dbtype) {
		this.dbtype = dbtype;
	}
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

}
