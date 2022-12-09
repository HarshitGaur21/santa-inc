package com.santainc.hoodpreparationservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.santainc.hoodpreparationservice.bean.HoodFillerRequest;
import com.santainc.hoodpreparationservice.exception.HoodFillerException;

@ExtendWith(MockitoExtension.class)
class HoodFillerServiceTest {

	@InjectMocks
	private HoodFillerService hoodFillerService;
	
	@Test
	void testGetWeights_ValidRequest() throws Exception {
		HoodFillerRequest request = new HoodFillerRequest(41, List.of(2, 5, 10, 50, 100));
		List<Integer> response = hoodFillerService.getWeights(request);
		assertEquals(List.of(10, 10, 10, 5, 2, 2, 2), response);
	}

	@Test
	void testGetWeights_NullRequest() throws Exception {
		assertThrows(HoodFillerException.class, () -> hoodFillerService.getWeights(null));
	}
	
	@Test
	void testGetWeights_NonNullRequestWithNoData() throws Exception {
		HoodFillerRequest request = new HoodFillerRequest();
		assertThrows(HoodFillerException.class, () -> hoodFillerService.getWeights(request));
	}
	
	@Test
	void testGetWeights_NonPositiveCapacity() throws Exception {
		HoodFillerRequest request = new HoodFillerRequest(0, List.of(2, 5, 10, 50, 100));
		assertThrows(HoodFillerException.class, () -> hoodFillerService.getWeights(request));
	}
	
	@Test
	void testGetWeights_EmptyPresentWeights() throws Exception {
		HoodFillerRequest request = new HoodFillerRequest(41, List.of());
		assertThrows(HoodFillerException.class, () -> hoodFillerService.getWeights(request));
	}

}
