package com.base.bean;

   /**
    * Loanback_status(����ƻ���)ʵ����
    * Mon May 28 11:31:08 CST 2018
    * @author huangyuanwei
    */ 

public class Loanback_status{
	private static final long serialVersionUID = 1L;

	//���ݱ�����
	public static final String DATABASE_TABLE_NAME = "loanback_status";


	//��ݱ��
	private String PUTOUTNO;
	public void setPutoutno(String PUTOUTNO){
		this.PUTOUTNO = PUTOUTNO;
	}
	public String getPutoutno(){
		return PUTOUTNO;
	}

	//�ڴ�
	private Long STERM;
	public void setSterm(Long STERM){
		this.STERM = STERM;
	}
	public Long getSterm(){
		return STERM;
	}

	//��ǰ�������
	private Long AHEADNUM;
	public void setAheadnum(Long AHEADNUM){
		this.AHEADNUM = AHEADNUM;
	}
	public Long getAheadnum(){
		return AHEADNUM;
	}

	//Ӧ������
	private String PAYDATE;
	public void setPaydate(String PAYDATE){
		this.PAYDATE = PAYDATE;
	}
	public String getPaydate(){
		return PAYDATE;
	}

	//����
	private String CURRENCY;
	public void setCurrency(String CURRENCY){
		this.CURRENCY = CURRENCY;
	}
	public String getCurrency(){
		return CURRENCY;
	}

	//������
	private Long GRACEPERIOD;
	public void setGraceperiod(Long GRACEPERIOD){
		this.GRACEPERIOD = GRACEPERIOD;
	}
	public Long getGraceperiod(){
		return GRACEPERIOD;
	}

	//Ӧ������
	private Long PAYCURRENTCORP;
	public void setPaycurrentcorp(Long PAYCURRENTCORP){
		this.PAYCURRENTCORP = PAYCURRENTCORP;
	}
	public Long getPaycurrentcorp(){
		return PAYCURRENTCORP;
	}

	//ʵ������
	private Long ACTUALCURRENTCORP;
	public void setActualcurrentcorp(Long ACTUALCURRENTCORP){
		this.ACTUALCURRENTCORP = ACTUALCURRENTCORP;
	}
	public Long getActualcurrentcorp(){
		return ACTUALCURRENTCORP;
	}

	//Ӧ��ΥԼ����
	private Long PAYDEFAULTCORP;
	public void setPaydefaultcorp(Long PAYDEFAULTCORP){
		this.PAYDEFAULTCORP = PAYDEFAULTCORP;
	}
	public Long getPaydefaultcorp(){
		return PAYDEFAULTCORP;
	}

	//ʵ��ΥԼ����
	private Long ACTUALDEFAULTCORP;
	public void setActualdefaultcorp(Long ACTUALDEFAULTCORP){
		this.ACTUALDEFAULTCORP = ACTUALDEFAULTCORP;
	}
	public Long getActualdefaultcorp(){
		return ACTUALDEFAULTCORP;
	}

	//Ӧ�����ڱ���
	private Long PAYOVERDUECORP;
	public void setPayoverduecorp(Long PAYOVERDUECORP){
		this.PAYOVERDUECORP = PAYOVERDUECORP;
	}
	public Long getPayoverduecorp(){
		return PAYOVERDUECORP;
	}

	//ʵ�����ڱ���
	private Long ACTUALOVERDUECORP;
	public void setActualoverduecorp(Long ACTUALOVERDUECORP){
		this.ACTUALOVERDUECORP = ACTUALOVERDUECORP;
	}
	public Long getActualoverduecorp(){
		return ACTUALOVERDUECORP;
	}

	//Ӧ����Ϣ
	private Long PAYINTE;
	public void setPayinte(Long PAYINTE){
		this.PAYINTE = PAYINTE;
	}
	public Long getPayinte(){
		return PAYINTE;
	}

	//ʵ����Ϣ
	private Long ACTUALINTE;
	public void setActualinte(Long ACTUALINTE){
		this.ACTUALINTE = ACTUALINTE;
	}
	public Long getActualinte(){
		return ACTUALINTE;
	}

	//Ӧ��Ӧ����Ϣ
	private Long PAYINNERINTE;
	public void setPayinnerinte(Long PAYINNERINTE){
		this.PAYINNERINTE = PAYINNERINTE;
	}
	public Long getPayinnerinte(){
		return PAYINNERINTE;
	}

