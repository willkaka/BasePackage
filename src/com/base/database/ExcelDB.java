package com.base.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.Vector;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
  
public class ExcelDB {  

    private final String driverName ="sun.jdbc.odbc.JdbcOdbcDriver";
    //private String dbURL = "jdbc:odbc:driver={Microsoft Excel Driver (*.xls, *.xlsx, *.xlsm, *.xlsb)};DBQ=D:\\1.xls";
    private final String dbURL1 = "jdbc:odbc:driver={Microsoft Excel Driver (*.xls)};DBQ=";
    private final String dbURL2 = ")};DBQ=";
    
    private String excelPath = null;
    private Connection connection = null;
    
    /**
     * 构�?�函数：输入excel全路径，并建立连�?
     * @param excelPath
     */
 	public ExcelDB(String excelPath) {
		this.excelPath = excelPath;

		File file = new File(this.excelPath);
		if (file.exists()) {
			try {
				Class.forName(driverName);

				String excelType = excelPath.substring(excelPath.indexOf('.'));
				String dbURL = dbURL1 + excelType + dbURL2 + excelPath;

				Properties prop = new Properties(); // 避免中文乱码
				prop.put("charSet", "gb2312");

				this.connection = DriverManager.getConnection(dbURL, prop);

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else{
			System.out.println("\""+excelPath +"\"文件不存在！" );
		}
	} 

    /** 返回连接
     * @param �?
     */
	public Connection getExcelConn() {
		return this.connection;
	}
	
	/**
	 * 返回EXCEL的Sheet清单，支�?.xls/.xlsx�?
	 * @param excelPath
	 * @return
	 */
	public static Vector<String> getExcelSheets(String excelPath){
		Vector<String> sheetList = new Vector<String>();
		
		File file = new File(excelPath);
		if (!file.exists()) return null;		
		
		FileInputStream fi;
		try {
			fi = new FileInputStream(file);
			
			//HSSFWorkbook:是操作Excel2003以前（包�?2003）的版本，扩展名�?.xls 
			//XSSFWorkbook:是操作Excel2007的版本，扩展名是.xlsx
			if(excelPath.toLowerCase().indexOf(".xlsx")>0){
				XSSFWorkbook xssfWorkBook = new XSSFWorkbook(fi);
				for(int i=0;i<xssfWorkBook.getNumberOfSheets();i++){
					XSSFSheet xssfSheet = xssfWorkBook.getSheetAt(i);
					String sheetName = xssfSheet.getSheetName();// sheet名称，用于校验模板是否正�?
					sheetList.addElement(sheetName);
				}
			}else{
				HSSFWorkbook hssfWorkBook = new HSSFWorkbook(fi);
				for(int i=0;i<hssfWorkBook.getNumberOfSheets();i++){
					HSSFSheet xssfSheet = hssfWorkBook.getSheetAt(i);
					String sheetName = xssfSheet.getSheetName();// sheet名称，用于校验模板是否正�?
					sheetList.addElement(sheetName);
				}
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sheetList;
	}
	
	/**
	 * 读取Excel表格指定sheet的记�?
	 * @param dbConn excelConn
	 * @param sheetName et:sheet1
	 */
    public Vector<Object> getSheetRecords(String sheetName){
    	Vector<Object> records = new Vector<Object>();
    	Vector<String> fieldValue = new Vector<String>();
		
		try {
			Statement smt = this.connection.createStatement();

			ResultSet set = smt.executeQuery("select * from [" + sheetName + "$]");
			ResultSetMetaData metaData = set.getMetaData();
			for (int i = 1; i <= metaData.getColumnCount(); i++) {
				fieldValue.addElement(metaData.getColumnName(i));
				System.out.println(metaData.getColumnName(i));
			}
			records.addElement(fieldValue);//首行为字段名�?
			
			while (set.next()) {
				fieldValue.clear();
				System.out.println("colCount:"+metaData.getColumnCount());
				for (int i = 1; i <= metaData.getColumnCount(); i++) {
					String string = "";
					if (metaData.getColumnType(i) == 8) {
						string = set.getInt(i) + "";
					} else {
						string = set.getString(i) + "";
					}
					System.out.println("value:"+string);
					fieldValue.addElement(string);
				}
				records.addElement(fieldValue);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return records;
    }
    
    public static void main(String[] args) {  
        ExcelDB excelDB = new ExcelDB("D:\\11.xls");
        Vector<String> sheetList = getExcelSheets("D:\\11.xls");
        
        for(String sheetName:sheetList){
        	System.out.println("sheet:"+sheetName);
        	Vector records = excelDB.getSheetRecords(sheetName);
        	
        	for(Object oneRow:records){
        		Vector<String> record = (Vector<String>) oneRow;
        		for(String value:record){
        			System.out.print(value+"\t");
        		}
        		System.out.println("");
        	}
        	
        	/*for(int rowNum=0;rowNum<records.size();rowNum++){
        		Vector record = (Vector) records.get(rowNum);
        		for(int colNum=0;colNum<record.size();colNum++){
        			String fieldValue = (String) record.get(colNum);
        			System.out.print(fieldValue+"\t");
        		}
        		System.out.println("");
        	}*/
        }
        //excelDB.getExcelConn();
        //excelDB.getExcelSheets();
    }
}