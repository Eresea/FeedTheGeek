package MainPackage;

import java.util.*;
import org.lwjgl.Sys;
import org.newdawn.slick.geom.*;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;


public class Play extends BasicGameState {
	
	private SpriteSheet FondSprite;
	private Animation IdleGaming;

	private SpriteSheet HautSprite;
	private Animation HautAnim;
	
	private SpriteSheet ChaiseSprite;
	private Animation ChaiseAnim;
	
	private GameContainer gc;
	private StateBasedGame sbg;
	private Color BackgroundColor = new Color(0.74117647058f,0.74117647058f,0.74117647058f);
	public float health =1,hunger = 1; // Plus tard sera dans la classe personnage
	public float healthCurrent, hungerCurrent;
	float sx=1,sy=1;
	Timer timer = new Timer();
	
	Color ScreenColor = Color.gray; // A utiliser plus tard sur la zone de l'écran (avant le personnage)
	
	private HUD hud;
	
	public Play(int state)
	{
		
	}
	
	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException
	{
		FondSprite = new SpriteSheet("resources/fond.png",128,140);
		IdleGaming = new Animation(FondSprite,200);
		
		hud.updateValues(health, hunger);
		
		timer.schedule(new TimerTask() {
			  @Override
			  public void run() {
			   Tick();
			  }
			}, 1000,1000);
		
		/* couleur du Tshirt*/
		if (WindowGame.PrimaryColor==Color.blue)
		{
			  HautSprite = new SpriteSheet("resources/hautBleu.png",128,140);            
		}
		else if(WindowGame.PrimaryColor==Color.red)
		{
			  HautSprite = new SpriteSheet("resources/hautRouge.png",128,140);            
		}		
		else if(WindowGame.PrimaryColor==Color.green)
		{
			  HautSprite = new SpriteSheet("resources/hautVert.png",128,140);            
		}
		else if(WindowGame.PrimaryColor==Color.white)
		{
			  HautSprite = new SpriteSheet("resources/hautBlanc.png",128,140);            
		}
		else
		{
			HautSprite = new SpriteSheet("resources/hautRouge.png",128,140);
		}
		HautAnim = new Animation(HautSprite,200);
		
		/* couleur de la chaise*/
		if (WindowGame.SecondaryColor==Color.blue)
		{
			ChaiseSprite = new SpriteSheet("resources/chaiseBleu.png",128,140);            
		}
		else if(WindowGame.SecondaryColor==Color.red)
		{
			ChaiseSprite = new SpriteSheet("resources/chaiseRouge.png",128,140);            
		}		
		else if(WindowGame.SecondaryColor==Color.green)
		{
			ChaiseSprite = new SpriteSheet("resources/chaiseVert.png",128,140);            
		}
		else if(WindowGame.SecondaryColor==Color.white)
		{
			ChaiseSprite = new SpriteSheet("resources/chaiseBlanc.png",128,140);            
		}
		else
		{
			ChaiseSprite = new SpriteSheet("resources/chaiseNoir.png",128,140);
		}
		ChaiseAnim = new Animation(ChaiseSprite,200);

		
	}
	
	public void init(GameContainer gc,StateBasedGame sbg) throws SlickException
	{
		this.gc = gc;
		this.sbg = sbg;
		hud = new HUD(this,gc,sbg);
		WindowGame.hud = hud;
	}
	
	public void Tick()
	{
		hungerCurrent = hunger-((float)(WindowGame.timePassed()) / (float)(WindowGame.confTimeToDie));
		healthCurrent = health;
		if(hungerCurrent <= 0)
		{
			health -= 0.1;
			//healthCurrent +=  hungerCurrent;
			hungerCurrent = 0;
			if(healthCurrent <= 0)
			{
				healthCurrent = 0;
				WindowGame.Death();
				timer.cancel();
			}
		}
		hud.updateValues(Math.min(1,healthCurrent), Math.min(1,hungerCurrent));
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
	
		HautAnim.draw(UIComponent.width*(466.5f/1920),UIComponent.top+(UIComponent.height*(0)),UIComponent.width*(987.0f/1920),UIComponent.height*(1));
		g.setBackground(BackgroundColor);
		hud.render(g);
		
		ChaiseAnim.draw(UIComponent.width*(466.5f/1920),UIComponent.top+(UIComponent.height*(0)),UIComponent.width*(987.0f/1920),UIComponent.height*(1));
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
