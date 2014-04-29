package edu.rit.se.coolTeamB.core;

import junit.framework.TestCase;
import edu.rit.se.coolTeamB.exceptions.*;

public class RestockerNoteTest extends TestCase {
    private int date;
    private int ID;
    private RestockerNote testNote = null;

    public RestockerNoteTest(String name) {
	super(name);
    }

    public void setUp() throws Exception {
	ID = 0;
	testNote = new RestockerNote(ID);
    }
    
    public void testNegativeDate()
    {
	try
	{
	    testNote.edit("Something", null);
	    fail("NullDateException should have been thrown.");
	}
	catch(Exception e)
	{
	    assertEquals(NullDateException.class, e.getClass());
	}
    }
    
    public void testNegativeConstructionID()
    {
	try
	{
	    testNote = new RestockerNote(-1);
	    fail("NegativeInputException should have ben thrown.");
	}
	catch(Exception e)
	{
	    assertEquals(NegativeInputException.class, e.getClass());
	}
    }
    
    
    

}
