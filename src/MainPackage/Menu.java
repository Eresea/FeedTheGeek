package MainPackage;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.font.*;

public class Menu extends BasicGameState {
	public String title = "Feed the Geek";
	private Font myFont;
	private Button PlayButton;
	private Button OptionsButton;
	private Button ExitButton;
	private Color BackgroundColor = Color.black;
	float sx =1, sy=1;

	public Menu(int state)
	{
		
	}
	
	public void init(GameContainer gc,StateBasedGame sbg) throws SlickException
	{
		myFont = gc.getDefaultFont();
		PlayButton = new Button(660,350,600,140,gc);
		PlayButton.text = "Play";
		OptionsButton = new Button(660,500,600,140,gc);
		OptionsButton.text = "Options";
		ExitButton = new Button(660,650,600,140,gc);
		ExitButton.text = "Exit";
	}
	
	/*public void GameStateUpdate(GameContainer gc, StateBasedGame sbg, int arg0) 
	{
		 
	}*/
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
	{
		if(Display.wasResized())
		{
			UIComponent.top = gc.getHeight()-Display.getHeight();
			UIComponent.width = Display.getWidth();
			UIComponent.height = Display.getHeight();
			sx = (float)(Display.getWidth())/(float)(gc.getWidth());
			sy = (float)(Display.getHeight())/(float)(gc.getHeight());
		}
		
		g.setBackground(BackgroundColor);
		g.drawString(title, Display.getWidth()/2-(myFont.getWidth(title)/2), UIComponent.top+50);
		PlayButton.render(g);
		OptionsButton.render(g);
		ExitButton.render(g);
		
		if(sy < sx) g.scale(sy,sy);
		else g.scale(sx,sx);
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{
		Input input = gc.getInput();
		
		if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON))
		{
			if(PlayButton.Hover()) sbg.enterState(1);
			if(OptionsButton.Hover()) sbg.enterState(2);
			if(ExitButton.Hover()) gc.exit();
		}
	}
	
	public int getID()
	{
		
		return 0;
	}
}
