package com.drozdAndrii.FullStackTask_911.exceptions;

import java.sql.Timestamp;
import java.time.LocalTime;

public class VesselErrorResponse {
    private String message;


    public VesselErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
