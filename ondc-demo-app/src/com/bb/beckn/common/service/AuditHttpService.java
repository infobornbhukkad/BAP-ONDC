// 
// Decompiled by Procyon v0.5.36
// 

package com.bb.beckn.common.service;

import org.slf4j.LoggerFactory;
import com.bb.beckn.common.model.HttpModel;
import com.bb.beckn.common.model.ConfigModel;
import com.bb.beckn.common.model.ApiParamModel;
import org.apache.commons.lang3.StringUtils;
import com.bb.beckn.common.model.AuditModel;
import com.bb.beckn.common.util.AdaptorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import com.bb.beckn.common.sender.Sender;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class AuditHttpService
{
    private static final Logger log;
    @Autowired
    private Sender sender;
    @Autowired
    private ApplicationConfigService appConfigService;
    @Autowired
    private AdaptorUtil adaptorUtil;
    
    public void doPost(final AuditModel auditModel) {
        AuditHttpService.log.info("in doPost of AuditHttpService...");
        final ConfigModel configuration = this.appConfigService.loadApplicationConfiguration(auditModel.getSubscriberId(), auditModel.getApiName());
        final String bapApiUrl = configuration.getMatchedApi().getHttpEntityEndpoint();
        if (this.adaptorUtil.isHttpPersistanceConfigured() && StringUtils.isNotBlank((CharSequence)bapApiUrl)) {
            final HttpModel httpModel = auditModel.getHttpModel();
            AuditHttpService.log.info("sending the http response back to buyer internal api {}", (Object)bapApiUrl);
            this.sender.send(bapApiUrl, httpModel.getRequestHeaders(), httpModel.getRequestBody(), null);
        }
    }
    
    static {
        log = LoggerFactory.getLogger((Class)AuditHttpService.class);
    }
}
