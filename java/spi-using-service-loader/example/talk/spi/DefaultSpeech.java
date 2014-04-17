package example.talk.spi;

import java.io.PrintStream;

public class DefaultSpeech implements Speech {

	public DefaultSpeech() {
		super();
	}
	
	@Override
	public void writeTo(PrintStream printStream) {
		System.out.println("Default... ");
	}

}
