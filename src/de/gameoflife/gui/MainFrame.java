package de.gameoflife.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import de.gameoflife.controllers.MainController;
import de.gameoflife.models.Spezies;

public class MainFrame extends JFrame
{
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
	//Jlist
	private JList<Spezies> spezienliste;
	private DefaultListModel listModel;
	//Slider
	private JSlider slider;
	//Controller
	private MainController parent;
	
	
	public MainFrame(MainController parent)
	{
		super("Game of Life");
		t = Toolkit.getDefaultToolkit();
		Dimension d = t.getScreenSize();
		x = (int)((d.getWidth()-width)/2);
		y = (int)((d.getHeight()-height)/2);
		setBounds(x,y,width,height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.initComponents();
		this.initListeners();
		setVisible(true);
		this.parent = parent;
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
		// JSlider
		this.slider = new JSlider(JSlider.HORIZONTAL,0,20,0);
		this.slider.setMajorTickSpacing(5);
		this.slider.setPaintTicks(true);
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
		listModel.addElement(new Spezies());
		this.spezienliste = new JList<Spezies>(listModel);
		//this.spezienliste.setVisibleRowCount(4);
		this.spezienliste.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.spezienliste.setPreferredSize(new Dimension(150, 50));
		this.getContentPane().add(this.container,BorderLayout.SOUTH);
		this.getContentPane().add(new JScrollPane(this.spezienliste),BorderLayout.WEST);
		this.pack();
	}
	
	private void initListeners()
	{
		Handler h = new Handler();
		this.play.addActionListener(h);
		this.save.addActionListener(h);
		this.export.addActionListener(h);
		this.load.addActionListener(h);
		this.stop.addActionListener(h);
		
	}
	
	private class Handler implements ActionListener
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
			if (e.getSource() == save)
			{
				String path = "";
				JFileChooser chooser = new JFileChooser();
		        int rueckgabeWert = chooser.showOpenDialog(null);
		        if(rueckgabeWert == JFileChooser.APPROVE_OPTION)
		        {
		             path = chooser.getSelectedFile().getName();
		        }
		        else
		        {
		        	return;
		        }
		        try 
		        {
					parent.speichern(path);
				} 
		        catch (Exception e1) 
		        {
					JOptionPane.showMessageDialog(null, "Speichern fehlgeschlagen", "Speichern fehlgeschlagen", JOptionPane.INFORMATION_MESSAGE);
				}

			}
			if (e.getSource() == export)
			{
				
			}
			if (e.getSource() == load)
			{
				String path = "";
				JFileChooser chooser = new JFileChooser();
		        int rueckgabeWert = chooser.showOpenDialog(null);
		        if(rueckgabeWert == JFileChooser.APPROVE_OPTION)
		        {
		             path = chooser.getSelectedFile().getName();
		        }
		        else
		        {
		        	return;
		        }
		        try 
		        {
					parent.laden(path);
				} 
		        catch (Exception e1) 
		        {
					JOptionPane.showMessageDialog(null, "Laden fehlgeschlagen", "Laden fehlgeschlagen", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
				
	}
}
