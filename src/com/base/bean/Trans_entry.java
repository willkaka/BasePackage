package com.base.bean;

   /**
    * Trans_entry(���׿�Ŀ���ñ�)ʵ����
    * Mon May 28 11:31:08 CST 2018
    * @author huangyuanwei
    */ 

public class Trans_entry{
	private static final long serialVersionUID = 1L;

	//���ݱ�����
	public static final String DATABASE_TABLE_NAME = "trans_entry";


	//���״���
	private String TRANSID;
	public void setTransid(String TRANSID){
		this.TRANSID = TRANSID;
	}
	public String getTransid(){
		return TRANSID;
	}

	//��¼���
	private String SORTID;
	public void setSortid(String SORTID){
		this.SORTID = SORTID;
	}
	public String getSortid(){
		return SORTID;
	}

	//��������
	private String DIRECTION;
	public void setDirection(String DIRECTION){
		this.DIRECTION = DIRECTION;
	}
	public String getDirection(){
		return DIRECTION;
	}

	//��Ŀ
	private String SUBJECTNO;
	public void setSubjectno(String SUBJECTNO){
		this.SUBJECTNO = SUBJECTNO;
	}
	public String getSubjectno(){
		return SUBJECTNO;
	}

	//�����ʽ
	private String AMOUNT;
	public void setAmount(String AMOUNT){
		this.AMOUNT = AMOUNT;
	}
	public String getAmount(){
		return AMOUNT;
	}

	//״̬
	private String STATUS;
	public void setStatus(String STATUS){
		this.STATUS = STATUS;
	}
	public String getStatus(){
		return STATUS;
	}

	//�Ƿ���Ч����
	private String VALIDEXPRESSION;
	public void setValidexpression(String VALIDEXPRESSION){
		this.VALIDEXPRESSION = VALIDEXPRESSION;
	}
	public String getValidexpression(){
		return VALIDEXPRESSION;
	}

	//����ժҪ
	private String DIGEST;
	public void setDigest(String DIGEST){
		this.DIGEST = DIGEST;
	}
	public String getDigest(){
		return DIGEST;
	}

	//���־
	private String TEAMFLAG;
	public void setTeamflag(String TEAMFLAG){
		this.TEAMFLAG = TEAMFLAG;
	}
	public String getTeamflag(){
		return TEAMFLAG;
	}

	//����ӳ������(���ŷָ�)
	private String COVER;
	public void setCover(String COVER){
		this.COVER = COVER;
	}
	public String getCover(){
		return COVER;
	}

}

