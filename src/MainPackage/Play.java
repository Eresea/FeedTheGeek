package MainPackage;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class Play extends BasicGameState {
	
	private SpriteSheet IdleSprite;
	private Animation IdleGaming;
	
	public Play(int state)
	{
		
	}
	
	public void init(GameContainer gc,StateBasedGame sbg) throws SlickException
	{
		IdleSprite = new SpriteSheet("resources/IdleGaming.png",128,140);
		IdleGaming = new Animation(IdleSprite,200);
	}
	
	public void GameStateUpdate(GameContainer gc, StateBasedGame sbg, int arg0) 
	{
		 
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
	{
		g.drawString("Playing Mode", 0, 0);
		IdleGaming.draw(100, 100);
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
	}
	
	public int getID()
	{
		return 1;
	}
}
