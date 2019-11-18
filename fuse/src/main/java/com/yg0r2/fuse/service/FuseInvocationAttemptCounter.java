package com.yg0r2.fuse.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

final class FuseInvocationAttemptCounter {

    private static final int DEFAULT_COUNTER_VALUE = 0;
    private static final Map<String, AtomicInteger> counterMap = new ConcurrentHashMap<>(10);

    static int start(String serviceName) {
        counterMap.putIfAbsent(serviceName, new AtomicInteger(DEFAULT_COUNTER_VALUE));

        return counterMap.get(serviceName).incrementAndGet();
    }

}
