package cn.lanehub.ai.local.function;

import cn.lanehub.ai.model.Arg;

import java.util.List;
import java.util.Map;

/**
 * @author shawn feng
 * @description
 * @date 2023/5/5 11:56
 */
public interface IFunction {

    String execute(String url, List<Arg> args, Map<String, String> argValueMap);
}
