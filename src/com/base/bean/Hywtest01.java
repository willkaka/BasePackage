package com.base.bean;

   /**
    * Hywtest01实体类
    * Mon May 28 11:31:08 CST 2018
    * @author huangyuanwei
    */ 

public class Hywtest01{
	private static final long serialVersionUID = 1L;

	//数据表名称
	public static final String DATABASE_TABLE_NAME = "hywtest01";


	//SEQ
	private String SEQ;
	public void setSeq(String SEQ){
		this.SEQ = SEQ;
	}
	public String getSeq(){
		return SEQ;
	}

	//NAME
	private Long NAME;
	public void setName(Long NAME){
		this.NAME = NAME;
	}
	public Long getName(){
		return NAME;
	}

	//C
	private Long C;
	public void setC(Long C){
		this.C = C;
	}
	public Long getC(){
		return C;
	}

	//D
	private Long D;
	public void setD(Long D){
		this.D = D;
	}
	public Long getD(){
		return D;
	}

	//备注
	private String COM;
	public void setCom(String COM){
		this.COM = COM;
	}
	public String getCom(){
		return COM;
	}

	//备注2
	private String COM2;
	public void setCom2(String COM2){
		this.COM2 = COM2;
	}
	public String getCom2(){
		return COM2;
	}

}

