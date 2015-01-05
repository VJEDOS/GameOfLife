package de.gameoflife.models;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;

import GifWriter.GifSequenceWriter;

/**
 * Schreibt gifs
 * @author Dominik Stegemann
 *
 */
public class GifWriter 
{
	/**
	 * Die Bilder, die geschrieben werden
	 */
	private List<BufferedImage> imageList;
	
	/**
	 * Speicherpfad
	 */
	private String pfad;
	
	/**
	 * Anzahl Sekunden zwischen den Bildern
	 */
	private int sekunden;
	
	/**
	 * Erstellt einen neuen GifWriter 
	 * @param pfad Zielpfad
	 * @param sekunden Sekunden zwischen den Gifs
	 */
	public GifWriter(String pfad, int sekunden) 
	{
		this.pfad = pfad;
		imageList = new ArrayList<BufferedImage>();
		this.sekunden = sekunden;
	}
	
	/**
	 * Schreibt die Bildliste in angegebenen Pfad 
	 */
	public void write()
	{
		if(pfad.equals("") || imageList.size() == 0)
		{
			throw new RuntimeException("Bild kann nicht geschrieben werden");
		}
		try
		{
		    ImageOutputStream output = new FileImageOutputStream(new File(pfad));
	
		    // ImageList.get(0) ist erlaubt, weil oben sichergestellt ist, dass die Liste Elemente hat
		    GifSequenceWriter writer = new GifSequenceWriter(output, imageList.get(0).getType(), sekunden, true);
	
		    // write out the first image to our sequence...
		    for(int i=0; i < imageList.size(); i++) {
		      writer.writeToSequence(imageList.get(i));
		    }
	
		    writer.close();
		    output.close();
		}
		catch(Exception e)
		{
			throw new RuntimeException("Bild kann nicht geschrieben werden");
		}
	}
	
	/**
	 * Fügt ein Bild an die Liste hinzu
	 * @param image Buffered Image
	 */
	public void add(BufferedImage image)
	{
		imageList.add(image);
	}
	
	/**
	 * Löscht die gespeicherten Bilder
	 */
	public void clear()
	{
		imageList.clear();
	}
	
}
