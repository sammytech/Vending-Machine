/*
 * File: Main.java The main class for running the program
 * 
 * Version 1.0
 * 
 * Authors: Michael Surdouski (mxs1649)
 */

package edu.rit.se.coolTeamB.build_run;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.io.File;

/******************************************************************************
 * The <CODE>Main</CODE> Java class interacts with the user and initalizes the
 * interface that is chosen.
 * 
 * @version 1.00 21 Mar 2013
 * @author Michael Surdouski (mxs1649@rit.edu)
 ******************************************************************************/

public class Main
{
	/**
	 * <CODE>main</CODE> Starts the smart vending machine program.
	 * 
	 * @param args
	 *            commandline arguments
	 * @throws IOException
	 */
	public static void main(String args[]) throws IOException
	{

		Scanner in = new Scanner(System.in);
		File lockfile = new File("./lock_file.txt");
		File problemFile = new File("./lock_file1.txt");

		if (problemFile.exists())
		{
			problemFile.delete();
		}
		if (!lockfile.exists())
		{
			lockfile.createNewFile();
			FileWriter writeStream = new FileWriter("./lock_file.txt");
			if (args.length > 0 && args[0].equals("UI"))
			{
				System.out.println("How Many Machines: ");
				int howManyMachines = in.nextInt();
				System.out.println("How many row: ");
				int row = in.nextInt();
				System.out.println("How many col: ");
				int col = in.nextInt();
				System.out.println("What is the depth: ");
				int depth = in.nextInt();
				SystemControl CLI = new SystemControl(howManyMachines, row,
						col, depth);
			}
			else
			{
				while (true)
				{
					MainGUI mainInterface = new MainGUI();
					while (true)
					{
						in.nextLine();
					}
				}
			}
		}
		else
		{
			boolean success = lockfile.renameTo(new File("./lock_file1.txt"));
			if (!success)
			{
				System.exit(0);
			}
			else
			{
				lockfile.renameTo(new File("./lock_file.txt"));
				FileWriter writeStream = new FileWriter("./lock_file.txt");
				if (args.length > 0 && args[0].equals("UI"))
				{
					System.out.println("How Many Machines: ");
					int howManyMachines = in.nextInt();
					System.out.println("How many row: ");
					int row = in.nextInt();
					System.out.println("How many col: ");
					int col = in.nextInt();
					System.out.println("What is the depth: ");
					int depth = in.nextInt();
					SystemControl CLI = new SystemControl(howManyMachines, row,
							col, depth);
				}
				else
				{
					while (true)
					{
						MainGUI mainInterface = new MainGUI();
						while (true)
						{
							in.nextLine();
						}
					}
				}
			}
		}
	}
}
