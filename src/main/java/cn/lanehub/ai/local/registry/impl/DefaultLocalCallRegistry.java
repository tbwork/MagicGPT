package cn.lanehub.ai.local.registry.impl;

import cn.lanehub.ai.exceptions.MagicGPTGeneralException;
import cn.lanehub.ai.local.registry.ILocalCallRegistry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author shawn feng
 * @description
 * @date 2023/4/29 10:34
 */
public class DefaultLocalCallRegistry implements ILocalCallRegistry {

    public static final DefaultLocalCallRegistry INSTANCE = new DefaultLocalCallRegistry();

    private final Map<String, Object> objectMap = new ConcurrentHashMap<>(256);

    @Override
    public void register(String name, Object obj) {
        if (obj == null) {
            throw new MagicGPTGeneralException("object cannot be null");
        }

        Object previousObj = this.objectMap.get(name);
        if (previousObj != null) {
            throw new MagicGPTGeneralException("object already exists: " + previousObj);
        }

        this.objectMap.put(name, obj);
    }

    @Override
    public void remove(String name) {
        if (name == null || name.isEmpty()) {
            throw new MagicGPTGeneralException("object name cannot be null or empty");
        }
        this.objectMap.remove(name);
    }

    @Override
    public Object get(String name) {
        return this.objectMap.get(name);
    }
}
