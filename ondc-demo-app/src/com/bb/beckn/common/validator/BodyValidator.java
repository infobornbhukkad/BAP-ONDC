// 
// Decompiled by Procyon v0.5.36
// 

package com.bb.beckn.common.validator;

import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.bb.beckn.common.exception.ErrorCode;
import com.bb.beckn.api.model.common.Error;
import com.bb.beckn.api.model.common.Ack;
import com.bb.beckn.api.enums.AckStatus;
import com.bb.beckn.api.model.response.ResponseMessage;
import com.bb.beckn.api.model.response.Response;
import com.bb.beckn.api.model.common.Context;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class BodyValidator
{
    private static final Logger log;
    
    public Response validateRequestBody(final Context ctx, final String actionType) throws JsonProcessingException {
        final Response adaptorResponse = new Response();
        final ResponseMessage resMsg = new ResponseMessage();
        resMsg.setAck(new Ack(AckStatus.NACK));
        adaptorResponse.setMessage(resMsg);
        final Error error = new Error();
        error.setCode(ErrorCode.INVALID_REQUEST.toString());
        adaptorResponse.setContext(ctx);
        adaptorResponse.setError(error);
        adaptorResponse.setContext(ctx);
        if (ctx == null) {
            error.setMessage("Invalid Context");
            return adaptorResponse;
        }
        if (ctx.getTransactionId() == null) {
            error.setMessage("Invalid TransactionId");
            return adaptorResponse;
        }
        if (ctx.getDomain() == null) {
            error.setMessage("Invalid Domain");
            return adaptorResponse;
        }
        if (!actionType.equalsIgnoreCase(ctx.getAction())) {
            error.setMessage("Invalid Action");
            return adaptorResponse;
        }
        BodyValidator.log.info("body validation of the request is ok");
        return null;
    }
    
    static {
        log = LoggerFactory.getLogger((Class)BodyValidator.class);
    }
}
