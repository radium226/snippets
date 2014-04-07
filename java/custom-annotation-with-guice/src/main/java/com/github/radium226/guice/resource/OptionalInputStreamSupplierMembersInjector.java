package com.github.radium226.guice.resource;

import java.lang.reflect.Field;

import com.google.inject.MembersInjector;
import com.google.inject.Provider;

public class OptionalInputStreamSupplierMembersInjector<T> implements MembersInjector<T> {

private Field field;
	
	private Provider<ResourceOpener> resourceOpenerProvider;
	
	public OptionalInputStreamSupplierMembersInjector(Provider<ResourceOpener> resourceOpenerProvider, Field field) {
		super();
		
		this.resourceOpenerProvider = resourceOpenerProvider;
		
		this.field = field;
		field.setAccessible(true);
	}
	
	@Override
	public void injectMembers(T t) {
		System.out.println(t.getClass());
		try {
			field.set(t, new OptionalInputStreamSupplier(resourceOpenerProvider, field.getAnnotation(Resource.class).value()));
	      } catch (IllegalAccessException e) {
	        throw new RuntimeException(e);
	      }
	}

}
