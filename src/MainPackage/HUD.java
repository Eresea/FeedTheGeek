package MainPackage;

import org.newdawn.slick.*;

public class HUD {
private Play p;

private Bar healthBar;
private Bar hungerBar;

private GameContainer gc;

	HUD(Play p, GameContainer gc)
	{
		this.p = p;
		this.gc = gc;
		healthBar = new Bar(-gc.getWidth()/2,-450,250,35,gc);
		hungerBar = new Bar(-gc.getWidth()/2,-400,250,35,gc);
	}

	void render(Graphics g)
	{
		int centerX = gc.getWidth()/2;
		int centerY = gc.getHeight()/2;
		
		healthBar.render(g);
		hungerBar.render(g);
		g.setColor(Color.black);
		g.drawString("Health", centerX-gc.getWidth()/2, centerY-(450-8));
		g.drawString("Hunger", centerX-gc.getWidth()/2, centerY-(400-8));
		g.setColor(Color.white);
	}
	
	void updateValues(float health, float hunger)
	{
		healthBar.setValue(health);
		hungerBar.setValue(hunger);
	}

	void update()
	{
		
	}
}
