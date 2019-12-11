package com.yahto.hydra.thrift.server.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author yahto
 * @date 2019/12/9 7:54 PM
 */

@ConfigurationProperties(prefix = "spring.thrift.server.hs-ha")
public class THsHaServerProperties {

    /**
     * 最少工作线程数量
     */
    private int minWorkerThreads = 5;

    /**
     * 最多工作线程数量
     */
    private int maxWorkerThreads = 20;

    /**
     * 线程的存活时间 (min)
     */
    private int keepAliveTime = 1;

    public int getMinWorkerThreads() {
        return minWorkerThreads;
    }

    public void setMinWorkerThreads(int minWorkerThreads) {
        this.minWorkerThreads = minWorkerThreads;
    }

    public int getMaxWorkerThreads() {
        return maxWorkerThreads;
    }

    public void setMaxWorkerThreads(int maxWorkerThreads) {
        this.maxWorkerThreads = maxWorkerThreads;
    }

    public int getKeepAliveTime() {
        return keepAliveTime;
    }

    public void setKeepAliveTime(int keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
    }
}
