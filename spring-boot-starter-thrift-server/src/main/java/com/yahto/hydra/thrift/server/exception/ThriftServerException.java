package com.yahto.hydra.thrift.server.exception;

/**
 * @author yahto
 * @date 2019/12/9 7:54 PM
 */
public class ThriftServerException extends RuntimeException {

    public ThriftServerException(String message) {
        super(message);
    }

    public ThriftServerException(String message, Throwable t) {
        super(message, t);
    }
}
