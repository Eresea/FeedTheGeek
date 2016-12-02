package MainPackage;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.*;
import org.newdawn.slick.gui.TextField;

public class TextBox extends UIComponent{

	Font myFont;
	GameContainer gc;
	TextField tField;
	String text;
	boolean hasFocus = false;
	
	Color BackgroundColor = Color.gray;
	
	public TextBox(int x,int y,int w,Font f,GameContainer gc)
	{
		super(x,y,w,(int)((float)(f.getHeight("Test"))*1.5f),gc);		
		myFont = f;
		this.gc = gc;
		tField = new TextField(gc,f,(int)(width*x/1920.0f),(int)(top+(height*y/1080.0f)),(int)(width*(w/1920.0f)),(int)(height*h*1.35));
		tField.setBackgroundColor(BackgroundColor);
	}
	
	public void BackGroundColor(Color bgc)
	{
		BackgroundColor = bgc;
		tField.setBackgroundColor(BackgroundColor);
	}
	
	public String getText()
	{
		return tField.getText().isEmpty() ? "" : tField.getText();
	}
	
	public void render(Graphics g)
	{
		tField.render(gc, g);
		if(text != "")
		{
			g.drawString(text, (int)(width*x)-myFont.getWidth(text),(int)(y*height));
		}
	}
	
	public void setFocus(boolean focus)
	{
		hasFocus = focus;
	}
	
	public void update(Input input)
	{
		int mouseX = Mouse.getX();
		int mouseY = gc.getHeight()-Mouse.getY();
		
		if((mouseX > width*x && mouseX < width*x+width*w) && (mouseY < top+(height*y)+height*h && mouseY > top+(height*y)))
		{
			if(!tField.hasFocus())
			{
				if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON))
				{
					setFocus(true);
				}
			}
		}
		if(hasFocus)
		{
			if(input.isKeyPressed(input.KEY_ESCAPE))
			{
				System.out.println("test");
				setFocus(false);
	        }
			else if(input.isKeyPressed(input.KEY_ENTER))
			{
	            setFocus(false);
	        }
			tField.setFocus(hasFocus);
		}
		
	}
}
