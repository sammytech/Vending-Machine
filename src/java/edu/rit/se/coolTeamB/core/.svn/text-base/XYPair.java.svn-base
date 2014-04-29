/*
 * File: XYPair.java
 * struct containing two integers used for coordinate purposes.
 * 
 * Version 1.0
 * 
 * Authors:
 *   Michael Surdouski (mxs1649@rit.edu)
 */

/******************************************************************************
 * The <CODE>XYPair</CODE> Java class acts as a struct and can be used
 * for coordinate purposes.
 * 
 * @version
 *   1.00 13 Apr 2013
 * @author
 *   Michael Surdouski (mxs1649@rit.edu)
 ******************************************************************************/

package edu.rit.se.coolTeamB.core;

public class XYPair 
{
    public int x;
    public int y;
    
    /**
     * Sets the x and y coordinates to the specified values.
     * 
     * @param X
     *   value of the x coordinate
     * @param Y
     *   value of the y coordinate
     */
    public XYPair(int X, int Y)
    {
	x = X;
	y = Y;
    }
    
    @Override
    public boolean equals(Object o)
    {
	if (o instanceof XYPair)
	{
	    if (((XYPair) o).x == this.x && ((XYPair) o).y == this.y)
	    {
		return true;
	    }
	}
	return false;
    }
    
    @Override
    public int hashCode()
    {
	return x * 10000 + y;
    }
    
}
