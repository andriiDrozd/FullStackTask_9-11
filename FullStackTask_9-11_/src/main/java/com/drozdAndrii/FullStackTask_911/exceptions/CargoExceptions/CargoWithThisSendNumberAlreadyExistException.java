package com.drozdAndrii.FullStackTask_911.exceptions.CargoExceptions;

import java.text.MessageFormat;

public class CargoWithThisSendNumberAlreadyExistException extends RuntimeException{
    public CargoWithThisSendNumberAlreadyExistException(String cargoSendNumber){
        super(MessageFormat.format("Cargo with Send Number: {0} already exist", cargoSendNumber));
    }
}
