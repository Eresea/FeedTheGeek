package MainPackage;

import org.lwjgl.Sys;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;


public class Play extends BasicGameState {
	
	private SpriteSheet IdleSprite;
	private Animation IdleGaming;
	private GameContainer gc;
	private int width;
	private Color BackgroundColor = new Color(0.74117647058f,0.74117647058f,0.74117647058f);
	
	
	public Play(int state)
	{
		
	}
	
	public void init(GameContainer gc,StateBasedGame sbg) throws SlickException
	{
		IdleSprite = new SpriteSheet("resources/IdleGaming.png",128,140);
		IdleGaming = new Animation(IdleSprite,200);
		this.gc = gc;
	}
	
	public void GameStateUpdate(GameContainer gc, StateBasedGame sbg, int arg0) 
	{
		
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
	{
		IdleGaming.draw((gc.getWidth()-width)/2, 75, width, gc.getHeight()-150);
		g.setBackground(BackgroundColor);
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{
		IdleGaming.update(delta);
		width = ((int)((gc.getHeight()-150)*(128.0f/140.0f))/10)*10; // (/10 *10) : Arrondir
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
			if(DialogBox.DialogBox("Quitter","Etes-vous sur de vouloir quitter le jeu ?") == 0)
			{
				WindowGame.Save();
				gc.exit();
			}
		}
	}
	
	public int getID()
	{
		return 1;
	}
}
