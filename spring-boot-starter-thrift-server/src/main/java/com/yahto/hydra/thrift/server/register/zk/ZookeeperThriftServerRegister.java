package com.yahto.hydra.thrift.server.register.zk;

import com.yahto.hydra.thrift.server.properties.ThriftServerProperties;
import com.yahto.hydra.thrift.server.properties.ThriftServerRegisterProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.CreateMode;

import java.net.Inet4Address;
import java.net.UnknownHostException;

/**
 * @author yahto
 * @date 2019/12/10 8:06 PM
 */
@Slf4j
public class ZookeeperThriftServerRegister {
    private ThriftServerProperties thriftServerProperties;

    private CuratorClient curatorClient;
    /**
     * 注册路径为/thrift/{serviceId}/{agentHost}:{port}
     */
    private static final String REGISTER_TEMPLATE = "/thrift/%s/%s:%s";

    public ZookeeperThriftServerRegister(ThriftServerProperties thriftServerProperties, CuratorClient curatorClient) {
        this.curatorClient = curatorClient;
        this.thriftServerProperties = thriftServerProperties;
    }

    private void registeredIn() throws UnknownHostException {
        ThriftServerRegisterProperties thriftServerRegisterProperties = thriftServerProperties.getRegister();
        String zkHost = thriftServerRegisterProperties.getZkHost();
        log.info("Service registered to server host:{}", zkHost);
        String serviceName = thriftServerProperties.getServiceId();
        String serverHostAddress = Inet4Address.getLocalHost().getHostAddress();
        int servicePort = thriftServerProperties.getPort();
        String serviceId = thriftServerProperties.getServiceId();
        log.info("Service id {}", serviceId);
        log.info("Service name {}", serviceName);
        log.info("Service host address {}, port {}", serverHostAddress, servicePort);
        String registeredPath = String.format(REGISTER_TEMPLATE, serviceId, serverHostAddress, servicePort);
        //服务注册到zookeeper
        if (!curatorClient.isExistNode(registeredPath)) {
            curatorClient.createNode(CreateMode.EPHEMERAL, registeredPath);
            log.info("registered service:{} on path:{}", serviceId, registeredPath);
        } else {
            log.warn("service {} is already registered,path:{}", serviceId, registeredPath);
        }
    }

    private void registeredOut() throws UnknownHostException {
        ThriftServerRegisterProperties thriftServerRegisterProperties = thriftServerProperties.getRegister();
        String zkHost = thriftServerRegisterProperties.getZkHost();
        log.info("Service registered to server host:{}", zkHost);
        String serviceName = thriftServerProperties.getServiceId();
        String serverHostAddress = Inet4Address.getLocalHost().getHostAddress();
        int servicePort = thriftServerProperties.getPort();
        String serviceId = thriftServerProperties.getServiceId();
        log.info("Service id {}", serviceId);
        log.info("Service name {}", serviceName);
        log.info("Service host address {}, port {}", serverHostAddress, servicePort);
        String registeredPath = String.format(REGISTER_TEMPLATE, serviceId, serverHostAddress, servicePort);
        //服务注册到zookeeper
        if (curatorClient.isExistNode(registeredPath)) {
            curatorClient.deleteNode(registeredPath);
            log.info("unregistered service:{} on path:{}", serviceId, registeredPath);
        } else {
            log.warn("service {} is already unregistered,path:{}", serviceId, registeredPath);
        }
    }


}
