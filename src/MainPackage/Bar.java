package MainPackage;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;

public class Bar extends UIComponent{ // Classe d'�l�ment graphique affichant une barre de progression
public Color backColor = Color.red;
public Color frontColor = Color.green;
public String text ="";
private Font myFont;

private float value;

Bar(int x,int y, int w, int h,GameContainer gc)
{
	super(x,y,w,h,gc); // Constructeur de UIComponent (parent)
	myFont = gc.getDefaultFont();
}

public void setValue ( float f ) { // Change la progression  de la barre
	   value = Math.max(0,Math.min(1,f));
	} 

public void render(Graphics g) // Affichage de l'�l�ment graphique
{
	Color tmp = g.getColor();	
	
	g.setColor(frontColor);
	g.fillRect(width*x, top+(height*y), width*w*value, height*h);
	g.setColor(backColor);
	g.fillRect(width*x+(width*w*value), top+(height*y), width*w*(1-value), height*h);
	if(text != "")
	{
		g.setColor(Color.black);
		g.drawString(text, width*x-myFont.getWidth(text)/2+w*width/2, top+(height*y)-myFont.getHeight(text)/2+h*height/2);
	}
	g.setColor(tmp);
}


}
