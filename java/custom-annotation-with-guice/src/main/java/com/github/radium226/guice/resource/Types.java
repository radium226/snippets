package adrien.guice.resource;

import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.google.common.base.Supplier;

public class Types {

	public static boolean isInputStream(Type type) {
		return type.equals(InputStream.class);
	}
	
	public static boolean isParameterizedType(Type type) {
		return type instanceof ParameterizedType;
	}
	
	public static Type getRawType(Type type) {
		return ((ParameterizedType) type).getRawType();
	}
	
	public static Type getTypeArgument(Type type) {
		return ((ParameterizedType) type).getActualTypeArguments()[0];
	}
	
	public static boolean is(Type type, Class<?> class_) {
		boolean is = false;
		if (isParameterizedType(type)) {
			is = is(getRawType(type), class_);
		} else if (type instanceof Class) {
			is = ((Class<?>) type).isAssignableFrom(class_);
		}
		return is;
	}
	
}
