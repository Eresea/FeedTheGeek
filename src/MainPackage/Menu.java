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
	private WindowGame wG;
	
	public Menu(int state)
	{
		
	}
	
	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException
	{
		WindowGame.changeMusic(0);
		wG = ((WindowGame)(sbg));
		
		//System.out.println(" DEAD : " + wG.dead);
		if(wG.dead)changeMenu(4);
		if(menu != 4) changeMenu(0);
		
	}
	
	public void changeMenu(int m)
	{
		if(menu == 4) wG.dead = false;
		menu = m;
		if(m == 2) loadMenu.reset();
	}
	
	public void init(GameContainer gc,StateBasedGame sbg) throws SlickException // Initialisation de l'objet
	{
		myFont = gc.getDefaultFont();
		NewGame = new Button(660,300,600,140,gc);
		NewGame.text = "Nouvelle Partie";
		LoadGame = new Button(660,450,600,140,gc);
		LoadGame.text = "Charger";
		OptionsButton = new Button(660,600,600,140,gc);
		OptionsButton.text = "Options";
		ExitButton = new Button(660,750,600,140,gc);
		ExitButton.text = "Quitter";
		
		loadMenu = new LoadMenu(gc,this);
		optionsMenu = new OptionsMenu(gc,this);
		newGameMenu = new NewGameMenu(gc,this);
	}	
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException // Affichage des éléments graphiques
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
		case 4:
			g.setBackground(Color.black);
			g.drawString("Vous êtes mort", (float)(UIComponent.width*.5-(myFont.getWidth("Vous êtes mort")/2)),(float)(UIComponent.top+(UIComponent.height*.3)));
			g.drawString("Appuyez sur une touche", (float)(UIComponent.width*.5-(myFont.getWidth("Appuyer sur une touche")/2)),(float)(UIComponent.top+(UIComponent.height*.6)));
			break;
		}
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException // Gestion des entrées / sorties
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
			
			break;
		}
	}
	
	public void keyPressed(int key, char c) // Appui d'une touche du clavier
	{
		if(key == Input.KEY_ESCAPE)
		{
			changeMenu(0);
		}
		if( menu == 4) changeMenu(0);
	}
	
	public int getID()
	{
		return 0;
	}
}
