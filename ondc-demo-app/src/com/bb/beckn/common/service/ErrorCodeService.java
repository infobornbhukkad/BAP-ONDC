// 
// Decompiled by Procyon v0.5.36
// 

package com.bb.beckn.common.service;

import org.slf4j.LoggerFactory;
import com.bb.beckn.common.model.HttpModel;
import com.bb.beckn.common.model.ConfigModel;
import com.bb.beckn.api.enums.AckStatus;
import com.bb.beckn.api.model.common.Ack;
import com.bb.beckn.api.model.common.Error;
import com.bb.beckn.api.model.response.Response;
import com.bb.beckn.api.model.response.ResponseMessage;
import com.bb.beckn.common.model.ApiParamModel;
import org.apache.commons.lang3.StringUtils;
import com.bb.beckn.common.model.AuditModel;
import com.bb.beckn.common.util.AdaptorUtil;
import com.bb.beckn.search.extension.Schema;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.bb.beckn.common.sender.Sender;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class ErrorCodeService
{
    private static final Logger log;
    @Autowired
	private ObjectMapper mapper;
	
    public ResponseEntity<String> sendErrorMessage(Schema request, String message, String code, String desc) throws JsonProcessingException {
		final Response adaptorResponse = new Response();
		final ResponseMessage resMsg = new ResponseMessage();
		resMsg.setAck(new Ack(AckStatus.NACK));
		adaptorResponse.setMessage(resMsg);
		final Error error = new Error();
		error.setMessage(message);
		error.setCode(code);
		error.setDesc(desc);
		adaptorResponse.setContext(request.getContext());
		adaptorResponse.setError(error);
		return (ResponseEntity<String>)new ResponseEntity((Object)this.mapper.writeValueAsString((Object)adaptorResponse), HttpStatus.BAD_REQUEST);
	}
    
    static {
        log = LoggerFactory.getLogger((Class)ErrorCodeService.class);
    }
}
