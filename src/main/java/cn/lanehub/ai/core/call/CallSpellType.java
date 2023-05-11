package cn.lanehub.ai.core.call;

public enum CallSpellType {

    REST_GET_CALL,// GET的Restful服务调用
    REST_POST_CALL,// POST的Restful服务调用
    LOCAL_FUNCTION_CALL, //本地方法调用，适用于实现了API的类
    LOCAL_STATIC_FUNCTION_CALL //本地静态方法调用，适用于通过注解注释的方法

}
