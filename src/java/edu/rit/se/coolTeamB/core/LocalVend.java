/*
 * File: LocalVend.java
 * A local vending machine on the Smart Vending Machine (SVM) server.
 * 
 * Version 1.0
 * 
 * Authors:
 *   Michael Surdouski (mxs1649@rit.edu)
 *   
 *   (TODO) fill out methods with correct behavior, including observable
 *   
 *   (TODO) implement vending machine capacity attributes and handle any 
 *   	    related functionality
 */

package edu.rit.se.coolTeamB.core;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Observable;

import edu.rit.se.coolTeamB.exceptions.*;
import edu.rit.se.coolTeamB.mechanics.*;

/******************************************************************************
 * The <CODE>LocalVend</CODE> Java class extends Observable and stores the 
 * data for a local vending machine connected to the Smart Vending 
 * Machine (SVM) system.
 * 
 * @version
 *   1.00 21 Mar 2013
 * @author
 *   Michael Surdouski (mxs1649@rit.edu)
 ******************************************************************************/
public class LocalVend extends Observable
{
    final int ID;
    private boolean lock;	
    private StockLocation stockLocation;
    private ShoppingCart shoppingCart;
    private double runningTotal;
    private RestockerNote restockerNote;
    private MarketingNote marketingNote;

    /* 
     * (TODO) this message should be incorporated into future versions
     */
    private String inUseMessage;
    

    /**
     * <CODE>LocalVend</CODE> stores all stock information which is necessary 
     * for the SVM system to operate.
     * 
     * <dt><b>Precondition:</b><dd>
     *   _ID is not negative
     * @param _ID
     *   ID of the vending machine
	 * @param row
	 *   number of rows in the vending machine
	 * @param col
	 *   number of columns in the vending machine
	 * @param depth
	 *   depth of the vending machine
     * <dt><b>Postcondition:</b><dd>
     *   all members have been initialized with values below (not all have 
     *   values upon initialization, in which case they aren't listed)
     *     lock - false
     *     runningTotal - 00.00
     * @throws StockException 
     * @throws NullDateException
     * @throws ExistingStockException
     * @throws OutOfBoundsStockException
     * @throws NegativeInputException
     *     
     */
    public LocalVend(int _ID, int row, int col, int depth) 
	    	     throws NegativeInputException,
	    	     OutOfBoundsStockException, ExistingStockException, 
	    	     StockException, NullDateException
    {
	if (_ID < 0 || row < 1 || col < 1 || depth < 1)
	{
	    throw new NegativeInputException();
	}
	ID = _ID;
	lock = false;
	stockLocation = new StockLocation(row, col, depth);
	

	// fill machine from its stock file
	
	shoppingCart = new ShoppingCart();
	runningTotal = 00.00;
	restockerNote = new RestockerNote(_ID);
	marketingNote = new MarketingNote(_ID);

	fillMachine();

    }

    /**
     * Locks the vending machine.
     * <dt><b>Precondition:</b><dd>
     *   lock status is false
     * @param - none
     * <dt><b>Postcondition:</b><dd>
     *   lock status is true
     * @throws IllegalLockOperationException
     */
    public void setLock() throws IllegalLockOperationException
    {
	if (lock)
	{
	    throw new IllegalLockOperationException();
	}
	lock = true;
    }

    /**
     * Unlocks the vending machine.
     * <dt><b>Precondition:</b><dd>
     *   lock status is true
     * @param - none
     * <dt><b>Postcondition:</b><dd>
     *   lock status is false
     * @throws IllegalLockOperationException
     */
    public void openLock() throws IllegalLockOperationException
    {
	if (!lock)
	{
	    throw new IllegalLockOperationException();
	}
	lock = false;
    }

    /**
     * Returns the current lock status.
     * @param - none
     * @return lock
     *   the current lock status
     */
    public boolean getLock()
    {
	return lock;
    }

    /**
     * Returns the current stock information.
     * @param - none
     * @return
     *   hashMap holding positions with the stock at that positions
     */
    public HashMap<XYPair, Integer> getTotalStock()
    {
	return stockLocation.getTotalStock();
    }
    
    /**
     * Returns the current shopping cart.
     * @param - none
     * @return shoppingCart
     *   the current shopping cart hash map
     */
    public HashMap<XYPair, Integer> getShoppingCart()
    {
	return shoppingCart.getCart();
    }
    
    /**
     * Returns the Item at a specified row and column and depth
     * @param - row
     * 	 the row of the item
     * @param - col
     * 	 the column of the item
     * @param - depth
     *   the depth position of the item
     * @return Item
     *   the item at the specified location
     * @throws OutOfBoundsStockException
     */
    public Item getAtXYZ(int row, int col, int depth)
    			 throws OutOfBoundsStockException
    {
	return stockLocation.getAtXYZ(row, col, depth);
    }

