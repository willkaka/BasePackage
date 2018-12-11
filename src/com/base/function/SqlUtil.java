package com.base.function;

import java.util.Date;
import java.util.StringTokenizer;

import org.apache.poi.ss.usermodel.DateUtil;

public class SqlUtil {

	/**
	 * ���PreparedStatement�����ݿ��ύ��SQL���
	 *
	 * @param sql����ռλ��?��ԭʼsql
	 * @param params����������
	 * @return��Ҫִ�е�sql���
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
