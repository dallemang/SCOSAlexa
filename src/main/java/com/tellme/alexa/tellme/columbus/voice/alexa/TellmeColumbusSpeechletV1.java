package com.tellme.alexa.tellme.columbus.voice.alexa;

import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.SessionEndedRequest;
import com.amazon.speech.speechlet.SessionStartedRequest;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.speechlet.SpeechletV2;
import com.amazon.speech.ui.OutputSpeech;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;
import com.tellme.alexa.tellme.columbus.data.dataworld.DataworldAccess;

public class TellmeColumbusSpeechletV1 implements SpeechletV2{
	public static final String last_intent = "last_intent";
	public static final String last_intent_value = "last_intent_value";
	public SpeechletResponse onIntent(SpeechletRequestEnvelope<IntentRequest> requestEnvelope) {
		IntentRequest request = requestEnvelope.getRequest();
       // log.info("onIntent requestId={}, sessionId={}", request.getRequestId(),
        //        requestEnvelope.getSession().getSessionId());
		System.out.println("TellmeColumbusSpeechletV1:onIntent:: ");
        Intent intent = request.getIntent();
        String intentName = (intent != null) ? intent.getName() : null;
        Slot soltObj = null;
        String response = "";
        DataworldAccess daccess;
        System.out.println("TellmeColumbusSpeechletV1:onIntent:: intent name ==> " + intentName);
        SpeechletResponse speechletResponse = new SpeechletResponse();
        if("food_pantry_near".equalsIgnoreCase(intentName)){
        	System.out.println("TellmeColumbusSpeechletV1:onIntent:Inside food_pantry_near");
        	requestEnvelope.getSession().setAttribute(last_intent, "food_pantry_near");
        	soltObj = request.getIntent().getSlot("szip_code");
        	daccess = new DataworldAccess();
        	System.out.println(request.getIntent().getSlots());
//            Set<String> keySet = request.getIntent().getSlots().keySet();
//            for(String key : keySet){
//            	System.out.println("Key from slots" + key);
//            }
        	if(soltObj != null && soltObj.getValue() != null && !"".equalsIgnoreCase(soltObj.getValue())){
        		System.out.println("Input zip code " + soltObj.getValue());
        		response = daccess.getPantriesNearMe(soltObj.getValue());
        	}else{
        		response = daccess.getPantriesNearMe("43215");
        	}
        	PlainTextOutputSpeech speech = getPlainTextOutputSpeech(response);
        	PlainTextOutputSpeech repromptSpeech = new PlainTextOutputSpeech();
        	repromptSpeech.setText("Do you like to hear more?");
        	speechletResponse = SpeechletResponse.newAskResponse(speech, getReprompt(repromptSpeech));
        	speechletResponse.setNullableShouldEndSession(false);
        }
        else if("pantry_details".equalsIgnoreCase(intentName)){
        	requestEnvelope.getSession().setAttribute(last_intent, "pantry_details");
        	System.out.println("Inside pantry_details" );
        	soltObj  = request.getIntent().getSlot("pantry_name");
        	System.out.println(request.getIntent().getSlots());
        	
        	daccess = new DataworldAccess();
//            Set<String> keySet = request.getIntent().getSlots().keySet();
//            for(String key : keySet){
//            	System.out.println("Key from slots" + key);
//            }
        	if(soltObj != null && soltObj.getValue() != null && !"".equalsIgnoreCase(soltObj.getValue())){
        		System.out.println("Input pantry_name " + soltObj.getValue());
        		response= daccess.getPantryDetails(soltObj.getValue());
        	}else{
        		response = daccess.getPantryDetails(null);
        	}
        	PlainTextOutputSpeech speech = getPlainTextOutputSpeech(response);
        	PlainTextOutputSpeech repromptSpeech = new PlainTextOutputSpeech();
        	repromptSpeech.setText("Do you like to hear more?");
        	speechletResponse = SpeechletResponse.newAskResponse(speech, getReprompt(repromptSpeech));
        	speechletResponse.setNullableShouldEndSession(false);
        }
        else if("pantry_with_item".equalsIgnoreCase(intentName)){
        	requestEnvelope.getSession().setAttribute(last_intent, "pantry_with_item");
        	System.out.println("Inside pantry_with_item" );
        	String dateSlotValue = null;
        	String itemSlotValue = null;
        	Slot dateSlot  = request.getIntent().getSlot("date_val");
        	if(dateSlot != null && dateSlot.getValue() != null && !"".equalsIgnoreCase(dateSlot.getValue())){
        		dateSlotValue = dateSlot.getValue();
        		System.out.println("pantry_with_item/dateSlotValue  "+	dateSlotValue);
        	}
        	Slot itemSlot  = request.getIntent().getSlot("food_item");
        	if(itemSlot != null && itemSlot.getValue() != null && !"".equalsIgnoreCase(itemSlot.getValue())){
        		itemSlotValue = itemSlot.getValue();
        		System.out.println("pantry_with_item/itemSlotValue  "+	itemSlotValue);
        	}
        	response = new DataworldAccess().findPantryWithGivenItem(dateSlotValue, itemSlotValue);
        	System.out.println(request.getIntent().getSlots());
        	PlainTextOutputSpeech speech = getPlainTextOutputSpeech(response);
        	PlainTextOutputSpeech repromptSpeech = new PlainTextOutputSpeech();
        	repromptSpeech.setText("Do you like to hear more?");
        	speechletResponse = SpeechletResponse.newAskResponse(speech, getReprompt(repromptSpeech));
        	speechletResponse.setNullableShouldEndSession(false);
        }
        else if ("transit_to_pantry".equalsIgnoreCase(intentName)){
        	System.out.println("Inside transit_to_pantry" );
        	requestEnvelope.getSession().setAttribute(last_intent, "transit_to_pantry");
        	String pantryNameVlue = null;
        	Slot pantryNameSlot = request.getIntent().getSlot("pantry_name");
        	if(pantryNameSlot != null && pantryNameSlot.getValue() != null && !"".equalsIgnoreCase(pantryNameSlot.getValue())){
        		pantryNameVlue = pantryNameSlot.getValue();
        		System.out.println("transit_to_pantry/pantryNameVlue " + pantryNameVlue);
        	}
        	response = new DataworldAccess().findTransitToAPantry(pantryNameVlue);
        	PlainTextOutputSpeech speech = getPlainTextOutputSpeech(response);
        	PlainTextOutputSpeech repromptSpeech = new PlainTextOutputSpeech();
        	repromptSpeech.setText("Do you like to hear more?");
        	speechletResponse = SpeechletResponse.newAskResponse(speech, getReprompt(repromptSpeech));
        	speechletResponse.setNullableShouldEndSession(false);
        }
        else if("AMAZON.NoIntent".equals(intentName)) {
        	System.out.println("No Intent");
        	return null;
        }
        else if("AMAZON.YesIntent".equals(intentName)){
        	System.out.println("Yes Intent");
        	String fromUserIntent="";
        	String fromUserIntentValue;
        	if(requestEnvelope.getSession().getAttribute(last_intent) != null){
        		fromUserIntent = requestEnvelope.getSession().getAttribute(last_intent).toString();
        		fromUserIntentValue =requestEnvelope.getSession().getAttribute(last_intent_value).toString();
        	}
        }
		System.out.println("TellmeColumbusSpeechletV1:onIntent::Found response ");
        
		SimpleCard card = getSimpleCard("Columbus Help ", response);
		speechletResponse.setCard(card);
        System.out.println("TellmeColumbusSpeechletV1:onIntent::Found returning response ");
        return speechletResponse;//SpeechletResponse.newTellResponse(speech, card);
	}

