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

	 public static void main(String[] args) throws SlickException {
		 new AppGameContainer(new WindowGame(),640,480,false).start();
	 		}
}
