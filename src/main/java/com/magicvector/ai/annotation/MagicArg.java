package com.magicvector.ai.annotation;


import java.lang.annotation.*;

/**
 * @author shawn feng
 * @description
 * @date 2023/5/5 09:47
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MagicArg {

    @NotNull
    String name();

    @NotNull
    String description();

    @NotNull
    boolean required() default true;

}
