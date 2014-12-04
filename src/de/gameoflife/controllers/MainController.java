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
import de.gameoflife.models.Spielfeld;

/**
 * Der Hauptcontroller, der GUI und Logik verwaltet
 * @author Dominik Stegemann
 */
public class MainController
{
	/**
	 * Das Spielfeld
	 */
	private Spielfeld spielfeld;
	
	/**
	 * Zeit zwischen zwei Zügen
	 */
	private int zeitschritt;
	
	/**
	 * Die Bilder, die für den gif Export benötigt werden, werden hier gespeichert
	 */
	private ArrayList<BufferedImage> bilder;
	
	/**
	 * Erstellt einen neuen Controller
	 */
	public MainController() 
	{
//		spielfeld = new Spielfeld(20, 20, Modus.BEGRENZT);		
		spielfeld = new Spielfeld();
		spielfeld.naechsterZug();
		spielfeld.print();
		BufferedImage image = spielfeld.toImage(5000, 5000	);
		try 
		{
			ImageIO.write(image, "png", new File("/home/ds/export"));
		}
		catch (IOException e) 
		{
			e.printStackTrace();
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
	 */
	public void bildExport(String pfad, int xSize, int ySize)
	{
		try 
		{
			ImageIO.write(spielfeld.toImage(xSize, ySize), "png", new File("/home/ds/export"));
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 */
	public void bildExportGif(String pfad, int xSize, int ySize)
	{
		
	}
}