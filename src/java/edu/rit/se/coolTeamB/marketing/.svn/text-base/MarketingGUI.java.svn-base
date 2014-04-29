/*
 * File: MarketingGUI.java Marketing access user interface for the Smart Vending
 * Machine (SVM) system.
 * 
 * Version 1.0
 * 
 * Authors: Zachary Calfin (zpc4747@rit.edu)
 */

package edu.rit.se.coolTeamB.marketing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;
import java.util.Set;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartPanel;

import edu.rit.se.coolTeamB.core.LocalVend;
import edu.rit.se.coolTeamB.core.XYPair;
import edu.rit.se.coolTeamB.exceptions.NegativeInputException;
import edu.rit.se.coolTeamB.exceptions.NullDateException;
import edu.rit.se.coolTeamB.exceptions.OutOfBoundsStockException;
import edu.rit.se.coolTeamB.mechanics.AnalyticsEngine;

/******************************************************************************
 * The <CODE>MarketingGUI</CODE> Java class provides a GUI for marketing level
 * access of the SVM system.
 * 
 * @version 1.00 14 April 2013
 * @author Samuel Babalola (sob8666@rit.edu)
 ******************************************************************************/

public class MarketingGUI extends JFrame implements Observer
{

	private static final long serialVersionUID = 8096173782337176528L;

	private JPanel mainLayout = new JPanel(new BorderLayout(20, 20));

	private MarketingManager manager;
	private LocalVend thisVend;
	private Object[][] itemsArray;
	private Scanner in = new Scanner(System.in);
	private int numberOfMachines;
	private int numOfSelected[];
	private DecimalFormat df = new DecimalFormat("0.00");

	/**
	 * <CODE> MarketingGUI</CODE> is the graphical interface for the marketing
	 * portion of the system. It shows statistics for all of the machines
	 * available or for individual machines. Their is also a marketing graph
	 * available to show graphical statistics of the system.
	 */
	public MarketingGUI(MarketingManager manager)
	{
		// setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.manager = manager;
		numberOfMachines = manager.getNumberOfVendingMachines();

		// setSize(600, 400);
		// mainLayout.removeAll();
		// setSize(600, 400);
		// sets the title
		setTitle("Marketing Interface for machine ID ");
		setResizable(true);
		setSize(1000, 700);
		globalVendScreen();
		add(mainLayout);
		setVisible(true);

	}

	/**
	 * Brings up the screen to choose what type of mareting statistics you want.
	 */
	private void choiceScreen()
	{
		// removes everything from the main layout
		mainLayout.removeAll();
		this.setResizable(false);
		setSize(600, 400);
		// sets the title
		setTitle("Marketing Interface for machine ID ");

		// main grid layout
		// JPanel mainGrid = new JPanel(new GridLayout(0, 2, 10, 10));

		// // buttons
		// JButton localVendButton = new JButton("Local Vend");
		// localVendButton.setFont(new Font("Verdana", 20, 20));
		// JButton globalVendButton = new JButton("Global Vend");
		// globalVendButton.setHorizontalAlignment(SwingConstants.CENTER);
		// globalVendButton.setFont(new Font("Verdana", 20, 20));

		// add to grid
		// mainGrid.add(localVendButton);
		// mainGrid.add(globalVendButton);

		// mainGrid.add(listStock);
		// mainGrid.add(exit);

		// actionListeners
		globalVendScreen();

		// localVendButton.addActionListener(new ActionListener()
		// {
		//
		// @Override
		// public void actionPerformed(ActionEvent e)
		// {
		// localVendScreen();
		//
		// }
		//
		// });
		//
		// globalVendButton.addActionListener(new ActionListener()
		// {
		//
		// @Override
		// public void actionPerformed(ActionEvent e)
		// {
		//
		// globalVendScreen();
		//
		// }
		//
		// });

		// exit.addActionListener(new ActionListener(){
		//
		// @Override
		// public void actionPerformed(ActionEvent arg0) {
		// //will go back to first screen when available
		// System.exit(DISPOSE_ON_CLOSE);
		//
		//
		// }
		//
		//
		// });

		// add to main layout

		// mainLayout.add(mainGrid, BorderLayout.CENTER);
		SwingUtilities.updateComponentTreeUI(this);

	}

