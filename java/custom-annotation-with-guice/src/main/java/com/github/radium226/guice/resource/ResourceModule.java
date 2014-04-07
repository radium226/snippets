package com.github.radium226.guice.resource;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;

public class ResourceModule extends AbstractModule {

	private ResourceOpener resourceOpener;
	
	public ResourceModule(ResourceOpener resourceOpener) {
		super();
		
		this.resourceOpener = resourceOpener;
	}
	
	@Override
	protected void configure() {
		bind(ResourceOpener.class).toInstance(resourceOpener);
		bindListener(Matchers.any(), new ResourceTypeListener());
	}

}
