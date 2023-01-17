package com.drozdAndrii.FullStackTask_911.exceptions;

import java.time.LocalTime;

public class CargoErrorResponse {
    private String message;


    public CargoErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
