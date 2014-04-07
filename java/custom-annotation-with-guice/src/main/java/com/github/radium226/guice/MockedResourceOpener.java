package com.github.radium226.guice;

import com.github.radium226.guice.resource.ResourceOpener;
import com.google.common.io.ByteStreams;
import com.google.common.io.CharStreams;
import java.io.IOException;
import java.io.InputStream;

public class MockedResourceOpener implements ResourceOpener {

	final public static String TEXT = "Wololooo!! ";
	
	public MockedResourceOpener() {
		super();
	}
	
	@Override
	public InputStream openResource(String resourceName) {
		try {
			return ByteStreams.newInputStreamSupplier((TEXT + " - " + resourceName).getBytes()).getInput();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
