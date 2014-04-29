/*
 * File: CustomerUI.java
 * Customer access user interface for the Smart Vending Machine (SVM) system.
 * 
 * Version 1.0
 * 
 * Authors:
 *   Michael Surdouski (mxs1649@rit.edu)
 */

package edu.rit.se.coolTeamB.customer;

import edu.rit.se.coolTeamB.core.Item;
import edu.rit.se.coolTeamB.core.LocalVend;
import edu.rit.se.coolTeamB.core.XYPair;
import edu.rit.se.coolTeamB.exceptions.*;
import edu.rit.se.coolTeamB.mechanics.DatabaseWriter;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;
import java.util.HashMap;

import javax.swing.*;

/******************************************************************************
 * The <CODE>CustomerUI</CODE> Java class provides a UI for customer level 
 * access of the SVM system.
 * 
 * @version
 *   1.00 22 Mar 2013
 * @author
 *   Michael Surdouski (mxs1649@rit.edu)
 ******************************************************************************/
public class CustomerUI implements Observer
{
    public int ID;
    private CustomerManager manager;
    private LocalVend thisVend;
    private Scanner in = new Scanner(System.in);

    public CustomerUI(int ID, CustomerManager manager)
    {
		this.ID = ID;
		this.manager = manager;
		setUp(ID);
		try
		{
		    runCLI();
		}
		catch(Exception e)
		{
		    Scanner in = new Scanner(System.in);
		    e.printStackTrace();
		    System.out.println("Press enter to quit.");
		    String waitString = new String(in.nextLine());
		}
		
		// update the stock file when the customer is finished
		thisVend.writeStockFile();
    }

    @Override
    public void update(Observable subject, Object arg)
    {
	if (arg instanceof LocalVend)
	{
	    thisVend = (LocalVend)subject;
	}
    }

    /**
     * Run the command line interface for customer interactions
     * 
     * <dt><b>Precondition:</b><dd>
     *   customer manager is set up correctly     
     * <dt><b>Postcondition:</b><dd>
     *   customer has finished interaction  
     * @throws NegativeInputException 
     * @throws OutOfBoundsStockException 
     * @throws NonExistingStockException 
     * @throws StockException 
     */
    public void runCLI() throws NegativeInputException, 
    		NonExistingStockException, OutOfBoundsStockException, StockException
    {
	/* #########################################################
	 * This is made to run with brute force and is in no way
	 * nice. We have to add a lot functionality before this
	 * becomes nice.
	 * ######################################################### */

	boolean inUse = true;
	boolean readyForCheckout = false;
	String inputString = "";
	String[] parsedInput;
	XYPair tmpItem;
	int tmpVal;
	double tmpDoubleVal;
	double totalCost;
	double tmpMoney;

	System.out.println("Welcome to the smart vending machine " +
		"system SVM:");
	System.out.println("=======================================" +
		"=======================");
	while(inUse)
	{
	    readyForCheckout = false;
	    //System.out.println("- Enter \"d\" to exit interface.");
	    while(!readyForCheckout)
	    {
		System.out.println("Options:");
		System.out.println("- Enter ID # of item you wish to purchase.");
		System.out.println("- Enter \"q\" to cancel order.");
		System.out.println("- Enter \"x\" to proceed to checkout.");
	    listStock();
		inputString = in.nextLine();
		parsedInput = inputString.split(" ");
		if (validIndex(parsedInput))
		{
		    tmpItem = findItem(parsedInput);
		    String name =  thisVend.getAtXYZ(tmpItem.x,
		    		tmpItem.y, 0).getName();
		    System.out.println("You selected: " + name);
		    System.out.println("There are " + 
			    thisVend.getTotalStock().get(tmpItem) + " in stock.");
		    while(true)
		    {
			System.out.println("Options:");
			System.out.println("- Enter # you wish to purchase.");
			System.out.println("- Enter \"0\" to cancel.");
			inputString = in.nextLine();
			parsedInput = inputString.split(" ");
			if(validAmount(parsedInput, tmpItem))
			{
				 name =  thisVend.getAtXYZ(tmpItem.x,
				    		tmpItem.y, 0).getName();
			    tmpVal = Integer.parseInt(parsedInput[0]);
			    if (tmpVal > 0)
			    {
				manager.addToShoppingCart(ID, tmpItem.x, tmpItem.y, 1);
				System.out.println(tmpVal + " items of " + name
					+ " selected.");
				System.out.format("Total cost: $%.2f\n\n",
					thisVend.getShoppingCartCost());
				//listStock();
			    }
			    else
			    {
				System.out.println("Selection cancelled.\n");
				//listStock();
			    }
			    break;
			}
			else
			{
			    System.out.println("Invalid entry.\n");
			    break;
			}

		    }

		}
		else if (isCallForCheckout(parsedInput))
		{
		    readyForCheckout = true;
		}
		
		else if (parsedInput.length == 1 && parsedInput[0].equals("q"))
		{
		    manager.cancel(ID);
		    System.out.println("Successfully cancelled order.\n");
		    listStock();
		}
		else
		{
		    System.out.println("Please enter another item with the "
			    + "correct input standard.");
		}
	    }

	    // ############## doing the checkout ####################
	    tmpMoney = 0.0;
	    totalCost = thisVend.getShoppingCartCost();
	    System.out.println("Ready for checkout.");
	    while (tmpMoney < totalCost)
	    {
		System.out.format("Please insert $%.2f.\n",
			(totalCost - tmpMoney));
		inputString = in.nextLine();
		parsedInput = inputString.split(" ");
		if (validMoneyInput(parsedInput))
		{
		    tmpDoubleVal = Double.parseDouble(parsedInput[0]);
		    tmpMoney += tmpDoubleVal;
		    manager.addMoney(ID, tmpDoubleVal);
		}
	    }
	    System.out.println("Buying items...");
	    System.out.println("Congratulations, you got:");
	    showShoppingCartItems();
	    tmpDoubleVal = manager.checkOut(ID);
	    System.out.format("Please take your change: $%.2f",
		    tmpDoubleVal);
	    System.out.println("\nEnter q to quit or anything else to continue.");
	    inputString = in.nextLine();
	    parsedInput = inputString.split(" ");
	    if ((parsedInput.length == 1) && (parsedInput[0].equals("q")))
	    {
		inUse = false;
	    }
	}
	System.out.println("Thank you for using this smart " +
		"vending machine SVM system.");
	}

