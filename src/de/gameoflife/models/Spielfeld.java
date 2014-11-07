package de.gameoflife.models;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.HashMap;
import de.gameoflife.enums.Modus;

/**
 * Diese Klasse repräsentiert das Spielfeld
 * @author Dominik Stegemann
 */
public class Spielfeld implements Serializable
{
	/**
	 * Das Spielfeld
	 */
	private int[][] feld;
	
	/**
	 * Liste der Spezien, Id der Spezies gemappt auf die Spezies
	 */
	private HashMap<Integer, Spezies> spezien;
	
	/**
	 * Der Spielmodus
	 */
	private Modus modus;
	
	/**
	 * Erstellt ein neues Spielfeld der angegebenen Größe und fügt 
	 * die Standartspezies hinzu
	 * @param x Größe x
	 * @param y Größe y
	 * @param modus Spielmodus
	 */
	public Spielfeld(int x, int y, Modus modus)
	{
		feld = new int[x][y];
		this.modus = modus;
		spezien = new HashMap<Integer, Spezies>();
		Spezies s = new Spezies();
		spezien.put(s.getId(), s);
		for(int i = 0; i < 10; i++)
		{
			for (int j = 0; j < 10; j++)
			{
				setFeld(i, j, 1);
			}
		}
	}	
	
	/**
	 * Berechnet den nächsten Zug
	 */
	public void naechsterZug()
	{
		int[][] temp = new int[feld.length][feld[0].length];
		
		/*
		 * Coding
		 */
		
		System.out.println(feld[1][2]);
		
		feld = temp;
	}
	
	/**
	 * Setzt den Wert des Felds an der angegebenen Stelle auf 
	 * den übergebenen Wert
	 * @param x x-Koordinate
	 * @param y y-Koordinate
	 * @param value Neuer Wert
	 */
	public void setFeld(int x, int y, int value)
	{
		if(x < 0 || x > feld[0].length || y < 0 || y > feld.length)
		{
			throw new RuntimeException("Ungueltiges feld");
		}
		feld[x][y] = value;
	}	
	
	/**
	 * Erstellt ein Buffered Image aus diesem Spielfeld
	 * @param xSize Breite des Bildes
	 * @param ySize Höhe des Bildes
	 * @return Buffered Image
	 */
	public BufferedImage toImage(int xSize, int ySize)
	{
		BufferedImage ret = new BufferedImage(xSize, ySize, BufferedImage.TYPE_3BYTE_BGR);
		int xDimension = xSize/ feld.length;
		int yDimension = ySize / feld[0].length;
		if (xDimension < 0 || yDimension < 0)
		{
			throw new RuntimeException("Invalid size");
		}
		for(int i = 0; i <= feld.length-xDimension; i+=xDimension)
		{
			for (int j = 0; j <= feld[i].length-yDimension; j+=yDimension)
			{
				System.out.println(j);
				for(int x = 0; x < xDimension; x++)
				{
					System.out.println("v");
					for(int y = 0; y < yDimension; y++)
					{
						System.out.println("c");
						if(feld[i/xDimension+x][j/yDimension+y] != 0)
						{
							ret.setRGB(i+x, j+y, Color.red.getRGB());
						}
					}
				}
			}
		}
		return ret;
	}
}