package com.github.radium226.guice.resource;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Resource {

	String name() default ""; 
	String value() default "";
	
}
