/*
 * File: ShoppingCart.java
 * contains all the data for items stored in the shopping cart
 * 
 * Version 1.0
 * 
 * Authors:
 *   Michael Surdouski (mxs1649@rit.edu)
 */

package edu.rit.se.coolTeamB.core;

import java.util.HashMap;
import edu.rit.se.coolTeamB.exceptions.*;


/******************************************************************************
 * The <CODE>ShoppingCart</CODE> Java class that stores the shopping cart
 * data for local vending machine connected to the Smart Vending Machine (SVM) 
 * system.
 * 
 * @version
 *   1.00 21 Mar 2013
 * @author
 *   Michael Surdouski (mxs1649@rit.edu)
 ******************************************************************************/

public class ShoppingCart
{
    private HashMap<XYPair, Integer> shoppingCart;
    private double shoppingCartCost;
    
    /**
     * <CODE>ShoppingCart</CODE> Holds all the items stored in a shopping cart
     * during a transaction.
     * 
     * <dt><b>Postcondition:</b><dd>
     *   the ShoppingCart has been initialized
     */
    public ShoppingCart()
    {
	shoppingCart = new HashMap<XYPair, Integer>();
	shoppingCartCost = 0.0;
	
    }
    
    /**
     * Returns the total cost of the shopping cart.
     *
     * @return
     *   the total cost of the items in the shopping cart
     */

    public double getCost()
    {
	return shoppingCartCost;
    }
    
    /**
     * Returns the current shopping cart.
     * 
     * @return
     *   A hashMap holding the current shopping cart
     */
    public HashMap<XYPair, Integer> getCart()
    {
	return shoppingCart;
    }
    
    /**
     * Adds certain amount of items to the shopping cart. This should only be 
     * called due to customer actions.
     * 
     * <dt><b>Precondition:</b><dd>\
     *   numberToAdd isn't negative
     * @param row
     *   the row of the items
     * @param col
     *   the column of the items
     * @param numberToAdd
     *   amount of items to add to the shopping cart
     * @param tmpVend
     *   a copy of the vending machine
     * <dt><b>Postcondition:</b><dd>
     *   shooping cart is updated with correct stock
     * @throws OutOfBoundsStockException 
     * @throws NegativeInputException 
     */
    public void add(int row, int col, int numberToAdd, LocalVend tmpVend) 
	    throws NegativeInputException, OutOfBoundsStockException
    {
	if (tmpVend.getNumRows() < row + 1 || tmpVend.getNumCols() < col + 1 
		|| row < 0 || col < 0)
	{
	    throw new OutOfBoundsStockException();
	}
	if (numberToAdd < 0)
	{
	    throw new NegativeInputException();
	}
	XYPair tmp = new XYPair(row, col);
	if (shoppingCart.containsKey(tmp))
	{
	    shoppingCart.put(tmp, shoppingCart.get(tmp) + numberToAdd);
	}
	else
	{
	    shoppingCart.put(tmp, numberToAdd);
	}
	System.out.println("cost before: " + shoppingCartCost);
	shoppingCartCost += (tmpVend.getAtXYZ(tmp.x, tmp.y, 0).getPrice()) * 
			(numberToAdd);
	shoppingCartCost = (double)(Math.round(shoppingCartCost * 100.0)) / 100.0;
	System.out.println("cost of item: " + tmpVend.getAtXYZ(tmp.x, tmp.y, 0).getPrice() );	
	System.out.println("cost after: " + shoppingCartCost);
    }
    
    /**
     * Removes items at a specified row and column from the shopping cart.
     * 
     * @param row
     *   row of the items
     * @param col
     *   column of the items
     * @param tmpVend
     *   a copy of the vending machine
     * @throws NonExistingStockException
     * @throws OutOfBoundsStockException
     */
    public void remove(int row, int col, LocalVend tmpVend) 
	    throws NonExistingStockException, OutOfBoundsStockException
    {

	if (tmpVend.getNumRows() < row + 1 || tmpVend.getNumCols() < col + 1 
		|| row < 0 || col < 0)
	{
	    throw new OutOfBoundsStockException();
	}
	if (!shoppingCart.containsKey(new XYPair(row, col)))
	{
	    throw new NonExistingStockException();
	}
	shoppingCartCost -= (shoppingCart.get(new XYPair(row, col))) * 
				(tmpVend.getAtXYZ(row, col, 0).getPrice());
	shoppingCart.remove(new XYPair(row, col));
	shoppingCartCost = (double)(Math.round(shoppingCartCost * 100.0)) / 100.0;
    }
    
    /**
     * Clears the shopping cart from all items and sets the cost to zero.
     */
    public void clear()
    {
	shoppingCart.clear();
	shoppingCartCost = 00.00;
    }
}
