package com.yahto.hydra.thrift.server.exception;

/**
 * @author yahto
 * @date 2019/12/9 7:54 PM
 */
public class ThriftServerInstantiateException extends ThriftServerException {

    public ThriftServerInstantiateException(String message) {
        super(message);
    }

    public ThriftServerInstantiateException(String message, Throwable t) {
        super(message, t);
    }
}
