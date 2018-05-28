package com.tellme.alexa.tellme.columbus.data.restendpoint;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Readdatafromapi {
	String url = "https://api.data.world/v0/queries/ce8a6172-3b48-44c9-8d48-9e803a003767/results";
	String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJwcm9kLXVzZXItY2xpZW50OmRhbGxlbWFuZyIsImlzcyI6ImFnZW50OmRhbGxlbWFuZzo6MzM5YTcwNzMtZjc0Zi00MzMwLTg4MGMtNzQ4MDRmODI4YWRlIiwiaWF0IjoxNDgyOTQ1NTc4LCJyb2xlIjpbInVzZXJfYXBpX3JlYWQiLCJ1c2VyX2FwaV93cml0ZSJdLCJnZW5lcmFsLXB1cnBvc2UiOnRydWV9.DGctsgVA7e0rmBm6l6_g4FT3det2PZvYrHV6t3a51YCw5K6UN1csAx23VY5PudK2B4PdBC6eDYHRhksbYulMpw";
	
	public String getSomeData(){
		String configData = "";
		
		try{
			
		ResteasyClient rsclient = new ResteasyClientBuilder().build();
		
		
		//Response response = target.request().header(HttpHeaders.AUTHORIZATION, "Bearer " + token);
		Response response = rsclient.target(url).request().header(HttpHeaders.AUTHORIZATION, "Bearer " + token).get();
		
		if (response.getStatus() != 200) {
			  throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
		}
		
		String respObject = response.readEntity(String.class);
		ObjectMapper mapper = new ObjectMapper();
		
		//JSON from String to Object
		configData = mapper.readValue(respObject, String.class);
		
		}catch(Exception ex){
			ex.printStackTrace();
			//log.error("KeycloakConfigDataVO/getKeycloakConfigData/PlatformBaseException");
			//throw new (PlatformErrorCodes.SECURITY_ERR, ex.getMessage());
		}
		return configData;
	}
}
