package com.github.radium226.guice.resource;

import java.lang.reflect.Field;

import com.google.inject.MembersInjector;
import com.google.inject.Provider;

public class ResourceMembersInjector<T> implements MembersInjector<T> {

private Field field;
	
	private Provider<ResourceOpener> resourceOpenerProvider;
	
	public ResourceMembersInjector(Provider<ResourceOpener> resourceOpenerProvider) {
		super();
		
		this.resourceOpenerProvider = resourceOpenerProvider;
	}
	
	@Override
	public void injectMembers(T t) {
		if ()
		System.out.println(t.getClass());
	}

}
