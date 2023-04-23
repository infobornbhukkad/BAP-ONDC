// 
// Decompiled by Procyon v0.5.36
// 

package com.bb.beckn.status.controller;

import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.bb.beckn.common.model.ConfigModel;
import org.springframework.http.HttpStatus;
import com.bb.beckn.common.model.ApiParamModel;
import com.bb.beckn.status.extension.OnSchema;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestBody;
import com.bb.beckn.common.builder.HeaderBuilder;
import com.bb.beckn.common.service.ApplicationConfigService;
import com.bb.beckn.common.sender.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import com.bb.beckn.common.util.JsonUtil;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OnStatusControllerSeller
{
    private static final Logger log;
    @Autowired
    private JsonUtil jsonUtil;
    @Autowired
    private Sender sender;
    @Autowired
    private ApplicationConfigService configService;
    @Autowired
    private HeaderBuilder authHeaderBuilder;
    
    @PostMapping({ "/seller/adaptor/on_status" })
    public ResponseEntity<String> onStatus(@RequestBody final String body, @RequestHeader final HttpHeaders httpHeaders) throws JsonProcessingException {
        OnStatusControllerSeller.log.info("The body in {} adaptor is {}", (Object)"status", (Object)this.jsonUtil.unpretty(body));
        try {
            final OnSchema model = (OnSchema)this.jsonUtil.toModel(body, (Class)OnSchema.class);
            final String bapUrl = model.getContext().getBapUri();
            final ConfigModel appConfigModel = this.configService.loadApplicationConfiguration(model.getContext().getBppId(), "status");
            final HttpHeaders headers = this.authHeaderBuilder.buildHeaders(body, appConfigModel);
            OnStatusControllerSeller.log.info("response in seller controller[which is will now send back to buyer adaptor] is {}", (Object)model.toString());
            this.sender.send(bapUrl, headers, body, (ApiParamModel)null);
        }
        catch (Exception e) {
            OnStatusControllerSeller.log.error("Error while sending request back to buyer adaptor:", (Throwable)e);
            return (ResponseEntity<String>)new ResponseEntity((Object)"Error while sending request back to buyer adaptor", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return (ResponseEntity<String>)new ResponseEntity((Object)"Response received. All Ok", HttpStatus.OK);
    }
    
    static {
        log = LoggerFactory.getLogger((Class)OnStatusControllerSeller.class);
    }
}
