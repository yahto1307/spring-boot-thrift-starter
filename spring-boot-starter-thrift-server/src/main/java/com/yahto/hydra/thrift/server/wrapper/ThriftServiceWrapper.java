package com.yahto.hydra.thrift.server.wrapper;

import lombok.Data;

import java.util.Objects;

/**
 * @author yahto
 * @date 2019/12/9 8:38 PM
 * 对每一个代理类服务的包装
 */
@Data
public class ThriftServiceWrapper {
    private String thriftServiceName;

    private String thriftServiceSignature;

    private Class<?> type;

    private Class<?> ifaceType;

    private double version;

    private final Object thriftService;

    private final static Double DEFAULT_VERSION = 1.0;

    public ThriftServiceWrapper(String thriftServiceName, Class<?> type, Object thriftService) {
        this(thriftServiceName, type, thriftService, DEFAULT_VERSION);
    }

    public ThriftServiceWrapper(String thriftServiceName, Class<?> type, Object thriftService, double version) {
        this.thriftServiceName = thriftServiceName;
        this.type = type;
        this.thriftService = thriftService;
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ThriftServiceWrapper that = (ThriftServiceWrapper) o;

        return Objects.equals(thriftServiceSignature, that.thriftServiceSignature);
    }

    @Override
    public int hashCode() {
        return thriftServiceSignature != null ? thriftServiceSignature.hashCode() : 0;
    }

    @Override
    public String toString() {

        return "ThriftServiceWrapper" + "{" +
                "thriftServiceName=" + thriftServiceName + "," +
                "thriftServiceSignature=" + thriftServiceSignature + "," +
                "type=" + type + "," +
                "ifaceType=" + ifaceType + "," +
                "version=" + version + "," +
                "thriftService=" + thriftService +
                "}";
    }
}
