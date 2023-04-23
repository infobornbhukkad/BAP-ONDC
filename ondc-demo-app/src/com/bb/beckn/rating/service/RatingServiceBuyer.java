// 
// Decompiled by Procyon v0.5.36
// 

package com.bb.beckn.rating.service;

import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import com.bb.beckn.common.model.ConfigModel;
import com.bb.beckn.api.model.common.Context;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.bb.beckn.api.model.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.bb.beckn.rating.extension.Schema;
import com.bb.beckn.common.util.JsonUtil;
import com.bb.beckn.common.validator.BodyValidator;
import com.bb.beckn.common.builder.HeaderBuilder;
import com.bb.beckn.common.service.ApplicationConfigService;
import com.bb.beckn.common.sender.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class RatingServiceBuyer
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
    
    public ResponseEntity<String> rating(final Schema request) throws JsonProcessingException {
        RatingServiceBuyer.log.info("Going to validate json request before sending to seller...");
        final Response errorResponse = this.bodyValidator.validateRequestBody(request.getContext(), "rating");
        if (errorResponse != null) {
            return (ResponseEntity<String>)new ResponseEntity((Object)this.mapper.writeValueAsString((Object)errorResponse), HttpStatus.BAD_REQUEST);
        }
        final String jsonResponse = this.sendRequestToSeller(request);
        return (ResponseEntity<String>)new ResponseEntity((Object)jsonResponse, HttpStatus.OK);
    }
    
    private String sendRequestToSeller(final Schema request) {
        final Context context = request.getContext();
        final String action = context.getAction();
        RatingServiceBuyer.log.info("going to call seller id: {} with action: {}", (Object)context.getBppId(), (Object)action);
        final String url = context.getBppUri();
        final String json = this.jsonUtil.toJson((Object)request);
        RatingServiceBuyer.log.info("final json to be send {}", (Object)json);
        final ConfigModel configModel = this.configService.loadApplicationConfiguration(context.getBapId(), "rating");
        final HttpHeaders headers = this.authHeaderBuilder.buildHeaders(json, configModel);
        return this.sender.send(url, headers, json, configModel.getMatchedApi());
    }
    
    static {
        log = LoggerFactory.getLogger((Class)RatingServiceBuyer.class);
    }
}
