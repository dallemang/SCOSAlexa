package com.tellme.alexa.tellme.columbus.voice.alexa;

import java.util.HashSet;
import java.util.Set;

import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;

public class TellmeColumbusSpeechletRequestStreamHandler extends SpeechletRequestStreamHandler {
	
	private static final Set<String> supportedApplicationIds;
	static{
		supportedApplicationIds = new HashSet<String>();
		supportedApplicationIds.add("amzn1.ask.skill.7ebae603-9e13-4d90-bc2b-fa0fa58e38ea");
	}
	
   public TellmeColumbusSpeechletRequestStreamHandler() {
	        super(new TellmeColumbusSpeechletV1(), supportedApplicationIds);
    }
}
