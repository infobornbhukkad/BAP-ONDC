package com.bb.beckn.search.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "ondc_audit_buyer_transaction")
public class ConfirmAuditBuyerObj {
    
	@Id
	private int id;	
	@NotBlank
	private String transactionid;
	@NotBlank
	private String orderid;
	private String sellerorderstate;
	private String paymenttype;
	private String paymentcollectedby;
	private String amountatconfirmation;
	private String logisticprovidername;
	private String logisticproviderid;
	private String logisticdeliverycharge;
	private String logisticdeliverytype;
	private String refundtype;
	private String refundamount;
	private String refundby;
	private String refundbearyby;
	private String refundreason;
	private String canceltype;
	private String cancelamount;
	private String camncelby;
	private String cancelamountbearbybuyer;
	private String cancelamountbearbyseller;
	private String cancelamountbearbylogistic;
	private String cancelreason;
	private String creationdate;
	private String updationdate;
	private String buyerappfindingfee;
	private String buyerappfindingfeetype;
	private String bppid;
	private String bppurl;
	
	
	public String getCancelamountbearbybuyer() {
		return cancelamountbearbybuyer;
	}

	public void setCancelamountbearbybuyer(String cancelamountbearbybuyer) {
		this.cancelamountbearbybuyer = cancelamountbearbybuyer;
	}

	public String getCancelamountbearbyseller() {
		return cancelamountbearbyseller;
	}

	public void setCancelamountbearbyseller(String cancelamountbearbyseller) {
		this.cancelamountbearbyseller = cancelamountbearbyseller;
	}

	public String getCancelamountbearbylogistic() {
		return cancelamountbearbylogistic;
	}

	public void setCancelamountbearbylogistic(String cancelamountbearbylogistic) {
		this.cancelamountbearbylogistic = cancelamountbearbylogistic;
	}

	public String getSellerorderstate() {
		return sellerorderstate;
	}

	public void setSellerorderstate(String sellerorderstate) {
		this.sellerorderstate = sellerorderstate;
	}

	public String getPaymentcollectedby() {
		return paymentcollectedby;
	}

	public void setPaymentcollectedby(String paymentcollectedby) {
		this.paymentcollectedby = paymentcollectedby;
	}

	public String getBppid() {
		return bppid;
	}

	public void setBppid(String bppid) {
		this.bppid = bppid;
	}

	public String getBppurl() {
		return bppurl;
	}

	public void setBppurl(String bppurl) {
		this.bppurl = bppurl;
	}

	public String getBuyerappfindingfee() {
		return buyerappfindingfee;
	}

	public void setBuyerappfindingfee(String buyerappfindingfee) {
		this.buyerappfindingfee = buyerappfindingfee;
	}

	public String getBuyerappfindingfeetype() {
		return buyerappfindingfeetype;
	}

	public void setBuyerappfindingfeetype(String buyerappfindingfeetype) {
		this.buyerappfindingfeetype = buyerappfindingfeetype;
	}

	public ConfirmAuditBuyerObj() {
		super();
	}

