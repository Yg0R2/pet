package com.yg0r2.pet.web.rest.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.yg0r2.core.api.model.RequestContext;
import com.yg0r2.core.api.model.RequestParams;
import com.yg0r2.pet.PetApplication;
import com.yg0r2.pet.api.model.PetEntry;
import com.yg0r2.pet.api.model.PetServiceRequestContext;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.net.URI;

@SpringBootTest(classes = PetApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ComponentScan(basePackages = "com.yg0r2")
public class PetControllerTest {

    private static final long ID = 123L;
    private static final String TITLE = "petTitle";
    private static final String CREATE_URL = "/api/pet";
    private static final String GET_ALL_URL = "/api/pet";
    private static final String REQUEST_ID = "requestId";
    private static final String SESSION_ID = "sessionId";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @Test
    public void testGetAll() throws Exception {
        HttpEntity<PetEntry> httpEntity = createHttpEntity(createRequestContext());

        ResponseEntity<String> actualResponse = //restTemplate.postForEntity(getUrl(CREATE_URL), httpEntity, String.class);
            restTemplate.exchange(getUrl(GET_ALL_URL), HttpMethod.GET, httpEntity, String.class);

        requestMappingHandlerMapping.getHandlerMethods().keySet()
            .forEach(System.out::println);

        assertEquals("", actualResponse.getBody());
    }

    public void testPost() throws Exception {
        PetEntry petEntry = createPetEntry();
        HttpEntity<PetEntry> httpEntity = createHttpEntity(petEntry, createRequestContext());

        ResponseEntity<String> actualResponse = //restTemplate.postForEntity(getUrl(CREATE_URL), httpEntity, String.class);
            restTemplate.exchange(getUrl(CREATE_URL), HttpMethod.POST, httpEntity, String.class);

        requestMappingHandlerMapping.getHandlerMethods().keySet()
            .forEach(System.out::println);

        assertEquals(petEntry, actualResponse.getBody());
    }

    private RequestContext createRequestContext() {
        return new PetServiceRequestContext.Builder()
            .withRequestId(REQUEST_ID)
            .withSessionId(SESSION_ID)
            .build();
    }

    private PetEntry createPetEntry() {
        return PetEntry.builder()
            .withId(ID)
            .withTitle(TITLE)
            .build();
    }

    private String getUrl(String urlPath) {
        return "http://localhost:" + port + urlPath;
    }

    private HttpEntity<PetEntry> createHttpEntity(RequestContext requestContext) {
        return new HttpEntity<>(createHttpHeaders(requestContext));
    }

    private HttpEntity<PetEntry> createHttpEntity(PetEntry body, RequestContext requestContext) {
        return new HttpEntity<>(body, createHttpHeaders(requestContext));
    }

    private HttpHeaders createHttpHeaders(RequestContext requestContext) {
        HttpHeaders headers = new HttpHeaders();

        headers.add(RequestParams.REQUEST_ID.getValue(), requestContext.getRequestId());
        headers.add(RequestParams.SESSION_ID.getValue(), requestContext.getSessionId());
        headers.add("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE);

        return headers;
    }

}