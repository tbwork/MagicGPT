package com.magicvector.ai.executors.impl;

import com.magicvector.ai.core.Spell;
import com.magicvector.ai.executors.IExecutor;
import com.magicvector.ai.executors.ITask;
import com.magicvector.ai.model.Arg;
import com.magicvector.ai.util.ClassUtil;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

public class Executor implements IExecutor {

    public static final IExecutor INSTANCE = new Executor();

    @Override
    public String execute(ITask task) {
        Spell spell = task.getSpell();
        List<Arg> args = task.getParamArgs();
        List<String> values = task.getArgValues();
        try {
            // 获取类
            Class<?> clazz = Class.forName(spell.getClassName());
            // 通过工具类获取方法
            Method method = ClassUtil.getMethodByName(clazz, spell.getMethodName());
            // 创建实例对象，如果是非静态方法需要实例对象
            Object instance = null;
            if (!Modifier.isStatic(method.getModifiers())) {
                instance = clazz.getDeclaredConstructor().newInstance(); // 创建类的实例
            }

            // 无参数函数
            if (args.size() == 0) {
                Object invokeResult = method.invoke(instance); // 对于非静态方法，需要传递实例对象
                return invokeResult.toString();
            }

            // 参数处理
            Object invoke = null;
            if (values != null) {
                Object[] params = values.toArray(new String[0]);
                invoke = method.invoke(instance, params); // 非静态方法传递实例对象
            } else {
                invoke = method.invoke(instance); // 非静态方法传递实例对象
            }

            return invoke.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
