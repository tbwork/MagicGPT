package com.magicvector.ai.util;

import com.magicvector.ai.exceptions.MagicGPTGeneralException;

import java.lang.reflect.Method;

/**
 * @author shawn feng
 * @description
 * @date 2023/5/5 15:46
 */
public class ClassUtil {

    public static Object transform(String value, Class<?> clazz) {
        if (clazz == String.class) {
            return value;
        } else if (clazz == Byte.class) {
            return Byte.valueOf(value);
        } else if (clazz == Short.class) {
            return Short.valueOf(value);
        } else if (clazz == Integer.class) {
            return Integer.valueOf(value);
        } else if (clazz == Long.class) {
            return Long.valueOf(value);
        } else if (clazz == Double.class) {
            return Double.valueOf(value);
        } else if (clazz == Float.class) {
            return Float.valueOf(value);
        } else if (clazz == Boolean.class) {
            return Boolean.valueOf(value);
        } else {
            throw new MagicGPTGeneralException("不支持的请求参数类型");
        }
    }


    public static Method getMethodByName(Class clazz, String methodName){
        for(Method method : clazz.getMethods()){
            if(method.getName().equals(methodName)){
                return method;
            }
        }
        throw new MagicGPTGeneralException(StringUtil.formatString("Cannot find method named '{}' in class '{}'", "spellProcedure", clazz.getTypeName()));
    }
}
