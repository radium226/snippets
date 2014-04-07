package com.github.radium226.guice.parameter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.google.inject.Provider;

public class Parameter {

	@Retention(RetentionPolicy.RUNTIME)
	public static @interface Named {
	
		String value();
	
	}
	
	
	private Provider<ParameterHelper> parameterHelperProvider;
	
	private String name;
	
	public Parameter(Provider<ParameterHelper> parameterHelperProvider, String name) {
		super();
		
		this.name = name;
		this.parameterHelperProvider = parameterHelperProvider;
	}
	
	public ParameterHelper getParameterHelper() {
		return parameterHelperProvider.get();
	}
	
	public String getValue() {
		return getParameterHelper().retreiveParameterValue(name);
	}
	
}
