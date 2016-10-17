package MainPackage;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.font.*;
import org.newdawn.slick.GameContainer;

public class OptionsMenu extends BasicGameState {
	
	public String title = "Options Menu";
	private Font myFont;
	private Button BackButton;
	private Button LowerResButton;
	private Button HigherResButton;
	
	private String Resolutions[] = { "640x480","960x720","1280x960" };
	private int index = 0;
	//private String Graphics[];
	
	public OptionsMenu(int state)
	{
		
	}
	
	public void init(GameContainer gc,StateBasedGame sbg) throws SlickException
	{
		myFont = gc.getDefaultFont();
		BackButton = new Button(-(gc.getWidth()/2)+100,(gc.getHeight()/2)-100,gc.getWidth()-200,80,gc);
		BackButton.text = "Back";
		LowerResButton = new Button(-200,-100,50,50,gc);
		LowerResButton.text = "<";
		HigherResButton = new Button(150,-100,50,50,gc);
		HigherResButton.text = ">";
		
	}
	
	/*public void GameStateUpdate(GameContainer gc, StateBasedGame sbg, int arg0) 
	{
		 
	}*/
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
	{
		g.drawString(title, gc.getWidth()/2-(myFont.getWidth(title)/2), 50);
		BackButton.render(g);
		
		g.drawString(String.valueOf(Resolutions[index]), gc.getWidth()/2-(myFont.getWidth(Resolutions[index])/2), gc.getHeight()/2-100+(myFont.getHeight(Resolutions[index])));
		LowerResButton.render(g);
		HigherResButton.render(g);
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{
		Input input = gc.getInput();

		if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON))
		{
			if(BackButton.Hover()) sbg.enterState(0);
			if(LowerResButton.Hover())
			{
				if(index > 0)
				{
					index--;
					AppGameContainer app = (AppGameContainer) gc;
					app.setDisplayMode(Integer.parseInt(Resolutions[index].split("x")[0]), Integer.parseInt(Resolutions[index].split("x")[1]), false);
					ChangedResolution(gc);
				}
			}
			if(HigherResButton.Hover())
			{
				if(index < Resolutions.length)
				{
					index++;
					AppGameContainer app = (AppGameContainer) gc;
					app.setDisplayMode(Integer.parseInt(Resolutions[index].split("x")[0]), Integer.parseInt(Resolutions[index].split("x")[1]), false);
					ChangedResolution(gc);
				}
			}
		}
	}
	
	private void ChangedResolution(GameContainer gc)
	{
		BackButton.ReSet(-(gc.getWidth()/2)+100,(gc.getHeight()/2)-100,gc.getWidth()-200,80);
		LowerResButton.ReSet(-200,-100,50,50);
		HigherResButton.ReSet(150,-100,50,50);
	}
	
	public int getID()
	{
		return 2;
	}
}
