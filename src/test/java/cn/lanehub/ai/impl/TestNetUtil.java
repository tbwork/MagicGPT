package cn.lanehub.ai.impl;

import cn.lanehub.ai.core.search.SearchEngineType;
import cn.lanehub.ai.util.NetUtil;

public class TestNetUtil {


    public static void main(String[] args) {


        System.out.println("Result:" + NetUtil.isUrlAccessible(SearchEngineType.GOOGLE_CN.getUrl()));
    }

}
