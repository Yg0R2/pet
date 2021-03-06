package com.yg0r2.fuse.service;

import com.yg0r2.fuse.domain.FuseConfig;
import com.yg0r2.fuse.exception.FuseException;
import com.yg0r2.fuse.exception.FuseRequestTimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class FuseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FuseService.class);

    private final FuseConfig fuseConfig;
    private final ExecutorService executorService;

    public FuseService(FuseConfig fuseConfig, ExecutorService executorService) {
        this.fuseConfig = fuseConfig;
        this.executorService = executorService;
    }

    public Object invoke(Callable<Object> callable, Object[] args) {
        if (fuseConfig.isLogRequest()) {
            LOGGER.info(String.format("Calling '%s' service with request: %s", fuseConfig.getServiceName(), getRequest(args)));
        }

        Object result = invoke(callable);

        if (fuseConfig.isLogResponse()) {
            LOGGER.info(String.format("Service '%s' call succeeded with response: %s", fuseConfig.getServiceName(), result));
        }

        return result;
    }

    public Object invokeFallback(Object obj, Class<?> clazz, Object... args) {
        LOGGER.info("Execute fallback on service: '{}'", fuseConfig.getServiceName());

        String fallbackMethodName = fuseConfig.getFallbackMethodName();

        try {
            Method method = clazz.getDeclaredMethod(fallbackMethodName);

            //noinspection JavaReflectionInvocation
            return method.invoke(obj, args);
        }
        catch (Throwable throwable) {
            throw new FuseException(String.format("Failed execute fallback method: '%s' on service '%s'", fallbackMethodName, fuseConfig.getServiceName()), throwable);
        }
    }

    private Object getRequest(Object[] args) {
        if ((args == null) || (args.length == 0)) {
            return null;
        }

        return args[0];
    }

    private Object invoke(Callable<Object> invocationCallable) {
        try {
            Future<Object> future = executorService.submit(invocationCallable);

            return future.get(fuseConfig.getServiceCallTimeout(), TimeUnit.MILLISECONDS);
        }
        catch (TimeoutException exception) {
            throw new FuseRequestTimeoutException(String.format("Request timed out; execution took more then %s ms", fuseConfig.getServiceCallTimeout()), exception);
        }
        catch (Throwable throwable) {
            throw new FuseException(throwable);
        }
    }

}
