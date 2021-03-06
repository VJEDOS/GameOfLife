package de.gameoflife.gui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import de.gameoflife.controllers.MainController;
import de.gameoflife.models.Spezies;

/**
 * Hinzufügen- und Editierenfenster
 * @author ds
 *
 */
public class AddFrame extends JFrame
{

	/** Bearbeitet oder neu */
	private boolean neu = false;
	
	//Komponenten

	/** Geburt bei */
	private JSpinner geburtBei;
	
	/** Isolation */
	private JSpinner isolation;
	
	/** Maximum */
	private JSpinner max;
	
	/** Name Field */
	private JTextField textField;
	
	/** OK-Button */
	private JButton ok;
	
	/** Schließen */
	private JButton esc;
	
	/** Farbe */
	private JButton farbe;
	
	/** Container */
	private JPanel container;

	/** Ausgewählte Farbe */
	private Color color=Color.BLUE;
	
	/** Referenz auf Controller */
	private MainController parent;
	
	/** Bearbeitete Spezies */
	private Spezies s;
	
	/**
	 * Erstellt ein neues Fenster zum editieren
	 * @param s Spezies, welche editiert werden soll
	 * @param parent Referenz auf Controller
	 */
	public AddFrame(Spezies s, MainController parent)
	{
		super("Hinzufügen");
		this.s = s;
		this.initComponents();
		this.initListeners();
		setVisible(true);
		this.parent = parent;
	}

	/**
	 * Erstellt ein neues Fenster zum Hinzufügen einer Spezies
	 * @param parent Referenz auf Controller
	 */
	public AddFrame(MainController parent)
	{
		super("Hinzufügen");
		neu = true;
		this.initComponents();
		this.initListeners();
		setVisible(true);
		this.parent = parent;
	}

	/**
	 * Initialisiert die Komponenten
	 */
	private void initComponents()
	{
		//Container
		this.container = new JPanel();
		container.setLayout(new GridLayout(0,2,10,10)); // das 0,2 wird benötigt, damit sich das Layout korrekt verhält 
		//Button
		this.ok = new JButton("OK");
		this.esc = new JButton("Abbrechen");
		this.farbe = new JButton("Farbe");
		farbe.setBackground(color);
		JLabel l = new JLabel("Name:", JLabel.TRAILING);
		container.add(l);
		this.textField = new JTextField(10);
		textField.setEditable(false);
		if(!neu)
		{ 
			textField.setText(String.valueOf(s.getId())); 
		}
		else 
		{ 
			textField.setText(String.valueOf(Spezies.getCounter() + 1)); 
		}
		container.add(textField);
		
		//JSpinner
		JLabel la = new JLabel("Geburt bei: ", JLabel.TRAILING);
		SpinnerModel model_geb;
		if(!neu) 
		{ 
			model_geb = new SpinnerNumberModel(s.getGeburt(),1,8,1); 
		}
		else 
		{ 
			model_geb = new SpinnerNumberModel(4,1,8,1); 
		}
		this.geburtBei = new JSpinner(model_geb);
		la.setLabelFor(geburtBei);
		container.add(la);
		container.add(geburtBei);
		
		JLabel label_iso = new JLabel( "Isolation: ", JLabel.TRAILING);
		SpinnerModel model_iso;
		if(!neu) 
		{
			model_iso = new SpinnerNumberModel(s.getIsolation(),1,8,1); 
		}
		else 
		{ 
			model_iso = new SpinnerNumberModel(3,1,8,1); 
		}
		this.isolation = new JSpinner(model_iso);
		container.add(label_iso);
		container.add(isolation);
		
		JLabel label_max = new JLabel("Überbevölkerung: ", JLabel.TRAILING);
		SpinnerModel model_max;
		if (!neu) 
		{
			model_max =  new SpinnerNumberModel(s.getMaximum(),1,8,1); 
		}
		else 
		{ 
			model_max = new SpinnerNumberModel(4,1,8,1);
		}
		this.max = new JSpinner(model_max);
		container.add(label_max);
		container.add(max);
		
		// Container
		this.container.add(farbe);
		this.container.add(new JPanel());
		this.container.add(this.ok);
		this.container.add(this.esc);
		this.getContentPane().add(this.container);
		this.pack();
	}
	
	/**
	 * Initialisiert Listener
	 */
	private void initListeners()
	{
		Handler h = new Handler();
		this.ok.addActionListener(h);
		this.esc.addActionListener(h);
		this.farbe.addActionListener(h);
	}
	
	/**
	 *	Handler für Clicks
	 */
	private class Handler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			//Spezies neu erstellen bzw änderungen Speichern
			if (e.getSource() == ok)
			{
				Spezies temp = new Spezies((int)geburtBei.getValue(), (int)isolation.getValue(), (int)max.getValue(), color, true);
				try 
				{
					temp.check();	
				}
				catch (Exception e1) 
				{
					JOptionPane.showMessageDialog(null, "Isolation > Überbevölkerung", "Isolation > Überbevölkerung", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				if(!neu)
				{
					s.setGeburt((int)geburtBei.getValue());
					s.setMaximum((int)max.getValue());
					s.setIsolation((int) isolation.getValue());
					s.setFarbe(color);
				}
				else
				{
					s = new Spezies((int)geburtBei.getValue(), (int)isolation.getValue(), (int)max.getValue(), color, false);
					parent.getMainFrame().addListModel(s);
				}
				parent.getSpielfeld().addSpezies(s);
				dispose();
			}
			
			// Schließen
			if (e.getSource() == esc)
			{
				dispose();
			}
			
			// Aufruf Colorchooser
			if(e.getSource() == farbe)
			{
				color = JColorChooser.showDialog(null,"Farbe für die Spezie wählen",color);
				farbe.setBackground(color);
			}
		}
	}
}