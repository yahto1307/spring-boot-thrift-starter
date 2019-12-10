package com.yahto.hydra.thrift.server.context;

import com.yahto.hydra.thrift.server.argument.TNonBlockingServerArgument;
import com.yahto.hydra.thrift.server.properties.ThriftServerProperties;
import com.yahto.hydra.thrift.server.wrapper.ThriftServiceWrapper;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TTransportException;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * @author yahto
 * @date 2019/12/9 8:59 PM
 */
public class TNonBlockingServerContext implements ContextBuilder {

    private static TNonBlockingServerContext serverContext;
    private TNonblockingServer.Args args;

    private TNonBlockingServerContext() {
    }

    public static TNonBlockingServerContext context() {
        if (Objects.isNull(serverContext)) {
            serverContext = new TNonBlockingServerContext();
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
        serverContext = (TNonBlockingServerContext) prepare();
        serverContext.args = new TNonBlockingServerArgument(serviceWrappers, properties);
        return new TNonblockingServer(serverContext.args);
    }

}
