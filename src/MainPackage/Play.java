package MainPackage;

import org.lwjgl.Sys;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;


public class Play extends BasicGameState {
	
	private SpriteSheet IdleSprite;
	private Animation IdleGaming;
	private GameContainer gc;
	private StateBasedGame sbg;
	private int width;
	private Color BackgroundColor = new Color(0.74117647058f,0.74117647058f,0.74117647058f);
	
	private float health =1,hunger = 1; // Plus tard sera dans la classe personnage
	
	private HUD hud;
	
	public Play(int state)
	{
		
	}
	
	public void init(GameContainer gc,StateBasedGame sbg) throws SlickException
	{
		IdleSprite = new SpriteSheet("resources/IdleGaming.png",128,140);
		IdleGaming = new Animation(IdleSprite,200);
		this.gc = gc;
		this.sbg = sbg;
		hud = new HUD(this,gc);
	}
	
	public void Tick()
	{ //plus tard appeler la fonction tick dans personnage par exemple
		hunger -= 0.00002;
		if(hunger <= 0)
		{
			hunger = 0;
			health -= 0.0001;
		}
		hud.updateValues(health, hunger);
	}
	
	public void GameStateUpdate(GameContainer gc, StateBasedGame sbg, int arg0) 
	{
		
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
	{
		IdleGaming.draw((gc.getWidth()-width)/2, 75, width, gc.getHeight()-150);
		g.setBackground(BackgroundColor);
		hud.render(g);
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{
		IdleGaming.update(delta);
		width = ((int)((gc.getHeight()-150)*(128.0f/140.0f))/10)*10; // (/10 *10) : Arrondir
		hud.update();
		Tick();
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
			sbg.enterState(0);
		}
	}
	
	public int getID()
	{
		return 1;
	}
}
