// 
// Decompiled by Procyon v0.5.36
// 

package com.bb.beckn.common.service;

import org.slf4j.LoggerFactory;
import com.bb.beckn.common.model.AuditDataModel;
import java.io.IOException;
import com.bb.beckn.common.model.AuditModel;
import org.springframework.beans.factory.annotation.Autowired;
import com.bb.beckn.common.util.AdaptorUtil;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class AuditService
{
    private static final Logger log;
    @Autowired
    private AdaptorUtil adaptorUtil;
    @Autowired
    private AuditFileService auditFileService;
    @Autowired
    private AuditHttpService auditHttpService;
    
    public void audit(final AuditModel auditModel) {
        final boolean isDbConfigured = this.adaptorUtil.isDataBasePersistanceConfigured();
        final boolean isFileAudit = this.adaptorUtil.isFilePersistanceConfigured();
        if (auditModel.getAuditFlags().isHttp()) {
            this.auditHttpService.doPost(auditModel);
        }
        final AuditDataModel dataModel = auditModel.getDataModel();
        if (auditModel.getAuditFlags().isDatabase() && isDbConfigured) {
            AuditService.log.warn("no jpa available in this version of jar. please use correct version");
        }
        if (auditModel.getAuditFlags().isFile() && isFileAudit) {
            try {
                this.auditFileService.fileAudit(dataModel);
            }
            catch (IOException e) {
                AuditService.log.error("file creating failed {}", (Throwable)e);
            }
        }
    }
    
    static {
        log = LoggerFactory.getLogger((Class)AuditService.class);
    }
}
