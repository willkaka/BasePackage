package com.base.bean;

   /**
    * Loanback_status(还款计划表)实体类
    * Mon May 28 11:31:08 CST 2018
    * @author huangyuanwei
    */ 

public class Loanback_status{
	private static final long serialVersionUID = 1L;

	//数据表名称
	public static final String DATABASE_TABLE_NAME = "loanback_status";


	//借据编号
	private String PUTOUTNO;
	public void setPutoutno(String PUTOUTNO){
		this.PUTOUTNO = PUTOUTNO;
	}
	public String getPutoutno(){
		return PUTOUTNO;
	}

	//期次
	private Long STERM;
	public void setSterm(Long STERM){
		this.STERM = STERM;
	}
	public Long getSterm(){
		return STERM;
	}

	//提前还款次数
	private Long AHEADNUM;
	public void setAheadnum(Long AHEADNUM){
		this.AHEADNUM = AHEADNUM;
	}
	public Long getAheadnum(){
		return AHEADNUM;
	}

	//应还日期
	private String PAYDATE;
	public void setPaydate(String PAYDATE){
		this.PAYDATE = PAYDATE;
	}
	public String getPaydate(){
		return PAYDATE;
	}

	//币种
	private String CURRENCY;
	public void setCurrency(String CURRENCY){
		this.CURRENCY = CURRENCY;
	}
	public String getCurrency(){
		return CURRENCY;
	}

	//宽限期
	private Long GRACEPERIOD;
	public void setGraceperiod(Long GRACEPERIOD){
		this.GRACEPERIOD = GRACEPERIOD;
	}
	public Long getGraceperiod(){
		return GRACEPERIOD;
	}

	//应还本金
	private Long PAYCURRENTCORP;
	public void setPaycurrentcorp(Long PAYCURRENTCORP){
		this.PAYCURRENTCORP = PAYCURRENTCORP;
	}
	public Long getPaycurrentcorp(){
		return PAYCURRENTCORP;
	}

	//实还本金
	private Long ACTUALCURRENTCORP;
	public void setActualcurrentcorp(Long ACTUALCURRENTCORP){
		this.ACTUALCURRENTCORP = ACTUALCURRENTCORP;
	}
	public Long getActualcurrentcorp(){
		return ACTUALCURRENTCORP;
	}

	//应还违约本金
	private Long PAYDEFAULTCORP;
	public void setPaydefaultcorp(Long PAYDEFAULTCORP){
		this.PAYDEFAULTCORP = PAYDEFAULTCORP;
	}
	public Long getPaydefaultcorp(){
		return PAYDEFAULTCORP;
	}

	//实还违约本金
	private Long ACTUALDEFAULTCORP;
	public void setActualdefaultcorp(Long ACTUALDEFAULTCORP){
		this.ACTUALDEFAULTCORP = ACTUALDEFAULTCORP;
	}
	public Long getActualdefaultcorp(){
		return ACTUALDEFAULTCORP;
	}

	//应还逾期本金
	private Long PAYOVERDUECORP;
	public void setPayoverduecorp(Long PAYOVERDUECORP){
		this.PAYOVERDUECORP = PAYOVERDUECORP;
	}
	public Long getPayoverduecorp(){
		return PAYOVERDUECORP;
	}

	//实还逾期本金
	private Long ACTUALOVERDUECORP;
	public void setActualoverduecorp(Long ACTUALOVERDUECORP){
		this.ACTUALOVERDUECORP = ACTUALOVERDUECORP;
	}
	public Long getActualoverduecorp(){
		return ACTUALOVERDUECORP;
	}

	//应还利息
	private Long PAYINTE;
	public void setPayinte(Long PAYINTE){
		this.PAYINTE = PAYINTE;
	}
	public Long getPayinte(){
		return PAYINTE;
	}

	//实还利息
	private Long ACTUALINTE;
	public void setActualinte(Long ACTUALINTE){
		this.ACTUALINTE = ACTUALINTE;
	}
	public Long getActualinte(){
		return ACTUALINTE;
	}

	//应还应计利息
	private Long PAYINNERINTE;
	public void setPayinnerinte(Long PAYINNERINTE){
		this.PAYINNERINTE = PAYINNERINTE;
	}
	public Long getPayinnerinte(){
		return PAYINNERINTE;
	}

