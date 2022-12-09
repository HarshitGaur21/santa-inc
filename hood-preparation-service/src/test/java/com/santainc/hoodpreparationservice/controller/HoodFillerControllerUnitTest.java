package com.santainc.hoodpreparationservice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.santainc.hoodpreparationservice.bean.HoodFillerRequest;
import com.santainc.hoodpreparationservice.service.HoodFillerService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = HoodFillerController.class)
class HoodFillerControllerUnitTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private HoodFillerService hoodFillerService;

	@Test
	void testFillHood_ValidRequest() throws Exception {
		String request = "{ \"hood_capacity\": 41, \"present_weights\": [ 2, 5, 10, 50, 100 ] }";
		when(hoodFillerService.getWeights(Mockito.any(HoodFillerRequest.class))).thenReturn(List.of(10,5,2));
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/hood-preparation/hoodfiller")
				.accept(MediaType.APPLICATION_JSON).content(request).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals("[10,5,2]", response.getContentAsString());
	}

	@Test
	void testFillHood_InvalidRequest_NonPositiveCapacity() throws Exception {
		String request = "{ \"hood_capacity\": 0, \"present_weights\": [ 2, 5, 10, 50, 100 ] }";
		when(hoodFillerService.getWeights(Mockito.any(HoodFillerRequest.class))).thenReturn(List.of(10,5,2));
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/hood-preparation/hoodfiller")
				.accept(MediaType.APPLICATION_JSON).content(request).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
	}
	
	@Test
	void testFillHood_InvalidRequest_EmptyPresentWeights() throws Exception {
		String request = "{ \"hood_capacity\": 0, \"present_weights\": [] }";
		when(hoodFillerService.getWeights(Mockito.any(HoodFillerRequest.class))).thenReturn(List.of(10,5,2));
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/hood-preparation/hoodfiller")
				.accept(MediaType.APPLICATION_JSON).content(request).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
	}
}
