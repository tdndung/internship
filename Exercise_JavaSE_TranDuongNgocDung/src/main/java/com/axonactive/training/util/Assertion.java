package com.axonactive.training.util;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

public class Assertion {

	private Assertion() {}

	public static void assertNotNull(Object obj, String errorMessage) {
		if (Objects.isNull(obj)) {
			throw new IllegalArgumentException(errorMessage);
		}
	}
	
	public static void assertNotNullOrEmpty(String str, String errorMessage) {
		if (StringUtils.isEmpty(str)) {
			throw new IllegalArgumentException(errorMessage);
		}
	}
}
