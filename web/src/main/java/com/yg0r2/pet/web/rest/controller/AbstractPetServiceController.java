package com.yg0r2.pet.web.rest.controller;

import com.yg0r2.core.api.model.RequestContext;
import com.yg0r2.pet.web.rest.RequestContextMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;

abstract class AbstractPetServiceController {

    @Autowired
    private RequestContextMapper requestContextMapper;

    @ModelAttribute
    private RequestContext createRequestContext(HttpServletRequest request) {
        return requestContextMapper.map(request);
    }

}
