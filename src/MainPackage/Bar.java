package MainPackage;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;

public class Bar {
public Color backColor = Color.red;
public Color frontColor = Color.green;

private float value;
int x,y,h,l;
GameContainer gc;

Bar(int x,int y, int l, int h,GameContainer gc)
{
	this.x = x;
	this.y = y;
	this.h = h;
	this.l = l;
	this.gc = gc;
}

public void setValue ( float f ) {
	   value = f;
	} 

void render(Graphics g)
{
	int centerX = gc.getWidth()/2;
	int centerY = gc.getHeight()/2;
	
	Color tmp = g.getColor();
	
	g.setColor(frontColor);
	g.fillRect(centerX+x, centerY+y, l*value, h);
	g.setColor(backColor);
	g.fillRect(centerX+x+(l*value), centerY+y, l*(1-value), h);
	g.setColor(tmp);
}


}
