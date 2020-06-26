package com.icommerce.nab.deliver.service.controller;

import com.icommerce.nab.common.controller.BaseController;
import com.icommerce.nab.deliver.service.service.DefaultDeliverService;
import com.icommerce.nab.dto.dto.OrderDTO;
import com.icommerce.nab.dto.restful.ICommerceRequest;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * Restful controller for handling deliver http request
 */
@RestController
public class DeliverController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeliverController.class);

    private DefaultDeliverService deliverService;

    @Autowired
    public DeliverController(@Autowired DefaultDeliverService deliverService) {
        this.deliverService = deliverService;
    }

    /**
     * Processing restful requests for url '/deliverOrders'
     *
     * @param transactionId transaction id
     * @param request deliver request
     * @return response entity object
     */
    @RequestMapping(value = "/deliverOrders", method = RequestMethod.POST)
    public ResponseEntity deliverOrders(@RequestHeader(name = "transaction_id") String transactionId,
                                      @RequestBody ICommerceRequest<OrderDTO> request) {

        return processRestfulRequest(transactionId, new BaseController.ProcessRequestConsumer() {
            @Override
            public boolean isInvalidRequest() {
                return Objects.isNull(request) || CollectionUtils.isEmpty(request.getData());
            }

            @Override
            public Object executeRequest() {
                LOGGER.info("Receiving deliver orders request with orders size: {}", request.getData().size());
                return deliverService.deliverOrders(transactionId, request.getData());
            }
        });
    }
}
