package com.santainc.hoodpreparationservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class HoodFillerException  extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	@Setter
	@Getter
	@NonNull
	private String message;
	
	@Setter
	@Getter
	private Throwable th;
}
