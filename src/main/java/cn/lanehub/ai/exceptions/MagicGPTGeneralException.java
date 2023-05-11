package cn.lanehub.ai.exceptions;

public class MagicGPTGeneralException extends RuntimeException{

    public MagicGPTGeneralException(String message){
        super("ERROR: " + message);
    }

}
