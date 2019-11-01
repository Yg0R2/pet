package com.yg0r2.fuse.service.aop;

import com.yg0r2.fuse.annotations.Fuse;
import com.yg0r2.fuse.domain.FuseConfig;
import com.yg0r2.fuse.exception.FuseException;
import com.yg0r2.fuse.service.FuseService;
import com.yg0r2.fusebox.FuseBox;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;

@Aspect
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public final class FuseAspect {

    @Autowired
    private FuseBox fuseBox;
    @Autowired
    private ExecutorService fuseBoxExecutorService;

    @Around("@annotation(com.yg0r2.fuse.annotations.Fuse) && execution(* *(..))")
    public Object execute(ProceedingJoinPoint proceedingJoinPoint) {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();

        FuseConfig fuseConfig = getFuseConfig(methodSignature);

        FuseService fuseService = new FuseService(fuseConfig, fuseBoxExecutorService);

        try {
            return fuseService.invoke(() -> invoke(proceedingJoinPoint), proceedingJoinPoint.getArgs());
        }
        catch (FuseException exception) {
            String fallbackMethodName = getFallbackMethodName(methodSignature, fuseConfig);

            if (fallbackMethodName != null) {
                return invokeFallback(fallbackMethodName, proceedingJoinPoint, fuseService);
            }

            throw exception;
        }
    }

    private FuseConfig getFuseConfig(MethodSignature methodSignature) {
        Fuse fuse = getFuseAnnotation(methodSignature);

        return fuseBox.getFuseConfig(fuse.serviceName());
    }

    private Fuse getFuseAnnotation(MethodSignature methodSignature) {
        return methodSignature.getMethod().getAnnotation(Fuse.class);
    }

    private Object invoke(ProceedingJoinPoint proceedingJoinPoint) {
        try {
            return proceedingJoinPoint.proceed();
        }
        catch (Throwable throwable) {
            throw new FuseException(throwable);
        }
    }

    private String getFallbackMethodName(MethodSignature methodSignature, FuseConfig fuseConfig) {
        String methodName = getFuseAnnotation(methodSignature).fallbackMethodName();

        if (methodName.isEmpty()) {
            methodName = fuseConfig.getFallbackMethodName();
        }

        return methodName;
    }

    private Object invokeFallback(String fallbackMethodName, ProceedingJoinPoint proceedingJoinPoint, FuseService fuseService) {
        Class<?> clazz = proceedingJoinPoint.getThis().getClass();

        return fuseService.invokeFallback(fallbackMethodName, proceedingJoinPoint.getThis(), clazz);
    }

}
