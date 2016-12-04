package MainPackage;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class TypingGame {
	int maxActors = 6;
	int speed = 2;
	public int points = 0;
	List<TypingActor> Actors;
	GameContainer gc;

	public TypingGame(GameContainer gc)
	{
		this.gc = gc;
		Actors = new ArrayList<TypingActor>();
	}
	
	public void Tick()
	{
		for(int i=0;i<maxActors;i++)
		{
			if(Actors.size() > i)
			{
				if(Actors.get(i).Tick())
				{
					Actors.remove(i);
					points--;
					Actors.add(new TypingActor("testing",randPos(gc.getDefaultFont(),"testing"),speed,gc));
				}
			}
			else Actors.add(new TypingActor("testing",randPos(gc.getDefaultFont(),"testing"),speed,gc));
		}
	}
	
	public void keyPressed(char c)
	{
		for(int i=0;i<Actors.size();i++)
		{
			if(Actors.get(i).typed(c))
			{
				Actors.remove(i);
				points++;
			}
		}
	}
	
	public void render(Graphics g)
	{
		for(int i=0;i<Actors.size();i++)
		{
			Actors.get(i).render(g);
		}
		
		g.drawString("Points : " + points, 0, 0);
	}
	
	private int randPos(Font f,String s)
	{
		int w = f.getWidth(s);
		int h = f.getHeight(s);
		
		int rand = -1;
		while(rand < 0)
		{
			rand = ThreadLocalRandom.current().nextInt(0,gc.getWidth());
			for(int i=0;i<Actors.size();i++)
			{
				if(Actors.get(i).isAreaUsed(rand, 0, w, h)) rand = -1;
			}
		}
		return rand;
	}
}

class TypingActor {
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
	
	public boolean Tick()
	{
		y += speed;
		return y > maxY;
	}
	
	public boolean isAreaUsed(int x,int y,int w,int h)
	{
		return false; // To finish
	}
	
	public void render(Graphics g)
	{
		Color tmp = g.getColor();
		g.setColor(BackgroundColor);
		g.fillRect(x, y, myFont.getWidth(word), myFont.getHeight(word));
		g.setColor(TextColor);
		g.drawString(word, x, y);
		g.setColor(DoneColor);
		g.drawString(typedWord, x, y);
		g.setColor(tmp);
	}
	
	public boolean typed(char c)
	{
		if(word.charAt(typedWord.length()) == c)
		{
			typedWord += c;
			return typedWord.length() == word.length();
		}
		return false;
	}
}
