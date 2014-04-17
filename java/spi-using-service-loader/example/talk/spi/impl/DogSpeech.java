package example.talk.spi.impl;

import java.io.PrintStream;

import example.talk.spi.Speech;

public class DogSpeech implements Speech {

	public DogSpeech() {
		super();
	}
	
	@Override
	public void writeTo(PrintStream printStream) {
		printStream.println("Woof Woof! ");
	}

}
