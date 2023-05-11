package cn.lanehub.ai.annotation;

import com.sun.istack.internal.NotNull;

import java.lang.annotation.*;

/**
 * @author shawn feng
 * @description
 * @date 2023/5/5 09:47
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OmniArg {

    @NotNull
    String name();

    @NotNull
    String description();

    boolean required() default true;

}
