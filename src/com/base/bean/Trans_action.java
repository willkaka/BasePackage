package com.base.bean;

   /**
    * Trans_action(�����ܱ�)ʵ����
    * Mon May 28 11:31:08 CST 2018
    * @author huangyuanwei
    */ 

public class Trans_action{
	private static final long serialVersionUID = 1L;

	//���ݱ�����
	public static final String DATABASE_TABLE_NAME = "trans_action";


	//���״���
	private String TRANSID;
	public void setTransid(String TRANSID){
		this.TRANSID = TRANSID;
	}
	public String getTransid(){
		return TRANSID;
	}

	//��������
	private String NAME;
	public void setName(String NAME){
		this.NAME = NAME;
	}
	public String getName(){
		return NAME;
	}

	//��������
	private String DESCRIPTION;
	public void setDescription(String DESCRIPTION){
		this.DESCRIPTION = DESCRIPTION;
	}
	public String getDescription(){
		return DESCRIPTION;
	}

	//����ִ�й���
	private String EXECUTESCRIPT;
	public void setExecutescript(String EXECUTESCRIPT){
		this.EXECUTESCRIPT = EXECUTESCRIPT;
	}
	public String getExecutescript(){
		return EXECUTESCRIPT;
	}

	//״̬
	private String STATUS;
	public void setStatus(String STATUS){
		this.STATUS = STATUS;
	}
	public String getStatus(){
		return STATUS;
	}

	//�����ܴ���
	private String ID;
	public void setId(String ID){
		this.ID = ID;
	}
	public String getId(){
		return ID;
	}

	//LAS �Ŵ���  IRR ���������
	private String ACCOUNTSET;
	public void setAccountset(String ACCOUNTSET){
		this.ACCOUNTSET = ACCOUNTSET;
	}
	public String getAccountset(){
		return ACCOUNTSET;
	}

	//������
	private String CLASSNAME;
	public void setClassname(String CLASSNAME){
		this.CLASSNAME = CLASSNAME;
	}
	public String getClassname(){
		return CLASSNAME;
	}

	//���������˾
	private String ACCOUNTOWNER;
	public void setAccountowner(String ACCOUNTOWNER){
		this.ACCOUNTOWNER = ACCOUNTOWNER;
	}
	public String getAccountowner(){
		return ACCOUNTOWNER;
	}

}