	//实还应计利息
	private Long ACTUALINNERINTE;
	public void setActualinnerinte(Long ACTUALINNERINTE){
		this.ACTUALINNERINTE = ACTUALINNERINTE;
	}
	public Long getActualinnerinte(){
		return ACTUALINNERINTE;
	}

	//应还非应计利息
	private Long PAYOUTINTE;
	public void setPayoutinte(Long PAYOUTINTE){
		this.PAYOUTINTE = PAYOUTINTE;
	}
	public Long getPayoutinte(){
		return PAYOUTINTE;
	}

	//实还非应计利息
	private Long ACTUALOUTINTE;
	public void setActualoutinte(Long ACTUALOUTINTE){
		this.ACTUALOUTINTE = ACTUALOUTINTE;
	}
	public Long getActualoutinte(){
		return ACTUALOUTINTE;
	}

	//实还复利
	private Long ACTUALINNERINTEFINE;
	public void setActualinnerintefine(Long ACTUALINNERINTEFINE){
		this.ACTUALINNERINTEFINE = ACTUALINNERINTEFINE;
	}
	public Long getActualinnerintefine(){
		return ACTUALINNERINTEFINE;
	}

	//实还罚息
	private Long ACTUALOUTINTEFINE;
	public void setActualoutintefine(Long ACTUALOUTINTEFINE){
		this.ACTUALOUTINTEFINE = ACTUALOUTINTEFINE;
	}
	public Long getActualoutintefine(){
		return ACTUALOUTINTEFINE;
	}

	//应还复利
	private Long PAYINNERINTEFINE;
	public void setPayinnerintefine(Long PAYINNERINTEFINE){
		this.PAYINNERINTEFINE = PAYINNERINTEFINE;
	}
	public Long getPayinnerintefine(){
		return PAYINNERINTEFINE;
	}

	//应还罚息
	private Long PAYOUTINTEFINE;
	public void setPayoutintefine(Long PAYOUTINTEFINE){
		this.PAYOUTINTEFINE = PAYOUTINTEFINE;
	}
	public Long getPayoutintefine(){
		return PAYOUTINTEFINE;
	}

	//记帐日期
	private String ACCDATE;
	public void setAccdate(String ACCDATE){
		this.ACCDATE = ACCDATE;
	}
	public String getAccdate(){
		return ACCDATE;
	}

	//结清标志
	private String PAYOFFFLAG;
	public void setPayoffflag(String PAYOFFFLAG){
		this.PAYOFFFLAG = PAYOFFFLAG;
	}
	public String getPayoffflag(){
		return PAYOFFFLAG;
	}

	//复利基数
	private Long INTEFINEBASE;
	public void setIntefinebase(Long INTEFINEBASE){
		this.INTEFINEBASE = INTEFINEBASE;
	}
	public Long getIntefinebase(){
		return INTEFINEBASE;
	}

	//操作日期
	private String UPDATEDATE;
	public void setUpdatedate(String UPDATEDATE){
		this.UPDATEDATE = UPDATEDATE;
	}
	public String getUpdatedate(){
		return UPDATEDATE;
	}

	//逾期本金利息
	private Long PAYOVERDUECORPINTE;
	public void setPayoverduecorpinte(Long PAYOVERDUECORPINTE){
		this.PAYOVERDUECORPINTE = PAYOVERDUECORPINTE;
	}
	public Long getPayoverduecorpinte(){
		return PAYOVERDUECORPINTE;
	}

	//罚息基数
	private Long OVERDUECORPINTEBASE;
	public void setOverduecorpintebase(Long OVERDUECORPINTEBASE){
		this.OVERDUECORPINTEBASE = OVERDUECORPINTEBASE;
	}
	public Long getOverduecorpintebase(){
		return OVERDUECORPINTEBASE;
	}

	//机构
	private String ORGID;
	public void setOrgid(String ORGID){
		this.ORGID = ORGID;
	}
	public String getOrgid(){
		return ORGID;
	}

	//罚息日期
	private String FINEBASEDATE;
	public void setFinebasedate(String FINEBASEDATE){
		this.FINEBASEDATE = FINEBASEDATE;
	}
	public String getFinebasedate(){
		return FINEBASEDATE;
	}

