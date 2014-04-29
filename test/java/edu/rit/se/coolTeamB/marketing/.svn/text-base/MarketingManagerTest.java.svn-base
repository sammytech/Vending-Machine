package edu.rit.se.coolTeamB.marketing;
import java.util.ArrayList;
import java.util.Calendar;

import edu.rit.se.coolTeamB.core.Item;
import edu.rit.se.coolTeamB.core.LocalVend;
import edu.rit.se.coolTeamB.core.XYPair;
import edu.rit.se.coolTeamB.customer.CustomerManager;
import edu.rit.se.coolTeamB.mechanics.DatabaseWriter;
import junit.framework.TestCase;



public class MarketingManagerTest extends TestCase {
	
	int ID;
	int testID = 1;
	int numMachines = 7;
	int rows = 8;
	int cols = 7;
	int depth = 10;
	String itemName;
	Item item;
	ArrayList<LocalVend> localVends;
	MarketingManager marketingManager;
	 
    public MarketingManagerTest(String name) 
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
		item = new Item(itemName, 2.00, Calendar.getInstance());
	    DatabaseWriter.createDatabase(numMachines,
			    rows, cols, depth);
		for(ID=0; ID < numMachines; ID++)
		{
			localVends.add(new LocalVend(ID, rows, cols, depth));
		}
		marketingManager = new MarketingManager(localVends);
    }
	
    public void testWriteMarketingNote()
    {	
	try
	{
	    marketingManager.editMarketingNote(testID, "Hello SVM!",
	    		Calendar.getInstance());
	    assertEquals("Note should be \"Hello SVM!\".", 
		    localVends.get(testID).getMarketingNote().getNoteText(),
		    "Hello SVM!");
	}
	catch(Exception e)
	{
	    e.printStackTrace();
	}
    }

    public void testReadRestockerNote()
    {	
	try
	{
	    assertEquals("Note should be \"<< EMPTY >>\".", 
		    localVends.get(testID).getRestockerNote().getNoteText(),
		    "<< EMPTY >>");
	}
	catch(Exception e)
	{
	    e.printStackTrace();
	}
    }

}