    /**
     * Checks if user entered command to check out items.
     * Command to go to checkout is "x".
     * 
     * @param input
     *   user input
     * @return
     *   true if user input equals "x", the command to check out items
     */
    private boolean isCallForCheckout(String[] input)
    {
	// user will enter "x" to checkout items
	if (input.length != 1)
	    return false;

	return (input[0].equals("x"));
    }

    /**
     * Checks if the user input is a number in the valid range for selecting
     * an item. Input has to be an integer between 1 and the number of 
     * different items the vending machine has in stock.
     * 
     * @param input
     *    the user input
     * @return
     *    true if input is an integer between 1 and the number of items
     */
    private boolean validIndex(String[] input)
    {
	int index;

	if (input.length != 1)
	    return false;

	try
	{
	    index = Integer.parseInt(input[0]);
	}
	catch (NumberFormatException e)
	{
	    index = -1; //give it an illegal value so it will return false
	}

	if (index <= 0)
	    return false;
	if (index > thisVend.getTotalStock().size())
	    return false;

	return true;
    }

    /**
     * Checks if the user input for amounts of an item is in a valid range.
     * Input has to be an integer between 0 and the total amount of stock
     * of the item.
     * 
     * @param input
     *    the user input
     * @param tmpItem
     *    the item the amount is specified for
     * @return
     *    true if the input is between 0 and the total stock of the item
     */
    private boolean validAmount(String[] input, XYPair tmpItem)
    {
	int amount;

	if (input.length != 1)
	    return false;

	try
	{
	    amount = Integer.parseInt(input[0]);
	}
	catch (NumberFormatException e)
	{
	    amount = -1; //give it an illegal value so it will return false
	}

	if (amount < 0)
	    return false;
	if (amount > thisVend.getTotalStock().get(tmpItem) )
	    return false;

	return true;    	
    }

