package com.drozdAndrii.FullStackTask_911.exceptions.CargoExceptions;

import java.text.MessageFormat;

public class CargoNotCreatedException extends RuntimeException{
    public CargoNotCreatedException(String msg){
        super(msg);
    }
}
