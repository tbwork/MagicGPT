package cn.lanehub.ai.local.function.impl;

import cn.lanehub.ai.local.function.IFunction;
import cn.lanehub.ai.model.Arg;
import cn.lanehub.ai.util.ClassUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author shawn feng
 * @description
 * @date 2023/5/5 11:56
 */
public abstract class AbstractFunction implements IFunction {

    @Override
    public String execute(String url, List<Arg> args, Map<String, String> argValueMap) {
        String[] strings = url.split("/");
        if (strings.length != 2) {
            throw new RuntimeException("本地方法url格式不正确");
        }
        String className = strings[0];
        String methodName = strings[1];

        List<String> values = new ArrayList<>();
        for (Arg arg : args) {
            String value = argValueMap.get(arg.getName());
            values.add(value);
        }
        return doExecute(className, methodName, args, values);
    }

    protected abstract String doExecute(String className, String methodName, List<Arg> args, List<String> values);
}
