package com.yg0r2.pet.web.rest;

import com.yg0r2.core.api.model.RequestContext;
import com.yg0r2.core.api.model.HeaderParams;
import com.yg0r2.pet.api.model.PetServiceRequestContext;
import com.yg0r2.pet.web.rest.exceptions.InvalidHeaderException;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Objects;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;

@Component
public class RequestContextMapper {

    @ModelAttribute("petServiceRequestContext")
    public RequestContext map(HttpServletRequest request) {
        return new PetServiceRequestContext.Builder()
            .withAuthorization(getHeader(request, HeaderParams.AUTHORIZATION))
            .withRequestId(getRequiredHeader(request, HeaderParams.REQUEST_ID))
            .withSessionId(getRequiredHeader(request, HeaderParams.SESSION_ID))
            .build();
    }

    private String getHeader(HttpServletRequest request, HeaderParams headerParams) {
        return request.getHeader(headerParams.getValue());
    }

    private String getRequiredHeader(HttpServletRequest request, HeaderParams headerParams) {
        return Optional.of(headerParams.getValue())
            .map(request::getHeader)
            .map(Objects::requireNonNull)
            .orElseThrow(() -> new InvalidHeaderException("Required header parameter is missing: " + headerParams.getValue()));
    }

}
