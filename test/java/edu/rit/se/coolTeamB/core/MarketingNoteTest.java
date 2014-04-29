package edu.rit.se.coolTeamB.core;

import junit.framework.TestCase;
import edu.rit.se.coolTeamB.core.*;
import edu.rit.se.coolTeamB.exceptions.*;

public class MarketingNoteTest extends TestCase {
    private int date;
    private int ID;
    private MarketingNote testNote = null;

    public MarketingNoteTest(String name) {
	super(name);
    }

    public void setUp() throws Exception {
	ID = 0;
	testNote = new MarketingNote(ID);
    }
    
    public void testNullDate()
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
	    testNote = new MarketingNote(-1);
	    fail("IllegalArgumentException should have ben thrown.");
	}
	catch(Exception e)
	{
	    assertEquals(NegativeInputException.class, e.getClass());
	}
    }
    
    
    

}
