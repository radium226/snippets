package com.github.radium226.guice.cache;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Maps;

public class CacheMethodInterceptor implements MethodInterceptor {

	private Map<Method, Cache<List<Object>, Object>> caches = Maps.newConcurrentMap();
	
	public CacheMethodInterceptor() {
		super();
	}
	
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Method method = invocation.getMethod();
		if (!caches.containsKey(method)) {
			Cache<List<Object>, Object> cache  = CacheBuilder.newBuilder().build();
			caches.put(method, cache);
			System.out.println("==> Cache created. ");
		}
		
		List<Object> arguments = Arrays.asList(invocation.getArguments());
		Object object = caches.get(method).getIfPresent(arguments);
		if (object == null) {
			System.out.println("--> Not in cache... For now! ");
			object = invocation.proceed();
			caches.get(method).put(arguments, object);
		} else {
			System.out.println("--> Retreived from cache! ");
		}
		
		return object;
	}

}