	/**
	 * The globalvend screen which shows statistics of the overall system.
	 */
	private void globalVendScreen()
	{
		// TODO Auto-generated method stub
		mainLayout.removeAll();

		mainLayout.setLayout(new BorderLayout(0, 20));
		JPanel mainLeftSide = new JPanel(new BorderLayout(0, 20));

		JLabel heading = new JLabel(
				"<html><center>List of Vending <br>Machines</center></html>");
		heading.setFont(new Font("Verdana", Font.BOLD, 20));

		String data[] = new String[numberOfMachines];

		for (int i = 0; i < numberOfMachines; ++i)
		{
			data[i] = "Vending Machine " + (i + 1);
		}

		final JList<String> listVendingMachine = new JList<String>(data);

		listVendingMachine.setFont(new Font("Arial", Font.PLAIN, 20));
		listVendingMachine.setFixedCellHeight(50);
		listVendingMachine.setSelectedIndex(0);
		numOfSelected = listVendingMachine.getSelectedIndices();

		JScrollPane listOfMachineScroll = new JScrollPane(listVendingMachine);

		JPanel southPanel = new JPanel(new GridLayout(3, 1));
		JPanel southButtonPanel = new JPanel(new GridLayout(1, 2));
		JPanel southTextPanel = new JPanel(new FlowLayout());

		JLabel textLabel = new JLabel("ID's: ");
		final JTextField idNumbers = new JTextField();
		idNumbers.setPreferredSize(new Dimension(200, 50));
		southTextPanel.add(textLabel);
		southTextPanel.add(idNumbers);

		final JButton updateButton = new JButton("Update");
		JButton selectButton = new JButton("Select All");
		JButton deselectButton = new JButton("Deselect All");
		southButtonPanel.add(deselectButton);
		southButtonPanel.add(selectButton);
		southPanel.add(southTextPanel);
		southPanel.add(southButtonPanel);
		southPanel.add(updateButton);

		mainLeftSide.add(heading, BorderLayout.NORTH);
		mainLeftSide.add(listOfMachineScroll, BorderLayout.CENTER);
		mainLeftSide.add(southPanel, BorderLayout.SOUTH);

		JTabbedPane tabbedPane = new JTabbedPane();

		final JComponent panel1 = localVendListStockPanel();
		JScrollPane scrollPanel1 = new JScrollPane(panel1);
		tabbedPane.addTab("List Stock", scrollPanel1);
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_5);

		// final JComponent panel4 = machineChart(curID+1);

		// tabbedPane.addTab("", panel4);

		final JComponent panel2 = localVendRestockerPanel();
		JScrollPane scrollPanel2 = new JScrollPane(panel2);
		tabbedPane.addTab("Restocker", scrollPanel2);
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

