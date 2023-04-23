// 
// Decompiled by Procyon v0.5.36
// 

package com.bb.beckn.confirm.controller;

import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import com.bb.beckn.common.model.AuditDataModel;
import com.bb.beckn.common.model.AuditFlagModel;
import com.bb.beckn.common.model.HttpModel;
import com.bb.beckn.common.model.AuditModel;
import org.springframework.web.bind.annotation.PostMapping;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.bb.beckn.common.model.ConfigModel;
import com.bb.beckn.ConnectionUtil;
import com.bb.beckn.api.model.common.Context;
import org.springframework.http.HttpStatus;
import com.bb.beckn.api.model.lookup.LookupRequest;
import com.bb.beckn.common.enums.BecknUserType;
import com.bb.beckn.confirm.extension.OnSchema;
import com.bb.beckn.search.model.ApiBuyerObj;
import com.bb.beckn.search.model.ConfirmAuditBuyerObj;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestBody;
import com.bb.beckn.common.validator.HeaderValidator;
import com.bb.beckn.common.service.AuditService;
import com.bb.beckn.common.service.ApplicationConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import com.bb.beckn.common.util.JsonUtil;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.RestController;

import com.bb.beckn.repository.ApiAuditbuyerRepository;
import com.bb.beckn.repository.ConfirmAuditBuyerRepository;


@RestController
public class OnConfirmControllerBuyer
{
    private static final Logger log;
    @Autowired
    private JsonUtil jsonUtil;
    @Autowired
    private ApplicationConfigService configService;
    @Autowired
    private AuditService auditService;
    @Autowired
    private HeaderValidator validator;
    @Autowired
    ConfirmAuditBuyerRepository mConfirmAuditByerRepository;
    @Autowired
    ApiAuditbuyerRepository mApiAuditBuyerRepository;
    
