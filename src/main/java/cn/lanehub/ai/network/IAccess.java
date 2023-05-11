package cn.lanehub.ai.network;

import java.util.Map;

/**
 * 本包定义了所有网络访问的基本方法
 */
public interface IAccess {



    String access(String url, Map<String,String> headers, Map<String,String> querys, String body);


}
