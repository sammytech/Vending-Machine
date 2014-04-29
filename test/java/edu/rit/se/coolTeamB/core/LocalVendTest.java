package edu.rit.se.coolTeamB.core;

import java.util.Calendar;

import junit.framework.TestCase;
import edu.rit.se.coolTeamB.core.*;
import edu.rit.se.coolTeamB.exceptions.*;

public class LocalVendTest extends TestCase 
{
    int ID;
    String itemName;
    Item item;
    LocalVend localVend;

    public LocalVendTest(String name) 
    {
	super(name);
    }

    public void setUp() throws Exception 
    {
	ID = 0;
	itemName = "Hershey";
	item = new Item(itemName, 2.00, Calendar.getInstance());
	localVend = new LocalVend(ID, 8, 8, 10);
	for (int row = 0; row < 8; ++row)
	{
	    for (int col = 0; col < 8; ++col)
	    {
		try
		{
		    if (localVend.getAtXY(row, col).size() != 0)
		    {
			localVend.removeAll(row, col);
		    }
		}
		catch(Exception e)
		{
		    e.printStackTrace();	
		}
	    }
	}
    }

    public void testIllegalConstructionID()
    {
	try
	{
	    localVend = new LocalVend(-1, 8, 8, 10);
	    fail("NegativeInputException should have been thrown.");
	}
	catch(Exception e)
	{
	    assertEquals(NegativeInputException.class, e.getClass());
	}
	try
	{
	    localVend = new LocalVend(0, -8, 8, 10);
	    fail("NegativeInputException should have been thrown.");
	}
	catch(Exception e)
	{
	    assertEquals(NegativeInputException.class, e.getClass());
	}
	try
	{
	    localVend = new LocalVend(0, 8, -8, 10);
	    fail("NegativeInputException should have been thrown.");
	}
	catch(Exception e)
	{
	    assertEquals(NegativeInputException.class, e.getClass());
	}
	try
	{
	    localVend = new LocalVend(0, 8, 8, -10);
	    fail("NegativeInputException should have been thrown.");
	}
	catch(Exception e)
	{
	    assertEquals(NegativeInputException.class, e.getClass());
	}
    }
    
    public void testSetLockWhenLocked()
    {
	try
	{
	    localVend.setLock();
	    try
	    {
		localVend.setLock();
		fail("IllegalLockOperationException should have been thrown.");
	    }
	    catch(Exception e)
	    {
		assertEquals(IllegalLockOperationException.class, e.getClass());
	    }
	}
	catch(Exception e)
	{
	    e.printStackTrace();
	}
    }

    public void testOpenLockWhenUnlocked()
    {
	try
	{
	    localVend.openLock();
	    fail("IllegalLockOperationException should have been thrown.");
	}
	catch(Exception e)
	{
	    assertEquals(IllegalLockOperationException.class, e.getClass());
	}
    }
    
    public void testRestock()
    {
	try
	{
	    localVend.addItem(item.getName(), item.getPrice(), item.getDate(), 
		    	      6, 6, 3);
	    localVend.restock(6, 6, Calendar.getInstance());
	    assertEquals(10, localVend.getAtXY(6, 6).size());
	    assertEquals((Integer) 10, localVend.getTotalStock().get(new XYPair(6, 6)));
	    
	}
	catch(Exception e)
	{
	    e.printStackTrace();
	}
    }
    
    public void testOutOfBoundsRestock()
    {
	try
	{
	    localVend.addItem(item.getName(), item.getPrice(), item.getDate(), 6, 6, 3);
	    localVend.restock(-1, 0, Calendar.getInstance());
	    fail("OutOfBoundsStockException should have been thrown.");
	}
	catch(Exception e)
	{
	    assertEquals(OutOfBoundsStockException.class, e.getClass());
	}
	try
	{
	    localVend.restock(0, -1, Calendar.getInstance());
	    fail("OutOfBoundsStockException should have been thrown.");
	}
	catch(Exception e)
	{
	    assertEquals(OutOfBoundsStockException.class, e.getClass());
	}
    }
    
    public void testNullDateRestock()
    {
	try
	{
	    localVend.addItem(item.getName(), item.getPrice(), item.getDate(), 6, 6, 3);
	    localVend.restock(6, 6, null);
	    fail("NullDateException should have been thrown.");
	}
	catch(Exception e)
	{
	    assertEquals(NullDateException.class, e.getClass());
	}
    }
    
    public void testNonExistingRestock()
    {
	try
	{
	    localVend.restock(6, 6, Calendar.getInstance());
	    fail("NonExistingStockException should have been thrown.");
	}
	catch(Exception e)
	{
	    assertEquals(NonExistingStockException.class, e.getClass());
	}
    }
    
    public void testRemoveAll()
    {
	try
	{
	    localVend.addItem(item.getName(), item.getPrice(), item.getDate(), 7, 7, 3);
	    localVend.removeAll(7, 7);
	    assertFalse(localVend.getTotalStock().containsKey(item));
	    assertTrue(localVend.getAtXY(7, 7).size() == 0);
	}
	catch(Exception e)
	{
	    e.printStackTrace();
	}
    }
    
    public void testNonExistingRemoveAll()
    {
	try
	{
	    localVend.removeAll(6, 6);
	    fail("NonExistingStockException should have been thrown.");
	}
	catch(Exception e)
	{
	    assertEquals(NonExistingStockException.class, e.getClass());
	}
    }
    
    public void testOutOfBoundsRemoveAll()
    {
	try
	{
	    localVend.removeAll(-1, -1);
	    fail("OutOfBoundsStockException should have been thrown.");
	}
	catch(Exception e)
	{
	    assertEquals(OutOfBoundsStockException.class, e.getClass());
	}
    }
    
