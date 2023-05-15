package cn.lanehub.ai.core.match.impl;

import cn.lanehub.ai.core.match.VectorIndex;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class LocalMapVectorbaseManager extends AbstractVectorbaseManager{


    private Map<String,String> kvbase = new HashMap<String,String>();

    public LocalMapVectorbaseManager(List<VectorIndex> indexList) {
        super(indexList);
    }


    @Override
    protected String getDataById(String dataId) {
        return kvbase.get(dataId);
    }


    @Override
    protected void saveToDatabase(String id, String data) {
        this.kvbase.put(id, data);
    }


}
