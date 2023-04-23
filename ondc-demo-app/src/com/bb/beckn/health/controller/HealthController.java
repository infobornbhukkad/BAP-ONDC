// 
// Decompiled by Procyon v0.5.36
// 

package com.bb.beckn.health.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
public class HealthController
{
    private static final Logger log;

    
    @GetMapping({ "/" })
    public ResponseEntity<String> get(@RequestHeader final HttpHeaders httpHeaders, final HttpServletRequest servletRequest) throws JsonProcessingException {
        HealthController.log.info("Get success");

        return (ResponseEntity<String>)new ResponseEntity(null, HttpStatus.OK);
    
    }
   
    
    static {
        log = LoggerFactory.getLogger((Class)HealthController.class);
    }
}
