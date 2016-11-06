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
	private CheckBox FullScreenCB;
	
	
	private String Resolutions[] = { "640x480","1280x720","1920x1080" };
	private int index = 2;
	//private String Graphics[];
	
	public OptionsMenu(int state)
	{
		
	}
	
	public void init(GameContainer gc,StateBasedGame sbg) throws SlickException
	{
		myFont = gc.getDefaultFont();
		BackButton = new Button(-(gc.getWidth()/8),(gc.getHeight()/2)-100,gc.getWidth()/4,80,gc);
		BackButton.text = "Back";
		LowerResButton = new Button(-200,-100,50,50,gc);
		LowerResButton.text = "<";
		HigherResButton = new Button(150,-100,50,50,gc);
		HigherResButton.text = ">";
		
		FullScreenCB = new CheckBox(0,0,16,gc);
		FullScreenCB.checked=gc.isFullscreen();
		FullScreenCB.text = "Fullscreen : ";
		
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
		FullScreenCB.render(g);
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
					app.setDisplayMode(Integer.parseInt(Resolutions[index].split("x")[0]), Integer.parseInt(Resolutions[index].split("x")[1]), FullScreenCB.checked);
					ChangedResolution(gc);
				}
			}
			if(HigherResButton.Hover())
			{
				if(index < Resolutions.length)
				{
					index++;
					AppGameContainer app = (AppGameContainer) gc;
					app.setDisplayMode(Integer.parseInt(Resolutions[index].split("x")[0]), Integer.parseInt(Resolutions[index].split("x")[1]), FullScreenCB.checked);
					ChangedResolution(gc);
				}
			}
			if(FullScreenCB.Hover())
			{
				FullScreenCB.checked = !FullScreenCB.checked;
				gc.setFullscreen(FullScreenCB.checked);
			}
		}
	}
	
	private void ChangedResolution(GameContainer gc)
	{
		BackButton.ReSet(-(gc.getWidth()/8),(gc.getHeight()/2)-100,gc.getWidth()/4,80);
		LowerResButton.ReSet(-200,-100,50,50);
		HigherResButton.ReSet(150,-100,50,50);
	}
	
	public int getID()
	{
		return 2;
	}
}
