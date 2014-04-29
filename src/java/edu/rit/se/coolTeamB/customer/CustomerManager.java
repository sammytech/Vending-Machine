/*
 * File: CustomerManager.java
 * Manager which provides access for customer users to interact with the Smart
 * Vending Machine (SVM) database.
 * 
 * Version 1.0
 * 
 * Authors:
 *   Michael Surdouski (mxs1649@rit.edu)
 *   
 *   (TODO) when UI's have been implemented, use actionListeners and make
 *   ALL methods private except actionPerformed(ActionEvent e)
 *   
 */

package edu.rit.se.coolTeamB.customer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import edu.rit.se.coolTeamB.core.Item;
import edu.rit.se.coolTeamB.core.LocalVend;
import edu.rit.se.coolTeamB.exceptions.*;

/******************************************************************************
 * The <CODE>CustomerManager</CODE> Java class provides utility functions which
 * are performed using the customer user interface - </CODE>CustomerUI<CODE>.
 * 
 * @version
 *   1.00 21 Mar 2013
 * @author
 *   Michael Surdouski (mxs1649@rit.edu)
 ******************************************************************************/
public class CustomerManager extends Observable implements ActionListener
{
	List<LocalVend> localVends;

	/**
	 * <CODE>CustomerManager</CODE> handles all customer access level changing
	 * of information in the SVM system.
	 * @param _localVendingMachine
	 *   a copy of the vending machine to perform actions on
	 */
	public CustomerManager(List<LocalVend> localVends)
	{
		this.localVends = localVends;
	}

	/**
	 * Verifies that the machine is unlocked, then locks it to begin the
	 * services provided.
	 * @param - none
	 * <dt><b>Postcondition:</b><dd>
	 *   localVendingMachine lock status is true
	 * @return
	 *   true if ready to begin transaction
	 * @throws IllegalLockOperationException 
	 */
	public boolean verifyBegin(int ID) throws IllegalLockOperationException
	{
		if(!localVends.get(ID).getLock())
		{
			triggerLock(ID);
			return true;
		}
		return false;
	}
	
	public void unlock(int ID) throws IllegalLockOperationException
	{
	    localVends.get(ID).openLock();
	}
	
	public boolean getLock(int ID)
	{
	    return localVends.get(ID).getLock();
	}

	/**
	 * Add money to running total on localVendingMachine.
	 * <dt><b>Precondition:</b><dd>
	 *   amount of money added doesn't bypass double memory limitations
	 *   the amount of money isn't negative
	 * @param amount
	 *   the amount of money to be added to running total on machine
	 * <dt><b>Postcondition:</b><dd>
	 *   amount has been added to running total
	 */
	public void addMoney(int ID, double amount) 
			throws NegativeInputException
	{
		localVends.get(ID).addMoney(amount);
	}

	/**
	 * Add specified amount of item to shopping cart.
	 * <dt><b>Preconditions:</b><dd>
	 *   howMany <= (current stock of item - amount of item already existing
	 *     on the shopping cart)
	 *   howMany > 0
	 *   item with name itemName exists in localVendingMachine register
	 * @param item
	 * @param howMany
	 *   the amount to be added to shopping cart
	 * <dt><b>Postcondition:</b><dd>
	 *   shopping cart has been updated
	 * @throws NegativeInputException 
	 * @throws StockException 
	 */
	public void addToShoppingCart(int ID, int row, int col, int numberToAdd)
			throws NonExistingStockException, 
			OutOfBoundsStockException, NegativeInputException, 
			StockException
	{
		localVends.get(ID).addToCart(row, col, numberToAdd);
	}

	/**
	 * Complete the transaction for items in shopping cart.
	 * <dt><b>Preconditions:</b><dd>
	 *   shopping cart isn't empty
	 *   there are sufficient funds to purchase items on shopping cart
	 * @return
	 *   the amount of change customer receives
	 * <dt><b>Postcondition:</b><dd>
	 *   shopping cart is empty
	 *   running total for money is empty
	 * @throws OutOfBoundsStockException 
	 * @throws StockException 
	 * @throws NonExistingStockException 
	 */
	public double checkOut(int ID) throws IllegalArgumentException,
			OutOfBoundsStockException, NonExistingStockException, 
			StockException
	{
		return localVends.get(ID).checkout();
	}
	
	public void removeFromCart(int ID, int row, int col) 
		throws OutOfBoundsStockException, NegativeInputException, 
		NonExistingStockException
	{
	    localVends.get(ID).removeFromCart(row, col);
	}
	
	public void cancel(int ID) throws OutOfBoundsStockException, 
		NegativeInputException, StockException
	{
	    localVends.get(ID).cancel();
	}

	public void register(Object UI, int ID)
	{
	    localVends.get(ID).addObserver((Observer) UI);
	    localVends.get(ID).startNotify();
	}
	
	public void deregister(CustomerGUI UI, int ID)
	{
	    localVends.get(ID).deleteObserver(UI);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		// TODO add implementation here when we start using GUI
	}

	/**
	 * Set's lock on LocalVend machine only after it has been verified.
	 * <dt><b>Precondition:</b><dd>
	 *   verify has taken place
	 */
	private void triggerLock(int ID) throws IllegalLockOperationException
	{
		localVends.get(ID).setLock();
	}























}
