package cn.lanehub.ai.network.impl.restful;

import cn.lanehub.ai.core.call.CallSpell;
import okhttp3.Request;
import org.apache.commons.lang.text.StrSubstitutor;

import java.util.HashMap;
import java.util.Map;

public class PostRestfulAccess extends RestfulAccess{


    public static final PostRestfulAccess INSTANCE = new PostRestfulAccess();

    private PostRestfulAccess(){}

    @Override
    protected Request buildRequest(String url, Map<String, String> headers, Map<String, String> querys, String body) {


        Request.Builder requestBuilder = new Request.Builder();
        //设置url
        requestBuilder.url(url);
        //设置body
        okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/json");

        okhttp3.RequestBody requestBody = okhttp3.RequestBody.create(mediaType, body);
        requestBuilder.post(requestBody);

        //设置header信息
        if (headers != null) {
            headers.forEach((k, v) -> {
                requestBuilder.addHeader(k, v);
            });
        }
        return requestBuilder.build();
    }

}
