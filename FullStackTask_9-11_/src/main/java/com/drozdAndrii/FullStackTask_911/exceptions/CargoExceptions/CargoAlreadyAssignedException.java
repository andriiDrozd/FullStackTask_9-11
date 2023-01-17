package com.drozdAndrii.FullStackTask_911.exceptions.CargoExceptions;

import java.text.MessageFormat;

public class CargoAlreadyAssignedException extends RuntimeException{
    public CargoAlreadyAssignedException(final int cargoId,final int vesselId){
        super(MessageFormat.format("Cargo: {0} is already assigned to vessel: {1}", cargoId, vesselId));
    }
}
