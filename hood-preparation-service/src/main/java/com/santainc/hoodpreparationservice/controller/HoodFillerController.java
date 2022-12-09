package com.santainc.hoodpreparationservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.santainc.hoodpreparationservice.bean.HoodFillerRequest;
import com.santainc.hoodpreparationservice.service.HoodFillerService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping(path = "/hood-preparation")
public class HoodFillerController {

	@Autowired
	private HoodFillerService hoodFillerService;
	
	
	@PostMapping("/hoodfiller")
	@ResponseStatus(code = HttpStatus.OK)
	public List<Integer> fillHood(@RequestBody @NotNull @Valid HoodFillerRequest request) {
		return hoodFillerService.getWeights(request); 
	}
}
