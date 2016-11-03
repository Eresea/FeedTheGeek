package MainPackage;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Font;
//import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public class CheckBox {
public boolean checked = false;
public int x,y,size;
public String text;
public Font myFont;
GameContainer gc;

public CheckBox(int x, int y, int size, GameContainer gc)
{
	this.x = x;
	this.y = y;
	this.size = size;
	this.gc = gc;
	myFont = gc.getDefaultFont();
}

public void ReSet(int x,int y, int size)
{
	this.x = x;
	this.y = y;
	this.size = size;
}

public void render(Graphics g)
{
	int centerX = gc.getWidth()/2;
	int centerY = gc.getHeight()/2;
	
	g.drawRect(centerX+x, centerY+y, size, size);
	g.drawString(text, centerX+x-size-(myFont.getWidth(text)), centerY+y-(myFont.getHeight(text)/4));
	if(checked)
	{
		g.drawString("*",centerX+x,centerY+y);
	}
}

public boolean Hover()
{
	int centerX = gc.getWidth()/2;
	int centerY = gc.getHeight()/2;
	
	Input input = gc.getInput();
	int mouseX = Mouse.getX();
	int mouseY = Mouse.getY();
	
	return (mouseX > centerX+x && mouseX < centerX+x+size) &&(mouseY < gc.getHeight() - (centerY+y) && mouseY > gc.getHeight() - (centerY+y+size));
}

}
