package com.yg0r2.fuse.domain;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

public final class FuseConfig {

    private final String serviceName;
    private final long serviceCallTimeout;
    private final int requestThreshold;
    private final float errorThresholdPercentage;
    private final long sleepingWindow;
    private final boolean logRequest;
    private final boolean logResponse;
    private final String fallbackMethodName;
    private final Map<String, Object> customArgs;

    private FuseConfig(ConfigBuilder configBuilder) {
        serviceName = Objects.requireNonNull(configBuilder.serviceName);
        serviceCallTimeout = Optional.ofNullable(configBuilder.serviceCallTimeout).orElse(5_000L);
        requestThreshold = Optional.ofNullable(configBuilder.requestThreshold).orElse(100);
        errorThresholdPercentage = Optional.ofNullable(configBuilder.errorThresholdPercentage).orElse(0.25F);
        sleepingWindow = Optional.ofNullable(configBuilder.sleepingWindow).orElse(10_000L);
        logRequest = Optional.ofNullable(configBuilder.logRequest).orElse(true);
        logResponse = Optional.ofNullable(configBuilder.logResponse).orElse(true);
        fallbackMethodName = Optional.ofNullable(configBuilder.fallbackMethodName).filter(Predicate.not(String::isBlank)).orElse(null);
        customArgs = Optional.ofNullable(configBuilder.customArgs).map(Collections::unmodifiableMap).orElse(Collections.emptyMap());
    }

    public String getServiceName() {
        return serviceName;
    }

    public long getServiceCallTimeout() {
        return serviceCallTimeout;
    }

    public int getRequestThreshold() {
        return requestThreshold;
    }

    public float getErrorThresholdPercentage() {
        return errorThresholdPercentage;
    }

    public long getSleepingWindow() {
        return sleepingWindow;
    }

    public boolean isLogRequest() {
        return logRequest;
    }

    public boolean isLogResponse() {
        return logResponse;
    }

    public String getFallbackMethodName() {
        return fallbackMethodName;
    }

    public Map<String, Object> getCustomArgs() {
        return customArgs;
    }

    public static final class ConfigBuilder {

        private String serviceName;
        private Long serviceCallTimeout;
        private Integer requestThreshold;
        private Float errorThresholdPercentage;
        private Long sleepingWindow;
        private Boolean logRequest;
        private Boolean logResponse;
        private String fallbackMethodName;
        private Map<String, Object> customArgs;

        public ConfigBuilder setServiceName(String serviceName) {
            this.serviceName = serviceName;

            return this;
        }

        public ConfigBuilder setServiceCallTimeout(Long serviceCallTimeout) {
            this.serviceCallTimeout = serviceCallTimeout;

            return this;
        }

        public ConfigBuilder setRequestThreshold(Integer requestThreshold) {
            this.requestThreshold = requestThreshold;

            return this;
        }

        public ConfigBuilder setErrorThresholdPercentage(Float errorThresholdPercentage) {
            this.errorThresholdPercentage = errorThresholdPercentage;

            return this;
        }

        public ConfigBuilder setSleepingWindow(Long sleepingWindow) {
            this.sleepingWindow = sleepingWindow;

            return this;
        }

        public ConfigBuilder setLogRequest(Boolean logRequest) {
            this.logRequest = logRequest;

            return this;
        }

        public ConfigBuilder setLogResponse(Boolean logResponse) {
            this.logResponse = logResponse;

            return this;
        }

        public ConfigBuilder setFallbackMethodName(String fallbackMethodName) {
            this.fallbackMethodName = fallbackMethodName;

            return this;
        }

        public ConfigBuilder setCustomArgs(Map<String, Object> customArgs) {
            this.customArgs = customArgs;

            return this;
        }

        public FuseConfig build() {
            return new FuseConfig(this);
        }

    }

}
