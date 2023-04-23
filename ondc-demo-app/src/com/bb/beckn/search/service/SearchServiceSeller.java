// 
// Decompiled by Procyon v0.5.36
// 

package com.bb.beckn.search.service;

import org.slf4j.LoggerFactory;
import com.bb.beckn.common.model.ConfigModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.bb.beckn.api.model.common.Context;
import com.bb.beckn.api.model.onsearch.OnSearchMessage;

import java.util.concurrent.CompletableFuture;
import com.bb.beckn.api.model.common.Ack;
import com.bb.beckn.api.model.common.Catalog;
import com.bb.beckn.api.enums.AckStatus;
import com.bb.beckn.api.model.response.ResponseMessage;
import com.bb.beckn.api.model.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.bb.beckn.search.extension.Schema;
import com.bb.beckn.search.extension.OnSchema;

import org.springframework.http.HttpHeaders;
import com.bb.beckn.common.util.JsonUtil;
import com.bb.beckn.common.validator.BodyValidator;
import com.bb.beckn.common.service.ApplicationConfigService;
import com.bb.beckn.common.sender.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.core.io.Resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class SearchServiceSeller
{
    private static final Logger log;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private Sender sendRequest;
    @Autowired
    private ApplicationConfigService configService;
    @Autowired
    private BodyValidator bodyValidator;
    @Autowired
    private JsonUtil jsonUtil;
    
    @Autowired
    @Value("classpath:dummyResponses/onSearch.json")
    private Resource resource;
    
    public ResponseEntity<String> search(final HttpHeaders httpHeaders, final Schema request) throws JsonProcessingException {
        SearchServiceSeller.log.info("Going to validate json request before sending to buyer...");
        final Response errorResponse = this.bodyValidator.validateRequestBody(request.getContext(), "search");
        if (errorResponse != null) {
            return (ResponseEntity<String>)new ResponseEntity((Object)this.mapper.writeValueAsString((Object)errorResponse), HttpStatus.BAD_REQUEST);
        }
        final Response adaptorResponse = new Response();
        final ResponseMessage resMsg = new ResponseMessage();
        resMsg.setAck(new Ack(AckStatus.ACK));
        adaptorResponse.setMessage(resMsg);
        final Context ctx = request.getContext();
        adaptorResponse.setContext(ctx);
        CompletableFuture.runAsync(() -> {
            this.sendRequestToSellerInternalApi(httpHeaders, request);
         });
        return (ResponseEntity<String>)new ResponseEntity((Object)this.mapper.writeValueAsString((Object)adaptorResponse), HttpStatus.OK);
    }
    
    private void sendRequestToSellerInternalApi(final HttpHeaders httpHeaders, final Schema request) {
        SearchServiceSeller.log.info("sending request to seller internal api [in seperate thread]");
        try {
            final ConfigModel configModel = this.configService.loadApplicationConfiguration(request.getContext().getBppId(), "search");
            final String url = configModel.getMatchedApi().getHttpEntityEndpoint();
            final String json = this.jsonUtil.toJson((Object)request);
            
            if(!"true".equals(configModel.getDisableAdaptorCalls())){              
                String resp = this.sendRequest.send(url, httpHeaders, json, configModel.getMatchedApi());
                SearchServiceSeller.log.info("Response from ekart adaptor: " + resp);
            }
            
            //creating a dummy response
            OnSearchMessage onSearch = this.mapper.readValue(this.resource.getInputStream(), OnSearchMessage.class);
            SearchServiceSeller.log.info(onSearch.toString());
            
            OnSchema respBody = new OnSchema();
            respBody.setContext(request.getContext());
            respBody.getContext().setAction("on_search");
            respBody.getContext().setBppId(configModel.getSubscriberId());
            respBody.getContext().setBppUri(configModel.getSubscriberUrl());

            respBody.setMessage(onSearch);
            String respJson = this.jsonUtil.toJson((Object)respBody);

            String host = httpHeaders.get("remoteHost").get(0);
            if("0:0:0:0:0:0:0:1".equals(host)) {
            	host="localhost";
            }else {
            	host = configModel.getBecknGateway();
            }
            
            String onSearchresp = this.sendRequest.send("https://" +host +"/on_search", 
            		httpHeaders, respJson, configModel.getMatchedApi());
            SearchServiceSeller.log.info(onSearchresp);

            
        }
        catch (Exception e) {
            SearchServiceSeller.log.error("error while sending post request to seller internal api" + e);
            e.printStackTrace();
        }
    }
    
    static {
        log = LoggerFactory.getLogger((Class)SearchServiceSeller.class);
    }
}
