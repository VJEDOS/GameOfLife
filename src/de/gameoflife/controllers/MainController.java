package de.gameoflife.controllers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import de.gameoflife.enums.Modus;
import de.gameoflife.gui.AddFrame;
import de.gameoflife.gui.ExportFrame;
import de.gameoflife.gui.InitFrame;
import de.gameoflife.gui.MainFrame;
import de.gameoflife.models.GifWriter;
import de.gameoflife.models.Spezies;
import de.gameoflife.models.Spielfeld;

/**
 * Der Hauptcontroller, der GUI und Logik verwaltet
 * @author Dominik Stegemann
 */
public class MainController implements Runnable
{
	/**
	 * Das Spielfeld
	 */
	private Spielfeld spielfeld;

	/**
	 * Zeit zwischen zwei Zügen
	 */
	private volatile int zeitschritt = 1;


	/**
	 * x-Größe des Gifs
	 */
	private int x;

	/**
	 * y-Größe des Gifs
	 */
	private int y;

	/**
	 * Anzahl Bilder, die noch in den gif writer geschrieben werden
	 */
	private int bilderAnzahl;

	/**
	 * Der Gif Writer
	 */
	private GifWriter gifWriter;

	/**
	 * Angabe, ob Bildexport läuft
	 */
	private boolean bilderExport;

	/**
	 * Angabe, ob gestartet
	 */
	private volatile boolean gestartet;

	// GUI
	
	private MainFrame mainFrame;
	private AddFrame addFrame;
	private ExportFrame exportFrame;
	private InitFrame initFrame;
	
	/**
	 * Erstellt einen neuen Controller
	 */
	public MainController() 
	{
		initFrame = new InitFrame(this);
		//DEBUG
		//spielfeld = new Spielfeld();
		//openMainFrame();
	}

	/**
	 * Führt einen Zug aus
	 */
	public void zug()
	{
		spielfeld.naechsterZug();
		if(bilderExport)
		{
			gifWriter.add(spielfeld.toImage(x, y));
			bilderAnzahl--;
			if(bilderAnzahl == 0)
			{
				bilderExport = false;
				schreibeGif();
			}
		}
	}

	/**
	 * Speichert den aktuellen Spielstand
	 * @param path Speicherpfad
	 * @throws Exception Exception wird geworfen, wenn ein Fehler bein Speichern auftritt
	 * F301
	 */
	public void speichern(String pfad) throws Exception
	{
		ObjectOutputStream outputStream;
		try
		{
			outputStream = new ObjectOutputStream(new FileOutputStream(pfad));
		}
		catch(Exception ex)
		{
			throw ex;
		}
		outputStream.writeObject(spielfeld);
		outputStream.close();
	}

	/**
	 * Laedt einen Spielstand
	 * @param path Aus diesem Pfad wird gelesen-
	 * @throws Exception Datei nicht gefunden oder 
	 * Objekt konnte nicht serialisiert werden
	 * 
	 * F302
	 */
	public void laden(String pfad) throws Exception
	{
		ObjectInputStream inputStream;
		try
		{
			inputStream = new ObjectInputStream(new FileInputStream(pfad));
		}
		catch(Exception e)
		{
			throw e;
		}
		spielfeld = (Spielfeld)inputStream.readObject();
		inputStream.close();
	}

	/**
	 * 
	 * @param pfad
	 * @param xSize
	 * @param ySize
	 * @return Angabe, ob erfolgreich oder nicht
	 */
	public boolean bildExport(String pfad, int xSize, int ySize)
	{
		try 
		{
			ImageIO.write(spielfeld.toImage(xSize, ySize), "png", new File(pfad));
		}
		catch (IOException e) 
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Startet den Gif export
	 * @param pfad Zielpfad
	 * @param xSize x-größe
	 * @param ySize y-größe
	 * @param sekunden Sekunden zwischen den Bildern
	 * @param bilderAnzahl Anzahl bilder
	 */
	public void starteGifExport(String pfad, int xSize, int ySize, int sekunden, int bilderAnzahl)
	{
		this.gifWriter = new GifWriter(pfad, sekunden);
		this.x = xSize;
		this.y = ySize;
		this.bilderAnzahl = bilderAnzahl;
		this.bilderExport = true;
	}

	/**
	 * Hauptschleife, welche die Runden steuert
	 */
	@Override
	public void run() 
	{
		while(true)
		{
			
			while (gestartet)
			{
				zug();
				try 
				{
					System.out.println(zeitschritt);
					Thread.sleep(zeitschritt*1000);
				} 
				catch (InterruptedException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Setzt gestartet
	 * @param gestartet Gestartet
	 */
	public void setGestartet(boolean gestartet)
	{
		this.gestartet = gestartet;
	}

	/**
	 * Setze Zeitschritt
	 * @param zeit Setzt Zeit
	 */
	public void setZeitschritt(int zeit)
	{
		this.zeitschritt = zeit;
	}
	
	/**
	 * Öffnet ein neues Hauptfenster, falls es noch keins gibt
	 */
	public void openMainFrame()
	{
		if (mainFrame == null)
		{
			mainFrame = new MainFrame(this);
			Thread t = new Thread(this);
			t.start();
		}
	}
	
	/**
	 * Öffnet ein neues Hinzufuegen-Fenster, falls es noch keins gibt
	 */
	public void openAddFrame()
	{
		addFrame = new AddFrame(this);
	}
	
	/**
	 * Öffnet ein neues Editfenster, falls es noch keins gibt
	 */
	public void openEditFrame(Spezies s)
	{
		addFrame  = new AddFrame(s, this);	
	}

	/**
	 * Öffnet ein neues Exportfenster, falls es noch keins gibt
	 */
	public void openExportFrame()
	{
//		if (exportFrame == null)
//		{
			exportFrame  = new ExportFrame(this);
//		}
	}
	
	public Spielfeld getSpielfeld()
	{
		return spielfeld;
	}
	
	public void setSpielfeld(Spielfeld s)
	{
		spielfeld = s;
	}
	
	public MainFrame getMainFrame()
	{
		return mainFrame;
	}
	
	/**
	 * Schreibt ein gif
	 */
	private void schreibeGif()
	{
		this.gifWriter.write();
	}
}