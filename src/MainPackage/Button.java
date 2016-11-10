package MainPackage;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;

public class Button {

	public int type = 0;
	public String text;
	public Font myFont;
	public boolean visible = true;
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
	
	public void ReSet(int x,int y, int w, int h)
	{
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public void render(Graphics g)
	{
		if(visible)
		{
			int centerX = gc.getWidth()/2;
			int centerY = gc.getHeight()/2;
			
			switch(type)
			{
			case 0:
				g.drawRect(centerX+x, centerY+y, w, h);
				g.drawString(text, centerX+(w/2)+x-myFont.getWidth(text)/2, centerY+y+(h/2)-myFont.getHeight(text)/2);
				//g.drawString(text, ((w)/2 + x)-myFont.getWidth(text)/2, ((h)/2 + y)-myFont.getHeight(text)/2);
				break;
			}
		}
	}
	
	public boolean Hover()
	{
		if(visible)
		{
			int centerX = gc.getWidth()/2;
			int centerY = gc.getHeight()/2;
			
			Input input = gc.getInput();
			int mouseX = Mouse.getX();
			int mouseY = Mouse.getY();
			
			return (mouseX > centerX+x && mouseX < centerX+x+w) &&(mouseY < gc.getHeight() - (centerY+y) && mouseY > gc.getHeight() - (centerY+y+h));
		}
		return false;
	}
}
