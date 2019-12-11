package com.yahto.hydra.thrift.server;

import com.yahto.hydra.thrift.server.annotation.ThriftService;
import com.yahto.hydra.thrift.server.context.AbstractThriftServerContext;
import com.yahto.hydra.thrift.server.context.ThriftServerContext;
import com.yahto.hydra.thrift.server.exception.ThriftServerException;
import com.yahto.hydra.thrift.server.exception.ThriftServerInstantiateException;
import com.yahto.hydra.thrift.server.properties.ThriftServerProperties;
import com.yahto.hydra.thrift.server.properties.ThriftServerRegisterProperties;
import com.yahto.hydra.thrift.server.wrapper.ThriftServiceWrapper;
import com.yahto.hydra.thrift.server.wrapper.ThriftServiceWrapperFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.transport.TTransportException;
import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.Advised;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yahto
 * @date 2019/12/9 7:54 PM
 * 服务启动
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

    @Bean
    @ConditionalOnMissingBean
    public ThriftServerGroup thriftServerGroup(ThriftServerProperties properties) throws TTransportException, IOException {
        //注解获取ThriftService bean
        String[] beanNames = applicationContext.getBeanNamesForAnnotation(ThriftService.class);
        if (beanNames.length == 0) {
            log.error("Can't search any thrift service annotated with @ThriftService");
            throw new ThriftServerException("Can not found any thrift service");
        }
        //获取service包装
        List<ThriftServiceWrapper> serviceWrappers = Arrays.stream(beanNames).distinct().map(beanName -> {
            Object bean = applicationContext.getBean(beanName);
            Object target = bean;
            ThriftService thriftService = bean.getClass().getAnnotation(ThriftService.class);
            String thriftServiceName = StringUtils.isEmpty(thriftService.value()) ? beanName : thriftService.value();
            if (target instanceof Advised) {
                //AOP 代理 获取代理对象
                final Object targetBean = target;
                TargetSource targetSource = ((Advised) target).getTargetSource();
                if (log.isDebugEnabled()) {
                    log.debug("Target object {} uses cglib proxy", target);
                }
                try {
                    target = targetSource.getTarget();
                } catch (Exception e) {
                    throw new ThriftServerInstantiateException("Failed to get target bean from " + target, e);
                }
                return ThriftServiceWrapperFactory.wrapper(properties.getServiceId(), thriftServiceName, targetBean, thriftService.version());
            }
            return ThriftServiceWrapperFactory.wrapper(properties.getServiceId(), thriftServiceName, target, thriftService.version());
        }).collect(Collectors.toList());
        AbstractThriftServerContext serverContext = new ThriftServerContext(properties, serviceWrappers);
        return new ThriftServerGroup(serverContext.buildServer());
    }

    @Bean
    @ConditionalOnMissingBean
    public ThriftServerBootstrap thriftServerBootstrap(ThriftServerGroup thriftServerGroup) {
        return new ThriftServerBootstrap(thriftServerGroup);
    }
}
