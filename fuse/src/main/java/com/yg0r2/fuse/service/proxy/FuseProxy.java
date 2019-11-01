package com.yg0r2.fuse.service.proxy;

import com.yg0r2.fuse.domain.FuseConfig;
import com.yg0r2.fuse.exception.FuseException;
import com.yg0r2.fuse.service.FuseService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.ExecutorService;

public class FuseProxy implements InvocationHandler {

    private final Object obj;
    private final FuseConfig fuseConfig;
    private final ExecutorService executorService;

    public FuseProxy(Object obj, FuseConfig fuseConfig, ExecutorService executorService) {
        this.obj = Objects.requireNonNull(obj);
        this.fuseConfig = Objects.requireNonNull(fuseConfig);
        this.executorService = Objects.requireNonNull(executorService);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        FuseService fuseService = new FuseService(fuseConfig, executorService);

        try {
            return fuseService.invoke(() -> method.invoke(obj, args), args);
        }
        catch (FuseException exception) {
            if (fuseConfig.getFallbackMethodName() != null) {
                return fuseService.invokeFallback(obj, obj.getClass());
            }

            throw exception;
        }
    }

}
