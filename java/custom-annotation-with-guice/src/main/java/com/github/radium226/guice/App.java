package com.github.radium226.guice;

import com.github.radium226.guice.cache.CacheModule;
import com.github.radium226.guice.cache.Cached;
import com.github.radium226.guice.parameter.Message;
import com.github.radium226.guice.parameter.Parameter;
import com.github.radium226.guice.parameter.ParameterModule;
import com.github.radium226.guice.resource.Resource;
import com.github.radium226.guice.resource.ResourceModule;
import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableList;
import com.google.common.io.CharStreams;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Random;


@Singleton
public class App {

	final private static List<String> TEXTS = ImmutableList.<String>builder()
			.add("Here")
			.add("I")
			.add("Am")
			.add("With")
			.add("My")
			.add("Empire")
			.add("Of")
			.add("Dirt")
		.build();
	
	@Inject 
	@Message
	private String t;
	
	@Parameter.Named("message")
	private Parameter message;
	
	@Resource("inputStreamSupplier")
	private Supplier<InputStream> inputStreamSupplier;
	
	@Resource("inputStream")
	private InputStream inputStream;
	
	@Resource("optionalInputStreamSupplier")
	private Supplier<Optional<InputStream>> optionalInputStreamSupplier;
	
	@Resource("optionalInputStream")
	private Optional<InputStream> optionalInputStream;
	
	
	public App() {
		super();
	}

	public void run(Injector injector) {
		/*System.out.println(message.getValue());
		
		System.out.println("t = " + t);	
		
		int times = 25000;
		for (int i = 0; i < times; i++) {
			int index = RandomUtil.randomInteger(0, 25);
			String text = lookUpText(index);
			System.out.println("index = " + index + " ==> text = " + text);
		}*/
		
		try {
			System.out.println(" --> " + CharStreams.toString(new InputStreamReader(inputStreamSupplier.get())));
			System.out.println(" --> " + CharStreams.toString(new InputStreamReader(optionalInputStreamSupplier.get().get())));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Cached
	public String lookUpText(int index) {
		try { Thread.sleep(500); } catch (InterruptedException e) {  }
		
		return TEXTS.get(index % TEXTS.size());
	}
	
	public static void main(String[] arguments) {
		Injector injector = Guice.createInjector(new AppModule(), new ParameterModule(), new CacheModule(), new ResourceModule(new MockedResourceOpener()));
		App app = injector.getInstance(App.class);
		app.run(injector);
		
	}
	
}
