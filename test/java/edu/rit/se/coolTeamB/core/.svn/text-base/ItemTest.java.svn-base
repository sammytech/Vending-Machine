package edu.rit.se.coolTeamB.core;

import java.util.Calendar;

import junit.framework.TestCase;
import edu.rit.se.coolTeamB.exceptions.*;

public class ItemTest extends TestCase {
    private Item testItem = null;
    private String name = null;
    private double price;
    
    public ItemTest(String arg0)
    {
	super(arg0);
    }
    
    public void setUp() throws Exception 
    {
	name = "MarsBars";
	price = 42.23;
	testItem = new Item(name, price, Calendar.getInstance());
    }
    
    public void testForConstructorNegativePrice()
    {
	try 
	{
	    Item testItem2 = new Item("Doritos", -2.00, Calendar.getInstance());
	    fail("NegativeInputException should have been thrown");
	} 
	catch (Exception e) 
	{
	    assertEquals(NegativeInputException.class, e.getClass());
	}
    }
    
    public void testForSetNegativePrice()
    {
	try 
	{
	    testItem.setPrice(-2.00);
	    fail("NegativeInputException should have been thrown");
	} 
	catch (Exception e) 
	{
	    assertEquals(NegativeInputException.class, e.getClass());
	}
    }
    
    public void testAfterSettingPrice()
    {
	try
	{
	    testItem.setPrice(2.50);
	    assertEquals("Price should be 2.50.", 2.50, testItem.getPrice());
	}
	catch(Exception e)
	{
	    e.printStackTrace();
	}
    }
    
    public void testAfterSettingName()
    {
	testItem.setName("Doritos");
	assertEquals("Name should be Doritos.", "Doritos", testItem.getName());
    }

}
