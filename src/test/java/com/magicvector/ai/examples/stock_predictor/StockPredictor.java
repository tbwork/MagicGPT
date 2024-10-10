package com.magicvector.ai.examples.stock_predictor;

import com.github.tbwork.anole.loader.AnoleApp;
import com.github.tbwork.anole.loader.annotion.AnoleConfigLocation;
import com.magicvector.ai.MagicGPT;
import com.magicvector.ai.annotation.MagicArg;
import com.magicvector.ai.annotation.SpellDefinition;
import com.magicvector.ai.brain.llm.openai.model.OpenAIModel;
import com.magicvector.ai.output.SystemOutputStream;
import com.magicvector.ai.prompts.Language;
import com.magicvector.ai.util.PromptUtil;
import com.magicvector.ai.wizards.model.CustomPrompt;
import com.magicvector.ai.wizards.model.MagicChat;

@AnoleConfigLocation()
public class StockPredictor {

    @SpellDefinition(name = "insertRecommendItem", description= "插入推荐股票")
    public static String insertRecommendItem(
            @MagicArg(name = "newsId", description = "新闻ID") String newsId,
            @MagicArg(name = "stockCode", description = "股票代码") String stockCode,
            @MagicArg(name = "reason", description =  "推荐原因") String reason
    ) {
        System.out.println(String.format("%s, %s, %s", newsId, stockCode, reason));
        return "EOF";
    }

    public static void main(String[] args) {

        // 启动配置管理器Anole
        AnoleApp.start();

        // 加载自定义提示词
        String headCustomPrompt = PromptUtil.readTestResourceFile("custom_prompts/stock_predictor.prompt");

        // 指定包名搜索本地Call类型咒语
        MagicGPT magicGPT = new MagicGPT("com.magicvector.ai.examples.stock_predictor", OpenAIModel.GPT4_O4_MINI);

        // 创建聊天
        MagicChat magicChat = magicGPT.startChat(CustomPrompt.buildHeadPrompt(headCustomPrompt), Language.CHINESE);

        magicGPT.proceedChatWithUserMessage( magicChat, "输入的事件：```据报道，Meta的AI聊天机器人将在21个新地区推出，包括在英国，巴西，拉丁美洲和亚洲。(第一财经)```",new SystemOutputStream());
    }

}
