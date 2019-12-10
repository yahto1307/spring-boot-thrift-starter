package com.yahto.hydra.thrift.server.processor;

import com.yahto.hydra.thrift.server.wrapper.ThriftServiceWrapper;
import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.TProcessor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yahto
 * @date 2019/12/9 8:44 PM
 * 多路复用注册
 */
public class TRegisterProcessor extends TMultiplexedProcessor {

    protected volatile Map<String, ThriftServiceWrapper> processorMetaMap;

    protected TRegisterProcessor() {
    }

    @Override
    public void registerProcessor(String serviceName, TProcessor processor) {
        super.registerProcessor(serviceName, processor);
    }

    public Map<String, ThriftServiceWrapper> getProcessorMap() {
        return new HashMap<>(processorMetaMap);
    }

    void setProcessorMap(Map<String, ThriftServiceWrapper> processorMetaMap) {
        this.processorMetaMap = processorMetaMap;
    }
}
