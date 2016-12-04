package MainPackage;

import javax.swing.JOptionPane;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import java.awt.EventQueue;
import org.newdawn.slick.font.*;

public class Menu extends BasicGameState {
	public String title = "Feed the Geek";
	private Font myFont;
	private Button NewGame;
	private Button LoadGame;	
	private Button OptionsButton;
	private Button ExitButton;
	private Color BackgroundColor = Color.black;
	float sx =1, sy=1;
	public int menu = 0;
	
	private LoadMenu loadMenu;
	private OptionsMenu optionsMenu;
	private NewGameMenu newGameMenu;
	
	public Menu(int state)
	{
		
	}
	
	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException
	{
		changeMenu(0);
	}
	
	public void changeMenu(int m)
	{
		menu = m;
	}
	
	public void init(GameContainer gc,StateBasedGame sbg) throws SlickException
	{
		myFont = gc.getDefaultFont();
		NewGame = new Button(660,300,600,140,gc);
		NewGame.text = "Nouvelle Partie";
		LoadGame = new Button(660,450,600,140,gc);
		LoadGame.text = "Charger";
		OptionsButton = new Button(660,600,600,140,gc);
		OptionsButton.text = "Options";
		ExitButton = new Button(660,750,600,140,gc);
		ExitButton.text = "Exit";
		
		loadMenu = new LoadMenu(gc,this);
		optionsMenu = new OptionsMenu(gc,this);
		newGameMenu = new NewGameMenu(gc,this);
	}	
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
	{
		g.setBackground(BackgroundColor);
		switch(menu)
		{
		case 0:
			if(Display.wasResized())
			{
				UIComponent.top = gc.getHeight()-Display.getHeight();
				UIComponent.width = Display.getWidth();
				UIComponent.height = Display.getHeight();
				sx = (float)(Display.getWidth())/(float)(gc.getWidth());
				sy = (float)(Display.getHeight())/(float)(gc.getHeight());
			}
			
			g.drawString(title, Display.getWidth()/2-(myFont.getWidth(title)/2), UIComponent.top+50);
			NewGame.render(g);
			LoadGame.render(g);
			OptionsButton.render(g);
			ExitButton.render(g);
			
			if(sy < sx) g.scale(sy,sy);
			else g.scale(sx,sx);
			break;
		case 1:
			optionsMenu.render(gc, sbg, g);
			break;
		case 2:
			loadMenu.render(gc, sbg, g);
			break;
		case 3:
			newGameMenu.render(gc, sbg, g);
			break;
		}
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{
		Input input = gc.getInput();
		
		switch(menu)
		{
		case 0:
			if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON))
			{
				if(NewGame.Hover()) changeMenu(3);
				if(OptionsButton.Hover()) changeMenu(1);
				if(LoadGame.Hover()) changeMenu(2);
				if(ExitButton.Hover()) sbg.closeRequested();
			}
			break;
		case 1:
			optionsMenu.update(gc, sbg, delta);
			break;
		case 2:
			loadMenu.update(gc, sbg, delta);
			break;
		case 3:
			newGameMenu.update(gc, sbg, delta);
			break;
		case 4:
			//Menu de pause
			break;
		}
	}
	
	public void keyPressed(int key, char c)
	{
		if(key == Input.KEY_ESCAPE)
		{
			changeMenu(0);
		}
	}
	
	public int getID()
	{
		return 0;
	}
}
