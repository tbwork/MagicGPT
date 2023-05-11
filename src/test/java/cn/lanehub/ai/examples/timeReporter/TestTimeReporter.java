package cn.lanehub.ai.examples.timeReporter;

import cn.lanehub.ai.MagicGPT;
import cn.lanehub.ai.annotation.CallSpellDefinition;
import cn.lanehub.ai.model.AIWizardType;
import cn.lanehub.ai.prompts.Language;
import cn.lanehub.ai.util.DateUtil;
import cn.lanehub.ai.util.PromptUtil;
import cn.lanehub.ai.util.TestUtil;
import cn.lanehub.ai.wizards.model.MagicChat;
import org.tbwork.anole.loader.AnoleApp;
import org.tbwork.anole.loader.annotion.AnoleConfigLocation;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

@AnoleConfigLocation()
public class TestTimeReporter {

    @CallSpellDefinition(name = "queryTime", description= "查询当前的时间")
    public static String queryTime() {
        return DateUtil.getCurrentTime();
    }


    public static void main(String[] args) {

        // 启动配置管理器Anole
        AnoleApp.start();

        // 加载自定义提示词
        String customSystemPrompt = PromptUtil.readTestResourceFile("custom_prompts/time_reporter.prompt");

        // 指定包名搜索本地Call类型咒语
        MagicGPT magicGPT = new MagicGPT("cn.lanehub.ai.examples.timeReporter", "时间播报员",  AIWizardType.GPT4);

        // 创建聊天
        MagicChat magicChat = magicGPT.startChat("你好，我是时间播报员？", customSystemPrompt, Language.CHINESE);
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
                magicGPT.proceedChatWithUserMessage(input, magicChat, TestUtil.getConsoleOutputStream());
            }

            try {
                TimeUnit.SECONDS.sleep(1L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

}
