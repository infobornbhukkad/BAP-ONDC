// 
// Decompiled by Procyon v0.5.36
// 

package com.bb.beckn.cancel.controller;

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
import com.bb.beckn.cancel.extension.OnSchema;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestBody;
import com.bb.beckn.common.validator.HeaderValidator;
import com.bb.beckn.repository.ApiAuditbuyerRepository;
import com.bb.beckn.repository.ConfirmAuditBuyerRepository;
import com.bb.beckn.search.model.ApiBuyerObj;
import com.bb.beckn.search.model.ConfirmAuditBuyerObj;
import com.bb.beckn.common.service.AuditService;
import com.bb.beckn.common.service.ApplicationConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import com.bb.beckn.common.util.JsonUtil;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OnCancelControllerBuyer
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
    ApiAuditbuyerRepository mApiAuditBuyerRepository;
    @Autowired
    ConfirmAuditBuyerRepository mConfirmAuditByerRepository;
    
    
    @PostMapping({ "/buyer/adaptor/on_cancel" })
    public ResponseEntity<String> onCancel(@RequestBody final String body, @RequestHeader final HttpHeaders httpHeaders) throws JsonProcessingException {
        OnCancelControllerBuyer.log.info("The body in {} adaptor is {}", (Object)"cancel", (Object)this.jsonUtil.unpretty(body));
        try {
            final OnSchema model = (OnSchema)this.jsonUtil.toModel(body, (Class)OnSchema.class);
            OnCancelControllerBuyer.log.info("data in buyer controller adaptor [which is will now send back to buyer internal api] is {}", (Object)model.toString());
            final Context context = model.getContext();
            final String bppId = context.getBppId();
            final ConfigModel configuration = this.configService.loadApplicationConfiguration(context.getBapId(), "cancel");
            final boolean authenticate = configuration.getMatchedApi().isHeaderAuthentication();
            OnCancelControllerBuyer.log.info("does seller {} requires to be authenticated ? {}", (Object)bppId, (Object)authenticate);
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
     		
     		System.out.println("Data Inserted in OnConfirmControllerBuyer in cancel ");   
       		
     		/// Data Inserted in ondc_audit_transaction
          	String canceltype ="";
        	String cancelamount="";
        	String camncelby="";
        	String cancelamountbearbybuyer="";
        	String cancelamountbearbyseller="";
        	String cancelamountbearbylogistic="";
        	String cancelreason="";
    		      	
        	if(request.getMessage().getOrder().getTags().getCancellationReasonId().equalsIgnoreCase("001") || request.getMessage().getOrder().getTags().getCancellationReasonId().equalsIgnoreCase("003")||
        			request.getMessage().getOrder().getTags().getCancellationReasonId().equalsIgnoreCase("004")|| request.getMessage().getOrder().getTags().getCancellationReasonId().equalsIgnoreCase("006") ||
        			request.getMessage().getOrder().getTags().getCancellationReasonId().equalsIgnoreCase("009")|| request.getMessage().getOrder().getTags().getCancellationReasonId().equalsIgnoreCase("010") ||
        			request.getMessage().getOrder().getTags().getCancellationReasonId().equalsIgnoreCase("012"))
        	{
        		canceltype="Full";
        		cancelamount=
        		camncelby="Buyer";
        		cancelamountbearbybuyer="";
        		cancelreason=request.getMessage().getOrder().getTags().getCancellationReasonId();
        		
        	}
        	
        	if(request.getMessage().getOrder().getTags().getCancellationReasonId().equalsIgnoreCase("002") || request.getMessage().getOrder().getTags().getCancellationReasonId().equalsIgnoreCase("005"))
        	{
        		camncelby="Seller";
        	}
        	if(request.getMessage().getOrder().getTags().getCancellationReasonId().equalsIgnoreCase("008") || request.getMessage().getOrder().getTags().getCancellationReasonId().equalsIgnoreCase("011")||
        			request.getMessage().getOrder().getTags().getCancellationReasonId().equalsIgnoreCase("013")|| request.getMessage().getOrder().getTags().getCancellationReasonId().equalsIgnoreCase("014") ||
        			request.getMessage().getOrder().getTags().getCancellationReasonId().equalsIgnoreCase("015")|| request.getMessage().getOrder().getTags().getCancellationReasonId().equalsIgnoreCase("016") ||
        			request.getMessage().getOrder().getTags().getCancellationReasonId().equalsIgnoreCase("017") || request.getMessage().getOrder().getTags().getCancellationReasonId().equalsIgnoreCase("018") ||
        			request.getMessage().getOrder().getTags().getCancellationReasonId().equalsIgnoreCase("019"))
        	{
        		camncelby="LogisticsProvider";
        	}
        	
        	ConfirmAuditBuyerObj myconfirmAuditBuyerObj = new ConfirmAuditBuyerObj(request.getMessage().getOrder().getId(), request.getContext().getTransactionId(), request.getMessage().getOrder().getState(), "", "", "", "",
        			"", "", "", "", "", "", "", "",
        			 canceltype, cancelamount, camncelby, cancelamountbearbybuyer,cancelamountbearbyseller,cancelamountbearbylogistic,  cancelreason,  timestamp.toString(),  "",  "", "" , "" , "");
          	
          	mConfirmAuditByerRepository.saveAndFlush(myconfirmAuditBuyerObj);
       	
       	
   		
   		System.out.println("Data Inserted in ondc_audit_buyer_transaction in cancel"); 
       	}catch(Exception e) {
   			System.out.println("Exception--"+ e);
   		}
   	}
    
    static {
        log = LoggerFactory.getLogger((Class)OnCancelControllerBuyer.class);
    }
}
