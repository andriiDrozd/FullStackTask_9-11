package com.drozdAndrii.FullStackTask_911.utils;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

public class BindingResultSender {
    public static StringBuilder errorMsg(BindingResult bindingResult){
        StringBuilder errorMsg = new StringBuilder();
        List<FieldError> errorList = bindingResult.getFieldErrors();
        for (FieldError error : errorList) {
            errorMsg.append(error.getField()).append("-").append(error.getDefaultMessage()).append(";");
        }
        return errorMsg;
    }
}
