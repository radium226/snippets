package com.github.radium226.guice.parameter;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class ParameterHelper {

	@Inject
	@Message
	private String message;
	
	public ParameterHelper() {
		super();
	}
	
	public String retreiveParameterValue(String parameterName) {
		String parameterValue = message;
		return parameterValue;
	}
	
}
