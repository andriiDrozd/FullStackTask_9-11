package com.drozdAndrii.FullStackTask_911.exceptions.VesselExceptions;

import java.text.MessageFormat;

public class VesselNotFoundException extends RuntimeException{
    public VesselNotFoundException(final int id){
        super(MessageFormat.format("Cannot find vessel with id: {0}",id));
    }

}
