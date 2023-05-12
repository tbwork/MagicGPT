package cn.lanehub.ai.wizards.model;

import cn.lanehub.ai.model.Role;
import cn.lanehub.ai.model.WizardStatus;
import cn.lanehub.ai.prompts.Language;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MagicChat {

    private List<MagicMessage> chatContent;
    private volatile WizardStatus wizardStatus;
    private Language language;

    public MagicChat(Language language){
        chatContent = new ArrayList<MagicMessage>();
        this.wizardStatus = WizardStatus.IDLE;
        this.language = language;
    }

    public void appendMessage(MagicMessage magicMessage){
        this.chatContent.add(magicMessage);
    }

    public void appendMessage(Role role, String content){
        this.appendMessage(new MagicMessage(role.getValue(), content));
    }

    public void appendUserMessage(String userMessage){
        this.appendMessage(new MagicMessage(Role.USER.getValue(), userMessage));
    }

    public void appendSystemMessage(String userMessage){
        this.appendMessage(new MagicMessage(Role.SYSTEM.getValue(), userMessage));
    }

    public void appendAssistantMessage(String userMessage){
        this.appendMessage(new MagicMessage(Role.ASSISTANT.getValue(), userMessage));
    }


    public void setStatus(WizardStatus wizardStatus){
        this.wizardStatus = wizardStatus;
    }


    public boolean isIdle(){
        return this.wizardStatus.equals(WizardStatus.IDLE);
    }


    public MagicMessage getLatestMessage(){
        return chatContent.get(chatContent.size() -1);
    }
}
