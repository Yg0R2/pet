package com.yg0r2.fusebox;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class FuseBoxThreadFactory implements ThreadFactory {

    private final AtomicInteger counter = new AtomicInteger(0);

    private final String threadBaseName;

    public FuseBoxThreadFactory(String threadBaseName) {
        this.threadBaseName = threadBaseName;
    }

    @Override
    public Thread newThread(Runnable runnable) {
        Thread thread = new Thread(runnable, createThreadName());

        thread.setDaemon(false);

        return thread;
    }

    private String createThreadName() {
        return threadBaseName + counter.getAndIncrement();
    }

}
