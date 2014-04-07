package com.github.radium226.guice.resource;

import java.io.InputStream;

public interface ResourceOpener {

	InputStream openResource(String name);
	
}
