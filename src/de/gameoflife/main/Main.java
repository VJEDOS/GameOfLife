package de.gameoflife.main;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import de.gameoflife.controllers.MainController;
import de.gameoflife.gui.MainFrame;

/**
 * Die Hauptklasse des Projekts
 * @author Dominik Stegemann
 */
public class Main 
{
	public static void main(String[] args) 
	{
		try 
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new MainController();
	}
}