package com.yahto.hydra.thrift.server.wrapper;

import lombok.Data;

import java.util.Objects;

/**
 * @author yahto
 * @date 2019/12/9 8:38 PM
 * 对每一个代理类服务的包装
 */
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

    public String getThriftServiceName() {
        return thriftServiceName;
    }

    public void setThriftServiceName(String thriftServiceName) {
        this.thriftServiceName = thriftServiceName;
    }

    public String getThriftServiceSignature() {
        return thriftServiceSignature;
    }

    public void setThriftServiceSignature(String thriftServiceSignature) {
        this.thriftServiceSignature = thriftServiceSignature;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public Class<?> getIfaceType() {
        return ifaceType;
    }

    public void setIfaceType(Class<?> ifaceType) {
        this.ifaceType = ifaceType;
    }

    public double getVersion() {
        return version;
    }

    public void setVersion(double version) {
        this.version = version;
    }

    public Object getThriftService() {
        return thriftService;
    }

    public static Double getDefaultVersion() {
        return DEFAULT_VERSION;
    }
}
