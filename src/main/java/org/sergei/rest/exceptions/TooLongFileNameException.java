package org.sergei.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class TooLongFileNameException extends RuntimeException {
    public TooLongFileNameException() {
        super();
    }

    public TooLongFileNameException(String message) {
        super(message);
    }

    public TooLongFileNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public TooLongFileNameException(Throwable cause) {
        super(cause);
    }

    protected TooLongFileNameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
