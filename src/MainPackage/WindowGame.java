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
	public static String saveName = ""; // Nom du personnage/Sauvegarde
	public static HUD hud = null; // L'objet HUD permettant d'affichant l'interface graphique dans l'état 1 (classe Play)
	public static Save s;
	public static Color PrimaryColor = Color.black;
	public static Color SecondaryColor = Color.red;
	public static Music backgroundMusic; // Musique de fond
	
	public static int confTimeToDie; // Le temps nécessaire pour que la barre de faim passe de 1 à 0 sans interractions
	public boolean dead = false; // Si le joueur actif est mort
	private static int music = -1; // Index de la musique qui joue
	
	public static void Save() // Appelle la sauvegarde du jeu actuel
	{
		s.SaveToFile();
	}
	
	public static String getConfig(int line) // Retourne la valeur d'une ligne du fichier config
	{
		File f = new File("config.ini");
		if(f.canRead())
		{
			try{
			List<String> lines = Files.readAllLines(f.toPath()); // Lit les lignes du fichier config
			return lines.get(line); // Retourne la ligne demandée
			} catch(IOException e)
			{
				System.out.println("GetConfig : "  + e.getMessage());
			}
		}
		return "";
	}
	
	public static boolean Load(File fi) // Charge une partie à partir d'un fichier
	{
		if(fi.canRead())
		{
			System.out.println("Loading " + fi.getName());
			readConfig(); // Lit le fichier config pour charger une partie
			s = new Save("Saves/"+fi.getName());
			return s.LoadData(fi.getName());
		}
		System.out.println("Impossible de lire/écrire sur ce fichier.");
		return false;
	}
	
	public void Death() // Mort du personnage
	{
		System.out.println("Death");
		dead = true;
		enterState(0);
		s.delete(); // Supprime la sauvegarde du personnage mort
	}
	
	public static boolean readConfig() // Lit le fichier config et attribue ses valeurs
	{
		String tmp = getConfig(0);
		if(tmp != "")
		{
			confTimeToDie = Integer.parseInt(tmp);
			return true;
		}
		return false;
	}
	
	public static void changeMusic(int title) // Change la musique qui joue
	{
		if(title != music) // Uniquement si elle n'est pas déjà en train de jouer le même morceau
		{
			try {
				switch(title)
				{
				case 0:
					backgroundMusic = new Music("resources/DungeonMaster.wav");
					break;
				case 1:
					backgroundMusic = new Music("resources/Typing.wav");
					break;
				}
				music = title;
				backgroundMusic.loop();
			} catch(SlickException e)
			{
				System.out.println("Erreur de changement de musique : " + e.getMessage());
			}
		}
	}
	
	public static int timePassed() // Retourne le temps passé entre la sauvegarde et le temps actuel
	{
		return (int)(Calendar.getInstance().getTime().getTime()/1000 - s.lastSave); // Calendar.getInstance().getTime()/1000 renvoi la date actuelle en secondes
	}
	/* Initialisation du jeu */
	private GameContainer container;
	public static final int MainMenu = 0;
	public static final int Play = 1;
	public static final int Work = 2;
	private static AppGameContainer appgc;
	
	public WindowGame() // Cette classe ajoute les différents états du programme et initialise l'objet WindowGame
	{
		super("Feed The Geek"); // Création de l'objet StateBasedGame de la librairie Slick2D
		this.addState(new Menu(MainMenu)); // Charge les différents états
		this.addState(new Play(Play));
		this.addState(new Work(Work));
	}
	
	public void initStatesList(GameContainer gc) throws SlickException // Initialise les classes d'états (BasicGameState)
	{
		this.getState(MainMenu).init(gc, this);
		this.getState(Play).init(gc, this);
		this.getState(Work).init(gc, this);
		this.enterState(MainMenu); // Entre dans l'état menu principal
	}
	
	 @Override
	    public boolean closeRequested() // L'application reçoit un signal pour fermer
	    {
		 if(s != null) s.SaveToFile();
	      System.exit(0); // Use this if you want to quit the app.
	      return false;
	    }

	 public static void main(String[] args) throws SlickException {
		 changeMusic(0);
		 Display.setResizable(true); // La taille de la fenêtre est modifiable (Sur windows passer du mode plein écran à fenêtré bloque la taille de la fenêtre)
		 Dimension scrnsize = Toolkit.getDefaultToolkit().getScreenSize(); // Récupère la taille de l'écran
		 UIComponent.width = scrnsize.width;
		 UIComponent.height = scrnsize.height;
		 appgc = new AppGameContainer(new WindowGame(),scrnsize.width,scrnsize.height,true); // Crée l'objet Container qui va être passé dans chaque state
		 appgc.setVSync(true); // Fixe les images par secondes à la fréquence de l'écran entre autre
		 appgc.setTitle("Feed the Geek");
		 appgc.start(); // Commence la partie dans l'état Menu
	 	}
}
