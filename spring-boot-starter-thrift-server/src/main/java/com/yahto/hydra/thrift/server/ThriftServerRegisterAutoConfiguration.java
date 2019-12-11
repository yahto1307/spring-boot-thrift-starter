package com.yahto.hydra.thrift.server;

import com.yahto.hydra.thrift.server.exception.ThriftServerRegisterException;
import com.yahto.hydra.thrift.server.properties.ThriftServerProperties;
import com.yahto.hydra.thrift.server.properties.ThriftServerRegisterProperties;
import com.yahto.hydra.thrift.server.register.zk.CuratorClient;
import com.yahto.hydra.thrift.server.register.zk.ZookeeperThriftServerRegister;
import org.apache.curator.RetryPolicy;
import org.apache.curator.ensemble.EnsembleProvider;
import org.apache.curator.ensemble.fixed.FixedEnsembleProvider;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.ACLProvider;
import org.apache.curator.framework.api.CompressionProvider;
import org.apache.curator.framework.imps.GzipCompressionProvider;
import org.apache.curator.retry.BoundedExponentialBackoffRetry;
import org.apache.curator.utils.DefaultZookeeperFactory;
import org.apache.curator.utils.ZookeeperFactory;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.util.StringUtils;

import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.ThreadFactory;

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

    @Bean(initMethod = "registeredIn", destroyMethod = "registeredOut")
    @ConditionalOnMissingBean
    public ZookeeperThriftServerRegister zookeeperThriftServerRegister(ThriftServerProperties thriftServerProperties,
                                                                       CuratorClient curatorClient) {
        return new ZookeeperThriftServerRegister(thriftServerProperties, curatorClient);
    }


    @Bean
    @ConditionalOnMissingBean
    public EnsembleProvider ensembleProvider(ThriftServerProperties thriftServerProperties) {
        ThriftServerRegisterProperties thriftServerRegisterProperties = thriftServerProperties.getRegister();
        return new FixedEnsembleProvider(thriftServerRegisterProperties.getZkHost());
    }

    @Bean
    @ConditionalOnMissingBean
    public RetryPolicy retryPolicy(ThriftServerProperties thriftServerProperties) {
        ThriftServerRegisterProperties thriftServerRegisterProperties = thriftServerProperties.getRegister();
        ThriftServerRegisterProperties.Retry retry = thriftServerRegisterProperties.getRetry();
        return new BoundedExponentialBackoffRetry(retry.getBaseSleepTimeMs(), retry.getMaxSleepTimeMs(), retry.getMaxRetries());
    }

    @Bean
    @ConditionalOnMissingBean
    public CompressionProvider compressionProvider() {
        return new GzipCompressionProvider();
    }

    @Bean
    @ConditionalOnMissingBean
    public ZookeeperFactory zookeeperFactory() {
        return new DefaultZookeeperFactory();
    }

    @Bean
    @ConditionalOnMissingBean
    public ACLProvider aclProvider() {
        return new ACLProvider() {
            @Override
            public List<ACL> getDefaultAcl() {
                return ZooDefs.Ids.CREATOR_ALL_ACL;
            }

            @Override
            public List<ACL> getAclForPath(String path) {
                return ZooDefs.Ids.CREATOR_ALL_ACL;
            }
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public CuratorFrameworkFactory.Builder builder(EnsembleProvider ensembleProvider,
                                                   RetryPolicy retryPolicy,
                                                   CompressionProvider compressionProvider,
                                                   ZookeeperFactory zookeeperFactory,
                                                   ACLProvider aclProvider,
                                                   ThriftServerProperties thriftServerProperties) {
        ThriftServerRegisterProperties thriftServerRegisterProperties = thriftServerProperties.getRegister();
        String charset = thriftServerRegisterProperties.getCharset();
        CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder()
                .ensembleProvider(ensembleProvider)
                .retryPolicy(retryPolicy)
                .compressionProvider(compressionProvider)
                .zookeeperFactory(zookeeperFactory)
                .namespace(thriftServerRegisterProperties.getNamespace())
                .sessionTimeoutMs(thriftServerRegisterProperties.getSessionTimeoutMs())
                .connectionTimeoutMs(thriftServerRegisterProperties.getConnectionTimeoutMs())
                .maxCloseWaitMs(thriftServerRegisterProperties.getMaxCloseWaitMs())
                .defaultData(thriftServerRegisterProperties.getDefaultData().getBytes(Charset.forName(charset)))
                .canBeReadOnly(thriftServerRegisterProperties.isCanBeReadOnly());
        if (!thriftServerRegisterProperties.isUseContainerParentsIfAvailable()) {
            builder.dontUseContainerParents();
        }
        ThriftServerRegisterProperties.Auth auth = thriftServerRegisterProperties.getAuth();
        if (!StringUtils.isEmpty(auth.getAuth())) {
            builder.authorization(auth.getScheme(), auth.getAuth().getBytes(Charset.forName(charset)));
            builder.aclProvider(aclProvider);
        }
        String threadFactoryClassName = thriftServerRegisterProperties.getThreadFactoryClassName();
        if (!StringUtils.isEmpty(threadFactoryClassName)) {
            try {
                Class<?> cls = Class.forName(threadFactoryClassName);
                ThreadFactory threadFactory = (ThreadFactory) cls.newInstance();
                builder.threadFactory(threadFactory);
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                throw new ThriftServerRegisterException("config CuratorClient error", e);
            }
        }
        return builder;
    }

    @Bean(initMethod = "init", destroyMethod = "stop")
    @ConditionalOnMissingBean
    public CuratorClient curatorClient(CuratorFrameworkFactory.Builder builder) {
        return new CuratorClient(builder);
    }


}
