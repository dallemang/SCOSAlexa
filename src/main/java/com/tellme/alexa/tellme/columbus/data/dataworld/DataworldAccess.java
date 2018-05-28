package com.tellme.alexa.tellme.columbus.data.dataworld;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataworldAccess {

	String userName="dallemang";
	String apiKey="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJwcm9kLXVzZXItY2xpZW50OmRhbGxlbWFuZyIsImlzcyI6ImFnZW50OmRhbGxlbWFuZzo6MzM5YTcwNzMtZjc0Zi00MzMwLTg4MGMtNzQ4MDRmODI4YWRlIiwiaWF0IjoxNDgyOTQ1NTc4LCJyb2xlIjpbInVzZXJfYXBpX3JlYWQiLCJ1c2VyX2FwaV93cml0ZSJdLCJnZW5lcmFsLXB1cnBvc2UiOnRydWV9.DGctsgVA7e0rmBm6l6_g4FT3det2PZvYrHV6t3a51YCw5K6UN1csAx23VY5PudK2B4PdBC6eDYHRhksbYulMpw";
	//String url = "https://dallemang.linked.data.world/d/smart-city-os-columbus";
	String url = "jdbc:data:world:sql:dallemang:smart-city-os-columbus";
	private final static String error_msg = "I am having problems finding your answeres. Please try again";
	
	public String getPantriesNearMe(String zipCode){
		System.out.println("Inside DataworldAccess::getPantriesNearMe zipCode " + zipCode);
		
		String query = "select pantry_name from foodpantries where zip like ?";
		String response = "";
		try 
		{
			Connection connection = DriverManager.getConnection(url, userName, apiKey);
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, "%" + zipCode + "%" );
			ResultSet resultSet = statement.executeQuery();  //execute the query
			StringBuffer resultBuffer = new StringBuffer();
	        while (resultSet.next()) { //loop through the query results
	        	System.out.println("Result looping");
                String pantry_name = resultSet.getString("pantry_name");
                if(resultBuffer.length() <= 0)
                { 	resultBuffer.append(pantry_name);}
                else
                {	resultBuffer.append(", ").append(pantry_name); }
                response = resultBuffer.toString();
	        }
		}catch(Exception ex){
			System.out.println("Inside DataworldAccess::getData/Eror");
			ex.printStackTrace();
			response = error_msg;
		}
		System.out.println("Response date -> " + response);
		return response;
	}
	
	public String getPantryDetails(String name){
		System.out.println("Inside DataworldAccess::getPantryDetails zipCode name " + name);
		String response = "";
		String query = "select pantry_name, address, phone, hours from foodpantries where upper(pantry_name) = upper(?) ";
		try{
			Connection connection = DriverManager.getConnection(url, userName, apiKey);

			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, name);
			ResultSet resultSet = statement.executeQuery();  //execute the query
			StringBuffer resultBuffer = new StringBuffer();
			//resultBuffer.append("Here are the details for ");
			while(resultSet.next()){
				System.out.println("Result looping");
				resultBuffer.append(resultSet.getString("pantry_name"));
				resultBuffer.append(". Address " + resultSet.getString("address"));
				resultBuffer.append(". Phone " + resultSet.getString("phone"));
				resultBuffer.append(". Hours of operation " + resultSet.getString("hours"));
				resultBuffer.append(".");
			}
			if(resultBuffer.length() > 0)
				response = "Here are the details for " + resultBuffer;
			else
				response = "Sorry, I couldn't find the record";
		}
		catch(Exception ex){
			System.out.println("Exception in getPantryDetails " + ex.getMessage());
			ex.printStackTrace();
			response = error_msg;
		}
		System.out.println("Exiting getPantryDetails response => " +response);
		return response;
	}
	public String findPantryWithGivenItem(String openDate, String itemName){
		System.out.println("Inside DataworldAccess::findPantryWithGivenItem openDate " + openDate + " itemName " + itemName);
		String response = "";
		
		//" and b.pt_loc_id in (select loc_id from pantrytrak_locations where zip='?') " +
		
		  String foodBankpattern = "yyyyMMdd";
	      String amzDatePattern = "yyyy-MM-dd";
	       
	     
	        
		try{
			
			 SimpleDateFormat inputDateFormat = new SimpleDateFormat(amzDatePattern);
		      SimpleDateFormat simpleDateFormat = new SimpleDateFormat(foodBankpattern);
		      Date inputDate = inputDateFormat.parse(openDate);
		     String dateforsql = simpleDateFormat.format(inputDate);
		     System.out.println("Date for query " + dateforsql);
		     
			Connection connection = DriverManager.getConnection(url, userName, apiKey);
			String query = "select b.agency_name, a.date_key, a.start_hour_key, a.end_hour_key from " +
					" trak_agency_schedule_dates a, " +
					"location_agency_crosswalk b " +
					" where a.mof_agency_num = b.mof_agency_num " +
					" and a.date_key= '" + dateforsql + "'" +
					" and b.mof_agency_num in (select mof_agency_num from AgencyInventory where upper(inventory) = upper('" + itemName + "'))";
			
			PreparedStatement statement = connection.prepareStatement(query);
			//statement.setString(1, itemName);
			//statement.setString(1, dateforsql);
			ResultSet resultSet = statement.executeQuery();  //execute the query
			StringBuffer resultBuffer = new StringBuffer();
			resultBuffer.append(itemName + " is available at ");
			boolean findResult=false;
			while(resultSet.next()){
				findResult=true;
				System.out.println("Result looping");
				resultBuffer.append(resultSet.getString("agency_name"));
				//resultBuffer.append(". Address " + resultSet.getString("address"));
				//resultBuffer.append(". Phone " + resultSet.getString("phone"));
				resultBuffer.append(". Hours of operation " + resultSet.getString("start_hour_key") + " " + resultSet.getString("end_hour_key"));
				resultBuffer.append(".");
				break;
			}
			if(!findResult){
				response = error_msg;
			}
				//resultBuffer.append("Linworth Road Church Pantry. Hours of operation 13:00 - 15:00");
			response = resultBuffer.toString();
		}catch(Exception ex){
			System.out.println("Exception in findPantryWithGivenItem " + ex.getMessage());
			ex.printStackTrace();
			response = error_msg;
		}
		return response;
	}
	
	public String findTransitToAPantry(String pantryName){
		System.out.println("Inside DataworldAccess::findTransitToAPantry pantryName " + pantryName );
		String response = "";
		
		//Address multiple
		try{
			response = "SHARE has a route that can take you there.  You will have to walk  0.2 miles to get to the start, and 2.5 miles at the end.  The trip will take 19 minutes.";
		}
		catch(Exception ex){
			System.out.println("Exception in findTransitToAPantry " + ex.getMessage());
			response = error_msg;
			ex.printStackTrace();
		}
		return response;
	}
}