		final JComponent panel3 = new JPanel();
		localVendMarketingPanel((JPanel) panel3);
		JScrollPane scrollPanel3 = new JScrollPane(panel3);
		tabbedPane.addTab("Marketing", panel3);
		tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);

		final JComponent panel4 = machineChart(numOfSelected);
		JScrollPane scrollPanel4 = new JScrollPane(panel4);
		tabbedPane.addTab("Item %", scrollPanel4);
		tabbedPane.setMnemonicAt(3, KeyEvent.VK_4);

		final JComponent panelGross = machineGrossChart(numOfSelected);
		JScrollPane scrollpanelGross = new JScrollPane(panelGross);
		tabbedPane.addTab("Item Gross", scrollpanelGross);
		tabbedPane.setMnemonicAt(4, KeyEvent.VK_5);

		final JComponent panel5 = machineItemChart(numOfSelected);
		JScrollPane scrollPanel5 = new JScrollPane(panel5);
		tabbedPane.addTab("% Reasons Item Sold", scrollPanel5);
		tabbedPane.setMnemonicAt(5, KeyEvent.VK_6);

		final JComponent panelIncome = makeIncomeGraph(numOfSelected);
		panelIncome.setRequestFocusEnabled(true);
		JScrollPane scrollpanelIncome = new JScrollPane(panelIncome);
		tabbedPane.addTab("Income Graph", scrollpanelIncome);
		tabbedPane.setMnemonicAt(6, KeyEvent.VK_7);

		mainLayout.add(mainLeftSide, BorderLayout.WEST);
		mainLayout.add(tabbedPane, BorderLayout.CENTER);

		SwingUtilities.updateComponentTreeUI(this);

		listVendingMachine.setSelectionModel(new DefaultListSelectionModel()
		{
			private static final long serialVersionUID = 1L;

			boolean gestureStarted = false;

			@Override
			public void setSelectionInterval(int index0, int index1)
			{
				if (!gestureStarted)
				{
					if (isSelectedIndex(index0))
					{
						super.removeSelectionInterval(index0, index1);
					}
					else
					{
						super.addSelectionInterval(index0, index1);
					}
				}
				gestureStarted = true;
			}

			@Override
			public void setValueIsAdjusting(boolean isAdjusting)
			{
				if (isAdjusting == false)
				{
					gestureStarted = false;
				}
			}

		});

		listVendingMachine.addListSelectionListener(new ListSelectionListener()
		{

			@Override
			public void valueChanged(ListSelectionEvent e)
			{
				// TODO Auto-generated method stub
				numOfSelected = listVendingMachine.getSelectedIndices();
				ArrayList<Integer> addOns = new ArrayList<Integer>();
				if (numOfSelected.length != 0)
				{
					String temp = "";
					for (int i = 0; i < numOfSelected.length; ++i)
					{
						if (i != (numOfSelected.length - 1))
						{
							if (numOfSelected[i] + 1 == numOfSelected[i + 1])
							{
								addOns.add(numOfSelected[i]);
							}
							else
							{
								if (addOns.size() == 0)
								{
									temp += (numOfSelected[i] + 1) + ",";
								}
								else
								{
									temp += (addOns.get(0) + 1) + "-"
											+ (numOfSelected[i] + 1) + ",";
									addOns = new ArrayList<Integer>();
								}
							}
						}
						else
						{
							if (addOns.size() == 0)
							{
								temp += (numOfSelected[i] + 1) + ",";
							}
							else
							{
								temp += (addOns.get(0) + 1) + "-"
										+ (numOfSelected[i] + 1) + ",";
								addOns = new ArrayList<Integer>();
							}
						}
						// temp += (numOfSelected[i] + 1) + ",";

					}
					temp = temp.substring(0, temp.length() - 1);
					idNumbers.setText(temp);
				}
			}
		});

		idNumbers.addKeyListener(new KeyAdapter()
		{
			public void keyTyped(KeyEvent e)
			{
				char c = e.getKeyChar();
				char last = ' ';

				if (idNumbers.getText().length() >= 1)
					last = idNumbers.getText().charAt(
							idNumbers.getText().length() - 1);

				int lastDash = idNumbers.getText().lastIndexOf('-');
				int lastComma = idNumbers.getText().lastIndexOf(',');
				if ((last == ' ' && (c == '-' || c == ','))
						|| (c == '-' && (lastComma - lastDash) < 0)
						|| (last == '-' && (c == '-' || c == ','))
						|| (last == ',' && (c == ',' || c == '-'))
						|| (!Character.isDigit(c) && c != '-' && c != ',')
						&& (c != KeyEvent.VK_BACK_SPACE))
				{
					e.consume(); // ignore event
				}
			}
		});

		selectButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				listVendingMachine
						.addSelectionInterval(0, numberOfMachines - 1);

				// TODO Auto-generated method stub

			}
		});
		deselectButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				idNumbers.setText("");
				listVendingMachine.removeSelectionInterval(0,
						numberOfMachines - 1);

			}
		});

		updateButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				listVendingMachine.removeSelectionInterval(0,
						numberOfMachines - 1);
				String numbers = idNumbers.getText();
				String[] splitNumbers = numbers.split(",");

				for (int i = 0; i < splitNumbers.length; ++i)
				{

					if (!splitNumbers[i].equals(""))
					{
						if (!splitNumbers[i].contains("-"))
						{
							int num = Integer.valueOf(splitNumbers[i]) - 1;
							if (num < 0)
							{
								num = 0;
							}
							if (num > numberOfMachines - 1)
							{
								num = numberOfMachines - 1;
							}

							listVendingMachine.addSelectionInterval(num, num);
						}
						else
						{
							String[] temp = splitNumbers[i].split("-");
							if (!temp[0].equals("") && !temp[1].equals(""))
							{
								int num1 = Integer.valueOf(temp[0]) - 1;
								int num2 = Integer.valueOf(temp[1]) - 1;
								if (num1 < 0)
								{
									num1 = 0;
								}
								if (num2 > numberOfMachines - 1)
								{
									num2 = numberOfMachines - 1;
								}
								listVendingMachine.addSelectionInterval(num1,
										num2);

							}
		
						}

					}
				}

				numOfSelected = listVendingMachine.getSelectedIndices();
				if (numOfSelected.length != 0)
				{

					panel1.removeAll();
					panel2.removeAll();
					panel3.removeAll();
					panel4.removeAll();
					panel5.removeAll();
					panelGross.removeAll();
					panelIncome.removeAll();

					panel1.add(localVendListStockPanel());
					panel2.add(localVendRestockerPanel());
					localVendMarketingPanel((JPanel) panel3);
					panel4.add(machineChart(numOfSelected));
					panel5.add(machineItemChart(numOfSelected));
					panelGross.add(machineGrossChart(numOfSelected));
					panelIncome.add(makeIncomeGraph(numOfSelected));

					panel1.revalidate();
					panel1.repaint();
					panel2.revalidate();
					panel2.repaint();
					panel3.revalidate();
					panel3.repaint();
					panel4.revalidate();
					panel4.repaint();
					panel5.revalidate();
					panel5.repaint();
					panelGross.revalidate();
					panelGross.repaint();
					panelIncome.revalidate();
					panelIncome.repaint();
				}
				else
				{
					panel1.removeAll();
					panel2.removeAll();
					panel3.removeAll();
					panel4.removeAll();
					panel5.removeAll();
					panelGross.removeAll();
					panelIncome.removeAll();

					panel1.setLayout(new FlowLayout());
					panel2.setLayout(new FlowLayout());
					panel3.setLayout(new FlowLayout());
					panel4.setLayout(new FlowLayout());
					panel5.setLayout(new FlowLayout());
					panelGross.setLayout(new FlowLayout());
					panelIncome.setLayout(new FlowLayout());

					panel1.add(returnEmptyPanelInfo());
					panel2.add(returnEmptyPanelInfo());
					panel3.add(returnEmptyPanelInfo());
					panel4.add(returnEmptyPanelInfo());
					panel5.add(returnEmptyPanelInfo());
					panelGross.add(returnEmptyPanelInfo());
					panelIncome.add(returnEmptyPanelInfo());

					panel1.revalidate();
					panel1.repaint();
					panel2.revalidate();
					panel2.repaint();
					panel3.revalidate();
					panel3.repaint();
					panel4.revalidate();
					panel4.repaint();
					panel5.revalidate();
					panel5.repaint();
					panelGross.revalidate();
					panelGross.repaint();
					panelIncome.revalidate();
					panelIncome.repaint();

				}
			}
		});

	}

	/**
	 * @param marketPanel
	 * @return
	 * @return
	 */
	private void localVendMarketingPanel(final JPanel marketPanel)
	{
		// TODO Auto-generated method stub

		marketPanel.setLayout(new GridLayout(2, 1, 20, 20));
		JButton viewButton = new JButton("View Selected Note(s)");
		viewButton.setFont(new Font("Verdana", Font.BOLD, 30));
		JButton editButton = new JButton("Edit Selected Note(s)");
		editButton.setFont(new Font("Verdana", Font.BOLD, 30));
		marketPanel.add(viewButton);
		marketPanel.add(editButton);

		viewButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				marketPanel.removeAll();
				viewMarketingPanel(marketPanel);
				marketPanel.revalidate();
				marketPanel.repaint();
			}
		});

		editButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				marketPanel.removeAll();
				if (numOfSelected.length == 1)
				{
					editMarketingPanel(marketPanel,numOfSelected[0]);
				}
				else
				{
					multipleEditMarketingPanel(marketPanel);
				}
				marketPanel.revalidate();
				marketPanel.repaint();
			}

		});

	}

	/**
	 * @return
	 */
	private JPanel multipleEditMarketingPanel(final JPanel marketPanel)
	{

		BorderLayout marketNoteLayout = new BorderLayout();
		marketNoteLayout.setVgap(10);
		marketPanel.setLayout(marketNoteLayout);

		JLabel noteDateLabel = new JLabel("Note Date: ");
		noteDateLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		final JLabel noteDate = new JLabel("Multiple");// getNoteDate(2));
		noteDate.setFont(new Font("Tahoma", Font.PLAIN, 20));
		JLabel machineIDLabel = new JLabel("Machine ID: ");
		machineIDLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		JLabel machineID = new JLabel("Multiple");
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

		// text field for the restocker note
		JLabel noteLabel = new JLabel("Note:  ");
		noteLabel.setFont(new Font("Tahoma", Font.BOLD, 20));

		final JTextArea marketNote = new JTextArea();

		marketNote.setFont(new Font("Arial", Font.PLAIN, 14));
		JScrollPane scrollText = new JScrollPane(marketNote);
		// buttons for the restocker note interface
		final JButton save = new JButton("Append to Note");
		;
		final JLabel info = new JLabel("No changes made");
		final JButton back = new JButton("Back");

		info.setVisible(false);

		save.setEnabled(false);

		// panels for restocker note
		JPanel buttonFlow = new JPanel(new FlowLayout());

		JPanel reNoteFlow = new JPanel(new BorderLayout(0, 20));
		// adds to the flow layouts for centering
		buttonFlow.add(save);
		buttonFlow.add(info);
		buttonFlow.add(back);
		reNoteFlow.add(noteLabel, BorderLayout.NORTH);
		reNoteFlow.add(scrollText, BorderLayout.CENTER);
		// adds to the main layout
		marketPanel.add(topGrid, BorderLayout.NORTH);
		marketPanel.add(reNoteFlow, BorderLayout.CENTER);
		marketPanel.add(buttonFlow, BorderLayout.SOUTH);

		marketNote.addCaretListener(new CaretListener()
		{

			@Override
			public void caretUpdate(CaretEvent e)
			{
				// TODO Auto-generated method stub

				if (marketNote.getText().trim() != "")
				{
					save.setEnabled(true);
				}
				else
				{
					save.setEnabled(false);
				}

			}
		});
		back.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				marketPanel.removeAll();
				localVendMarketingPanel(marketPanel);
				marketPanel.revalidate();
				marketPanel.repaint();
			}
		});
		save.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				try
				{

					changeMarketingNote(marketNote.getText());
					marketNote.setText("");
					noteDate.setText(getNoteDate(2, numOfSelected[0]));
					info.setText("Appending Successful");

				}
				catch (NegativeInputException | NullDateException e)
				{
					System.err
							.println("Error. Changing Marketing Note Failed.");
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		});

		return marketPanel;
	}

	public JLabel returnEmptyPanelInfo()
	{
		JLabel noSelection = new JLabel("<html><center><br><br><br>"
				+ "NO VENDING MACINE<br> WAS SELECTED" + "<center></html>");
		noSelection.setFont(new Font("Tahoma", Font.BOLD, 40));
		return noSelection;
	}

	private JPanel localVendRestockerPanel()
	{
		// removes everything from the main layout
		JPanel mainRestockPanel = new JPanel();
		mainRestockPanel.setLayout(new BoxLayout(mainRestockPanel,
				BoxLayout.Y_AXIS));
		JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		headerPanel.setPreferredSize(new Dimension(600, 60));
		JLabel header = new JLabel("RESTOCKER NOTE");
		header.setFont(new Font("Verdana", Font.BOLD, 25));
		headerPanel.add(header);
		mainRestockPanel.add(headerPanel);

		for (int i = 0; i < numOfSelected.length; ++i)
		{
			int ID = numOfSelected[i];
			JPanel restockPanel = new JPanel();
			BorderLayout restockNoteLayout = new BorderLayout();
			restockNoteLayout.setVgap(10);
			restockPanel.setLayout(restockNoteLayout);

			JLabel noteDateLabel = new JLabel("Note Date: ");
			noteDateLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
			JLabel noteDate = new JLabel(getNoteDate(1, ID));
			noteDate.setFont(new Font("Tahoma", Font.PLAIN, 20));
			JLabel machineIDLabel = new JLabel("Machine ID: ");
			machineIDLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
			JLabel machineID = new JLabel(String.valueOf(ID + 1));
			machineID.setVerticalTextPosition(SwingConstants.BOTTOM);
			machineID.setFont(new Font("Tahoma", Font.PLAIN, 20));

			JPanel dateFlow = new JPanel(new FlowLayout());
			JPanel idFlow = new JPanel(new FlowLayout());
			JPanel dateIdGrid = new JPanel(new GridLayout(1, 2));

			dateFlow.add(noteDateLabel);
			dateFlow.add(noteDate);
			idFlow.add(machineIDLabel);
			idFlow.add(machineID);

			dateIdGrid.add(dateFlow);
			dateIdGrid.add(idFlow);

			// topFlow.add(topGrid);
			JPanel notePanel = new JPanel(new FlowLayout());
			JLabel noteLabel = new JLabel("Note:  ");
			noteLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
			noteLabel.setPreferredSize(new Dimension(600, 30));
			notePanel.add(noteLabel);
			final JTextArea restockerNote = new JTextArea(getRestockerNote(ID));
			restockerNote.setFont(new Font("Arial", Font.PLAIN, 14));
			restockerNote.setEditable(false);
			restockerNote.setPreferredSize(new Dimension(600, 200));
			JScrollPane scrollText = new JScrollPane(restockerNote);
			JPanel scrollPanel = new JPanel(new FlowLayout());
			scrollPanel.add(scrollText);

			// panels for restocker note

			JPanel reNoteFlow = new JPanel(new BorderLayout(0, 20));
			// adds to the flow layouts for centering

			reNoteFlow.add(notePanel, BorderLayout.NORTH);
			reNoteFlow.add(scrollPanel, BorderLayout.CENTER);
			// adds to the main layout
			restockPanel.add(dateIdGrid, BorderLayout.NORTH);
			restockPanel.add(reNoteFlow, BorderLayout.CENTER);
			restockPanel.setPreferredSize(new Dimension(400, 400));
			mainRestockPanel.add(restockPanel);
			mainRestockPanel.add(Box.createVerticalGlue());
		}

		return mainRestockPanel;

	}

	private JPanel viewMarketingPanel(final JPanel mainPanel)
	{
		// removes everything from the main layout
		mainPanel.setLayout(new BorderLayout());
		JPanel mainmarketPanel = new JPanel();
		mainmarketPanel.setLayout(new BoxLayout(mainmarketPanel,
				BoxLayout.Y_AXIS));
		JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		headerPanel.setPreferredSize(new Dimension(600, 60));
		JLabel header = new JLabel("MARKETING NOTE");
		header.setFont(new Font("Verdana", Font.BOLD, 25));
		headerPanel.add(header);
		mainmarketPanel.add(headerPanel);

		for (int i = 0; i < numOfSelected.length; ++i)
		{
			int ID = numOfSelected[i];
			JPanel marketPanel = new JPanel();
			BorderLayout marketNoteLayout = new BorderLayout();
			marketNoteLayout.setVgap(10);
			marketPanel.setLayout(marketNoteLayout);

			JLabel noteDateLabel = new JLabel("Note Date: ");
			noteDateLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
			JLabel noteDate = new JLabel(getNoteDate(2, ID));
			noteDate.setFont(new Font("Tahoma", Font.PLAIN, 20));
			JLabel machineIDLabel = new JLabel("Machine ID: ");
			machineIDLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
			JLabel machineID = new JLabel(String.valueOf(ID + 1));
			machineID.setVerticalTextPosition(SwingConstants.BOTTOM);
			machineID.setFont(new Font("Tahoma", Font.PLAIN, 20));

			JPanel dateFlow = new JPanel(new FlowLayout());
			JPanel idFlow = new JPanel(new FlowLayout());
			JPanel dateIdGrid = new JPanel(new GridLayout(1, 2));

			dateFlow.add(noteDateLabel);
			dateFlow.add(noteDate);
			idFlow.add(machineIDLabel);
			idFlow.add(machineID);

			dateIdGrid.add(dateFlow);
			dateIdGrid.add(idFlow);

			// topFlow.add(topGrid);
			JPanel notePanel = new JPanel(new FlowLayout());
			JLabel noteLabel = new JLabel("Note:  ");
			noteLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
			noteLabel.setPreferredSize(new Dimension(600, 30));
			notePanel.add(noteLabel);
			final JTextArea marketerNote = new JTextArea(getMarketingNote(ID));
			marketerNote.setFont(new Font("Arial", Font.PLAIN, 14));
			marketerNote.setEditable(false);
			marketerNote.setPreferredSize(new Dimension(600, 200));
			JScrollPane scrollText = new JScrollPane(marketerNote);
			JPanel scrollPanel = new JPanel(new FlowLayout());
			scrollPanel.add(scrollText);

			// panels for marketer note

			JPanel reNoteFlow = new JPanel(new BorderLayout(0, 20));
			// adds to the flow layouts for centering

			reNoteFlow.add(notePanel, BorderLayout.NORTH);
			reNoteFlow.add(scrollPanel, BorderLayout.CENTER);
			// adds to the main layout
			marketPanel.add(dateIdGrid, BorderLayout.NORTH);
			marketPanel.add(reNoteFlow, BorderLayout.CENTER);
			// marketPanel.setPreferredSize(new Dimension(400, 400));
			mainmarketPanel.add(marketPanel);
			// mainmarketPanel.add(Box.createVerticalGlue());
		}
		JScrollPane sPane = new JScrollPane(mainmarketPanel);
		mainPanel.add(sPane, BorderLayout.CENTER);
		// JPanel buttonPanel = new JPanel(new FlowLayout());
		JButton backButton = new JButton("Back");
		backButton.setPreferredSize(new Dimension(10, 50));
		backButton.setFont(new Font("Arial", Font.PLAIN, 18));
		// buttonPanel.add(backButton);
		mainPanel.add(backButton, BorderLayout.SOUTH);

		backButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				mainPanel.removeAll();
				// mainmarketPanel.setLayout( new GridLayout());
				localVendMarketingPanel(mainPanel);
				mainPanel.revalidate();
				mainPanel.repaint();
			}
		});
		return mainPanel;

	}

	/**
	 * Gets the date of when the note was edited.
	 * 
	 * @param flag
	 * @return The date of the note waas edited.
	 */
	private String getNoteDate(int flag, int ID)
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
	 * gets the restocker note from the manager
	 * 
	 * @return note the restocker note for the vending machine
	 */
	private String getRestockerNote(int ID)
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

	private JPanel editMarketingPanel(final JPanel marketPanel, int ID)
	{


		BorderLayout marketNoteLayout = new BorderLayout();
		marketNoteLayout.setVgap(10);
		marketPanel.setLayout(marketNoteLayout);

		JLabel noteDateLabel = new JLabel("Note Date: ");
		noteDateLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		final JLabel noteDate = new JLabel(getNoteDate(2, ID));
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

		// text field for the restocker note
		JLabel noteLabel = new JLabel("Note:  ");
		noteLabel.setFont(new Font("Tahoma", Font.BOLD, 20));

		final JTextArea marketNote = new JTextArea(
				getMarketingNote(numOfSelected[0]));

		marketNote.setFont(new Font("Arial", Font.PLAIN, 14));
		JScrollPane scrollText = new JScrollPane(marketNote);
		// buttons for the restocker note interface
		final JButton save = new JButton("Save Note");
		final JButton back = new JButton("Back");
		final JLabel info = new JLabel("No changes made");

		info.setVisible(true);

		save.setEnabled(false);

		// panels for restocker note
		JPanel buttonFlow = new JPanel(new FlowLayout());

		JPanel reNoteFlow = new JPanel(new BorderLayout(0, 20));
		// adds to the flow layouts for centering
		buttonFlow.add(save);
		buttonFlow.add(info);
		buttonFlow.add(back);
		reNoteFlow.add(noteLabel, BorderLayout.NORTH);
		reNoteFlow.add(scrollText, BorderLayout.CENTER);
		// adds to the main layout
		marketPanel.add(topGrid, BorderLayout.NORTH);
		marketPanel.add(reNoteFlow, BorderLayout.CENTER);
		marketPanel.add(buttonFlow, BorderLayout.SOUTH);

		marketNote.addCaretListener(new CaretListener()
		{

			@Override
			public void caretUpdate(CaretEvent e)
			{

				String databaseText = getDatabaseText(marketNote.getText());
				// System.out.println(databaseText);
				if (databaseText.equals(getMarketingNote(numOfSelected[0])))
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

		back.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				marketPanel.removeAll();
				localVendMarketingPanel(marketPanel);
				marketPanel.revalidate();
				marketPanel.repaint();

			}
		});
		save.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				try
				{

					changeMarketingNote(marketNote.getText());
					noteDate.setText(getNoteDate(2, numOfSelected[0]));
					marketNote.setText(getMarketingNote(numOfSelected[0]));
					info.setText("Changes Saved");

				}
				catch (NegativeInputException | NullDateException e)
				{
					System.err
							.println("Error. Changing Marketing Note Failed.");
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		});

		return marketPanel;

	}

	/**
	 * @param text
	 * @return text in the database
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
	 * Makes the chart and places it inside a jpanel to update the GUI of
	 * machines available.
	 * 
	 * @param IDS
	 *            - Array of ints to display.
	 * @return A jpanel with the chart.
	 */
	private JPanel machineChart(int[] IDS)
	{

		AnalyticsEngine engine;
		ChartPanel panel1;
		engine = new AnalyticsEngine(IDS, manager.localVends.size());
		panel1 = new ChartPanel(engine.globalPopularItem());
		JPanel p1 = new JPanel(new FlowLayout());
		p1.add(panel1);
		return p1;

	}

	/**
	 * Makes the chart and places it inside a jpanel to update the GUI of the
	 * gross sales made.
	 * 
	 * @param An
	 *            array of ints to display.
	 * @return A jpanel with the chart.
	 */
	private JPanel machineGrossChart(int[] IDS)
	{
		AnalyticsEngine engine = new AnalyticsEngine(IDS,
				manager.localVends.size());
		ChartPanel panel1 = new ChartPanel(
				engine.generateMachineGrossPieChart());
		JPanel p1 = new JPanel(new FlowLayout());
		p1.add(panel1);
		return p1;
	}

	/**
	 * Makes the chart and places it inside a jpanel to update the GUI with the
	 * amount of items in machines.
	 * 
	 * @param An
	 *            array of ints to display.
	 * @return A jpanel with the chart.
	 */
	private JPanel machineItemChart(int[] IDS)
	{

		AnalyticsEngine tempEngine;
		final AnalyticsEngine engine = new AnalyticsEngine(IDS,
				manager.localVends.size());
		Set<String> differentNames = engine.getDifferentNames();
		String[] itemNames = new String[differentNames.size()];
		int i = 0;
		for (String itemName : differentNames)
		{
			itemNames[i] = itemName;
			++i;
		}

		final JList<String> jListItemData = new JList<String>(itemNames);
		jListItemData.setFont(new Font("Arial", Font.PLAIN, 20));
		jListItemData.setFixedCellHeight(50);
		jListItemData.setSelectedIndex(0);
		jListItemData.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JPanel itemPanel = new JPanel(new BorderLayout(0, 20));

		JLabel heading = new JLabel(
				"<html><center>List of Items in <br>Machine</center></html>");
		JScrollPane itemScroll = new JScrollPane(jListItemData);
		heading.setFont(new Font("Verdana", Font.BOLD, 20));

		itemPanel.add(heading, BorderLayout.NORTH);
		itemPanel.add(itemScroll, BorderLayout.CENTER);

		ChartPanel chartPanel = new ChartPanel(
				engine.generateItemPieChart(jListItemData.getSelectedValue()));
		final JPanel chartPanelwork = new JPanel();// new FlowLayout());
		chartPanelwork.add(chartPanel);

		JPanel jpanelMain = new JPanel(new BorderLayout(2, 20));

		jpanelMain.add(itemPanel, BorderLayout.WEST);
		jpanelMain.add(chartPanelwork, BorderLayout.CENTER);

		jListItemData.addListSelectionListener(new ListSelectionListener()
		{
			@Override
			public void valueChanged(ListSelectionEvent e)
			{
				chartPanelwork.removeAll();

				chartPanelwork.add(new ChartPanel(engine
						.generateItemPieChart(jListItemData.getSelectedValue())));
				chartPanelwork.revalidate();
				chartPanelwork.repaint();
			}

		});

		return jpanelMain;
	}

	/**
	 * A jpanel with the chart to update the GUI of the amount of income made by
	 * the machines.
	 * 
	 * @param IDS
	 *            - The integers to display.
	 * @return A jpanel with the chart inside.
	 */
	private JPanel makeIncomeGraph(int[] IDS)
	{
		AnalyticsEngine engine = new AnalyticsEngine(IDS,
				manager.localVends.size());
		ChartPanel panel1 = new ChartPanel(engine.generateIncomeGraph());
		panel1.setFillZoomRectangle(true);
		JPanel p1 = new JPanel();
		p1.add(panel1);
		return p1;
	}

	private JPanel makeGraph()
	{
		return null;

	}

	private JPanel localVendListStockPanel()
	{
		JPanel mainListPanel = new JPanel();
		mainListPanel.setLayout(new BoxLayout(mainListPanel, BoxLayout.Y_AXIS));
		JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		headerPanel.setPreferredSize(new Dimension(600, 60));
		JLabel header = new JLabel("LIST STOCK");
		header.setFont(new Font("Verdana", Font.BOLD, 25));
		headerPanel.add(header);
		mainListPanel.add(headerPanel);

		for (int j = 0; j < numOfSelected.length; ++j)
		{
			int ID = numOfSelected[j];

			JPanel stockPanel = new JPanel();

			BorderLayout listItemsLayout = new BorderLayout(0, 20);
			stockPanel.setLayout(listItemsLayout);
			Object[] columnHeading = { "Row", "Column", "Item Name", "Amount",
					"Price", "Total" };
			try
			{
				itemsArray = getStock(ID);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				System.out.println("Failed to populate itemsArray...");
				System.out.println("Quitting the Marketing manager...");
				System.exit(DISPOSE_ON_CLOSE);
			}
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
			// tableModel.setRowCount(itemsArray.length);

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
			JPanel stockScrollPanel = new JPanel(new FlowLayout());
			// stockTable.setPreferredSize(new Dimension(800, 400));
			// stockScrollPanel.setPreferredSize(new Dimension(800, 600));
			stockScrollPanel.add(scrollStock);
			int row = stockTable.getRowCount();
			double totalPrice = 0;
			int totalAmount = 0;
			for (int i = 0; i < row; i++)
			{
				totalPrice += (Double) stockTable.getValueAt(i, 5);
				totalAmount += (Integer) stockTable.getValueAt(i, 3);
			}

			// String totalPriceResult = new Double(totalPrice).toString();
			String totalAmountResult = new Integer(totalAmount).toString();

			JLabel totalPriceLabel = new JLabel("Total Price in Stock     ");
			totalPriceLabel.setFont(new Font("Verdana", Font.BOLD, 18));
			JLabel totalPriceText = new JLabel(df.format(totalPrice));
			totalPriceText.setFont(new Font("Arial", Font.PLAIN, 16));

			JLabel totalAmountLabel = new JLabel("Total Amount in Stock    ");
			totalAmountLabel.setFont(new Font("Verdana", Font.BOLD, 18));
			JLabel totalAmountText = new JLabel(totalAmountResult);
			totalAmountText.setFont(new Font("Arial", Font.PLAIN, 16));

			JPanel southLayout = new JPanel(new GridLayout(1, 1));
			JPanel priceLayout = new JPanel(new FlowLayout());
			JPanel amountLayout = new JPanel(new FlowLayout());

			priceLayout.add(totalPriceLabel);
			priceLayout.add(totalPriceText);

			amountLayout.add(totalAmountLabel);
			amountLayout.add(totalAmountText);

			southLayout.add(amountLayout);
			southLayout.add(priceLayout);

			JLabel northLabel = new JLabel("MACHINE " + (ID + 1));
			northLabel.setHorizontalAlignment(SwingConstants.CENTER);
			northLabel.setFont(new Font("Verdana", Font.BOLD, 25));

			stockPanel.add(northLabel, BorderLayout.NORTH);
			stockPanel.add(stockScrollPanel, BorderLayout.CENTER);
			stockPanel.add(southLayout, BorderLayout.SOUTH);

			mainListPanel.add(stockPanel);

		}
		return mainListPanel;
	}

	/**
	 * Edits the marketing note on a machine.
	 * 
	 * @param The
	 *            new note to place in the machine.
	 */
	private void changeMarketingNote(String newNote)
			throws NegativeInputException, NullDateException
	{
		// String word = "";
		// String rest = "";
		// if (newNote.contains(" "))
		// {
		// int index = newNote.indexOf(' ');
		// word = newNote.substring(0, index);
		// rest = newNote.substring(index + 2);
		// }
		// // System.out.println(newNote);
		// if (word.equals("Notes"))
		// {
		String temp[] = newNote.trim().split("\\n");
		String all = "";
		for (int i = 0; i < temp.length; ++i)
		{
			String tempString = temp[i].trim();
			if (!tempString.equals(""))
			{
				all += temp[i].trim() + "|";
			}

			// System.out.println(temp[i]);
		}

		if (numOfSelected.length == 1)
		{
			manager.editMarketingNote(numOfSelected[0], all,
					Calendar.getInstance());
		}
		else
		{
			for (int i = 0; i < numOfSelected.length; ++i)
			{
				int ID = numOfSelected[i];
				String temp1[] = getMarketingNote(ID).trim().split("\\n");
				String databaseText = "";
				for (int i1 = 0; i1 < temp1.length; ++i1)
				{
					String tempString = temp1[i1].trim();
					if (!tempString.equals(""))
					{
						databaseText += temp1[i1].trim() + "|";
					}
				}
				// System.out.println(all);

				databaseText += all;

				manager.editMarketingNote(ID, databaseText,
						Calendar.getInstance());
			}
		}
		// }
		// else
		// {
		// manager.editMarketingNote(ID, newNote, Calendar.getInstance());
		// }

	}

	/**
	 * gets the marketing note from the manager
	 * 
	 * @return note returns the marketing note for the vending machine
	 */
	private String getMarketingNote(int ID)
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

	private Object[][] getStock(int ID) throws OutOfBoundsStockException
	{

		thisVend = manager.getVend(ID);
		Object stock[][] = new Object[thisVend.getTotalStock().size()][6];
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
			stock[i][5] = (int) stock[i][3] * (double) stock[i][4];
			++i;

		}

		sort2DArray(stock);
		return stock;
	}

	/**
	 * Sorts the 2d array of items in the stock
	 * 
	 * @param stock
	 *            - The stock of the machine to sort.
	 */
	private void sort2DArray(Object[][] stock)
	{
		Arrays.sort(stock, new Comparator<Object[]>()
		{

			@Override
			public int compare(Object[] first, Object[] second)
			{
				// TODO Auto-generated method stub
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

	@Override
	public void update(Observable arg, Object subject)
	{
		if (arg instanceof LocalVend)
		{

			thisVend = (LocalVend) subject;

		}
	}

}
