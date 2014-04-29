/*
 * FancyButton.java File: $ID$
 * 
 * Revisions: $Log$
 */
package edu.rit.se.coolTeamB.miscellaneous;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * @author SAMMYTECH
 * 
 */
public class FancyButton extends JButton implements ActionListener
{

	private int width;
	private int height;
	private String path;
	private String text = "";
	@SuppressWarnings("rawtypes")
	private Class cla;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor to make FancyButton
	 * 
	 * @param cla
	 *            The class to which the Button are being placed
	 * @param path
	 *            The path of the the image of the Button
	 * @param width
	 *            The width of the Button;
	 * @param height
	 *            The height of the Button;
	 */
	@SuppressWarnings("rawtypes")
	public FancyButton(Class cla, String path, int width, int height)
	{
		// TODO Auto-generated constructor stub
		this.cla = cla;
		this.path = path;
		this.width = width;
		this.height = height;

	}

	/**
	 * Constructor to make FancyButton
	 * 
	 * @param cla
	 *            The class to which the Button are being placed
	 * @param path
	 *            The path of the the image of the Button
	 * @param width
	 *            The width of the Button;
	 * @param height
	 *            The height of the Button;
	 */
	@SuppressWarnings("rawtypes")
	public FancyButton(Class cla, String path, int width, int height,
			String text)
	{
		// TODO Auto-generated constructor stub
		this.cla = cla;
		this.path = path;
		this.width = width;
		this.height = height;
		this.text = text;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		super.setIcon(createImageIcon(path, width, height));

		super.setFocusPainted(false);
		super.setMargin(new Insets(0, 0, 0, 0));
		super.setContentAreaFilled(false);
		super.setBorderPainted(false);
		super.setOpaque(false);

	}

	/**
	 * Gets the inputed text
	 * 
	 * @return the text from the constructor
	 */
	public String getCustomText()
	{
		return text;
	}

	/** Returns an ImageIcon, or null if the path was invalid. */
	private ImageIcon createImageIcon(String path, int width, int height)
	{
		java.net.URL imgURL = cla.getResource(path);
		if (imgURL != null)
		{
			ImageIcon icon = new ImageIcon(imgURL);
			Image img = icon.getImage();
			Image newimg = img.getScaledInstance(width, height,
					java.awt.Image.SCALE_SMOOTH);
			icon = new ImageIcon(newimg);

			return icon;
		}
		else
		{
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub

	}

}
