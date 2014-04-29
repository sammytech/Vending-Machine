/*
 * File: DatabaseWriter.java
 * writes files to the database for the Smart Vending Machine system
 * 
 * Version 1.0
 * 
 * Authors:
 *   Raphael Kahler (rak9698@rit.edu)
 */

package edu.rit.se.coolTeamB.mechanics;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import edu.rit.se.coolTeamB.core.Item;
import edu.rit.se.coolTeamB.core.RestockerNote;
import edu.rit.se.coolTeamB.core.MarketingNote;

/******************************************************************************
 * The <CODE>DatabaseWriter</CODE> Java class contains the functions to
 * write vending machine information to the database for the Smart Vending 
 * Machine (SVM) system.
 * 
 * @version
 *   1.00 12 Apr 2013
 * @author
 *   Raphael Kahler (rak9698@rit.edu)
 ******************************************************************************/
public class DatabaseWriter
{
	final static private String delimiter = "	";//"\u222B"; // integral symbol
	final static private String dateDelimiter = "/";
	final static private String emptyPositionMarker = "x";
	final static private String nullDateMarker = "nullDate";
	
	/**
	 * Create the directories for the database files and create a file
	 * holding information about the database. The information includes
	 * the number of vending machines in the database, as well as the
	 * dimensions of the vending machines.
	 * 
	 * @param numMachines
	 *   number of vending machines in the database
	 * @param rows
	 *   number of rows in the vending machines
	 * @param cols
	 *   number of columns in the vending machines
	 * @param depth
	 *   depth of the vending machines
	 */
	static public void createDatabase(int numMachines, int rows, int cols,
			int depth)
	{
		File databaseFolder = new File("./database");
		File stockFolder = new File("./database/stock");
		File reportFolder = new File("./database/reports");
		File databaseFile;
		Integer[] databaseInfo;
		int numOfExistingMachines = 0;
		String filename;

		// delete old database foler
		deleteFolder(databaseFolder);
		
		// stall, to make sure folder is deleted before making new folders
		try
		{
		    Thread.sleep(250); // Sleep for a while
		}
		catch (InterruptedException e)
		{
		    Thread.currentThread().interrupt();
		}
		
		// create database folder		
		if (!databaseFolder.exists())
		{
			if (databaseFolder.mkdir())
			{
				System.out.println("Database folder created");
			}
			else
			{
				System.out.println("Failed to create database folder");
			}
		}
		
		// create sub-folder for stock files of vending machines
		if (!stockFolder.exists())
		{
			if (stockFolder.mkdir())
			{
				System.out.println("Stock folder created.");
			}
			else
			{
				System.out.println("Failed to create stock folder.");
			}
		}

		// create sub-folder for report files of vending machines
		if (!reportFolder.exists())
		{
			if (reportFolder.mkdir())
			{
				System.out.println("Reports folder created.");
			}
			else
			{
				System.out.println("Failed to create reports folder.");
			}
		}
		// make new database files
		filename = "./database/database_information.txt";
	    databaseFile = new File(filename);
		writeDatabaseFile(numMachines, rows, cols, depth);
		for (int i = 0; i < numMachines; i++)
		{
			writeEmptyStockFile(i, rows, cols, depth);
			writeEmptyReportFile(i);
		}
	}


	/**
	 * Writes the database information file containing the number of
	 * vending machines, as well as the dimension of the vending machines.

	 * @param numMachines
	 *   number of vending machines in the database
	 * @param rows
	 *   number of rows in the vending machines
	 * @param cols
	 *   number of columns in the vending machines
	 * @param depth
	 *   depth of the vending machines
	 */
	static private void writeDatabaseFile(int numMachines, int rows, 
			int cols, int depth)
	{
		String filename;
		String info;
		FileWriter writeStream;
		BufferedWriter wr;
		
		filename = "./database/database_information.txt";
		try
		{
			writeStream = new FileWriter(filename, false);
			wr = new BufferedWriter(writeStream);
			info = numMachines + delimiter + rows + delimiter + cols +
					delimiter + depth;
			wr.write(info);
			wr.close();
			writeStream.close();
		}
		catch (FileNotFoundException e)
		{
			System.out.println("Error in opening file.");
		}
		catch (IOException e)
		{
			System.out.println("Error in writing to file.");
		}
	}

