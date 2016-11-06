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
	
	public Menu(int state)
	{
		
	}
	
	public void init(GameContainer gc,StateBasedGame sbg) throws SlickException
	{
		myFont = gc.getDefaultFont();
		PlayButton = new Button(-200,-100,400,80,gc);
		PlayButton.text = "Play";
		OptionsButton = new Button(-200,0,400,80,gc);
		OptionsButton.text = "Options";
		ExitButton = new Button(-200,100,400,80,gc);
		ExitButton.text = "Exit";
	}
	
	/*public void GameStateUpdate(GameContainer gc, StateBasedGame sbg, int arg0) 
	{
		 
	}*/
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
	{
		g.setBackground(BackgroundColor);
		g.drawString(title, gc.getWidth()/2-(myFont.getWidth(title)/2), 50);
		PlayButton.render(g);
		OptionsButton.render(g);
		ExitButton.render(g);
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
