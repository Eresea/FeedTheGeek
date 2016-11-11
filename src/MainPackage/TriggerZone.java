package MainPackage;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.geom.*;
import org.newdawn.slick.*;

public class TriggerZone extends UIComponent {
	
	public boolean visible = true;
	public boolean enabled = true;
	public Color FillColor = Color.white;
	private Shape TriggerZone;
	
	TriggerZone(int x,int y,int w, int h,GameContainer gc)
	{
		super(x,y,w,h,gc);
	}
	
	public void render(Graphics g)
	{
		Color tmp = g.getColor();
		g.setColor(FillColor);		
		if(TriggerZone != null)
		{
			g.fill(TriggerZone);
		}
		else g.fillRect(width*x, top+(height*y), width*w, height*h);
		g.setColor(tmp);
	}
	
	public void setShape(Shape s)
	{
		TriggerZone = s;
	}
	
	public boolean Hover()
	{
		gc.getInput();
		int mouseX = Mouse.getX();
		int mouseY = gc.getHeight()-Mouse.getY();
		
		if(TriggerZone != null)
		{
			return enabled && TriggerZone.contains(mouseX, mouseY);
		}
		else return enabled && (mouseX > width*x && mouseX < width*x+width*w) &&(mouseY < top+(height*y)+height*h && mouseY > top+(height*y));
	}
}
