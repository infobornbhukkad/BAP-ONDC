// 
// Decompiled by Procyon v0.5.36
// 

package com.bb.beckn.common.service;

import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.OpenOption;
import java.nio.file.Files;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.Paths;
import java.time.LocalDate;
import com.bb.beckn.common.model.AuditDataModel;
import org.springframework.beans.factory.annotation.Value;
import java.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class AuditFileService
{
    private static final Logger log;
    private static final DateTimeFormatter FORMATTER;
    @Value("${beckn.persistence.file-path}")
    private String filePath;
    
    public void fileAudit(final AuditDataModel dataModel) throws IOException {
        final String fileName = dataModel.getAction().toLowerCase() + "_" + dataModel.getMessageId() + "_" + System.currentTimeMillis();
        final byte[] strToBytes = dataModel.getJson().getBytes();
        final LocalDate now = LocalDate.now();
        final String folderName = now.format(AuditFileService.FORMATTER).toUpperCase();
        final StringBuilder sb = new StringBuilder();
        final StringBuilder file = sb.append(this.filePath).append(folderName).append("/").append(fileName).append(".json");
        final Path path = Paths.get(file.toString(), new String[0]);
        Files.createDirectories(path.getParent(), (FileAttribute<?>[])new FileAttribute[0]);
        Files.createFile(path, (FileAttribute<?>[])new FileAttribute[0]);
        Files.write(path, strToBytes, new OpenOption[0]);
        AuditFileService.log.info("file create at path {}", (Object)file);
    }
    
    static {
        log = LoggerFactory.getLogger((Class)AuditFileService.class);
        FORMATTER = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
    }
}
