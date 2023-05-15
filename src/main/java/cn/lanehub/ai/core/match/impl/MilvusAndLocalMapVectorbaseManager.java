package cn.lanehub.ai.core.match.impl;

import cn.lanehub.ai.core.match.VectorIndex;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MilvusAndLocalMapVectorbaseManager extends LocalMapVectorbaseManager{



    // milvus相关的一些属性可以定义在这里

    public MilvusAndLocalMapVectorbaseManager(List<VectorIndex> indexList) {
        super(indexList);
    }


    @Override
    public void createIndex(String indexName) {
        //TODO @河川
    }

    @Override
    protected String getTheBestVectorId(String indexName, List<Float> vectors) {

        // 匹配出最佳的片段
        //TODO @河川
        return null;
    }

    @Override
    protected void saveToVectorbase(String id, List<Float> vectors) {

        // 保存到Milvus中

        //TODO @河川

    }

}
