package com.yahto.hydra.thrift.server;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author yahto
 * @date 2019/12/10 7:55 PM
 * 注册服务到zk
 */
@Configuration
@AutoConfigureAfter(ThriftServerAutoConfiguration.class)
@ConditionalOnProperty(name = "spring.thrift.server.register.enabled", havingValue = "true")
@Import(ThriftServerAutoConfiguration.class)
public class ThriftServerRegisterAutoConfiguration {
    private static final String REGISTER_TEMPLATE = "/rpc/%s:%d";


}
