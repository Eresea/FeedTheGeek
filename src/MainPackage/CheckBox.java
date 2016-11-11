package MainPackage;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Font;
//import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public class CheckBox extends UIComponent{
	
public boolean checked = false;
public String text;
public Font myFont;

public CheckBox(int x, int y, int size, GameContainer gc)
{
	super(x,y,size,size,gc);
	myFont = gc.getDefaultFont();
}

public void render(Graphics g)
{
	
	g.drawRect(width*x,top+(height*y),w*gc.getWidth(),gc.getHeight()*h);
	g.drawString(text, width*x-myFont.getWidth(text)-5+w*width/2, top+(height*y)-myFont.getHeight(text)/3);
	
	//g.drawString(text, centerX+x-w-(myFont.getWidth(text)), centerY+y-(myFont.getHeight(text)/4));
	if(checked)
	{
		g.drawString("x", width*x, top+(height*y)-myFont.getHeight(text)/3);
	}
}

public boolean Hover()
{
	if(visible)
	{
		Input input = gc.getInput();
		int mouseX = Mouse.getX();
		int mouseY = gc.getHeight()-Mouse.getY();
		
		return (mouseX > width*x && mouseX < width*x+gc.getWidth()*w) &&(mouseY < top+(height*y)+gc.getHeight()*h && mouseY > top+(height*y));
	}
	return false;
	
}

}
