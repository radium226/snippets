package com.github.radium226.guice;

import com.google.inject.Injector;

public class Injectors {

	final private static Instance INSTANCE = new Instance();
	
	final private static class Instance {
		
		private Instance() {
			super();
		}
		
		public Injector getDefaultInjector() {
			
			return null; 
		}
		
	}
	
	public Injector getDefaultInjector() {
		return INSTANCE.getDefaultInjector();
	}
	
}