    /**
     * Returns an ArrayList<Item> holding all items at the specified
     * row and column.
     * 
     * @param - row
     * 	 the row of the items
     * @param - col
     * 	 the column of the items
     * @return ArrayList<Item>
     *   the ArrayList<Item> for the specified location
     *  @throws OutOfBoundsStockException
     */
    public ArrayList<Item> getAtXY(int row, int col)
    				   throws OutOfBoundsStockException
    {
	return stockLocation.getAtXY(row, col);
    }
    
    /**
     * Returns the number of rows in the current vending machine
     * @param - none
     * @return rows
     *   the number of rows in the vending machine
     */
    public int getNumRows()
    {
	return stockLocation.getNumRows();
    }
    
    /**
     * Returns the number of columns in the current vending machine
     * @param - none
     * @return cols
     *   the number of columns in the vending machine
     */
    public int getNumCols()
    {
	return stockLocation.getNumCols();
    }
    
    /**
     * Returns the depth of the vending machine
     * @param - none
     * @return depth
     *   the number slots available in each space.
     */
    public int getDepth()
    {
	return stockLocation.getDepth();
    }

    /**
     * Returns the current restocker note.
     * @param - none
     * @return restockerNote
     */
    public RestockerNote getRestockerNote()
    {
	return restockerNote;
    }

    /**
     * Returns the current marketing note.
     * @param - none
     * @return marketingNote
     */
    public MarketingNote getMarketingNote()
    {
	return marketingNote;
    }
    
    /**
     * Fills up the item slot at a certain row and column with items of the
     * same type as already are in the slot but with new expiration dates.
     * @param row
     *   row of the slot that is to be restocked
     * @param col
     *  column of the slot that is to be restocked
     * @param expDate
     *   expiration date for the new items being restocked
     * @throws StockException
     * @throws NegativeInputException
     * @throws OutOfBoundsStockException
     * @throws NullDateException
     * @throws NonExistingStockException
     */
    public void restock(int row, int col, Calendar expDate) 
	    throws StockException, NegativeInputException, 
	    OutOfBoundsStockException, 
	    NullDateException, NonExistingStockException
    {
	if (row < 0 || col < 0 || row > stockLocation.getNumRows() - 1 || 
			col > stockLocation.getNumCols() - 1)
	{
	    throw new OutOfBoundsStockException();
	}
	if (getAtXY(row, col).size() == 0)
	{
	    throw new NonExistingStockException();
	}
	while (getAtXY(row, col).size() != getDepth())
	{
	    stockLocation.addItem(getAtXYZ(row, col, 0).getName(),
		    getAtXYZ(row, col, 0).getPrice(), expDate, row, col);
	}
	writeStockFile();
    setChanged();
    notifyObservers(this);
    }

    /**
     * Removes all items from the slot at the specified row and column.
     * 
     * @param row
     *   row of slot to be emptied
     * @param col
     *   column of slot to be emptied
     * @throws OutOfBoundsStockException
     * @throws NonExistingStockException
     * @throws StockException
     * @throws NegativeInputException
     */
    public void removeAll(int row, int col) throws OutOfBoundsStockException, 
    NonExistingStockException, StockException, 
    NegativeInputException
    {
	if (getAtXY(row, col).size() == 0)
	{
	    throw new NonExistingStockException();
	}
	stockLocation.removeItemFromTotalStock(row, col, 
		getAtXY(row, col).size());
	while (getAtXY(row, col).size() != 0)
	{
	    // update the report file
	    try
	    {
		DatabaseWriter.writeToReportFile(ID,
			stockLocation.getAtXYZ(row, col, 0), row, col,
			Calendar.getInstance(), ReasonRemoved.BY_REQUEST);
	    }
	    catch(Exception e)
	    {
		System.out.println("Error." +
			" Failed to updated report file for ID " + ID);
	    }
	    // remove item
	    stockLocation.removeItemFromItems(row, col, 0);
	}
	writeStockFile();
	setChanged();
	notifyObservers(this);
    }

