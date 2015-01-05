package de.gameoflife.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.FocusTraversalPolicy;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.Collection;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import de.gameoflife.controllers.MainController;
import de.gameoflife.models.Spezies;
import de.gameoflife.models.Spielfeld;

/**
 * Das Hauptfenster
 * @author Vethiha Jegatheesan
 *
 */
public class MainFrame extends JFrame
{
	/** Referenz für Handler */
	private MainFrame frame = this;

	/** Toolkit */
	private Toolkit t;
	
	/** X-Pos */
	private int x = 0;
	
	/** Y-Pos */
	private int y = 0;
	
	/** Breite */
	private int width = 800;
	
	/** Höhe */
	private int height = 600;
	
	/** Container */
	private JPanel container;
	
	//Buttons
	
	/** Hinzufügen */
	private JButton add;

	/** Spielen */
	private JButton play;
	
	/** Stopp */
	private JButton stop;
	
	/** Speichern */
	private JButton save;
	
	/** Laden */
	private JButton load;
	
	/** Export */
	private JButton export;
	
	/** Neustart */
	private JButton restart;
	
	/** Spezienliste */
	private JList<Spezies> spezienliste;
	
	/** Listen-Modell */
	private DefaultListModel listModel;
	
	/** Slider für Zeitschritte */
	private JSlider slider;
	
	/** Referenz auf Controller */
	private MainController parent;
	
	/** Spielfeld */
	private FieldPanel feld;
	
