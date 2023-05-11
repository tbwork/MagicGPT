package cn.lanehub.ai.impl;

import cn.lanehub.ai.model.CallSpellAPI;
import cn.lanehub.ai.model.Arg;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shawn feng
 * @description
 * @date 2023/4/29 11:17
 */
public class QueryOrderCallSpell implements CallSpellAPI {
    @Override
    public String getName() {
        return "queryOrder";
    }

    @Override
    public String getDescription() {
        return "查询订单";
    }

    @Override
    public List<Arg> getArgs() {
        List<Arg> list = new ArrayList<>();
        list.add(new Arg(false, "id", "订单ID", true));
        return list;
    }

    @Override
    public String spellProcedure(String... args) {
        return "这是购买技能服务的订单，售价40000元";
    }

}
