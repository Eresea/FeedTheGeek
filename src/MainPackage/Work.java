package MainPackage;

import java.util.Timer;
import java.util.TimerTask;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class Work extends BasicGameState {
	private Color BackgroundColor = Color.black;
	private float sx,sy;
	Timer timer = new Timer();
	TypingGame miniGame;
	StateBasedGame sbg;
	
	public Work(int state)
	{
		
	}
	
	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException // Entr�e du joueur dans cette classe (State)
	{
		WindowGame.changeMusic(0);
		miniGame = new TypingGame(gc);
		this.sbg = sbg;
		timer.schedule(new TimerTask() {
			  @Override
			  public void run() {
			   Tick();
			  }
			}, 100,100);
	}
	
	public void Tick() // Tick du monde du jeu (Permet d'avancer dans le temps chaque 0.1 seconde.)
	{
		miniGame.Tick();
	}
	
	public void init(GameContainer gc,StateBasedGame sbg) throws SlickException
	{
		
	}
	
	public void GameStateUpdate(GameContainer gc, StateBasedGame sbg, int arg0) 
	{
		
	}
	
	private void Resized(GameContainer gc) // Cette fonction est appel�e quand la fen�tre est redimensionn�e et permet de garder les �l�ments d'interface intacts ind�pendemment de la r�solution.
	{
		UIComponent.top = gc.getHeight()-Display.getHeight();
		UIComponent.width = Display.getWidth();
		UIComponent.height = Display.getHeight();
		sx = (float)(Display.getWidth())/(float)(gc.getWidth());
		sy = (float)(Display.getHeight())/(float)(gc.getHeight());
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException // Fonction d'affichage des �l�ments graphiques
	{
		//if(Display.wasResized()) Resized(gc);
		int width = (int)(UIComponent.height*(128.0f/140.0f));

		g.setBackground(BackgroundColor);
		
		//if(sy < sx) g.scale(sy,sy);
		//else g.scale(sx,sx);
		miniGame.render(g);
	}
	
	public void keyPressed(int key, char c) // Ev�nement : Touche du clavier
	{
		if(key == Input.KEY_ESCAPE)
		{
			WindowGame.hud.addMoney(miniGame.points);
			WindowGame.Save();
			sbg.enterState(1);
		}
		else
		{
			if(miniGame != null)
			{
				miniGame.keyPressed(c); // Touche press�e pour le mini jeu
			}
		}
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{
		
	}
	
	public int getID()
	{
		return 2;
	}

}
