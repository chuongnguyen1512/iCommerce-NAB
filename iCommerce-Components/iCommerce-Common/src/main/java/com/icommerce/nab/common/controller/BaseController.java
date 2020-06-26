package com.icommerce.nab.common.controller;

import com.icommerce.nab.common.restful.BaseCallRestfulService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.icommerce.nab.common.constant.ICommerceConstants.TRANSACTION_ID;

/**
 * Base restful controller class for wrap structure failure handling
 */
public abstract class BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);

    /**
     * Skeleton for processing restful request with error handling
     *
     * @param transactionId          transaction id
     * @param processRequestConsumer process request consumer
     * @return response entity object
     */
    public ResponseEntity processRestfulRequest(String transactionId, ProcessRequestConsumer processRequestConsumer) {
        try {
            MDC.put(TRANSACTION_ID, transactionId);
            if (processRequestConsumer.isInvalidRequest()) {
                return (ResponseEntity) ResponseEntity.badRequest();
            }
            return ResponseEntity.ok(processRequestConsumer.executeRequest());
        } catch (Exception e) {
            LOGGER.error("Cannot process restful request due to has an exception", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal error is happening. Please contact administrators for support");
        } finally {
            MDC.clear();
        }
    }

    /**
     * Interface for processing request
     */
    public interface ProcessRequestConsumer {
        /**
         * Verify request is valid or not
         *
         * @return true if request is invalid. Otherwise is false
         */
        boolean isInvalidRequest();

        /**
         * Executing request
         *
         * @return response object
         */
        Object executeRequest();
    }
}
