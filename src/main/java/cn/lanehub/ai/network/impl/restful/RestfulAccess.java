package cn.lanehub.ai.network.impl.restful;

import cn.lanehub.ai.model.AccessType;
import cn.lanehub.ai.network.IAccess;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Map;

public abstract class RestfulAccess implements IAccess {


    @Override
    public String access(String url, Map<String, String> headers, Map<String, String> querys, String body) {

        Request request = this.buildRequest(url, headers, querys, body);

        return this.doRequest(request);
    }


    protected abstract Request buildRequest(String url, Map<String, String> headers, Map<String, String> querys, String body);


    private String doRequest(Request request){
        // TODO @杨恒
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        OkHttpClient httpClient = clientBuilder.build();
        Response apiResponse = null;
        try {
            apiResponse = httpClient.newCall(request).execute();
            if (apiResponse != null) {
                String result = apiResponse.body().string();
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
