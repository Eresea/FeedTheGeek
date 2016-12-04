package MainPackage;

import java.util.*;
import org.lwjgl.Sys;
import org.newdawn.slick.geom.*;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;


public class Play extends BasicGameState {
	
	private SpriteSheet IdleSprite;
	private Animation IdleGaming;
	private GameContainer gc;
	private StateBasedGame sbg;
	private Color BackgroundColor = new Color(0.74117647058f,0.74117647058f,0.74117647058f);
	public float health =1,hunger = 1; // Plus tard sera dans la classe personnage
	float sx=1,sy=1;
	Timer timer = new Timer();
	
	Color ScreenColor = Color.gray; // A utiliser plus tard sur la zone de l'écran (avant le personnage)
	
	private HUD hud;
	
	public Play(int state)
	{
		
	}
	
	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException
	{
		IdleSprite = new SpriteSheet("resources/IdleGaming.png",128,140);
		IdleGaming = new Animation(IdleSprite,200);
		this.gc = gc;
		this.sbg = sbg;
		hud = new HUD(this,gc);
		
		timer.schedule(new TimerTask() {
			  @Override
			  public void run() {
			   Tick();
			  }
			}, 200,200);
		
		WindowGame.hud = hud;
	}
	
	public void init(GameContainer gc,StateBasedGame sbg) throws SlickException
	{
		
	}
	
	public void Tick()
	{ //plus tard appeler la fonction tick dans personnage par exemple
		hunger -= 0.001;
		if(hunger <= 0)
		{
			hunger = 0;
			health -= 0.001;
		}
		hud.updateValues(health, hunger);
	}
	
	public void GameStateUpdate(GameContainer gc, StateBasedGame sbg, int arg0) 
	{
		
	}
	
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
		int width = (int)(UIComponent.height*(128.0f/140.0f));

		//IdleGaming.draw(UIComponent.width/2-width/2,UIComponent.top,width,UIComponent.height);  Manière pour afficher avec ratio X/Y
		IdleGaming.draw(UIComponent.width*(466.5f/1920),UIComponent.top+(UIComponent.height*(0)),UIComponent.width*(987.0f/1920),UIComponent.height*(1));
		g.setBackground(BackgroundColor);
		hud.render(g);
		
		if(sy < sx) g.scale(sy,sy);
		else g.scale(sx,sx);
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{
		IdleGaming.update(delta);
		hud.update();
	}
	
	public void keyPressed(int key, char c)
	{
		if(key == Input.KEY_LEFT)
		{
			System.out.println("LEFT");
		}
		if(key == Input.KEY_ESCAPE)
		{
			//Sys.alert("Quitter ?", "�tes-vous s�r de vouloir quitter");
			/*if(DialogBox.DialogBox("Quitter","Etes-vous sur de vouloir quitter le jeu ?") == 0)
			{
				sbg.closeRequested();
			}*/
			WindowGame.Save();
			sbg.enterState(0);
		}
	}
	
	public int getID()
	{
		return 1;
	}
}
