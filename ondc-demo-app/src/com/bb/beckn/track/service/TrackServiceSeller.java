// 
// Decompiled by Procyon v0.5.36
// 

package com.bb.beckn.track.service;

import lombok.Builder;

import org.slf4j.LoggerFactory;
import com.bb.beckn.common.model.ConfigModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.bb.beckn.api.model.common.Context;
import com.bb.beckn.api.model.ontrack.OnTrackMessage;
import com.bb.beckn.api.model.ontrack.OnTrackRequest;
import com.bb.beckn.api.model.common.Tracking;

import java.util.concurrent.CompletableFuture;
import com.bb.beckn.api.model.common.Ack;
import com.bb.beckn.api.enums.AckStatus;
import com.bb.beckn.api.model.response.ResponseMessage;
import com.bb.beckn.api.model.response.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.bb.beckn.track.extension.Schema;

import org.springframework.http.HttpHeaders;
import com.bb.beckn.common.util.JsonUtil;
import com.bb.beckn.common.validator.BodyValidator;
import com.bb.beckn.common.service.ApplicationConfigService;
import com.bb.beckn.common.sender.Sender;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class TrackServiceSeller
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
    @Value("classpath:dummyResponses/onTrack.json")
    private Resource resource;
    
    public ResponseEntity<String> track(final HttpHeaders httpHeaders, final Schema request) throws JsonProcessingException {
        TrackServiceSeller.log.info("Going to validate json request before sending to buyer...");
        final Response errorResponse = this.bodyValidator.validateRequestBody(request.getContext(), "track");
        if (errorResponse != null) {
            return (ResponseEntity<String>)new ResponseEntity((Object)this.mapper.writeValueAsString((Object)errorResponse), HttpStatus.BAD_REQUEST);
        }
        final Response adaptorResponse = new Response();
        final ResponseMessage resMsg = new ResponseMessage();
        resMsg.setAck(new Ack(AckStatus.ACK));
        adaptorResponse.setMessage(resMsg);
        final Context ctx = request.getContext();
        adaptorResponse.setContext(ctx);

        //CompletableFuture.runAsync(this::lambda$track$0);
        CompletableFuture.runAsync(() -> {
            this.sendRequestToSellerInternalApi(httpHeaders, request);
        });

        return (ResponseEntity<String>)new ResponseEntity((Object)this.mapper.writeValueAsString((Object)adaptorResponse), HttpStatus.OK);
    }
    
    private void sendRequestToSellerInternalApi(final HttpHeaders httpHeaders, final Schema request) {
        TrackServiceSeller.log.info("sending request to seller internal api [in seperate thread]");
        try {
            final ConfigModel configModel = this.configService.loadApplicationConfiguration(request.getContext().getBppId(), "track");
            final String url = configModel.getMatchedApi().getHttpEntityEndpoint();
            final String json = this.jsonUtil.toJson((Object)request);
            //     this.sendRequest.send(url, httpHeaders, json, configModel.getMatchedApi());

            if(!"true".equals(configModel.getDisableAdaptorCalls())){
                String resp = this.sendRequest.send(url, httpHeaders, json, configModel.getMatchedApi());
                TrackServiceSeller.log.info("Response from ekart adaptor: " + resp);
            }

            //creating a dummy response
       //     OnTrackMessage onTrack = this.mapper.readValue(this.resource.getInputStream(), OnTrackMessage.class);
         //   TrackServiceSeller.log.info(onTrack.toString());

            OnTrackRequest respBody = new OnTrackRequest();
            respBody.setContext(request.getContext());
            respBody.getContext().setAction("on_track");
            respBody.getContext().setBppId(configModel.getSubscriberId());
            respBody.getContext().setBppUri(configModel.getSubscriberUrl());
            httpHeaders.remove("host");

            respBody.setMessage(createResponseMessage(request));
            String respJson = this.jsonUtil.toJson((Object)respBody);

            String host = httpHeaders.get("remoteHost").get(0);
            if("0:0:0:0:0:0:0:1".equals(host)) {
                host="localhost";
            }

            String onTrackresp = this.sendRequest.send(respBody.getContext().getBapUri() +"on_track",
                    httpHeaders, respJson, configModel.getMatchedApi());
            TrackServiceSeller.log.info(onTrackresp);
        }
        catch (Exception e) {
            TrackServiceSeller.log.error("error while sending post request to seller internal api" + e);
            e.printStackTrace();
        }
    }
    private OnTrackMessage createResponseMessage(final Schema request){
        OnTrackMessage onTrack =new OnTrackMessage();
        onTrack.setTracking(createTrackingInfo(request));
        return onTrack;
    }
    private Tracking createTrackingInfo(final Schema request){

        return Tracking.builder()
                .status("active")
                .url(getString(request))
                .build();

    }
    private String getString(final Schema request){

        return "https://track.mock_bpp.com?order_id="+request.getMessage().getOrderId();
    }
    static {
        log = LoggerFactory.getLogger((Class)TrackServiceSeller.class);
    }
}
