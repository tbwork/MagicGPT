package cn.lanehub.ai.util;

import cn.lanehub.ai.exceptions.PromptTokensExceedException;
import cn.lanehub.ai.prompts.Language;
import cn.lanehub.ai.wizards.model.MagicChat;
import cn.lanehub.ai.wizards.model.MagicMessage;

import java.util.ArrayList;
import java.util.List;

public class MessageUtil {


    public static String getLLMDownError(Language language) {
        if(Language.ENGLISH.equals(language))
            return StringUtil.formatString("An error has occurred in the system, and I am unable to provide service for you at this time. Please contact the system administrator.");
        else
            return "系统出现了一些错误，我现在无法为您提供服务，请联系系统管理员。";

    }

    public static int countTokens(String input){
        return getTokens(input).size();
    }
    public static List<String> getTokens(String input) {
        List<String> result = new ArrayList<>();
        StringBuilder temp = new StringBuilder();

        int maxEnglishTokenLength = 5;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (isChineseChar(c)) {
                if (temp.length() > 0) {
                    result.add(temp.toString());
                    temp = new StringBuilder();
                }
                result.add(String.valueOf(c));
            } else if (isEnglishOrNumberChar(c)) {
                if (temp.length() >= maxEnglishTokenLength) {
                    result.add(temp.toString());
                    temp = new StringBuilder();
                }
                temp.append(c);
            } else{
                if (temp.length() > 0) {
                    result.add(temp.toString());
                    temp = new StringBuilder();
                }
                result.add(String.valueOf(c));
            }
        }

        if (temp.length() > 0) {
            result.add(temp.toString());
        }

        return result;
    }

    private static boolean isChineseChar(char c) {
        return c >= '\u4e00' && c <= '\u9fa5';
    }

    private static boolean isEnglishOrNumberChar(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9');
    }



    public static void optimizeChat(MagicChat magicChat, int maxChatTokenCount) {
        List<MagicMessage> chatMagicMessages = magicChat.getChatContent();
        if (chatMagicMessages == null || chatMagicMessages.size() <= 1 || getTotalLength(chatMagicMessages) <= maxChatTokenCount) {
            // If the message list is empty, contains only one message, or its total length does not exceed the maximum limit, just return directly.
            return;
        }

        // A dialogue turn represents the conversation between all roles from the user's one utterance to the next one.
        while(getTotalLength(chatMagicMessages) > maxChatTokenCount){
            reduceOneTalkRound(chatMagicMessages);
        }

        int chatTokenCount = getTotalLength(chatMagicMessages);
        if(chatTokenCount > maxChatTokenCount){
            throw new PromptTokensExceedException( chatTokenCount, maxChatTokenCount);
        }

    }


    /**
     *
     * 删除一个对话轮次
     * @param magicMessageList
     * @return
     */
    public static void reduceOneTalkRound(List<MagicMessage> magicMessageList){

        int s = 1,e = s;
        // The second element is definitely going to be deleted.
        // start from the third message, delete it until the message's role is 'user'
        for(int i = s+1; i < magicMessageList.size(); i++){
            MagicMessage item  = magicMessageList.get(i);
            if(item.getRole().equals("user")){
                break;
            }
            e ++;
        }

        magicMessageList.subList(s, e + 1).clear();
    }

    public static int getTotalLength(List<MagicMessage> magicMessageList) {
        // 计算消息列表的总长度
        int totalLength = 0;
        for (MagicMessage magicMessage : magicMessageList) {
            totalLength += magicMessage.getTokenCount();
        }
        return totalLength;
    }


}
