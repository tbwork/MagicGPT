package cn.lanehub.ai.impl;

import cn.lanehub.ai.call.DateQueryCallSpell;
import cn.lanehub.ai.util.ClassUtil;
import cn.lanehub.ai.util.StringUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestStringUtil {


    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {


        Method method = ClassUtil.getMethodByName(DateQueryCallSpell.class, "spellProcedure");
        System.out.println(method.invoke(new DateQueryCallSpell(), new String [] {"1","2"}));

    }
}
