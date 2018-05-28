package com.tellme.alexa.tellme.columbus;

import com.tellme.alexa.tellme.columbus.data.dataworld.DataworldAccess;
import com.tellme.alexa.tellme.columbus.data.restendpoint.Readdatafromapi;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
        //DataworldAccess ada = new DataworldAccess();
       //ada.getPantriesNearMe("43016");
        //System.out.println(ada.findPantryWithGivenItem("05/20/2018", "Apple")); 
        //System.out.println(ada.findPantryWithGivenItem("2018-05-22", "Bananas"));
       String res = new Readdatafromapi().getSomeData();
     }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }
}
