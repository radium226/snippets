package com.github.radium226.guice.resource;

import java.io.InputStream;

import com.google.common.base.Supplier;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class InputStreamSupplier implements Supplier<InputStream> {

	private String resourceName;
	
	private Provider<ResourceOpener> resourceOpenerProvider;
	
	public InputStreamSupplier(Provider<ResourceOpener> resourceOpenerProvider, String resourceName) {
		super();
		
		this.resourceOpenerProvider = resourceOpenerProvider;
		this.resourceName = resourceName;
	}
	
	@Override
	public InputStream get() {
		return resourceOpenerProvider.get().openResource(resourceName);
	}

}
