// 
// Decompiled by Procyon v0.5.36
// 

package com.bb.beckn.init.service;

import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import com.bb.beckn.common.model.ConfigModel;
import com.bb.beckn.api.model.common.Context;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.bb.beckn.api.model.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.bb.beckn.init.extension.Schema;
import com.bb.beckn.repository.ApiAuditbuyerRepository;
import com.bb.beckn.search.model.ApiBuyerObj;
import com.bb.beckn.select.service.SelectServiceBuyer;
import com.bb.beckn.common.util.JsonUtil;
import com.bb.beckn.common.validator.BodyValidator;
import com.bb.beckn.common.builder.HeaderBuilder;
import com.bb.beckn.common.service.ApplicationConfigService;
import com.bb.beckn.common.sender.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Timestamp;
import java.time.Instant;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class InitServiceBuyer
{
    private static final Logger log;
    @Autowired
    ApiAuditbuyerRepository mApiAuditBuyerRepository;
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
    
    public ResponseEntity<String> init(final Schema request) throws JsonProcessingException {
        InitServiceBuyer.log.info("Going to validate json request before sending to seller...");
        final Response errorResponse = this.bodyValidator.validateRequestBody(request.getContext(), "init");
        if (errorResponse != null) {
            return (ResponseEntity<String>)new ResponseEntity((Object)this.mapper.writeValueAsString((Object)errorResponse), HttpStatus.BAD_REQUEST);
        }
        final String jsonResponse = this.sendRequestToSeller(request);
        return (ResponseEntity<String>)new ResponseEntity((Object)jsonResponse, HttpStatus.OK);
    }
    
    private String sendRequestToSeller(final Schema request) {
    	final Context context = request.getContext();
    	final ConfigModel configModel = this.configService.loadApplicationConfiguration(context.getBapId(), "init");
        
        final String action = context.getAction();
        request.getContext().setTimestamp(Instant.now().toString());
        request.getContext().setTtl(configModel.getBecknTtl());
        request.getMessage().getOrder().getBilling().setCreatedAt(Instant.now().toString());
        //this will be used when order is updated
        request.getMessage().getOrder().getBilling().setUpdatedAt(Instant.now().toString());
        InitServiceBuyer.log.info("going to call seller id: {} with action: {}", (Object)context.getBppId(), (Object)action);
        String url = context.getBppUri();
        final String json = this.jsonUtil.toJson((Object)request);
        InitServiceBuyer.log.info("final json to be send {}", (Object)json);        
        final HttpHeaders headers = this.authHeaderBuilder.buildHeaders(json, configModel);
        url=url+"init";
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
    		
    		InitServiceBuyer.log.info("After Data Inserted in InitServiceBuyer  ");    
		}catch(Exception e) {
			InitServiceBuyer.log.info("Exception in inserting records InitServiceBuyer --"+ e);
		}
		
	}
 
    static {
        log = LoggerFactory.getLogger((Class)InitServiceBuyer.class);
    }
}