	/**
	 * Erstellt ein neues Hauptfenster
	 * @param parent Controller Referenz
	 */
	public MainFrame(MainController parent)
	{
		super("Game of Life");
		this.parent = parent;
		t = Toolkit.getDefaultToolkit();
		Dimension d = t.getScreenSize();
		x = (int)((d.getWidth()-width)/2);
		y = (int)((d.getHeight()-height)/2);
		setBounds(x,y,width,height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.initComponents();
		this.initListeners();
		this.setFocusTraversalPolicy(new FocusTraversal());
		setVisible(true);
	}
	
	/**
	 * Initialisiere Kompoenten
	 */
	private void initComponents()
	{
		this.getContentPane().setLayout(new BorderLayout(5,5));
		//Container
		this.container = new JPanel(new FlowLayout(FlowLayout.LEFT));
		//Button
		this.add = new JButton("Add");
		this.play = new JButton("Play");
		this.stop = new JButton("Stop");
		this.save = new JButton("Save");
		this.export = new JButton("Export");
		this.load = new JButton("Load");
		this.restart = new JButton("Restart");
		// JSlider
		this.slider = new JSlider(JSlider.HORIZONTAL,0,20,0);
		this.slider.setMajorTickSpacing(5);
		this.slider.setPaintTicks(true);
		this.slider.setMinimum(1);
		//JLabel
		this.add.setPreferredSize(new Dimension(120,30));
		this.play.setPreferredSize(new Dimension(120,30));
		this.stop.setPreferredSize(new Dimension(120,30));
		this.save.setPreferredSize(new Dimension(120,30));
		this.load.setPreferredSize(new Dimension(120,30));
		this.export.setPreferredSize(new Dimension(120,30));
		this.restart.setPreferredSize(new Dimension(120,30));
		
		//Add
		this.container.add(this.add);
		this.container.add(this.play);
		this.container.add(this.stop);
		this.container.add(this.save);
		this.container.add(this.load);
		this.container.add(this.export);
		this.container.add(this.restart);
		this.container.add(this.slider);		
		listModel = new DefaultListModel<Spezies>();
		for(Spezies s : parent.getSpielfeld().getSpezien())
		{
			listModel.addElement(s);
		}
		this.spezienliste = new JList<Spezies>(listModel);
		//this.spezienliste.setVisibleRowCount(4);
		this.spezienliste.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.spezienliste.setPreferredSize(new Dimension(150, 50));
		this.getContentPane().add(this.container,BorderLayout.SOUTH);
		this.getContentPane().add(new JScrollPane(this.spezienliste),BorderLayout.WEST);
		
		feld = new FieldPanel(parent.getSpielfeld(), this);
		
		this.add(feld, BorderLayout.CENTER);
		this.pack();
	}

	/**
	 * Gibt aktuell ausgewählte Spezies zurück
	 * @return Spezies
	 */
	public Spezies getSpezies()
	{
		return spezienliste.getSelectedValue();
	}
	
	/**
	 * Fügt dem List Model eine Spezies hinzu
	 * @param s
	 */
	public void addListModel(Spezies s)
	{
		listModel.add(listModel.getSize(), s);
	}
	
	/**
	 * Initialisiert Listener
	 */
	private void initListeners()
	{
		Handler h = new Handler();
		this.play.addActionListener(h);
		this.add.addActionListener(h);
		this.save.addActionListener(h);
		this.export.addActionListener(h);
		this.load.addActionListener(h);
		this.stop.addActionListener(h);
		this.restart.addActionListener(h);
		this.slider.addChangeListener(h);
		this.spezienliste.addMouseListener(h);
	}
	
	/**
	 * Handler für Klicks und Wechsel des Zeitschritts
	 * @author Dominik Stegemann
	 *
	 */
	private class Handler implements ActionListener, ChangeListener, MouseListener
	{
		@Override
		/**
		 * Click Handler
		 */
		public void actionPerformed(ActionEvent e) 
		{
			//Starten
			if (e.getSource() == play)
			{
				parent.setGestartet(true);
				System.out.println("Start");
			}
			//Stoppen
			if (e.getSource() == stop)
			{
				parent.setGestartet(false);
			}
			//Spezies hinzufpgen
			if (e.getSource() == add)
			{
				parent.openAddFrame();
			}
			//Neustart
			if (e.getSource() == restart)
			{
				parent.openInitFrame();
				dispose();
			}
			//Speichern
			if (e.getSource() == save)
			{
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
		        try 
		        {
					parent.speichern(path);
					JOptionPane.showMessageDialog(null, "Speichern erfolgreich", "Speichern erfolgreich", JOptionPane.INFORMATION_MESSAGE);
				} 
		        catch (Exception e1) 
		        {
					JOptionPane.showMessageDialog(null, "Speichern fehlgeschlagen", "Speichern fehlgeschlagen", JOptionPane.INFORMATION_MESSAGE);
				}

			}
			//Export
			if (e.getSource() == export)
			{
				parent.openExportFrame();
			}
			//LLaden
			if (e.getSource() == load)
			{
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
		        try 
		        {
					parent.laden(path);
					dispose();
					parent.openMainFrame();
				} 
		        catch (Exception e1) 
		        {
					JOptionPane.showMessageDialog(null, "Laden fehlgeschlagen", "Laden fehlgeschlagen", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}

		/**
		 * Slider wechsel
		 */
		@Override
		public void stateChanged(ChangeEvent arg0) 
		{
			if(arg0.getSource() == slider)
			{
				parent.setZeitschritt(slider.getValue());				
			}
		}

		/**
		 * Mouseclick auf die Lisgte
		 */
		@Override
		public void mouseClicked(MouseEvent arg0) 
		{
			if(arg0.getSource() == spezienliste && arg0.getClickCount() == 2 && spezienliste.getSelectedIndex() != 0)
			{
				parent.openEditFrame(spezienliste.getSelectedValue());	
			}
			else if (arg0.getSource() == spezienliste && arg0.getClickCount() == 1 )
			{
				feld.resolveMarked();
			}
		}

		/** Nicht implementiert */
		@Override
		public void mouseEntered(MouseEvent arg0) {	}

		/** Nicht implementiert */
		@Override
		public void mouseExited(MouseEvent arg0) { }

		/** Nicht implementiert */
		@Override
		public void mousePressed(MouseEvent arg0) {	}

		/** Nicht implementiert */
		@Override
		public void mouseReleased(MouseEvent arg0) { }			
	}
	
	/**
	 * Tabreihenfolge
	 * @author Dominik Stegemann
	 */
	private class FocusTraversal extends FocusTraversalPolicy
	{

		/**
		 * Nachher
		 */
		@Override
		public Component getComponentAfter(Container aContainer, Component aComponent) {
			if(aComponent == play)
			{
				return add;
			}
			return null;
		}

		/**
		 * Vorher
		 */
		@Override
		public Component getComponentBefore(Container aContainer, Component aComponent) {
			if(aComponent == play)
			{
				return restart;
			}
			return null;
		}

		/**
		 * Start
		 */
		@Override
		public Component getDefaultComponent(Container aContainer) {
			return play;		
		}

		/**
		 * Erster
		 */
		@Override
		public Component getFirstComponent(Container aContainer) {
			return play;
		}

		/**
		 * Letzter
		 */
		@Override
		public Component getLastComponent(Container aContainer) {
			return restart;
		}
		
	}
}
