package cn.lanehub.ai.exceptions;

import cn.lanehub.ai.model.WizardStatus;
import org.tbwork.anole.loader.Anole;

public class AIBusyException extends RuntimeException{

    public AIBusyException(WizardStatus wizardStatus){
        super(getBusyMessage(wizardStatus));
    }


    private static String getBusyMessage(WizardStatus wizardStatus){

        if(WizardStatus.RESPONDING.equals(wizardStatus)){
            return Anole.getProperty("magicgpt.config.wizard.status.responding.message", "magicgpt.config.wizard.status.responding.message config not found!");
        }

        if(WizardStatus.SPELLING.equals(wizardStatus)){
            return Anole.getProperty("magicgpt.config.wizard.status.spelling.message", "magicgpt.config.wizard.status.spelling.message config not found!");
        }

        return "";

    }

}