package com.yg0r2.core.api.model;

public interface RequestContext<T extends CoreEntry> {

    String getRequestId();

    String getSessionId();

}
