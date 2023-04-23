// 
// Decompiled by Procyon v0.5.36
// 

package com.bb.beckn.common.util;

import org.slf4j.LoggerFactory;
import java.util.function.Function;
import java.util.stream.Stream;
import com.fasterxml.jackson.databind.JavaType;
import java.util.List;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.bb.beckn.common.exception.ApplicationException;
import com.bb.beckn.common.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Autowired;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class JsonUtil
{
    private static final Logger log;
    @Autowired
    @Qualifier("snakeCaseObjectMapper")
    private ObjectMapper mapper;
    @Autowired
    private AdaptorUtil adaptorUtil;
    @Value("${beckn.persistence.audit-schema-error}")
    private boolean auditSchemaError;
    
    public String toJson(final Object request) {
        try {
            return this.mapper.writeValueAsString(request);
        }
        catch (JsonProcessingException e) {
            JsonUtil.log.error("error while building json" + e);
            throw new ApplicationException(ErrorCode.JSON_PROCESSING_ERROR);
        }
    }
    
    public <schemaClass> schemaClass toModel(final String body, final Class<?> schemaClass) {
        try {
            final schemaClass model = (schemaClass)this.mapper.readValue(body, (Class)schemaClass);
            JsonUtil.log.debug("The {} model is {}", (Object)schemaClass, (Object)model);
            return model;
        }
        catch (JsonProcessingException e) {
            JsonUtil.log.error("Error while json processing for {}. Not a valid json {}", (Object)schemaClass, e.getMessage());
            throw new ApplicationException(ErrorCode.INVALID_REQUEST);
        }
    }
    
    public <schemaClass> schemaClass toModelList(final String body, final Class<?> schemaClass) {
        try {
            final schemaClass model = (schemaClass)this.mapper.readValue(body, (JavaType)this.mapper.getTypeFactory().constructCollectionType((Class)List.class, (Class)schemaClass));
            JsonUtil.log.debug("The {} model list is {}", (Object)schemaClass, (Object)model);
            return model;
        }
        catch (JsonProcessingException e) {
            JsonUtil.log.error("Error while json processing for {}. Not a valid json {}", (Object)schemaClass, (Object)e);
            throw new ApplicationException(ErrorCode.INVALID_REQUEST);
        }
    }
    
    public String unpretty(final String json) {
        try {
            final String[] lines = json.split("\n");
            return json;
            //Stream.of(lines).map((Function<? super String, ? extends String>)String::trim).reduce(String::concat).orElseThrow();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    static {
        log = LoggerFactory.getLogger((Class)JsonUtil.class);
    }
}
