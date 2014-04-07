package com.github.radium226.guice.cache;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;

public class CacheModule extends AbstractModule {

	public CacheModule() {
		super();
	}
	
	@Override
	protected void configure() {
		bindInterceptor(Matchers.any(), Matchers.annotatedWith(Cached.class), new CacheMethodInterceptor());
	}

}
