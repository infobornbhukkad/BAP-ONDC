// 
// Decompiled by Procyon v0.5.36
// 

package com.bb.beckn.search.controller;

import org.slf4j.LoggerFactory;
import java.time.LocalDateTime;
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
import com.bb.beckn.search.extension.OnSchema;
import com.bb.beckn.search.model.ApiBuyerObj;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestBody;
import com.bb.beckn.common.validator.HeaderValidator;

import com.bb.beckn.repository.ApiAuditbuyerRepository;
import com.bb.beckn.common.service.AuditService;
import com.bb.beckn.common.service.ApplicationConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import com.bb.beckn.common.util.JsonUtil;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.RestController;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
@RestController
public class OnSearchControllerBuyer
{
    private static final Logger log;
    @Autowired
    ApiAuditbuyerRepository mApiAuditBuyerRepository;
    @Autowired
    private JsonUtil jsonUtil;
    @Autowired
    private ApplicationConfigService configService;
    @Autowired
    private AuditService auditService;
    @Autowired
    private HeaderValidator validator;
    
    @PostMapping( "/buyer/adaptor/on_search" )
    public ResponseEntity<String> onSearch(@RequestBody final String body, @RequestHeader final HttpHeaders httpHeaders) throws JsonProcessingException {
        OnSearchControllerBuyer.log.info("The body in {} adaptor is {}", (Object)"search", (Object)this.jsonUtil.unpretty(body));
        String sException="false";
        if(body.contains("Food and Grocery Store")) {
        	System.out.println("OnSearchControllerBuyer --body--  ");        	
        }
        try {
            final OnSchema model = (OnSchema)this.jsonUtil.toModel(body, (Class)OnSchema.class);
            OnSearchControllerBuyer.log.info("data in buyer controller adaptor [which is will now send back to buyer internal api] is {}", (Object)model.toString());
            final Context context = model.getContext();
            final String bppId = context.getBppId();
            final ConfigModel configuration = this.configService.loadApplicationConfiguration(context.getBapId(), "search");
            final boolean authenticate = configuration.getMatchedApi().isHeaderAuthentication();
            OnSearchControllerBuyer.log.info("does seller {} requires to be authenticated ? {}", (Object)bppId, (Object)authenticate);
            if (authenticate) {
                final LookupRequest lookupRequest = new LookupRequest((String)null, context.getCountry(), context.getCity(), context.getDomain(), BecknUserType.BPP.type());
                this.validator.validateHeader(context.getBppId(), httpHeaders, body, lookupRequest);
            }
           
        }
        catch (Exception e) {
        	sException="true";
        	OnSearchControllerBuyer.log.info("inside exception .....................................................");
            e.printStackTrace();
        }
        if(sException.equals("false")) {
        	 
         this.getMySQLDataSource(body);
        }
        return (ResponseEntity<String>)new ResponseEntity((Object)"Response received. All Ok", HttpStatus.OK);
    }
    
	/*
	 * private AuditModel buildAuditModel(final HttpHeaders httpHeaders, final
	 * String body, final OnSchema model) { final AuditModel auditModel = new
	 * AuditModel(); final HttpModel httpModel = new HttpModel();
	 * httpModel.setRequestHeaders(httpHeaders); httpModel.setRequestBody(body);
	 * final AuditFlagModel flagModel = new AuditFlagModel();
	 * flagModel.setHttp(true); flagModel.setFile(true);
	 * flagModel.setDatabase(true); auditModel.setApiName("search");
	 * auditModel.setSubscriberId(model.getContext().getBapId());
	 * auditModel.setAuditFlags(flagModel);
	 * //auditModel.setDataModel(this.buildAuditDataModel(body, model));
	 * //this.getMySQLDataSource(body, model); auditModel.setHttpModel(httpModel);
	 * return auditModel; }
	 */
    
   
    
  public void getMySQLDataSource(final String body ) {
    	
    	try {
    		final OnSchema request = (OnSchema)this.jsonUtil.toModel(body, (Class)OnSchema.class);
    		//System.out.println("Before Data Inserted in OnSearchControllerBuyer  ");
    		//System.out.println("body  " + request);
    		Timestamp timestamp = new Timestamp(System.currentTimeMillis());  
    		//System.out.println("request.getContext().getMessageId()  " + request.getContext().getMessageId());
    		//System.out.println("request.getContext().getTransactionId()  " + request.getContext().getTransactionId());
    		//System.out.println("request.getContext().getAction()  " + request.getContext().getAction());
    		//System.out.println("request.getContext().getDomain()  " + request.getContext().getDomain());
    		//System.out.println("request.getContext().getCoreVersion()  " + request.getContext().getCoreVersion());
    		//System.out.println("timestamp.toString()  " + timestamp.toString());
    		//System.out.println("request.getMessage().getCatalog().getBppProviders().get(0).getItems().get(0).getDescriptor().getName()  " + request.getMessage().getCatalog().getBppProviders().get(0).getItems().get(0).getDescriptor().getName());
    		
    		
    		
    		ApiBuyerObj myApibuyerObj = new ApiBuyerObj(request.getContext().getMessageId(), request.getContext().getTransactionId(),
    				request.getContext().getAction(), request.getContext().getDomain(), request.getContext().getCoreVersion(),
    				timestamp.toString(), body, "N", request.getMessage().getCatalog().getBppProviders().get(0).getItems().get(0).getDescriptor().getName());
    		//System.out.println("*******************************  ");
    		mApiAuditBuyerRepository.save(myApibuyerObj);
    		//System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!  ");
    		OnSearchControllerBuyer.log.info("After Data Inserted in OnSearchControllerBuyer  ");    
		}catch(Exception e) {
			OnSearchControllerBuyer.log.info("Exception in inserting records --"+ e);
		}
		
	}
    
    static {
        log = LoggerFactory.getLogger((Class)OnSearchControllerBuyer.class);
    }
}
