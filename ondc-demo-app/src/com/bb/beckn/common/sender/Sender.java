// 
// Decompiled by Procyon v0.5.36
// 

package com.bb.beckn.common.sender;

import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.util.retry.RetryBackoffSpec;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import com.bb.beckn.common.exception.ErrorCode;
import java.util.concurrent.TimeoutException;
import reactor.util.retry.Retry;
import java.time.Duration;
import com.bb.beckn.common.exception.ApplicationException;
import org.springframework.http.HttpStatus;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import com.bb.beckn.common.model.ApiParamModel;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class Sender
{
    private static final Logger log;
    private final WebClient webClient;
    
    public Sender(final WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }
    
    public String send(final String url, final HttpHeaders headers, final String json, final ApiParamModel apiModel) {
        int timeout = 30000;
        int retryCount = 0;
        if (apiModel != null) {
            timeout = apiModel.getHttpTimeout();
            retryCount = apiModel.getHttpRetryCount();
        }
        Sender.log.info("making post request to url {} with timeout {} ms and retryCount {}", new Object[] { url, timeout, retryCount });
        final Mono<String> response = (Mono<String>)((WebClient.RequestBodySpec)((WebClient.RequestBodySpec)this.webClient.post().uri(url, new Object[0])).headers(h -> h.addAll((MultiValueMap)headers))).contentType(MediaType.APPLICATION_JSON).body((Publisher)Mono.just((Object)json), (Class)String.class).retrieve().onStatus(status -> {
            Sender.log.info("The http response code is {}", (Object)status);
            return status.compareTo(HttpStatus.REQUEST_TIMEOUT) == 0;
        }, res -> {
        	int rawStatusCode = res.rawStatusCode();
            Sender.log.error("Error has occured. status code is: {} and reason phrase is {}", (Object)rawStatusCode, (Object)res.statusCode().getReasonPhrase());
            return Mono.error((Throwable)new ApplicationException(rawStatusCode, res.statusCode().getReasonPhrase()));
        }).bodyToMono((Class)String.class).timeout(Duration.ofMillis(timeout)).retryWhen((Retry)Retry.backoff((long)retryCount, Duration.ofSeconds(1L)).doAfterRetry(retrySignal -> Sender.log.info("Retried " + retrySignal.totalRetries())).filter(throwable -> {
            Sender.log.error("Excpetion has occured while sending request: {}", (Object)throwable.getMessage());
            return throwable instanceof TimeoutException;
        }).onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> {
            Sender.log.error("All retry exhaused");
            return new ApplicationException(ErrorCode.HTTP_TIMEOUT_ERROR);
        }));
        final String responseData = (String)response.block();
        Sender.log.info("response from post call: {}", (Object)responseData);
        return responseData;
    }
    
    public String send1(final String url, final HttpHeaders headers, final String json, final ApiParamModel apiModel) {
        int timeout = 30000;
        int retryCount = 0;
        if (apiModel != null) {
            timeout = apiModel.getHttpTimeout();
            retryCount = apiModel.getHttpRetryCount();
        }
        Sender.log.info("making post request to url {} with timeout {} ms and retryCount {}", new Object[] { url, timeout, retryCount });
        try {
            final Mono<String> response = (Mono<String>)((WebClient.RequestBodySpec)((WebClient.RequestBodySpec)this.webClient.post().uri(url, new Object[0])).headers(h -> h.addAll((MultiValueMap)headers))).contentType(MediaType.APPLICATION_JSON).body((Publisher)Mono.just((Object)json), (Class)String.class).retrieve().onStatus(status -> {
                Sender.log.info("The http response code is {}", (Object)status);
                return status.compareTo(HttpStatus.REQUEST_TIMEOUT) == 0;
            }, res -> {
                int rawStatusCode = res.rawStatusCode();
                Sender.log.error("Error has occured. status code is: {} and reason phrase is {}", (Object)rawStatusCode, (Object)res.statusCode().getReasonPhrase());
                return Mono.error((Throwable)new ApplicationException(rawStatusCode, res.statusCode().getReasonPhrase()));
            }).bodyToMono((Class)String.class).timeout(Duration.ofMillis(timeout)).retryWhen((Retry)Retry.backoff((long)retryCount, Duration.ofSeconds(1L)).filter(throwable -> {
                Sender.log.error("Exception has occured while sending request: {}", (Object)throwable.getMessage());
                return throwable instanceof TimeoutException;
            }).onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> {
                Sender.log.error("All retry exhaused");
                return new TimeoutException();
            }));
            final String responseData = (String)response.block();
            Sender.log.info("response from post call: {}", (Object)responseData);
            return responseData;
        }
        catch (WebClientResponseException we) {
            Sender.log.error("WebClientResponseException while calling the post at url {}", (Object)url);
            Sender.log.error("WebClientResponseException is {}", (Object)we.getMessage());
            throw new ApplicationException(we.getRawStatusCode(), we.getMessage());
        }
        catch (WebClientRequestException e) {
            Sender.log.error("WebClientRequestException while calling the post at url {}", (Object)url);
            Sender.log.error("WebClientRequestException is {}", (Object)e.getMessage());
            throw new ApplicationException(ErrorCode.NETWORK_ERROR, e.getMessage());
        }
        catch (Exception e2) {
            Sender.log.error("Exception while calling the post at url {}", (Object)url);
            Sender.log.error("Exception is {}", (Object)e2.getMessage());
            throw new ApplicationException(ErrorCode.NETWORK_ERROR, e2.getMessage());
        }
    }
    
    static {
        log = LoggerFactory.getLogger((Class)Sender.class);
    }
}