    /**
     * Reads in the database information file and returns the information
     * contained therein.
     * @return
     *   Integer[] that contains the information in the database file
     */
    private static Integer[] getDatabaseInfo()
    {
    	Integer[] databaseInfo = null;
    	String[] input = null;
    	String delimiter = DatabaseWriter.getDelimiter();
    	String filename;
		String line;
		FileReader readStream;
		BufferedReader reader;
		
		filename = "./database/database_information.txt";
		try
		{
			readStream = new FileReader(filename);
			reader = new BufferedReader(readStream);
			
			if((line = reader.readLine()) != null)
			{
				input = line.split(delimiter);
			}
			else
			{
				System.out.println("Error." +
						" Database information file is empty.");
			}
			reader.close();
			readStream.close();
		}
		catch (FileNotFoundException e)
		{
			System.out.println("Error in opening database information file.");
		}
		catch (IOException e)
		{
			System.out.println("Error in reading from database" +
					" information file.");
		}
		
		if (input == null)
		{
			System.out.println("Error. Database information file is empty.");
		}
		else if (input.length != 4)
		{
			System.out.println("Error. Database information file is corrupt.");
		}
		else
		{
			databaseInfo = new Integer[4];
			try
			{
				for (int i = 0; i < 4; i ++)
				{
					databaseInfo[i] = Integer.parseInt(input[i]);
					if (databaseInfo[i] <= 0)
					{
						System.out.println("Error." +
								" Database information file is corrupt.");
						// so main does not try to use database, set to null
						databaseInfo = null;
						break;
					}
				}
			}
			catch (NumberFormatException e)
			{
				System.out.println("Error." +
						" Database information file is corrupt.");
				// so main does not try to use database, set to null
				databaseInfo = null;
			}
		}
    	return databaseInfo;
    }

	
	/**
	 * Write an empty stock file for the vending machine. To be used when
	 * a new database is created.
	 * 
	 * @param ID
	 *   ID of the vending machine
	 * @param row
	 *   number of rows in the vending machine
	 * @param col
	 *   number of columns in the vending machine
	 * @param depth
	 *   number of items that can be stored at each row-column position
	 */
	static private void writeEmptyStockFile(int ID, int row,
			int col, int depth)
	{
		String filename;
		FileWriter writeStream;
		BufferedWriter wr;
		
		
		filename = String.format("./database/stock/%06d_Stock.txt", ID);
		try
		{
			writeStream = new FileWriter(filename, false);			
			wr = new BufferedWriter(writeStream);
			writeHeading(wr, ID, row, col, depth, 0.0);
			writeEmptyItems(wr, row, col, depth);
			wr.newLine();
			wr.write(nullDateMarker);
			wr.newLine();
			wr.write("No notes.");
			wr.newLine();
			wr.write(nullDateMarker);
			wr.newLine();
			wr.write("No notes.");
			wr.close();
			writeStream.close();
		}
		catch (FileNotFoundException e)
		{
			System.out.println("Error in opening file.");
		}
		catch (IOException e)
		{
			System.out.println("Error in writing to file.");
		}		
	}
	
	/**
	 * Write the current stock of a vending machine as well as its 
	 * restocker and marketing notes to a file corresponding to the ID
	 * of the vending machine.
	 * 
	 * @param ID
	 *   ID of the vending machine
	 * @param row
	 *   number of rows in the vending machine
	 * @param col
	 *   number of columns in the vending machine
	 * @param depth
	 *   number of items that can be stored at each row-column position
	 * @param money
	 *   money stored in the vending machine
	 * @param items
	 *   row-column-depth array of items in the vending machine
	 * @param restockerNote
	 *   the current restocker note for the vending machine
	 * @param marketingNote
	 *   the current marketing note for the vending machine 
	 */
	static public void writeStockFile(int ID, int row, int col, int depth,
			double money, ArrayList<ArrayList<ArrayList<Item>>> items,
			RestockerNote restockerNote, MarketingNote marketingNote)
	{
		String filename;
		FileWriter writeStream;
		BufferedWriter wr;
		
		filename = String.format("./database/stock/%06d_Stock.txt", ID);
		try
		{
			writeStream = new FileWriter(filename, false);			
			wr = new BufferedWriter(writeStream);
			writeHeading(wr, ID, row, col, depth, money);
			writeItems(wr, row, col, depth, items);
			writeNotes(wr, restockerNote, marketingNote);
			wr.close();
			writeStream.close();
		}
		catch (FileNotFoundException e)
		{
			System.out.println("Error in opening file.");
		}
		catch (IOException e)
		{
			System.out.println("Error in writing to file.");
		}
	}

