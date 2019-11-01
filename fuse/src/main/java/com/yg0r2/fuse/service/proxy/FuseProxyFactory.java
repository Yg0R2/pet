package com.yg0r2.fuse.service.proxy;

import com.yg0r2.fuse.domain.FuseConfig;
import com.yg0r2.fusebox.FuseBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Proxy;
import java.util.Objects;
import java.util.concurrent.ExecutorService;

@Component
public class FuseProxyFactory {

    @Autowired
    private FuseBox fuseBox;
    @Autowired
    private ExecutorService fuseBoxExecutorService;

    @SuppressWarnings("unchecked")
    public <T> T create(Object obj, Class[] interfaces, String serviceName) {
        return (T) Proxy.newProxyInstance(getClassLoader(obj), interfaces, createFuseProxy(obj, getFuseConfig(serviceName)));
    }

    private FuseProxy createFuseProxy(Object obj, FuseConfig fuseConfig) {
        return new FuseProxy(obj, fuseConfig, fuseBoxExecutorService);
    }

    private ClassLoader getClassLoader(Object obj) {
        return Objects.requireNonNull(obj).getClass().getClassLoader();
    }

    private FuseConfig getFuseConfig(String serviceName) {
        return fuseBox.getFuseConfig(serviceName);
    }

}