	//居间服务费
	private Long WAITOVERDUECORPINTE;
	public void setWaitoverduecorpinte(Long WAITOVERDUECORPINTE){
		this.WAITOVERDUECORPINTE = WAITOVERDUECORPINTE;
	}
	public Long getWaitoverduecorpinte(){
		return WAITOVERDUECORPINTE;
	}

	//当期剩余本金
	private Long RESIDUALAMOUNT;
	public void setResidualamount(Long RESIDUALAMOUNT){
		this.RESIDUALAMOUNT = RESIDUALAMOUNT;
	}
	public Long getResidualamount(){
		return RESIDUALAMOUNT;
	}

	//应还期收费用1
	private Long PAYAMOUNT1;
	public void setPayamount1(Long PAYAMOUNT1){
		this.PAYAMOUNT1 = PAYAMOUNT1;
	}
	public Long getPayamount1(){
		return PAYAMOUNT1;
	}

	//实还期收费用1
	private Long ACTUALPAYAMOUNT1;
	public void setActualpayamount1(Long ACTUALPAYAMOUNT1){
		this.ACTUALPAYAMOUNT1 = ACTUALPAYAMOUNT1;
	}
	public Long getActualpayamount1(){
		return ACTUALPAYAMOUNT1;
	}

	//应还资金占用费
	private Long PAYMONEY;
	public void setPaymoney(Long PAYMONEY){
		this.PAYMONEY = PAYMONEY;
	}
	public Long getPaymoney(){
		return PAYMONEY;
	}

	//实还资金占用费
	private Long ACTUALPAYMONEY;
	public void setActualpaymoney(Long ACTUALPAYMONEY){
		this.ACTUALPAYMONEY = ACTUALPAYMONEY;
	}
	public Long getActualpaymoney(){
		return ACTUALPAYMONEY;
	}

	//安信状态标识(1:已经执行扣款)
	private String AXFLAG;
	public void setAxflag(String AXFLAG){
		this.AXFLAG = AXFLAG;
	}
	public String getAxflag(){
		return AXFLAG;
	}

	//代偿时间
	private String DCPAYDATE;
	public void setDcpaydate(String DCPAYDATE){
		this.DCPAYDATE = DCPAYDATE;
	}
	public String getDcpaydate(){
		return DCPAYDATE;
	}

	//代偿标识
	private String DCSTATUS;
	public void setDcstatus(String DCSTATUS){
		this.DCSTATUS = DCSTATUS;
	}
	public String getDcstatus(){
		return DCSTATUS;
	}

	//代偿应还本金
	private Long DCPAYCORP;
	public void setDcpaycorp(Long DCPAYCORP){
		this.DCPAYCORP = DCPAYCORP;
	}
	public Long getDcpaycorp(){
		return DCPAYCORP;
	}

	//代偿实还本金
	private Long DCACTUALCORP;
	public void setDcactualcorp(Long DCACTUALCORP){
		this.DCACTUALCORP = DCACTUALCORP;
	}
	public Long getDcactualcorp(){
		return DCACTUALCORP;
	}

	//代偿应还利息
	private Long DCPAYINTE;
	public void setDcpayinte(Long DCPAYINTE){
		this.DCPAYINTE = DCPAYINTE;
	}
	public Long getDcpayinte(){
		return DCPAYINTE;
	}

	//代偿实还利息
	private Long DCACTUALINTE;
	public void setDcactualinte(Long DCACTUALINTE){
		this.DCACTUALINTE = DCACTUALINTE;
	}
	public Long getDcactualinte(){
		return DCACTUALINTE;
	}

	//代偿应还罚息
	private Long DCPAYOUTFINE;
	public void setDcpayoutfine(Long DCPAYOUTFINE){
		this.DCPAYOUTFINE = DCPAYOUTFINE;
	}
	public Long getDcpayoutfine(){
		return DCPAYOUTFINE;
	}

	//代偿实还罚息
	private Long DCACTUALOUTFINE;
	public void setDcactualoutfine(Long DCACTUALOUTFINE){
		this.DCACTUALOUTFINE = DCACTUALOUTFINE;
	}
	public Long getDcactualoutfine(){
		return DCACTUALOUTFINE;
	}

