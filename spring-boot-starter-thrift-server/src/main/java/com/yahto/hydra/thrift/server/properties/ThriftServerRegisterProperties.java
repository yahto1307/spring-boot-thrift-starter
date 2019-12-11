package com.yahto.hydra.thrift.server.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author yahto
 * @date 2019/12/10 7:57 PM
 */
@ConfigurationProperties(prefix = "spring.thrift.server.register")
public class ThriftServerRegisterProperties {
    /**
     * 是否允许服务注册
     */
    private Boolean enabled = false;

    /**
     * zookeeper的地址
     */
    private String zkHost;

    private String namespace;
    private String charset = "utf8";
    private int sessionTimeoutMs = 60000;
    private int connectionTimeoutMs = 15000;
    private int maxCloseWaitMs = 1000;
    private String defaultData = "";
    private boolean canBeReadOnly = false;
    private boolean useContainerParentsIfAvailable = true;
    private String threadFactoryClassName;
    private Retry retry = new Retry();
    private Auth auth = new Auth();

    public static class Retry {
        private int maxSleepTimeMs = 10000;
        private int baseSleepTimeMs = 1000;
        private int maxRetries = 3;

        public int getMaxSleepTimeMs() {
            return maxSleepTimeMs;
        }

        public void setMaxSleepTimeMs(int maxSleepTimeMs) {
            this.maxSleepTimeMs = maxSleepTimeMs;
        }

        public int getBaseSleepTimeMs() {
            return baseSleepTimeMs;
        }

        public void setBaseSleepTimeMs(int baseSleepTimeMs) {
            this.baseSleepTimeMs = baseSleepTimeMs;
        }

        public int getMaxRetries() {
            return maxRetries;
        }

        public void setMaxRetries(int maxRetries) {
            this.maxRetries = maxRetries;
        }
    }

    public static class Auth {
        private String scheme = "digest";
        private String auth;

        public String getScheme() {
            return scheme;
        }

        public void setScheme(String scheme) {
            this.scheme = scheme;
        }

        public String getAuth() {
            return auth;
        }

        public void setAuth(String auth) {
            this.auth = auth;
        }
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getZkHost() {
        return zkHost;
    }

    public void setZkHost(String zkHost) {
        this.zkHost = zkHost;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public int getSessionTimeoutMs() {
        return sessionTimeoutMs;
    }

    public void setSessionTimeoutMs(int sessionTimeoutMs) {
        this.sessionTimeoutMs = sessionTimeoutMs;
    }

    public int getConnectionTimeoutMs() {
        return connectionTimeoutMs;
    }

    public void setConnectionTimeoutMs(int connectionTimeoutMs) {
        this.connectionTimeoutMs = connectionTimeoutMs;
    }

    public int getMaxCloseWaitMs() {
        return maxCloseWaitMs;
    }

    public void setMaxCloseWaitMs(int maxCloseWaitMs) {
        this.maxCloseWaitMs = maxCloseWaitMs;
    }

    public String getDefaultData() {
        return defaultData;
    }

    public void setDefaultData(String defaultData) {
        this.defaultData = defaultData;
    }

    public boolean isCanBeReadOnly() {
        return canBeReadOnly;
    }

    public void setCanBeReadOnly(boolean canBeReadOnly) {
        this.canBeReadOnly = canBeReadOnly;
    }

    public boolean isUseContainerParentsIfAvailable() {
        return useContainerParentsIfAvailable;
    }

    public void setUseContainerParentsIfAvailable(boolean useContainerParentsIfAvailable) {
        this.useContainerParentsIfAvailable = useContainerParentsIfAvailable;
    }

    public String getThreadFactoryClassName() {
        return threadFactoryClassName;
    }

    public void setThreadFactoryClassName(String threadFactoryClassName) {
        this.threadFactoryClassName = threadFactoryClassName;
    }

    public Retry getRetry() {
        return retry;
    }

    public void setRetry(Retry retry) {
        this.retry = retry;
    }

    public Auth getAuth() {
        return auth;
    }

    public void setAuth(Auth auth) {
        this.auth = auth;
    }
}
