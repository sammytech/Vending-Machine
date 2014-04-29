package edu.rit.se.coolTeamB.build_run;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import edu.rit.se.coolTeamB.mechanics.DatabaseWriter;

public class MainGUI extends JFrame
{

	private boolean newDatabase;
	private int row;
	private int col;
	private int depth;
	private int numberOfVendingMachines;
	private int flag;
	private int machineID;
	private Integer[] databaseInfo;
	private JPanel mainLayout;
	private JPanel mainGrid;
	private JButton exit;
	private SystemControl control;

	public MainGUI()
	{
		setSize(600, 500);
		setTitle("Main");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);

		mainLayout = new JPanel(new BorderLayout());
		mainGrid = new JPanel(new GridLayout(0, 1));

		JButton create = new JButton("Create new database");
		JButton existing = new JButton("Use existing database");
		exit = new JButton("Exit");
		exit.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});

		mainGrid.add(create);
		mainGrid.add(existing);
		mainGrid.add(exit);
		mainLayout.add(mainGrid);

		this.add(mainLayout, BorderLayout.CENTER);
		setVisible(true);

		create.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{

				createDB();
			}

		});

		existing.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				existingDBInfo();
				if (databaseInfo != null)
				{
					haveDB();
				}
			}

		});

	}

	private void makeNewDB()
	{

		if (numberOfVendingMachines > 0)
		{
			DatabaseWriter.createDatabase(numberOfVendingMachines, row, col,
					depth);
			control = new SystemControl(numberOfVendingMachines, row, col,
					depth);
		}
		else
		{
			System.out.println("Incorrect number " + "of vending machines.");
		}
	}

	private void existingDBInfo()
	{
		databaseInfo = getDatabaseInfo();
		if (databaseInfo != null)
		{
			numberOfVendingMachines = databaseInfo[0];
			row = databaseInfo[1];
			col = databaseInfo[2];
			depth = databaseInfo[3];
			control = new SystemControl(numberOfVendingMachines, row, col,
					depth);
		}
		else
		{
			System.out.println("Error. Corrupt database.");
		}
	}

	private void createDB()
	{
		JPanel centerFlow1 = new JPanel(new FlowLayout());
		JPanel centerFlow2 = new JPanel(new FlowLayout());
		JPanel centerFlow3 = new JPanel(new FlowLayout());
		JPanel centerFlow4 = new JPanel(new FlowLayout());
		JPanel centerFlow5 = new JPanel(new FlowLayout());

		JPanel textGrid1 = new JPanel(new GridLayout(0, 1));
		JPanel textGrid2 = new JPanel(new GridLayout(0, 1));
		JPanel textGrid3 = new JPanel(new GridLayout(0, 1));
		JPanel textGrid4 = new JPanel(new GridLayout(0, 1));
		JPanel textGrid5 = new JPanel(new GridLayout(0, 1));

		JLabel numL = new JLabel("Number of vending machines");
		JLabel rowL = new JLabel("Number of Rows");
		JLabel colL = new JLabel("Number of Columns");
		JLabel depthL = new JLabel("Depth Size");

		final JTextField numMachines = new JTextField(2);
		final JTextField numRows = new JTextField(2);
		final JTextField numCols = new JTextField(2);
		final JTextField depthAmt = new JTextField(2);

		makeStringInvalid(numMachines);
		makeStringInvalid(numRows);
		makeStringInvalid(numCols);
		makeStringInvalid(depthAmt);

		JButton create = new JButton("Create");
		JButton useExisting = new JButton("Use Existing Database");

		textGrid1.add(numL);
		textGrid1.add(numMachines);
		textGrid2.add(rowL);
		textGrid2.add(numRows);
		textGrid3.add(colL);
		textGrid3.add(numCols);
		textGrid4.add(depthL);
		textGrid4.add(depthAmt);
		textGrid5.add(create);
		textGrid5.add(useExisting);

		centerFlow1.add(textGrid1);
		centerFlow2.add(textGrid2);
		centerFlow3.add(textGrid3);
		centerFlow4.add(textGrid4);
		centerFlow5.add(textGrid5);

		create.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent a)
			{
				try
				{
					numberOfVendingMachines = Integer.parseInt(numMachines
							.getText());
					row = Integer.parseInt(numRows.getText());
					col = Integer.parseInt(numCols.getText());
					depth = Integer.parseInt(depthAmt.getText());
					makeNewDB();
					haveDB();
				}
				catch (NumberFormatException e)
				{
					System.out.println("Incorrect input.");
				}

			}

		});

		useExisting.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				existingDBInfo();
				if (databaseInfo != null)
				{
					haveDB();
				}

			}

		});

		mainGrid.removeAll();

		mainGrid.add(centerFlow1);
		mainGrid.add(centerFlow2);
		mainGrid.add(centerFlow3);
		mainGrid.add(centerFlow4);
		mainLayout.add(centerFlow5, BorderLayout.SOUTH);

		this.validate();

	}

	private void makeStringInvalid(final JTextField text)
	{
		text.addKeyListener(new KeyAdapter()
		{
			public void keyTyped(KeyEvent e)
			{
				char c = e.getKeyChar();

				if ((!Character.isDigit(c)) && (c != KeyEvent.VK_BACK_SPACE))
				{
					e.consume(); // ignore event
				}
			}
		});

	}

	private void haveDB()
	{
		JPanel textGrid1 = new JPanel(new GridLayout(3, 1));

		JButton cust = new JButton("Customer");
		JButton rest = new JButton("Restocker");
		JButton mark = new JButton("Marketing");

		JLabel nMachines = new JLabel("Number Of Vending Machines: "
				+ numberOfVendingMachines, SwingConstants.CENTER);
		JLabel rowColDepth = new JLabel("Row: " + row + "     Column: " + col
				+ "     Depth: " + depth, SwingConstants.CENTER);
		JLabel type = new JLabel(
				"Please pick which type of interface you'd like to enter:",
				SwingConstants.CENTER);
		textGrid1.add(nMachines);
		textGrid1.add(rowColDepth);
		textGrid1.add(type);
		// type.setBorderPainted(false);

		mainGrid.removeAll();
		mainLayout.removeAll();
		mainGrid.add(textGrid1);
		mainGrid.add(cust);
		mainGrid.add(rest);
		mainGrid.add(mark);
		mainLayout.add(mainGrid);

		this.validate();

		cust.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				getID(1);
			}

		});
		rest.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				getID(2);
			}

		});

		mark.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				control.openMarketingUI();
				haveDB();
			}
		});

	}

	private void getID(int ui)
	{
		flag = ui;

		JPanel centerFlow6 = new JPanel(new FlowLayout());
		JPanel centerFlow7 = new JPanel(new FlowLayout());

		JPanel textGrid1 = new JPanel(new GridLayout(0, 1));
		JLabel enterID = new JLabel("Enter the ID of the vending machine",
				SwingConstants.CENTER);
		JLabel validID = new JLabel("Your options are 1 - "
				+ numberOfVendingMachines, SwingConstants.CENTER);
		textGrid1.add(enterID);
		textGrid1.add(validID);

		final JTextField id = new JTextField();
		JButton enter = new JButton("Enter");
		JButton back = new JButton("Back");

		mainGrid.removeAll();
		mainLayout.removeAll();
		mainGrid.add(textGrid1);
		mainGrid.add(id);

		centerFlow6.add(mainGrid);
		centerFlow7.add(back);
		centerFlow7.add(enter);

		mainLayout.add(centerFlow6, BorderLayout.CENTER);
		mainLayout.add(centerFlow7, BorderLayout.SOUTH);

		this.validate();

		enter.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					machineID = Integer.parseInt(id.getText());
					if (flag == 1)
					{
						control.openCustomerUI(machineID - 1);
					}
					if (flag == 2)
					{
						control.openRestockerUI(machineID - 1);
					}
					if (flag == 3)
					{
						control.openMarketingUI();
					}
					haveDB();
				}
				catch (Exception e1)
				{
					JOptionPane.showMessageDialog(null,
							"Please enter a valid number");
				}
			}

		});
		back.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				haveDB();

			}

		});

	}

	/**
	 * Reads in the database information file and returns the information
	 * contained therein.
	 * 
	 * @return Integer[] that contains the information in the database file
	 */
	private static Integer[] getDatabaseInfo()
	{
		Integer[] databaseInfoArray = null;
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

			if ((line = reader.readLine()) != null)
			{
				input = line.split(delimiter);
			}
			else
			{
				String infoMessage = " Error. Database information file is empty.";

				JOptionPane.showMessageDialog(null, infoMessage, "Error",
						JOptionPane.ERROR_MESSAGE);
				System.out.println("Error."
						+ " Database information file is empty.");
			}
			reader.close();
			readStream.close();
		}
		catch (FileNotFoundException e)
		{
			System.out.println("Error in opening database information file.");
			String infoMessage = " Error in reading from database information file";

			JOptionPane.showMessageDialog(null, infoMessage, "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		catch (IOException e)
		{
			String infoMessage = " Error in reading from database information file";

			JOptionPane.showMessageDialog(null, infoMessage, "Error",
					JOptionPane.ERROR_MESSAGE);
			System.out.println("Error in reading from database"
					+ " information file.");
		}

		if (input == null)
		{
			String infoMessage = " Error. Database information file is empty.";

			JOptionPane.showMessageDialog(null, infoMessage, "Error",
					JOptionPane.ERROR_MESSAGE);
			System.out.println("Error. Database information file is empty.");
		}
		else if (input.length != 4)
		{
			String infoMessage = "Error. Database information file is corrupt.";

			JOptionPane.showMessageDialog(null, infoMessage, "Error",
					JOptionPane.ERROR_MESSAGE);
			System.out.println("Error. Database information file is corrupt.");
		}
		else
		{
			databaseInfoArray = new Integer[4];
			try
			{
				for (int i = 0; i < 4; i++)
				{

					databaseInfoArray[i] = Integer.parseInt(input[i]);
					if (databaseInfoArray[i] <= 0)
					{
						String infoMessage = "Error."
								+ " Database information file is corrupt.";

						JOptionPane.showMessageDialog(null, infoMessage,
								"Error", JOptionPane.ERROR_MESSAGE);
						System.out.println("Error."
								+ " Database information file is corrupt.");
						// so main does not try to use database, set to null
						databaseInfoArray = null;
						break;
					}
				}
			}
			catch (NumberFormatException e)
			{
				String infoMessage = "Error."
						+ " Database information file is corrupt.";

				JOptionPane.showMessageDialog(null, infoMessage, "Error",
						JOptionPane.ERROR_MESSAGE);
				System.out.println("Error."
						+ " Database information file is corrupt.");
				// so main does not try to use database, set to null
				databaseInfoArray = null;

			}
		}
		return databaseInfoArray;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		
		MainGUI GUI = new MainGUI();
		

	}

}