	//代偿应还复利
	private Long DCPAYINNERFINE;
	public void setDcpayinnerfine(Long DCPAYINNERFINE){
		this.DCPAYINNERFINE = DCPAYINNERFINE;
	}
	public Long getDcpayinnerfine(){
		return DCPAYINNERFINE;
	}

	//代偿实还复利
	private Long DCACTUALINNERFINE;
	public void setDcactualinnerfine(Long DCACTUALINNERFINE){
		this.DCACTUALINNERFINE = DCACTUALINNERFINE;
	}
	public Long getDcactualinnerfine(){
		return DCACTUALINNERFINE;
	}

	//现金流
	private Long CASHFLOW;
	public void setCashflow(Long CASHFLOW){
		this.CASHFLOW = CASHFLOW;
	}
	public Long getCashflow(){
		return CASHFLOW;
	}

	//放款天数
	private Long PUTOUTDAY;
	public void setPutoutday(Long PUTOUTDAY){
		this.PUTOUTDAY = PUTOUTDAY;
	}
	public Long getPutoutday(){
		return PUTOUTDAY;
	}

	//累计放款天数
	private Long TOLPUTOUTDAY;
	public void setTolputoutday(Long TOLPUTOUTDAY){
		this.TOLPUTOUTDAY = TOLPUTOUTDAY;
	}
	public Long getTolputoutday(){
		return TOLPUTOUTDAY;
	}

	//剩余本金
	private Long REMAINCORP;
	public void setRemaincorp(Long REMAINCORP){
		this.REMAINCORP = REMAINCORP;
	}
	public Long getRemaincorp(){
		return REMAINCORP;
	}

	//管理费现值
	private Long FEEPRESENTVALUE;
	public void setFeepresentvalue(Long FEEPRESENTVALUE){
		this.FEEPRESENTVALUE = FEEPRESENTVALUE;
	}
	public Long getFeepresentvalue(){
		return FEEPRESENTVALUE;
	}

	//管理费-利息调整
	private Long FEEINTEADJUST;
	public void setFeeinteadjust(Long FEEINTEADJUST){
		this.FEEINTEADJUST = FEEINTEADJUST;
	}
	public Long getFeeinteadjust(){
		return FEEINTEADJUST;
	}

	//管理费-本金
	private Long FEECORP;
	public void setFeecorp(Long FEECORP){
		this.FEECORP = FEECORP;
	}
	public Long getFeecorp(){
		return FEECORP;
	}

	//实还管理费-本金
	private Long ACTUALFEECORP;
	public void setActualfeecorp(Long ACTUALFEECORP){
		this.ACTUALFEECORP = ACTUALFEECORP;
	}
	public Long getActualfeecorp(){
		return ACTUALFEECORP;
	}

	//管理费本金余额
	private Long FEEREMAINCORP;
	public void setFeeremaincorp(Long FEEREMAINCORP){
		this.FEEREMAINCORP = FEEREMAINCORP;
	}
	public Long getFeeremaincorp(){
		return FEEREMAINCORP;
	}

	//递延收益-管理费
	private Long FEEDEFERREDVALUE;
	public void setFeedeferredvalue(Long FEEDEFERREDVALUE){
		this.FEEDEFERREDVALUE = FEEDEFERREDVALUE;
	}
	public Long getFeedeferredvalue(){
		return FEEDEFERREDVALUE;
	}

	//管理费-利息收入
	private Long FEEINTE;
	public void setFeeinte(Long FEEINTE){
		this.FEEINTE = FEEINTE;
	}
	public Long getFeeinte(){
		return FEEINTE;
	}

	//实还管理费-利息收入
	private Long ACTUALPAYFEEINTE;
	public void setActualpayfeeinte(Long ACTUALPAYFEEINTE){
		this.ACTUALPAYFEEINTE = ACTUALPAYFEEINTE;
	}
	public Long getActualpayfeeinte(){
		return ACTUALPAYFEEINTE;
	}

	//递延管理费余额
	private Long FEEREMAINDEFERREDVALUE;
	public void setFeeremaindeferredvalue(Long FEEREMAINDEFERREDVALUE){
		this.FEEREMAINDEFERREDVALUE = FEEREMAINDEFERREDVALUE;
	}
	public Long getFeeremaindeferredvalue(){
		return FEEREMAINDEFERREDVALUE;
	}

