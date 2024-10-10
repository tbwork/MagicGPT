package com.magicvector.ai.exceptions;

public class MessageStreamException extends RuntimeException{

    public MessageStreamException(Exception e){
        super(e);
    }

    public MessageStreamException(String message){
        super(message);
    }

}
