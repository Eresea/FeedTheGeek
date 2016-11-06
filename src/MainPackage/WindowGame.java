/**
 * 
 */
package MainPackage;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

/**
 * @author Eresea
 *
 */
public class WindowGame extends StateBasedGame {
	
	public static void Save()
	{
		System.out.println("test");
	}

	private GameContainer container;
	public static final int MainMenu = 0;
	public static final int Play = 1;
	public static final int OptionsMenu = 2;
	private static AppGameContainer appgc;
	
	public WindowGame()
	{
		super("MainPackage :: WindowGame");
		this.addState(new Menu(MainMenu));
		this.addState(new Play(Play));
		this.addState(new OptionsMenu(OptionsMenu));
	}
	
	public void initStatesList(GameContainer gc) throws SlickException
	{
		this.getState(MainMenu).init(gc, this);
		this.getState(Play).init(gc, this);
		this.getState(OptionsMenu).init(gc, this);
		this.enterState(MainMenu);
	}
	
	 /*@Override
	    public void keyReleased(int key, char c) {
	        if (Input.KEY_ESCAPE == key) {
	        	container.exit();
	        }
	 }*/
	
	 @Override
	    public boolean closeRequested()
	    {
		 Save();
	      System.exit(0); // Use this if you want to quit the app.
	      return false;
	    }

	 public static void main(String[] args) throws SlickException {
		 appgc = new AppGameContainer(new WindowGame(),1920,1080,true);
		 appgc.start();
	 		}
}
