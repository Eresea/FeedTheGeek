/**
 * 
 */
package MainPackage;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.lwjgl.*;
import org.lwjgl.opengl.Display;

/**
 * @author Eresea
 *
 */
public class WindowGame extends StateBasedGame {
	public static String saveName = "";
	
	public static File Save()
	{
		
		System.out.println("test");
		return null;
	}
	
	public static void Load(File fi)
	{
		if(fi.canRead())
		{
			System.out.println("Loading " + fi.getName());
		}
		else System.out.println("Impossible de lire/écrire sur ce fichier.");
	}

	private GameContainer container;
	public static final int MainMenu = 0;
	public static final int Play = 1;
	private static AppGameContainer appgc;
	
	public WindowGame()
	{
		super("MainPackage :: WindowGame");
		this.addState(new Menu(MainMenu));
		this.addState(new Play(Play));
	}
	
	public void initStatesList(GameContainer gc) throws SlickException
	{
		this.getState(MainMenu).init(gc, this);
		this.getState(Play).init(gc, this);
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
		 Display.setResizable(true);
		 Dimension scrnsize = Toolkit.getDefaultToolkit().getScreenSize();
		 UIComponent.width = scrnsize.width;
		 UIComponent.height = scrnsize.height;
		 appgc = new AppGameContainer(new WindowGame(),scrnsize.width,scrnsize.height,true);
		 appgc.setVSync(true);
		 appgc.setTitle("Feed the Geek");
		 appgc.start();
	 	}
}
