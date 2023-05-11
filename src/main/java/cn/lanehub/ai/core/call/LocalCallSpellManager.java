package cn.lanehub.ai.core.call;

import cn.lanehub.ai.core.call.collector.impl.APICallSpellCollector;
import cn.lanehub.ai.core.call.collector.impl.AnnotationCallSpellCollector;
import cn.lanehub.ai.core.spell.ISpell;
import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.reflections.scanners.Scanners.*;

public class LocalCallSpellManager {


    public static final LocalCallSpellManager INSTANCE = new LocalCallSpellManager();


    private Reflections reflections = new Reflections(new ConfigurationBuilder().forPackages("").setScanners(SubTypes, TypesAnnotated, MethodsAnnotated));

    private List<LocalCallSpell> localCallSpellRepository = new ArrayList<LocalCallSpell>();

    private LocalCallSpellManager(){

        localCallSpellRepository.addAll(new AnnotationCallSpellCollector(reflections).collect());
        localCallSpellRepository.addAll(new APICallSpellCollector(reflections).collect());

    }


    public List<CallSpell> getLocalSpellsUnderPackage(String packageName){

        List<CallSpell> result = new ArrayList<CallSpell>();

        localCallSpellRepository.forEach(item->{
            if(item.getPackageName().startsWith(packageName)){
                result.add(item.getInnerSpell());
            }
        });

        return result;
    }





}
