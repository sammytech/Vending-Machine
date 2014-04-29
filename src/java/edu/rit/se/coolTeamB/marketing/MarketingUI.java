/*
 * File: MarketingUI.java
 * Marketing access user interface for the Smart Vending Machine (SVM) system.
 * 
 * Version 1.0
 * 
 * Authors:
 *   Michael Surdouski (mxs1649@rit.edu)
 */

package edu.rit.se.coolTeamB.marketing;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import edu.rit.se.coolTeamB.core.Item;
import edu.rit.se.coolTeamB.core.LocalVend;
import edu.rit.se.coolTeamB.core.XYPair;
import edu.rit.se.coolTeamB.exceptions.*;

/******************************************************************************
 * The <CODE>MarketingUI</CODE> Java class provides a UI for marketing level 
 * access of the SVM system.
 * 
 * @version
 *   1.00 22 Mar 2013
 * @author
 *   Michael Surdouski (mxs1649@rit.edu)
 ******************************************************************************/
public class MarketingUI implements Observer
{
    public int ID;
    private MarketingManager manager;
    private LocalVend thisVend;

    public MarketingUI(int ID, MarketingManager manager)
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
    }
    
    
    /**
     * <CODE>MarketingUI</CODE> is the command line user interface for the 
     * marketing portion of the system.
     * @throws NegativeInputException
     * @throws NullDateException 
     * @throws OutOfBoundsStockException 
     */
    public void runCLI() throws NegativeInputException, NullDateException, OutOfBoundsStockException
    {
	Scanner in = new Scanner(System.in);
	boolean inUse = true;
	int option = 0;
	while (true)
	{
		System.out.println("\n\nWelcome to the Marketing " +
				"Command Line Interface for the system SVM:");
		System.out.println("=======================================" +
				"============================");
	    System.out.println("1. Read Marketing Note");
	    System.out.println("2. Change Marketing Note");
	    System.out.println("3. Read Restocker Note");
	    System.out.println("4. List Stock");
	    System.out.println("5. Exit");
	    while (true)
	    {
		System.out.print("Choose an option: ");
		try
		{
		    option = Integer.parseInt(in.nextLine());
		    if (option > 0 && option < 6)
		    {
			break;
		    }
		    System.out.println("Invalid entry.");
		}
		catch(NumberFormatException e)
		{
		    System.out.println("Incorrect input.");
		}
	    }
	    if (option == 1)
	    {
		readMarketingNote();
	    }
	    else if (option == 2)
	    {
		changeMarketingNote();
	    }
	    else if (option == 3) 
	    {
		readRestockerNote();
	    }
	    else if (option == 4)
	    {
		listStock();
	    }
	    else if (option == 5)
	    {
		System.out.println("Exiting marketing interface...");
		System.out.println("Goodbye");
		break;
	    }
	}
    }

    public void readMarketingNote()
    {
    	System.out.println("\n+++++++++++++++");
    	System.out.println("Marketing Note");
    	System.out.println("+++++++++++++++\n");
    	System.out.println(manager.readMarketingNote(ID));
    }

    public void changeMarketingNote() throws NegativeInputException, NullDateException
    {
	int date = 0;
	String note;
	Scanner in = new Scanner(System.in);

	System.out.println("\n+++++++++++++++");
	System.out.println("Change Marketing Note");
	System.out.println("+++++++++++++++\n");
	System.out.println("Current marketing note:");
	System.out.println(manager.readMarketingNote(ID));
	System.out.println("\nWrite new marketing note:");
	note = in.nextLine();
	while (true)
	{
	    System.out.println("Date: ");
	    try
	    {
		date = Integer.parseInt(in.nextLine());
		if (date >= 0)
		{
		    break;
		}
		System.out.println("Can't enter negative date.");
	    }
	    catch(NumberFormatException e)
	    {
		System.out.println("Please enter an integer.");
	    }
	}
	manager.editMarketingNote(ID, note, Calendar.getInstance());
    }

    public void readRestockerNote()
    {
    	System.out.println("\n+++++++++++++++");
    	System.out.println("Restocker Note");
    	System.out.println("+++++++++++++++\n");
    	System.out.println(manager.readRestockerNote(ID));
    }

    private void listStock() throws OutOfBoundsStockException
    {
	HashMap<XYPair, Integer> tmpStock;
	Integer itemCount;

	tmpStock = thisVend.getTotalStock();

	Iterator<Entry<XYPair, Integer>> iter =
		tmpStock.entrySet().iterator();
	itemCount = 0;
	System.out.format("%4s | %-20s | %5s | %9s\n",
		"Nr", "Item", "Stock", "Price");
	while(iter.hasNext())
	{
	    itemCount++;
	    Entry<XYPair, Integer> entry = iter.next();
	    String name = thisVend.getAtXYZ(entry.getKey().x,
				entry.getKey().y, 0).getName();
	    
	    double price = thisVend.getAtXYZ(entry.getKey().x,
				entry.getKey().y, 0).getPrice();
	    // This will look like for example:
	    //   Nr | Item                 | Stock |     Price
	    //    1 | Chips                |     4 | $   20.33
	    System.out.format("%4d | %-20s | %5d | $%8.2f\n",
		    itemCount, name, entry.getValue(), price);
	}
    }

    @Override
    public void update(Observable subject, Object arg)
    {
	if (arg instanceof LocalVend)
	{
	    thisVend = (LocalVend)subject;
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
