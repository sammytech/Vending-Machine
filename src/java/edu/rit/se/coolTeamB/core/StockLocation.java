/*
 * File: StockLocation.java
 * contains all the stock data for a vending machine
 * 
 * Version 1.0
 * 
 * Authors:
 *   Michael Surdouski (mxs1649@rit.edu)
 */

package edu.rit.se.coolTeamB.core;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import edu.rit.se.coolTeamB.exceptions.NegativeInputException;
import edu.rit.se.coolTeamB.exceptions.NonExistingStockException;
import edu.rit.se.coolTeamB.exceptions.NullDateException;
import edu.rit.se.coolTeamB.exceptions.OutOfBoundsStockException;
import edu.rit.se.coolTeamB.exceptions.StockException;

/******************************************************************************
 * The <CODE>StockLocation</CODE> Java class that stores the stock and location
 * data for local vending machine connected to the Smart Vending Machine (SVM) 
 * system.
 * 
 * @version
 *   1.00 21 Mar 2013
 * @author
 *   Michael Surdouski (mxs1649@rit.edu)
 ******************************************************************************/

public class StockLocation
{
    final private int depth;
    final private int row;
    final private int col;
    private HashMap<XYPair, Integer> totalStock;
    private ArrayList<ArrayList<ArrayList<Item>>> items;
    
    /**
     * <CODE>StockLocation</CODE> holds all the stock and location data
     * for items stored in a local vending machine
     * 
     * @param row
     *   number of rows in the machine
     * @param col
     *   number of cols in the machine
     * @param depth
     *   depth of the machine
     * <dt><b>Postcondition:</b><dd>
     *   the StockLocation has been initialized
     * @throws NegativeInputException
     */
    public StockLocation(int row, int col, int depth)
    		throws NegativeInputException
    {
	if (depth < 1 || row < 1 || col < 1)
	{
	    throw new NegativeInputException();
	}
	this.depth = depth;
	this.row = row;
	this.col = col;
	totalStock = new HashMap<XYPair, Integer>();
	items = new ArrayList<ArrayList<ArrayList<Item>>>(row);
	for (int r = 0; r < row; ++r)
	{
	    items.add(new ArrayList<ArrayList<Item>>(col));
	    for (int c = 0; c < col; ++c)
	    {
		items.get(r).add(new ArrayList<Item>(depth));
	    }
	}
    }
    
    /**
     * Returns the stock information for a vending machine.
     * 
     * @param - none
     * @return 
     *   HashMap of the stock data for a vending machine
     */
    public HashMap<XYPair, Integer> getTotalStock()
    {
	return totalStock;
    }
    
    /**
     * Returns the row-col-depth array holding all the items in the
     * vending machine.
     * 
     * @return
     *   row-col-depth array holding all the items in the vending machine
     */
    public ArrayList<ArrayList<ArrayList<Item>>> getItems()
    {
    	return items;
    }
    
    /**
     * Creates and adds an item to the end of the slot at a specified
     * row and column.
     * 
     * @param name
     *   name of the item
     * @param price
     *   price of the item
     * @param expDate
     *   expiration date of the item
     * @param row
     *   row of the item
     * @param col
     *   column of the item
     * @throws StockException
     * @throws NegativeInputException
     * @throws OutOfBoundsStockException
     * @throws NullDateException
     */
    public void addItem(String name, double price, Calendar expDate, int row,
	    		int col) throws StockException,
	    		NegativeInputException, OutOfBoundsStockException,
	    		NullDateException
    {
	Item newItem = new Item(name, price, expDate);
	XYPair tmp = new XYPair(row, col);
	if (!getAtXY(row, col).contains(newItem) && 
		getAtXY(row, col).size() != 0)
	{
	    throw new StockException();
	}
	if (getAtXY(row, col).size() == this.depth)
	{
	    throw new StockException();
	}
	if (totalStock.containsKey(tmp))
	{
	    totalStock.put(tmp, totalStock.get(tmp) + 1);
	}
	else
	{
	    totalStock.put(tmp, 1);
	}
	getAtXY(row, col).add(newItem);
    }
    
    /**
     * Removes the an items from a location in the machine specified by
     * row, column, and depth.
     * 
     * @param row
     *   row of the item
     * @param col
     *   column of the item
     * @param depth
     *   depth of the item
     * @throws OutOfBoundsStockException
     * @throws NonExistingStockException
     */
    public void removeItemFromItems(int row, int col, int depth) 
	    throws OutOfBoundsStockException, NonExistingStockException
    {
	if (this.row < row + 1 || this.col < col + 1 || this.depth < depth + 1 
		|| row < 0 || col < 0 || depth < 0)
	{
	    throw new OutOfBoundsStockException();
	}
	if (getAtXY(row, col).size() == 0)
	{
	    throw new NonExistingStockException();
	}
	if (getAtXY(row, col).size() <= depth)
	{
	    throw new OutOfBoundsStockException();
	}
	getAtXY(row, col).remove(depth);
    }
    
