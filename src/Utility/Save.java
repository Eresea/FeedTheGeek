package Utility;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.*;

import org.newdawn.slick.Color;

import MainPackage.WindowGame;

public class Save {
public String name;
public int money;
public int lastSave;

private List<String> lines;

public Save(String url) // Constructor
{
	name = url.substring(url.lastIndexOf("/") + 1);
	name = name.substring(0,name.length()-4);
}

public void delete() // Supprime la sauvegarde concernée
{
	File f = new File("Saves/"+name+".sav");
	f.delete();
}

public boolean LoadData(String url) // Charge les données de l'url dans la partie
{
	name = url.substring(url.lastIndexOf("/") + 1);
	name = name.substring(0,name.length()-4);
	
	File f = new File("Saves/"+name+".sav");
	if(f.canRead())
	{
		try{
		List<String> lines = Files.readAllLines(f.toPath());
		WindowGame.PrimaryColor = StringToColor(lines.get(0));
		WindowGame.SecondaryColor = StringToColor(lines.get(1));
		WindowGame.hud.Money = Integer.parseInt(lines.get(2).substring(6));
		WindowGame.hud.setInventory(lines.get(3).substring(10,lines.get(3).length()-1));
		WindowGame.hud.setHealthHunger(Float.parseFloat(lines.get(4).substring(7)), Float.parseFloat(lines.get(5).substring(7)));
		lastSave = Integer.parseInt(lines.get(6).substring(9));
		
		return true;
		} catch(IOException e)
		{
			System.out.println(e.getMessage());
		}
	}
	return false;
}

private Color StringToColor(String s) // Converti un string définissant une couleur vers une couleur
{
	switch(s)
	{
	case "black":
		return Color.black;
	case "white":
		return Color.white;
	case "blue":
		return Color.blue;
	case "red":
		return Color.red;
	case "green":
		return Color.green;
	}
	return null;
}

private List<String> setValues(List<String> l) // Setteur de valeurs 
{
	List<String> Hud = WindowGame.hud.Save();
	for(int i=0;i<Hud.size();i++)
	{
		if(l.size()<=i) l.add(Hud.get(i));
		else l.set(i, Hud.get(i));
	}	
	return l;
}

public void SaveToFile() // Sauvegarde les données de la partie
{
	String url = "Saves/"+name+".sav";
	
	System.out.println(url);
	
	File f = new File(url);
	f.delete();
	
	try {
	if(!f.exists()) Files.createFile(f.toPath());
	} catch(IOException e)
	{
		System.out.println(e.getMessage());
	}
	try {
	      List<String> lines = Files.readAllLines(f.toPath());
	      lines = setValues(lines);
	      lines.add("lastSave:"+Calendar.getInstance().getTime().getTime()/1000);
	      
	    Files.write(f.toPath(), lines);
	    
	    System.out.println("SAVED");
	      
	    } catch (IOException e) {
	      System.out.println(e);
	    }
}
}
