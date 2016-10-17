package MainPackage;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;

public class Button {

	public int type = 0;
	public String text;
	public Font myFont;
	private int x,y,w,h;
	GameContainer gc;
	
	public Button()
	{
		
	}
	
	public Button(int x, int y, int w, int h, GameContainer gc)
	{
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.gc = gc;
		myFont = gc.getDefaultFont();
	}
	
	public void render(Graphics g)
	{
		switch(type)
		{
		case 0:
			g.drawRect(x, y, w, h);
			g.drawString(text, ((w)/2 + x)-myFont.getWidth(text)/2, ((h)/2 + y)-myFont.getHeight(text)/2);
			break;
		}
	}
	
	public boolean Hover()
	{
		Input input = gc.getInput();
		int mouseX = Mouse.getX();
		int mouseY = Mouse.getY();
		return (mouseX > this.x && mouseX < this.x+this.w) &&(mouseY < gc.getHeight() - this.y && mouseY > gc.getHeight() - (this.y + this.h));
	}
}
