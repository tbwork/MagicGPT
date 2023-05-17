package cn.lanehub.ai.exceptions;

public class FailToStartException extends RuntimeException{

    public FailToStartException(String message){
        super("Details: "+ message);
    }

}