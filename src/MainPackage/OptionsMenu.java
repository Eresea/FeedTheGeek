package MainPackage;

import java.awt.Toolkit;
import java.util.*;
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
	
	private ArrayList<String> Resolutions;
	private int index = 5;
	//private String Graphics[];
	
	public OptionsMenu(int state)
	{
		
	}
	
	public void init(GameContainer gc,StateBasedGame sbg) throws SlickException
	{
		Resolutions = new ArrayList<String>(Arrays.asList("800x600","1280x720","1366x768","1600x900","1920x1080","2715x1527"));
		
		myFont = gc.getDefaultFont();
		BackButton = new Button(-(gc.getWidth()/8),(gc.getHeight()/2)-100,gc.getWidth()/4,80,gc);
		BackButton.text = "Back";
		LowerResButton = new Button(-200,-100,50,50,gc);
		LowerResButton.text = "<";
		HigherResButton = new Button(150,-100,50,50,gc);
		HigherResButton.text = ">";
		HigherResButton.visible = false;
		
		FullScreenCB = new CheckBox(0,0,16,gc);
		FullScreenCB.checked=gc.isFullscreen();
		FullScreenCB.text = "Fullscreen : ";
		
		int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		int height = Toolkit.getDefaultToolkit().getScreenSize().height;
		int i = Resolutions.size()-1;
		while(Integer.parseInt(Resolutions.get(i).split("x")[0]) > width || Integer.parseInt(Resolutions.get(i).split("x")[1]) >height)
		{
			Resolutions.remove(i);
			i--;
			index = i;
		}		
	}
	
	/*public void GameStateUpdate(GameContainer gc, StateBasedGame sbg, int arg0) 
	{
		 
	}*/
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
	{
		g.drawString(title, gc.getWidth()/2-(myFont.getWidth(title)/2), 50);
		BackButton.render(g);
		
		g.drawString(String.valueOf(Resolutions.get(index)), gc.getWidth()/2-(myFont.getWidth(Resolutions.get(index))/2), gc.getHeight()/2-100+(myFont.getHeight(Resolutions.get(index))));
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
					app.setDisplayMode(Integer.parseInt(Resolutions.get(index).split("x")[0]), Integer.parseInt(Resolutions.get(index).split("x")[1]), FullScreenCB.checked);
					ChangedResolution(gc);
					if(index < Resolutions.size()) HigherResButton.visible = true;
					if(index == 0) LowerResButton.visible = false;
				}
			}
			if(HigherResButton.Hover())
			{
				if(index < Resolutions.size()-1)
				{
					index++;
					AppGameContainer app = (AppGameContainer) gc;
					app.setDisplayMode(Integer.parseInt(Resolutions.get(index).split("x")[0]), Integer.parseInt(Resolutions.get(index).split("x")[1]), FullScreenCB.checked);
					ChangedResolution(gc);
					if(index == 1) LowerResButton.visible = true;
					if(index == Resolutions.size()-1) HigherResButton.visible = false;
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
