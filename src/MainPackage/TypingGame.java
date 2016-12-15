package MainPackage;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.*;
import java.nio.file.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class TypingGame {
	int maxActors = 6;
	int speed = 2;
	public int points = 0;
	List<TypingActor> Actors;
	GameContainer gc;
	List<String> words;

	public TypingGame(GameContainer gc) // Mini jeu o� il faut taper les mots s'affichant � l'�cran avant qu'ils n'atteignent le bas de l'�cran
	{
		this.gc = gc;
		Actors = new ArrayList<TypingActor>();
		//words = new ArrayList<String>
		
		String tmp = WindowGame.getConfig(1); // R�cup�re la ligne 1 du fichier config (nombre d'actors � chaque instant dans ce mini jeu)
		if(tmp != "")
		{
			maxActors = Integer.parseInt(tmp);
		}
		tmp = WindowGame.getConfig(2); // R�cup�re la ligne du fichier config d�terminant la vitesse des actors du mini jeu
		if(tmp != "")
		{
			speed = Integer.parseInt(tmp);
		}
		
		File f = new File("dictionary.txt");
		try{
			words = Files.readAllLines(f.toPath()); // R�cup�re les lignes du dictionnaire
			} catch(IOException e)
			{
				System.out.println(e.getMessage());
			}
		if(words.size() == 0) words.add("No dictionary");
		
	}
	
	public void Tick() // Tic de l'horloge
	{
		for(int i=0;i<maxActors;i++) // Pour chaque actors 'n�cessaire'
		{
			int randI = RandInRange(0,words.size()); // Valeur al�atoire entre 0 et le nombre de mots du dictionnaire
			if(Actors.size() > i) // Si l'actor i n'existe pas
			{
				if(Actors.get(i).Tick()) // Si la fonction retourne vrai alors l'actor doit �tre supprim�
				{
					Actors.remove(i);
					points--;
					Actors.add(new TypingActor(words.get(randI),randPos(gc.getDefaultFont(),words.get(randI)),speed,gc));
				}
			}
			else Actors.add(new TypingActor(words.get(randI),randPos(gc.getDefaultFont(),words.get(randI)),speed,gc));
		}
	}
	
	public void keyPressed(char c) // Ev�nement de touche du clavier
	{
		for(int i=0;i<Actors.size();i++) // Pour chaque actor
		{
			if(Actors.get(i).typed(c)) // Lance la fonction qui v�rifie si cette touche �t� attendue, si retourne vrai alors le mot est complet
			{
				Actors.remove(i); // Supprime l'actor
				points++; // Ajout un point au score
			}
		}
	}
	
	public void render(Graphics g) // Affichage du jeu
	{
		for(int i=0;i<Actors.size();i++) // Affichage des diff�rents mots
		{
			Actors.get(i).render(g);
		}
		
		g.drawString("Points : " + points, 0, 0); // Affiche les points
	}
	
	private int RandInRange(int min, int max) // Renvoi un entier al�atoire entre min et max 
	{
		return ThreadLocalRandom.current().nextInt(min,max);
	}
	
	private int randPos(Font f,String s) // Renvoi une position al�atoire pour la largeur de l'�cran
	{
		int w = f.getWidth(s);
		int h = f.getHeight(s);
		
		int rand = -1;
		rand = RandInRange(0,gc.getWidth());
		return rand;
		/*while(rand < 0) Partie utilisable dans une mise � jour avec la condition que la fonction isAreaUsed soit termin�e
		{
			rand = RandInRange(0,gc.getWidth());
			/for(int i=0;i<Actors.size();i++) 
			{
				if(Actors.get(i).isAreaUsed(rand, 0, w, h)) rand = -1;
			}
		}
		return rand;*/
	}
}

class TypingActor { // Mot qui s'affiche � taper pour gagner des points
	private String word = "";
	private String typedWord = "";
	
	private Color TextColor = Color.white;
	private Color DoneColor = Color.green;
	private Color BackgroundColor = Color.black;
	private Font myFont;
	
	private int x,y,maxY,speed;
	private GameContainer gc;
	
	public TypingActor(String word,int x,int speed,GameContainer gc)
	{
		this.word = word;
		this.x = x;
		this.gc = gc;
		this.speed = speed;
		y = 0;
		maxY = gc.getHeight();
		myFont = gc.getDefaultFont();
	}
	
	public boolean Tick() // D�place le personnage et renvoi vrai s'il d�passe de l'�cran en Y
	{
		y += speed;
		return y > maxY;
	}
	
	/*public boolean isAreaUsed(int x,int y,int w,int h) // Permettra de d�terminer si le point fourni est occup� par l'objet
	{
		return false; // To finish
	}*/
	
	public void render(Graphics g) // Affichage de l'�l�ment
	{
		Color tmp = g.getColor();
		g.setColor(BackgroundColor);
		g.fillRect(x, y, myFont.getWidth(word), myFont.getHeight(word)); // Rectangle de fond
		g.setColor(TextColor); // Remplace la couleur par la couleur de texte normal
		g.drawString(word, x, y); // Affiche le texte complet
		g.setColor(DoneColor); // Remplace la couleur par la couleur de texte actif
		g.drawString(typedWord, x, y); // Affiche le texte actif par dessus
		g.setColor(tmp);
	}
	
	public boolean typed(char c) // V�rifie si la touche tap�e est dans la continuit� du mot
	{
		if(word.charAt(typedWord.length()) == c) // Si le caract�re est la prochain demand�
		{
			typedWord += c;
			return typedWord.length() == word.length();
		}
		return false;
	}
}
