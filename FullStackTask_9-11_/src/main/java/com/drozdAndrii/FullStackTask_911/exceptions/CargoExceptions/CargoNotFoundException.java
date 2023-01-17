package com.drozdAndrii.FullStackTask_911.exceptions.CargoExceptions;

import com.drozdAndrii.FullStackTask_911.model.Cargo;

import java.text.MessageFormat;

public class CargoNotFoundException extends RuntimeException{

    public CargoNotFoundException(final int id){
        super(MessageFormat.format("Cannot found cargo with id: {0}", id));
    }
}
