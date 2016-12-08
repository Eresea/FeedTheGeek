package MainPackage;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;

public class UIComponent { // Classe parente permettant de simplifier l'utilisation des éléments d'interface

	public static float left,top,width,height;
	protected float x,y,w,h;
	protected GameContainer gc;
	public boolean visible = true;
	
	UIComponent(float x,float y,float w,float h,GameContainer gc)
	{
		this.x = x/1920;
		this.y = y/1080;
		this.w = w/1920;
		this.h = h/1080;
		this.gc = gc;
	}
	
	public void render(Graphics g)
	{
		if(visible)
		{
			g.drawRect(width*x,top+(height*y),width*w,height*h);
		}
	}
	
	public boolean Hover()
	{
		if(visible)
		{
			int centerX = gc.getWidth()/2;
			int centerY = gc.getHeight()/2;
			
			gc.getInput();
			int mouseX = Mouse.getX();
			int mouseY = Mouse.getY();
			
			return (mouseX > centerX+x && mouseX < centerX+x+w) &&(mouseY < gc.getHeight() - (centerY+y) && mouseY > gc.getHeight() - (centerY+y+h));
		}
		return false;
	}
	
	public static void drawText(String text, Font f, Graphics g,int x,int y,int w) // Affiche du texte pour une largeur donnée (auto-wrap)
	{
		int j = 0;
	    String words[] = text.split(" ");
	    String toDraw = "";
	    int currentW = 0;
	    for(int i=0;i<words.length;i++)
	    {
	    	if(f.getWidth(toDraw + words[i]) > w) // Si le text est trop grand
	    	{
	    		g.drawString(toDraw, x, y+(j*(f.getHeight(text))));
	    		toDraw = " " + words[i];
	    		j++;
	    	}
	    	else
	    	{
	    		toDraw += " " + words[i];
	    	}
	    }
	    g.drawString(toDraw, x, y+(j*(f.getHeight(text))));
	}
}
