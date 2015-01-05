package de.gameoflife.gui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * Einzelnes Feld-Element
 * @author Dominik Stegemann
 *
 */
public class FieldPanelElement extends JPanel
{
	/** Markiert */
	private boolean marked;

	/**
	 * Angabe ob Markiert
	 * @return Markiert
	 */
	public boolean isMarked()
	{
		return marked;
	}

	/**
	 * Setze Markierung
	 * @param marked Neuer Wert
	 */
	public void setMarked(boolean marked) 
	{
		this.marked = marked;
		repaint();
	}
	
	/**
	 * Wenn markiert: gelb markieren
	 */
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
