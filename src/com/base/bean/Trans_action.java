package com.base.bean;

   /**
    * Trans_action(交易总表)实体类
    * Mon May 28 11:31:08 CST 2018
    * @author huangyuanwei
    */ 

public class Trans_action{
	private static final long serialVersionUID = 1L;

	//数据表名称
	public static final String DATABASE_TABLE_NAME = "trans_action";


	//交易代码
	private String TRANSID;
	public void setTransid(String TRANSID){
		this.TRANSID = TRANSID;
	}
	public String getTransid(){
		return TRANSID;
	}

	//交易名称
	private String NAME;
	public void setName(String NAME){
		this.NAME = NAME;
	}
	public String getName(){
		return NAME;
	}

	//交易描述
	private String DESCRIPTION;
	public void setDescription(String DESCRIPTION){
		this.DESCRIPTION = DESCRIPTION;
	}
	public String getDescription(){
		return DESCRIPTION;
	}

	//交易执行规则
	private String EXECUTESCRIPT;
	public void setExecutescript(String EXECUTESCRIPT){
		this.EXECUTESCRIPT = EXECUTESCRIPT;
	}
	public String getExecutescript(){
		return EXECUTESCRIPT;
	}

	//状态
	private String STATUS;
	public void setStatus(String STATUS){
		this.STATUS = STATUS;
	}
	public String getStatus(){
		return STATUS;
	}

	//交易总代码
	private String ID;
	public void setId(String ID){
		this.ID = ID;
	}
	public String getId(){
		return ID;
	}

	//LAS 信贷帐  IRR 安永审计帐
	private String ACCOUNTSET;
	public void setAccountset(String ACCOUNTSET){
		this.ACCOUNTSET = ACCOUNTSET;
	}
	public String getAccountset(){
		return ACCOUNTSET;
	}

	//处理类
	private String CLASSNAME;
	public void setClassname(String CLASSNAME){
		this.CLASSNAME = CLASSNAME;
	}
	public String getClassname(){
		return CLASSNAME;
	}

	//贷款归属公司
	private String ACCOUNTOWNER;
	public void setAccountowner(String ACCOUNTOWNER){
		this.ACCOUNTOWNER = ACCOUNTOWNER;
	}
	public String getAccountowner(){
		return ACCOUNTOWNER;
	}

}