	public ConfirmAuditBuyerObj(String orderid, String transactionid, String sellerorderstate, String paymenttype, String paymentcollectedby, String amountatconfirmation, String logisticprovidername,
			String logisticproviderid, String logisticdeliverycharge, String logisticdeliverytype,String refundtype,String refundamount,String refundby,String refundbearyby, 
			String refundreason,
			String canceltype,String cancelamount,String camncelby,String cancelamountbearbybuyer, String cancelamountbearbyseller,String cancelamountbearbylogistic, String cancelreason, String creationdate, String updationdate, String buyerappfindingfee,String buyerappfindingfeetype ,String bppid ,String bppurl) {
		this.orderid = orderid;
		this.transactionid = transactionid;
		this.sellerorderstate = sellerorderstate;
		this.paymenttype = paymenttype;
		this.paymentcollectedby = paymentcollectedby;
		this.amountatconfirmation = amountatconfirmation;
		this.logisticprovidername = logisticprovidername;
		this.logisticproviderid = logisticproviderid;
		this.logisticdeliverycharge = logisticdeliverycharge;
		this.logisticdeliverytype = logisticdeliverytype;
		this.refundtype = refundtype;
		this.refundamount = refundamount;
		this.refundby = refundby;
		this.refundbearyby = refundbearyby;
		this.refundreason = refundreason;
		this.canceltype = canceltype;
		this.cancelamount = cancelamount;
		this.camncelby = camncelby;
		this.cancelamountbearbybuyer = cancelamountbearbybuyer;	
		this.cancelamountbearbyseller = cancelamountbearbyseller;	
		this.cancelamountbearbylogistic = cancelamountbearbylogistic;			
		this.cancelreason = cancelreason;
		this.creationdate = creationdate;
		this.updationdate = updationdate;
		this.buyerappfindingfee = buyerappfindingfee;
		this.buyerappfindingfeetype = buyerappfindingfeetype;
		this.bppid = bppid;
		this.bppurl = bppurl;
		
	}

	public String getTransactionid() {
		return transactionid;
	}

	public void setTransactionid(String transactionid) {
		this.transactionid = transactionid;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getPaymenttype() {
		return paymenttype;
	}

	public void setPaymenttype(String paymenttype) {
		this.paymenttype = paymenttype;
	}
	
	public String getAmountatconfirmation() {
		return amountatconfirmation;
	}

	public void setAmountatconfirmation(String amountatconfirmation) {
		this.amountatconfirmation = amountatconfirmation;
	}

	public String getLogisticprovidername() {
		return logisticprovidername;
	}

	public void setLogisticprovidername(String logisticprovidername) {
		this.logisticprovidername = logisticprovidername;
	}

	public String getLogisticproviderid() {
		return logisticproviderid;
	}

	public void setLogisticproviderid(String logisticproviderid) {
		this.logisticproviderid = logisticproviderid;
	}

	public String getLogisticdeliverycharge() {
		return logisticdeliverycharge;
	}

	public void setLogisticdeliverycharge(String logisticdeliverycharge) {
		this.logisticdeliverycharge = logisticdeliverycharge;
	}

	public String getLogisticdeliverytype() {
		return logisticdeliverytype;
	}

	public void setLogisticdeliverytype(String logisticdeliverytype) {
		this.logisticdeliverytype = logisticdeliverytype;
	}

	public String getRefundtype() {
		return refundtype;
	}

	public void setRefundtype(String refundtype) {
		this.refundtype = refundtype;
	}

	public String getRefundamount() {
		return refundamount;
	}

	public void setRefundamount(String refundamount) {
		this.refundamount = refundamount;
	}

	public String getRefundby() {
		return refundby;
	}

	public void setRefundby(String refundby) {
		this.refundby = refundby;
	}

	public String getRefundbearyby() {
		return refundbearyby;
	}

	public void setRefundbearyby(String refundbearyby) {
		this.refundbearyby = refundbearyby;
	}

	public String getRefundreason() {
		return refundreason;
	}

	public void setRefundreason(String refundreason) {
		this.refundreason = refundreason;
	}

	public String getCanceltype() {
		return canceltype;
	}

	public void setCanceltype(String canceltype) {
		this.canceltype = canceltype;
	}

	public String getCancelamount() {
		return cancelamount;
	}

	public void setCancelamount(String cancelamount) {
		this.cancelamount = cancelamount;
	}

	public String getCamncelby() {
		return camncelby;
	}

	public void setCamncelby(String camncelby) {
		this.camncelby = camncelby;
	}
	
	public String getCancelreason() {
		return cancelreason;
	}

	public void setCancelreason(String cancelreason) {
		this.cancelreason = cancelreason;
	}

	public String getCreationdate() {
		return creationdate;
	}

	public void setCreationdate(String creationdate) {
		this.creationdate = creationdate;
	}

	public String getUpdationdate() {
		return updationdate;
	}

	public void setUpdationdate(String updationdate) {
		this.updationdate = updationdate;
	}

	
	
    
	

}
