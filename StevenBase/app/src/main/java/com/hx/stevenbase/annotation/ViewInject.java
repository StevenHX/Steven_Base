package com.hx.stevenbase.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * view注解
 * Created by Steven on 2018/1/28.
 */
@Target({ElementType.TYPE,ElementType.PARAMETER,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewInject {
    int value() default -1;
}
