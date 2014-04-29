/*
 * File: Statistics.java
 * Statistics report generated to be used with graphs for marketing UI.
 * 
 * Version 1.0
 * 
 * Authors:
 *   Michael Surdouski (mxs1649@rit.edu)
 *   
 */

package edu.rit.se.coolTeamB.core;

import java.util.ArrayList;
import java.util.Calendar;
import edu.rit.se.coolTeamB.mechanics.*;

/******************************************************************************
 * The <CODE>ShoppingCart</CODE> The statistics engine that drive the marketing
 * GUI and user interface.
 * 
 * @version 
 * @author rak9698 (rak9698@rit.edu)
 ******************************************************************************/
public class StatisticsReport
{
    String filename;
    ArrayList<String> name;
    ArrayList<Double> price;
    ArrayList<ReasonRemoved> reason;
    ArrayList<Calendar> actionDate;
    ArrayList<Calendar> expDate;
    ArrayList<Integer> row;
    ArrayList<Integer> col;
    
    /**
     * Constructor for a single statistics report.
     * @param _filename - The filename of the statistics report.
     */
    public StatisticsReport(String _filename)
    {
	filename = _filename;
	name = new ArrayList<String>(20);
	price = new ArrayList<Double>(20);
	reason = new ArrayList<ReasonRemoved>(20);
	actionDate = new ArrayList<Calendar>(20);
	expDate = new ArrayList<Calendar>(20);
	row = new ArrayList<Integer>(20);
	col = new ArrayList<Integer>(20);
    }
    
    /**
     * The statistics report compiled with all the 
     * global statistics of all the machines.
     * @param report - An arraylist of all the reports 
     */
    public StatisticsReport(ArrayList<ArrayList<Object>> report)
    {
	filename = null;
	name = new ArrayList<String>(report.size());
	price = new ArrayList<Double>(report.size());
	reason = new ArrayList<ReasonRemoved>(report.size());
	actionDate = new ArrayList<Calendar>(report.size());
	expDate = new ArrayList<Calendar>(report.size());
	row = new ArrayList<Integer>(report.size());
	col = new ArrayList<Integer>(report.size());
	build(report);
    }
    
    public void itemAction(Item _item, ReasonRemoved _reason, XYPair _location,
	    		   Calendar _date)
    {
	name.add(_item.getName());
	price.add(_item.getPrice());
	reason.add(_reason);
	actionDate.add(_date);
	expDate.add(_item.getDate());
	row.add(_location.x);
	col.add(_location.y);
    }
    
    public void updateDatabase()
    {
	/*	implement here  	*/
    }
    
    /**
     * Clears the cache.
     */
    public void clearCache()
    {
	name.clear();
	price.clear();
	reason.clear();
	actionDate.clear();
	expDate.clear();
	row.clear();
	col.clear();
    }
    
    /**
     * Gets the names.
     * @return An arraylist containing all the names.
     */
    public ArrayList<String> getNames()
    {
	return name;
    }
    
    /**
     * Returns all the prices.
     * @return An arrayList containing all the prices.
     */
    public ArrayList<Double> getPrices()
    {
	return price;
    }
    
    /**
     * Returns the reason why an item was removed.
     * @return An arraylist of reasons why items were removed.
     */
    public ArrayList<ReasonRemoved> getReasons()
    {
	return reason;
    }
    
    /**
     * Returns the action dates.
     * @return An arraylist of all the action dates.
     */
    public ArrayList<Calendar> getActionDates()
    {
	return actionDate;
    }
    
    /**
     * Gets the expiration dates of all the items.
     * @return An arrayList containing all the expiration dates
     * of all the items.
     */
    public ArrayList<Calendar> getExpDates()
    {
	return expDate;
    }
    
    /**
     * Gets the rows in a machine.
     * @return The rows in a machine.
     */
    public ArrayList<Integer> getRow()
    {
	return row;
    }
    
    /**
     * Gets the columns in a machine.
     * @return Gets the columns in a machine.
     */
    public ArrayList<Integer> getCol()
    {
	return col;
    }
    
    /**
     * Gets the size/number of machines.
     * @return number of machines
     */
    public int getSize()
    {
	return name.size();
    }
    
    /**
     * Compiles all the statistic reports into one report.
     * @param report - arrayList containing all the individual reports.
     */
    private void build(ArrayList<ArrayList<Object>> report)
    {
	for (ArrayList<Object> h : report)
	{
	    name.add((String) h.get(0));
	    price.add((Double) h.get(1));
	    reason.add((ReasonRemoved) h.get(2));
	    actionDate.add((Calendar) h.get(3));
	    expDate.add((Calendar) h.get(4));
	    row.add((Integer) h.get(5));
	    col.add((Integer) h.get(6));
	}
    }
}
