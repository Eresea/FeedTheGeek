package Utility;

import org.newdawn.slick.Graphics;
import MainPackage.UIComponent;
import org.newdawn.slick.*;
import java.util.*;
import MainPackage.Button;

public class Dialog {
	private String text;
	private List<String> options;
	private List<Button> Buttons;
	private float x,y,w,h;
	public Color BackgroundColor = Color.gray;
	private GameContainer gc;
	public int id = -1;
	
	public Dialog(String text,String[] options,int x,int y, int w,int h,GameContainer gc)
	{
		this.text = text;
		this.options = Arrays.asList(options);
		this.x = x/1920.0f;
		this.y = y/1080.0f;
		this.w = w/1920.0f;
		this.h = h/1080.0f;
		this.gc = gc;
		
		
		for(int i=0;i<options.length;i++)
		{
			int x1 = i*(w/options.length);
			int y1 = (int)(y+(y*0.65));
			Button b = new Button(x1,y1,w/options.length,(int)(h*0.15),gc);
			Buttons.add(b);
		}
	}
	
	public void render(Graphics g)
	{
		g.fillRect(UIComponent.width*x, UIComponent.top+(UIComponent.height*y), UIComponent.width*w, UIComponent.height*h);
		for(int i=0;i<Buttons.size();i++)
		{
			Buttons.get(i).render(g);
		}
	}
	
	public int update()
	{
		for(int i=0;i<Buttons.size();i++)
		{
			if(Buttons.get(i).Hover()) return i;
		}
		return -1;
	}
}
