package de.gameoflife.gui;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import de.gameoflife.models.Spielfeld;

/**
 * 
 * @author Dominik Stegemann
 * Panel für das Spielfeld
 *
 */
public class FieldPanel extends JPanel implements Observer
{
	private Spielfeld feld;
	private JPanel[][] panels;
	private MainFrame parent;
	
	public FieldPanel(Spielfeld feld, MainFrame parent)
	{
		this.parent = parent;
		
		this.feld = feld;
		feld.addObserver(this);
		
		this.setLayout(new GridLayout(feld.getHeight(), feld.getWidth(), 1, 1));
		
		panels = new JPanel[feld.getWidth()][feld.getHeight()];
		addPanels();
		
	}
	
	private void addPanels() 
	{
		Handler h = new Handler();
		for(int i = 0; i < feld.getWidth(); i++)
		{
			for(int j = 0; j < feld.getHeight(); j++)
			{
				JPanel p = new JPanel();
				p.setSize(this.getWidth() / feld.getWidth(), this.getHeight() / feld.getHeight());
				p.setOpaque(false);
				p.addMouseListener(h);
				panels[i][j] = p;
				this.add(p);
			}
		}
	}

	@Override
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		g.drawImage(feld.toImage(this.getWidth(), this.getHeight()),0,0,null);
	}

	@Override
	public void update(Observable o, Object arg) 
	{
		repaint();
	}
	
	/**
	 * Click Handler
	 * @author Dominik Stegemann
	 *
	 */
	private class Handler implements MouseListener
	{
		@Override
		public void mouseClicked(MouseEvent e) {
			for(int i = 0; i < feld.getWidth(); i++)
			{
				for(int j = 0; j < feld.getHeight(); j++)
				{
					if(panels[i][j] == e.getSource())
					{
						if (!SwingUtilities.isRightMouseButton(e))
						{ 
//							System.out.println("Test");
							if (parent.getSpezies() != null)
							{
								feld.setFeld(j, i, parent.getSpezies().getId());
								repaint();
							}
							return;
						}
						else
						{
							System.out.println("test");
							feld.setFeld(j,i,0);
							repaint();
							return;
						}
					}
				}
			}
		}

		public void mouseEntered(MouseEvent arg0) {	}
		public void mouseExited(MouseEvent arg0) { }
		public void mousePressed(MouseEvent arg0) {	}
		public void mouseReleased(MouseEvent arg0) { }
	
		
	}
}
