package com.magicvector.ai.exceptions;

public class NotSupportedException extends RuntimeException{


    public NotSupportedException(String target){
        super("The feature named '"+target+"' is not supported by now.");
    }

}
