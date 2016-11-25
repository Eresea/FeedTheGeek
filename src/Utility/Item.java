package Utility;

import org.newdawn.slick.Image;

public class Item {
	int type;
	int number;
	String name;
	Image Icon;
	
	Item(int type, int number, String name, Image Icon)
	{
		this.type = type;
		this.number = number;
		this.name = name;
		this.Icon = Icon;
	}

}
