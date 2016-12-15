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

	public TypingGame(GameContainer gc) // Mini jeu où il faut taper les mots s'affichant à l'écran avant qu'ils n'atteignent le bas de l'écran
	{
		this.gc = gc;
		Actors = new ArrayList<TypingActor>();
		//words = new ArrayList<String>
		
		String tmp = WindowGame.getConfig(1); // Récupère la ligne 1 du fichier config (nombre d'actors à chaque instant dans ce mini jeu)
		if(tmp != "")
		{
			maxActors = Integer.parseInt(tmp);
		}
		tmp = WindowGame.getConfig(2); // Récupère la ligne du fichier config déterminant la vitesse des actors du mini jeu
		if(tmp != "")
		{
			speed = Integer.parseInt(tmp);
		}
		
		File f = new File("dictionary.txt");
		try{
			words = Files.readAllLines(f.toPath()); // Récupère les lignes du dictionnaire
			} catch(IOException e)
			{
				System.out.println(e.getMessage());
			}
		if(words.size() == 0) words.add("No dictionary");
		
	}
	
	public void Tick() // Tic de l'horloge
	{
		for(int i=0;i<maxActors;i++) // Pour chaque actors 'nécessaire'
		{
			int randI = RandInRange(0,words.size()); // Valeur aléatoire entre 0 et le nombre de mots du dictionnaire
			if(Actors.size() > i) // Si l'actor i n'existe pas
			{
				if(Actors.get(i).Tick()) // Si la fonction retourne vrai alors l'actor doit être supprimé
				{
					Actors.remove(i);
					points--;
					Actors.add(new TypingActor(words.get(randI),randPos(gc.getDefaultFont(),words.get(randI)),speed,gc));
				}
			}
			else Actors.add(new TypingActor(words.get(randI),randPos(gc.getDefaultFont(),words.get(randI)),speed,gc));
		}
	}
	
	public void keyPressed(char c) // Evènement de touche du clavier
	{
		for(int i=0;i<Actors.size();i++) // Pour chaque actor
		{
			if(Actors.get(i).typed(c)) // Lance la fonction qui vérifie si cette touche été attendue, si retourne vrai alors le mot est complet
			{
				Actors.remove(i); // Supprime l'actor
				points++; // Ajout un point au score
			}
		}
	}
	
	public void render(Graphics g) // Affichage du jeu
	{
		for(int i=0;i<Actors.size();i++) // Affichage des différents mots
		{
			Actors.get(i).render(g);
		}
		
		g.drawString("Points : " + points, 0, 0); // Affiche les points
	}
	
	private int RandInRange(int min, int max) // Renvoi un entier aléatoire entre min et max 
	{
		return ThreadLocalRandom.current().nextInt(min,max);
	}
	
	private int randPos(Font f,String s) // Renvoi une position aléatoire pour la largeur de l'écran
	{
		int w = f.getWidth(s);
		int h = f.getHeight(s);
		
		int rand = -1;
		rand = RandInRange(0,gc.getWidth());
		return rand;
		/*while(rand < 0) Partie utilisable dans une mise à jour avec la condition que la fonction isAreaUsed soit terminée
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

class TypingActor { // Mot qui s'affiche à taper pour gagner des points
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
	
	public boolean Tick() // Déplace le personnage et renvoi vrai s'il dépasse de l'écran en Y
	{
		y += speed;
		return y > maxY;
	}
	
	/*public boolean isAreaUsed(int x,int y,int w,int h) // Permettra de déterminer si le point fourni est occupé par l'objet
	{
		return false; // To finish
	}*/
	
	public void render(Graphics g) // Affichage de l'élément
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
	
	public boolean typed(char c) // Vérifie si la touche tapée est dans la continuité du mot
	{
		if(word.charAt(typedWord.length()) == c) // Si le caractère est la prochain demandé
		{
			typedWord += c;
			return typedWord.length() == word.length();
		}
		return false;
	}
}