    @PostMapping({ "/buyer/adaptor/on_confirm" })
    public ResponseEntity<String> onConfirm(@RequestBody final String body, @RequestHeader final HttpHeaders httpHeaders) throws JsonProcessingException {
        OnConfirmControllerBuyer.log.info("The body in {} adaptor is {}", (Object)"confirm", (Object)this.jsonUtil.unpretty(body));
        try {
            final OnSchema model = (OnSchema)this.jsonUtil.toModel(body, (Class)OnSchema.class);
            OnConfirmControllerBuyer.log.info("data in buyer controller adaptor [which is will now send back to buyer internal api] is {}", (Object)model.toString());
            final Context context = model.getContext();
            final String bppId = context.getBppId();
            final ConfigModel configuration = this.configService.loadApplicationConfiguration(context.getBapId(), "confirm");
            final boolean authenticate = configuration.getMatchedApi().isHeaderAuthentication();
            OnConfirmControllerBuyer.log.info("does seller {} requires to be authenticated ? {}", (Object)bppId, (Object)authenticate);
            if (authenticate) {
                final LookupRequest lookupRequest = new LookupRequest((String)null, context.getCountry(), context.getCity(), context.getDomain(), BecknUserType.BPP.type());
                this.validator.validateHeader(context.getBapId(), httpHeaders, body, lookupRequest);
            }
            
            this.getMySQLDataSource(body,  model);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return (ResponseEntity<String>)new ResponseEntity((Object)"Response received. All Ok", HttpStatus.OK);
    }
    
	
    
    public void getMySQLDataSource(final String body, final OnSchema request) {
  	   Timestamp timestamp = new Timestamp(System.currentTimeMillis());
      	try {
    		ApiBuyerObj myApibuyerObj = new ApiBuyerObj(request.getContext().getMessageId(), request.getContext().getTransactionId(),
    				request.getContext().getAction(), request.getContext().getDomain(), request.getContext().getCoreVersion(),
    				timestamp.toString(), body, "N",
    				"Not Applicable");
    		mApiAuditBuyerRepository.save(myApibuyerObj);
    		
    		System.out.println("Data Inserted in OnConfirmControllerBuyer  in confirm ");   
      		
      	/// Data Inserted in ondc_audit_transaction
      	String paymenttype ="";
      	String paymentcollectedby="";
      	String amountatconfirmation="";
      	String logisticprovidername="";
      	String logisticdeliverycharge="";
      	if(request.getMessage().getOrder().getPayment().getStatus().equalsIgnoreCase("Paid")) {
      		paymenttype="Prepaid";
      		paymentcollectedby="Buyer";
      	}else if(request.getMessage().getOrder().getPayment().getStatus().equalsIgnoreCase("NOT-PAID")){
      		paymenttype="CoD";
      		paymentcollectedby="seller";
      	}
      	amountatconfirmation=request.getMessage().getOrder().getQuote().getPrice().getValue();
      	logisticprovidername= request.getMessage().getOrder().getFulfillments().get(0).getProviderName();
      	
      	for(int i=0; i < request.getMessage().getOrder().getQuote().getBreakup().size(); i++) {
      		if(request.getMessage().getOrder().getQuote().getBreakup().get(i).getTitle().equalsIgnoreCase("Delivery charges")) {
      			logisticdeliverycharge=request.getMessage().getOrder().getQuote().getBreakup().get(i).getPrice().getValue();
      		}
      	}
      	List<ConfirmAuditBuyerObj> mConfirmAuditBuyerObj =mConfirmAuditByerRepository.findByorderidandtransactionid(request.getMessage().getOrder().getId(), request.getContext().getTransactionId());
      	ConfirmAuditBuyerObj myconfirmAuditSellerObj = new ConfirmAuditBuyerObj(request.getMessage().getOrder().getId(), request.getContext().getTransactionId(), request.getMessage().getOrder().getState(),
      			paymenttype,  paymentcollectedby, amountatconfirmation, logisticprovidername,"",logisticdeliverycharge,"","","","","","","","","","","","","",
      			timestamp.toString(), "",request.getMessage().getOrder().getPayment().getBuyerAppFinderFeeAmount(), request.getMessage().getOrder().getPayment().getBuyerAppFinderFeeType(),request.getContext().getBppId(), request.getContext().getBppUri());
      	
      	if(mConfirmAuditBuyerObj.size()==0) {
      		mConfirmAuditByerRepository.saveAndFlush(myconfirmAuditSellerObj);
      	}else {
      		
      		System.out.println("else ----------");
			//ResultSet rsUpdate;
			int resultset=0;
	        Connection connUpdate= ConnectionUtil.getConnection();
	        Statement stmUpdate= connUpdate.createStatement();
	        String querySTRUpdate= "update bornbhukkad.ondc_audit_buyer_transaction baf set baf.orderstate ='" +request.getMessage().getOrder().getState()+
	        		         "', baf.paymenttype ='"+paymenttype+"' , baf.paymentdoneby='"+paymentcollectedby+ "' , baf.amountatconfirmation='"+amountatconfirmation+
	        		         "' , baf.logisticprovidername='"+logisticprovidername + "', baf.logisticdeliverycharge ='"+logisticdeliverycharge+ "', baf.updationdate ='"+timestamp.toString()+
	        		        "' where baf.orderid ='"+ request.getMessage().getOrder().getId()  +"' "
	        		        		+ " and baf.transaction_id ='" +request.getContext().getTransactionId()+ "';";
	        resultset = stmUpdate.executeUpdate(querySTRUpdate);
	        System.out.println("data updated--LogisticBppObj  in confirm");
	        stmUpdate.close(); 
	        
      	}
      	
      	
  		
  		System.out.println("Data Inserted in ondc_audit_buyer_transaction in confirm"); 
      	}catch(Exception e) {
  			System.out.println("Exception--"+ e);
  		}
  	}
    
    static {
        log = LoggerFactory.getLogger((Class)OnConfirmControllerBuyer.class);
    }
}
