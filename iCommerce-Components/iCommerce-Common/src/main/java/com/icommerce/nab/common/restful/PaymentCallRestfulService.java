package com.icommerce.nab.common.restful;

import com.google.gson.Gson;
import com.icommerce.nab.common.config.CallRestfulRetryConfig;
import com.icommerce.nab.dto.builder.RestfulRequestBuilder;
import com.icommerce.nab.dto.dto.OrderDTO;
import com.icommerce.nab.dto.restful.ICommerceRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.icommerce.nab.common.constant.ICommerceConstants.TRANSACTION_ID;

/**
 * Handling call Payment-Service Restful APIs
 */
public class PaymentCallRestfulService extends BaseCallRestfulService {

    public PaymentCallRestfulService(RestTemplate restTemplate, Gson gson, CallRestfulRetryConfig retryConfig, String host) {
        super(restTemplate, gson, retryConfig, host);
    }

    /**
     * Preparing for request for API '/deliverOrders'
     *
     * @param transactionId transaction id
     * @param orderDTOS     list of order dto objects
     * @return http request
     */
    public HttpEntity prepareRequestForCashOrders(String transactionId, List<OrderDTO> orderDTOS) {
        LinkedMultiValueMap<String, String> headers = new LinkedMultiValueMap();
        headers.set(TRANSACTION_ID, transactionId);
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        ICommerceRequest iCommerceRequest = RestfulRequestBuilder.buildICommerceRequest(transactionId, orderDTOS);

        String bodyRequest = gson.toJson(iCommerceRequest, ICommerceRequest.class);
        return new HttpEntity(bodyRequest, headers);
    }
}