    public void testRemoveExpired()
    {
	Calendar date = Calendar.getInstance();
	date.set(2010, 1, 1);
	try
	{
	    localVend.addItem(item.getName(), item.getPrice(), date, 6, 6, 3);
	    localVend.removeExpired();
	    assertEquals(0, localVend.getAtXY(6, 6).size());
	}
	catch(Exception e)
	{
	    e.printStackTrace();
	}
    }
    
    public void testNonExistingRemoveExpired()
    {
	try
	{
	    localVend.removeAll(6, 6);
	    fail("NonExistingStockException should have been thrown.");
	}
	catch(Exception e)
	{
	    assertEquals(NonExistingStockException.class, e.getClass());
	}
    }
    
    public void testOutOfBoundsRemoveExpired()
    {
	try
	{
	    localVend.removeAll(-1, -1);
	    fail("OutOfBoundsStockException should have been thrown.");
	}
	catch(Exception e)
	{
	    assertEquals(OutOfBoundsStockException.class, e.getClass());
	}
    }

    public void testAddItem()
    {
	try
	{
	    localVend.addItem(item.getName(), item.getPrice(), item.getDate(), 6, 6, 3);
	    assertEquals(3, localVend.getAtXY(6, 6).size());
	    assertEquals((Integer) 3, localVend.getTotalStock().get(new XYPair(6,6)));
	}
	catch(Exception e)
	{
	    e.printStackTrace();
	}
    }

    public void testBadAmountAddItem()
    {
	try
	{
	    localVend.addItem(item.getName(), item.getPrice(), item.getDate(), 6, 6, -1);
	    fail("NegativeInputException should have been thrown.");
	}
	catch(Exception e)
	{
	    assertEquals(NegativeInputException.class, e.getClass());
	}
	try
	{
	    localVend.addItem(item.getName(), item.getPrice(), item.getDate(), 6, 6, 11);
	    fail("StockException should have been thrown.");
	}
	catch(Exception e)
	{
	    assertEquals(StockException.class, e.getClass());
	}
    }
    
    public void testOutOfBoundsAddItem()
    {
	try
	{
	    localVend.addItem(item.getName(), item.getPrice(), item.getDate(), -1, 1, 1);
	    fail("OutOfBoundsStockException should have been thrown.");
	}
	catch(Exception e)
	{
	    assertEquals(OutOfBoundsStockException.class, e.getClass());
	}
	try
	{
	    localVend.addItem(item.getName(), item.getPrice(), item.getDate(), 1, -1, 1);
	    fail("OutOfBoundsStockException should have been thrown.");
	}
	catch(Exception e)
	{
	    assertEquals(OutOfBoundsStockException.class, e.getClass());
	}
	try
	{
	    localVend.addItem(item.getName(), item.getPrice(), item.getDate(), 11, 1, 1);
	    fail("OutOfBoundsStockException should have been thrown.");
	}
	catch(Exception e)
	{
	    assertEquals(OutOfBoundsStockException.class, e.getClass());
	}
	try
	{
	    localVend.addItem(item.getName(), item.getPrice(), item.getDate(), 1, 11, 1);
	    fail("OutOfBoundsStockException should have been thrown.");
	}
	catch(Exception e)
	{
	    assertEquals(OutOfBoundsStockException.class, e.getClass());
	}
    }
    
    public void testNullDateAddItem()
    {
	try
	{
	    Calendar date = null;
	    localVend.addItem(item.getName(), item.getPrice(), date, 6, 6, 2);
	    fail("NullDateException should have been thrown.");
	}
	catch(Exception e)
	{
	    assertEquals(NullDateException.class, e.getClass());
	}
    }
  
    public void testAddMoney()
    {
	try
	{
	    localVend.addMoney(2.00);
	    assertEquals("Running total should be 2.00.", localVend.getRunningTotal(), 2.00);
	}
	catch(Exception e)
	{
	    e.printStackTrace();
	}
    }
    
    public void testNegativeAddMoney()
    {
	try
	{
	    localVend.addMoney(-2.00);
	    fail("NegativeInputException should have been thrown.");
	}
	catch(Exception e)
	{
	    assertEquals(NegativeInputException.class, e.getClass());
	}
    }
    
    public void testCheckOut()
    {
	try
	{
	    localVend.addMoney(2.00);
	    localVend.addItem(item.getName(), item.getPrice(), item.getDate(), 6, 6, 3);
	    localVend.addToCart(6, 6, 1);
	    assertEquals("Amount of change given should be 0.", 0.0, localVend.checkout());
	    assertEquals("Amount in running total should be 0.", 0.0, localVend.getRunningTotal());
	    assertEquals("Amount in stock should be 1.", 2, localVend.getAtXY(6, 6).size());
	    assertEquals("Amount in stock should be 1.", (Integer) 2, localVend.getTotalStock().get(new XYPair(6, 6)));
	    /*
	     * TODO Should add getters for shoppingCart and shoppingCart total in
	     * future versions of LocalVend for testing and for observers so
	     * that they may call them to update the view
	     */
	}
	catch(Exception e)
	{
	    e.printStackTrace();
	}
    }

    public void testNotEnoughMoneyCheckOut()
    {
	try
	{
	    localVend.addMoney(1.00);
	    localVend.addItem(item.getName(), item.getPrice(), item.getDate(), 6, 6, 3);
	    localVend.addToCart(6, 6, 1);
	    try
	    {
		localVend.checkout();
		fail("IllegalArgumentException should have been thrown.");
	    }
	    catch(IllegalArgumentException e)
	    {
		assertEquals(e.getMessage(), "Not enough money in the " +
			"runningTotal.");
	    }
	}
	catch(Exception e)
	{
	    e.printStackTrace();
	}
    }

}
