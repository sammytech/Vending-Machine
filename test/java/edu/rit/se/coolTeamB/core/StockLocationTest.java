package edu.rit.se.coolTeamB.core;

import java.util.Calendar;

import junit.framework.TestCase;
import edu.rit.se.coolTeamB.exceptions.*;

public class StockLocationTest extends TestCase
{ 
    private String name;
    private double price;
    private Item item;
    private StockLocation stockLocation;
    
    
    public StockLocationTest(String name)
    {
	super(name);
    }

    public void setUp() throws Exception
    {
	name = "hershey";
	price = 2.00;
	item = new Item(name, price, Calendar.getInstance());
	stockLocation = new StockLocation(8, 8, 10);
    }
    
    public void testNegativeValuesConstruction()
    {
    	try
    	{
    	    stockLocation = new StockLocation(10, 8, -1);
    	    fail("NegativeInputException should have been thrown.");
    	}
    	catch(Exception e)
    	{
    	    assertEquals(NegativeInputException.class, e.getClass());
    	}
    	try
    	{
    	    stockLocation = new StockLocation(10, -1, 8);
    	    fail("NegativeInputException should have been thrown.");
    	}
    	catch(Exception e)
    	{
    	    assertEquals(NegativeInputException.class, e.getClass());
    	}
    	try
    	{
    	    stockLocation = new StockLocation(-1, 8, 8);
    	    fail("NegativeInputException should have been thrown.");
    	}
    	catch(Exception e)
    	{
    	    assertEquals(NegativeInputException.class, e.getClass());
    	}
    }
    
    public void testAddItem()
    {
	try
	{
	    stockLocation.addItem(item.getName(), item.getPrice(), item.getDate(), 6, 6);
	    assertEquals(1, stockLocation.getAtXY(6, 6).size());
	    assertEquals((Integer) 1, stockLocation.getTotalStock().get(new XYPair(6, 6)));
	}
	catch(Exception e)
	{
	    e.printStackTrace();
	}
    }
    
    public void testOutOfBoundsAddItem()
    {
	try
	{
	    stockLocation.addItem(item.getName(), item.getPrice(), item.getDate(), -1, 1);
	    fail("OutOfBoundsStockException should have been thrown.");
	}
	catch(Exception e)
	{
	    assertEquals(OutOfBoundsStockException.class, e.getClass());
	}
	try
	{
	    stockLocation.addItem(item.getName(), item.getPrice(), item.getDate(), 1, -1);
	    fail("OutOfBoundsStockException should have been thrown.");
	}
	catch(Exception e)
	{
	    assertEquals(OutOfBoundsStockException.class, e.getClass());
	}
	try
	{
	    stockLocation.addItem(item.getName(), item.getPrice(), item.getDate(), 9, 1);
	    fail("OutOfBoundsStockException should have been thrown.");
	}
	catch(Exception e)
	{
	    assertEquals(OutOfBoundsStockException.class, e.getClass());
	}
	try
	{
	    stockLocation.addItem(item.getName(), item.getPrice(), item.getDate(), 1, 9);
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
	    stockLocation.addItem(item.getName(), item.getPrice(), date, 6, 6);
	    fail("NullDateException should have been thrown.");
	}
	catch(Exception e)
	{
	    assertEquals(NullDateException.class, e.getClass());
	}
    }
    
    public void testFullAddItem()
    {
	try
	{
	    for (int i = 0; i < 10; ++i)
	    {
		stockLocation.addItem(item.getName(), item.getPrice(), item.getDate(), 6, 6);
	    }
	    stockLocation.addItem(item.getName(), item.getPrice(), item.getDate(), 6, 6);
	    fail("StockException should have been thrown.");
	}
	catch(Exception e)
	{
	    assertEquals(StockException.class, e.getClass());
	}
    }
    
    public void testDifferentStockAddItem()
    {
	try
	{
	    stockLocation.addItem(item.getName(), item.getPrice(), item.getDate(), 6, 6);
	    stockLocation.addItem("conflicting name", item.getPrice(), item.getDate(), 6, 6);
	    fail("StockException should have been thrown.");
	}
	catch(Exception e)
	{
	    assertEquals(StockException.class, e.getClass());
	}
    }

    /*
     * TODO Finish unit tests for StockLocation starting with
     * 		RemoveItemsFromItems...
     */
    
    public void testRemoveItemsFromItems()
    {
	try
	{
	    stockLocation.addItem(item.getName(), item.getPrice(), item.getDate(), 6, 6);
	    stockLocation.removeItemFromItems(6, 6, 0);
	    assertEquals(0, stockLocation.getAtXY(6, 6).size());
	}
	catch(Exception e)
	{
	    e.printStackTrace();
	}
    }
    
