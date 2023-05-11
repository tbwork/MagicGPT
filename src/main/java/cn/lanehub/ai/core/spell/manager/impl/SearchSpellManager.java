package cn.lanehub.ai.core.spell.manager.impl;

import cn.lanehub.ai.core.search.SearchEngineType;
import cn.lanehub.ai.core.search.SearchSpell;
import cn.lanehub.ai.core.search.engine.ISearchEngine;
import cn.lanehub.ai.core.search.engine.impl.BaiduSearchEngine;
import cn.lanehub.ai.core.search.engine.impl.BingSearchEngine;
import cn.lanehub.ai.core.search.engine.impl.GoogleSearchEngine;
import cn.lanehub.ai.core.spell.ISpell;
import cn.lanehub.ai.exceptions.Assert;
import cn.lanehub.ai.executors.IExecutor;
import cn.lanehub.ai.executors.ITask;
import cn.lanehub.ai.executors.search.SearchTask;
import cn.lanehub.ai.executors.search.impl.SearchExecutor;
import cn.lanehub.ai.model.SpellType;
import cn.lanehub.ai.prompts.IPrompt;
import cn.lanehub.ai.prompts.impl.SearchEngineItemPrompt;
import cn.lanehub.ai.prompts.impl.SearchPrompt;
import cn.lanehub.ai.util.DateUtil;
import cn.lanehub.ai.util.NetUtil;
import org.apache.commons.compress.utils.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tbwork.anole.loader.Anole;

import java.util.*;

public class SearchSpellManager extends AbstractSpellManager {

    private static final Logger logger = LoggerFactory.getLogger(SearchSpellManager.class);

    private static final IExecutor executor = SearchExecutor.INSTANCE;

    private ISpell searchSpell;


    /**
     * 用户指定允许的搜索引擎
     */
    private static final Map<String, ISearchEngine> searchEngineMap = new HashMap<String, ISearchEngine>();

    /**
     * 框架所有可用的搜索引擎仓库
     */
    private static final Map<SearchEngineType, ISearchEngine> searchEngineRepository = new HashMap<SearchEngineType, ISearchEngine>();

    public SearchSpellManager() {
        super("search");
    }


    @Override
    public void registerSpell(ISpell spell) {
        Assert.judge(SpellType.SEARCH.equals(spell.getType()) , "Can not collector the " + spell.getType()+"-type spell to SearchSpellManager.");

        searchEngineRepository.put(SearchEngineType.BAIDU, BaiduSearchEngine.INSTANCE);
        searchEngineRepository.put(SearchEngineType.BING, BingSearchEngine.INSTANCE);
        searchEngineRepository.put(SearchEngineType.GOOGLE, GoogleSearchEngine.INSTANCE);
        searchEngineRepository.put(SearchEngineType.GOOGLE_CN, GoogleSearchEngine.INSTANCE);

        SearchSpell searchSpell = (SearchSpell) spell;
        this.searchSpell = searchSpell;

        for(SearchEngineType userSpecifiedSearchEngineType : searchSpell.getSupportedSearchEngines()){

            boolean removeUnavailableSearchEngine = Anole.getBoolProperty("spell.search.engine.check", false);
            if(removeUnavailableSearchEngine && !this.checkURLAvailable(userSpecifiedSearchEngineType.getUrl())){
                logger.warn("The search engine named '{}' is not available, it's removed out, please check the '{}' and restart again. Or set the key '{}' as 'false' in anole config file if you persist to support it any way. ",
                        userSpecifiedSearchEngineType.getValue(), userSpecifiedSearchEngineType.getUrl(), "spell.search.engine.check");
            }else{
                searchEngineMap.put(userSpecifiedSearchEngineType.getValue(), searchEngineRepository.get(userSpecifiedSearchEngineType));
            }
        }

    }

    @Override
    public List<ISpell> getSpellList() {
        List<ISpell> result =  Lists.newArrayList();
        result.add(this.searchSpell);
        return result;
    }

    @Override
    public IPrompt generateSpellPrompt(int th) {

        List<IPrompt> enginePromptList = new ArrayList<>();

        for(Map.Entry<String, ISearchEngine> item: searchEngineMap.entrySet()){
            SearchEngineType searchEngineType = SearchEngineType.fromValue(item.getKey());
            enginePromptList.add(new SearchEngineItemPrompt(searchEngineType.getValue()));
        }

        return new SearchPrompt(th, enginePromptList.toArray(new IPrompt[0]));
    }

    @Override
    protected String doCastSpell(List<String> spellArgs) {
        try{
            Assert.isNotEmpty(spellArgs,"Search-type spell's args");
            Assert.judge(spellArgs.size()== 4,"Search-type spell must have 4 arguments which represent engine's name, keywords, start date and end date type respectively.");

            String engineName = spellArgs.get(0);
            String keywords = spellArgs.get(1);
            Date startDate = spellArgs.get(2).isEmpty()? null : DateUtil.toDate(spellArgs.get(2));
            Date endDate =  spellArgs.get(3).isEmpty()? null : DateUtil.toDate(spellArgs.get(3));

            ITask searchTask = new SearchTask(engineName, keywords, startDate, endDate);

            return  executor.execute(searchTask);
        }
        catch (Exception e){
            return "ERROR:"+e.getMessage();
        }
    }


    private boolean checkURLAvailable(String url){
        return NetUtil.isUrlAccessible(url);
    }
}
