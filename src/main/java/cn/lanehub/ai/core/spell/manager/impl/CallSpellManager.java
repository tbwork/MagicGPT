package cn.lanehub.ai.core.spell.manager.impl;

import cn.lanehub.ai.core.call.CallSpell;
import cn.lanehub.ai.core.spell.ISpell;
import cn.lanehub.ai.exceptions.Assert;
import cn.lanehub.ai.executors.IExecutor;
import cn.lanehub.ai.executors.ITask;
import cn.lanehub.ai.executors.call.CallTask;
import cn.lanehub.ai.executors.call.impl.CallExecutor;
import cn.lanehub.ai.prompts.IPrompt;
import cn.lanehub.ai.prompts.impl.CallItemPrompt;
import cn.lanehub.ai.prompts.impl.CallPrompt;
import cn.lanehub.ai.util.PromptUtil;
import cn.lanehub.ai.util.SpellUtil;
import cn.lanehub.ai.util.StringUtil;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Call咒语管理器，包含基础的功能以及咒语执行管理
 */
public class CallSpellManager extends AbstractSpellManager {

    private static final IExecutor executor = CallExecutor.INSTANCE;

    private final static String spellName = "call";

    private volatile int number ;

    private static final Map<String, CallSpell> callSpellMap = new HashMap<String, CallSpell>();


    public CallSpellManager() {
        super("call");
    }



    @Override
    public void registerSpell(ISpell spell) {
        Assert.judge(spell instanceof CallSpell, "Can not collector the " + spell.getType()+"-type spell to CallSpellManager.");
        CallSpell _spell = (CallSpell) spell;
        callSpellMap.put(_spell.getApiName(), _spell);
    }


    @Override
    public List<ISpell> getSpellList() {
        return callSpellMap.values().stream().collect(Collectors.toList());
    }

    @Override
    public IPrompt generateSpellPrompt(int th) {
        this.number = th;
        List<IPrompt> callItemPromptList = this.generateCallItemPrompts();
        CallPrompt callPrompt = new CallPrompt(th, callItemPromptList.toArray(new IPrompt[0]));
        return callPrompt;
    }


    private List<IPrompt> generateCallItemPrompts(){
        List<IPrompt> result = new ArrayList<>();
        for (ISpell spell : this.getSpellList()) {
            result.add( new CallItemPrompt((CallSpell) spell));
        }
        return result;
    }

    @Override
    protected String doCastSpell(List<String> spellArgs) {

        try{
            Assert.isNotEmpty(spellArgs,"Call-type spell's args");
            String callName = spellArgs.get(0);
            Assert.isNotBlank(callName,"callName");

            // 1. 根据callName找到对应的咒语对象。
            Assert.judge(callSpellMap.get(callName) != null, "The call api named "+ callName +" is not existed, please check the GPT prompts.");
            CallSpell callSpell = callSpellMap.get(callName);


            // 2. 构建咒语执行任务
            spellArgs.remove(0);
            ITask callTask = new CallTask(callSpell, spellArgs);


            // 3. 执行咒语返回文本结果
            return executor.execute(callTask);
        }
        catch (Exception e){
            return e.getMessage();
        }
    }

}