    public void testOutOfBoundsRemoveItemsFromItems()
    {
	try
	{
	    stockLocation.removeItemFromItems(-1, 1, 1);
	    fail("OutOfBoundsStockException should have been thrown.");
	}
	catch(Exception e)
	{
	    assertEquals(OutOfBoundsStockException.class, e.getClass());
	}
	try
	{
	    stockLocation.removeItemFromItems(1, -1, 1);
	    fail("OutOfBoundsStockException should have been thrown.");
	}
	catch(Exception e)
	{
	    assertEquals(OutOfBoundsStockException.class, e.getClass());
	}
	try
	{
	    stockLocation.removeItemFromItems(1, 1, -1);
	    fail("OutOfBoundsStockException should have been thrown.");
	}
	catch(Exception e)
	{
	    assertEquals(OutOfBoundsStockException.class, e.getClass());
	}
	try
	{
	    stockLocation.removeItemFromItems(11, 1, 1);
	    fail("OutOfBoundsStockException should have been thrown.");
	}
	catch(Exception e)
	{
	    assertEquals(OutOfBoundsStockException.class, e.getClass());
	}
	try
	{
	    stockLocation.removeItemFromItems(1, 11, 1);
	    fail("OutOfBoundsStockException should have been thrown.");
	}
	catch(Exception e)
	{
	    assertEquals(OutOfBoundsStockException.class, e.getClass());
	}
	try
	{
	    stockLocation.removeItemFromItems(1, 1, 11);
	    fail("OutOfBoundsStockException should have been thrown.");
	}
	catch(Exception e)
	{
	    assertEquals(OutOfBoundsStockException.class, e.getClass());
	}
    }
    
    public void testNonExistingRemoveItemsFromItems()
    {
	try
	{
	    stockLocation.removeItemFromItems(6, 6, 0);
	    fail("NonExistingStockException should have been thrown.");
	}
	catch(Exception e)
	{
	    assertEquals(NonExistingStockException.class, e.getClass());
	}
    }
    
    public void testRemoveItemFromTotalStock()
    {
	try
	{
	    stockLocation.addToTotalStockOnly(6, 6, 3);
	    stockLocation.removeItemFromTotalStock(6, 6, 2);
	    assertEquals((Integer) 1, stockLocation.getTotalStock().get(new XYPair(6, 6)));
	}
	catch(Exception e)
	{
	    e.printStackTrace();
	}
    }
    
    public void testNonExistingRemoveItemFromTotalStock()
    {
	try
	{
	    stockLocation.removeItemFromTotalStock(6, 6, 1);
	    fail("NonExistingStockException should have been thrown.");
	}
	catch(Exception e)
	{
	    assertEquals(NonExistingStockException.class, e.getClass());
	}
    }
    
    public void testOutOfBoundsRemoveItemFromTotalStock()
    {
	try
	{
	    stockLocation.removeItemFromTotalStock(-1, 1, 1);
	    fail("OutOfBoundsStockException should have been thrown.");
	}
	catch(Exception e)
	{
	    assertEquals(OutOfBoundsStockException.class, e.getClass());
	}
	try
	{
	    stockLocation.removeItemFromTotalStock(1, -1, 1);
	    fail("OutOfBoundsStockException should have been thrown.");
	}
	catch(Exception e)
	{
	    assertEquals(OutOfBoundsStockException.class, e.getClass());
	}
	try
	{
	    stockLocation.removeItemFromTotalStock(11, 1, 1);
	    fail("OutOfBoundsStockException should have been thrown.");
	}
	catch(Exception e)
	{
	    assertEquals(OutOfBoundsStockException.class, e.getClass());
	}
	try
	{
	    stockLocation.removeItemFromTotalStock(1, 11, 1);
	    fail("OutOfBoundsStockException should have been thrown.");
	}
	catch(Exception e)
	{
	    assertEquals(OutOfBoundsStockException.class, e.getClass());
	}
    }
    
    public void testBadAmountRemoveItemFromTotalStock()
    {
	try
	{
	    stockLocation.addItem(item.getName(), item.getPrice(), item.getDate(), 6, 6);
	}
	catch(Exception e)
	{
	    e.printStackTrace();
	}
	try
	{
	    stockLocation.removeItemFromTotalStock(6, 6, -1);
	    fail("NegativeInputException should have been thrown.");
	}
	catch(Exception e)
	{
	    assertEquals(NegativeInputException.class, e.getClass());
	}
	try
	{
	    stockLocation.removeItemFromTotalStock(6, 6, 11);
	    fail("StockException should have been thrown.");
	}
	catch(Exception e)
	{
	    assertEquals(StockException.class, e.getClass());
	}
    }
    
