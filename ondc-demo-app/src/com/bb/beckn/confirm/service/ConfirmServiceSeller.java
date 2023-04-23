// 
// Decompiled by Procyon v0.5.36
// 

package com.bb.beckn.confirm.service;

import com.bb.beckn.api.model.onconfirm.OnConfirmMessage;
import com.bb.beckn.api.model.onconfirm.OnConfirmRequest;
import com.bb.beckn.common.service.OrderStateService;
import org.slf4j.LoggerFactory;
import com.bb.beckn.common.model.ConfigModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.bb.beckn.api.model.common.Context;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import com.bb.beckn.api.model.common.Ack;
import com.bb.beckn.api.enums.AckStatus;
import com.bb.beckn.api.model.response.ResponseMessage;
import com.bb.beckn.api.model.response.Response;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.bb.beckn.confirm.extension.Schema;
import org.springframework.http.HttpHeaders;
import com.bb.beckn.common.util.JsonUtil;
import com.bb.beckn.common.validator.BodyValidator;
import com.bb.beckn.common.service.ApplicationConfigService;
import com.bb.beckn.common.sender.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class ConfirmServiceSeller
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
    @Value("classpath:dummyResponses/onConfirm.json")
    private Resource resource;

    @Autowired
    private OrderStateService orderStateService;
    
    public ResponseEntity<String> confirm(final HttpHeaders httpHeaders, final Schema request) throws JsonProcessingException {
        ConfirmServiceSeller.log.info("Going to validate json request before sending to buyer...");
        final Response errorResponse = this.bodyValidator.validateRequestBody(request.getContext(), "confirm");
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
        ConfirmServiceSeller.log.info("sending request to seller internal api [in seperate thread]");
        try {
            final Context context = request.getContext();
            final String bppId = context.getBppId();
            final ConfigModel configModel = this.configService.loadApplicationConfiguration(bppId, "confirm");
            final String url = configModel.getMatchedApi().getHttpEntityEndpoint();
            final String json = this.jsonUtil.toJson((Object)request);
            //this.sendRequest.send(url, httpHeaders, json, configModel.getMatchedApi());

            if(!"true".equals(configModel.getDisableAdaptorCalls())){
                String resp = this.sendRequest.send(url, httpHeaders, json, configModel.getMatchedApi());
                ConfirmServiceSeller.log.info("Response from ekart adaptor: " + resp);
            }

            //creating a dummy response
      //      OnConfirmMessage onConfirm = this.mapper.readValue(this.resource.getInputStream(), OnConfirmMessage.class);
        //    ConfirmServiceSeller.log.info(onConfirm.toString());

            OnConfirmRequest respBody = new OnConfirmRequest();
            respBody.setContext(request.getContext());
            respBody.getContext().setAction("on_confirm");
            respBody.getContext().setBppId(configModel.getSubscriberId());
            respBody.getContext().setBppUri(configModel.getSubscriberUrl());
            httpHeaders.remove("host");

            respBody.setMessage(createResponseMessage(request));
            String respJson = this.jsonUtil.toJson((Object)respBody);

            String host = httpHeaders.get("remoteHost").get(0);
            if("0:0:0:0:0:0:0:1".equals(host)) {
                host="localhost";
            }

            String onConfirmResp = this.sendRequest.send(respBody.getContext().getBapUri() +"on_confirm",
                    httpHeaders, respJson, configModel.getMatchedApi());
            ConfirmServiceSeller.log.info(onConfirmResp);
        }
        catch (Exception e) {
            ConfirmServiceSeller.log.error("error while sending post request to seller internal api" + e);
            e.printStackTrace();
        }
    }
    private OnConfirmMessage createResponseMessage(final Schema request){
        OnConfirmMessage onConfirm =new OnConfirmMessage();
        onConfirm.setOrder(request.getMessage().getOrder());
        onConfirm.getOrder().setState("SEARCHING-FOR-FMD-AGENT");
        String uniqueID = UUID.randomUUID().toString();
        onConfirm.getOrder().setId(uniqueID);
        orderStateService.addOrder(onConfirm.getOrder());
        return onConfirm;
    }
    static {
        log = LoggerFactory.getLogger((Class)ConfirmServiceSeller.class);
    }
}
