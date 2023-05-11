package cn.lanehub.ai.exceptions;

public class RemoteLLMCallException extends RuntimeException{


    public RemoteLLMCallException(String target){
        super("Fail to call the remote llm named '"+target+"'.");
    }

}
