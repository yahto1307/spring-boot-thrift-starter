package com.yahto.hydra.thrift.server.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author yahto
 * @date 2019/12/9 7:54 PM
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Component
public @interface ThriftService {

    @AliasFor("name")
    String value() default "";

    @AliasFor("value")
    String name() default "";

    double version() default 1.0;
}
