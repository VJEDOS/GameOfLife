package de.gameoflife.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.FocusTraversalPolicy;
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

public class MainFrame extends JFrame
{
	private MainFrame frame = this;
	
	private Toolkit t;
	private int x = 0, y = 0, width = 800, height = 600;
	private JPanel container;
	//Button
	private JButton add;
	private JButton play;
	private JButton stop;
	private JButton save;
	private JButton load;
	private JButton export;
	private JButton restart;
	
	//Jlist
	private JList<Spezies> spezienliste;
	private DefaultListModel listModel;
	//Slider
	private JSlider slider;
	//Controller
	private MainController parent;
	//Spielfeld
	private FieldPanel feld;
	
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
		
		this.container.add(this.add);
		this.container.add(this.play);
		this.container.add(this.stop);
		this.container.add(this.save);
		this.container.add(this.load);
		this.container.add(this.export);
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
	
	public Spezies getSpezies()
	{
		return spezienliste.getSelectedValue();
	}
	
	public void addListModel(Spezies s)
	{
		listModel.add(listModel.getSize(), s);
	}
	
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
	
	private class Handler implements ActionListener, ChangeListener, MouseListener
	{

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			if (e.getSource() == play)
			{
				parent.setGestartet(true);
				System.out.println("Start");
			}
			if (e.getSource() == stop)
			{
				parent.setGestartet(false);
			}
			if (e.getSource() == add)
			{
				parent.openAddFrame();
			}
			if (e.getSource() == restart)
			{
				
			}
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
			if (e.getSource() == export)
			{
				parent.openExportFrame();
			}
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
					// Update 
					feld = new FieldPanel(parent.getSpielfeld(), frame);
					feld.repaint();
				} 
		        catch (Exception e1) 
		        {
					JOptionPane.showMessageDialog(null, "Laden fehlgeschlagen", "Laden fehlgeschlagen", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}

		@Override
		public void stateChanged(ChangeEvent arg0) {
			if(arg0.getSource() == slider)
			{
				parent.setZeitschritt(slider.getValue());				
			}
		}

		@Override
		public void mouseClicked(MouseEvent arg0) 
		{
			if(arg0.getSource() == spezienliste && arg0.getClickCount() == 2 && spezienliste.getSelectedIndex() != 0)
			{
				parent.openEditFrame(spezienliste.getSelectedValue());	
			}
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {	}

		@Override
		public void mouseExited(MouseEvent arg0) { }

		@Override
		public void mousePressed(MouseEvent arg0) {	}

		@Override
		public void mouseReleased(MouseEvent arg0) { }			
	}
	
	private class FocusTraversal extends FocusTraversalPolicy
	{

		@Override
		public Component getComponentAfter(Container aContainer, Component aComponent) {
			if(aComponent == play)
			{
				return add;
			}
			return null;
		}

		@Override
		public Component getComponentBefore(Container aContainer, Component aComponent) {
			if(aComponent == play)
			{
				return restart;
			}
			return null;
		}

		@Override
		public Component getDefaultComponent(Container aContainer) {
			return play;		
		}
		
		@Override
		public Component getFirstComponent(Container aContainer) {
			return play;
		}

		@Override
		public Component getLastComponent(Container aContainer) {
			return restart;
		}
		
	}
}