	//月管理费及利息收入合计
	private Long TOLFEEINTE;
	public void setTolfeeinte(Long TOLFEEINTE){
		this.TOLFEEINTE = TOLFEEINTE;
	}
	public Long getTolfeeinte(){
		return TOLFEEINTE;
	}

	//咨询费收入
	private Long CONFEE;
	public void setConfee(Long CONFEE){
		this.CONFEE = CONFEE;
	}
	public Long getConfee(){
		return CONFEE;
	}

	//咨询费余额
	private Long CONFEEREMAIN;
	public void setConfeeremain(Long CONFEEREMAIN){
		this.CONFEEREMAIN = CONFEEREMAIN;
	}
	public Long getConfeeremain(){
		return CONFEEREMAIN;
	}

	//月收入合计
	private Long TOLMONTHVALUE;
	public void setTolmonthvalue(Long TOLMONTHVALUE){
		this.TOLMONTHVALUE = TOLMONTHVALUE;
	}
	public Long getTolmonthvalue(){
		return TOLMONTHVALUE;
	}

	//摊销标志
	private String AMORIZEFLAG;
	public void setAmorizeflag(String AMORIZEFLAG){
		this.AMORIZEFLAG = AMORIZEFLAG;
	}
	public String getAmorizeflag(){
		return AMORIZEFLAG;
	}

	//实还银行罚息
	private Long PAYINTEPENALTY;
	public void setPayintepenalty(Long PAYINTEPENALTY){
		this.PAYINTEPENALTY = PAYINTEPENALTY;
	}
	public Long getPayintepenalty(){
		return PAYINTEPENALTY;
	}

	//应还银行罚息
	private Long SPAYINTEPENALTY;
	public void setSpayintepenalty(Long SPAYINTEPENALTY){
		this.SPAYINTEPENALTY = SPAYINTEPENALTY;
	}
	public Long getSpayintepenalty(){
		return SPAYINTEPENALTY;
	}

	//实还银行复利
	private Long PAYCOMPOUNDINTE;
	public void setPaycompoundinte(Long PAYCOMPOUNDINTE){
		this.PAYCOMPOUNDINTE = PAYCOMPOUNDINTE;
	}
	public Long getPaycompoundinte(){
		return PAYCOMPOUNDINTE;
	}

	//应还银行复利
	private Long SPAYCOMPOUNDINTE;
	public void setSpaycompoundinte(Long SPAYCOMPOUNDINTE){
		this.SPAYCOMPOUNDINTE = SPAYCOMPOUNDINTE;
	}
	public Long getSpaycompoundinte(){
		return SPAYCOMPOUNDINTE;
	}

	//应还咨询费
	private Long CONSULEFEE;
	public void setConsulefee(Long CONSULEFEE){
		this.CONSULEFEE = CONSULEFEE;
	}
	public Long getConsulefee(){
		return CONSULEFEE;
	}

	//实还咨询费
	private Long ACTUALCONSULEFEE;
	public void setActualconsulefee(Long ACTUALCONSULEFEE){
		this.ACTUALCONSULEFEE = ACTUALCONSULEFEE;
	}
	public Long getActualconsulefee(){
		return ACTUALCONSULEFEE;
	}

	//合作机构应还信贷服务费
	private Long ORGPAYAMOUNT;
	public void setOrgpayamount(Long ORGPAYAMOUNT){
		this.ORGPAYAMOUNT = ORGPAYAMOUNT;
	}
	public Long getOrgpayamount(){
		return ORGPAYAMOUNT;
	}

	//合作机构实还信贷服务费
	private Long ORGACTUALPAYAMOUNT;
	public void setOrgactualpayamount(Long ORGACTUALPAYAMOUNT){
		this.ORGACTUALPAYAMOUNT = ORGACTUALPAYAMOUNT;
	}
	public Long getOrgactualpayamount(){
		return ORGACTUALPAYAMOUNT;
	}

	//合作方咨询费收取标志（1 成功  2失败  0初始化）
	private String CONSULEFEEFLAG;
	public void setConsulefeeflag(String CONSULEFEEFLAG){
		this.CONSULEFEEFLAG = CONSULEFEEFLAG;
	}
	public String getConsulefeeflag(){
		return CONSULEFEEFLAG;
	}

