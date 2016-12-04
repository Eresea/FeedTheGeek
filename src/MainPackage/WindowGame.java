/**
 * 
 */
package MainPackage;

import java.awt.*;
import java.io.Writer;
import java.nio.file.Files;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.lwjgl.*;
import org.lwjgl.opengl.Display;
import Utility.Save;

/**
 * @author Eresea
 *
 */
public class WindowGame extends StateBasedGame {
	public static String saveName = "";
	public static HUD hud = null;
	public static Save s;
	
	public static File Save()
	{
		s.SaveToFile();
		
		return null;
	}
	
	public static boolean Load(File fi)
	{
		if(fi.canRead())
		{
			System.out.println("Loading " + fi.getName());
			s = new Save("Saves/"+fi.getName()+".sav");
			
			return true;
		}
		System.out.println("Impossible de lire/écrire sur ce fichier.");
		return false;
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
