package de.gameoflife.enums;

/**
 * Der Spielmodus
 * @author Dominik Stegemann
 */
public enum Modus 
{
	/**
	 * Spielfeld wird durch tote Zellen besetzt
	 * F201.1.1
	 */
	BEGRENZT,
	
	/**
	 * Spielfeld ist durchlässig 
	 * F201.1.2
	 */
	TORSUS,
	
	/**
	 * Spielfeld ist unendlich groß
	 * NICHT Verwendet
	 * F201.1.3
	 */
	UNENDLICH,
}