package com.github.radium226.guice.resource;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import com.google.inject.Provider;
import com.google.inject.TypeLiteral;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;

public class ResourceTypeListener implements TypeListener {

	final public static String EOL = String.format("%n");
	
	public static enum Strategy {
		
		INPUT_STREAM, 
		INPUT_STREAM_SUPPLIER, 
		OPTIONAL_INPUT_STREAM, 
		OPTIONAL_INPUT_STREAM_SUPPLIER
		
	}
	
	public static String toString(Type type) {
		return toString(type, true);
	}
	
	public static String toString(Type type, boolean withEOL) {
		Type argumentType = null; 
		StringBuffer buffer = new StringBuffer();
		if (type instanceof ParameterizedType) {
			ParameterizedType parameterizedType = (ParameterizedType) type;
			type = parameterizedType.getRawType();
			argumentType = parameterizedType.getActualTypeArguments()[0];
		}
		
		buffer.append(getName(type));
		if (argumentType != null) {
			buffer
				.append("<")
				.append(toString(argumentType, false))
				.append(">");
		}
		
		if (withEOL) {
			buffer.append(EOL);
		}
		
		return buffer.toString();
	}
	
	public static String getName(Type type) {
		return ((Class<?>) type).getName();
	}
	
	public Strategy chooseStrategy(Type type) {
		Strategy strategy = null; 
		if (Types.is(type, InputStream.class)) {
			strategy = Strategy.INPUT_STREAM;
		} else if (Types.is(type, Optional.class)) {
			strategy = Strategy.OPTIONAL_INPUT_STREAM;			
		} else if (Types.is(type, Supplier.class)) {
			Type typeArgument = Types.getTypeArgument(type);
			if (Types.is(typeArgument, Optional.class)) {
				strategy = Strategy.OPTIONAL_INPUT_STREAM_SUPPLIER;
			} else if (Types.is(typeArgument, InputStream.class)) {
				strategy = Strategy.INPUT_STREAM_SUPPLIER;
			}
		}
		return strategy;
	}
	
	@Override
	public <I> void hear(TypeLiteral<I> typeLiteral, TypeEncounter<I> typeEncounter) {
		for (Field field : typeLiteral.getRawType().getDeclaredFields()) {
			if (field.isAnnotationPresent(Resource.class)) {
				Type type = field.getGenericType();
				Provider<ResourceOpener> resourceOpenerProvider = typeEncounter.getProvider(ResourceOpener.class);
				typeEncounter.register(new ResourceMembersInjector(resourceOpenerProvider));
				
			}
		}
	}

}
