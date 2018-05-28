package com.tellme.alexa.tellme.columbus;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.text.DateFormatter;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
        System.out.println( "Hello World!" );
        
        String foodBankpattern = "yyyyMMdd";
        String amzDatePattern = "yyyy-MM-dd";
        
        SimpleDateFormat inputDateFormat = new SimpleDateFormat(amzDatePattern);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(foodBankpattern);
        
        Date inputDate = inputDateFormat.parse("2018-05-20");
        String strDate = simpleDateFormat.format(inputDate);
        //String inDate = inputDateFormat.format(new Date());
        
        System.out.println(strDate);
        
    }
}
