package com.yg0r2.pet.web.rest;

import com.yg0r2.core.api.model.RequestContext;
import com.yg0r2.core.api.model.RequestParams;
import com.yg0r2.pet.api.model.PetServiceRequestContext;
import com.yg0r2.pet.web.rest.exceptions.InvalidHeaderException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.Optional;

@Component
public class RequestContextMapper {

    @ModelAttribute("petServiceRequestContext")
    public RequestContext map(HttpServletRequest request) {
        return new PetServiceRequestContext.Builder()
            .withRequestId(getHeader(request, RequestParams.REQUEST_ID))
            .withSessionId(getHeader(request, RequestParams.SESSION_ID))
            .build();
    }

    private String getHeader(HttpServletRequest request, RequestParams requestParam) {
        return Optional.of(requestParam.getValue())
            .map(request::getHeader)
            .map(Objects::requireNonNull)
            .orElseThrow(() -> new InvalidHeaderException("Required header parameter is missing: " + requestParam.getValue()));
    }

}
