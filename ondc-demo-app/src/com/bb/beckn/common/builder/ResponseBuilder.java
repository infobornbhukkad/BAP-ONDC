// 
// Decompiled by Procyon v0.5.36
// 

package com.bb.beckn.common.builder;

import org.slf4j.LoggerFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpHeaders;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.bb.beckn.api.enums.AckStatus;
import com.bb.beckn.api.model.common.Ack;
import com.bb.beckn.api.model.common.Error;
import com.bb.beckn.api.model.response.ResponseMessage;
import com.bb.beckn.api.model.response.Response;
import com.bb.beckn.common.exception.ApplicationException;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class ResponseBuilder
{
    private static final Logger log;
    
    public String buildValidResponse() {
        return "";
    }
    
    public String buildErrorResponse(final ApplicationException ae) throws JsonProcessingException {
        final Response response = new Response();
        final ResponseMessage message = new ResponseMessage();
        final Error error = new Error();
        final Ack ack = new Ack();
        ack.setStatus(AckStatus.NACK);
        message.setAck(ack);
        response.setMessage(message);
        error.setCode(ae.getErrorCode().toString());
        error.setMessage(ae.getMessage());
        response.setError(error);
        final ObjectMapper mapper = new ObjectMapper();
        final String header = "(created) (expires) digest";
        final HttpHeaders headers = new HttpHeaders();
        headers.set("WWW-Authenticate", "Signature realm=\"ed25519\",headers=\"" + header + "\"");
        return mapper.writeValueAsString((Object)response);
    }
    
    public ResponseEntity buildErrorResponse1() {
        final String errorMessage = "error has occured";
        final Response response = new Response();
        final Ack ack = new Ack();
        final ResponseMessage message = new ResponseMessage();
        final Error error = new Error();
        ack.setStatus(AckStatus.NACK);
        message.setAck(ack);
        response.setMessage(message);
        error.setCode(errorMessage);
        error.setMessage("Unauthorized Request.");
        response.setError(error);
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String header = "(created) (expires) digest";
            final HttpHeaders headers = new HttpHeaders();
            headers.set("WWW-Authenticate", "Signature realm=\"ed25519\",headers=\"" + header + "\"");
            final String json = mapper.writeValueAsString((Object)response);
            return new ResponseEntity((Object)json, (MultiValueMap)headers, HttpStatus.UNAUTHORIZED);
        }
        catch (JsonProcessingException e) {
            ResponseBuilder.log.error("Couldn't serialize response for content type application/json", (Throwable)e);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    static {
        log = LoggerFactory.getLogger((Class)ResponseBuilder.class);
    }
}
