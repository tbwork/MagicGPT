package cn.lanehub.ai.network.impl.restful;

import okhttp3.Request;
import org.apache.commons.lang.text.StrSubstitutor;

import java.util.HashMap;
import java.util.Map;

public class GetRestfulAccess extends RestfulAccess {


    public static final GetRestfulAccess INSTANCE = new GetRestfulAccess();

    private GetRestfulAccess(){}


    @Override
    protected Request buildRequest(String url, Map<String, String> headers, Map<String, String> querys, String body) {

        Request.Builder requestBuilder = new Request.Builder();

        //设置url中请求参数
        StrSubstitutor strSubstitutor = new StrSubstitutor(querys);
        String requestUrl = strSubstitutor.replace(url);

        //设置url
        requestBuilder.url(requestUrl);
        requestBuilder.get();

        //设置header信息
        if (headers != null) {
            headers.forEach((k, v) -> {
                requestBuilder.addHeader(k, v);
            });
        }
        return requestBuilder.build();
    }

}
