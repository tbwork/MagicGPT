package cn.lanehub.ai.executors.search.impl;

import cn.lanehub.ai.core.search.SearchEngineType;
import cn.lanehub.ai.core.search.engine.ISearchEngine;
import cn.lanehub.ai.core.search.engine.SearchEngineRepository;
import cn.lanehub.ai.exceptions.Assert;
import cn.lanehub.ai.executors.ITask;
import cn.lanehub.ai.executors.search.ISearchExecutor;
import cn.lanehub.ai.executors.search.SearchTask;

import java.util.HashMap;
import java.util.Map;

/**
 * 执行搜索任务
 */
public class SearchExecutor implements ISearchExecutor {

    public static final ISearchExecutor INSTANCE = new SearchExecutor();

    private SearchExecutor(){

    }


    @Override
    public String execute(ITask task) {

        Assert.judge(task instanceof SearchTask, "The search executor can only execute SearchTask, the input task is " + task.getClass().getTypeName());
        SearchTask searchTask = ((SearchTask) task);
        String searchEngineName = searchTask.getEngineName();
        SearchEngineType searchEngineType = SearchEngineType.fromValue(searchEngineName);

        Assert.judge(searchEngineType!=null, "Can not find the search engine type named : "+ searchEngineName);

        ISearchEngine searchEngine =SearchEngineRepository.INSTANCE.getEngine(searchEngineType);
        return searchEngine.search(searchTask.getKeywords(), searchTask.getStartDate(), searchTask.getEndDate());
    }
}