	//ʵ��Ӧ����Ϣ
	private Long ACTUALINNERINTE;
	public void setActualinnerinte(Long ACTUALINNERINTE){
		this.ACTUALINNERINTE = ACTUALINNERINTE;
	}
	public Long getActualinnerinte(){
		return ACTUALINNERINTE;
	}

	//Ӧ����Ӧ����Ϣ
	private Long PAYOUTINTE;
	public void setPayoutinte(Long PAYOUTINTE){
		this.PAYOUTINTE = PAYOUTINTE;
	}
	public Long getPayoutinte(){
		return PAYOUTINTE;
	}

	//ʵ����Ӧ����Ϣ
	private Long ACTUALOUTINTE;
	public void setActualoutinte(Long ACTUALOUTINTE){
		this.ACTUALOUTINTE = ACTUALOUTINTE;
	}
	public Long getActualoutinte(){
		return ACTUALOUTINTE;
	}

	//ʵ������
	private Long ACTUALINNERINTEFINE;
	public void setActualinnerintefine(Long ACTUALINNERINTEFINE){
		this.ACTUALINNERINTEFINE = ACTUALINNERINTEFINE;
	}
	public Long getActualinnerintefine(){
		return ACTUALINNERINTEFINE;
	}

	//ʵ����Ϣ
	private Long ACTUALOUTINTEFINE;
	public void setActualoutintefine(Long ACTUALOUTINTEFINE){
		this.ACTUALOUTINTEFINE = ACTUALOUTINTEFINE;
	}
	public Long getActualoutintefine(){
		return ACTUALOUTINTEFINE;
	}

	//Ӧ������
	private Long PAYINNERINTEFINE;
	public void setPayinnerintefine(Long PAYINNERINTEFINE){
		this.PAYINNERINTEFINE = PAYINNERINTEFINE;
	}
	public Long getPayinnerintefine(){
		return PAYINNERINTEFINE;
	}

	//Ӧ����Ϣ
	private Long PAYOUTINTEFINE;
	public void setPayoutintefine(Long PAYOUTINTEFINE){
		this.PAYOUTINTEFINE = PAYOUTINTEFINE;
	}
	public Long getPayoutintefine(){
		return PAYOUTINTEFINE;
	}

	//��������
	private String ACCDATE;
	public void setAccdate(String ACCDATE){
		this.ACCDATE = ACCDATE;
	}
	public String getAccdate(){
		return ACCDATE;
	}

	//�����־
	private String PAYOFFFLAG;
	public void setPayoffflag(String PAYOFFFLAG){
		this.PAYOFFFLAG = PAYOFFFLAG;
	}
	public String getPayoffflag(){
		return PAYOFFFLAG;
	}

	//��������
	private Long INTEFINEBASE;
	public void setIntefinebase(Long INTEFINEBASE){
		this.INTEFINEBASE = INTEFINEBASE;
	}
	public Long getIntefinebase(){
		return INTEFINEBASE;
	}

	//��������
	private String UPDATEDATE;
	public void setUpdatedate(String UPDATEDATE){
		this.UPDATEDATE = UPDATEDATE;
	}
	public String getUpdatedate(){
		return UPDATEDATE;
	}

	//���ڱ�����Ϣ
	private Long PAYOVERDUECORPINTE;
	public void setPayoverduecorpinte(Long PAYOVERDUECORPINTE){
		this.PAYOVERDUECORPINTE = PAYOVERDUECORPINTE;
	}
	public Long getPayoverduecorpinte(){
		return PAYOVERDUECORPINTE;
	}

	//��Ϣ����
	private Long OVERDUECORPINTEBASE;
	public void setOverduecorpintebase(Long OVERDUECORPINTEBASE){
		this.OVERDUECORPINTEBASE = OVERDUECORPINTEBASE;
	}
	public Long getOverduecorpintebase(){
		return OVERDUECORPINTEBASE;
	}

	//����
	private String ORGID;
	public void setOrgid(String ORGID){
		this.ORGID = ORGID;
	}
	public String getOrgid(){
		return ORGID;
	}

	//��Ϣ����
	private String FINEBASEDATE;
	public void setFinebasedate(String FINEBASEDATE){
		this.FINEBASEDATE = FINEBASEDATE;
	}
	public String getFinebasedate(){
		return FINEBASEDATE;
	}

