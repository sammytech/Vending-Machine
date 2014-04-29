/*
 * File: MarketingManager.java Manager which provides access for marketing users
 * to interact with the Smart Vending Machine (SVM) database.
 * 
 * Version 1.0
 * 
 * Authors: Michael Surdouski (mxs1649@rit.edu)
 */

package edu.rit.se.coolTeamB.marketing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.List;
import java.util.Observable;

import edu.rit.se.coolTeamB.core.LocalVend;
import edu.rit.se.coolTeamB.exceptions.*;
import edu.rit.se.coolTeamB.restocker.RestockerGUI;

/******************************************************************************
 * The <CODE>MarketingManager</CODE> Java class provides utility functions which
 * are performed using the marketing user interface - </CODE>MarketingUI<CODE>.
 * 
 * @version 1.00 21 Mar 2013
 * @author Michael Surdouski (mxs1649@rit.edu)
 ******************************************************************************/
public class MarketingManager extends Observable
{
	List<LocalVend> localVends;

	/**
	 * <CODE>MarketingManager</CODE> handles all marketing access level changing
	 * of information in the SVM system.
	 * 
	 * @param _localVendingMachine
	 *            a copy of the vending machine to perform actions on
	 */
	public MarketingManager(List<LocalVend> localVends)
	{
		this.localVends = localVends;
	}

	/**
	 * Reads, or gets, the marketing note off localVendingMachine. Functionality
	 * of this method may change depending on implementation.
	 * 
	 * @param - none
	 * @return the marketing note on localVendingMachine
	 */
	// public MarketingNote getMarketingNote()
	// {
	// return localVendingMachine.getMarketingNote();
	// }

	/**
	 * Edits the marketing note on localVendingMachine.
	 * 
	 * @param notes
	 *            the notes to add to vending machine
	 * @throws NegativeInputException
	 * @throws NullDateException 
	 */
	public void editMarketingNote(int ID, String notes, Calendar date)
			throws NegativeInputException, NullDateException
	{
		localVends.get(ID).getMarketingNote().edit(notes, date);
		localVends.get(ID).writeStockFile();
	}

	/**
	 * Reads, or gets, the restocker note off localVendingMachine. Functionality
	 * of this method may change depending on implementation.
	 * 
	 * @param - none
	 * @return the restocker note on localVendingMachine
	 */
	// public RestockerNote getRestockerNote()
	// {
	// return localVendingMachine.getRestockerNote();
	// }

	/**
	 * Read the String interpretation of the MarketingNote contained in
	 * localVendingMachine.
	 * 
	 * @return the string representation of the MarketingNote
	 */
	public String readMarketingNote(int ID)
	{
		return localVends.get(ID).getMarketingNote().read();
	}

	/**
	 * Read the String interpretation of the RestockerNote contained in
	 * localVendingMachine.
	 * 
	 * @return the string representation of the RestockerNote
	 */
	public String readRestockerNote(int ID)
	{
		return localVends.get(ID).getRestockerNote().read();
	}

	/**
	 * Gets the date that the restocker note was edited.
	 * @param ID - The id of the machine
	 * @return A calendar object when the note was edited.
	 */
    public Calendar getRestockerDate(int ID){
    	return localVends.get(ID).getRestockerNote().getDate();
    }
    
    /**
     * Gets the date that the marketing note was edited.
     * @param ID - The id of the machine 
     * @return A calendar object when the note was edited.
     */
    public Calendar getMarketingDate(int ID){
    	return localVends.get(ID).getMarketingNote().getDate();
    }
    
    /**
     * Get the number of vending machines currently running.
     * @return The number of vending machines available for use.
     */
	public int getNumberOfVendingMachines()
	{
		return localVends.size();
	}
	
	/**
	 * Get the vending machine object.
	 * @param ID - The id of the machine 
	 * @return The vending machine object.
	 */
	public LocalVend getVend(int ID)
	{
		return localVends.get(ID);
	}
	// public void getGlobalInfo()
	// {
	// TODO R2 part of project I believe, we will not be implementing
	// the GlobalVend yet... possibly. Ask me about this in case
	// something changed or to remind me to figure out if we need
	// it or not - Mike
	// }

	/**
	 * @param marketingUI
	 * @param iD
	 */
	public void register(MarketingUI UI, int ID)
	{
		// TODO Auto-generated method stub
		localVends.get(ID).addObserver(UI);
		localVends.get(ID).startNotify();
	}

	// public void getGlobalStatistics()
	// {
	// TODO R2 part of project I believe, we will not be implementing
	// the GlobalVend yet... possibly. Ask me about this in case
	// something changed or to remind me to figure out if we need
	// it or not - Mike
	// }

	// public void register(MarketingGUI UI, int ID)
	// {
	// localVends.get(ID).addObserver(UI);
	// localVends.get(ID).startNotify();
	// }
	//
	// public void deregister(MarketingGUI UI, int ID)
	// {
	// localVends.get(ID).deleteObserver(UI);
	// }
}
