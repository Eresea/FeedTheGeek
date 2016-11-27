package Utility;

import org.newdawn.slick.Image;

public class Item {
	public int type;
	public int number;
	public String name;
	public String description;
	public Image Icon;
	
	public Item(int type, int number, String name, Image Icon, String description)
	{
		this.type = type;
		this.number = number;
		this.name = name;
		this.Icon = Icon;
		this.description = description;
	}

}
