package com.magicvector.ai.annotation;

import java.lang.annotation.*;

/**
 * @author shawn feng
 * @description 该注解仅适用于静态方法
 * @date 2023/4/29 10:05
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SpellDefinition {

    @NotNull
    String name();

    @NotNull
    String description();

    @NotNull
    boolean needCast() default true;

}
