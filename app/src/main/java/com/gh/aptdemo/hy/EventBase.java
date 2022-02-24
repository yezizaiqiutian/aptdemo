package com.gh.aptdemo.hy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: gh
 * @description:
 * @date: 2022/1/21.
 * @from:
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventBase
{
    Class<?> listenerType();

    String listenerSetter();

    String methodName();
}
