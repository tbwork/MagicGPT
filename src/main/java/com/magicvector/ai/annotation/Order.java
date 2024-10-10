package com.magicvector.ai.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Order {

    /**
     * 处理次序，越大代表越靠后
     * @return
     */
    @NotNull
    int value();


}
