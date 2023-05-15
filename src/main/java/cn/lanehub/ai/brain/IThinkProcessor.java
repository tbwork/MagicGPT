package cn.lanehub.ai.brain;

import cn.lanehub.ai.brain.model.ThinkResult;
import cn.lanehub.ai.wizards.model.MagicChat;

import java.io.OutputStream;

/**
 * 思考处理器
 */
public interface IThinkProcessor {


    /**
     * 根据上下文进行思考，不断的将结果输出到指定流对象中
     *
     * @param magicChat
     * @return
     */
    public ThinkResult process(MagicChat magicChat);


    /**
     *
     * 我们假设大脑处理器都是流式生成回答的，因此需要从流式片段中解析出内容片段。
     * 此方法往往对于远端脑比较重要。
     * 如果是可以直接输出内容片段的本地脑，输入即返回。
     * @param chunk 流式片段
     * @return 内容片段
     */
    public String parseChunk(String chunk);




}