	//�Ӽ�����
	private Long WAITOVERDUECORPINTE;
	public void setWaitoverduecorpinte(Long WAITOVERDUECORPINTE){
		this.WAITOVERDUECORPINTE = WAITOVERDUECORPINTE;
	}
	public Long getWaitoverduecorpinte(){
		return WAITOVERDUECORPINTE;
	}

	//����ʣ�౾��
	private Long RESIDUALAMOUNT;
	public void setResidualamount(Long RESIDUALAMOUNT){
		this.RESIDUALAMOUNT = RESIDUALAMOUNT;
	}
	public Long getResidualamount(){
		return RESIDUALAMOUNT;
	}

	//Ӧ�����շ���1
	private Long PAYAMOUNT1;
	public void setPayamount1(Long PAYAMOUNT1){
		this.PAYAMOUNT1 = PAYAMOUNT1;
	}
	public Long getPayamount1(){
		return PAYAMOUNT1;
	}

	//ʵ�����շ���1
	private Long ACTUALPAYAMOUNT1;
	public void setActualpayamount1(Long ACTUALPAYAMOUNT1){
		this.ACTUALPAYAMOUNT1 = ACTUALPAYAMOUNT1;
	}
	public Long getActualpayamount1(){
		return ACTUALPAYAMOUNT1;
	}

	//Ӧ���ʽ�ռ�÷�
	private Long PAYMONEY;
	public void setPaymoney(Long PAYMONEY){
		this.PAYMONEY = PAYMONEY;
	}
	public Long getPaymoney(){
		return PAYMONEY;
	}

	//ʵ���ʽ�ռ�÷�
	private Long ACTUALPAYMONEY;
	public void setActualpaymoney(Long ACTUALPAYMONEY){
		this.ACTUALPAYMONEY = ACTUALPAYMONEY;
	}
	public Long getActualpaymoney(){
		return ACTUALPAYMONEY;
	}

	//����״̬��ʶ(1:�Ѿ�ִ�пۿ�)
	private String AXFLAG;
	public void setAxflag(String AXFLAG){
		this.AXFLAG = AXFLAG;
	}
	public String getAxflag(){
		return AXFLAG;
	}

	//����ʱ��
	private String DCPAYDATE;
	public void setDcpaydate(String DCPAYDATE){
		this.DCPAYDATE = DCPAYDATE;
	}
	public String getDcpaydate(){
		return DCPAYDATE;
	}

	//������ʶ
	private String DCSTATUS;
	public void setDcstatus(String DCSTATUS){
		this.DCSTATUS = DCSTATUS;
	}
	public String getDcstatus(){
		return DCSTATUS;
	}

	//����Ӧ������
	private Long DCPAYCORP;
	public void setDcpaycorp(Long DCPAYCORP){
		this.DCPAYCORP = DCPAYCORP;
	}
	public Long getDcpaycorp(){
		return DCPAYCORP;
	}

	//����ʵ������
	private Long DCACTUALCORP;
	public void setDcactualcorp(Long DCACTUALCORP){
		this.DCACTUALCORP = DCACTUALCORP;
	}
	public Long getDcactualcorp(){
		return DCACTUALCORP;
	}

	//����Ӧ����Ϣ
	private Long DCPAYINTE;
	public void setDcpayinte(Long DCPAYINTE){
		this.DCPAYINTE = DCPAYINTE;
	}
	public Long getDcpayinte(){
		return DCPAYINTE;
	}

	//����ʵ����Ϣ
	private Long DCACTUALINTE;
	public void setDcactualinte(Long DCACTUALINTE){
		this.DCACTUALINTE = DCACTUALINTE;
	}
	public Long getDcactualinte(){
		return DCACTUALINTE;
	}

	//����Ӧ����Ϣ
	private Long DCPAYOUTFINE;
	public void setDcpayoutfine(Long DCPAYOUTFINE){
		this.DCPAYOUTFINE = DCPAYOUTFINE;
	}
	public Long getDcpayoutfine(){
		return DCPAYOUTFINE;
	}

	//����ʵ����Ϣ
	private Long DCACTUALOUTFINE;
	public void setDcactualoutfine(Long DCACTUALOUTFINE){
		this.DCACTUALOUTFINE = DCACTUALOUTFINE;
	}
	public Long getDcactualoutfine(){
		return DCACTUALOUTFINE;
	}

