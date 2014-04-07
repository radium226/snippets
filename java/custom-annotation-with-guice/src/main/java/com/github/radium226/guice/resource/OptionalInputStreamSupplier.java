package com.github.radium226.guice.resource;

import java.io.InputStream;

import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class OptionalInputStreamSupplier implements Supplier<Optional<InputStream>> {

	private String resourceName;
	
	private Provider<ResourceOpener> resourceOpenerProvider;
	
	public OptionalInputStreamSupplier(Provider<ResourceOpener> resourceOpenerProvider, String resourceName) {
		super();
		
		this.resourceOpenerProvider = resourceOpenerProvider;
		this.resourceName = resourceName;
	}
	
	@Override
	public Optional<InputStream> get() {
		return Optional.fromNullable(resourceOpenerProvider.get().openResource(resourceName));
	}

}
