// 
// Decompiled by Procyon v0.5.36
// 

package com.bb.beckn.fetchResult.controller;

import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.bb.beckn.confirm.controller.ConfirmControllerBuyer;
import com.bb.beckn.confirm.extension.Schema;
import com.bb.beckn.common.exception.ApplicationException;
import com.bb.beckn.common.exception.ErrorCode;
import com.bb.beckn.common.enums.OndcUserType;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Value;
import com.bb.beckn.common.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import com.bb.beckn.confirm.service.ConfirmServiceBuyer;
import com.bb.beckn.repository.ApiAuditbuyerRepository;
import com.bb.beckn.search.model.ApiBuyerObj;

import org.hibernate.mapping.Map;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FetchResultControllerBuyer
{
        
	private static final Logger log;
	@Autowired
	ApiAuditbuyerRepository mapiAuditbuyerRepository;
	@Autowired
    private JsonUtil jsonUtil;
	List<ApiBuyerObj> apiBuyerObj_list= null;
	
    @PostMapping({ "/buyer/adaptor/fetch" })
    public ResponseEntity<List> fetch( @RequestBody  HashMap<String, String>   body)  {
    	
    	FetchResultControllerBuyer.log.info("The request in FetchResultControllerBuyer" ); 
    	String transactionId= body.get("transactionId");
    	String messageId= body.get("messageId");
    	String action=body.get("action");
    	
    	System.out.println("transactionId --" + transactionId);
    	System.out.println("messageId -- "+ messageId);
    	apiBuyerObj_list = new ArrayList<ApiBuyerObj>();
    	List<ApiBuyerObj> apiBuyerObj=null;
    	
        int retries = 3; // maximum number of retries
        while (retries > 0) {
                 try {
                     Thread.sleep(5000); // wait for 1 second before retrying
                 } catch (InterruptedException ex) {
                     ex.printStackTrace();
                 }
        	
                 System.out.println("retries --" + retries);
        	 retries--;
        }
        apiBuyerObj=mapiAuditbuyerRepository.findByTransactionidandMessageid(transactionId , messageId, action);
        
        if(apiBuyerObj.size() == 0) {
        	
        	System.out.println("record not found");
        }
        for(int i=0; i < apiBuyerObj.size(); i++) {
        	
        	apiBuyerObj_list.add(apiBuyerObj.get(i));
        }
        return (ResponseEntity<List>)new ResponseEntity((List)apiBuyerObj_list, HttpStatus.OK);
    }
    	
    
    
    static {
        log = LoggerFactory.getLogger((Class)FetchResultControllerBuyer.class);
    }
}
