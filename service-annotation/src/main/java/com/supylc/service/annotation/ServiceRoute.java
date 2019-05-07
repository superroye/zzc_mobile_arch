package com.supylc.service.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * create by elileo on 2019/2/27
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
public @interface ServiceRoute {

}
