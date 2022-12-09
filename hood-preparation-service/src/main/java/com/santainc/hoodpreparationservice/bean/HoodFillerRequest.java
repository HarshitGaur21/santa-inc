/**
 * 
 */
package com.santainc.hoodpreparationservice.bean;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Harshit
 *
 */
@AllArgsConstructor
@NoArgsConstructor
public class HoodFillerRequest {

	@Getter
	@Setter
	@Positive(message = "hood_capacity must be greater than zero.")
	@JsonProperty("hood_capacity")
	private int hoodCapacity;

	@Getter
	@Setter
	@NotEmpty(message = "present_weights must not be empty.")
	@JsonProperty("present_weights")
	private List<Integer> presentWeights;

}
