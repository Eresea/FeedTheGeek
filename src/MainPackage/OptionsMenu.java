package MainPackage;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.font.*;
import org.newdawn.slick.GameContainer;

public class OptionsMenu {
	
	public String title = "Options Menu";
	private Font myFont;
	private Button BackButton;
	private CheckBox FullScreenCB;
	private Menu MainMenu;
	float sx = 1,sy = 1; // Scaling from window
	
	public OptionsMenu(GameContainer gc,Menu mM)
	{
		myFont = gc.getDefaultFont();
		BackButton = new Button(660,940,600,100,gc);
		BackButton.text = "Back";
		
		FullScreenCB = new CheckBox(952,232,16,gc);
		FullScreenCB.checked=gc.isFullscreen();
		FullScreenCB.text = "Fullscreen : ";
		
		MainMenu = mM;
	}
	
	/*public void GameStateUpdate(GameContainer gc, StateBasedGame sbg, int arg0) 
	{
		 
	}*/
	private void Resized(GameContainer gc) // Fonction permettant de garder l'apparence des éléments interfaces quel que soit la résolution
	{
		UIComponent.top = gc.getHeight()-Display.getHeight();
		UIComponent.width = Display.getWidth();
		UIComponent.height = Display.getHeight();
		sx = (float)(Display.getWidth())/(float)(gc.getWidth());
		sy = (float)(Display.getHeight())/(float)(gc.getHeight());
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException // Affichage des éléments graphiques de l'interface
	{
		if(Display.wasResized()) Resized(gc);
		g.drawRect(5, 5+UIComponent.top, UIComponent.width-10, UIComponent.height-10);
		
		//g.drawString(title, gc.getWidth()/2-(myFont.getWidth(title)/2), 50);
		BackButton.render(g);
		FullScreenCB.render(g);
		
		if(sy < sx) g.scale(sy,sy);
		else g.scale(sx,sx);
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException // Gestion I/O
	{
		if(Display.wasResized()) Resized(gc);
		Input input = gc.getInput();
		
		if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON))
		{
			if(BackButton.Hover()) MainMenu.changeMenu(0);
			if(FullScreenCB.Hover())
			{
				FullScreenCB.checked = !FullScreenCB.checked;
				gc.setFullscreen(FullScreenCB.checked);
				Resized(gc);
			}
		}
	}
	
	public int getID()
	{
		return 2;
	}
}
