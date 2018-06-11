package com.base.bean;

   /**
    * Trans_entry(交易科目对用表)实体类
    * Mon May 28 11:31:08 CST 2018
    * @author huangyuanwei
    */ 

public class Trans_entry{
	private static final long serialVersionUID = 1L;

	//数据表名称
	public static final String DATABASE_TABLE_NAME = "trans_entry";


	//交易代码
	private String TRANSID;
	public void setTransid(String TRANSID){
		this.TRANSID = TRANSID;
	}
	public String getTransid(){
		return TRANSID;
	}

	//分录编号
	private String SORTID;
	public void setSortid(String SORTID){
		this.SORTID = SORTID;
	}
	public String getSortid(){
		return SORTID;
	}

	//发生方向
	private String DIRECTION;
	public void setDirection(String DIRECTION){
		this.DIRECTION = DIRECTION;
	}
	public String getDirection(){
		return DIRECTION;
	}

	//科目
	private String SUBJECTNO;
	public void setSubjectno(String SUBJECTNO){
		this.SUBJECTNO = SUBJECTNO;
	}
	public String getSubjectno(){
		return SUBJECTNO;
	}

	//金额表达式
	private String AMOUNT;
	public void setAmount(String AMOUNT){
		this.AMOUNT = AMOUNT;
	}
	public String getAmount(){
		return AMOUNT;
	}

	//状态
	private String STATUS;
	public void setStatus(String STATUS){
		this.STATUS = STATUS;
	}
	public String getStatus(){
		return STATUS;
	}

	//是否有效检验
	private String VALIDEXPRESSION;
	public void setValidexpression(String VALIDEXPRESSION){
		this.VALIDEXPRESSION = VALIDEXPRESSION;
	}
	public String getValidexpression(){
		return VALIDEXPRESSION;
	}

	//发生摘要
	private String DIGEST;
	public void setDigest(String DIGEST){
		this.DIGEST = DIGEST;
	}
	public String getDigest(){
		return DIGEST;
	}

	//组标志
	private String TEAMFLAG;
	public void setTeamflag(String TEAMFLAG){
		this.TEAMFLAG = TEAMFLAG;
	}
	public String getTeamflag(){
		return TEAMFLAG;
	}

	//覆盖映射条件(逗号分隔)
	private String COVER;
	public void setCover(String COVER){
		this.COVER = COVER;
	}
	public String getCover(){
		return COVER;
	}

}