	//机构应收咨询费
	private Long ORGCONSULEFEE;
	public void setOrgconsulefee(Long ORGCONSULEFEE){
		this.ORGCONSULEFEE = ORGCONSULEFEE;
	}
	public Long getOrgconsulefee(){
		return ORGCONSULEFEE;
	}

	//机构实还咨询费
	private Long ORGACTUALCONSULEFEE;
	public void setOrgactualconsulefee(Long ORGACTUALCONSULEFEE){
		this.ORGACTUALCONSULEFEE = ORGACTUALCONSULEFEE;
	}
	public Long getOrgactualconsulefee(){
		return ORGACTUALCONSULEFEE;
	}

	//应还技术服务费
	private Long PAYTECHAMOUNT;
	public void setPaytechamount(Long PAYTECHAMOUNT){
		this.PAYTECHAMOUNT = PAYTECHAMOUNT;
	}
	public Long getPaytechamount(){
		return PAYTECHAMOUNT;
	}

	//实还技术服务费
	private Long ACTUALTECHAMOUNT;
	public void setActualtechamount(Long ACTUALTECHAMOUNT){
		this.ACTUALTECHAMOUNT = ACTUALTECHAMOUNT;
	}
	public Long getActualtechamount(){
		return ACTUALTECHAMOUNT;
	}

	//应还贷款服务费
	private Long PAYSERVICEAMOUNT;
	public void setPayserviceamount(Long PAYSERVICEAMOUNT){
		this.PAYSERVICEAMOUNT = PAYSERVICEAMOUNT;
	}
	public Long getPayserviceamount(){
		return PAYSERVICEAMOUNT;
	}

	//实还贷款服务费
	private Long ACTUALSERVICEAMOUNT;
	public void setActualserviceamount(Long ACTUALSERVICEAMOUNT){
		this.ACTUALSERVICEAMOUNT = ACTUALSERVICEAMOUNT;
	}
	public Long getActualserviceamount(){
		return ACTUALSERVICEAMOUNT;
	}

	//应还违约金
	private Long POUNDAGEAMT;
	public void setPoundageamt(Long POUNDAGEAMT){
		this.POUNDAGEAMT = POUNDAGEAMT;
	}
	public Long getPoundageamt(){
		return POUNDAGEAMT;
	}

	//实还违约金
	private Long ACTUALPOUNDAGEAMT;
	public void setActualpoundageamt(Long ACTUALPOUNDAGEAMT){
		this.ACTUALPOUNDAGEAMT = ACTUALPOUNDAGEAMT;
	}
	public Long getActualpoundageamt(){
		return ACTUALPOUNDAGEAMT;
	}

	//应收合作机构服务费罚息
	private Long PAYSERVICEOUTFINE;
	public void setPayserviceoutfine(Long PAYSERVICEOUTFINE){
		this.PAYSERVICEOUTFINE = PAYSERVICEOUTFINE;
	}
	public Long getPayserviceoutfine(){
		return PAYSERVICEOUTFINE;
	}

	//实还合作机构服务费罚息
	private Long ACTUALSERVICEOUTFINE;
	public void setActualserviceoutfine(Long ACTUALSERVICEOUTFINE){
		this.ACTUALSERVICEOUTFINE = ACTUALSERVICEOUTFINE;
	}
	public Long getActualserviceoutfine(){
		return ACTUALSERVICEOUTFINE;
	}

	//应收合作机构服务费复利
	private Long PAYSERVICEINNERFINE;
	public void setPayserviceinnerfine(Long PAYSERVICEINNERFINE){
		this.PAYSERVICEINNERFINE = PAYSERVICEINNERFINE;
	}
	public Long getPayserviceinnerfine(){
		return PAYSERVICEINNERFINE;
	}

	//实还合作机构服务费复利
	private Long ACTUALSERVICEINNERFINE;
	public void setActualserviceinnerfine(Long ACTUALSERVICEINNERFINE){
		this.ACTUALSERVICEINNERFINE = ACTUALSERVICEINNERFINE;
	}
	public Long getActualserviceinnerfine(){
		return ACTUALSERVICEINNERFINE;
	}

}

