package com.santainc.hoodpreparationservice.bean;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class ExceptionDetails {

	@Getter
	@Setter
	private String message;
	
	@Getter
	@Setter
	private LocalDateTime time;
	
	
}