	//����Ӧ������
	private Long DCPAYINNERFINE;
	public void setDcpayinnerfine(Long DCPAYINNERFINE){
		this.DCPAYINNERFINE = DCPAYINNERFINE;
	}
	public Long getDcpayinnerfine(){
		return DCPAYINNERFINE;
	}

	//����ʵ������
	private Long DCACTUALINNERFINE;
	public void setDcactualinnerfine(Long DCACTUALINNERFINE){
		this.DCACTUALINNERFINE = DCACTUALINNERFINE;
	}
	public Long getDcactualinnerfine(){
		return DCACTUALINNERFINE;
	}

	//�ֽ���
	private Long CASHFLOW;
	public void setCashflow(Long CASHFLOW){
		this.CASHFLOW = CASHFLOW;
	}
	public Long getCashflow(){
		return CASHFLOW;
	}

	//�ſ�����
	private Long PUTOUTDAY;
	public void setPutoutday(Long PUTOUTDAY){
		this.PUTOUTDAY = PUTOUTDAY;
	}
	public Long getPutoutday(){
		return PUTOUTDAY;
	}

	//�ۼƷſ�����
	private Long TOLPUTOUTDAY;
	public void setTolputoutday(Long TOLPUTOUTDAY){
		this.TOLPUTOUTDAY = TOLPUTOUTDAY;
	}
	public Long getTolputoutday(){
		return TOLPUTOUTDAY;
	}

	//ʣ�౾��
	private Long REMAINCORP;
	public void setRemaincorp(Long REMAINCORP){
		this.REMAINCORP = REMAINCORP;
	}
	public Long getRemaincorp(){
		return REMAINCORP;
	}

	//�������ֵ
	private Long FEEPRESENTVALUE;
	public void setFeepresentvalue(Long FEEPRESENTVALUE){
		this.FEEPRESENTVALUE = FEEPRESENTVALUE;
	}
	public Long getFeepresentvalue(){
		return FEEPRESENTVALUE;
	}

	//�����-��Ϣ����
	private Long FEEINTEADJUST;
	public void setFeeinteadjust(Long FEEINTEADJUST){
		this.FEEINTEADJUST = FEEINTEADJUST;
	}
	public Long getFeeinteadjust(){
		return FEEINTEADJUST;
	}

	//�����-����
	private Long FEECORP;
	public void setFeecorp(Long FEECORP){
		this.FEECORP = FEECORP;
	}
	public Long getFeecorp(){
		return FEECORP;
	}

	//ʵ�������-����
	private Long ACTUALFEECORP;
	public void setActualfeecorp(Long ACTUALFEECORP){
		this.ACTUALFEECORP = ACTUALFEECORP;
	}
	public Long getActualfeecorp(){
		return ACTUALFEECORP;
	}

	//����ѱ������
	private Long FEEREMAINCORP;
	public void setFeeremaincorp(Long FEEREMAINCORP){
		this.FEEREMAINCORP = FEEREMAINCORP;
	}
	public Long getFeeremaincorp(){
		return FEEREMAINCORP;
	}

	//��������-�����
	private Long FEEDEFERREDVALUE;
	public void setFeedeferredvalue(Long FEEDEFERREDVALUE){
		this.FEEDEFERREDVALUE = FEEDEFERREDVALUE;
	}
	public Long getFeedeferredvalue(){
		return FEEDEFERREDVALUE;
	}

	//�����-��Ϣ����
	private Long FEEINTE;
	public void setFeeinte(Long FEEINTE){
		this.FEEINTE = FEEINTE;
	}
	public Long getFeeinte(){
		return FEEINTE;
	}

	//ʵ�������-��Ϣ����
	private Long ACTUALPAYFEEINTE;
	public void setActualpayfeeinte(Long ACTUALPAYFEEINTE){
		this.ACTUALPAYFEEINTE = ACTUALPAYFEEINTE;
	}
	public Long getActualpayfeeinte(){
		return ACTUALPAYFEEINTE;
	}

	//���ӹ�������
	private Long FEEREMAINDEFERREDVALUE;
	public void setFeeremaindeferredvalue(Long FEEREMAINDEFERREDVALUE){
		this.FEEREMAINDEFERREDVALUE = FEEREMAINDEFERREDVALUE;
	}
	public Long getFeeremaindeferredvalue(){
		return FEEREMAINDEFERREDVALUE;
	}

