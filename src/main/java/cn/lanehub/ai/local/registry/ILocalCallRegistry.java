package cn.lanehub.ai.local.registry;

/**
 * @author shawn feng
 * @description
 * @date 2023/4/29 10:28
 */
public interface ILocalCallRegistry {

    void register(String name, Object obj);

    void remove(String name);

    Object get(String name);
}
