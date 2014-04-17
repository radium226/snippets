package example.talk;

import example.talk.spi.Speech;
import example.talk.spi.SpeechProvider;

public class Talker {

	public static void talk() {
		Speech speech = SpeechProvider.provideSpeech();
		speech.writeTo(System.out);
	}
	
}
