package cn.lanehub.ai.core.spell.manager.impl;

import cn.lanehub.ai.core.spell.ISpell;
import cn.lanehub.ai.core.spell.manager.ISpellManager;
import cn.lanehub.ai.exceptions.Assert;
import cn.lanehub.ai.executors.IExecutor;
import cn.lanehub.ai.executors.ITask;
import cn.lanehub.ai.executors.search.SearchTask;
import cn.lanehub.ai.executors.search.impl.SearchExecutor;
import cn.lanehub.ai.executors.view.ViewTask;
import cn.lanehub.ai.executors.view.impl.ViewExecutor;
import cn.lanehub.ai.model.SpellType;
import cn.lanehub.ai.prompts.IPrompt;
import cn.lanehub.ai.prompts.impl.ViewPrompt;
import cn.lanehub.ai.util.DateUtil;
import org.apache.commons.compress.utils.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

public class ViewSpellManager extends AbstractSpellManager {

    private static final Logger logger = LoggerFactory.getLogger(ViewSpellManager.class);

    public static final ISpellManager INSTANCE = new ViewSpellManager();

    private static final IExecutor executor = ViewExecutor.INSTANCE;

    private ISpell viewSpell;

    private ViewSpellManager() {
        super("view");
        this.enabled = false;
    }

    private volatile boolean enabled;

    @Override
    public void registerSpell(ISpell spell) {
        if(this.enabled){
            logger.warn("The view spell is already registered, please do not collector it again.");
        }
        Assert.judge(SpellType.VIEW.equals(spell.getType()) , "Can not collector the " + spell.getType()+"-type spell to ViewSpellManager.");
        this.viewSpell = spell;
        this.enabled = true;
    }

    @Override
    public List<ISpell> getSpellList() {
        List<ISpell> result =  Lists.newArrayList();
        result.add(this.viewSpell);
        return result;
    }

    @Override
    public IPrompt generateSpellPrompt(int th) {

        if(this.enabled){
            return  new ViewPrompt(th);
        }
        return null;
    }

    @Override
    protected String doCastSpell(List<String> spellArgs) {

        Assert.isNotEmpty(spellArgs,"View-type spell's args");
        Assert.judge(spellArgs.size()== 1,"View-type spell must have 1 arguments which represents the target page's url.");

        String url = spellArgs.get(0);
        ITask viewTask = new ViewTask(url, null, true, true);

        return  executor.execute(viewTask);
    }
}
