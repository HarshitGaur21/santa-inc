package com.santainc.hoodpreparationservice.controller;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.santainc.hoodpreparationservice.HoodPreparationServiceApplication;
import com.santainc.hoodpreparationservice.bean.HoodFillerRequest;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = HoodPreparationServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HoodFillerControllerIntegrationTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	void testFillHood() {
		final int capacity = 41;
		final HoodFillerRequest request = new HoodFillerRequest(capacity, List.of(100, 10, 5, 2));
		HttpEntity<HoodFillerRequest> entity = new HttpEntity<HoodFillerRequest>(request);
		List<Integer> expectedResponse = List.of(10, 10, 10, 5, 2, 2, 2);
		final ResponseEntity<List> responseEntity = restTemplate.exchange(buildUrl("/hood-preparation/hoodfiller"),
				HttpMethod.POST, entity, List.class);
		List<Integer> actualResponse = responseEntity.getBody();
		assertTrue(responseEntity.getStatusCode().is2xxSuccessful(), "HttpStatusCode is Not Successful!");
		assertTrue(Objects.nonNull(actualResponse), "Response Body is null or empty!");
		assertIterableEquals(expectedResponse, actualResponse, "Response Body does not contain expected data!");
	}

	private String buildUrl(String uri) {
		return String.format("http://localhost:%s%s", port, uri);
	}
}
