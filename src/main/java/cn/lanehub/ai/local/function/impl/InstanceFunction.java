package cn.lanehub.ai.local.function.impl;

import cn.lanehub.ai.exceptions.MagicGPTGeneralException;
import cn.lanehub.ai.local.registry.impl.DefaultLocalCallRegistry;
import cn.lanehub.ai.model.Arg;
import cn.lanehub.ai.util.ClassUtil;
import org.tbwork.anole.loader.util.CollectionUtil;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author shawn feng
 * @description
 * @date 2023/5/5 11:57
 */
public class InstanceFunction extends AbstractFunction {

    public static final InstanceFunction INSTANCE = new InstanceFunction();

    @Override
    protected String doExecute(String className, String methodName, List<Arg> args, List<String> values) {
        Object instance = DefaultLocalCallRegistry.INSTANCE.get(className);
        try {
            Class<?> clazz = Class.forName(className);
            Object o = clazz.newInstance();
            Method method = ClassUtil.getMethodByName(clazz, methodName);

            Object invoke = values!=null? method.invoke(instance, new Object[]{values.toArray(new String[0])}) :  method.invoke(instance, new Object[]{new String[]{}});
            return invoke.toString();
        } catch (Exception e) {
            throw new MagicGPTGeneralException(e.getMessage());
        }
    }
}
