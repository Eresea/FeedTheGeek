package MainPackage;

import org.lwjgl.Sys;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;


public class Play extends BasicGameState {
	
	private SpriteSheet IdleSprite;
	private Animation IdleGaming;
	private GameContainer gc;
	private StateBasedGame sbg;
	
	public Play(int state)
	{
		
	}
	
	public void init(GameContainer gc,StateBasedGame sbg) throws SlickException
	{
		IdleSprite = new SpriteSheet("resources/IdleGaming.png",128,140);
		IdleGaming = new Animation(IdleSprite,200);
		this.gc = gc;
		this.sbg = sbg;
	}
	
	public void GameStateUpdate(GameContainer gc, StateBasedGame sbg, int arg0) 
	{
		 
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
	{
		g.drawString("Playing Mode", 0, 0);
		IdleGaming.draw((gc.getWidth()-(int)(gc.getWidth()/1.75))/2, (gc.getHeight()-(int)(gc.getHeight()/1.2))/2,(int)(gc.getWidth()/1.75),(int)(gc.getHeight()/1.2));
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{
		IdleGaming.update(delta);
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
			if(DialogBox.DialogBox("Quitter","�tes-vous s�r de vouloir quitter le jeu ?") == 0)
			{
				sbg.closeRequested();
			}
		}
	}
	
	public int getID()
	{
		return 1;
	}
}
