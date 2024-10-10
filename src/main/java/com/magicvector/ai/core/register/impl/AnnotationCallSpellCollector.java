package com.magicvector.ai.core.register.impl;

import com.magicvector.ai.annotation.MagicArg;
import com.magicvector.ai.annotation.SpellDefinition;
import com.magicvector.ai.core.Spell;
import com.magicvector.ai.core.register.ISpellCollector;
import com.magicvector.ai.exceptions.MagicGPTGeneralException;
import com.magicvector.ai.model.Arg;
import com.magicvector.ai.util.StringUtil;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.reflections.scanners.Scanners.MethodsAnnotated;
import static org.reflections.util.ReflectionUtilsPredicates.withModifier;

/**
 * 通过注解注册
 */
public class AnnotationCallSpellCollector implements ISpellCollector {

    private static final Logger logger = LoggerFactory.getLogger(AnnotationCallSpellCollector.class);

    private Set<Method> methods;

    public AnnotationCallSpellCollector(Set<Method> methods){
        this.methods = methods;
    }


    private List<Arg> getParameterAnnotationAsArgs(Method method){

        List<Arg> result = new ArrayList<>();
        Parameter[] parameters = method.getParameters();
        for (Parameter parameter : parameters) {
            if(parameter.getType() != String.class){
                throw new MagicGPTGeneralException(StringUtil.formatString("MagicGPT only supports calling local methods that have string types as input parameters. Details: method:{}, parameter's type:{}", method.getName(), parameter.getType().getTypeName()));
            }
            Annotation[] annotations = parameter.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation instanceof MagicArg) {
                    MagicArg magicArg = (MagicArg) annotation;
                    result.add( new Arg(false, magicArg.name(), magicArg.description(), magicArg.required()));
                }
                else{
                    throw new MagicGPTGeneralException(StringUtil.formatString(" In MagicGPT, each parameter of the local function executed by each spell must be annotated with the @OmniArg annotation. Details: method:{}, parameter's type:{}", method.getName(), parameter.getType().getTypeName()));
                }
            }
        }

        return result;

    }

    @Override
    public List<Spell> collect() {
        List<Spell> spells = new ArrayList<>();
        logger.debug("开始收集注解标注的静态方法咒语...");
        for (Method method : methods) {
            int modifiers = method.getModifiers();
            boolean isStatic = Modifier.isStatic(modifiers);
            if (!isStatic) {
                throw new MagicGPTGeneralException("SpellDefinition注解只能用于静态方法");
            }
            SpellDefinition annotation = method.getAnnotation(SpellDefinition.class);
            String apiName = annotation.name();
            if (StringUtil.isEmpty(apiName)) {
                apiName = method.getName();
            }

            String description = annotation.description();
            List<Arg> argList = getParameterAnnotationAsArgs(method);

            Spell spell = new Spell.Builder()
                    .setApiName(apiName)
                    .setDescription(description)
                    .setClassName(method.getDeclaringClass().getName())
                    .setMethodName(method.getName())
                    .setFeedArgs(argList)
                    .build();

            spells.add(spell);
            logger.debug("静态方法咒语{}注册成功", apiName);
        }
        return spells;
    }
}
