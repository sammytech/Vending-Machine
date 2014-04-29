package edu.rit.se.coolTeamB.restocker;
import junit.framework.TestCase;
import java.util.ArrayList;
import java.util.Calendar;

import edu.rit.se.coolTeamB.core.Item;
import edu.rit.se.coolTeamB.core.LocalVend;
import edu.rit.se.coolTeamB.core.XYPair;
import edu.rit.se.coolTeamB.customer.CustomerManager;
import edu.rit.se.coolTeamB.exceptions.*;
import edu.rit.se.coolTeamB.mechanics.DatabaseWriter;



public class RestockerManagerTest extends TestCase {
	
	int ID;
	int testID = 3;
	int numMachines = 7;
	int rows = 8;
	int cols = 7;
	int depth = 10;
	String itemName;
	Item item;
	ArrayList<LocalVend> localVends;
	RestockerManager restockerManager;
	 
    public RestockerManagerTest(String name) 
    {
    	super(name);
    }

    public void setUp() throws Exception 
    {
		localVends = new ArrayList<LocalVend>();
		itemName = "Hershey";
		item = new Item(itemName, 2.00, Calendar.getInstance());
	    DatabaseWriter.createDatabase(numMachines,
			    rows, cols, depth);
		for(ID=0; ID < numMachines; ID++)
		{
			localVends.add(new LocalVend(ID, rows, cols, depth));
		}
		restockerManager = new RestockerManager(localVends);
	    restockerManager.addStock(testID, "Water", 1.99, Calendar.getInstance(),
	    		4, 4, 2);
    }

    public void testAddNewStock()
    {	
	try
	{
	    restockerManager.addStock(testID, "Water", 1.99, Calendar.getInstance(),
	    		2, 3, 2);
	    assertEquals("Current amount of item in stock should be 2.", 
		    localVends.get(testID).getTotalStock().get(new XYPair(2,3)),
		    (Integer)2);
	}
	catch(Exception e)
	{
	    e.printStackTrace();
	}
    }

    public void testAddMoreStock()
    {
	try
	{
	    restockerManager.restock(testID, 4, 4, Calendar.getInstance());
	    assertEquals("Current amount of item in stock should be "+ 
	    		depth + " .", 
		    localVends.get(testID).getTotalStock().get(new XYPair(4,4)),
		    (Integer)depth);
	}
	catch(Exception e)
	{
	    e.printStackTrace();
	}
    }
}
