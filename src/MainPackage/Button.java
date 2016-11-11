package MainPackage;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.*;

public class Button extends UIComponent{

	public int type = 0;
	public String text ="";
	public Font myFont;
	public Color BackGroundColor = Color.black;
	GameContainer gc;
	
	
	public Button(int x, int y, int w, int h, GameContainer gc)
	{
		super(x,y,w,h,gc);
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
			switch(type)
			{
			case 0:
				g.drawRect(width*x,top+(height*y),width*w,height*h);
				//g.setColor(BackGroundColor);
				//g.fillRect(width*x,top+(height*y),width*w,height*h);
				//g.setColor(Color.white);
				g.drawString(text, width*x-myFont.getWidth(text)/2+w*width/2, top+(height*y)-myFont.getHeight(text)/2+h*height/2);
				break;
			}
		}
	}
	
	public boolean Hover()
	{
		
		if(visible)
		{
			
			gc.getInput();
			int mouseX = Mouse.getX();
			int mouseY = gc.getHeight()-Mouse.getY();
			
			return (mouseX > width*x && mouseX < width*x+width*w) &&(mouseY < top+(height*y)+height*h && mouseY > top+(height*y));
		}
		return false;
	}
}