    /**
     * Removes all expired items from the vending machine.
     * 
     * @throws OutOfBoundsStockException
     * @throws NonExistingStockException
     * @throws StockException
     * @throws NegativeInputException
     */
    public void removeExpired() 
	    throws OutOfBoundsStockException, 
	    NonExistingStockException, StockException, NegativeInputException
	    {
	for (int rowIter = 0; rowIter < stockLocation.getNumRows(); ++rowIter)
	{
	    for (int colIter = 0; colIter < stockLocation.getNumCols(); ++colIter)
	    {
		for (int depIter = 0; depIter < stockLocation.getDepth(); )
		{
		    if (depIter < stockLocation.getAtXY(rowIter, colIter).size())
		    {
			if (stockLocation.getAtXYZ(rowIter, colIter, 
				depIter).getDate().before(
					Calendar.getInstance()))
			{
			    try
			    {
				DatabaseWriter.writeToReportFile(ID,
					stockLocation.getAtXYZ(rowIter, 
						colIter, depIter), 
						rowIter, 
						colIter,
						Calendar.getInstance(), 
						ReasonRemoved.EXPIRED);
			    }
			    catch(Exception e)
			    {
				System.out.println("Error." +
					" Failed to updated report file for ID " + ID);
			    }
			    // remove
			    stockLocation.removeItemFromTotalStock(rowIter, colIter, 1);
			    stockLocation.removeItemFromItems(rowIter, colIter, depIter);
			}
			else
			{
			    ++depIter;
			}
		    }
		    else
		    {
			++depIter;
		    }
		}
	    }
	}
	writeStockFile();
	setChanged();
	notifyObservers(this);
	    }

    /**
     * Creates and adds an specified number of items to the end
     * of the slot specified by row and column.
     * 
     * @param name
     *   name of the item
     * @param price
     *   price of the item
     * @param expDate
     *   expiration date of the item
     * @param row
     *   row of the slot where item is to be added
     * @param col
     *   column of the slot where item is to be added
     * @param howMany
     *   number of items to be added
     * 
     * @throws OutOfBoundsStockException
     * @throws StockException
     * @throws NegativeInputException
     * @throws NullDateException
     */
    public void addItem(String name, double price, Calendar expDate, int row,
	    int col, int howMany) 
		    throws OutOfBoundsStockException, StockException, 
		    NegativeInputException, NullDateException
		    {
	if (howMany < 0)
	{
	    throw new NegativeInputException();
	}
	if (getDepth() - getAtXY(row, col).size() < howMany)
	{
	    throw new StockException();
	}
	for (int i = 0; i < howMany; ++i)
	{
	    System.out.println("Added " + name + " at " + (row + 1) + ", " + (col + 1));
	    stockLocation.addItem(name, price, expDate, row, col);
	}
	writeStockFile();
	setChanged();
	notifyObservers(this);
    }
    
    /**
     * Adds an number of items from a slot specified by row and column
     * to the shopping cart and removes them from the machine stock.
     * @param row
     *   row of the items
     * @param col
     *   column of the items
     * @param howMany
     *   number of items
     * @throws NegativeInputException
     * @throws NonExistingStockException
     * @throws StockException
     * @throws OutOfBoundsStockException
     */
    public void addToCart(int row, int col, int howMany) 
	    throws NegativeInputException, NonExistingStockException,
	    StockException, OutOfBoundsStockException
    {
	stockLocation.removeItemFromTotalStock(row, col, howMany);
	shoppingCart.add(row, col, howMany, this);
	setChanged();
	notifyObservers(this);
    }
    
    /**
     * Removes items specified by row and column from the shopping cart
     * and puts them back into the machine stock.
     * 
     * @param row
     *   row of the items
     * @param col
     *   column of the items
     * @throws OutOfBoundsStockException
     * @throws NegativeInputException
     * @throws NonExistingStockException
     */
    public void removeFromCart(int row, int col) throws
    		OutOfBoundsStockException, NegativeInputException,
    		NonExistingStockException
    {
	stockLocation.addToTotalStockOnly(row, col,
			shoppingCart.getCart().get(new XYPair(row, col)));
	shoppingCart.remove(row, col, this);
	setChanged();
	notifyObservers(this);
    }

    /**
     * Add money to running total on machine.
     * 
     * <dt><b>Precondition:</b><dd>
     *   amount of money added doesn't bypass double memory limitations
     *   the amount of money isn't negative
     * @param amount
     *   the amount of money to be added to running total on machine
     * <dt><b>Postcondition:</b><dd>
     *   amount has been added to running total
     *  @throws NegativeInputException
     */
    public void addMoney(double amount) throws NegativeInputException
    {
	if (amount < 0)
	{
	    throw new NegativeInputException();
	}
	runningTotal += amount;
	setChanged();
	notifyObservers(this);
    }

    /**
     * Return the current amount of money the customer has entered.
     * 
     * @return runningTotal
     *   the amount of money the customer has entered
     */
    public double getRunningTotal()
    {
	return runningTotal;
    }
    
    /**
     * Return the cost of the current items in the shopping cart.
     * 
     * @return cost
     *   the cost for all the items in the shopping cart.
     */
    public double getShoppingCartCost()
    {
	return shoppingCart.getCost();
    }

