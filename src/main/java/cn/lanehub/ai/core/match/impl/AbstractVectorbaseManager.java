package cn.lanehub.ai.core.match.impl;

import cn.lanehub.ai.core.match.IVectorbaseManager;
import cn.lanehub.ai.core.match.VectorIndex;
import cn.lanehub.ai.util.IDUtil;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractVectorbaseManager implements IVectorbaseManager {

    private List<VectorIndex> vectorIndexList;


    public AbstractVectorbaseManager(List<VectorIndex> indexList){
        this.vectorIndexList = indexList;
    }

    @Override
    public String query(String indexName, String queryText) {

        // 把queryText变成向量
        List<Float> vectors = this.getVectorForText(queryText);

        // 检索获得ID
        String vectorId = this.getTheBestVectorId(indexName, vectors);

        // 根据ID获取元数据
        return this.getDataById(vectorId);
    }

    @Override
    public void store(String indexName, String data) {

        // 把数据变成向量
        List<Float> vectors = this.getVectorForText(data);

        String dataId = IDUtil.getUniqueId();

        // 存入向量库
        this.saveToVectorbase(dataId, vectors);

        // 存入数据库
        this.saveToDatabase(dataId, data);

    }

    @Override
    public List<VectorIndex> getIndexs() {
        return this.vectorIndexList;
    }

    private List<Float> getVectorForText(String text){

        // 使用openAI for java的embedding把文本变成向量

        //TODO @杨恒

        return new ArrayList<>();
    }


    protected abstract String getTheBestVectorId(String indexName, List<Float> vectors);


    protected abstract String getDataById(String dataId);

    protected abstract void saveToVectorbase(String id, List<Float> vectors);

    protected abstract void saveToDatabase(String id, String data);


}