	//�¹���Ѽ���Ϣ����ϼ�
	private Long TOLFEEINTE;
	public void setTolfeeinte(Long TOLFEEINTE){
		this.TOLFEEINTE = TOLFEEINTE;
	}
	public Long getTolfeeinte(){
		return TOLFEEINTE;
	}

	//��ѯ������
	private Long CONFEE;
	public void setConfee(Long CONFEE){
		this.CONFEE = CONFEE;
	}
	public Long getConfee(){
		return CONFEE;
	}

	//��ѯ�����
	private Long CONFEEREMAIN;
	public void setConfeeremain(Long CONFEEREMAIN){
		this.CONFEEREMAIN = CONFEEREMAIN;
	}
	public Long getConfeeremain(){
		return CONFEEREMAIN;
	}

	//������ϼ�
	private Long TOLMONTHVALUE;
	public void setTolmonthvalue(Long TOLMONTHVALUE){
		this.TOLMONTHVALUE = TOLMONTHVALUE;
	}
	public Long getTolmonthvalue(){
		return TOLMONTHVALUE;
	}

	//̯����־
	private String AMORIZEFLAG;
	public void setAmorizeflag(String AMORIZEFLAG){
		this.AMORIZEFLAG = AMORIZEFLAG;
	}
	public String getAmorizeflag(){
		return AMORIZEFLAG;
	}

	//ʵ�����з�Ϣ
	private Long PAYINTEPENALTY;
	public void setPayintepenalty(Long PAYINTEPENALTY){
		this.PAYINTEPENALTY = PAYINTEPENALTY;
	}
	public Long getPayintepenalty(){
		return PAYINTEPENALTY;
	}

	//Ӧ�����з�Ϣ
	private Long SPAYINTEPENALTY;
	public void setSpayintepenalty(Long SPAYINTEPENALTY){
		this.SPAYINTEPENALTY = SPAYINTEPENALTY;
	}
	public Long getSpayintepenalty(){
		return SPAYINTEPENALTY;
	}

	//ʵ�����и���
	private Long PAYCOMPOUNDINTE;
	public void setPaycompoundinte(Long PAYCOMPOUNDINTE){
		this.PAYCOMPOUNDINTE = PAYCOMPOUNDINTE;
	}
	public Long getPaycompoundinte(){
		return PAYCOMPOUNDINTE;
	}

	//Ӧ�����и���
	private Long SPAYCOMPOUNDINTE;
	public void setSpaycompoundinte(Long SPAYCOMPOUNDINTE){
		this.SPAYCOMPOUNDINTE = SPAYCOMPOUNDINTE;
	}
	public Long getSpaycompoundinte(){
		return SPAYCOMPOUNDINTE;
	}

	//Ӧ����ѯ��
	private Long CONSULEFEE;
	public void setConsulefee(Long CONSULEFEE){
		this.CONSULEFEE = CONSULEFEE;
	}
	public Long getConsulefee(){
		return CONSULEFEE;
	}

	//ʵ����ѯ��
	private Long ACTUALCONSULEFEE;
	public void setActualconsulefee(Long ACTUALCONSULEFEE){
		this.ACTUALCONSULEFEE = ACTUALCONSULEFEE;
	}
	public Long getActualconsulefee(){
		return ACTUALCONSULEFEE;
	}

	//��������Ӧ���Ŵ������
	private Long ORGPAYAMOUNT;
	public void setOrgpayamount(Long ORGPAYAMOUNT){
		this.ORGPAYAMOUNT = ORGPAYAMOUNT;
	}
	public Long getOrgpayamount(){
		return ORGPAYAMOUNT;
	}

	//��������ʵ���Ŵ������
	private Long ORGACTUALPAYAMOUNT;
	public void setOrgactualpayamount(Long ORGACTUALPAYAMOUNT){
		this.ORGACTUALPAYAMOUNT = ORGACTUALPAYAMOUNT;
	}
	public Long getOrgactualpayamount(){
		return ORGACTUALPAYAMOUNT;
	}

	//��������ѯ����ȡ��־��1 �ɹ�  2ʧ��  0��ʼ����
	private String CONSULEFEEFLAG;
	public void setConsulefeeflag(String CONSULEFEEFLAG){
		this.CONSULEFEEFLAG = CONSULEFEEFLAG;
	}
	public String getConsulefeeflag(){
		return CONSULEFEEFLAG;
	}

