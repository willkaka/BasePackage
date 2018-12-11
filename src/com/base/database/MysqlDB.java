package com.base.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MysqlDB {
	public String env = "DEV";
	public DatabaseInfo databaseInfo = null;

	public Connection con;
	
	//���췽��
	public MysqlDB(DatabaseInfo databaseInfo){
		try {
			if(con !=null && !con.isClosed())
				con.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String sDriver = "com.mysql.jdbc.Driver";
		// URLָ��Ҫ���ʵ����ݿ���mydata
		String sUrl = "jdbc:mysql://d0caes.mysql.dbdev.dsfdc.com:5093/caes?useUnicode=yes&characterEncoding=UTF8&useSSL=false";
		// MySQL����ʱ���û���
		String sUser = "deployop";
		// MySQL����ʱ������
		String sPassword = "Tc822bf22ec72#";
		try{
			if(databaseInfo == null){
				throw new Exception("���ݿ���Ϣ����Ϊ�գ�");
			}
			
			//�ر�ԭ������
			if(databaseInfo != null && databaseInfo != databaseInfo){
				if(con != null) con.close();
			}
						
			sDriver = databaseInfo.getDriver();
			sUrl = "jdbc:mysql://"+databaseInfo.getIp()+":"+databaseInfo.getPort()+"/"+databaseInfo.getLabel();
			sUser = databaseInfo.getUser();
			sPassword = databaseInfo.getPassword();
			
			Class.forName(sDriver);
			con= DriverManager.getConnection(sUrl, sUser, sPassword);
			           
        }catch (Exception e){
            e.printStackTrace();
        }
	}
	
	public void testString(String s1, String s2){
		if(s1.length() != s2.length()){System.out.println("length error");}
		
		for(int i=0; i<s1.length(); i++){
			if(s1.charAt(i) != s2.charAt(i)){
				System.out.println(i+","+s1.charAt(i)+","+s2.charAt(i));
			}
		}
		
	}
	
	public Connection getConnection(){
		return con;
	}
	
	public ResultSet getSqlResultSet(String sql){
		if(con == null)
			new MysqlDB(databaseInfo);
		
		try{
			PreparedStatement pra = con.prepareStatement(sql);
			ResultSet ret = pra.executeQuery();
			return ret;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public void exeSql(String sql) throws Exception{
		if(con == null)
			new MysqlDB(databaseInfo);
		
		PreparedStatement pra = con.prepareStatement(sql);
		pra.executeUpdate();
		pra.close();
	}
	
	public void closeCon(){
		if(con != null){
			try{
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void setDatabaseInfo(DatabaseInfo databaseInfo){
		this.databaseInfo = databaseInfo;
	}
	
	public static void main(String[] args){
		
		Connection con;
		//����������
		String sDriver = "com.mysql.jdbc.Driver";
		//URLָ��Ҫ���ʵ����ݿ���mydata
		String sUrl = "jdbc:mysql://d0caes.mysql.dbdev.dsfdc.com:5093/caes?useUnicode=yes&characterEncoding=UTF8&useSSL=false";
		//MySQL����ʱ���û���
		String sUser = "deployop";
		//MySQL����ʱ������
		String sPassword = "Tc822bf22ec72#";
		//������ѯ�����
		try {
			//������������
			Class.forName(sDriver);
			//1.getConnection()����������MySQL���ݿ⣡��
			con = DriverManager.getConnection(sUrl,sUser,sPassword);
			if(!con.isClosed())
				System.out.println("Succeeded connecting to the Database!");
			con.close();
			
			sDriver = "com.mysql.jdbc.Driver";
			sUrl = "jdbc:mysql://d0caes.mysql.dbdev.dsfdc.com:5093/caes?useUnicode=yes&characterEncoding=UTF8&useSSL=false";
			sUser = "deployop";
			sPassword = "Tc822bf22ec72#";
			
			System.out.println("driver:"+sDriver);
			System.out.println("url:"+sUrl);
			System.out.println("user:"+sUser);
			System.out.println("psw:"+sPassword);
			
			//Class.forName(sDriver).newInstance();
			Class.forName(sDriver);
			con= DriverManager.getConnection(sUrl, sUser, sPassword);
			if(!con.isClosed())
				System.out.println("Succeeded connecting to the Database!");
			con.close();
		}catch(Exception exception){
			exception.printStackTrace();
		}
	}
}
