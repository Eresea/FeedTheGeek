package MainPackage;

import java.awt.Toolkit;
import java.util.*;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.font.*;
import org.newdawn.slick.GameContainer;

public class OptionsMenu extends BasicGameState {
	
	public String title = "Options Menu";
	private Font myFont;
	private Button BackButton;
	private CheckBox FullScreenCB;
	//private String Graphics[];
	float sx = 1,sy = 1; // Scaling from window
	
	public OptionsMenu(int state)
	{
		
	}
	
	public void init(GameContainer gc,StateBasedGame sbg) throws SlickException
	{
		myFont = gc.getDefaultFont();
		BackButton = new Button(660,940,600,100,gc);
		BackButton.text = "Back";
		
		FullScreenCB = new CheckBox(952,232,16,gc);
		FullScreenCB.checked=gc.isFullscreen();
		FullScreenCB.text = "Fullscreen : ";
	}
	
	/*public void GameStateUpdate(GameContainer gc, StateBasedGame sbg, int arg0) 
	{
		 
	}*/
	private void Resized(GameContainer gc)
	{
		UIComponent.top = gc.getHeight()-Display.getHeight();
		UIComponent.width = Display.getWidth();
		UIComponent.height = Display.getHeight();
		sx = (float)(Display.getWidth())/(float)(gc.getWidth());
		sy = (float)(Display.getHeight())/(float)(gc.getHeight());
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
	{
		if(Display.wasResized()) Resized(gc);
		g.drawRect(5, 5+UIComponent.top, UIComponent.width-10, UIComponent.height-10);
		
		//g.drawString(title, gc.getWidth()/2-(myFont.getWidth(title)/2), 50);
		BackButton.render(g);
		FullScreenCB.render(g);
		
		if(sy < sx) g.scale(sy,sy);
		else g.scale(sx,sx);
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{
		if(Display.wasResized()) Resized(gc);
		Input input = gc.getInput();
		
		if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON))
		{
			if(BackButton.Hover()) sbg.enterState(0);
			if(FullScreenCB.Hover())
			{
				FullScreenCB.checked = !FullScreenCB.checked;
				gc.setFullscreen(FullScreenCB.checked);
				Resized(gc);
			}
		}
	}
	
	public int getID()
	{
		return 2;
	}
}
