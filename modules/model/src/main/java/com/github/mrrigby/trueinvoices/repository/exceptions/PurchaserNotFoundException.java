package com.github.mrrigby.trueinvoices.repository.exceptions;

/**
 * Not very inventive... exception that informs that na purchaser can be found.
 *
 * @author MrRigby
 */
public class PurchaserNotFoundException extends RuntimeException {

    public PurchaserNotFoundException() {
    }

    public PurchaserNotFoundException(String message) {
        super(message);
    }

    public PurchaserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PurchaserNotFoundException(Throwable cause) {
        super(cause);
    }

    public PurchaserNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
