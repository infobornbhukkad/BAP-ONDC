// 
// Decompiled by Procyon v0.5.36
// 

package com.bb.beckn.init.service;

import com.bb.beckn.api.model.common.*;
import org.slf4j.LoggerFactory;
import com.bb.beckn.common.model.ConfigModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.bb.beckn.api.model.oninit.OnInitMessage;
import com.bb.beckn.api.model.oninit.OnInitRequest;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

import com.bb.beckn.api.enums.AckStatus;
import com.bb.beckn.api.model.response.ResponseMessage;
import com.bb.beckn.api.model.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.bb.beckn.init.extension.Schema;

import org.springframework.http.HttpHeaders;
import com.bb.beckn.common.util.JsonUtil;
import com.bb.beckn.common.validator.BodyValidator;
import com.bb.beckn.common.service.ApplicationConfigService;
import com.bb.beckn.common.sender.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class InitServiceSeller
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
    @Value("classpath:dummyResponses/onInit.json")
    private Resource resource;
    
    public ResponseEntity<String> init(final HttpHeaders httpHeaders, final Schema request) throws JsonProcessingException {
        InitServiceSeller.log.info("Going to validate json request before sending to buyer...");
        final Response errorResponse = this.bodyValidator.validateRequestBody(request.getContext(), "init");
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
        InitServiceSeller.log.info("sending request to seller internal api [in seperate thread]");
        try {
            final Context context = request.getContext();
            final String bppId = context.getBppId();
            final ConfigModel configModel = this.configService.loadApplicationConfiguration(bppId, "init");
            final String url = configModel.getMatchedApi().getHttpEntityEndpoint();
            final String json = this.jsonUtil.toJson((Object)request);
            this.sendRequest.send(url, httpHeaders, json, configModel.getMatchedApi());
        }
        catch (Exception e) {
            InitServiceSeller.log.error("error while sending post request to seller internal api" + e);
            e.printStackTrace();
        }
    }
    
	/*
	 * private void sendRequestToSellerInternalApi(final HttpHeaders httpHeaders,
	 * final Schema request) { InitServiceSeller.log.
	 * info("sending request to seller internal api [in seperate thread]"); try {
	 * final ConfigModel configModel =
	 * this.configService.loadApplicationConfiguration(request.getContext().getBppId
	 * (), "search"); final String url =
	 * configModel.getMatchedApi().getHttpEntityEndpoint(); final String json =
	 * this.jsonUtil.toJson((Object)request);
	 * 
	 * if(!"true".equals(configModel.getDisableAdaptorCalls())){ String resp =
	 * this.sendRequest.send(url, httpHeaders, json, configModel.getMatchedApi());
	 * InitServiceSeller.log.info("Response from ekart adaptor: " + resp); }
	 * 
	 * OnInitRequest respBody = new OnInitRequest();
	 * respBody.setContext(request.getContext());
	 * respBody.getContext().setAction("on_init");
	 * respBody.getContext().setBppId(configModel.getSubscriberId());
	 * respBody.getContext().setBppUri(configModel.getSubscriberUrl());
	 * httpHeaders.remove("host");
	 * 
	 * respBody.setMessage(createResponseMessage(request)); String respJson =
	 * this.jsonUtil.toJson((Object)respBody); InitServiceSeller.log.info(respJson);
	 * InitServiceSeller.log.info(httpHeaders.toString());
	 * 
	 * 
	 * String host = httpHeaders.get("remoteHost").get(0);
	 * if("0:0:0:0:0:0:0:1".equals(host)) { host="localhost"; }
	 * 
	 * String onSearchresp = this.sendRequest.send(respBody.getContext().getBapUri()
	 * +"on_init", httpHeaders, respJson, configModel.getMatchedApi());
	 * InitServiceSeller.log.info(onSearchresp);
	 * 
	 * 
	 * } catch (Exception e) { InitServiceSeller.log.
	 * error("error while sending post request to seller internal api" + e);
	 * e.printStackTrace(); } }
	 * 
	 * private OnInitMessage createResponseMessage(final Schema request){
	 * OnInitMessage onInit = new OnInitMessage();
	 * 
	 * //creating a dummy response //OnInitMessage onInit =
	 * this.mapper.readValue(this.resource.getInputStream(), OnInitMessage.class);
	 * 
	 * onInit.setOrder(request.getMessage().getOrder());
	 * onInit.getOrder().setQuote(getQuote(onInit));
	 * //onInit.getOrder().setPayment(getPaymentInfo(onInit));
	 * 
	 * return onInit;
	 * 
	 * }
	 * 
	 * private Quotation getQuote(OnInitMessage onInitMessage){ return
	 * Quotation.builder() .price(calculatePrice(onInitMessage.getOrder()))
	 * .build();
	 * 
	 * }
	 * 
	 * private Payment getPaymentInfo(OnInitMessage onInitMessage){
	 * 
	 * return Payment.builder() .status("NOT-PAID") .tlMethod("http/get") .type(
	 * "ON-FULFILMENT")
	 * .uri("https://api.bpp.com/pay?amt=$180&mode=upi&vpa=bpp@upi")
	 * .params(getPaymentParams(onInitMessage.getOrder().getQuote().getPrice()))
	 * .build(); }
	 * 
	 * private PaymentParams getPaymentParams(Price price){ return
	 * PaymentParams.builder() .amount(Float.toString(price.getValue()))
	 * .mode("upi") .vpa("fk@upi") .build(); }
	 * 
	 * private Price calculatePrice(Order order){ Price price = new Price();
	 * price.setCurrency("INR"); AtomicReference<Float> totalPrice = new
	 * AtomicReference<>((float) 0); final float distanceMultiplier =
	 * getDistanceMultiplier(order.getFulfillment()); final float perItemPrice = 10;
	 * order.getItems().forEach((item) -> { totalPrice.set(totalPrice.get() +
	 * item.getQuantity().getCount()*perItemPrice*distanceMultiplier); });
	 * price.setValue(String.valueOf(totalPrice.get())); return price; }
	 * 
	 * private float getDistanceMultiplier(Fulfillment fulfillment){ String
	 * startingCoordinate = fulfillment.getStart().getLocation().getGps(); String
	 * endCoordinate = fulfillment.getEnd().getLocation().getGps();
	 * 
	 * String stPoints[] = startingCoordinate.split(","); String enPoints[] =
	 * endCoordinate.split(",");
	 * 
	 * double lat1,lat2,lon1,lon2;
	 * 
	 * lat1=Double.parseDouble(stPoints[0]); lon1=Double.parseDouble(stPoints[1]);
	 * lat2=Double.parseDouble(enPoints[0]); lon2=Double.parseDouble(enPoints[1]);
	 * 
	 * final int R = 6371; // Radius of the earth
	 * 
	 * double latDistance = Math.toRadians(lat2 - lat1); double lonDistance =
	 * Math.toRadians(lon2 - lon1); double a = Math.sin(latDistance / 2) *
	 * Math.sin(latDistance / 2) + Math.cos(Math.toRadians(lat1)) *
	 * Math.cos(Math.toRadians(lat2)) Math.sin(lonDistance / 2) *
	 * Math.sin(lonDistance / 2); double c = 2 * Math.atan2(Math.sqrt(a),
	 * Math.sqrt(1 - a)); double distance = R * c; // convert to meters
	 * 
	 * distance = Math.pow(distance, 2);
	 * 
	 * float multiplier =(float) Math.max(100.0, Math.cbrt(distance/2)); return
	 * Math.min(1, multiplier);
	 * 
	 * }
	 */

    static {
        log = LoggerFactory.getLogger((Class)InitServiceSeller.class);
    }
}
