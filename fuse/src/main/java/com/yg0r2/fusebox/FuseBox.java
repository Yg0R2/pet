package com.yg0r2.fusebox;

import com.yg0r2.fuse.domain.FuseConfig;
import com.yg0r2.fuse.domain.FuseState;
import com.yg0r2.fusebox.exception.NoSuchFusedServiceException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public final class FuseBox {

    private final Map<String, FuseConfig> fuseConfigMap;
    private final Map<String, AtomicInteger> fuseErrorCounter;

    public FuseBox(Map<String, FuseConfig> fuseConfigMap) {
        this.fuseConfigMap = Map.copyOf(fuseConfigMap);
        fuseErrorCounter = new HashMap<>();
    }

    public FuseConfig getFuseConfig(String serviceName) {
        Objects.requireNonNull(serviceName);

        return Optional.of(serviceName)
            .map(fuseConfigMap::get)
            .orElseThrow(() -> new NoSuchFusedServiceException("Fuse is missing for service: " + serviceName));
    }

    public FuseState getFuseState() {
        return null;
    }

}
