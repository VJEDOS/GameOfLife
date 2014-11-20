package de.gameoflife.controllers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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
	 * Erstellt einen neuen Controller
	 */
	public MainController() 
	{
//		spielfeld = new Spielfeld(20, 20, Modus.BEGRENZT);		
//		BufferedImage image = spielfeld.toImage(140, 140);
//		try 
//		{
//			ImageIO.write(image, "png", new File("/home/ds/export"));
//		}
//		catch (IOException e) 
//		{
//			e.printStackTrace();
//		}
		spielfeld = new Spielfeld();
		spielfeld.naechsterZug();
		spielfeld.print();
	}
	
	/**
	 * Speichert den aktuellen Spielstand
	 * @param path Speicherpfad
	 * @throws Exception Exception wird geworfen, wenn 
	 * 
	 * F301
	 */
	public void saveGame(String path) throws Exception
	{
		ObjectOutputStream outputStream;
		try
		{
			outputStream = new ObjectOutputStream(new FileOutputStream(path));
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
	public void loadGame(String path) throws Exception
	{
		ObjectInputStream inputStream;
		try
		{
			inputStream = new ObjectInputStream(new FileInputStream(path));
		}
		catch(Exception e)
		{
			throw e;
		}
		spielfeld = (Spielfeld)inputStream.readObject();
		inputStream.close();
	}
}
