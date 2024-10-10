package com.magicvector.ai.core.register;

import com.magicvector.ai.annotation.SpellDefinition;
import com.magicvector.ai.core.Spell;
import com.magicvector.ai.core.manager.ISpellManager;
import com.magicvector.ai.core.manager.impl.SpellManager;
import com.magicvector.ai.core.register.impl.AnnotationCallSpellCollector;
import com.magicvector.ai.core.register.impl.InterfaceSpellCollector;
import com.magicvector.ai.model.SpellProcedure;
import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.reflections.scanners.Scanners.*;

public class SpellLoader {


    public static void loadAndRegister(String packageName){
        loadAndRegister(packageName, new Spell[0]);
    }


    public static void loadAndRegister(String packageName, Spell... spells){
        // 使用 Reflections 加载指定包的内容
        Reflections reflections = new Reflections(packageName, SubTypes, MethodsAnnotated);

        // 查找实现了 SpellProcedure 接口的所有子类
        Set<Class<? extends SpellProcedure>> interfaceSpellDefinitions = reflections.getSubTypesOf(SpellProcedure.class);
        Set<Method> annotatedMethods = reflections.getMethodsAnnotatedWith(SpellDefinition.class);

        List<Spell> candidates = new ArrayList<>();
        for (Spell spell : spells) {
            candidates.add(spell);
        }
        candidates.addAll(new InterfaceSpellCollector(interfaceSpellDefinitions).collect());
        candidates.addAll(new AnnotationCallSpellCollector(annotatedMethods).collect());
        ISpellManager spellManager = new SpellManager();
        for (Spell candidate : candidates) {
            spellManager.registerSpell(candidate);
        }
    }


}
