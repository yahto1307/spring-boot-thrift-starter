package com.yahto.hydra.thrift.server;

import org.apache.thrift.server.TServer;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author yahto
 * @date 2019/12/10 5:58 PM
 */
public class ThriftServerGroup {
    private Queue<TServer> servers = new LinkedBlockingDeque<>();

    public ThriftServerGroup(TServer... servers) {
        if (Objects.isNull(servers) || servers.length == 0) {
            return;
        }
        this.servers.addAll(Arrays.asList(servers));
    }

    public ThriftServerGroup(List<TServer> servers) {
        if (CollectionUtils.isEmpty(servers)) {
            return;
        }
        this.servers.clear();
        this.servers.addAll(servers);
    }

    public Queue<TServer> getServers() {
        return servers;
    }

    public void setServers(Queue<TServer> servers) {
        this.servers = servers;
    }
}
