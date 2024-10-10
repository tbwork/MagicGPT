package com.magicvector.ai.model;

import java.util.List;

/**
 * 所有实现了该API的类中都会被自动并注册。
 */
public interface SpellProcedure {

    String getName();

    String getDescription();


    //该方法的作用在于告诉GPT，每个参数的含义是什么
    List<Arg> getArgs();

    /**
     * 具体的咒语程序过程定义
     * @param args
     * @return
     */
    String spellProcedure(String... args);


}
