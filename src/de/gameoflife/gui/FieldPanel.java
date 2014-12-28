package de.gameoflife.gui;

import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import de.gameoflife.models.Spielfeld;

public class FieldPanel extends JPanel implements Observer
{

	private Spielfeld feld;
	
	public FieldPanel(Spielfeld feld)
	{
		this.feld = feld;
		feld.addObserver(this);
		
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
		System.out.println("paint");
		revalidate();
		repaint();
	}
	
}
