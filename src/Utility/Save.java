package Utility;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.*;

import MainPackage.WindowGame;

public class Save {
public String name;
public int money;

private List<String> lines;

public Save(String url)
{
	LoadData(url);
}

private void LoadData(String url)
{
	name = url.substring(url.lastIndexOf("/") + 1);
	name = name.substring(0,name.length()-4);
	/*try (FileReader in = new FileReader(url);)
	{
	    BufferedReader br = new BufferedReader(in);
	    String line;
		while((line = br.readLine()) != null)
		{
			String[] split = line.split(":");
			process(split[0],split[1]);
		}
	}
	catch (IOException e)
	{
		
	}*/
}

private void process(String key,String value)
{
	switch(key)
	{
	case "Money":
		money = Integer.parseInt(value);
		break;
	}
}

private void UpdateValue(String key, String value)
{
	
}

private List<String> setValues(List<String> l)
{
	List<String> Hud = WindowGame.hud.Save();
	for(int i=0;i<Hud.size();i++)
	{
		if(l.size()<=i) l.add(Hud.get(i));
		else l.set(i, Hud.get(i));
	}	
	return l;
}

public void SaveToFile()
{
	String url = "Saves/"+name+".sav";
	
	File f = new File(url);
		//if(!f.exists()) SetupFile(url);
	try {
	      List<String> lines = Files.readAllLines(f.toPath());
	      lines = setValues(lines);
	      
	    Files.write(f.toPath(), lines);
	    
	    System.out.println("SAVED");
	      
	    } catch (IOException e) {
	      System.out.println(e);
	    }
}
}
