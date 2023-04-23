// 
// Decompiled by Procyon v0.5.36
// 

package com.bb.beckn.init.controller;

import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import com.bb.beckn.common.model.AuditDataModel;
import com.bb.beckn.common.model.AuditFlagModel;
import com.bb.beckn.common.model.HttpModel;
import com.bb.beckn.common.model.AuditModel;
import org.springframework.web.bind.annotation.PostMapping;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.bb.beckn.common.model.ConfigModel;
import com.bb.beckn.api.model.common.Context;
import org.springframework.http.HttpStatus;
import com.bb.beckn.api.model.lookup.LookupRequest;
import com.bb.beckn.common.enums.BecknUserType;
import com.bb.beckn.init.extension.OnSchema;
import com.bb.beckn.repository.ApiAuditbuyerRepository;
import com.bb.beckn.search.model.ApiBuyerObj;

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

@RestController
public class OnInitControllerBuyer
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
    
    @PostMapping({ "/buyer/adaptor/on_init" })
    public ResponseEntity<String> onInit(@RequestBody final String body, @RequestHeader final HttpHeaders httpHeaders) throws JsonProcessingException {
        OnInitControllerBuyer.log.info("The body in {} adaptor is {}", (Object)"init", (Object)this.jsonUtil.unpretty(body));
        try {
            final OnSchema model = (OnSchema)this.jsonUtil.toModel(body, (Class)OnSchema.class);
            OnInitControllerBuyer.log.info("data in buyer controller adaptor [which is will now send back to buyer internal api] is {}", (Object)model.toString());
            final Context context = model.getContext();
            final String bppId = context.getBppId();
            final ConfigModel configuration = this.configService.loadApplicationConfiguration(context.getBapId(), "init");
            final boolean authenticate = configuration.getMatchedApi().isHeaderAuthentication();
            OnInitControllerBuyer.log.info("does seller {} requires to be authenticated ? {}", (Object)bppId, (Object)authenticate);
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
    	
    	try {
    		
    		Timestamp timestamp = new Timestamp(System.currentTimeMillis());  
    		
    		ApiBuyerObj myApibuyerObj = new ApiBuyerObj(request.getContext().getMessageId(), request.getContext().getTransactionId(),
    				request.getContext().getAction(), request.getContext().getDomain(), request.getContext().getCoreVersion(),
    				timestamp.toString(), body, "N",
    				"Not Applicable");
    		mApiAuditBuyerRepository.save(myApibuyerObj);
    		
    		System.out.println("Data Inserted in OnSearchControllerBuyer  ");    
		}catch(Exception e) {
			System.out.println("Exception--"+ e);
		}
		
	}
    static {
        log = LoggerFactory.getLogger((Class)OnInitControllerBuyer.class);
    }
}
