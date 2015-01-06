package de.gameoflife.gui;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import de.gameoflife.controllers.MainController;
import de.gameoflife.enums.Modus;
import de.gameoflife.models.Spezies;
import de.gameoflife.models.Spielfeld;

/**
 *	Startfenster
 */
public class InitFrame extends JFrame
{
	//Komponenten
	/** Torsus */
	private JRadioButton torsus;
	
	/** Begrenzt*/
	private JRadioButton begrenzt;
	
	/** Breite */
	private JTextField breite;
	
	/** Höhe */
	private JTextField hoehe;
	
	/** OK */
	private JButton ok;
	
	/** Beenden */
	private JButton esc;

	/** Container */
	private JPanel container;
	
	/** Referenz auf Controller */
	private MainController parent;	
	
	/**
	 * Erstellt neues Startfenster
	 * @param parent Referenz auf Controller
	 */
	public InitFrame(MainController parent)
	{
		super("Game of Life-Spielfeld");

		this.parent = parent;
		
		this.initComponents();
		this.initListeners();
		setVisible(true);
	}
	
	/**
	 * Initialisiert die Komponenten
	 */
	private void initComponents() 
	{
		this.container = new JPanel();
		container.setLayout(new GridLayout(0,2,10,10));
		torsus = new JRadioButton("Torsus",true);
		begrenzt = new JRadioButton("Begrenzt");
		
		JLabel breiteLabel = new JLabel ("Breite");
		breite = new JTextField();
		breiteLabel.setLabelFor(breite);
		JLabel hoeheLabel = new JLabel ("Hoehe");
		hoehe = new JTextField();
		hoeheLabel.setLabelFor(hoehe);

		//Group the radio buttons.
		ButtonGroup group = new ButtonGroup();
		group.add(torsus);
		group.add(begrenzt);

		// Button
		ok = new JButton("OK");
		esc = new JButton("Abbrechen");

		container.add(torsus);
		container.add(begrenzt);
		container.add(breiteLabel);
		container.add(breite);
		container.add(hoeheLabel);
		container.add(hoehe);

		container.add(ok);
		container.add(esc);
		getContentPane().add(this.container);
		pack();
	}

	/**
	 *	Initialisiert Listener 
	 */
	private void initListeners() 
	{
		Handler h = new Handler();
		torsus.addActionListener(h);
		begrenzt.addActionListener(h);
		ok.addActionListener(h);
		esc.addActionListener(h);
	}
	
	/**
	 * Click-Handler
	 */
	private class Handler implements ActionListener
	{
		@Override
		//Starte das Hauptfenster
		public void actionPerformed(ActionEvent e)
		{
			Modus modus = Modus.TORSUS;
			if (e.getSource() == ok)
			{
				int breiteInt = 0;
				int hoeheInt = 0;
				try
				{
					breiteInt = Integer.valueOf(breite.getText());
					hoeheInt = Integer.valueOf(hoehe.getText());
					if(breiteInt < 0 || hoeheInt < 0)
					{
						JOptionPane.showMessageDialog(null, "Ungueltige Werte", "Ungueltige Werte", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
				}
				catch (Exception ex)
				{
					JOptionPane.showMessageDialog(null, "Ungueltige Werte", "Ungueltige Werte", JOptionPane.INFORMATION_MESSAGE);	
					return;
				}
				Spezies.resetCounter();
				parent.setSpielfeld(new Spielfeld(breiteInt, hoeheInt,modus));
				parent.openMainFrame();
				dispose();
			}
			
			// Beendet Programm
			if (e.getSource() == esc)
			{
				dispose();
			}
			
			// Modus -> Begrenzt
			if(e.getSource()== torsus)
			{
				modus=Modus.TORSUS;
			}
			
			//Modus -> Begrenzt
			if(e.getSource()== begrenzt)
			{
				modus=Modus.BEGRENZT;
			}
		}
	}

}
