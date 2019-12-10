package com.yahto.hydra.thrift.server.exception;

/**
 * @author yahto
 * @date 2019/12/10 8:58 PM
 */
public class ThriftServerRegisterException extends ThriftServerException {

    public ThriftServerRegisterException(String message) {
        super(message);
    }

    public ThriftServerRegisterException(String message, Throwable t) {
        super(message, t);
    }
}
