/*
 * File: RestockerGUI.java Restocker access user interface for the Smart Vending
 * Machine (SVM) system.
 * 
 * Version 1.0
 * 
 * Authors: Zachary Calfin (zpc4747@rit.edu)
 */

package edu.rit.se.coolTeamB.restocker;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormatSymbols;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.table.DefaultTableModel;
import edu.rit.se.coolTeamB.core.LocalVend;
import edu.rit.se.coolTeamB.core.XYPair;
import edu.rit.se.coolTeamB.exceptions.ExistingStockException;
import edu.rit.se.coolTeamB.exceptions.NegativeInputException;
import edu.rit.se.coolTeamB.exceptions.NonExistingStockException;
import edu.rit.se.coolTeamB.exceptions.NullDateException;
import edu.rit.se.coolTeamB.exceptions.OutOfBoundsStockException;
import edu.rit.se.coolTeamB.exceptions.StockException;
import edu.rit.se.coolTeamB.exceptions.StockNullPointerException;
import com.toedter.calendar.JDateChooser;

/******************************************************************************
 * The <CODE>RestockerGUI</CODE> Java class provides a GUI for restocker level
 * access of the SVM system.
 * 
 * @version 1.00 14 April 2013
 * @author Zachary Calfin (zpc4747@rit.edu)
 ******************************************************************************/

