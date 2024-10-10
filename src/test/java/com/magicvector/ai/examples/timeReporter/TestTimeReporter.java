package com.magicvector.ai.examples.timeReporter;

import com.magicvector.ai.annotation.SpellDefinition;
import com.magicvector.ai.brain.llm.openai.model.OpenAIModel;
import com.magicvector.ai.output.SystemOutputStream;
import com.magicvector.ai.prompts.Language;
import com.magicvector.ai.util.DateUtil;
import com.magicvector.ai.util.PromptUtil;
import com.magicvector.ai.wizards.model.CustomPrompt;
import com.magicvector.ai.wizards.model.MagicChat;
import com.github.tbwork.anole.loader.AnoleApp;
import com.github.tbwork.anole.loader.annotion.AnoleConfigLocation;
import com.magicvector.ai.MagicGPT;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

@AnoleConfigLocation()
public class TestTimeReporter {

    @SpellDefinition(name = "queryTime", description= "查询当前的时间")
    public static String queryTime() {
        return DateUtil.getCurrentTime();
    }


    public static void main(String[] args) {

        // 启动配置管理器Anole
        AnoleApp.start();

        // 加载自定义提示词
        String headCustomPrompt = PromptUtil.readTestResourceFile("custom_prompts/time_reporter.prompt");

        // 指定包名搜索本地Call类型咒语
        MagicGPT magicGPT = new MagicGPT(TestTimeReporter.class.getPackage().getName(),
                OpenAIModel.GPT4_O4_MINI,
                true
        );

        // 创建聊天
        MagicChat magicChat = magicGPT.startChat(CustomPrompt.buildHeadPrompt(headCustomPrompt), Language.CHINESE);
        System.out.print("AI：你好，当你需要知道现在几点了，随时问我!");

        // 开始聊天
        Scanner scanner = new Scanner(System.in);
        while (true) {
            if(magicChat.isIdle()){
                System.out.print("\n我：");
                String input = scanner.nextLine();
                if(input.equals("exit")){
                    break;
                }
                System.out.print("AI：");
                // 推进一个聊天，指定一个输出流用于承载AI的输出
                magicGPT.proceedChatWithUserMessage(magicChat, input, new SystemOutputStream());
            }

            try {
                TimeUnit.SECONDS.sleep(1L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

}
