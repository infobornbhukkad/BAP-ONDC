// 
// Decompiled by Procyon v0.5.36
// 

package com.bb.beckn.common.config;

import org.springframework.beans.factory.annotation.Qualifier;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class AppConfig
{
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    
    @Bean
    @Qualifier("snakeCaseObjectMapper")
    public ObjectMapper jacksonObjectMapper() {
        return new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE).setSerializationInclusion(JsonInclude.Include.NON_NULL).findAndRegisterModules();
    }
}
