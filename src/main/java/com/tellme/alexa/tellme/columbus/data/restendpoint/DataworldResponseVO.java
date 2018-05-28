package com.tellme.alexa.tellme.columbus.data.restendpoint;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class DataworldResponseVO {

	ArrayList<String> results;
	String queryText = "";
	
	//@JsonProperty(value="results")
	public ArrayList<String> getResults() {
		return results;
	}

	public void setResults(ArrayList<String> results) {
		this.results = results;
	}
	@JsonProperty(value="query_text")
	public String getQueryText() {
		return queryText;
	}

	public void setQueryText(String queryText) {
		this.queryText = queryText;
	}
	
	
	
	
	
}
