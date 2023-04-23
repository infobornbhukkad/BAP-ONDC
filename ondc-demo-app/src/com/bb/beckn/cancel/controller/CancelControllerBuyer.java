// 
// Decompiled by Procyon v0.5.36
// 

package com.bb.beckn.cancel.controller;

import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.bb.beckn.cancel.extension.Schema;
import com.bb.beckn.common.exception.ApplicationException;
import com.bb.beckn.common.exception.ErrorCode;
import com.bb.beckn.common.enums.OndcUserType;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Value;
import com.bb.beckn.common.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import com.bb.beckn.cancel.service.CancelServiceBuyer;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CancelControllerBuyer
{
    private static final Logger log;
    @Autowired
    private CancelServiceBuyer service;
    @Autowired
    private JsonUtil jsonUtil;
    @Value("${beckn.entity.type}")
    private String entityType;
    
    @PostMapping({ "/buyer/adaptor/cancel" })
    public ResponseEntity<String> cancel(@RequestBody final String body, @RequestHeader final HttpHeaders httpHeaders, final HttpServletRequest servletRequest) throws JsonProcessingException, ParseException {
        CancelControllerBuyer.log.info("The body in {} adaptor is {}", (Object)"cancel", (Object)this.jsonUtil.unpretty(body));
        CancelControllerBuyer.log.info("Entity type is {}", (Object)this.entityType);
        if (!OndcUserType.BUYER.type().equalsIgnoreCase(this.entityType)) {
            throw new ApplicationException(ErrorCode.INVALID_ENTITY_TYPE);
        }
        final Schema request = (Schema)this.jsonUtil.toModel(body, (Class)Schema.class);
        return (ResponseEntity<String>)this.service.cancel(request);
    }
    
    static {
        log = LoggerFactory.getLogger((Class)CancelControllerBuyer.class);
    }
}
