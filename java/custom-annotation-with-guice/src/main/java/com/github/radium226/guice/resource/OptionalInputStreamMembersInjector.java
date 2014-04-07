package com.github.radium226.guice.resource;

import java.io.InputStream;
import java.lang.reflect.Field;

import com.google.common.base.Optional;
import com.google.inject.MembersInjector;
import com.google.inject.Provider;

public class OptionalInputStreamMembersInjector<T> implements MembersInjector<T> {

private Field field;
	
	private Optional<InputStream> optionalInputStream;
	
	public OptionalInputStreamMembersInjector(Optional<InputStream> optionalInputStream, Field field) {
		super();
		
		this.optionalInputStream = optionalInputStream;
		this.field = field;
		field.setAccessible(true);
	}
	
	@Override
	public void injectMembers(T t) {
		
		System.out.println(t);
		try {
			field.set(t, optionalInputStream);
	      } catch (IllegalAccessException e) {
	        throw new RuntimeException(e);
	      }
	}

}
