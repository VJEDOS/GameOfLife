package de.gameoflife.gui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class FieldPanelElement extends JPanel
{
	private boolean marked;

	public boolean isMarked()
	{
		return marked;
	}

	public void setMarked(boolean marked) 
	{
		this.marked = marked;
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		if(this.marked)
		{
			g.setColor(new Color(1, 1, 0, 0.5f));
			g.fillRect(0, 0, getWidth(), getHeight());
		}
		super.paintComponent(g);
	}
	
}
