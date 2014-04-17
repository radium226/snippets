package example.talk.spi.impl;

import java.io.PrintStream;

import example.talk.spi.Speech;

public class BossSpeech implements Speech {

	public BossSpeech() {
		super();
	}
	
	@Override
	public void writeTo(PrintStream printStream) {
		printStream.println("Do it NOW!! ");
	}

}
