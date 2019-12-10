package com.yahto.hydra.thrift.server.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author yahto
 * @date 2019/12/9 7:54 PM
 */
@Data
@ConfigurationProperties(prefix = "spring.thrift.server")
public class ThriftServerProperties {

    /**
     * 服务名称ID
     */
    private String serviceId;

    /**
     * 服务端口号
     */
    private int port = 25000;
    /**
     * 服务的工作线程队列容量
     */
    private int workerQueueCapacity = 1000;

    /**
     * 服务模型(单线程/多线程/阻塞/非阻塞) 默认hsHa
     * <p>
     * simple: 单线程阻塞模型
     * nonBlocking: 单线程非阻塞模型
     * threadPool: 线程池同步模型
     * hsHa: 半同步半异步模型
     * threadedSelector: 线程池选择器模型
     * </p>
     */
    private String serviceModel = TServiceModel.SERVICE_MODEL_DEFAULT;

    private TThreadPoolServerProperties threadPool;

    private THsHaServerProperties hsHa;

    private TThreadedSelectorServerProperties threadedSelector;

    private ThriftServerRegisterProperties thriftServerRegisterProperties;
}
