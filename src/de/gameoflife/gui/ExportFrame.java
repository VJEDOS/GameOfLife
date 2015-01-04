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

public class ExportFrame extends JFrame
{
	private Toolkit t;
	private int x = 0, y = 0, width = 800, height = 600;
	private JPanel container;

	//Komponenten
	private JRadioButton jpg;
	private JRadioButton gif;
	private MainController parent;
	private JTextField breite;
	private JTextField hoehe;
	private JTextField anzahl;
	private JButton ok;
	private JButton esc;

	public ExportFrame(MainController parent)
	{
		super("Exportieren");

		t = Toolkit.getDefaultToolkit();
		Dimension d = t.getScreenSize();
		x = (int)((d.getWidth()-width)/2);
		y = (int)((d.getHeight()-height)/2);
		setBounds(x,y,width,height);
		this.parent = parent;
		this.initComponents();
		this.initListeners();
		setVisible(true);
	}
	
	private void initComponents() {
		this.container = new JPanel();
		container.setLayout(new GridLayout(0,2,10,10));
		jpg = new JRadioButton("jpg",true);
		gif = new JRadioButton("gif");

		NumberFormat format = NumberFormat.getInstance();
		format.setGroupingUsed(false);
		
		NumberFormatter formatter = new NumberFormatter(format);
		formatter.setAllowsInvalid(false);
		
		JLabel breiteLabel = new JLabel ("Breite");
		breite = new JFormattedTextField(formatter);
		breiteLabel.setLabelFor(breite);
		
		JLabel hoeheLabel = new JLabel ("Hoehe");
		hoehe = new JFormattedTextField(formatter);
		hoeheLabel.setLabelFor(hoehe);
		
		JLabel anzahlLabel = new JLabel ("Anzahl");
		anzahl = new JFormattedTextField(formatter);
		anzahl.setEditable(false);
		anzahlLabel.setLabelFor(anzahl);
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

	private void initListeners() 
	{
		Handler h = new Handler();
		jpg.addActionListener(h);
		gif.addActionListener(h);
		ok.addActionListener(h);
		esc.addActionListener(h);
	}
	
	private class Handler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
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
				}
				catch (Exception ex)
				{
					JOptionPane.showMessageDialog(null, "Ungueltige Werte", "Ungueltige Werte", JOptionPane.INFORMATION_MESSAGE);	
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
					parent.starteGifExport(path, breiteInt, hoeheInt, 1000, anzahlInt);
				}
				dispose();
			}
			if (e.getSource() == esc)
			{
				dispose();
			}
			if(e.getSource() == jpg)
			{
				anzahl.setEditable(false);
			}
			if(e.getSource() == gif)
			{
				anzahl.setEditable(true);
			}
		}
	}

}