    public void testOutOfBoundsGetAtXY()
    {
	try
	{
	    stockLocation.getAtXY(-1, 1);
	    fail("OutOfBoundsStockException should have been thrown.");
	}
	catch(Exception e)
	{
	    assertEquals(OutOfBoundsStockException.class, e.getClass());
	}
	try
	{
	    stockLocation.getAtXY(1, -1);
	    fail("OutOfBoundsStockException should have been thrown.");
	}
	catch(Exception e)
	{
	    assertEquals(OutOfBoundsStockException.class, e.getClass());
	}
	try
	{
	    stockLocation.getAtXY(11, 1);
	    fail("OutOfBoundsStockException should have been thrown.");
	}
	catch(Exception e)
	{
	    assertEquals(OutOfBoundsStockException.class, e.getClass());
	}
	try
	{
	    stockLocation.getAtXY(1, 11);
	    fail("OutOfBoundsStockException should have been thrown.");
	}
	catch(Exception e)
	{
	    assertEquals(OutOfBoundsStockException.class, e.getClass());
	}
    }
    
    public void testOutOfBoundsGetAtXYZ()
    {
	try
	{
	    stockLocation.getAtXYZ(-1, 1, 1);
	    fail("OutOfBoundsStockException should have been thrown.");
	}
	catch(Exception e)
	{
	    assertEquals(OutOfBoundsStockException.class, e.getClass());
	}
	try
	{
	    stockLocation.getAtXYZ(1, -1, 1);
	    fail("OutOfBoundsStockException should have been thrown.");
	}
	catch(Exception e)
	{
	    assertEquals(OutOfBoundsStockException.class, e.getClass());
	}
	try
	{
	    stockLocation.getAtXYZ(1, -1, 1);
	    fail("OutOfBoundsStockException should have been thrown.");
	}
	catch(Exception e)
	{
	    assertEquals(OutOfBoundsStockException.class, e.getClass());
	}
	try
	{
	    stockLocation.getAtXYZ(11, 1, 1);
	    fail("OutOfBoundsStockException should have been thrown.");
	}
	catch(Exception e)
	{
	    assertEquals(OutOfBoundsStockException.class, e.getClass());
	}
	try
	{
	    stockLocation.getAtXYZ(1, 11, 1);
	    fail("OutOfBoundsStockException should have been thrown.");
	}
	catch(Exception e)
	{
	    assertEquals(OutOfBoundsStockException.class, e.getClass());
	}
	try
	{
	    stockLocation.getAtXYZ(1, 1, 11);
	    fail("OutOfBoundsStockException should have been thrown.");
	}
	catch(Exception e)
	{
	    assertEquals(OutOfBoundsStockException.class, e.getClass());
	}
    }
    
    public void testNullPointerGetAtXYZ()
    {
	try
	{
	    stockLocation.getAtXYZ(1, 1, 1);
	    fail("OutOfBoundsStockException should have been thrown.");
	}
	catch(Exception e)
	{
	    assertEquals(OutOfBoundsStockException.class, e.getClass());
	}
    }
    
    public void testAddToTotalStockOnly()
    {
	try
	{
	    stockLocation.addToTotalStockOnly(6, 6, 2);
	    assertEquals((Integer) 2, stockLocation.getTotalStock().get(new XYPair(6, 6)));
	}
	catch(Exception e)
	{
	    e.printStackTrace();
	}
    }
    
    public void testOutOfBoundsAddToTotalStockOnly()
    {
	try
	{
	    stockLocation.addToTotalStockOnly(-1, 1, 1);
	    fail("OutOfBoundsStockException should have been thrown.");
	}
	catch(Exception e)
	{
	    assertEquals(OutOfBoundsStockException.class, e.getClass());
	}
	try
	{
	    stockLocation.addToTotalStockOnly(1, -1, 1);
	    fail("OutOfBoundsStockException should have been thrown.");
	}
	catch(Exception e)
	{
	    assertEquals(OutOfBoundsStockException.class, e.getClass());
	}
	try
	{
	    stockLocation.addToTotalStockOnly(11, 1, 1);
	    fail("OutOfBoundsStockException should have been thrown.");
	}
	catch(Exception e)
	{
	    assertEquals(OutOfBoundsStockException.class, e.getClass());
	}
	try
	{
	    stockLocation.addToTotalStockOnly(1, 11, 1);
	    fail("OutOfBoundsStockException should have been thrown.");
	}
	catch(Exception e)
	{
	    assertEquals(OutOfBoundsStockException.class, e.getClass());
	}
    }
    
    public void testBadAmountAddToTotalStockOnly()
    {
	try
	{
	    stockLocation.addToTotalStockOnly(6, 6, -1);
	    fail("NegativeInputException should have been thrown.");
	}
	catch(Exception e)
	{
	    assertEquals(NegativeInputException.class, e.getClass());
	}
    }
}
