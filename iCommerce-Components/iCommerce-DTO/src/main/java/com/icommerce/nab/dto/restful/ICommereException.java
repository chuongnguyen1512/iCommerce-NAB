package com.icommerce.nab.dto.restful;

/**
 * Wrapper exception for iCommerce common error
 */
public class ICommereException extends RuntimeException {

    public ICommereException() {
        super();
    }

    public ICommereException(String message) {
        super(message);
    }

    public ICommereException(String message, Throwable cause) {
        super(message, cause);
    }
}
