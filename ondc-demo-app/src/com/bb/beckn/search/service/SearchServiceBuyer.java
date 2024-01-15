package com.bb.beckn.search.service;

import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import com.bb.beckn.common.model.ConfigModel;
import com.bb.beckn.api.model.common.Context;
import com.bb.beckn.api.model.common.Payment;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.bb.beckn.search.extension.Schema;
import com.bb.beckn.api.model.response.Response;
import com.bb.beckn.common.util.JsonUtil;
import com.bb.beckn.common.validator.BodyValidator;

import com.bb.beckn.common.builder.HeaderBuilder;
import com.bb.beckn.common.service.ApplicationConfigService;
import com.bb.beckn.common.service.ErrorCodeService;
import com.bb.beckn.common.sender.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class SearchServiceBuyer
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
    private ErrorCodeService errorCodeService;
    
    @SuppressWarnings("unchecked")
	public ResponseEntity<String> search(final Schema request) throws JsonProcessingException {
    	SearchServiceBuyer.log.info("Inside the search method of SearchServiceBuyer class ...");
        SearchServiceBuyer.log.info("Going to validate json request before sending to seller...");
        final Response errorResponse = this.bodyValidator.validateRequestBody(request.getContext(), "search");
        if (errorResponse != null) {            
            return this.errorCodeService.sendErrorMessage(request,"Stale Request","20002", "Cannot process stale request");
        }
        final String jsonResponse = this.sendRequestToSeller(request);
        
        return (ResponseEntity<String>)new ResponseEntity((Object)jsonResponse, HttpStatus.OK);
    }
        
    private String sendRequestToSeller(final Schema request) {
    	SearchServiceBuyer.log.info("Inside the sendRequestToSeller method of SearchServiceBuyer class ...");
    	
        final Context context = request.getContext();
        final String action = context.getAction();
        SearchServiceBuyer.log.info("going to call seller id: {} with action: {}", (Object)context.getBppId(), (Object)action);        
        //System.out.println("Instant.now().toString() -"+ Instant.now().toString());
        final ConfigModel configModel = this.configService.loadApplicationConfiguration(context.getBapId(), "search");
        //request.getContext().setTimestamp(getTimeStamp().toString());
        request.getContext().setTimestamp(Instant.now().toString());
        request.getContext().setTtl(configModel.getBecknTtl());
        //Payment payment =new Payment();
        
        //request.getMessage().getIntent().setPayment(payment);
       // request.getMessage().getIntent().getPayment().setBuyerAppFinderFeeType(configModel.getBuyerappfinderfeeType());
        //request.getMessage().getIntent().getPayment().setBuyerAppFinderFeeAmount(configModel.getBuyerappfinderfeeAmount());
        final String json = this.jsonUtil.toJson((Object)request);
       
        
        final String url = configModel.getBecknGateway() + "/search";
        final HttpHeaders headers = this.authHeaderBuilder.buildHeaders(json, configModel);
        this.getMySQLDataSource(json);
        SearchServiceBuyer.log.info("Going to validate json request before sending to seller..." , url);
        return this.sender.send(url, headers, json, configModel.getMatchedApi());
    }
    
  public void getMySQLDataSource(final String body ) {
	  SearchServiceBuyer.log.info("Inside the getMySQLDataSource method of SearchServiceBuyer class for inserting the request in api_audit_buyer table...");
    	
    	 SearchServiceBuyer.log.info("exiting the getMySQLDataSource method of SearchServiceBuyer class for inserting the request in api_audit_buyer table...");
	}
    static {
        log = LoggerFactory.getLogger((Class)SearchServiceBuyer.class);
    }
}
