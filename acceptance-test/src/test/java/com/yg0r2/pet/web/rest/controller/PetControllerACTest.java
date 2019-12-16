package com.yg0r2.pet.web.rest.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.yg0r2.core.api.model.RequestContext;
import com.yg0r2.core.api.model.RequestParams;
import com.yg0r2.pet.PetApplication;
import com.yg0r2.pet.api.model.PetEntry;
import com.yg0r2.pet.api.model.PetServiceRequestContext;
import com.yg0r2.pet.dao.repository.PetRepository;

import org.assertj.core.api.Condition;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.function.Predicate;

@SpringBootTest(classes = PetApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PetControllerACTest {

    private static final long ID = 123L;
    private static final String TITLE = "petTitle";
    private static final String CREATE_URL = "/api/pet";
    private static final String GET_ALL_URL = "/api/pet";
    private static final String REQUEST_ID = "requestId";
    private static final String SESSION_ID = "sessionId";
    private static final Condition<ResponseEntity<?>> OK_STATUS_CONDITION = new Condition<>(responseEntity -> HttpStatus.OK == responseEntity.getStatusCode(), "OK status");

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private PetRepository petRepository;

    @AfterEach
    void tearDown() {
        petRepository.deleteAll();
    }

    @Test
    void testGetAll() {
        HttpEntity<PetEntry> httpEntity = createHttpEntity();

        ResponseEntity<PetEntry[]> actual = restTemplate.exchange(getUrl(GET_ALL_URL), HttpMethod.GET, httpEntity, PetEntry[].class);

        assertThat(actual)
            .is(OK_STATUS_CONDITION)
            .extracting(HttpEntity::getBody)
            .is(new Condition<>(body -> ((PetEntry[])body).length == 0, "Empty array as body"));
    }

    @Test
    void testPost() {
        PetEntry petEntry = createPetEntry(ID);
        HttpEntity<PetEntry> httpEntity = createHttpEntity(petEntry);

        ResponseEntity<PetEntry> actual = restTemplate.exchange(getUrl(CREATE_URL), HttpMethod.POST, httpEntity, PetEntry.class);

        assertThat(actual)
            .is(OK_STATUS_CONDITION)
            .extracting(ResponseEntity::getBody)
            .is(new Condition<>(body -> petEntry.getTitle().equals(((PetEntry)body).getTitle()), TITLE));
    }

    private PetEntry createPetEntry(long id) {
        return PetEntry.builder()
            .withId(id)
            .withTitle(TITLE)
            .build();
    }

    private String getUrl(String urlPath) {
        return "http://localhost:" + port + urlPath;
    }

    private HttpEntity<PetEntry> createHttpEntity(PetEntry body) {
        return new HttpEntity<>(body, createHttpHeaders(createRequestContext()));
    }

    private HttpEntity<PetEntry> createHttpEntity() {
        return new HttpEntity<>(createHttpHeaders(createRequestContext()));
    }

    private HttpHeaders createHttpHeaders(RequestContext requestContext) {
        HttpHeaders headers = new HttpHeaders();

        headers.add(RequestParams.REQUEST_ID.getValue(), requestContext.getRequestId());
        headers.add(RequestParams.SESSION_ID.getValue(), requestContext.getSessionId());
        headers.add("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE);

        return headers;
    }

    private RequestContext createRequestContext() {
        return new PetServiceRequestContext.Builder()
            .withRequestId(REQUEST_ID)
            .withSessionId(SESSION_ID)
            .build();
    }

}