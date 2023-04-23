// 
// Decompiled by Procyon v0.5.36
// 

package com.bb.beckn.cancel.service;

import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import com.bb.beckn.common.model.ConfigModel;
import com.bb.beckn.api.model.common.Context;
import com.bb.beckn.api.model.common.Error;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.bb.beckn.api.model.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.bb.beckn.cancel.extension.Schema;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class CancelServiceBuyer
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
    
    public ResponseEntity<String> cancel(final Schema request) throws JsonProcessingException, ParseException {
        CancelServiceBuyer.log.info("Going to validate json request before sending to seller...");
        OnSchema model =null;
        final Response errorResponse = this.bodyValidator.validateRequestBody(request.getContext(), "cancel");
        if (errorResponse != null) {
            return (ResponseEntity<String>)new ResponseEntity((Object)this.mapper.writeValueAsString((Object)errorResponse), HttpStatus.BAD_REQUEST);
        }
        ConfirmAuditBuyerObj mconfirmAuditBuyerObj= mConfirmAuditByerRepository.findByorderidandorderstate(request.getMessage().getOrderId(),"Accepted");
        if(mconfirmAuditBuyerObj.getSellerorderstate().equalsIgnoreCase("Cancelled")) {
        	return (ResponseEntity<String>)new ResponseEntity((Object)this.mapper.writeValueAsString((Object)errorResponse), HttpStatus.BAD_REQUEST);
        }
        if (request.getMessage().getCancellationReasonId()!=null && request.getMessage().getCancellationReasonId().equals("004") || request.getMessage().getCancellationReasonId().equals("006")) {
        	
        	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
            Date parsedDate = dateFormat.parse(mconfirmAuditBuyerObj.getCreationdate());
            Timestamp creationTimestamp = new java.sql.Timestamp(parsedDate.getTime());
        	
            Timestamp nowTimestamp = new Timestamp(System.currentTimeMillis());
            System.out.println("nowTimestamp -- "+ nowTimestamp);
            System.out.println("mconfirmAuditBuyerObj.getCreationdate() -- "+ mconfirmAuditBuyerObj.getCreationdate());
            System.out.println("creationTimestamp.getTime() -- "+ creationTimestamp.getTime());
            System.out.println("nowTimestamp.getTime() -- "+ nowTimestamp.getTime());
            // Calculate the time difference between the two Timestamp objects
            long timeDiffMillis = nowTimestamp.getTime() - creationTimestamp.getTime();
            //long timeDiffSeconds = TimeUnit.MILLISECONDS.toSeconds(timeDiffMillis);
            long timeDiffMinutes = TimeUnit.MILLISECONDS.toMinutes(timeDiffMillis);
            //long timeDiffHours = TimeUnit.MILLISECONDS.toHours(timeDiffMillis);

            // Output the result
            System.out.println("The time difference between " + creationTimestamp + " and " + nowTimestamp + " is " + timeDiffMillis + " minutes");
            ApiBuyerObj mApiBuyerObj= mApiAuditBuyerRepository.findByTransactionidandaction(mconfirmAuditBuyerObj.getTransactionid(),"on_select");
             
            model = (OnSchema)this.jsonUtil.toModel(mApiBuyerObj.getJson(), (Class)OnSchema.class);
            String ordertodelivery= model.getMessage().getOrder().getFulfillments().get(0).getTat();
            Duration duration = Duration.parse(ordertodelivery);
            
            System.out.println("duration.toMinutes()---  "+ duration.toMinutes());
            if(duration.toMinutes() < timeDiffMinutes) {
            	final Error error = new Error();
            	error.setMessage("Delivery SLA is not Breached");
            	errorResponse.setError(error);
            	return (ResponseEntity<String>)new ResponseEntity((Object)this.mapper.writeValueAsString((Object)errorResponse), HttpStatus.BAD_REQUEST);
            }
        }
        final String jsonResponse = this.sendRequestToSeller(request, model);
        return (ResponseEntity<String>)new ResponseEntity((Object)jsonResponse, HttpStatus.OK);
    }
    
    private String sendRequestToSeller(final Schema request, OnSchema model) {
    	final Context context = request.getContext();
    	final ConfigModel configModel = this.configService.loadApplicationConfiguration(context.getBapId(), "cancel");
        
        final String action = context.getAction();
        request.getContext().setTimestamp(Instant.now().toString());
        request.getContext().setTtl(configModel.getBecknTtl());
        request.getContext().setBppId(model.getContext().getBppId());
        request.getContext().setBppUri(model.getContext().getBppUri());
        request.getContext().setTransactionId(model.getContext().getTransactionId());
        CancelServiceBuyer.log.info("going to call seller id: {} with action: {}", (Object)context.getBppId(), (Object)action);
        String url = model.getContext().getBppUri();
        final String json = this.jsonUtil.toJson((Object)request);
        CancelServiceBuyer.log.info("final json to be send {}", (Object)json);
        
        final HttpHeaders headers = this.authHeaderBuilder.buildHeaders(json, configModel);
        url=url+"cancel";
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
    		
    		CancelServiceBuyer.log.info("After Data Inserted in CancelServiceBuyer  ");    
		}catch(Exception e) {
			CancelServiceBuyer.log.info("Exception in inserting records CancelServiceBuyer--"+ e);
		}
		
	}

    static {
        log = LoggerFactory.getLogger((Class)CancelServiceBuyer.class);
    }
}