public class RestockerGUI extends JFrame implements Observer
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel mainLayout = new JPanel(new BorderLayout(20, 20));
	public int ID;
	private RestockerManager manager;
	private LocalVend thisVend;
	private Object[][] itemsArray;
	private Scanner in = new Scanner(System.in);

	/**
	 * <CODE>RestockerGUI</CODE> displays the graphical user interface for the
	 * restocker
	 * 
	 * @param ID
	 *            the id of the vending machine being restocked
	 * @param manager
	 *            the manager for the restocker <dt><b>Postcondition:</b>
	 *            <dd>
	 *            restocker GUI is created and displayed
	 */
	public RestockerGUI(int ID, RestockerManager manager)
	{
		// setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.manager = manager;
		this.ID = ID;
		setUp(ID);
		setSize(600, 500);
		lockScreen();
		add(mainLayout);
		this.setResizable(false);
		setVisible(true);
		try
		{
			itemsArray = getStock();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Failed to populate itemsArray...");
			System.out.println("Quitting the restocker manager...");
			System.exit(DISPOSE_ON_CLOSE);
		}
	}

	/**
	 * setUp
	 * 
	 * @param ID
	 */
	private void setUp(int ID)
	{
		manager.register(this, ID);
	}

	/**
	 * screen to unlock the gui <dt><b>Postcondition:</b>
	 * <dd>
	 * the lock screen is displated
	 */
	private void lockScreen()
	{
		mainLayout.setLayout(new BorderLayout());

		JButton lock = new JButton("LOCKED (CLICK TO UNLOCK)");
		lock.setPreferredSize(new Dimension(5, 5));
		lock.setFont(new Font("Verdana", Font.BOLD, 32));

		lock.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				try
				{
					if (manager.verifyBegin(ID))
					{
						mainLayout.removeAll();
						choiceScreen();
					}
				}
				catch (Exception e1)
				{
					// TODO Auto-generated catch block
					System.out
							.println("problem with choice screen");
				}
			}
		});
		mainLayout.add(lock, BorderLayout.CENTER);
		mainLayout.revalidate();
		mainLayout.repaint();

	}

	/**
	 * choiceScreen creates the gui screen for the restocker to choose what he
	 * wants to do. the options are Read Marking note, Read restocker note, list
	 * stock and edit stock <dt><b>Postcondition:</b>
	 * <dd>
	 * The choice screen is displayed
	 */
	private void choiceScreen()
	{
		// removes everything from the main layout
		mainLayout.removeAll();

		// sets the title
		setTitle("Restocker Interface for machine ID " + (ID + 1));

		// main grid layout
		JPanel mainGrid = new JPanel(new GridLayout(0, 2, 10, 10));

		// buttons
		JButton readRestockNote = new JButton("Read Restocker Note");
		readRestockNote.setFont(new Font("Verdana", 20, 20));
		JButton changeRestockNote = new JButton("Change Restocker Note");
		changeRestockNote.setFont(new Font("Verdana", 20, 20));
		JButton readMarketNote = new JButton("Read Marketing Note");
		readMarketNote.setFont(new Font("Verdana", 20, 20));
		JButton restockItem = new JButton("Edit Stock");
		restockItem.setFont(new Font("Verdana", 20, 20));
		JButton addItem = new JButton("Add New Items");
		addItem.setFont(new Font("Verdana", 20, 20));
		JButton removeItem = new JButton("Remove Items");
		removeItem.setFont(new Font("Verdana", 20, 20));
		JButton listStock = new JButton("List Stock");
		listStock.setFont(new Font("Verdana", 20, 20));
		JButton exit = new JButton("Exit");
		exit.setFont(new Font("Verdana", 20, 20));

		// add to grid
		mainGrid.add(readRestockNote);
		// mainGrid.add(changeRestockNote);
		mainGrid.add(readMarketNote);
		mainGrid.add(restockItem);
		// mainGrid.add(addItem);
		// mainGrid.add(removeItem);
		mainGrid.add(listStock);
		// mainGrid.add(exit);

		// actionListeners
		changeRestockNote.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				readRestockerNote();

			}

		});

		readRestockNote.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{

				readRestockerNote();

			}

		});

		readMarketNote.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				readMarketNote();

			}

		});

		restockItem.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				editItems();

			}

		});

		listStock.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				listStock();

			}

		});

		mainLayout.add(mainGrid, BorderLayout.CENTER);
		SwingUtilities.updateComponentTreeUI(this);

	}

	private void readRestockerNote()
	{
		// removes everything from the main layout
		mainLayout.removeAll();

		BorderLayout mainLayoutLayout = new BorderLayout();
		mainLayoutLayout.setVgap(10);
		mainLayout.setLayout(mainLayoutLayout);

		JLabel noteDateLabel = new JLabel("Note Date: ");
		noteDateLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		final JLabel noteDate = new JLabel(getNoteDate(1));
		noteDate.setFont(new Font("Tahoma", Font.PLAIN, 20));
		JLabel machineIDLabel = new JLabel("Machine ID: ");
		machineIDLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		JLabel machineID = new JLabel(String.valueOf(ID + 1));
		machineID.setVerticalTextPosition(SwingConstants.BOTTOM);
		machineID.setFont(new Font("Tahoma", Font.PLAIN, 20));

		JLabel header = new JLabel("RESTOCKER NOTE");
		header.setFont(new Font("Verdana", Font.BOLD, 25));
		header.setHorizontalAlignment(SwingConstants.CENTER);
		JPanel topGrid = new JPanel(new GridLayout(2, 1));
		JPanel dateFlow = new JPanel(new FlowLayout());
		JPanel idFlow = new JPanel(new FlowLayout());
		JPanel dateIdGrid = new JPanel(new GridLayout(1, 2));

		dateFlow.add(noteDateLabel);
		dateFlow.add(noteDate);
		idFlow.add(machineIDLabel);
		idFlow.add(machineID);

		topGrid.add(header);
		dateIdGrid.add(dateFlow);
		dateIdGrid.add(idFlow);
		topGrid.add(dateIdGrid);

		// topFlow.add(topGrid);

		JLabel noteLabel = new JLabel("Note:  ");
		noteLabel.setFont(new Font("Tahoma", Font.BOLD, 20));

		final JTextArea restockerNote = new JTextArea(getRestockerNote());
		restockerNote.setFont(new Font("Arial", Font.PLAIN, 14));
		JScrollPane scrollText = new JScrollPane(restockerNote);

		JPanel buttonFlow = new JPanel(new FlowLayout());

		final JButton save = new JButton("Save Note");
		save.setEnabled(false);
		JButton back = new JButton("Back");
		final JLabel info = new JLabel("No changes made");

		buttonFlow.add(save);
		buttonFlow.add(back);
		buttonFlow.add(info);

		// panels for restocker note

		JPanel reNoteFlow = new JPanel(new BorderLayout(0, 20));
		// adds to the flow layouts for centering

		reNoteFlow.add(noteLabel, BorderLayout.NORTH);
		reNoteFlow.add(scrollText, BorderLayout.CENTER);
		// adds to the main layout
		mainLayout.add(topGrid, BorderLayout.NORTH);
		mainLayout.add(reNoteFlow, BorderLayout.CENTER);
		mainLayout.add(buttonFlow, BorderLayout.SOUTH);

		back.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				choiceScreen();

			}

		});

		SwingUtilities.updateComponentTreeUI(this);

		restockerNote.addCaretListener(new CaretListener()
		{

			@Override
			public void caretUpdate(CaretEvent e)
			{
				// TODO Auto-generated method stub
				String databaseText = getDatabaseText(restockerNote.getText());
				// System.out.println(databaseText);
				if (databaseText.equals(getRestockerNote()))
				{
					save.setEnabled(false);
					info.setText("No changes made");
				}
				else
				{
					save.setEnabled(true);
					info.setText("You need to save your changes");
				}
			}

		});
		save.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				try
				{
					changeRestockerNote(restockerNote.getText());
					noteDate.setText(getNoteDate(1));
					restockerNote.setText(getRestockerNote());
					info.setText("Changes Saved");
				}
				catch (NullDateException e)
				{
					// TODO Auto-generated catch block
					System.out.println("Error. Couldn't change Restocker Note");
					e.printStackTrace();
				}

			}

		});

	}

	private void readMarketNote()
	{
		// removes everything from the main layout
		mainLayout.removeAll();

		BorderLayout marketNoteLayout = new BorderLayout();
		marketNoteLayout.setVgap(10);
		mainLayout.setLayout(marketNoteLayout);

		JLabel noteDateLabel = new JLabel("Note Date: ");
		noteDateLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		JLabel noteDate = new JLabel(getNoteDate(2));
		noteDate.setFont(new Font("Tahoma", Font.PLAIN, 20));
		JLabel machineIDLabel = new JLabel("Machine ID: ");
		machineIDLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		JLabel machineID = new JLabel(String.valueOf(ID + 1));
		machineID.setVerticalTextPosition(SwingConstants.BOTTOM);
		machineID.setFont(new Font("Tahoma", Font.PLAIN, 20));

		JLabel header = new JLabel("MARKETING NOTE");
		header.setFont(new Font("Verdana", Font.BOLD, 25));
		header.setHorizontalAlignment(SwingConstants.CENTER);
		JPanel topGrid = new JPanel(new GridLayout(2, 1));
		JPanel dateFlow = new JPanel(new FlowLayout());
		JPanel idFlow = new JPanel(new FlowLayout());
		JPanel dateIdGrid = new JPanel(new GridLayout(1, 2));

		dateFlow.add(noteDateLabel);
		dateFlow.add(noteDate);
		idFlow.add(machineIDLabel);
		idFlow.add(machineID);

		topGrid.add(header);
		dateIdGrid.add(dateFlow);
		dateIdGrid.add(idFlow);
		topGrid.add(dateIdGrid);

		// topFlow.add(topGrid);

		JLabel noteLabel = new JLabel("Note:  ");
		noteLabel.setFont(new Font("Tahoma", Font.BOLD, 20));

		final JTextArea marketerNote = new JTextArea(readMarketingNote());
		marketerNote.setFont(new Font("Arial", Font.PLAIN, 14));
		marketerNote.setEditable(false);
		JScrollPane scrollText = new JScrollPane(marketerNote);

		JPanel buttonFlow = new JPanel(new FlowLayout());

		JButton back = new JButton("Back");

		buttonFlow.add(back);

		// panels for marketer note

		JPanel reNoteFlow = new JPanel(new BorderLayout(0, 20));
		// adds to the flow layouts for centering

		reNoteFlow.add(noteLabel, BorderLayout.NORTH);
		reNoteFlow.add(scrollText, BorderLayout.CENTER);
		// adds to the main layout
		mainLayout.add(topGrid, BorderLayout.NORTH);
		mainLayout.add(reNoteFlow, BorderLayout.CENTER);
		mainLayout.add(buttonFlow, BorderLayout.SOUTH);

		back.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				choiceScreen();

			}

		});

		SwingUtilities.updateComponentTreeUI(this);

	}

	/**
	 * opens the edit stock screen, allows the restocker to restock items, add
	 * stock, remove items including expired items. <dt><b>Postcondition:</b>
	 * <dd>
	 * The edit stock screen is displayed.
	 */
	private void editItems()
	{
		mainLayout.removeAll();
		BorderLayout editItemsLayout = new BorderLayout();
		mainLayout.setLayout(editItemsLayout);
		Object[] columnHeading = { "Row", "Column", "Item Name", "Amount",
				"Price" };
		
		DefaultTableModel tableModel = new DefaultTableModel(itemsArray,
				columnHeading)
		{

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column)
			{
				// all cells false
				return false;
			}
		};

		JLabel marketNote = new JLabel("Edit Stock");
		marketNote.setFont(new Font("Verdana",Font.BOLD, 20));
		final JLabel addRow = new JLabel("Row");
		JLabel addCol = new JLabel("Column");
		JLabel addNum = new JLabel("Amount");
		JLabel addPrice = new JLabel("Price");
		JLabel addName = new JLabel("Item Name");
		JLabel addNew = new JLabel("Add Stock");
		JLabel expDate = new JLabel("Expiration Date");
		JLabel removeOld = new JLabel("Remove Stock");
		JLabel addDay = new JLabel("Day");
		JLabel addMonth = new JLabel("Month");
		JLabel addYear = new JLabel("Year");
		JLabel rowColDepth = new JLabel("Row: " + thisVend.getNumRows()
				+ "     Col: " + thisVend.getNumCols() + "     Depth: "
				+ thisVend.getDepth());
		final JTextField row = new JTextField(3);
		final JTextField col = new JTextField(3);
		final JTextField price = new JTextField(4);
		final JTextField num = new JTextField(4);
		final JTextField name = new JTextField(7);
		final JTextField expDay = new JTextField(3);
		final JTextField expMonth = new JTextField(3);
		final JTextField expYear = new JTextField(3);
		JPanel addNewStock = new JPanel(new GridLayout(6, 1));
		JPanel addStockFlow1 = new JPanel(new FlowLayout());
		JPanel addStockFlow2 = new JPanel(new FlowLayout());
		JPanel addStockFlow3 = new JPanel(new FlowLayout());
		JPanel addStockFlow4 = new JPanel(new FlowLayout());
		JPanel addStockFlow5 = new JPanel(new FlowLayout());
		JPanel addStockFlow6 = new JPanel(new FlowLayout());
		JPanel addStockFlow7 = new JPanel(new FlowLayout());
		JPanel removeStockFlow1 = new JPanel(new FlowLayout());
		JPanel removeStockFlow2 = new JPanel(new FlowLayout());
		JPanel removeStockFlow3 = new JPanel(new FlowLayout());
		JPanel noteFlow = new JPanel(new FlowLayout());
		JPanel restockGrid = new JPanel(new GridLayout());
		JPanel editBorder = new JPanel(new BorderLayout());
		JPanel editButtons = new JPanel(new FlowLayout());
		JPanel editFinal = new JPanel(new FlowLayout());
		JPanel addRemoveGrid = new JPanel(new GridLayout(0, 1));
		JPanel removeGrid = new JPanel(new GridLayout(0, 1));
		JPanel removeFinal = new JPanel(new FlowLayout());
		JButton addItem = new JButton("Add");
		JButton restockItem = new JButton("Restock Selected Item");
		JButton removeExpired = new JButton("Remove Expired Items");
		JButton editBack = new JButton("Back");
		JButton removeSelected = new JButton("Remove Selected Item");
		final JDateChooser pickDate = new JDateChooser();

		addStockFlow1.add(addNew);
		addStockFlow2.add(addName);
		addStockFlow2.add(name);
		addStockFlow2.add(addNum);
		addStockFlow2.add(num);
		addStockFlow3.add(addRow);
		addStockFlow3.add(row);
		addStockFlow3.add(addCol);
		addStockFlow3.add(col);
		addStockFlow3.add(addPrice);
		addStockFlow3.add(price);
		addStockFlow4.add(addItem);
		addStockFlow4.add(restockItem);
		addStockFlow5.add(expDate);
		addStockFlow6.add(pickDate);
		addStockFlow7.add(rowColDepth);
		// addStockFlow6.add(addDay);
		// addStockFlow6.add(expDay);
		// addStockFlow6.add(addMonth);
		// addStockFlow6.add(expMonth);
		// addStockFlow6.add(addYear);
		// addStockFlow6.add(expYear);
		addNewStock.add(addStockFlow1);
		addNewStock.add(addStockFlow2);
		addNewStock.add(addStockFlow3);
		addNewStock.add(addStockFlow5);
		addNewStock.add(addStockFlow6);
		addNewStock.add(addStockFlow7);
		// addNewStock.add(addStockFlow4);
		editFinal.add(addNewStock);
		noteFlow.add(marketNote);
		removeStockFlow1.add(removeOld);
		removeStockFlow2.add(removeSelected);
		removeStockFlow3.add(removeExpired);
		removeGrid.add(addStockFlow4);
		removeGrid.add(removeStockFlow1);
		removeGrid.add(removeStockFlow2);
		removeGrid.add(removeStockFlow3);
		removeFinal.add(removeGrid);
		addRemoveGrid.add(editFinal);
		addRemoveGrid.add(removeFinal);
		editBorder.add(addRemoveGrid, BorderLayout.CENTER);
		editButtons.add(editBack);

		mainLayout.add(editButtons, BorderLayout.SOUTH);

		// mainLayout.add()

		setTitle("Add or Remove Items");

		final JTable stockTable = new JTable(tableModel);
		stockTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		stockTable.setFont(new Font("Verdana", 20, 15));
		stockTable.setRowHeight(40);
		stockTable.getColumnModel().getColumn(0).setMaxWidth(50);
		stockTable.getColumnModel().getColumn(0).setMinWidth(50);
		stockTable.getColumnModel().getColumn(1).setMaxWidth(50);
		stockTable.getColumnModel().getColumn(1).setMinWidth(50);
		stockTable.setBackground(Color.white);
		stockTable.setGridColor(Color.BLACK);
		stockTable.setFont(new Font("Verdana", 20, 15));
		stockTable.setRowHeight(40);

		stockTable.getTableHeader().setResizingAllowed(false);
		stockTable.getTableHeader().setReorderingAllowed(false);
		stockTable.getTableHeader().setRequestFocusEnabled(false);

		JScrollPane scrollStock = new JScrollPane(stockTable);

		mainLayout.add(noteFlow, BorderLayout.NORTH);

		restockGrid.add(scrollStock);
		restockGrid.add(editBorder);

		mainLayout.add(restockGrid, BorderLayout.CENTER);

		SwingUtilities.updateComponentTreeUI(this);

		editBack.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				choiceScreen();
			}

		});

		/*
		 * SO IMPORTANT, SET UP DATE HANDLING FOR THIS PLEASE!!!!! WE WANT THE
		 * RESTOCKER TO BE ABLE TO ENTER HIS OWN EXPIRATION DATE!!!!
		 */
		addItem.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					int getRow = Integer.parseInt(row.getText());
					int getCol = Integer.parseInt(col.getText());
					double getPrice = Double.parseDouble(price.getText());
					int getAmount = Integer.parseInt(num.getText());
					String getName = name.getText();
					if (pickDate.getCalendar() == null)
					{
						throw new NullDateException();
					}
					if (thisVend.getAtXY(getRow - 1, getCol - 1).size() != 0)
					{
						throw new StockException();
					}
					if (getName.trim().length() > 10
							|| getName.trim().length() == 0
							|| getName.trim().length() + 2 < getName.length())
					{
						throw new OutOfBoundsStockException();
					}
					try
					{
						addItem(getAmount, getPrice, getRow - 1, getCol - 1,
								getName, pickDate.getCalendar());
						try
						{
							itemsArray = getStock();
							editItems();
						}
						catch (Exception e2)
						{
							e2.printStackTrace();
							System.out
									.println("Failed to populate stock list.");
						}
					}
					catch (Exception e1)
					{
						if (e1.getClass() == NegativeInputException.class)
						{
							JOptionPane.showMessageDialog(null,
									"Can't add negative items.");
						}
						if (e1.getClass() == OutOfBoundsStockException.class)
						{
						    JOptionPane.showMessageDialog(null, "Incorrect (row, col)");
						}
					}
				}
				catch (Exception incorrectEdit)
				{
					if (incorrectEdit.getClass() == NullDateException.class)
					{
						JOptionPane.showMessageDialog(null,
								"Must input expiration date.");
					}
					if (incorrectEdit.getClass() == StockException.class)
					{
						JOptionPane
								.showMessageDialog(null,
										"Use the restock button if you want to restock an item.");
					}
					if (incorrectEdit.getClass() == OutOfBoundsStockException.class)
					{
						JOptionPane
								.showMessageDialog(
										null,
										"Improper name formatting. Please use the following guidelines:"
												+ "\nLength must be greater than 0 \n"
												+ "Length without whitespace must less than 10 \n"
												+ "May only include 2 spaces");
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Incorrect input.");
					}
				}
			}

		});

		restockItem.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					int remRow = Integer.parseInt(stockTable
							.getValueAt(stockTable.getSelectedRow(), 0)
							.toString().trim());
					int remCol = Integer.parseInt(stockTable
							.getValueAt(stockTable.getSelectedRow(), 1)
							.toString().trim());
					if (remRow == -1 || remCol == -1)
					{
						throw new NonExistingStockException();
					}
					if (pickDate.getCalendar() == null)
					{
						throw new NullDateException();
					}
					try
					{
						manager.restock(ID, remRow - 1, remCol - 1,
								pickDate.getCalendar());
					}
					catch (Exception e2)
					{
						e2.printStackTrace();
					}
					try
					{
						itemsArray = getStock();
						editItems();
					}
					catch (Exception e3)
					{
						e3.printStackTrace();
						System.out.println("Failed to populate stock list.");
					}
				}
				catch (Exception e1)
				{
					if (e1.getClass() == NullDateException.class)
					{
						JOptionPane.showMessageDialog(null,
								"Must input expiration date");
					}
					else if (e1.getClass() == NonExistingStockException.class)
					{
						JOptionPane.showMessageDialog(null,
								"Must select an item.");
					}
					else
					{
						e1.printStackTrace();
					}
				}
			}
		});

		removeExpired.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					removeExpiredItems();
				}
				catch (Exception e1)
				{
					e1.printStackTrace();
					System.out.println("Failed to remove expired items.");
				}
				try
				{
					itemsArray = getStock();
				}
				catch (Exception e2)
				{
					e2.printStackTrace();
					System.out.println("Failed to populate stock list.");
				}
				editItems();

			}

		});

		/*
		 * restockSelected.addActionListener(new ActionListener(){
		 * 
		 * @Override public void actionPerformed(Actionevent e) { String
		 * restockName = stockTable.getValueAt(stockTable.getSelectedRow(),
		 * 0).toString().trim(); int remRow =
		 * Integer.parseInt(stockTable.getValueAt(stockTable.getSelectedRow(),
		 * 1).toString().trim()); int remCol =
		 * Integer.parseInt(stockTable.getValueAt(stockTable.getSelectedRow(),
		 * 2).toString().trim());
		 * 
		 * // add here the popup with date to add or something similar // also
		 * make sure this button gets added to screen, very simple // make sure
		 * to copy any error handling I did in the customer
		 * 
		 * try { manager.restock(ID, row, col, date); } } });
		 */

		removeSelected.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				String remName = stockTable
						.getValueAt(stockTable.getSelectedRow(), 2).toString()
						.trim();
				int remRow = Integer.parseInt(stockTable
						.getValueAt(stockTable.getSelectedRow(), 0).toString()
						.trim());
				int remCol = Integer.parseInt(stockTable
						.getValueAt(stockTable.getSelectedRow(), 1).toString()
						.trim());
				// uncomment if needed
				// int remAmt =
				// Integer.parseInt(stockTable.getValueAt(stockTable.getSelectedRow(),
				// 3).toString().trim());
				// double remPrice
				// Double.parsedouble(stockTable.getValueAt(stockTable.getSelectedRow(),
				// 4).toString().trim());
				try
				{
					removeItem(remName, remRow - 1, remCol - 1);
				}
				catch (Exception e1)
				{
					e1.printStackTrace();
					System.out.println("Failed to remove item.");
				}
				try
				{
					itemsArray = getStock();
				}
				catch (Exception e2)
				{
					e2.printStackTrace();
					System.out.println("Failed to populate stock list.");
				}
				editItems();

			}

		});
	}

	/**
	 * listStock displays the screen with a stock listing <dt>
	 * <b>Postcondition:</b>
	 * <dd>
	 * The list stock screen is displayed
	 */
	private void listStock()
	{
		mainLayout.removeAll();
		BorderLayout listItemsLayout = new BorderLayout();
		mainLayout.setLayout(listItemsLayout);
		Object[] columnHeading = { "Row", "Column", "Item Name", "Amount",
				"Price" };
		
		DefaultTableModel tableModel = new DefaultTableModel(itemsArray,
				columnHeading)
		{

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column)
			{
				// all cells false
				return false;
			}
		};

		JButton back = new JButton("Back");
		JPanel buttonFlow = new JPanel(new FlowLayout());
		buttonFlow.add(back);

		JTable stockTable = new JTable(tableModel);
		stockTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		stockTable.setFont(new Font("Verdana", 20, 15));
		stockTable.setRowHeight(40);
		stockTable.getColumnModel().getColumn(0).setMaxWidth(50);
		stockTable.getColumnModel().getColumn(0).setMinWidth(50);
		stockTable.getColumnModel().getColumn(1).setMaxWidth(50);
		stockTable.getColumnModel().getColumn(1).setMinWidth(50);
		stockTable.setBackground(Color.white);
		stockTable.setGridColor(Color.BLACK);
		stockTable.setFont(new Font("Verdana", 20, 15));
		stockTable.setRowHeight(40);

		stockTable.getTableHeader().setResizingAllowed(false);
		stockTable.getTableHeader().setReorderingAllowed(false);
		stockTable.getTableHeader().setRequestFocusEnabled(false);

		JScrollPane scrollStock = new JScrollPane(stockTable);
		mainLayout.add(scrollStock, BorderLayout.CENTER);
		mainLayout.add(buttonFlow, BorderLayout.SOUTH);
		SwingUtilities.updateComponentTreeUI(this);

		back.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				choiceScreen();

			}

		});

	}

	/**
	 * changes the restocker note for the vending machine <dt>
	 * <b>Postcondition:</b>
	 * <dd>
	 * The restocker note is modified
	 * 
	 * @throws NullDateException
	 */
	private void changeRestockerNote(String newNote) throws NullDateException
	{
		String temp[] = newNote.trim().split("\\n");
		String all = "";
		for (int i = 0; i < temp.length; ++i)
		{
			String tempString = temp[i].trim();
			if (!tempString.equals(""))
			{
				all += temp[i].trim() + "|";
			}
		}

		System.out.println(all);
		manager.editRestockerNote(ID, all, Calendar.getInstance());

	}

	/**
	 * @param text
	 * @return
	 */
	private String getDatabaseText(String text)
	{
		// TODO Auto-generated method stub
		String temp[] = text.trim().split("\\n");
		String all = "";
		for (int i = 0; i < temp.length; ++i)
		{
			String tempString = temp[i].trim();
			if (!tempString.equals(""))
			{
				all += temp[i].trim() + "\n";
			}
		}
		// System.out.println(all);
		return all.trim();

	}

	/**
	 * gets the restocker note from the manager
	 * 
	 * @return note the restocker note for the vending machine
	 */
	private String getRestockerNote()
	{
		// return manager.readRestockerNote(ID);
		Scanner noteScan = new Scanner(manager.readRestockerNote(ID));
		String curLine;
		String all = "";
		while (noteScan.hasNext())
		{
			curLine = noteScan.nextLine();
			if (curLine.contains("Notes"))
			{
				curLine = curLine.substring(8);

				String temp[] = curLine.split("\\|");

				for (int i = 0; i < temp.length; ++i)
				{
					all += temp[i].trim() + "\n";
					// System.out.println(temp[i]);
				}

				return all.trim();
			}
		}
		return manager.readRestockerNote(ID);
	}

	/**
	 * gets the restocker note date from the manager
	 * 
	 * @return note the restocker note date for the vending machine
	 */
	private String getNoteDate(int flag)
	{
		String dateString = "";
		Calendar date = null;
		if (flag == 1)
		{
			date = manager.getRestockerDate(ID);

		}
		else
		{
			date = manager.getMarketingDate(ID);

		}

		if (date == null)
		{
			return "no date"; // needed when no note is written yet
		}
		int day = date.get(Calendar.DATE);
		int month = date.get(Calendar.MONTH);
		int year = date.get(Calendar.YEAR);
		String monthString = new DateFormatSymbols().getMonths()[month];
		dateString += monthString + " " + day + ", " + year;
		return dateString;
	}

	/**
	 * gets the marketing note from the manager
	 * 
	 * @return note returns the marketing note for the vending machine
	 */
	private String readMarketingNote()
	{
		Scanner noteScan = new Scanner(manager.readMarketingNote(ID));
		String curLine;
		String all = "";
		while (noteScan.hasNext())
		{
			curLine = noteScan.nextLine();
			if (curLine.contains("Notes"))
			{
				curLine = curLine.substring(8);

				String temp[] = curLine.split("\\|");

				for (int i = 0; i < temp.length; ++i)
				{
					all += temp[i].trim() + "\n";
					// System.out.println(temp[i]);
				}

				return all.trim();
			}
		}
		return manager.readMarketingNote(ID);
	}

	/**
	 * gets the list of stock for the vending machine
	 * 
	 * @return stock the list of stock
	 * @throws OutOfBoundsStockException
	 */
	private Object[][] getStock() throws OutOfBoundsStockException
	{
		Object stock[][] = new Object[thisVend.getTotalStock().size()][5];
		int i = 0;
		for (Map.Entry<XYPair, Integer> itemPair : thisVend.getTotalStock()
				.entrySet())
		{
			System.out.println(itemPair.getValue());
			stock[i][2] = thisVend.getAtXYZ(itemPair.getKey().x,
					itemPair.getKey().y, 0).getName();
			stock[i][0] = itemPair.getKey().x + 1;
			stock[i][1] = itemPair.getKey().y + 1;
			stock[i][3] = itemPair.getValue();
			stock[i][4] = thisVend.getAtXYZ(itemPair.getKey().x,
					itemPair.getKey().y, 0).getPrice();
			++i;

		}

		sort2DArray(stock);

		return stock;
	}

	private void sort2DArray(Object[][] stock)
	{
		Arrays.sort(stock, new Comparator<Object[]>()
		{

			@Override
			public int compare(Object[] first, Object[] second)
			{
				String fString0 = "" + first[0];
				String fString1 = "" + first[1];
				String sString0 = "" + second[0];
				String sString1 = "" + second[1];
				String comb1 = fString0 + fString1;
				String comb2 = sString0 + sString1;
				return comb1.compareTo(comb2);

			}
		});
	}

	/**
	 * adds an item to the vending machine
	 * 
	 * @param amount
	 *            the number of items to add
	 * @param price
	 *            the price of the item to add
	 * @param row
	 *            the row to add the item to
	 * @param col
	 *            the col to add the item to
	 * @param name
	 *            the name of the item
	 * @param date
	 *            the expiration date of the item
	 * @throws NegativeInputException
	 * @throws OutOfBoundsStockException
	 * @throws ExistingStockException
	 * @throws StockException
	 * @throws NullDateException
	 *             <dt><b>Postcondition:</b>
	 *             <dd>
	 *             The item is created and added to stock
	 */
	private void addItem(int amount, double price, int row, int col,
			String name, Calendar date) throws NegativeInputException,
			OutOfBoundsStockException, ExistingStockException, StockException,
			NullDateException
	{
		manager.addStock(ID, name, price, date, row, col, amount);
	}

	/**
	 * removes an item from stock
	 * 
	 * @param name
	 *            the name of the item to remove - unused?
	 * @param row
	 *            the row of them item being removed
	 * @param col
	 *            the column of the item being removed
	 * @throws NonExistingStockException
	 * @throws OutOfBoundsStockException
	 * @throws StockException
	 * @throws NegativeInputException
	 *             <dt><b>Postcondition:</b>
	 *             <dd>
	 *             The specified item is removed from stock
	 */
	private void removeItem(String name, int row, int col)
			throws NonExistingStockException, OutOfBoundsStockException,
			StockException, NegativeInputException
	{
		manager.removeAll(ID, row, col);
	}

	/**
	 * removes all expired items from stock
	 * 
	 * @throws OutOfBoundsStockException
	 * @throws NonExistingStockException
	 * @throws StockException
	 * @throws StockNullPointerException
	 * @throws NegativeInputException
	 *             <dt><b>Postcondition:</b>
	 *             <dd>
	 *             All expired items are removed from stock
	 */
	private void removeExpiredItems() throws OutOfBoundsStockException,
			NonExistingStockException, StockException,
			StockNullPointerException, NegativeInputException
	{
		manager.removeExpired(ID);
	}

	/**
	 * update
	 */
	public void update(Observable arg, Object subject)
	{
		if (arg instanceof LocalVend)
		{

			thisVend = (LocalVend) subject;

		}
	}

}
