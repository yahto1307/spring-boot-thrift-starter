package com.yahto.hydra.thrift.server.context;

import com.yahto.hydra.thrift.server.argument.TThreadedSelectorServerArgument;
import com.yahto.hydra.thrift.server.properties.ThriftServerProperties;
import com.yahto.hydra.thrift.server.wrapper.ThriftServiceWrapper;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TTransportException;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * @author yahto
 * @date 2019/12/9 9:00 PM
 */
public class TThreadedSelectorServerContext implements ContextBuilder {

    private static TThreadedSelectorServerContext serverContext;
    private TThreadedSelectorServer.Args args;

    private TThreadedSelectorServerContext() {
    }

    public static TThreadedSelectorServerContext context() {
        if (Objects.isNull(serverContext)) {
            serverContext = new TThreadedSelectorServerContext();
        }
        return serverContext;
    }

    @Override
    public ContextBuilder prepare() {
        return context();
    }

    @Override
    public TServer buildThriftServer(ThriftServerProperties properties,
                                     List<ThriftServiceWrapper> serviceWrappers)
            throws TTransportException, IOException {
        serverContext = (TThreadedSelectorServerContext) prepare();
        serverContext.args = new TThreadedSelectorServerArgument(serviceWrappers, properties);
        return new TThreadedSelectorServer(serverContext.args);
    }
}