    /**
     * Check if user input is a valid dollar amount. Has to be a non-negative
     * integer or double with at most two decimal places.
     * 
     * @param input
     *    user input
     * @return
     *    true if input is a valid dollar amount
     */
    private boolean validMoneyInput(String[] input)
    {
	double amount;
	String[] numberSplit;
	
	if (input.length != 1)
	    return false;

	try
	{
	    amount = Double.parseDouble(input[0]);
	}
	catch (NumberFormatException e)
	{
	    amount = -1.0; //give it an illegal value so it will return false
	}

	if (amount < 0)
	    return false;
	
	
	// check that the number has at most two decimal places
	numberSplit = input[0].split("\\.");
	if (numberSplit.length > 1)
	{
		if (numberSplit[1].length() > 2)
			return false;
	}

	return true;
    }

    /**
     * Takes a number as input and finds item with that index from the
     * vending machine stock
     * 
     * @param input
     *    user input is an integer
     * @return
     *    the item at the index corresponding to the input
     * @throws OutOfBoundsStockException 
     */
    private XYPair findItem(String[] input) throws OutOfBoundsStockException
    {
	HashMap<XYPair, Integer> tmpStock;
	Integer itemCount;
	XYPair result = null; // initialized to null so Eclipse doesn't complain

	tmpStock = thisVend.getTotalStock();
	Iterator<Entry<XYPair, Integer>> iter = tmpStock.entrySet().iterator();
	itemCount = 0;
	while(iter.hasNext())
	{
	    itemCount++;
	    Entry<XYPair, Integer> entry = iter.next();
	    if (itemCount == Integer.parseInt(input[0]) )
	    {
		result = entry.getKey();
		break;
	    }
	}
	
	return result;
	
    }


    /**
     * Prints the items that a user has in his shopping cart to the
     * terminal window.
     * @throws OutOfBoundsStockException 
     * 
     */
    private void showShoppingCartItems() throws OutOfBoundsStockException
    {
	HashMap<XYPair, Integer> tmpStock;
	tmpStock = thisVend.getShoppingCart();

	Iterator<Entry<XYPair, Integer>> iter = tmpStock.entrySet().iterator();
	while(iter.hasNext())
	{
	    Entry<XYPair, Integer> entry = iter.next();
	    
	    String name =  thisVend.getAtXYZ(entry.getKey().x,
				entry.getKey().y, 0).getName();
	    
	    System.out.format("%4d items of %s.\n",
		    entry.getValue(), name);
	}
	System.out.format("Total cost: $%.2f\n", thisVend.getShoppingCartCost());
    }


    /**
     * Print out the stock of the vending machine in a nice
     * fashion in the terminal window.
     * @throws OutOfBoundsStockException 
     */
    private void listStock () throws OutOfBoundsStockException
    {
	HashMap<XYPair, Integer> tmpStock;
	Integer itemCount;

	tmpStock = thisVend.getTotalStock();
	Iterator<Map.Entry<XYPair, Integer>> iter = tmpStock.entrySet().iterator();
	itemCount = 0;
    System.out.format("%4s | %-20s | %5s | %9s\n",
		    "Nr", "Item", "Stock", "Price");
	while(iter.hasNext())
	{
	    itemCount++;
	    Entry<XYPair, Integer> entry = iter.next();
	    
	    String name =  thisVend.getAtXYZ(entry.getKey().x,
				entry.getKey().y, 0).getName();
	    
	    double price =  thisVend.getAtXYZ(entry.getKey().x,
				entry.getKey().y, 0).getPrice();
	    // This will look like for example:
	    //   Nr | Item                 | Stock |     Price
	    //    1 | Chips                |     4 | $   20.33
	    System.out.format("%4d | %-20s | %5d | $%8.2f\n",
		    itemCount, name, entry.getValue(), price);
	}
    }

    private void setUp(int ID)
    {
	manager.register(this, ID);
    }

    /*
     * This needs to be implemented on closing of the user interface, shouldn't
     * need much code here, just make sure stuff is closed.
     * Put all code before manager.deleteObserver(this) and DO NOT DELETE IT!!! 
     */
    //	private void onClose()
    //	{
    //		manager.deleteObserver(this);
    //	}

}
