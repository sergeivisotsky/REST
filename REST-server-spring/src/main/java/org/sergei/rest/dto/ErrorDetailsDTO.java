/*
 * Copyright (c) 2018 Sergei Visotsky
 */

package org.sergei.rest.dto;

import java.util.Date;

public class ErrorDetailsDTO {
    private Date timestamp;
    private String message;
    private String details;

    public ErrorDetailsDTO(Date timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
