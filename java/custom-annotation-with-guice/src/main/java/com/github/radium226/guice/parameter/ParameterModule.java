package com.github.radium226.guice.parameter;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;

public class ParameterModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(ParameterHelper.class).asEagerSingleton();
		bindConstant().annotatedWith(Message.class).to("Hello, World! ");
		
		bindListener(Matchers.any(), new ParameterTypeListener());
	}

}
