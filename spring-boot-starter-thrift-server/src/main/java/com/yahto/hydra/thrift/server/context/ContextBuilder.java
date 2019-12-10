package com.yahto.hydra.thrift.server.context;

import com.yahto.hydra.thrift.server.properties.ThriftServerProperties;
import com.yahto.hydra.thrift.server.wrapper.ThriftServiceWrapper;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TTransportException;

import java.io.IOException;
import java.util.List;

/**
 * @author yahto
 * @date 2019/12/9 8:56 PM
 */
public interface ContextBuilder {
    ContextBuilder prepare();

    TServer buildThriftServer(ThriftServerProperties properties,
                              List<ThriftServiceWrapper> serviceWrappers)
            throws TTransportException, IOException;
}
