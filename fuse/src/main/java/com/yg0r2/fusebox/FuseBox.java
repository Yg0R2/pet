package com.yg0r2.fusebox;

import com.yg0r2.fuse.domain.FuseConfig;
import com.yg0r2.fusebox.exception.NoSuchFusedServiceException;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public final class FuseBox {

    private final Map<String, FuseConfig> fuseBoxConfigurationMap;

    public FuseBox(Map<String, FuseConfig> fuseBoxConfigurationMap) {
        this.fuseBoxConfigurationMap = fuseBoxConfigurationMap;
    }

    public FuseConfig getFuseConfig(String serviceName) {
        Objects.requireNonNull(serviceName);

        return Optional.of(serviceName)
            .map(fuseBoxConfigurationMap::get)
            .orElseThrow(() -> new NoSuchFusedServiceException("Fuse is missing for service: " + serviceName));
    }

}
