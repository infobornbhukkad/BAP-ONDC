// 
// Decompiled by Procyon v0.5.36
// 

package com.bb.beckn.search.service;

import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import com.bb.beckn.common.model.ConfigModel;
import com.bb.beckn.api.model.common.Context;
import com.bb.beckn.api.model.common.Payment;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.bb.beckn.api.model.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.bb.beckn.search.controller.OnSearchControllerBuyer;
import com.bb.beckn.search.extension.OnSchema;
import com.bb.beckn.search.extension.Schema;
import com.bb.beckn.search.model.ApiBuyerObj;
import com.bb.beckn.common.util.JsonUtil;
import com.bb.beckn.common.validator.BodyValidator;
import com.bb.beckn.repository.ApiAuditbuyerRepository;
import com.bb.beckn.common.builder.HeaderBuilder;
import com.bb.beckn.common.service.ApplicationConfigService;
import com.bb.beckn.common.sender.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.time.Instant;

@Service
public class SearchServiceBuyer
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
    
    @SuppressWarnings("unchecked")
	public ResponseEntity<String> search(final Schema request) throws JsonProcessingException {
        SearchServiceBuyer.log.info("Going to validate json request before sending to seller...");
        final Response errorResponse = this.bodyValidator.validateRequestBody(request.getContext(), "search");
        if (errorResponse != null) {
            return (ResponseEntity<String>)new ResponseEntity((Object)this.mapper.writeValueAsString((Object)errorResponse), HttpStatus.BAD_REQUEST);
        }
        final String jsonResponse = this.sendRequestToSeller(request);
        
        return (ResponseEntity<String>)new ResponseEntity((Object)jsonResponse, HttpStatus.OK);
    }
    public static Timestamp getTimeStamp() {
		Timestamp myTimeStamp = new Timestamp(System.currentTimeMillis());
		return myTimeStamp;
	}
    
    private String sendRequestToSeller(final Schema request) {
        final Context context = request.getContext();
        final String action = context.getAction();
        SearchServiceBuyer.log.info("going to call seller id: {} with action: {}", (Object)context.getBppId(), (Object)action);        
        System.out.println("Instant.now().toString() -"+ Instant.now().toString());
        final ConfigModel configModel = this.configService.loadApplicationConfiguration(context.getBapId(), "search");
        //request.getContext().setTimestamp(getTimeStamp().toString());
        request.getContext().setTimestamp(Instant.now().toString());
        request.getContext().setTtl(configModel.getBecknTtl());
        Payment payment =new Payment();
        
        request.getMessage().getIntent().setPayment(payment);
        request.getMessage().getIntent().getPayment().setBuyerAppFinderFeeType(configModel.getBuyerappfinderfeeType());
        request.getMessage().getIntent().getPayment().setBuyerAppFinderFeeAmount(configModel.getBuyerappfinderfeeAmount());
        final String json = this.jsonUtil.toJson((Object)request);
       
        
        final String url = configModel.getBecknGateway() + "/search";
        final HttpHeaders headers = this.authHeaderBuilder.buildHeaders(json, configModel);
        this.getMySQLDataSource(json);
        SearchServiceBuyer.log.info("Going to validate json request before sending to seller..." , url);
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
    		
    		SearchServiceBuyer.log.info("After Data Inserted in OnSearchControllerBuyer  ");    
		}catch(Exception e) {
			SearchServiceBuyer.log.info("Exception in inserting records --"+ e);
		}
		
	}
    static {
        log = LoggerFactory.getLogger((Class)SearchServiceBuyer.class);
    }
}
