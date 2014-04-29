/*
 * File: RestockerNote.java
 * RestockerNote contains notes from the restocker to marketing
 * about things he might have noticed during restocking 
 * 
 * Version 1.0
 * 
 * Authors:
 *   Raphael Kahler (rak9698@rit.edu)
 */

package edu.rit.se.coolTeamB.core;

import java.util.Calendar;

import edu.rit.se.coolTeamB.exceptions.NegativeInputException;
import edu.rit.se.coolTeamB.exceptions.NullDateException;

/******************************************************************************
 * The <CODE>RestockerNote</CODE> Java class stores written notifications
 * to marketing that restockers can leave while restocking a given
 * vending machine.
 * 
 * @version
 *   1.00 22 Mar 2013
 * @author
 *   Raphael Kahler (rak9698@rit.edu)
 ******************************************************************************/
public class RestockerNote {
    private String notes;
    private Calendar issueDate; 
    private int vendingMachineID;	// the machine corresponding to the note

    /**
     * <CODE>RestockerNote</CODE> holds notes to marketing about restocking,
     * along with date of issue and ID of corresponding vending machine.
     *
     * <dt><b>Precondition:</b><dd>
     *   ID is a legal value
     * @param ID
     *   the ID of the corresponding vending machine
     * <dt><b>Postcondition:</b><dd>
     *   the RestockerNote has been created
     * @throws NegativeInputException
     */
    public RestockerNote(int ID) throws NegativeInputException
    {
	if (ID < 0)
	{
	    throw new NegativeInputException();
	}
	notes = "<< EMPTY >>";
	issueDate = null;
	vendingMachineID = ID;
    }

    /**
     * Returns the ID of the correspoding vending machine.
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
     * Returns notes to marketing about restocking. The returned note
     * begins with a line displaying the vending machine ID. Next
     * follows a line with the issue date. After a blank line, the
     * actual restocking notes are displayed. 
     * 
     * @return
     *   formatted note to marketing about restocking
     */
    public String read()
    {
		return "Vending ID : " + (vendingMachineID+1) + "\n" +
					"Date : " + issueDate + "\n\nNotes : "+ notes;
    }


}
