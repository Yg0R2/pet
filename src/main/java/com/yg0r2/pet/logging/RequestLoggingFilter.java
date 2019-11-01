package com.yg0r2.pet.logging;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import javax.servlet.http.HttpServletRequest;

@Component
public final class RequestLoggingFilter extends CommonsRequestLoggingFilter {

    private static final String SESSION_ID_KEY = "sessionId";
    private static final String REQUEST_ID_KEY = "requestId";

    @Override
    protected boolean shouldLog(HttpServletRequest request) {
        return true;
    }

    @Override
    protected void beforeRequest(HttpServletRequest request, String message) {
        MDC.put(SESSION_ID_KEY, request.getHeader("SessionId"));
        MDC.put(REQUEST_ID_KEY, request.getHeader("RequestId"));
    }

    @Override
    protected void afterRequest(HttpServletRequest request, String message) {
        MDC.remove(SESSION_ID_KEY);
        MDC.remove(REQUEST_ID_KEY);
    }

}
