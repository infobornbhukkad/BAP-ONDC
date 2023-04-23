// 
// Decompiled by Procyon v0.5.36
// 

package com.bb.beckn.common.service;

import org.slf4j.LoggerFactory;
import com.bb.beckn.common.model.SigningModel;
import com.bb.beckn.common.model.ApiParamModel;
import org.springframework.cache.annotation.Cacheable;
import java.io.IOException;
import com.fasterxml.jackson.databind.JavaType;
import java.util.List;
import com.bb.beckn.common.model.ConfigModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class ApplicationConfigService
{
    private static final Logger log;
    @Autowired
    @Value("classpath:adaptor-config-${beckn.entity.type}.json")
    private Resource resource;
    @Autowired
    private ObjectMapper mapper;
    
    @Cacheable({ "beckn-api-cache-common" })
    public ConfigModel loadApplicationConfiguration(final String subscriberId, final String apiName) {
        ApplicationConfigService.log.info("going to load the configuration for subscriberid: {} and api: {}", (Object)subscriberId, (Object)apiName);
        try {
            final List<ConfigModel> configModels = (List<ConfigModel>)this.mapper.readValue(this.resource.getInputStream(), (JavaType)this.mapper.getTypeFactory().constructCollectionType((Class)List.class, (Class)ConfigModel.class));
            ConfigModel configModel = configModels.get(0);
            /*
             * Always respond with the default configmodel for the app
             * 
            if("".equals(subscriberId)) {
            	ApplicationConfigService.log.info("Getting the default config model since subscriberid is empty");
            	configModel=configModels.get(0);
            }else {
            	configModel = this.findConfigById(subscriberId, configModels);
            }
            */
            configModel.setMatchedApi(this.findMatchingApi(configModel, apiName));
            ApplicationConfigService.log.info("The content of init config file is {}", (Object)configModel);
            return configModel;
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Exception while loading the config json file");
        }
    }
    
    private ConfigModel findConfigById(final String subscriberId, final List<ConfigModel> configModels) {
        final ConfigModel configModel = configModels.stream().filter(model -> model.getSubscriberId().equalsIgnoreCase(subscriberId)).findFirst().orElseThrow(() -> {
            return new RuntimeException("not able to find the subscriberId [" + subscriberId + "] in the config json file");
        });
        ApplicationConfigService.log.info("the matched config model for the id {} is  {}", (Object)subscriberId, (Object)configModel);
        return configModel;
    }
    
    @Cacheable({ "beckn-api-cache-common" })
    public ConfigModel loadApplicationConfiguration1(final String apiName) {
        ConfigModel configModel = null;
        try {
            configModel = (ConfigModel)this.mapper.readValue(this.resource.getInputStream(), (Class)ConfigModel.class);
            configModel.setMatchedApi(this.findMatchingApi(configModel, apiName));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        ApplicationConfigService.log.info("The content of init config file is {}", (Object)configModel);
        return configModel;
    }
    
    private ApiParamModel findMatchingApi(final ConfigModel configModel, final String apiName) {
        return configModel.getApi().stream().filter(model -> apiName.equalsIgnoreCase(model.getName())).findFirst().orElseThrow(() -> {
            throw new RuntimeException("Invalid API name configured. Please configure json file correctly");
        });
    }
    
    @Cacheable({ "beckn-api-cache-common" })
    public SigningModel getSigningConfiguration(final String subscriberId) {
        try {
            final List<ConfigModel> configModels = (List<ConfigModel>)this.mapper.readValue(this.resource.getInputStream(), (JavaType)this.mapper.getTypeFactory().constructCollectionType((Class)List.class, (Class)ConfigModel.class));
            final ConfigModel configModel = this.findConfigById(subscriberId, configModels);
            ApplicationConfigService.log.info("The content of init config file is {}", (Object)configModel);
            return configModel.getSigning();
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Exception while loading the config json file");
        }
    }
    
    static {
        log = LoggerFactory.getLogger((Class)ApplicationConfigService.class);
    }
}
