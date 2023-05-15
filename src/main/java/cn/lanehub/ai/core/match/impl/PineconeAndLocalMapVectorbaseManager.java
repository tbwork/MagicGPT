package cn.lanehub.ai.core.match.impl;

import cn.lanehub.ai.core.match.VectorIndex;

import java.util.List;

public class PineconeAndLocalMapVectorbaseManager extends LocalMapVectorbaseManager{


    // pinecone相关的一些属性可以定义在这里

    public PineconeAndLocalMapVectorbaseManager(List<VectorIndex> indexList) {
        super(indexList);
    }

    @Override
    public void createIndex(String indexName) {


        //TODO @桂林

    }

    @Override
    protected String getTheBestVectorId(String indexName, List<Float> vectors) {
        //TODO @桂林
        return null;
    }

    @Override
    protected void saveToVectorbase(String id, List<Float> vectors) {
        //TODO @桂林
    }



}
