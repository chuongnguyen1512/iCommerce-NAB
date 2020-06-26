package com.icommerce.nab.common.restful;

import com.google.gson.Gson;
import com.icommerce.nab.common.config.CallRestfulRetryConfig;
import com.icommerce.nab.dto.restful.ICommereException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Abstract class for handling call Restful APIs
 */
public abstract class BaseCallRestfulService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseCallRestfulService.class);

    protected CallRestfulRetryConfig retryConfig;
    protected RestTemplate restTemplate;
    protected Gson gson;
    protected String host;

    protected BaseCallRestfulService(RestTemplate restTemplate, Gson gson, CallRestfulRetryConfig retryConfig, String host) {
        this.restTemplate = restTemplate;
        this.gson = gson;
        this.retryConfig = retryConfig;
        this.host = host;
    }

    /**
     * Executing POST request
     *
     * @param url     url
     * @param request http request
     * @return http response
     */
    public ResponseEntity executePOSTRequest(String url, HttpEntity<String> request) {
        if (Objects.isNull(url) || Objects.isNull(request)) {
            throw new IllegalArgumentException("Input params for executing POST request are not valid");
        }

        AtomicReference<ResponseEntity> response = new AtomicReference();
        executeRequestWithRetry(url, HttpMethod.POST, () -> {
            ResponseEntity responseEntity = restTemplate.exchange(buildFullURI(url), HttpMethod.POST, request, Object.class);
            if (Objects.nonNull(responseEntity)) {
                response.set(responseEntity);
            }
        });

        if (Objects.isNull(response)) {
            return null;
        }
        return response.get();
    }

    /**
     * Execute calling Restful request with retry mechanism
     *
     * @param url            url
     * @param httpMethod     type of http method
     * @param executeRequest interface class using to implement execute logic
     */
    private void executeRequestWithRetry(String url, HttpMethod httpMethod, ExecuteRequest executeRequest) {
        int count = 0;
        while (count < retryConfig.getMaxRetry()) {
            try {
                executeRequest.execute();
                break;
            } catch (Exception e) {
                LOGGER.warn("Failed to execute {} request for url: {} with retry counter: {}", httpMethod.name(), url, count);
                if (count == retryConfig.getMaxRetry()) {
                    String errorMsg = String.format("Failed to execute %s request for url: %s", httpMethod.name(), url);
                    LOGGER.error(errorMsg, e);
                    throw new ICommereException(errorMsg, e);
                }

                try {
                    Thread.sleep(retryConfig.getWaitingRetry());
                } catch (InterruptedException ex) {
                    LOGGER.error("Cannot waiting {} for next retry due to thread is interrupted", retryConfig.getWaitingRetry(), e);
                }
            }
        }
    }

    /**
     * Build URI
     *
     * @param url url
     * @return full URI
     */
    private URI buildFullURI(String url) {
        String trimmedHost = StringUtils.trimWhitespace(host);
        String trimmedUrl = StringUtils.trimWhitespace(url);
        return URI.create(trimmedHost + trimmedUrl);
    }

    /**
     * Interface using to implement execute http requests logic
     */
    public interface ExecuteRequest {
        /**
         * Execute http requests logic
         */
        void execute();
    }
}
