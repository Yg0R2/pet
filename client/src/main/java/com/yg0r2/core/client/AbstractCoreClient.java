package com.yg0r2.core.client;

import com.yg0r2.core.api.model.RequestContext;
import com.yg0r2.core.api.model.RequestParams;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public abstract class AbstractCoreClient implements CoreClient {

    protected HttpHeaders createHeaders(RequestContext requestContext) {
        HttpHeaders headers = new HttpHeaders();

        headers.add(RequestParams.AUTHORIZATION.getValue(), requestContext.getAuthorization());
        headers.add(RequestParams.CONTENT_TYPE.getValue(), MediaType.APPLICATION_JSON_VALUE);
        headers.add(RequestParams.REQUEST_ID.getValue(), requestContext.getRequestId());
        headers.add(RequestParams.SESSION_ID.getValue(), requestContext.getSessionId());

        return headers;
    }

}
