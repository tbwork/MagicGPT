package cn.lanehub.ai.exceptions;

public class SpellNotFoundException extends RuntimeException{

    public SpellNotFoundException(String spellName){
        super("Could not find the spell named :" + spellName);
    }

}
