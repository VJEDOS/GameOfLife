package de.gameoflife.models;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import de.gameoflife.enums.Modus;

/**
 * Diese Klasse repräsentiert das Spielfeld
 * @author Dominik Stegemann
 */
public class Spielfeld  extends Observable implements Serializable
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
		
		for(int i = 0; i < feld.length; i++)
		{
			for(int j = 0; j < feld[i].length; j++)
			{
				int[] nachbarn = getNachbarn(feld, i, j);
				/*
				 * Feld ist lebendigig
				 */
				if(feld[i][j] > 0)
				{
					Spezies spezies = null;
					try
					{
						 spezies = spezien.get(feld[i][j]);
					}
					catch (Exception e)
					{
						temp[i][j] = 0;
					}
					int nachbarAnzahl = zaehleNachbarn(nachbarn, spezies.getId());
					/*
					 * Zellen sterben, wenn sie weniger als Nachbarn haben, als im Objekt als Minimum hinterlegt
					 */
					if (nachbarAnzahl <= spezies.getIsolation())
					{
						temp[i][j] = 0;
					}
					/*
					 * Zellen sterben, wenn sie mehr als Nachbarn haben, als im Objekt áls Maximuum hinterlegt
					 */
					else if (nachbarAnzahl >= spezies.getMaximum())
					{
						temp[i][j] = 0;
					}
					/*
					 * Sonst überlebt das Feld
					 */
					else
					{
						temp[i][j] = feld[i][j];
					}
				}
				/*
				 * Totes Feld
				 * Ein totes Feld wird lebendig, wenn es Nachbarn hat, als in der 
				 * angrenzenden Spezies definiert
				 * Bei mehreren angrenzenden Spezien gewinnt die mit höheren Nachbarn
				 */
				else
				{
					Spezies max = null;
					int maxInt = 0;
					for ( Object s : spezien.values().toArray())
					{
						Spezies spezies = (Spezies)s;
						int menge = zaehleNachbarn(nachbarn, spezies.getId());
						if (menge > maxInt)
						{
							max = spezies;
							maxInt = menge;
						}
					}
					if (max != null)
					{
						if (maxInt >= max.getGeburt())
						{
							temp[i][j] = max.getId();
						}
					}
					else
					{
						temp[i][j] = 0;
					}
				}
			}
		}
		
		feld = temp;
		// Panel über Änderung informieren
		setChanged();
		super.notifyObservers();
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
	 * Gibt die Spezienliste zurück.
	 * @return
	 */
	public Collection<Spezies> getSpezien()
	{
		return spezien.values();
	}
	
	/**
	 * Gibt die Breite des Felds zurueck
	 * @return
	 */
	public int getWidth()
	{
		return feld[0].length;
	}
	
	/**
	 * Gibt die Hoehe des Felds zurueck
	 */
	public int getHeight()
	{
		return feld.length;
	}
	
	/**
	 * Erstellt ein Buffered Image aus diesem Spielfeld
	 * Skaliert das Feld hoch
	 * @param xSize Breite des Bildes
	 * @param ySize Höhe des Bildes
	 * @return Buffered Image
	 */
	public BufferedImage toImage(int xSize, int ySize)
	{
		/*
		 * -1 für das Raster 
		 */
		int xDimension = xSize / feld.length - 1;
		int yDimension = ySize / feld[0].length - 1;
		
		if (xDimension <= 0 || yDimension <= 0)
		{
			throw new RuntimeException("Invalid size");
		}
		BufferedImage ret = new BufferedImage(xSize, ySize, BufferedImage.TYPE_INT_RGB);
		for(int i = 0; i < feld.length; i++)
		{
			for (int j = 0; j < feld[i].length; j++)
			{
				/*
				 * Die Schleifen starten bei 1, damit sie das Raster nicht überschreiben
				 */
				for(int x = 1; x < xDimension; x++)
				{
					for(int y = 1; y < yDimension; y++)
					{
						if(feld[i][j] != 0)
						{
							ret.setRGB(i*xDimension+x, j*yDimension+y, spezien.get(feld[i][j]).getFarbe().getRGB());
						}
						else
						{
							ret.setRGB(i*xDimension+x, j*yDimension+y, Color.white.getRGB());
						}
					}
					ret.setRGB(i*xDimension, (j+1)*yDimension, Color.BLACK.getRGB());
				}
				ret.setRGB((i+1)*xDimension, (j+1)*yDimension, Color.BLACK.getRGB());
			}
		}
		return ret;
	}
		
	/**
	 * Bestimmt die Nachbarn (aktuell nur für begrenztes feld)
	 * @param field
	 * @param x
	 * @param y
	 * @return
	 */
	private int[] getNachbarn(int[][] field, int x, int y)
	{
		int[] array = new int[8];
		
		/*
		 * Bestimmung der Nachbarn bei einem begrenzten Feld
		 */
		if (modus == Modus.BEGRENZT)
		{
			array[0] = ( x+1 >= field.length || y+1 >= field[0].length ? 0 : feld[x+1][y+1]);
			array[1] = ( x+1 >= field.length ? 0 : feld[x+1][y]);
			array[2] = ( x+1 >= field.length || y-1 < 0 ? 0 : feld[x+1][y-1]);
			array[3] = ( y+1 >= field[0].length ? 0 : feld[x][y+1]);
			array[4] = ( y-1 < 0 ? 0 : feld[x][y-1]);
			array[5] = ( x-1 < 0 || y+1 >= field[0].length ? 0 : feld[x-1][y+1]);
			array[6] = ( x-1 < 0 ? 0 : feld[x-1][y]);
			array[7] = ( x-1 < 0 || y-1 < 0 ? 0 : feld[x-1][y-1]);		
		}
		/*
		 * Bestimmung der Nachbarn bei einem Torsus-Feld
		 */
		else if (modus == Modus.TORSUS)
		{
			array[0] = ( x+1 >= field.length || y+1 >= field[0].length ? gegenueber(feld,x+1,y+1) : feld[x+1][y+1]);
			array[1] = ( x+1 >= field.length ? gegenueber(feld,x+1,y) : feld[x+1][y]);
			array[2] = ( x+1 >= field.length || y-1 <= 0 ? gegenueber(feld,x+1,y-1) : feld[x+1][y-1]);
			array[3] = ( y+1 >= field[0].length ? gegenueber(feld,x,y+1) : feld[x][y+1]);
			array[4] = ( y-1 < 0 ? gegenueber(feld,x,y-1) : feld[x][y-1]);
			array[5] = ( x-1 < 0 || y+1 >= field[0].length ? gegenueber(feld,x-1,y+1) : feld[x-1][y+1]);
			array[6] = ( x-1 < 0 ? gegenueber(feld,x-1,y) : feld[x-1][y]);
			array[7] = ( x-1 < 0 || y-1 < 0 ? gegenueber(feld,x-1,y-1) : feld[x-1][y-1]);					
		}
		return array;
	}
	
	/**
	 * Gibt den Wert des gegenüberliegenden Feldes zurück
	 * @param feld Das Feld
	 * @param x x-Koordinate
	 * @param y y-Koordinate
	 * @return Wert
	 */
	private int gegenueber(int[][] feld, int x, int y)
	{		
		int tempX = x;
		int tempY = y;
		/*
		 * X-Wert
		 */
		if (x < 0)
		{
			tempX = feld.length-1;
		}
		else if (x >= feld.length)
		{
			tempX = 0;
		}
		/*
		 * Y-Wert
		 */
		if (y < 0)
		{
			tempY = feld[0].length-1;
		}
		else if (y >= feld[0].length)
		{
			tempY = 0;
		}
		
		return feld[tempX][tempY];
	}
	
	/**
	 * Zaehlt die Nachbarn
	 * @return
	 */
	private int zaehleNachbarn(int[] feld, int spezies)
	{
		int zaehler = 0;
		for (int i = 0; i < feld.length; i++) 
		{
			if (feld[i] == spezies)
			{
				zaehler++;
			}
		}
		return zaehler;
	}
	
	
	/**
	 * TEST CONSTRUCTOR
	 */
	public Spielfeld()
	{
		feld = new int[4][4];
		modus = Modus.BEGRENZT;
		spezien = new HashMap<Integer, Spezies>();
		Spezies s = new Spezies();
		spezien.put(s.getId(), s);
		Spezies s2 = new Spezies(2,3,4,Color.blue);
		spezien.put(s2.getId(), s2);
		feld[1][0] = 1;
		feld[2][0] = 1;
		feld[3][0] = 1;
		feld[2][1] = 1;
		feld[1][2] = 2;
		feld[2][2] = 2;
						
		print();
	}
	
	
	public void print()
	{ 
		for (int i = 0; i < feld.length; i++)
		{
			for (int j = 0; j < feld[i].length; j++)
			{
				System.out.print(feld[j][i] + "  ");
			}
			System.out.println();
		}
		System.out.println();
	}
}