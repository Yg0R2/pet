package com.yg0r2.pet.logging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Map;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public final class LoggingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingService.class);
    private static final String PATTERN = "%s: {\"method\": \"%s\", \"path\": \"%s\", \"headers\": %s, \"body\": %s}";

    @Autowired
    private ObjectMapper objectMapper;

    public void logRequest(HttpServletRequest request, Object body) {
        Map<String, String> headers = getHeaders(request.getHeaderNames().asIterator(), request::getHeader);

        LOGGER.info(String.format(PATTERN, "Request", request.getMethod(), request.getRequestURL().toString(), headers, getBodyAsJson(body)));
    }

    public void logResponse(HttpServletRequest request, HttpServletResponse response, Object body) {
        Map<String, String> headers = getHeaders(response.getHeaderNames().iterator(), response::getHeader);

        LOGGER.info(String.format(PATTERN, "Response", request.getMethod(), request.getRequestURL().toString(), headers, getBodyAsJson(body)));
    }

    private Map<String, String> getHeaders(Iterator<String> headerNames, Function<String, String> valueMappingFunction) {
        Spliterator<String> spliterator = Spliterators.spliteratorUnknownSize(headerNames, Spliterator.IMMUTABLE);

        return StreamSupport.stream(spliterator, false)
            .collect(Collectors.toMap(Function.identity(), valueMappingFunction));
    }

    private String getBodyAsJson(Object body) {
        try {
            return objectMapper.writeValueAsString(body);
        }
        catch (JsonProcessingException e) {
            LOGGER.error("Failed to create JSON from body: " + body);
        }

        return String.valueOf(body);
    }

}
