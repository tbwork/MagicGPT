package com.magicvector.ai.core.register.impl;

import com.magicvector.ai.core.Spell;
import com.magicvector.ai.core.register.ISpellCollector;
import com.magicvector.ai.model.Arg;
import com.magicvector.ai.model.SpellProcedure;
import com.magicvector.ai.util.ClassUtil;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.reflections.scanners.Scanners.SubTypes;
import static org.reflections.util.ReflectionUtilsPredicates.withClassModifier;

/**
 * 通过实现接口注册
 */
public class InterfaceSpellCollector implements ISpellCollector{


    private static final Logger logger = LoggerFactory.getLogger(InterfaceSpellCollector.class);

    private Set<Class<? extends SpellProcedure>> classes;

    public InterfaceSpellCollector(Set<Class<? extends SpellProcedure>> classes){
        this.classes = classes;
    }

    @Override
    public List<Spell> collect() {
        logger.debug("Registering API call spells...");
        List<Spell> spells = new ArrayList<Spell>();
        for (Class<?> clazz : classes) {
            int modifiers = clazz.getModifiers();
            if (!clazz.isInterface() && !Modifier.isAbstract(modifiers)) {
                try {
                    Object instance = clazz.newInstance();
                    Method getNameMethod = ClassUtil.getMethodByName(clazz,"getName");
                    Method getArgsMethod = ClassUtil.getMethodByName(clazz,"getArgs");
                    Method getDescriptionMethod = ClassUtil.getMethodByName(clazz,"getDescription");
                    Method spellRoutineMethod = ClassUtil.getMethodByName(clazz,"spellProcedure");
                    String apiName = (String) getNameMethod.invoke(instance);
                    String description = (String) getDescriptionMethod.invoke(instance);
                    List<Arg> argList = (List<Arg>) getArgsMethod.invoke(instance);
                    Spell spell = new Spell.Builder()
                            .setApiName(apiName)
                            .setDescription(description)
                            .setClassName(spellRoutineMethod.getDeclaringClass().getName())
                            .setMethodName(spellRoutineMethod.getName())
                            .setFeedArgs(argList)
                            .build();
                    spells.add(spell);
                    logger.debug("咒语{}注册成功!", apiName);
                } catch (Exception e) {
                    logger.error("Failed to collector call spell: {}", clazz.getName(), e);
                }
            }
        }
        return spells;
    }
}
