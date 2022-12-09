package com.santainc.hoodpreparationservice.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.santainc.hoodpreparationservice.bean.HoodFillerRequest;
import com.santainc.hoodpreparationservice.exception.HoodFillerException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

@Service
public class HoodFillerService {

	private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

	private final Logger log = LoggerFactory.getLogger(getClass());

	/**
	 * @param request
	 * @return
	 */
	public List<Integer> getWeights(HoodFillerRequest request) {
		log.info("getWeights, request [{}]", request);
		validateRequest(request);
		List<Integer> ans = new ArrayList<>();
		List<Integer> presentWeights = request.getPresentWeights();
		findWeights(presentWeights.stream().sorted(Collections.reverseOrder()).toList(), 0, request.getHoodCapacity(),
				ans);
		log.info("getWeights, Optimized Present Weights are: {}", ans);
		return ans;
	}

	/**
	 * Prepare the list of optimized number of weights
	 * 
	 * @param weights
	 * @param index
	 * @param capacity
	 * @param ans
	 */
	private void findWeights(List<Integer> weights, int index, int capacity, List<Integer> ans) {
		if (capacity == 0 || index > weights.size() - 1) {
			return;
		}
		if (capacity < weights.get(index)) {
			if (ans.isEmpty() || weights.subList(index + 1, weights.size()).stream().anyMatch(w -> capacity >= w)) {
				findWeights(weights, index + 1, capacity, ans);
			} else {
				findWeights(weights, index + 1, capacity + ans.remove(ans.size() - 1), ans);
			}
		} else {
			ans.add(weights.get(index));
			findWeights(weights, index, capacity - weights.get(index), ans);
		}
	}

	/**
	 * Validate the request
	 * 
	 * @param request
	 */
	private void validateRequest(HoodFillerRequest request) {
		if (Objects.isNull(request)) {
			throw new HoodFillerException("HoodFillerRequest object is null");
		}
		Set<ConstraintViolation<HoodFillerRequest>> violations = validator.validate(request);
		if (!violations.isEmpty()) {
			throw new HoodFillerException(
					violations.stream().map(v -> v.getMessage()).collect(Collectors.joining(", ")));
		}
	}
}
