// 
// Decompiled by Procyon v0.5.36
// 

package com.bb.beckn.common.exception;

import org.slf4j.LoggerFactory;
import java.util.List;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.slf4j.Logger;

public class WebClientFilter
{
    private static final Logger log;
    
    private WebClientFilter() {
    }
    
    public static ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(request -> {
            logMethodAndUrl(request);
            logHeaders(request);
            return Mono.just(request);
        });
    }
    
    public static ExchangeFilterFunction logResponse() {
        return ExchangeFilterFunction.ofResponseProcessor(response -> {
            logStatus(response);
            logHeaders(response);
            return logBody(response);
        });
    }
    
    private static void logStatus(final ClientResponse response) {
        final HttpStatus status = response.statusCode();
        WebClientFilter.log.debug("Returned staus code {} ({})", (Object)status.value(), (Object)status.getReasonPhrase());
    }
    
    private static Mono<ClientResponse> logBody(final ClientResponse response) {
        WebClientFilter.log.info("In logBody with error statusCode {}", (Object)response.statusCode());
        if (response.statusCode() == null || (!response.statusCode().is4xxClientError() && !response.statusCode().is5xxServerError())) {
            return (Mono<ClientResponse>)Mono.just(response);
        }
        return (Mono<ClientResponse>)response.bodyToMono((Class)String.class).flatMap(body -> {
            WebClientFilter.log.debug("Body is {}", (Object)body);
            return Mono.error((Throwable)new ApplicationException(response.rawStatusCode(), (String)body));
        });
    }
    
    private static void logHeaders(final ClientResponse response) {
        response.headers().asHttpHeaders().forEach((name, values) -> values.forEach(value -> logNameAndValuePair(name, value)));
    }
    
    private static void logHeaders(final ClientRequest request) {
        request.headers().forEach((name, values) -> values.forEach(value -> logNameAndValuePair(name, value)));
    }
    
    private static void logNameAndValuePair(final String name, final String value) {
        WebClientFilter.log.debug("{}={}", (Object)name, (Object)value);
    }
    
    private static void logMethodAndUrl(final ClientRequest request) {
        final StringBuilder sb = new StringBuilder();
        sb.append(request.method().name());
        sb.append(" to ");
        sb.append(request.url());
        WebClientFilter.log.debug(sb.toString());
    }
    
    static {
        log = LoggerFactory.getLogger((Class)WebClientFilter.class);
    }
}
