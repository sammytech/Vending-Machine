package edu.rit.se.coolTeamB.customer;

import java.util.ArrayList;
import java.util.Calendar;

import edu.rit.se.coolTeamB.build_run.SystemControl;
import edu.rit.se.coolTeamB.core.Item;
import edu.rit.se.coolTeamB.core.LocalVend;
import edu.rit.se.coolTeamB.core.XYPair;
import edu.rit.se.coolTeamB.exceptions.*;
import edu.rit.se.coolTeamB.mechanics.DatabaseWriter;

import junit.framework.TestCase;



public class CustomerManagerTest extends TestCase 
{
	
	 private int ID;
	 private String itemName;
	 private ArrayList<LocalVend> localVends;
	 private CustomerManager customerManager;
	 
    public CustomerManagerTest(String name) 
    {
    	super(name); 
    }

    public void setUp() throws Exception 
    {
    	int numMachines = 7;
    	int rows = 8;
    	int cols = 7;
    	int depth = 10;
		localVends = new ArrayList<LocalVend>();
		itemName = "Hershey";
	    DatabaseWriter.createDatabase(numMachines,
			    rows, cols, depth);
		for(ID=0; ID < numMachines; ID++)
		{
			localVends.add(new LocalVend(ID, rows, cols, depth));
		}
		customerManager = new CustomerManager(localVends);
    }
    
    public void testAddMoney()
    {
	try
	{
	    customerManager.addMoney(1, 1.75);
	    customerManager.addMoney(2, 2.50);
	    customerManager.addMoney(3, 1.25);
	    customerManager.addMoney(4, 1.50);
	    assertEquals("Running total should be 1.75.", 
		    localVends.get(1).getRunningTotal(), 1.75);
	    assertEquals("Running total should be 2.50.", 
		    localVends.get(2).getRunningTotal(), 2.50);
	    assertEquals("Running total should be 1.25.", 
		    localVends.get(3).getRunningTotal(), 1.25);
	    assertEquals("Running total should be 1.50.", 
		    localVends.get(4).getRunningTotal(), 1.50);
	}
	catch(Exception e)
	{
	    e.printStackTrace();
	}

    }
    
	public void testAddToShoppingCart()
	{
	    try
	    {
		localVends.get(2).addItem(itemName, 2.00, Calendar.getInstance(),
				2, 2, 2);
		customerManager.addToShoppingCart(2, 2, 2, 1);
		assertEquals("Current amount of item in stock should be 1.", 
    			localVends.get(2).getTotalStock().get(new XYPair(2, 2)),
    			(Integer)1);
	    }
	    catch(Exception e)
	    {
		e.printStackTrace();
	    }
	}
}
	

