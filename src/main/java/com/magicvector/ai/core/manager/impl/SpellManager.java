package com.magicvector.ai.core.manager.impl;

import com.magicvector.ai.exceptions.Assert;
import com.magicvector.ai.executors.IExecutor;
import com.magicvector.ai.executors.ITask;
import com.magicvector.ai.executors.impl.Task;
import com.magicvector.ai.executors.impl.Executor;
import com.magicvector.ai.core.Spell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 咒语管理器，包含基础的功能以及咒语执行管理
 */
public class SpellManager extends AbstractSpellManager {

    private static final IExecutor executor = Executor.INSTANCE;

    private static final Map<String, Spell> magicSpellBook = new HashMap<String, Spell>();


    @Override
    public void registerSpell(Spell spell) {
        magicSpellBook.put(spell.getApiName(), spell);
    }

    @Override
    public List<Spell> getSpellList() {
        return new ArrayList<>(magicSpellBook.values());
    }


    @Override
    protected String doExecSpell(List<String> spellArgs) {

        try{
            Assert.isNotEmpty(spellArgs,"咒语参数列表不为空");
            String callName = spellArgs.get(0);
            Assert.isNotBlank(callName,"咒语名称不为空");
            // 1. 根据callName找到对应的咒语对象。
            Assert.judge(magicSpellBook.get(callName) != null, "找不到名为 "+ callName +" 的咒语。");
            Spell callSpell = magicSpellBook.get(callName);

            // 2. 构建咒语执行任务
            spellArgs.remove(0);
            ITask callTask = new Task(callSpell, spellArgs);

            // 3. 执行咒语返回文本结果
            return executor.execute(callTask);
        }
        catch (Exception e){
            return "ERROR:"+e.getMessage();
        }
    }

}
