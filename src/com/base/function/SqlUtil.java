package com.base.function;

import java.util.Date;
import java.util.StringTokenizer;

import org.apache.poi.ss.usermodel.DateUtil;

public class SqlUtil {

	/**
	 * 获得PreparedStatement向数据库提交的SQL语句
	 *
	 * @param sql：带占位符?的原始sql
	 * @param params：参数数组
	 * @return：要执行的sql语句
	 */
	 
	private static String getSQL(String targetStr, Object[] sqlValues) {
		StringTokenizer token = new StringTokenizer(targetStr, "?", false);
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < sqlValues.length; i++) {
		buf.append(token.nextToken());
		buf.append("'" + sqlValues[i] + "'");
		}
		return buf.toString();
		}
	
}
