package com.yahto.hydra.thrift.server.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "spring.thrift.server.threaded-selector")
public class TThreadedSelectorServerProperties {

    /**
     * 每个Selector线程可接收连接的阻塞队列大小
     */
    private int acceptQueueSizePerThread = 4;

    /**
     * 接收连接的Selector线程数量
     */
    private int selectorThreads = 2;

    /**
     * 处理任务的最小工作线程池大小
     * 如果为0，默认由Selector线程进行处理
     */
    private int minWorkerThreads = 5;

    /**
     * 处理任务的最大工作线程池大小
     */
    private int maxWorkerThreads = 20;

    /**
     * 工作线程池中的线程的存活时间
     */
    private int keepAliveTime = 5;
}
