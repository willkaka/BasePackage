package com.base.database;

public class TableField {
	private int seq = 0;
	private String fieldName = null;
	private String fieldDsc = null;
	private String fieldType = null;
	private int fieldLen = 0;
	private int fieldDec = 0;
	private Object fieldValue = null;
	private String table = null;
	private String tableshort = null;
	
	public String getTypeShowString(){
		String type = fieldType.toUpperCase().trim();
		String typestring= type+"("+fieldLen;
		
		if (type.equals("NUMBER") || 
				type.equals("INTEGER") || 
				type.equals("DOUBLE") || 
				type.equals("LONG") || 
				type.equals("FLOAT")){
			typestring = typestring + "," + fieldDec + ")";
		}else{
			typestring = typestring + ")";
		}
		return typestring;
	}
	
	public String getValueShowString(){
		String type = fieldType.toUpperCase().trim();
		String value= null;
		
		if (type.equals("NUMBER") || 
				type.equals("INTEGER") || 
				type.equals("DOUBLE") || 
				type.equals("LONG") || 
				type.equals("FLOAT")){
			value = fieldValue.toString();
		}else if(type.equals("DATE")){
			value = fieldValue.toString();
		}else{
			value = fieldValue.toString();
		}
		return value;
	}
	
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getFieldDsc() {
		return fieldDsc;
	}
	public void setFieldDsc(String fieldDsc) {
		this.fieldDsc = fieldDsc;
	}
	public String getFieldType() {
		return fieldType;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	public int getFieldLen() {
		return fieldLen;
	}
	public void setFieldLen(int fieldLen) {
		this.fieldLen = fieldLen;
	}
	public int getFieldDec() {
		return fieldDec;
	}
	public void setFieldDec(int fieldDec) {
		this.fieldDec = fieldDec;
	}
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
	public String getTableshort() {
		return tableshort;
	}
	public void setTableshort(String tableshort) {
		this.tableshort = tableshort;
	}
	public Object getFieldValue() {
		return fieldValue;
	}
	public void setFieldValue(Object fieldValue) {
		this.fieldValue = fieldValue;
	}
	
	
}
