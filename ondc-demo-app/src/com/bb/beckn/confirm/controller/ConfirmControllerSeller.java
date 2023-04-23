// 
// Decompiled by Procyon v0.5.36
// 

package com.bb.beckn.confirm.controller;

import com.bb.beckn.init.controller.InitControllerSeller;
import org.slf4j.LoggerFactory;
import java.time.LocalDateTime;
import com.bb.beckn.common.model.AuditDataModel;
import com.bb.beckn.common.model.AuditFlagModel;
import com.bb.beckn.common.model.HttpModel;
import com.bb.beckn.common.model.AuditModel;
import org.springframework.web.bind.annotation.PostMapping;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.bb.beckn.common.model.ConfigModel;
import com.bb.beckn.api.model.common.Context;
import com.bb.beckn.api.model.lookup.LookupRequest;
import com.bb.beckn.common.enums.BecknUserType;
import com.bb.beckn.confirm.extension.Schema;
import com.bb.beckn.common.exception.ApplicationException;
import com.bb.beckn.common.exception.ErrorCode;
import com.bb.beckn.common.enums.OndcUserType;
import org.springframework.http.ResponseEntity;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Value;
import com.bb.beckn.common.service.AuditService;
import com.bb.beckn.common.service.ApplicationConfigService;
import com.bb.beckn.common.util.JsonUtil;
import com.bb.beckn.common.validator.HeaderValidator;
import org.springframework.beans.factory.annotation.Autowired;
import com.bb.beckn.confirm.service.ConfirmServiceSeller;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfirmControllerSeller
{
    private static final Logger log;
    @Autowired
    private ConfirmServiceSeller service;
    @Autowired
    private HeaderValidator validator;
    @Autowired
    private JsonUtil jsonUtil;
    @Autowired
    private ApplicationConfigService configService;
    @Autowired
    private AuditService auditService;
    @Value("${beckn.entity.type}")
    private String entityType;
    
    @PostMapping({ "/confirm" })
    public ResponseEntity<String> confirm(@RequestBody final String body, @RequestHeader final HttpHeaders httpHeaders, final HttpServletRequest servletRequest) throws JsonProcessingException {
        ConfirmControllerSeller.log.info("The body in {} adaptor is {}", (Object)"confirm", (Object)this.jsonUtil.unpretty(body));
        ConfirmControllerSeller.log.info("Entity type is {}", (Object)this.entityType);

        //Injecting remote client hostname to headers
        httpHeaders.add("remoteHost", servletRequest.getRemoteHost());
        ConfirmControllerSeller.log.info("Got call from " + servletRequest.getRemoteHost());

        if (!OndcUserType.SELLER.type().equalsIgnoreCase(this.entityType)) {
            throw new ApplicationException(ErrorCode.INVALID_ENTITY_TYPE);
        }
        final Schema model = (Schema)this.jsonUtil.toModel(body, (Class)Schema.class);
        final Context context = model.getContext();
        final String bapId = context.getBapId();
        final String bppId = context.getBppId();
        final ConfigModel configModel = this.configService.loadApplicationConfiguration(bppId, "confirm");
        final boolean authenticate = configModel.getMatchedApi().isHeaderAuthentication();
        ConfirmControllerSeller.log.info("does buyer {} requires to be authenticated ? {}", (Object)bapId, (Object)authenticate);
        if (authenticate) {
            final LookupRequest lookupRequest = new LookupRequest((String)null, context.getCountry(), context.getCity(), context.getDomain(), BecknUserType.BAP.type());
            this.validator.validateHeader(bppId, httpHeaders, body, lookupRequest);
        }
        this.auditService.audit(this.buildAuditModel(httpHeaders, body, model));
        return (ResponseEntity<String>)this.service.confirm(httpHeaders, model);
    }
    
    private AuditModel buildAuditModel(final HttpHeaders httpHeaders, final String body, final Schema model) {
        final AuditModel auditModel = new AuditModel();
        final HttpModel httpModel = new HttpModel();
        httpModel.setRequestHeaders(httpHeaders);
        httpModel.setRequestBody(body);
        final AuditFlagModel flagModel = new AuditFlagModel();
        flagModel.setHttp(false);
        flagModel.setFile(true);
        flagModel.setDatabase(true);
        auditModel.setApiName("confirm");
        auditModel.setSubscriberId(model.getContext().getBppId());
        auditModel.setAuditFlags(flagModel);
        auditModel.setDataModel(this.buildAuditDataModel(body, model));
        auditModel.setHttpModel(httpModel);
        return auditModel;
    }
    
    private AuditDataModel buildAuditDataModel(final String body, final Schema request) {
        final AuditDataModel model = new AuditDataModel();
        model.setAction(request.getContext().getAction());
        model.setCoreVersion(request.getContext().getCoreVersion());
        model.setDomain(request.getContext().getDomain());
        model.setTransactionId(request.getContext().getTransactionId());
        model.setMessageId(request.getContext().getMessageId());
        model.setCreatedOn(LocalDateTime.now());
        model.setJson(body);
        model.setStatus("N");
        return model;
    }
    
    static {
        log = LoggerFactory.getLogger((Class)ConfirmControllerSeller.class);
    }
}
