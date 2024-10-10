package com.magicvector.ai.exceptions;

public class FailToStartException extends RuntimeException{

    public FailToStartException(String message){
        super("Details: "+ message);
    }

}