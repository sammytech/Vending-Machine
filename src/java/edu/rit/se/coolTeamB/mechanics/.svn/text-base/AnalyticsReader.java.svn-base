/*
 * File: AnalyticsReader.java
 * reads report files from the database of the Smart Vending Machine system
 * 
 * Version 1.0
 * 
 * Authors:
 *   Michael Surdouski (mxs1649@rit.edu)
 *   Raphael Kahler (rak9698@rit.edu)
 */

package edu.rit.se.coolTeamB.mechanics;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

/******************************************************************************
 * The <CODE>AnalyticsReader</CODE> Java class contains the functions to
 * read vending machine sales reports from the database of the Smart Vending 
 * Machine (SVM) system.
 * 
 * @version
 *   1.00 13 Apr 2013
 * @author
 *   Michael Surdouski (mxs1649@rit.edu)
 *   Raphael Kahler (rak9698@rit.edu)
 ******************************************************************************/
public class AnalyticsReader
{
	/**
	 * Reads in the report file for a specific vending machine and
	 * returns an ArrayList containing all item report information.
	 * 
	 * @param ID
	 *   ID of the vending machine
	 * @return
	 *   ArrayList containing ArrayLists of the following format:
	 *   itemName, itemPrice, reason, actionDate, expDate, row, col
	 */
    static public ArrayList<ArrayList<Object>> getData(int ID)
    {
	/*
	 * use the following format within the return arraylist:
	 * itemName, itemPrice, reason, actionDate, expDate, row, col
	 */
    	String filename;
    	String line;
    	FileReader readStream;
    	BufferedReader reader;
    	ArrayList<ArrayList<Object>> report = 
    				new ArrayList<ArrayList<Object>>();

    	filename = String.format("./database/reports/%06d_Report.txt", ID);
    	try
    	{
    		
    	    readStream = new FileReader(filename);
    	    reader = new BufferedReader(readStream);

    	    while((line = reader.readLine()) != null)
    	    {
	    		ArrayList<Object> tmp = readInItem(line);
	    		if (tmp != null)
	    		{
	    		    report.add(tmp);
	    		}				
    	    }
       	    reader.close();
    	    readStream.close();
    	    return report;
    	}
    	catch (FileNotFoundException e)
    	{
    	    System.out.println("Error in opening file.");
    	    return null;
    	}
    	catch (IOException e)
    	{
    	    System.out.println("Error in reading from file.");
    	    return null;
    	}
    }
    
    /**
     * Parses a string representation of an item into an ArrayList that
     * contains all necessary item information.
     * 
     * @param itemInfo
     *   string representation of the item
     * @return
     *   ArrayList containing all necessary item information
     */
    static private ArrayList<Object> readInItem(String itemInfo)
    {
    	String delimiter = DatabaseWriter.getDelimiter();
    	String dateDelimiter = DatabaseWriter.getDateDelimiter();
    	String[] split = itemInfo.split(delimiter);
    	String[] expDateSplit;
    	String[] actionDateSplit;
    	ArrayList<Object> itemInfoObject = new ArrayList<Object>(7);
    	String itemName;
    	double itemPrice;
    	ReasonRemoved reason;
    	Calendar expDate = Calendar.getInstance();
    	Calendar actionDate = Calendar.getInstance();
    	int row;
    	int col;

    	// check if line is empty
    	if (split.length == 0)
    	{
    		System.out.println("Invalid empty report information.");
    		return null;
    	}

    	// check if all the information is there
    	if (split.length != 7)
    	{
    	    System.out.println("Error. Invalid report information.");
    	    return null;
    	}

    	// check if expiration date is valid
    	expDateSplit = split[4].split(dateDelimiter);
    	if (expDateSplit.length != 3)
    	{
    	    System.out.println("Error. Invalid expiration date information.");
    	    return null;
    	}
    	expDate.clear();
    	expDate.set(Integer.parseInt(expDateSplit[0]),
    		Integer.parseInt(expDateSplit[1]),
    		Integer.parseInt(expDateSplit[2]));

    	// check if action date is valid
    	actionDateSplit = split[3].split(dateDelimiter);
    	if (actionDateSplit.length != 3)
    	{
    	    System.out.println("Error. Invalid action date information.");
    	    return null;
    	}
    	actionDate.clear();
    	actionDate.set(Integer.parseInt(actionDateSplit[0]),
    		Integer.parseInt(actionDateSplit[1]),
    		Integer.parseInt(actionDateSplit[2]));

    	// set item name
    	itemName = split[0];
    	if (itemName == null)
    	{
    	    System.out.println("Error. Corrupt item name.");
    	    return null;
    	}
    	// set item price
    	try
    	{
    	    itemPrice = Double.parseDouble(split[1]);
    	}
    	catch (NullPointerException | NumberFormatException e)
    	{
    	    System.out.println("Error. Corrupt item price.");
    	    return null;			
    	}

    	// set reason
    	reason = ReasonRemoved.valueOf(split[2]);
    	if ((reason != ReasonRemoved.BY_REQUEST) &&
    			(reason != ReasonRemoved.SOLD) &&
    			(reason != ReasonRemoved.EXPIRED))
    	{
    		System.out.println("Error. Corrupt item removal reason.");
    	}
    	
    	// set item row
    	try
    	{
    	    row = Integer.parseInt(split[5]);
    	}
    	catch (NumberFormatException e)
    	{
    	    System.out.println("Error. Corrupt item row.");
    	    return null;
    	}

    	// set item column
    	try
    	{
    	    col = Integer.parseInt(split[6]);
    	}
    	catch (NumberFormatException e)
    	{
    	    System.out.println("Error. Corrupt item column.");
    	    return null;
    	}
    	
    	itemInfoObject.add(itemName);
    	itemInfoObject.add(itemPrice);
    	itemInfoObject.add(reason);
    	itemInfoObject.add(actionDate);
    	itemInfoObject.add(expDate);
    	itemInfoObject.add(row);
    	itemInfoObject.add(col);
    
    	return itemInfoObject;
    	
    	
    }
}
