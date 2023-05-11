package cn.lanehub.ai.core.call.collector.impl;

import cn.lanehub.ai.core.call.CallSpell;
import cn.lanehub.ai.core.call.CallSpellType;
import cn.lanehub.ai.core.call.LocalCallSpell;
import cn.lanehub.ai.local.registry.impl.DefaultLocalCallRegistry;
import cn.lanehub.ai.model.CallSpellAPI;
import cn.lanehub.ai.model.Arg;
import cn.lanehub.ai.util.ClassUtil;
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
public class APICallSpellCollector extends AbstractSpellCollector {


    private static final Logger logger = LoggerFactory.getLogger(APICallSpellCollector.class);

    private Reflections reflections;

    public APICallSpellCollector(Reflections reflections){
        this.reflections = reflections;
    }


    protected List<LocalCallSpell> retrieveCallSpells() {
        logger.debug("Registering API call spells...");
        List<LocalCallSpell> callSpells = new ArrayList<LocalCallSpell>();
        Set<Class<?>> classes = reflections.get(SubTypes.of(CallSpellAPI.class).asClass().filter(withClassModifier(Modifier.PUBLIC)));
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
                    CallSpell callSpell = new CallSpell.Builder()
                            .setApiName(apiName)
                            .setDescription(description)
                            //url形式样例：cn.lanehub.ai.core.call.collector.impl.AnnotationCallSpellRegister/retrieveCallSpells
                            .setUrl(spellRoutineMethod.getDeclaringClass().getName() + "/" + spellRoutineMethod.getName())
                            .setCallSpellType(CallSpellType.LOCAL_FUNCTION_CALL)
                            .setGptFeedArgs(argList)
                            .build();
                    callSpells.add(new LocalCallSpell(clazz.getPackage().getName(), callSpell));
                    DefaultLocalCallRegistry.INSTANCE.register(clazz.getName(), instance);
                    logger.debug("Registered {} as call spell : {} successfully!", clazz, callSpell);
                } catch (Exception e) {
                    logger.error("Failed to collector call spell: {}", clazz.getName(), e);
                }
            }
        }
        return callSpells;
    }


}
