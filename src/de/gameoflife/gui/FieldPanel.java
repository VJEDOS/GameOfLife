package de.gameoflife.gui;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
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
	/** Feld */
	private Spielfeld feld;
	
	/** Unterpanels */
	private FieldPanelElement[][] panels;
	
	/** Parent */
	private MainFrame parent;
	
	private GridLayout layout;
	
	/**
	 * Erstellt ein neues Feld
	 * @param feld Feld
	 * @param parent Controller
	 */
	public FieldPanel(Spielfeld feld, MainFrame parent)
	{
		this.parent = parent;
		
		this.feld = feld;
		feld.addObserver(this);
		
		layout = new GridLayout(0, feld.getHeight(), 1, 1);
		
		this.setLayout(layout);
		
		panels = new FieldPanelElement[feld.getWidth()][feld.getHeight()];
		addPanels();
	}
	
	/**
	 * Fügt Panels hinzu
	 */
	private void addPanels() 
	{
		Handler h = new Handler();
		for(int i = 0; i < feld.getWidth(); i++)
		{
			for(int j = 0; j < feld.getHeight(); j++)
			{
				FieldPanelElement p = new FieldPanelElement();
				p.setSize(this.getWidth() / feld.getWidth() -1, this.getHeight() / feld.getHeight() -1);
				p.setOpaque(false);
				p.addMouseListener(h);
				panels[i][j] = p;
				this.add(p);
			}
		}
	}

	/**
	 * Neuzeichnen des Felds
	 */
	@Override
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		BufferedImage i = feld.toImage(this.getWidth(), this.getHeight());

//		this.setSize(i.getWidth(), i.getHeight());
		g.drawImage(i,0,0,null);
//		for(int i = 0; i < feld.getWidth(); i++)
//		{
//			for(int j = 0; j < feld.getHeight(); j++)()
//			{
//				JPanel p = panels[i][j];
//				p.setSize(this.getWidth() / feld.getWidth()-2, this.getHeight() / feld.getHeight()-2);
//			}
//		}
	}
	
	/**
	 * Neuzeichnen bei Zugende
	 */
	@Override
	public void update(Observable o, Object arg) 
	{
		repaint();
	}
	
	/**
	 * Setzt alle markierten auf die ausgewählte Spezies
	 */
	public void resolveMarked()
	{
		for(int i = 0; i < feld.getWidth(); i++)
		{
			for(int j = 0; j < feld.getHeight(); j++)
			{
				if(panels[i][j].isMarked())
				{
					if (parent.getSpezies() != null)
					{
						panels[i][j].setMarked(false);
						feld.setFeld(j, i, parent.getSpezies().getId());
						repaint();
					}
					else
					{
						return;
					}				
				}
			}
		}
	}
	
	/**
	 * Click Handler
	 * @author Dominik Stegemann
	 *
	 */
	private class Handler implements MouseListener
	{
		@Override
		public void mouseClicked(MouseEvent e) 
		{
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
							else
							{
								panels[i][j].setMarked(true);
							}
							return;
						}
						else
						{
							if(panels[i][j].isMarked())
							{
								panels[i][j].setMarked(false);
							}
							else
							{
								feld.setFeld(j,i,0);
								repaint();
							}
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
