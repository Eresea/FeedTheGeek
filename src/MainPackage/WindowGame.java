/**
 * 
 */
package MainPackage;

import java.awt.Toolkit;
import java.awt.Dimension;
import java.io.Writer;
import java.nio.file.Files;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.TimerTask;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.lwjgl.*;
import org.lwjgl.opengl.Display;
import Utility.Save;
import static java.lang.Math.toIntExact;

/**
 * @author Eresea
 *
 */
public class WindowGame extends StateBasedGame {
	public static String saveName = "";
	public static HUD hud = null;
	public static Save s;
	public static Color PrimaryColor = Color.black;
	public static Color SecondaryColor = Color.red;
	
	public static int confTimeToDie;
	
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
			readConfig();
			s = new Save("Saves/"+fi.getName());
			return s.LoadData(fi.getName());
		}
		System.out.println("Impossible de lire/écrire sur ce fichier.");
		return false;
	}
	
	public static void Death()
	{
		new java.util.Timer().schedule(new java.util.TimerTask() {
			@Override
			public void run() {
					s.delete();
				}
			},
			5000
		);
	}
	
	public static boolean readConfig()
	{
		File f = new File("config.ini");
		if(f.canRead())
		{
			try{
			List<String> lines = Files.readAllLines(f.toPath());
			confTimeToDie = Integer.parseInt(lines.get(0));
			return true;
			} catch(IOException e)
			{
				System.out.println(e.getMessage());
			}
			return false;
		}
		//else if(!f.exists()) createConfig();
		else return false;
	}
	
	public static int timePassed()
	{
		return (int)(Calendar.getInstance().getTime().getTime()/1000 - s.lastSave);
	}

	private GameContainer container;
	public static final int MainMenu = 0;
	public static final int Play = 1;
	public static final int Work = 2;
	private static AppGameContainer appgc;
	
	public WindowGame()
	{
		super("MainPackage :: WindowGame");
		this.addState(new Menu(MainMenu));
		this.addState(new Play(Play));
		this.addState(new Work(Work));
	}
	
	public void initStatesList(GameContainer gc) throws SlickException
	{
		this.getState(MainMenu).init(gc, this);
		this.getState(Play).init(gc, this);
		this.getState(Work).init(gc, this);
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
