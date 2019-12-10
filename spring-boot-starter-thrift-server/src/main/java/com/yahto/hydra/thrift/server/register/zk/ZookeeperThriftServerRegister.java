package com.yahto.hydra.thrift.server.register.zk;

import com.yahto.hydra.thrift.server.properties.ThriftServerProperties;
import com.yahto.hydra.thrift.server.properties.ThriftServerRegisterProperties;
import lombok.extern.slf4j.Slf4j;

import java.net.Inet4Address;
import java.net.UnknownHostException;

/**
 * @author yahto
 * @date 2019/12/10 8:06 PM
 */
@Slf4j
public class ZookeeperThriftServerRegister {
    private ThriftServerProperties thriftServerProperties;
    /**
     * 注册路径为/thrift/{serviceId}/{agentHost}:{port}
     */
    private static final String REGISTER_TEMPLATE = "/thrift/%s/%s:%s";

    public ZookeeperThriftServerRegister(ThriftServerProperties thriftServerProperties, String registerTemplate) {
        this.thriftServerProperties = thriftServerProperties;
    }

    private void registeredIn() throws UnknownHostException {
        ThriftServerRegisterProperties thriftServerRegisterProperties = thriftServerProperties.getThriftServerRegisterProperties();
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

    }

    private void registeredOut() {

    }


}
