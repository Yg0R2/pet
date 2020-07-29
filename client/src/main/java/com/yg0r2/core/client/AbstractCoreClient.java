package com.yg0r2.core.client;

import com.yg0r2.core.api.model.RequestContext;
import com.yg0r2.core.api.model.HeaderParams;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public abstract class AbstractCoreClient implements CoreClient {

    protected HttpHeaders createHeaders(RequestContext requestContext) {
        HttpHeaders headers = new HttpHeaders();

        headers.add(HeaderParams.AUTHORIZATION.getValue(), requestContext.getAuthorization());
        headers.add(HeaderParams.CONTENT_TYPE.getValue(), MediaType.APPLICATION_JSON_VALUE);
        headers.add(HeaderParams.REQUEST_ID.getValue(), requestContext.getRequestId());
        headers.add(HeaderParams.SESSION_ID.getValue(), requestContext.getSessionId());

        return headers;
    }

}