	/**
	 * Write the heading for the stock file for a vending machine.
	 * The heading contains the vending machine ID, the number of 
	 * rows, columns, and the depth of the machine, as well as the
	 * money stored in the machine.
	 * 
	 * @param wr
	 *   BufferedWriter for the file
	 * @param ID
	 *   ID of the vending machine
	 * @param row
	 *   number of rows in the vending machine
	 * @param col
	 *   number of columns in the vending machine
	 * @param depth
	 *   number of items that can be stored at each row-column position
	 * @param money
	 *   money stored in the vending machine
	 * @throws IOException
	 */
	static private void writeHeading(BufferedWriter wr, int ID, int row,
			int col, int depth, double money) throws IOException
	{
		String header;
		header = ID + delimiter + row + delimiter + col + delimiter + depth;
		wr.write(header);
		wr.newLine();
		wr.write(""+  money);
	}

	/**
	 * Writes the items stored in the vending machine into the stock file.
	 * Items are written line per line, going through the vending machine
	 * by row, then column, then depth. An empty position will be marked with
	 * a special character.
	 * 
	 * @param wr
	 *   BufferedWriter for the file
	 * @param row
	 *   number of rows in the vending machine
	 * @param col
	 *   number of columns in the vending machine
	 * @param depth
	 *   number of items that can be stored at each row-column position
	 * @param items
	 *   row-column-depth array of items in the vending machine 
	 * @throws IOException
	 */
	static private void writeItems(BufferedWriter wr, int row, int col,
			int depth, ArrayList<ArrayList<ArrayList<Item>>> items)
			throws IOException
	{
		Item tmpItem;
		
		for (int i = 0; i < row; i++)
		{
			for (int j = 0; j < col; j++)
			{
				for (int k = 0; k < depth; k++)
				{
					wr.newLine();
					if (k < items.get(i).get(j).size())
					{
						tmpItem = items.get(i).get(j).get(k);
						writeSingleItem(wr, tmpItem, i, j, k);
					}
					else
					{
						wr.write(emptyPositionMarker);
					}
				}
			}
		}
	}

	/**
	 * Writes the empty item information for every item position
	 * in the vending machine.
	 * 
	 * @param wr
	 *   BufferedWriter for the file
	 * @param row
	 *   number of rows in the vending machine
	 * @param col
	 *   number of columns in the vending machine
	 * @param depth
	 *   number of items that can be stored at each row-column position
	 * @throws IOException
	 */
	static private void writeEmptyItems(BufferedWriter wr, int row, int col,
			int depth)
			throws IOException
	{
		for (int i = 0; i < row; i++)
		{
			for (int j = 0; j < col; j++)
			{
				for (int k = 0; k < depth; k++)
				{
					wr.newLine();
					wr.write(emptyPositionMarker);
				}
			}
		}
	}

	
	
	/**
	 * Writes a single item to the stock file of a vending machine.
	 * The item will be written in the following format:
	 * [itemName]delimiter[itemPrice]delimiter[expirationDate]delimiter...
	 * [rowOfItem]delimiter[columnOfItem]delimiter[depthOfItem]
	 * @param wr
	 *   BufferedWriter for the file
	 * @param item
	 *   Item to be written to the file
	 * @param row
	 *   Row the item is located at
	 * @param col
	 *   Column the item is located at
	 * @param depth
	 *   Depth the item is located at
	 * @throws IOException
	 */
	static private void writeSingleItem(BufferedWriter wr, Item item, int row,
			int col, int depth) throws IOException
	{
		String itemInfo;
		String date;
		
		date = item.getDate().get(Calendar.YEAR) + dateDelimiter +
				item.getDate().get(Calendar.MONTH) + dateDelimiter +
				item.getDate().get(Calendar.DAY_OF_MONTH);
		itemInfo = item.getName() + delimiter + item.getPrice() + delimiter +
				date + delimiter + row + delimiter + col + delimiter + depth;
		wr.write(itemInfo);
	}
	
	
	/**
	 * Writes the restocker and marketing notes to the stock file of
	 * the corresponding vending machine.
	 * 
	 * @param wr
	 *   BufferedWriter for the file
	 * @param restockerNote
	 *   RestockerNote of the vending machine
	 * @param marketingNote
	 *   MarketingNote of the vending machine
	 */
	static private void writeNotes(BufferedWriter wr,
			RestockerNote restockerNote, MarketingNote marketingNote)
			throws IOException
	{
		String rDateString = nullDateMarker;
		String mDateString = nullDateMarker;

		if (restockerNote.getDate() != null)
		{
			rDateString = restockerNote.getDate().get(Calendar.YEAR) + 
			dateDelimiter + restockerNote.getDate().get(Calendar.MONTH) +
			dateDelimiter +	restockerNote.getDate().get(Calendar.DAY_OF_MONTH);
		}
		if (marketingNote.getDate() != null)
		{
			mDateString = marketingNote.getDate().get(Calendar.YEAR) + 	
			dateDelimiter + marketingNote.getDate().get(Calendar.MONTH) +
			dateDelimiter +	marketingNote.getDate().get(Calendar.DAY_OF_MONTH);
		}
		
		wr.newLine();
		wr.write(rDateString);
		wr.newLine();
		wr.write(restockerNote.getNoteText());
		wr.newLine();
		wr.write(mDateString);
		wr.newLine();
		wr.write(marketingNote.getNoteText());
	}

	
	/**
	 * Creates an empty report file for a vending machine. To be called when
	 * creating a new database.
	 * 
	 * @param ID
	 *   ID of the vending machine
	 */
	static private void writeEmptyReportFile(int ID)
	{
		File reportFile;
		String filename = 
				String.format("./database/reports/%06d_Report.txt", ID);
	    reportFile = new File(filename);
	    try
	    {
	    	if (reportFile.createNewFile())
	    	{
	    		// file created successfully
	    	}
	    	else
	    	{
	    		System.out.println("Error. Could not create report file" +
	    				" for ID " + ID);
	    	}
	    }
	    catch (Exception e)
	    {
    		System.out.println("An error occured while creating report file" +
    				" for ID " + ID);
	    }

	}
	
