package com.yahto.hydra.thrift.server.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "spring.thrift.server.thread-pool")
public class TThreadPoolServerProperties {

    /**
     * 最少工作线程的数量
     */
    private int minWorkerThreads = 5;

    /**
     * 最大工作线程的数量
     */
    private int maxWorkerThreads = 20;

    /**
     * 线程请求超时时间
     */
    private int requestTimeout = 5;

    /**
     * 线程的存活时间
     */
    private int keepAliveTime = 1;
}