    /**
     * Reduces the stock information for items at a specified row and column
     * position by a specified amount.
     * 
     * @param row
     *   row of the items
     * @param col
     *   column of the items
     * @param numberToRemove
     *   number of items to be removed from stock
     * @throws NonExistingStockException
     * @throws StockException
     * @throws OutOfBoundsStockException
     * @throws NegativeInputException
     */
    public void removeItemFromTotalStock(int row, int col, int numberToRemove) 
	    throws NonExistingStockException, StockException, 
	    OutOfBoundsStockException, NegativeInputException
    {
	XYPair tmp = new XYPair(row, col);
	if (this.row < row + 1 || this.col < col + 1 || row < 0 || col < 0)
	{
	    throw new OutOfBoundsStockException();
	}
	if (!totalStock.containsKey(tmp))
	{
	    throw new NonExistingStockException();
	}
	if (numberToRemove < 0)
	{
	    throw new NegativeInputException();
	}
	if (totalStock.get(tmp) - numberToRemove < 0)
	{
	    throw new StockException();
	}
	if (totalStock.get(tmp) - numberToRemove != 0)
	{
	    totalStock.put(tmp, totalStock.get(tmp) - numberToRemove);
	}
	else
	{
	    totalStock.remove(tmp);
    	}
    }
    
    /**
     * Removes item information from a certain position specified by row
     * and column.
     * 
     * @param row
     *   row of the position
     * @param col
     *   column of the position
     * @throws OutOfBoundsStockException
     * @throws StockException
     */
    public void removeInstanceFromTotalStock(int row, int col) throws 
    		OutOfBoundsStockException, StockException
    {
    	if (this.row < row + 1 || this.col < col + 1 || row < 0 || col < 0)
    	{
    	    throw new OutOfBoundsStockException();
    	}
    	if (totalStock.get(new XYPair(row, col)) != 0)
    	{
    		throw new StockException();
    	}
    	totalStock.remove(new XYPair(row, col));
    }
    
    /**
     * Returns an ArrayList<Item> of all the items in a slot at a certain
     * row and column.
     * 
     * @param row
     *   row of the items
     * @param col
     *   column of the items
     * @return
     *   ArrayList<Item> containing all items at that position
     * @throws OutOfBoundsStockException
     */
    public ArrayList<Item> getAtXY(int row, int col) 
	    			   throws OutOfBoundsStockException
    {
	if (this.row < row + 1 || this.col < col + 1 || row < 0 || col < 0)
	{
	    throw new OutOfBoundsStockException();
	}
	return items.get(row).get(col);
    }
    
    /**
     * Returns an item located at a certain row, column, and depth.
     * 
     * @param row
     *   row of the item
     * @param col
     *   column of the item
     * @param depth
     *   depth of the item
     * @return
     *   item at the specified position
     * @throws OutOfBoundsStockException
     */
    public Item getAtXYZ(int row, int col, int depth) 
	    		 throws OutOfBoundsStockException
    {
	if (this.row < row + 1 || this.col < col + 1 || this.depth < depth + 1
		|| row < 0 || col < 0 || depth < 0)
	{
	    throw new OutOfBoundsStockException();
	}
	try
	{
	    return items.get(row).get(col).get(depth);
	}
	catch(Exception e)
	{
	    throw new OutOfBoundsStockException();
	}
    }
    
    /*
     * Only call this in checkout!!!!
     */
    /**
     * Removes a specified number of items from the vending machine at a
     * specified row-column position.
     * 
     * @param pair
     *   row-column position of the items
     * @param numberToRemove
     *   number of items to remove
     * @throws OutOfBoundsStockException
     * @throws NonExistingStockException
     * @throws StockException
     */
    public void checkout(XYPair pair, int numberToRemove) 
	    	throws OutOfBoundsStockException, NonExistingStockException, 
	    	StockException
    {
	while (numberToRemove > 0)
	{
	    removeItemFromItems(pair.x, pair.y, 0);
	    --numberToRemove;
	}
	
    }

    /**
     * Adds a specified number of stock to the item stock at a specified
     * row and column.
     * 
     * @param row
     *   row of the items
     * @param col
     *   column of the items
     * @param numberToAdd
     *   number of items to be added to stock
     * @throws OutOfBoundsStockException
     * @throws NegativeInputException
     */
    public void addToTotalStockOnly(int row, int col, int numberToAdd) 
	    throws OutOfBoundsStockException, NegativeInputException
    {
	if (this.row < row + 1 || this.col < col + 1 || row < 0 || col < 0)
	{
	    throw new OutOfBoundsStockException();
	}
	if (numberToAdd < 0)
	{
	    throw new NegativeInputException();
	}
	XYPair tmp = new XYPair(row, col);
	if (totalStock.containsKey(tmp))
	{
	    totalStock.put(tmp, totalStock.get(tmp) + numberToAdd);
	}
	else
	{
	    totalStock.put(tmp, numberToAdd);
	}
    }
    
    /**
     * Returns the number of rows in the current vending machine
     * @param - none
     * @return rows
     *   the number of rows in the vending machine
     */
    public int getNumRows()
    {
	return row;
    }
    
    /**
     * Returns the number of columns in the current vending machine
     * @param - none
     * @return cols
     *   the number of columns in the vending machine
     */   
    public int getNumCols()
    {
	return col;
    }
    
    /**
     * Returns the depth of the vending machine
     * @param - none
     * @return depth
     *   the number slots available in each space.
     */   
    public int getDepth()
    {
	return depth;
    }
}
