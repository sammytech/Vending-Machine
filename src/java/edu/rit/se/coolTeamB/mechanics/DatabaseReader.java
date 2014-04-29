/*
 * File: DatabaseReader.java
 * reads stock files from the database of the Smart Vending Machine system
 * 
 * Version 1.0
 * 
 * Authors:
 *   Raphael Kahler (rak9698@rit.edu)
 */

package edu.rit.se.coolTeamB.mechanics;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import edu.rit.se.coolTeamB.exceptions.NegativeInputException;
import edu.rit.se.coolTeamB.exceptions.OutOfBoundsStockException;
import edu.rit.se.coolTeamB.exceptions.StockException;

/******************************************************************************
 * The <CODE>DatabaseReader</CODE> Java class contains the functions to
 * read vending machine information from the database of the Smart Vending 
 * Machine (SVM) system.
 * 
 * @version
 *   1.00 12 Apr 2013
 * @author
 *   Raphael Kahler (rak9698@rit.edu)
 ******************************************************************************/
public class DatabaseReader
{
    static public String name;
    static public double price;
    static public Calendar date;
    static public int row;
    static public int col;
    
    /**
     * Reads in the stock file for a vending machine and returns the
     * information necessary to fill the machine as an ArrayList.
     * The first element contains information on how many items there
     * are in total, the next elements are the item informations, and
     * the last two elements are the restocker and marketing note
     * informations
     * 
     * @param ID
     *   ID of the vending machine
     * @param row
     *   number of rows in the vending machine
     * @param col
     *   number of columns in the vending machine
     * @param depth
	 *   number of items that can be stored at each row-column position
     * @return
     *   ArrayList containing all necessary information to fill the
     *   vending machine
     */
    static public ArrayList<ArrayList<Object>> fillMachine(int ID, int row, 
	    						   int col, int depth)
    {
	String filename;
	String line;
	FileReader readStream;
	BufferedReader reader;
	ArrayList<ArrayList<Object>> itemsInfo = 
				new ArrayList<ArrayList<Object>>();
	int numItems;
	Calendar rDate;
	Calendar mDate;

	filename = String.format("./database/stock/%06d_Stock.txt", ID);
	try
	{
	    readStream = new FileReader(filename);
	    reader = new BufferedReader(readStream);

	    // check heading of stock file
	    if((line = reader.readLine()) != null)
	    {
		checkHeading(line, ID, row, col, depth);
	    }
	    else
	    {
		System.out.println("Error. Stock file is empty.");
	    }

	    // read in money
	    if ((line = reader.readLine()) != null)
	    {
		//(TODO) Internal money stock should get set by this.
	    }
	    else
	    {
		System.out.println("Error. " +
			"Stock file contains only one line.");
	    }

	    // read in items
	    //while((line = reader.readLine()) != null)
	    for (int i = 0; i < row*col*depth; i++)
	    {
			if ((line = reader.readLine()) == null)
			{
				System.out.println("Error. " +
					"Premature end of stock list.");
				break;
			}
			ArrayList<Object> tmp = readInItem(line);
			if (tmp != null)
			{
			    itemsInfo.add(readInItem(line));
			}				
	    }
	    
	    // add number of items to beginning of list
	    numItems = itemsInfo.size();
	    ArrayList<Object> sizeInfo = new ArrayList<Object>(1);
		sizeInfo.add(numItems);
		itemsInfo.add(0, sizeInfo);

	    // read in notes
	    if((line = reader.readLine()) != null)
	    {
	    	// restocker note
	    	if(line.equals(DatabaseWriter.getNullDateMarker()))
	    	{
	    		line = reader.readLine();
	    		// leave note empty, so add null object
	    		ArrayList<Object> rNull = null;
	    		itemsInfo.add(rNull);
	    	}
	    	else
	    	{
	    		rDate = makeDate(line);
	    		line = reader.readLine();
	    		ArrayList<Object> rTmp = new ArrayList<Object>(2);
	    		rTmp.add(rDate);
	    		rTmp.add(line);
	    		itemsInfo.add(rTmp);	
	    	}
	    	// marketing note
	    	line = reader.readLine();
	    	if(line.equals(DatabaseWriter.getNullDateMarker()))
	    	{
	    		line = reader.readLine();
	    		// leave note empty, so add null object
	    		ArrayList<Object> mNull = null;
	    		itemsInfo.add(mNull);
	    	}
	    	else
	    	{
	    		mDate = makeDate(line);
	    		line = reader.readLine();
	    		ArrayList<Object> mTmp = new ArrayList<Object>(2);
	    		mTmp.add(mDate);
	    		mTmp.add(line);
	    		itemsInfo.add(mTmp);	
	    	}
	    }
	    else
	    {
		System.out.println("Error. Notes are not in stock file.");
		// add two empty note informations
		ArrayList<Object> noNotes = null;
		itemsInfo.add(noNotes);
		itemsInfo.add(noNotes);
	    }
	    
	    
	    reader.close();
	    readStream.close();
	    return itemsInfo;
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
     * Check the machine information in the heading of the stock file
     * against the actual machine parameters.
     * 
     * @param heading
     *   String containing the heading information of the stock file
     * @param ID
     *   ID of the vending machine
     * @param row
     *   number of rows in the vending machine
     * @param col
     *   number of columns in the vending machine
     * @param depth
     *   depth of the vending machine
     */
    static private void checkHeading(String heading, int ID, int row, int col,
	    int depth)
    {
		String delimiter = DatabaseWriter.getDelimiter();
		String[] split = heading.split(delimiter);
	
		if (split.length != 4)
		{
		    System.out.println("Invalid header in stock file.");    		
		}
		if (Integer.parseInt(split[0]) != ID)
		{
		    System.out.println("Wrong ID in stock file.");
		}
		if (Integer.parseInt(split[1]) != row)
		{
		    System.out.println("Wrong number of rows in stock file.");
		}
		if (Integer.parseInt(split[2]) != col)
		{
		    System.out.println("Wrong number of columns in stock file.");
		}
		if (Integer.parseInt(split[3]) != depth)
		{
		    System.out.println("Wrong machine depth in stock file.");
		}
    }

    /**
     * Takes a string representation of an item and parses the information
     * into an ArrayList, so that an item can be created using the
     * ArrayList information.
     *  
     * @param itemInfo
     *   string representation of an item
     * @return
     *   ArrayList containing all necessary item information
     */
    static private ArrayList<Object> readInItem(String itemInfo)
    {
		String delimiter = DatabaseWriter.getDelimiter();
		String dateDelimiter = DatabaseWriter.getDateDelimiter();
		String emptyPositionMarker = DatabaseWriter.getEmptyPositionMarker();
		String[] split = itemInfo.split(delimiter);
		String[] dateSplit;
		ArrayList<Object> itemInfoObject = new ArrayList<Object>(5);
		String itemName;
		double itemPrice;
		Calendar date = Calendar.getInstance();
		int row;
		int col;
		//depth not needed to add item
	
		// check if position is empty
		if (split.length == 1)
		{
		    if (split[0].equals(emptyPositionMarker))
		    {
			// no item at that position, so nothing to do
			return null;
		    }
		    else
		    {
			System.out.println("Invalid empty position information.");
		    }
		}
	
		// check if all the information is there
		if (split.length != 6)
		{
		    System.out.println("Error. Invalid item information.");
		    return null;
		}
	
		// check if date is valid
		dateSplit = split[2].split(dateDelimiter);
		if (dateSplit.length != 3)
		{
		    System.out.println("Error. Invalid expiration date information.");
		    return null;
		}
		date.clear();
		date.set(Integer.parseInt(dateSplit[0]),
			Integer.parseInt(dateSplit[1]),
			Integer.parseInt(dateSplit[2]));
	
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
	
		// set item row
		try
		{
		    row = Integer.parseInt(split[3]);
		}
		catch (NumberFormatException e)
		{
		    System.out.println("Error. Corrupt item row.");
		    return null;
		}
	
		// set item column
		try
		{
		    col = Integer.parseInt(split[4]);
		}
		catch (NumberFormatException e)
		{
		    System.out.println("Error. Corrupt item column.");
		    return null;
		}
		
		itemInfoObject.add(itemName);
		itemInfoObject.add(itemPrice);
		itemInfoObject.add(date);
		itemInfoObject.add(row);
		itemInfoObject.add(col);
		return itemInfoObject;
    }
    
    /**
     * Turn a string representation of a date into a Calendar instance.
     * 
     * @param dateInfo
     *   string representation of a date
     * @return
     *   Calendar with the specified date
     */
    static private Calendar makeDate(String dateInfo)
    {
    	String dateDelimiter = DatabaseWriter.getDateDelimiter();
    	String[] dateSplit = dateInfo.split(dateDelimiter);
    	Calendar date = Calendar.getInstance();

    	if (dateSplit.length != 3)
    	{
    	    System.out.println("Error. Invalid date information.");
    	    return null;
    	}
    	date.clear();
    	date.set(Integer.parseInt(dateSplit[0]),
    		Integer.parseInt(dateSplit[1]),
    		Integer.parseInt(dateSplit[2]));

    	return date;
    }
}
