package com.yahto.hydra.thrift.server;

import com.yahto.hydra.thrift.server.properties.ThriftServerProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

/**
 * @author yahto
 * @date 2019/12/9 7:54 PM
 */
@Configuration
@ConditionalOnProperty(value = "spring.thrift.server.service-id")
@EnableConfigurationProperties({ThriftServerProperties.class})
@Slf4j
public class ThriftServerAutoConfiguration {

    private final ApplicationContext applicationContext;

    public ThriftServerAutoConfiguration(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
