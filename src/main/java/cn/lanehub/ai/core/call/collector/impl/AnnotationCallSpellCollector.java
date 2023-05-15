package cn.lanehub.ai.core.call.collector.impl;

import cn.lanehub.ai.annotation.CallSpellDefinition;
import cn.lanehub.ai.annotation.MagicArg;
import cn.lanehub.ai.core.call.CallSpell;
import cn.lanehub.ai.core.call.CallSpellType;
import cn.lanehub.ai.core.call.LocalCallSpell;
import cn.lanehub.ai.exceptions.MagicGPTGeneralException;
import cn.lanehub.ai.model.Arg;
import cn.lanehub.ai.util.PromptUtil;
import cn.lanehub.ai.util.StringUtil;
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
public class AnnotationCallSpellCollector extends AbstractSpellCollector {

    private static final Logger logger = LoggerFactory.getLogger(AnnotationCallSpellCollector.class);

    private Reflections reflections;

    public AnnotationCallSpellCollector(Reflections reflections){
        this.reflections = reflections;
    }

    @Override
    protected List<LocalCallSpell> retrieveCallSpells() {
        List<LocalCallSpell> localCallSpells = new ArrayList<>();
        logger.debug("Start to collector call spells from annotations...");
        Set<Method> methods = reflections.get(MethodsAnnotated
                .with(CallSpellDefinition.class)
                .as(Method.class)
                .filter(withModifier(Modifier.PUBLIC)));

        for (Method method : methods) {
            int modifiers = method.getModifiers();
            boolean isStatic = Modifier.isStatic(modifiers);
            if (!isStatic) {
                throw new MagicGPTGeneralException("OmniApi注解只能用于静态方法");
            }
            CallSpellDefinition annotation = method.getAnnotation(CallSpellDefinition.class);
            String apiName = annotation.name();
            if (StringUtil.isEmpty(apiName)) {
                apiName = method.getName();
            }

            String description = annotation.description();


            List<Arg> argList = getParameterAnnotationAsArgs(method);

            CallSpell callSpell = new CallSpell.Builder()
                    .setApiName(apiName)
                    .setDescription(description)
                    .setUrl(method.getDeclaringClass().getName() + "/" + method.getName())
                    .setCallSpellType(CallSpellType.LOCAL_STATIC_FUNCTION_CALL)
                    .setGptFeedArgs(argList)
                    .build();

            localCallSpells.add(new LocalCallSpell(method.getDeclaringClass().getPackage().getName(), callSpell));
            logger.debug("Registered method {} 2 call spell: {}", method, callSpell);
        }
        return localCallSpells;
    }


    private List<Arg> getParameterAnnotationAsArgs(Method method){

        List<Arg> result = new ArrayList<>();
        Parameter[] parameters = method.getParameters();
        for (Parameter parameter : parameters) {
            if(parameter.getType() != String.class){
                throw new MagicGPTGeneralException(StringUtil.formatString("OmniGPT only supports calling local methods that have string types as input parameters. Details: method:{}, parameter's type:{}", method.getName(), parameter.getType().getTypeName()));
            }
            Annotation[] annotations = parameter.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation instanceof MagicArg) {
                    MagicArg magicArg = (MagicArg) annotation;
                    result.add( new Arg(false, magicArg.name(), magicArg.description(), magicArg.required()));
                }
                else{
                    throw new MagicGPTGeneralException(StringUtil.formatString(" In OmniGPT, each parameter of the local function executed by each spell must be annotated with the @OmniArg annotation. Details: method:{}, parameter's type:{}", method.getName(), parameter.getType().getTypeName()));
                }
            }
        }

        return result;

    }
}
