package com.magicvector.ai.examples.timeReporter;


import com.magicvector.ai.model.Arg;
import com.magicvector.ai.model.SpellProcedure;
import com.magicvector.ai.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

public class DateQueryCallSpell implements SpellProcedure {
    @Override
    public String getName() {
        return "nowTime";
    }

    @Override
    public String getDescription() {
        return "该咒语可以帮助你获得当前的时间。";
    }

    @Override
    public List<Arg> getArgs() {
        return new ArrayList<>();
    }

    @Override
    public String spellProcedure(String... args) {
        return DateUtil.getCurrentTime();
    }

}