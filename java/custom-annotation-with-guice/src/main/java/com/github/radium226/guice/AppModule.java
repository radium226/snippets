package com.github.radium226.guice;

import com.google.inject.AbstractModule;

public class AppModule extends AbstractModule {

	public AppModule() {
		super();
	}
	
	@Override
	protected void configure() {
		bind(App.class).asEagerSingleton();
	}

}
