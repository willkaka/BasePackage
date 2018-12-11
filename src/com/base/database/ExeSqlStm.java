package com.base.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ExeSqlStm {
	
	public static ResultSet getSqlResultSet(String sql, Connection con){
		if(con == null)	return null;
		
		try{
			PreparedStatement pra = con.prepareStatement(sql);
			ResultSet ret = pra.executeQuery();
			return ret;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public static void exeSql(String sql, Connection con) throws Exception{
		if(con == null) return;
		
		PreparedStatement pra = con.prepareStatement(sql);
		pra.executeUpdate();
		pra.close();
	}
}
