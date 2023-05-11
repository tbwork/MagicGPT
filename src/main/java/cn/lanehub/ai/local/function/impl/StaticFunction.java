package cn.lanehub.ai.local.function.impl;

import cn.lanehub.ai.model.Arg;
import cn.lanehub.ai.util.ClassUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author shawn feng
 * @description
 * @date 2023/5/5 11:57
 */
public class StaticFunction extends AbstractFunction {

    public static final StaticFunction INSTANCE = new StaticFunction();

    @Override
    protected String doExecute(String className, String methodName, List<Arg> args, List<String> values) {
        try {
            Class<?> clazz = Class.forName(className);
            Method method = ClassUtil.getMethodByName(clazz,methodName);

            if(args.size() == 0){
                // 无参数函数
                Object invokeResult = method.invoke(null);
                return invokeResult.toString();
            }
            Object invoke = values != null? method.invoke(null, new Object[]{values.toArray(new String[0])}) :  method.invoke(null, new Object[]{new String[]{}});
            return invoke.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
