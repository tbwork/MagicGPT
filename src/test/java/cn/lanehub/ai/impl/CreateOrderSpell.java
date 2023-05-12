package cn.lanehub.ai.impl;

import cn.lanehub.ai.annotation.CallSpellDefinition;
import cn.lanehub.ai.annotation.MagicArg;

/**
 * @author shawn feng
 * @description
 * @date 2023/4/29 11:18
 */
public class CreateOrderSpell {

    @CallSpellDefinition(name = "createOrder", description= "创建订单")
    public static String createOrder( @MagicArg(name = "itemName", description = "商品名称") String itemName,
                              @MagicArg(name = "price", description =  "价格") String price) {
        return "api implement by annotation";
    }

}
