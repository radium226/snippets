package com.github.radium226.guice.parameter;

import java.lang.reflect.Field;

import com.google.inject.MembersInjector;
import com.google.inject.Provider;

public class ParameterMembersInjector<T> implements MembersInjector<T> {

	private Field field;
	
	private Provider<ParameterHelper> parameterHelperProvider;
	
	public ParameterMembersInjector(Provider<ParameterHelper> parameterHelperProvider, Field field) {
		super();
		
		this.parameterHelperProvider = parameterHelperProvider;
		
		this.field = field;
		field.setAccessible(true);
	}
	
	@Override
	public void injectMembers(T t) {
		
		try {
	        field.set(t, new Parameter(parameterHelperProvider, field.getAnnotation(Parameter.Named.class).value()));
	      } catch (IllegalAccessException e) {
	        throw new RuntimeException(e);
	      }
	}

}
