package MainPackage;

import org.newdawn.slick.*;

public class HUD {
private Play p;
private Bar healthBar;
private Bar hungerBar;

private Assiette test;

private GameContainer gc;

	HUD(Play p, GameContainer gc)
	{
		this.p = p;
		this.gc = gc;
		healthBar = new Bar(0,40,250,80,gc);
		healthBar.text= "Health";
		hungerBar = new Bar(0,140,250,80,gc);
		hungerBar.text = "Hunger";
		test = new Assiette(10,5,5,gc);

	}

	void render(Graphics g)
	{
		int centerX = gc.getWidth()/2;
		int centerY = gc.getHeight()/2;
		
		healthBar.render(g);
		hungerBar.render(g);
		
		test.render(g);
	}
	
	void updateValues(float health, float hunger)
	{
		healthBar.setValue(health);
		hungerBar.setValue(hunger);
	}

	void update()
	{
Input input = gc.getInput();
		
		if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON))
		{
			if(test.Hover())
			{
				p.hunger +=  (float)(test.bouchee())/100.0f;
			}
		}
	}
}
