package example.talk.spi;

import java.util.Iterator;
import java.util.ServiceLoader;

public class SpeechProvider {

	public static Speech provideSpeech() {
		Iterator<Speech> speechIterator = ServiceLoader.load(Speech.class).iterator();
		Speech providedSpeech = null; 
		while(speechIterator.hasNext()) {
			Speech speech =  speechIterator.next();
			System.out.println(" --> " + speech.getClass().getSimpleName() + " was found");
			if (providedSpeech == null) {
				providedSpeech = speech;
			}
		}
		return providedSpeech == null ? new DefaultSpeech() : providedSpeech;
	}
	
}
