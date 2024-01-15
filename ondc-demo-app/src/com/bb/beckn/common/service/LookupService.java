// 
// Decompiled by Procyon v0.5.36
// 

package com.bb.beckn.common.service;

import org.slf4j.LoggerFactory;
import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import com.bb.beckn.common.model.ApiParamModel;
import com.bb.beckn.common.model.ConfigModel;
import org.springframework.http.HttpHeaders;
import java.util.ArrayList;
import java.util.List;
import com.bb.beckn.api.model.lookup.LookupResponse;
import com.bb.beckn.api.model.lookup.LookupRequest;
import com.bb.beckn.common.cache.CachingService;
import com.bb.beckn.common.util.JsonUtil;
import com.bb.beckn.common.builder.HeaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import com.bb.beckn.common.sender.Sender;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class LookupService
{
    private static final Logger log;
    @Autowired
    private Sender sender;
    @Autowired
    private ApplicationConfigService appConfigService;
    @Autowired
    private HeaderBuilder authHeaderBuilder;
    @Autowired
    private JsonUtil jsonUtil;
    @Autowired
    private CachingService cachingService;
    private static final String API_NAME = "lookup";
    private static final String DATE_FORMAT_TZ = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    
    public LookupResponse getProvidersByKeyId(final String subscriberId, final String keyId, final LookupRequest request) {
        LookupService.log.info("finding matching provider for keyid {}", (Object)keyId);
        final LookupResponse fromCache = (LookupResponse)this.cachingService.getFromCache("beckn-api-cache-lookup", keyId);
        if (fromCache != null && this.isCacheValid(fromCache)) {
            //LookupService.log.info("data found in cache. no lookup call will be done");
            return fromCache;
        }
        final List<LookupResponse> list = this.lookup(subscriberId, request);
        //LookupService.log.info("lookup response list is {}", (Object)list);
        this.putInCache(list);
        final LookupResponse lookupResponse = list.stream().filter(response -> (response.getSubscriberId() + "|" + response.getUniqueKeyId()).equalsIgnoreCase(keyId)).findAny().orElse(null);
        LookupService.log.info("The matching provider for key id {} is {}", (Object)keyId, (Object)lookupResponse);
        return lookupResponse;
    }
    
    public List<LookupResponse> lookup(final String entityId, final LookupRequest request) {
        List<LookupResponse> lookupList = new ArrayList<LookupResponse>();
        final String response = this.lookupJson(entityId, request);
        if (response != null) {
            lookupList = this.jsonUtil.toModelList(response, LookupResponse.class);
        }
        return lookupList;
    }
    
    public String lookupJson(final String subscriberId, final LookupRequest request) {
        final ConfigModel configModel = this.appConfigService.loadApplicationConfiguration(subscriberId, "lookup");
        final ApiParamModel matchedApiModel = configModel.getMatchedApi();
        final String url = matchedApiModel.getHttpEntityEndpoint();
        final String json = this.jsonUtil.toJson(request);
        //LookupService.log.info("calling the lookup to url: {}", (Object)url);
        LookupService.log.info("final json to be send {}", (Object)json);
        HttpHeaders headers = new HttpHeaders();
        if (matchedApiModel.isSetAuthorizationHeader()) {
            headers = this.authHeaderBuilder.buildHeaders(json, configModel);
        }
        final String response = this.sender.send(url, headers, json, configModel.getMatchedApi());
        //LookupService.log.info("lookup response json is {}", (Object)response);
        return response;
    }
    
    private void putInCache(final List<LookupResponse> list) {
        list.stream().forEach(response -> {
            String key = response.getSubscriberId() + "|" + response.getUniqueKeyId();
            this.cachingService.putToCache("beckn-api-cache-lookup", key, response);
        });
    }
    
    private boolean isCacheValid(final LookupResponse fromCache) {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        final Date currentDate = new Date();
        Date validFrom = null;
        Date validUntil = null;
        try {
            validFrom = sdf.parse(fromCache.getValidFrom());
            validUntil = sdf.parse(fromCache.getValidUntil());
        }
        catch (ParseException e) {
            throw new RuntimeException(e);
        }
        LookupService.log.info("ValidFrom: {} & ValidUntil: {}", (Object)validFrom, (Object)validUntil);
        final boolean isValidFrom = validFrom.before(currentDate);
        final boolean isValidUntil = validUntil.after(currentDate);
        LookupService.log.info("isValidFrom: {} & isValidUntil: {}", (Object)isValidFrom, (Object)isValidUntil);
        return isValidFrom && isValidUntil;
    }
    
    static {
        log = LoggerFactory.getLogger((Class)LookupService.class);
    }
}
