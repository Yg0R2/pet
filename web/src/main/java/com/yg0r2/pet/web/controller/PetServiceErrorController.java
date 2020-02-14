package com.yg0r2.pet.web.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Controller
public class PetServiceErrorController implements ErrorController {

    private static final String ERROR_PATH = "/error";

    @RequestMapping(path = ERROR_PATH)
    public String error(HttpServletRequest request) {
        Integer errorStatusCode = getErrorStatusCode(request);

        if (Objects.equals(errorStatusCode, HttpStatus.FORBIDDEN.value())) {
            return "error_403";
        }
        else if (Objects.equals(errorStatusCode, HttpStatus.NOT_FOUND.value())) {
            return "error_404";
        }

        return "error_page";
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

    private Integer getErrorStatusCode(HttpServletRequest request) {
        Object errorStatusCode = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        return Objects.isNull(errorStatusCode) ? null : Integer.valueOf(errorStatusCode.toString());
    }

}