	//����Ӧ����ѯ��
	private Long ORGCONSULEFEE;
	public void setOrgconsulefee(Long ORGCONSULEFEE){
		this.ORGCONSULEFEE = ORGCONSULEFEE;
	}
	public Long getOrgconsulefee(){
		return ORGCONSULEFEE;
	}

	//����ʵ����ѯ��
	private Long ORGACTUALCONSULEFEE;
	public void setOrgactualconsulefee(Long ORGACTUALCONSULEFEE){
		this.ORGACTUALCONSULEFEE = ORGACTUALCONSULEFEE;
	}
	public Long getOrgactualconsulefee(){
		return ORGACTUALCONSULEFEE;
	}

	//Ӧ�����������
	private Long PAYTECHAMOUNT;
	public void setPaytechamount(Long PAYTECHAMOUNT){
		this.PAYTECHAMOUNT = PAYTECHAMOUNT;
	}
	public Long getPaytechamount(){
		return PAYTECHAMOUNT;
	}

	//ʵ�����������
	private Long ACTUALTECHAMOUNT;
	public void setActualtechamount(Long ACTUALTECHAMOUNT){
		this.ACTUALTECHAMOUNT = ACTUALTECHAMOUNT;
	}
	public Long getActualtechamount(){
		return ACTUALTECHAMOUNT;
	}

	//Ӧ����������
	private Long PAYSERVICEAMOUNT;
	public void setPayserviceamount(Long PAYSERVICEAMOUNT){
		this.PAYSERVICEAMOUNT = PAYSERVICEAMOUNT;
	}
	public Long getPayserviceamount(){
		return PAYSERVICEAMOUNT;
	}

	//ʵ����������
	private Long ACTUALSERVICEAMOUNT;
	public void setActualserviceamount(Long ACTUALSERVICEAMOUNT){
		this.ACTUALSERVICEAMOUNT = ACTUALSERVICEAMOUNT;
	}
	public Long getActualserviceamount(){
		return ACTUALSERVICEAMOUNT;
	}

	//Ӧ��ΥԼ��
	private Long POUNDAGEAMT;
	public void setPoundageamt(Long POUNDAGEAMT){
		this.POUNDAGEAMT = POUNDAGEAMT;
	}
	public Long getPoundageamt(){
		return POUNDAGEAMT;
	}

	//ʵ��ΥԼ��
	private Long ACTUALPOUNDAGEAMT;
	public void setActualpoundageamt(Long ACTUALPOUNDAGEAMT){
		this.ACTUALPOUNDAGEAMT = ACTUALPOUNDAGEAMT;
	}
	public Long getActualpoundageamt(){
		return ACTUALPOUNDAGEAMT;
	}

	//Ӧ�պ�����������ѷ�Ϣ
	private Long PAYSERVICEOUTFINE;
	public void setPayserviceoutfine(Long PAYSERVICEOUTFINE){
		this.PAYSERVICEOUTFINE = PAYSERVICEOUTFINE;
	}
	public Long getPayserviceoutfine(){
		return PAYSERVICEOUTFINE;
	}

	//ʵ��������������ѷ�Ϣ
	private Long ACTUALSERVICEOUTFINE;
	public void setActualserviceoutfine(Long ACTUALSERVICEOUTFINE){
		this.ACTUALSERVICEOUTFINE = ACTUALSERVICEOUTFINE;
	}
	public Long getActualserviceoutfine(){
		return ACTUALSERVICEOUTFINE;
	}

	//Ӧ�պ�����������Ѹ���
	private Long PAYSERVICEINNERFINE;
	public void setPayserviceinnerfine(Long PAYSERVICEINNERFINE){
		this.PAYSERVICEINNERFINE = PAYSERVICEINNERFINE;
	}
	public Long getPayserviceinnerfine(){
		return PAYSERVICEINNERFINE;
	}

	//ʵ��������������Ѹ���
	private Long ACTUALSERVICEINNERFINE;
	public void setActualserviceinnerfine(Long ACTUALSERVICEINNERFINE){
		this.ACTUALSERVICEINNERFINE = ACTUALSERVICEINNERFINE;
	}
	public Long getActualserviceinnerfine(){
		return ACTUALSERVICEINNERFINE;
	}

}

