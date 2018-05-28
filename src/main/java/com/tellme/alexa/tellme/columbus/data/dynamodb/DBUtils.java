package com.tellme.alexa.tellme.columbus.data.dynamodb;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;

public class DBUtils {

	//private static final String URL = "http://localhost:8000";
		private static final String URL = " https://dynamodb.us-west-2.amazonaws.com";
		private static final String REGION =  "US_EAST_1";
		//Regions.US_EAST_1; US East Virginia
		//https://dynamodb.us-west-2.amazonaws.com
		private static final String accessKeyId = "AKIAJIU7HW4RFY52Y5CA";
		private static final String sec_key = "somehthing";
		private static DynamoDB dynamoDB;
		private static AmazonDynamoDB  amzDynamoDB;
		
		public static DynamoDB getDynamoDB(){
			if(dynamoDB == null){
				amzDynamoDB = getAmazonDynamoDB();
				dynamoDB = new DynamoDB(amzDynamoDB);
			}
			return dynamoDB;
		}
		
		public static AmazonDynamoDB getAmazonDynamoDB(){
			if(amzDynamoDB == null){
				amzDynamoDB = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
				//		AmazonDynamoDBClientBuilder.standard().
				//		withEndpointConfiguration(new EndpointConfiguration(URL, REGION)).build();
			}
			return amzDynamoDB;
		}
}