	/**
	 * Writes item information to the report file when an item leaves the
	 * vending machine.
	 * 
	 * @param ID
	 *   ID of the vending machine
	 * @param item
	 *   Item that left the vending machine
	 * @param row
	 *   row that the item was stored in
	 * @param col
	 *   column that the item was stored in
	 * @param actionDate
	 *   date of when the item left the vending machine
	 * @param reason
	 *   reason why the item left the vending machine (sold, expired, removed)
	 */
	static public void writeToReportFile(int ID, Item item, int row, int col,
			Calendar actionDate, ReasonRemoved reason)
	{
		String filename;
		FileWriter writeStream;
		BufferedWriter wr;
		String itemInfo;
		String expDate;
		String date;

		filename = String.format("./database/reports/%06d_Report.txt", ID);
		try
		{
			writeStream = new FileWriter(filename, true);			
			wr = new BufferedWriter(writeStream);
			expDate = item.getDate().get(Calendar.YEAR) + dateDelimiter +
					item.getDate().get(Calendar.MONTH) + dateDelimiter +
					item.getDate().get(Calendar.DAY_OF_MONTH);
			date = actionDate.get(Calendar.YEAR) + dateDelimiter +
					actionDate.get(Calendar.MONTH) + dateDelimiter +
					actionDate.get(Calendar.DAY_OF_MONTH);

			itemInfo = item.getName() + delimiter + item.getPrice() +
					delimiter + reason + delimiter + date +
					delimiter + expDate + delimiter + row + 
					delimiter + col;
			wr.write(itemInfo);
			wr.newLine();
			wr.close();
			writeStream.close();
		}
		catch (FileNotFoundException e)
		{
			System.out.println("Error in opening file.");
		}
		catch (IOException e)
		{
			System.out.println("Error in writing to file.");
		}
		
	}
	
	/**
	 * Returns the delimiter used in the database files.
	 * 
	 * @return
	 *   delimiter used in the database files
	 */
	static public String getDelimiter()
	{
		return delimiter;
	}

	/**
	 * Returns the delimiter used for dates in the database files.
	 * 
	 * @return
	 *   delimiter used in date information
	 */
	static public String getDateDelimiter()
	{
		return dateDelimiter;
	}

	/**
	 * Returns the marker used to signify empty positions in the
	 * vending machine when writing the stock file.
	 * 
	 * @return
	 *   marker for empty positions in the vending machine.
	 */
	static public String getEmptyPositionMarker()
	{
		return emptyPositionMarker;
	}
	
	/**
	 * Returns the marker used to signify null dates for notes
	 * without initialized dates when writing the stock file.
	 * 
	 * @return
	 *   marker for null dates
	 */
	static public String getNullDateMarker()
	{
		return nullDateMarker;
	}

	
	/**
	 * Delete a folder and all the files and sub folders contained in it.
	 * 
	 * @param folder
	 *   the folder that is to be deleted
	 */
	static private void deleteFolder(File folder)
	{
	    File[] files = folder.listFiles();
	    if (files != null)
	    {
		for (File f : files)
		{
		    if (f.isDirectory())
		    {
			deleteFolder(f);
		    }
		    else
		    {
			f.delete();
		    }
		}
	    }
	    folder.delete();
	}

}
