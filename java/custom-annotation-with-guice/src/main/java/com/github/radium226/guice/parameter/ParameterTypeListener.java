package com.github.radium226.guice.parameter;

import java.lang.reflect.Field;

import com.google.inject.Provider;
import com.google.inject.TypeLiteral;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;

public class ParameterTypeListener implements TypeListener {

	@Override
	public <I> void hear(TypeLiteral<I> typeLiteral, TypeEncounter<I> typeEncounter) {
		for (Field field : typeLiteral.getRawType().getDeclaredFields()) {
			if (field.getType() == Parameter.class) {
				if (field.isAnnotationPresent(Parameter.Named.class)) {
					Provider<ParameterHelper> parameterHelperProvider = typeEncounter.getProvider(ParameterHelper.class);
					typeEncounter.register(new ParameterMembersInjector<I>(parameterHelperProvider, field));
				}
			}
		}
	}

}