    /**
     * Perform a checkout on the vending machine for current items in cart.
     * 
     * <dt><b>Precondition:</b><dd>
     *   there is enough money in running total to pay for items
     * @return change
     *   the amount of change returned to customer after purchasing items
     * @throws OutOfBoundsStockException 
     * @throws StockException 
     * @throws NonExistingStockException 
     * @throws IllegalArgumentException
     */
    public double checkout() throws IllegalArgumentException, 
    		OutOfBoundsStockException, NonExistingStockException, 
    		StockException
    {
		if (runningTotal < shoppingCart.getCost())
		{
		    throw new IllegalArgumentException("Not enough money in the " +
			    "runningTotal.");
		}
		for (Map.Entry<XYPair, Integer> h : shoppingCart.getCart().entrySet())
		{
		    // update the report file
		    try
		    {
			for (int i = 0; i < h.getValue(); ++i)
			{
			    DatabaseWriter.writeToReportFile(ID,
				    stockLocation.getAtXYZ(h.getKey().x, h.getKey().y, 0),
		    		h.getKey().x, h.getKey().y,
		    		Calendar.getInstance(), ReasonRemoved.SOLD);
			}
		    }
		    catch(Exception e)
		    {
		    	System.out.println("Error." +
		    			" Failed to updated report file for ID " + ID);
		    }
			stockLocation.checkout(h.getKey(), h.getValue());
		}
		
		double change = runningTotal - shoppingCart.getCost();
		runningTotal = 0;
		shoppingCart.clear();
		setChanged();
		writeStockFile();
		notifyObservers(this);
		return change;
    }
    
    /**
     * Cancels a current order and empties the shopping cart
     * 
     * <dt><b>Postcondition:</b><dd>
     *   The order has been canceled and the shopping cart is empty.
     * @throws StockException 
     * @throws NegativeInputException 
     * @throws OutOfBoundsStockException 
     */
    public void cancel() throws OutOfBoundsStockException,
    		NegativeInputException, StockException
    {
	for (Map.Entry<XYPair, Integer> h : shoppingCart.getCart().entrySet())
	{
	    stockLocation.addToTotalStockOnly(h.getKey().x, h.getKey().y, 
		    			      h.getValue());
	}
	shoppingCart.clear();
	runningTotal = 0;
	setChanged();
	notifyObservers(this);
	
    }

    /**
     * Notifies observes of any changes
     */
    public void startNotify()
    {
	setChanged();
	notifyObservers(this);
    }
    
    /**
     * Write the stock file for the vending machine.
     */
    public void writeStockFile()
    {
    	/* the value 150.23 is a place holder for the internal money
    	 * stock in the machine, should it ever be implemented */
		DatabaseWriter.writeStockFile(ID, stockLocation.getNumRows(),
				stockLocation.getNumCols(), stockLocation.getDepth(),
				150.23, stockLocation.getItems(), restockerNote,
				marketingNote);

    }
    
    /**
     * Fills the vending machine to its previous state by reading in
     * data from the stock file associated with the vending machine.
     * 
     * @throws OutOfBoundsStockException 
     * @throws NegativeInputException
     * @throws StockException
     * @throws NullDateException
     */
    public void fillMachine() throws StockException, NegativeInputException, 
    				OutOfBoundsStockException, NullDateException
    {
		ArrayList<ArrayList<Object>> tmp;
		tmp = DatabaseReader.fillMachine(ID, getNumRows(), getNumCols(), 
						 getDepth());
		int numItems = 0;
		ArrayList<Object> h = null;
		Iterator<ArrayList<Object>> iter = tmp.iterator();
		
		// find out how many items there are
		h = iter.next();
		numItems = (Integer) h.get(0);

		// add items
		for (int c = 0; c < numItems; c++)
		{
			h = iter.next();
			stockLocation.addItem((String) h.get(0), (double) h.get(1),
					(Calendar) h.get(2), (int) h.get(3), (int) h.get(4));
		}

		// edit restocker note
		h = iter.next();
		if (h == null)
		{
			// no restocker note, nothing to do
		}
		else if (h.get(0) == null)
		{
			
		}
		else if (h.size() == 2)
		{
			restockerNote.edit((String) h.get(1), (Calendar) h.get(0));
		}

		// edit marketing note
		h = iter.next();
		if (h == null)
		{
			// no marketing note, nothing to do
		}
		else if (h.get(0) == null)
		{
			
		}
		else if (h.size() == 2)
		{
			marketingNote.edit((String) h.get(1), (Calendar) h.get(0));
		}
    }
    
}














