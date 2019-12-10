package com.yahto.hydra.thrift.server.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author yahto
 * @date 2019/12/9 7:54 PM
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(ThriftServerConfigurationSelector.class)
public @interface EnableThriftServer {
}
