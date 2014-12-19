package de.gameoflife.models;

import java.awt.Color;
import java.io.Serializable;

/**
 * Diese Klasse repräsentiert eine Spezies des Spiels 
 * 
 * @author Dominik Stegemann
 */
public class Spezies implements Serializable
{
	/**
	 * ID der Spezies 
	 */
	private int id;

	/**
	 * Anzahl der lebendigen Zellen die benoetigt werden,
	 * damit eine tote Zelle lebendig wird
	 */
	private int geburt;
	
	/**
	 * Liegt die Anzahl Nachbarn unter oder gleich diesem Wert,
	 * stirbt diese Zelle
	 */
	private int isolation;
	
	/**
	 * Liegt die Anzahl Nachbarn über dieser Zahl oder ist sie gleich,
	 * stirbt diese Zelle 
	 */
	private int maximum;
	
	/**
	 * Farbe des Felds auf dem Spielfeld
	*/
	private Color farbe;
	
	/**
	 * Anzahl Instanzen dieser Klasse
	 */
	private static int zaehler;
	
	/**
	 * Gibt an, ob diese Spezies editierbar ist
	 */
	private boolean editierbar;
	
	/**
	 * Erstellt die Standartspezies, welche von Conway beschrieben wurde
	 * Diese ist nicht editierbar.
	 */
	public Spezies()
	{
		zaehler++;
		this.id = zaehler;
		geburt = 3;
		maximum = 4;
		isolation = 1;
		editierbar = false;
		farbe = Color.RED;
	}
	
	/**
	 * Erstellt eine eigene Spezies
	 * @param geburt Geburt
	 * @param isolation Isolation
	 * @param maximum Maximum
	 */
	public Spezies(int geburt, int isolation, int maximum, Color farbe)
	{
		zaehler++;
		this.id = zaehler;
		this.geburt = geburt;
		this.maximum = maximum;
		this.isolation = isolation;
		this.farbe = farbe;
		editierbar = true;
	}
	
	/**
	 * Gibt die Id der Spezies zurück
	 * @return Id der Spezies
	 */
	public int getId() 
	{
		return id;
	}

	/**
	 * Gibt die Anzahl der benoetigten Zellen zurück,
	 * damit eine tote Zelle wiederbelebt wird
	 * @return Geburt
	 */
	public int getGeburt() 
	{
		return geburt;
	}
	
	/**
	 * Setze den Geburtswert
	 * @param geburt Neuer Wert, muss zwischen 0 und 8 liegen
	 */
	public void setGeburt(int geburt) 
	{
		if (geburt > 0 && geburt < 9 && editierbar)
		{
			this.geburt = geburt;
		}
		throw new RuntimeException("Ungueltiger Wert");
	}
	
	/**
	 * Gibt den Isolationswert zurueck
	 * @return
	 */
	public int getIsolation() 
	{
		return isolation;
	}

	/**
	 * Setzt den Isolationswert. Muss zwischen 1 und 8 liegen und 
	 * unterhalb des Maximums.
	 * @param isolation Der neue Isolationswert. 
	 */
	public void setIsolation(int isolation) 
	{
		if(isolation > 0 && isolation < 9 && isolation < this.maximum && this.editierbar)
		{
			this.isolation = isolation;
		}
		throw new RuntimeException("Ungueltiger Isolationswert");
	}
	
	/**
	 * Gibt dem Maximalwert zurück
	 * @return Maximum
	 */
	public int getMaximum() 
	{
		return maximum;
	}
	
	/**
	 * Setzt den Maximalwert. Muss über dem Minimum liegen.
	 * Zudem muss das Editier-Flag gesetzt sein.
	 * @param maximum Das neue Maximum
	 */
	public void setMaximum(int maximum) 
	{
		if(maximum > 0 && maximum < 9 && maximum > this.isolation && editierbar)
		{
			this.maximum = maximum;
		}
		throw new RuntimeException("Ungültiger Maximalwert");
	}
	
	/**
	 * Gibt die aktuelle Farbe zurück
	 * @return Farbe
	 */
	public Color getFarbe()
	{
		return this.farbe;
	}
	
	/**
	 * Setzt die Farbe
	 * @param farbe Farbe
	 */
	public void setFarbe(Color farbe)
	{
		this.farbe = farbe;
	}
	
	/**
	 * Rückgabe Id
	 */
	@Override
	public String toString() {
		return String.valueOf(id);
	}
}
