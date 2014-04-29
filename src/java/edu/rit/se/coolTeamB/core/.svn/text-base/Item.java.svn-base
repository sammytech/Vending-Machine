/*
 * File: Item.java
 * An item that is stored on a local vending machine.
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
 * The <CODE>Item</CODE> Java class stores relevant data of the
 * items stored in a local vending machine connected to the Smart Vending 
 * Machine (SVM) system.
 * 
 * @version
 *   1.00 22 Mar 2013
 * @author
 *   Raphael Kahler (rak9698@rit.edu)
 ******************************************************************************/
public class Item
{
	private String name;
	private double price;
	private Calendar expDate;

	/**
	 * <CODE>Item</CODE> stores relevant item information necessary for 
	 * use in the vending machine system.
	 * 
	 * <dt><b>Precondition:</b><dd>
	 *   itemPrice is not negative, date is not null
	 * @param itemName
	 *   name of the item
	 * @param itemPrice
	 *   price of the item
	 * @param itemExpDate
	 *   expiration date of item
	 * @param itemExpDate
	 *   expiration date of the item
	 * <dt><b>Postcondition:</b><dd>
	 *   item was created with specified name and price and expiration date
	 */
	public Item(String itemName, double itemPrice, Calendar itemExpDate) 
			throws NegativeInputException, NullDateException
			{
		if (itemPrice < 0)
		{
			throw new NegativeInputException();
		}
		if (itemExpDate == null)
		{
			throw new NullDateException();
		}
		name = itemName;
		price = itemPrice;
		expDate = itemExpDate;

			}

	/**
	 * Returns the item name.
	 * 
	 * @return
	 *   name of the item
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Set the name of the item to a new name.
	 * @param newName
	 *   the new name of the item
	 * <dt><b>Postcondition:</b><dd>
	 *   item name has been updated to the new name
	 */
	public void setName(String newName)
	{
		name = newName;
	}

	/**
	 * Returns the item price.
	 * 
	 * @return
	 *   price of the item
	 */
	public double getPrice()
	{
		return price;
	}

	/**
	 * Set the price of the item to a new value.
	 * 
	 * <dt><b>Precondition:</b><dd>
	 *   newPrice is not negative
	 * @param newPrice
	 *   the new price of the item
	 * <dt><b>Postcondition:</b><dd>
	 *   item price has been updated to the new price
	 */
	public void setPrice(double newPrice) throws NegativeInputException
	{
		if (newPrice < 0)
		{
			throw new NegativeInputException();
		}
		price = newPrice;
	}


	/**
	 * Returns the expiration date of an item
	 * @return expDate
	 *   item expiration date
	 */


	/**
	 * Returns the expiration date of the item
	 *
	 * @return
	 *   expiration date of the item
	 */

	public Calendar getDate()
	{
		return expDate;
	}


	/**
	 * makes a copy of an item
	 * @param itemToCopy
	 * 	 the item to be copied
	 * @return item
	 * 	 the copy of the item passed in
	 * @throws NegativeInputException
	 * @throws NullDateException
	 */

	/**
	 * Makes a copy on an existing item.
	 * 
	 * @param itemToCopy
	 *   item to be copied
	 * @return
	 *   a copy of the item
	 * @throws NegativeInputException
	 * @throws NullDateException
	 */

	public Item copy(Item itemToCopy) throws NegativeInputException, 
	NullDateException
	{
		Item item = new Item(itemToCopy.getName(), itemToCopy.getPrice(),
				itemToCopy.getDate());
		return item;
	}

	/**
	 * checks to see if other item is the same as this item
	 * @return 
	 * 	 true if the items are the same, false otherwise
	 */
	@Override
	public boolean equals(Object o)
	{
		if(o instanceof Item)
		{
			return this.name.equals(((Item) o).getName());
		}
		return false;
	}

	/**
	 * returns the hash code of the name of the item
	 * @return int
	 * 	 hash code of this item
	 */
	@Override
	public int hashCode()
	{
		return this.name.hashCode();
	}
}