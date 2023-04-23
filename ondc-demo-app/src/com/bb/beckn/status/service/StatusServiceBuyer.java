// 
// Decompiled by Procyon v0.5.36
// 

package com.bb.beckn.status.service;

import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import com.bb.beckn.common.model.ConfigModel;
import com.bb.beckn.api.model.common.Context;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.bb.beckn.api.model.response.Response;
import com.bb.beckn.api.model.status.StatusMessage;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.bb.beckn.status.extension.Schema;
import com.bb.beckn.common.util.JsonUtil;
import com.bb.beckn.common.validator.BodyValidator;
import com.bb.beckn.init.service.InitServiceBuyer;
import com.bb.beckn.repository.ApiAuditbuyerRepository;
import com.bb.beckn.repository.ConfirmAuditBuyerRepository;
import com.bb.beckn.search.model.ApiBuyerObj;
import com.bb.beckn.search.model.ConfirmAuditBuyerObj;
import com.bb.beckn.select.extension.OnSchema;
import com.bb.beckn.common.builder.HeaderBuilder;
import com.bb.beckn.common.service.ApplicationConfigService;
import com.bb.beckn.common.sender.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class StatusServiceBuyer
{
    private static final Logger log;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private Sender sender;
    @Autowired
    private ApplicationConfigService configService;
    @Autowired
    private HeaderBuilder authHeaderBuilder;
    @Autowired
    private BodyValidator bodyValidator;
    @Autowired
    private JsonUtil jsonUtil;
    @Autowired
    ConfirmAuditBuyerRepository mConfirmAuditByerRepository;
    @Autowired
    ApiAuditbuyerRepository mApiAuditBuyerRepository;
    
    public ResponseEntity<String> status(final Schema request) throws JsonProcessingException {
        StatusServiceBuyer.log.info("Going to validate json request before sending to seller...");
       
        final Response errorResponse = this.bodyValidator.validateRequestBody(request.getContext(), "status");
        if (errorResponse != null) {
            return (ResponseEntity<String>)new ResponseEntity((Object)this.mapper.writeValueAsString((Object)errorResponse), HttpStatus.BAD_REQUEST);
        }
        
        List<ConfirmAuditBuyerObj> mconfirmAuditBuyerObjlist= mConfirmAuditByerRepository.findByorderid(request.getMessage().getOrderId());
        ConfirmAuditBuyerObj mconfirmAuditBuyerObj=null;
        
        if(mconfirmAuditBuyerObjlist.size()==0) {
        	return (ResponseEntity<String>)new ResponseEntity((Object)this.mapper.writeValueAsString((Object)errorResponse), HttpStatus.BAD_REQUEST);
        }
        for(int i=0; i < mconfirmAuditBuyerObjlist.size(); i++) {
        	if(mconfirmAuditBuyerObjlist.get(i).getSellerorderstate().equalsIgnoreCase("cancelled")){
        		return (ResponseEntity<String>)new ResponseEntity((Object)this.mapper.writeValueAsString((Object)errorResponse), HttpStatus.BAD_REQUEST);
        	}else {
        		mconfirmAuditBuyerObj=mconfirmAuditBuyerObjlist.get(i);
        	}
        }
        final String jsonResponse = this.sendRequestToSeller(request, mconfirmAuditBuyerObj);
        return (ResponseEntity<String>)new ResponseEntity((Object)jsonResponse, HttpStatus.OK);
    }
    
    private String sendRequestToSeller(final Schema request, ConfirmAuditBuyerObj mconfirmAuditBuyerObj) {
    	final Context context = request.getContext();
    	final ConfigModel configModel = this.configService.loadApplicationConfiguration(context.getBapId(), "status");
       
        final String action = context.getAction();
        request.getContext().setDomain(configModel.getBapDomain());
        request.getContext().setTimestamp(Instant.now().toString());
        request.getContext().setTtl(configModel.getBecknTtl());
        request.getContext().setBppId(mconfirmAuditBuyerObj.getBppid());
        request.getContext().setBppUri(mconfirmAuditBuyerObj.getBppurl());
        request.getContext().setTransactionId(mconfirmAuditBuyerObj.getTransactionid());
        request.getContext().setAction(context.getAction());
        request.getContext().setCoreVersion(configModel.getVersion());
        //request.setMessage(new StatusMessage());
        request.getMessage().setOrderId(request.getMessage().getOrderId());
        
        StatusServiceBuyer.log.info("going to call seller id: {} with action: {}", (Object)context.getBppId(), (Object)action);
        String url = mconfirmAuditBuyerObj.getBppurl();
        final String json = this.jsonUtil.toJson((Object)request);
        StatusServiceBuyer.log.info("final json to be send {}", (Object)json);
        
        final HttpHeaders headers = this.authHeaderBuilder.buildHeaders(json, configModel);
        url=url+"status";
        this.getMySQLDataSource(json);
        return this.sender.send(url, headers, json, configModel.getMatchedApi());
    }
    
    public void getMySQLDataSource(final String body ) {
    	
    	try {
    		final Schema request = (Schema)this.jsonUtil.toModel(body, (Class)Schema.class);    		
    		Timestamp timestamp = new Timestamp(System.currentTimeMillis());  
    		
    		ApiBuyerObj myApibuyerObj = new ApiBuyerObj(request.getContext().getMessageId(), request.getContext().getTransactionId(),
    				request.getContext().getAction(), request.getContext().getDomain(), request.getContext().getCoreVersion(),
    				timestamp.toString(), body, "N", "NA");
    		
    		mApiAuditBuyerRepository.save(myApibuyerObj);
    		
    		StatusServiceBuyer.log.info("After Data Inserted in StatusServiceBuyer   ");    
		}catch(Exception e) {
			StatusServiceBuyer.log.info("Exception in inserting records StatusServiceBuyer --"+ e);
		}
		
	}

    static {
        log = LoggerFactory.getLogger((Class)StatusServiceBuyer.class);
    }
}
