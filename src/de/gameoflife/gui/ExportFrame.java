package de.gameoflife.gui;

import java.text.NumberFormat;
import javax.swing.text.NumberFormatter;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import de.gameoflife.controllers.MainController;

/**
 * Das Fenster für den Export
 * @author Vethiha Jegatheesan
 *
 */
public class ExportFrame extends JFrame
{
	//Komponenten
	/** JPG */
	private JRadioButton jpg;
	
	/** GIF */
	private JRadioButton gif;
	
	/** Referenz auf Controller */
	private MainController parent;
	
	/** Breite */
	private JTextField breite;
	
	/** Höhe */
	private JTextField hoehe;
	
	/** Anzahl */
	private JTextField anzahl;
	
	/** OK */
	private JButton ok;
	
	/** Schließen */
	private JButton esc;
	
	/** Container */
	private JPanel container;

	/**
	 * Erstellt ein neues Exportframe
	 * @param parent Referenz auf Controller
	 */
	public ExportFrame(MainController parent)
	{
		super("Exportieren");
		this.parent = parent;
		this.initComponents();
		this.initListeners();
		setVisible(true);
	}
	
	/**
	 * Initialisiert Komponenten
	 */
	private void initComponents() {
		this.container = new JPanel();
		container.setLayout(new GridLayout(0,2,10,10));
		jpg = new JRadioButton("jpg",true);
		gif = new JRadioButton("gif");
		
		JLabel breiteLabel = new JLabel ("Breite");
		breiteLabel.setLabelFor(breite);
		
		JLabel hoeheLabel = new JLabel ("Hoehe");
		hoeheLabel.setLabelFor(hoehe);
		
		JLabel anzahlLabel = new JLabel ("Anzahl");
		anzahlLabel.setLabelFor(anzahl);
		
		breite = new JTextField();
		hoehe = new JTextField();
		anzahl = new JTextField();
		
		
		//Group the radio buttons.
		
		ButtonGroup group = new ButtonGroup();
		group.add(jpg);
		group.add(gif);
		this.ok = new JButton("OK");
		this.esc = new JButton("Abbrechen");

		container.add(jpg);
		container.add(gif);
		container.add(breiteLabel);
		container.add(breite);
		container.add(hoeheLabel);
		container.add(hoehe);
		container.add(anzahlLabel);
		container.add(anzahl);
		container.add(ok);
		container.add(esc);
		getContentPane().add(this.container);
		pack();
	}

	/**
	 * Initalisiert Listener
	 */
	private void initListeners() 
	{
		Handler h = new Handler();
		jpg.addActionListener(h);
		gif.addActionListener(h);
		ok.addActionListener(h);
		esc.addActionListener(h);
	}
	
	/**
	 * Klick Handler
	 */
	private class Handler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			// Exportieren
			if (e.getSource() == ok)
			{
				int breiteInt = 0;
				int hoeheInt = 0;
				int anzahlInt = 0;
				try
				{
					breiteInt = Integer.valueOf(breite.getText());
					hoeheInt = Integer.valueOf(hoehe.getText());
					if (gif.isSelected()) anzahlInt = Integer.valueOf(anzahl.getText());
					if(breiteInt < 0 || hoeheInt < 0  || anzahlInt < 0)
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
								
				if (breiteInt > 3794 || hoeheInt > 3794)
				{
					JOptionPane.showMessageDialog(null, "große Bildgrößen", "wählen Sie Bildgrößen,welche kleiner sind als 3795 aus", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				
				String path = "";
				JFileChooser chooser = new JFileChooser();
		        int rueckgabeWert = chooser.showOpenDialog(null);
		        if(rueckgabeWert == JFileChooser.APPROVE_OPTION)
		        {
		             path = chooser.getSelectedFile().getAbsolutePath();
		        }
		        else
		        {
		        	return;
		        }
				
				if (jpg.isSelected())
				{
					parent.bildExport(path, breiteInt, hoeheInt);
				}
				else
				{
					parent.starteGifExport(path, breiteInt, hoeheInt, 100, anzahlInt);
				}
				dispose();
			}
			
			//Schließen
			if (e.getSource() == esc)
			{
				dispose();
			}
			
			// Anzahl-Feld sperren
			if(e.getSource() == jpg)
			{
				anzahl.setEditable(false);
			}
			
			//Anzahl Feld öffnen
			if(e.getSource() == gif)
			{
				anzahl.setEditable(true);
			}
		}
	}
}