	public SpeechletResponse onLaunch(SpeechletRequestEnvelope<LaunchRequest> arg0) {
		System.out.println("TellmeColumbusSpeechletV1/onLaunch");
		return getWelcomeResponse();
	}

	public void onSessionEnded(SpeechletRequestEnvelope<SessionEndedRequest> arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onSessionStarted(SpeechletRequestEnvelope<SessionStartedRequest> arg0) {
		// TODO Auto-generated method stub
		
	}
    private SpeechletResponse getWelcomeResponse() {
    	System.out.println("TellmeColumbusSpeechletV1/getWelcomeResponse");
        String speechText = "Welcome to the Smart City Columbus.";
        return getAskResponse("Smart City Columbus", speechText);
    }
    
    private SpeechletResponse getAskResponse(String cardTitle, String speechText) {
        SimpleCard card = getSimpleCard(cardTitle, speechText);
        PlainTextOutputSpeech speech = getPlainTextOutputSpeech(speechText);
        Reprompt reprompt = getReprompt(speech);

        return SpeechletResponse.newAskResponse(speech, reprompt, card);
    }
    
    private PlainTextOutputSpeech getPlainTextOutputSpeech(String speechText) {
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);

        return speech;
    }
    
    private SimpleCard getSimpleCard(String title, String content) {
        SimpleCard card = new SimpleCard();
        card.setTitle(title);
        card.setContent(content);

        return card;
    }
    
    private Reprompt getReprompt(OutputSpeech outputSpeech) {
        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(outputSpeech);

        return reprompt;
    }
}
