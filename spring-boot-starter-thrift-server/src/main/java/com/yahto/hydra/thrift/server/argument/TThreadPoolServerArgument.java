package com.yahto.hydra.thrift.server.argument;

import com.yahto.hydra.thrift.server.exception.ThriftServerException;
import com.yahto.hydra.thrift.server.processor.TRegisterProcessor;
import com.yahto.hydra.thrift.server.processor.TRegisterProcessorFactory;
import com.yahto.hydra.thrift.server.properties.TThreadPoolServerProperties;
import com.yahto.hydra.thrift.server.properties.ThriftServerProperties;
import com.yahto.hydra.thrift.server.wrapper.ThriftServiceWrapper;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TFastFramedTransport;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author yahto
 * @date 2019/12/9 8:55 PM
 */
public class TThreadPoolServerArgument extends TThreadPoolServer.Args {

    private Map<String, ThriftServiceWrapper> processorMap = new HashMap<>();

    public TThreadPoolServerArgument(List<ThriftServiceWrapper> serviceWrappers, ThriftServerProperties properties)
            throws TTransportException, IOException {
        super(new TServerSocket(new ServerSocket(properties.getPort())));

        transportFactory(new TFastFramedTransport.Factory());
        protocolFactory(new TCompactProtocol.Factory());

        TThreadPoolServerProperties threadPoolProperties = properties.getThreadPool();

        minWorkerThreads(threadPoolProperties.getMinWorkerThreads());
        maxWorkerThreads(threadPoolProperties.getMaxWorkerThreads());
        requestTimeout(threadPoolProperties.getRequestTimeout());

        executorService(createInvokerPool(properties));

        try {
            TRegisterProcessor registerProcessor = TRegisterProcessorFactory.registerProcessor(serviceWrappers);

            processorMap.clear();
            processorMap.putAll(registerProcessor.getProcessorMap());

            processor(registerProcessor);
        } catch (Exception e) {
            throw new ThriftServerException("Can not create multiplexed processor for " + serviceWrappers, e);
        }
    }

    private ExecutorService createInvokerPool(ThriftServerProperties properties) {
        TThreadPoolServerProperties threadPoolProperties = properties.getThreadPool();

        return new ThreadPoolExecutor(
                threadPoolProperties.getMinWorkerThreads(),
                threadPoolProperties.getMaxWorkerThreads(),
                threadPoolProperties.getKeepAliveTime(), TimeUnit.MINUTES,
                new LinkedBlockingQueue<>(properties.getWorkerQueueCapacity()));
    }

    public Map<String, ThriftServiceWrapper> getProcessorMap() {
        return processorMap;
    }

}
