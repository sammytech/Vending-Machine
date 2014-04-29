package edu.rit.se.coolTeamB.core;

import java.util.Calendar;

import junit.framework.TestCase;
import edu.rit.se.coolTeamB.exceptions.*;
import edu.rit.se.coolTeamB.mechanics.DatabaseWriter;

public class ShoppingCartTest extends TestCase
{
    private ShoppingCart testCart;
    private LocalVend testVend;

    public ShoppingCartTest(String name)
    {
	super(name);
    }

    public void setUp() throws Exception
    {
	int rows = 8;
	int cols = 7;
	int depth = 10;
    DatabaseWriter.createDatabase(1,
		    rows, cols, depth);
	testCart = new ShoppingCart();
	testVend = new LocalVend(0, rows, cols, depth);
	testVend.addItem("hershey", 2.00, Calendar.getInstance(), 6, 6, 3);
    }
    
    public void testAdd()
    {
	try
	{
	    testCart.add(6, 6, 3, testVend);
	    assertEquals((Integer) 3, testCart.getCart().get(new XYPair(6, 6)));
	    assertEquals(6.00, testCart.getCost());
	}
	catch(Exception e)
	{
	    e.printStackTrace();
	}
    }
    
    public void testOutOfBoundsAdd()
    {
	try
	{
	    testCart.add(-1, 1, 1, testVend);
	    fail("OutOfBoundsStockException should have been thrown");
	}
	catch(Exception e)
	{
	    assertEquals(OutOfBoundsStockException.class, e.getClass());
	}
	try
	{
	    testCart.add(1, -1, 1, testVend);
	    fail("OutOfBoundsStockException should have been thrown");
	}
	catch(Exception e)
	{
	    assertEquals(OutOfBoundsStockException.class, e.getClass());
	}
	try
	{
	    testCart.add(11, 1, 1, testVend);
	    fail("OutOfBoundsStockException should have been thrown");
	}
	catch(Exception e)
	{
	    assertEquals(OutOfBoundsStockException.class, e.getClass());
	}
	try
	{
	    testCart.add(1, 11, 1, testVend);
	    fail("OutOfBoundsStockException should have been thrown");
	}
	catch(Exception e)
	{
	    assertEquals(OutOfBoundsStockException.class, e.getClass());
	}
    }
    
    public void testNegativeInputAdd()
    {
	try
	{
	    testCart.add(1, 1, -1, testVend);
	    fail("NegativeInputException should have been thrown.");
	}
	catch(Exception e)
	{
	    assertEquals(NegativeInputException.class, e.getClass());
	}
    }
 }
