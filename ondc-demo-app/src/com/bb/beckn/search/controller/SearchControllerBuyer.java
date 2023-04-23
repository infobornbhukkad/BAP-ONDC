// 
// Decompiled by Procyon v0.5.36
// 

package com.bb.beckn.search.controller;

import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.bb.beckn.search.extension.Schema;
import com.bb.beckn.common.exception.ApplicationException;
import com.bb.beckn.common.exception.ErrorCode;
import com.bb.beckn.common.enums.OndcUserType;
import org.springframework.http.ResponseEntity;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Value;
import com.bb.beckn.common.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import com.bb.beckn.search.service.SearchServiceBuyer;
import com.bb.beckn.utility.Utility;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchControllerBuyer
{
    private static final Logger log;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private Utility appError;
    @Autowired
    private SearchServiceBuyer service;
    @Autowired
    private JsonUtil jsonUtil;
    @Value("${beckn.entity.type}")
    private String entityType;
    
    @PostMapping({ "/buyer/adaptor/search" })
    public ResponseEntity<String> search(@RequestBody final String body, @RequestHeader final HttpHeaders httpHeaders, final HttpServletRequest servletRequest) throws JsonProcessingException {
            	
    	SearchControllerBuyer.log.info("The body in {} adaptor is {}", (Object)"search", (Object)this.jsonUtil.unpretty(body));
        SearchControllerBuyer.log.info("Entity type is {}", (Object)this.entityType);
        if (!OndcUserType.BUYER.type().equalsIgnoreCase(this.entityType)) {
            throw new ApplicationException(ErrorCode.INVALID_ENTITY_TYPE);
        }
        final Schema request = (Schema)this.jsonUtil.toModel(body, (Class)Schema.class);
        return (ResponseEntity<String>)this.service.search(request);
    }
    
    static {
        log = LoggerFactory.getLogger((Class)SearchControllerBuyer.class);
    }
}
