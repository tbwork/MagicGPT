package cn.lanehub.ai.core.match;

import java.util.List;

public interface IVectorbaseManager {


    String query(String indexName, String queryText);


    void store(String indexName, String data);


    List<VectorIndex> getIndexs();

    void createIndex(String indexName);


}
