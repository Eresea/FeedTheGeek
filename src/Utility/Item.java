package Utility;

import org.newdawn.slick.Image;

public class Item {
	public int type;
	public int number;
	public String name;
	public String description;
	public Image Icon;
	public int Nutrition;
	public int duree;
	
	public Item(int type, int number, String name, Image Icon, String description,int nutri,int duree)
	{
		this.type = type;
		this.number = number;
		this.name = name;
		this.Icon = Icon;
		this.description = description;
		Nutrition = nutri;
		this.duree = duree;
	}
	
	public String Save()
	{
		return "name:"+name+",type:"+Integer.toString(type)+",number:"+Integer.toString(number)+",description:\""+description+"\",nutrition:"+Integer.toString(Nutrition)+",duree:"+Integer.toString(duree);
	}

}
