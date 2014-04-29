/*
 * File: MarketingNote.java
 * MarketingNote contains written information that tells a restocker
 * how to restock a given vending machine.
 * 
 * Version 1.0
 * 
 * Authors:
 *   Raphael Kahler (rak9698@rit.edu)
 */

package edu.rit.se.coolTeamB.core;

import java.util.Calendar;

import edu.rit.se.coolTeamB.exceptions.*;

/******************************************************************************
 * The <CODE>MarketingNote</CODE> Java class stores written instructions for
 * restockers that hold information on how to restock a given vending machine.
 * 
 * @version
 *   1.00 22 Mar 2013
 * @author
 *   Raphael Kahler (rak9698@rit.edu)
 ******************************************************************************/
public class MarketingNote {
    private String notes;
    private Calendar issueDate; 
    private int vendingMachineID;	// the machine corresponding to the note

    /**
     * <CODE>MarketingNote</CODE> holds notes on restocking, along with
     * date of issue and ID of corresponding vending machine.
     *
     * <dt><b>Precondition:</b><dd>
     *   ID is a legal value
     * @param ID
     *   the ID of the corresponding vending machine
     * <dt><b>Postcondition:</b><dd>
     *   the MarketingNote has been created
     *  @throws NegativeInputException
     */
    public MarketingNote(int ID) throws NegativeInputException
    {
	if (ID < 0)
	{
	    throw new NegativeInputException();
	}
	notes = "Initial notes.";
	issueDate = null;
	vendingMachineID = ID;
    }


    /**
     * Returns the ID of the corresponding vending machine.
     * 
     * @return
     *   ID of corresponding vending machine
     */
    public int getID()
    {
	return vendingMachineID;
    }


    /**
     * Returns the issue date of the note.
     * @return
     *   issue date of the note
     */
    public Calendar getDate()
    {
	return issueDate;
    }

    /**
     * Returns the written text in the note.
     * @return
     *   the written text in the note
     */
    public String getNoteText()
    {
    	return notes;
    }
    
    /**
     * Replaces the old note and date with new ones.
     * 
     * <dt><b>Precondition:</b><dd>
     *   the date is a legal date
     * @param newNote
     *   the new restocking information
     * @param date
     *   the date the note was edited
     * <dt><b>Postcondition:</b><dd>
     *   the notes have been changed. 
     * @throws NullDateException 
     */
    public void edit(String newNote, Calendar date) throws NullDateException
    {
	if (date == null)
	{
	    throw new NullDateException();
	}
	notes = newNote;
	issueDate = date;
    }

    /**
     * Returns the restocking information notes. The returned note
     * begins with a line displaying the vending machine ID. Next
     * follows a line with the issue date. After a blank line, the
     * actual restocking notes are displayed. 
     * 
     * @return
     *   formatted restocking information notes
     */
    public String read()
    {
    	return "Vending ID : " + (vendingMachineID+1) + "\n" +
    				"Date : " + issueDate + "\n\nNotes : "+ notes;
     }

}
