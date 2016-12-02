package MainPackage;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

public class NewGameMenu {
	public String title = "Menu de Chargement";
	private Font myFont;
	float sx = 1,sy = 1; // Scaling from window
	private Menu MainMenu;
	
	private Button BackButton;
	private Button StartButton;
	private TextBox nomTBox;
	private int ColorIndex1 = 0;
	private int ColorIndex2 = 1;
	
	private Button LowerColorIndex1;
	private Button HigherColorIndex1;
	private Button LowerColorIndex2;
	private Button HigherColorIndex2;
	
	Color[] colors = {Color.black,Color.red,Color.blue,Color.green,Color.white};
	
	public NewGameMenu(GameContainer gc,Menu mM)
	{
		myFont = gc.getDefaultFont();
		MainMenu = mM;
		
		BackButton = new Button(430,940,500,100,gc);
		BackButton.text = "Back";
		StartButton = new Button(990,940,500,100,gc);
		StartButton.text = "Jouer";
		nomTBox = new TextBox(430,300,200,gc.getDefaultFont(),gc);
		nomTBox.text = "Nom : ";
		
		LowerColorIndex1 = new Button(715,275,50,50,gc);
		LowerColorIndex1.text = "<";
		LowerColorIndex1.visible = false;
		HigherColorIndex1 = new Button(835,275,50,50,gc);
		HigherColorIndex1.text = ">";
		
		LowerColorIndex2 = new Button(715,330,50,50,gc);
		LowerColorIndex2.text = "<";
		HigherColorIndex2 = new Button(835,330,50,50,gc);
		HigherColorIndex2.text = ">";
	}
	
	private void Resized(GameContainer gc)
	{
		UIComponent.top = gc.getHeight()-Display.getHeight();
		UIComponent.width = Display.getWidth();
		UIComponent.height = Display.getHeight();
		sx = (float)(Display.getWidth())/(float)(gc.getWidth());
		sy = (float)(Display.getHeight())/(float)(gc.getHeight());
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
	{
		if(Display.wasResized()) Resized(gc);
		g.drawRect(5, 5+UIComponent.top, UIComponent.width-10, UIComponent.height-10);
		
		g.drawString("Nouvelle Partie", Display.getWidth()/2-(myFont.getWidth("Nouvelle partie")/2), UIComponent.top+50);
		
		BackButton.render(g);
		StartButton.render(g);
		nomTBox.render(g);
		
		LowerColorIndex1.render(g);
		LowerColorIndex2.render(g);
		HigherColorIndex1.render(g);
		HigherColorIndex2.render(g);
		
		g.drawString("Couleur principale : ", UIComponent.width*(690.0f/1920.0f)-myFont.getWidth("Couleur principale : "), UIComponent.top+(UIComponent.height*(300.0f/1080.0f))-myFont.getHeight("Couleur principale : ")/2);
		g.drawString("Couleur secondaire : ", UIComponent.width*(690.0f/1920.0f)-myFont.getWidth("Couleur secondaire : "), UIComponent.top+(UIComponent.height*(355.0f/1080.0f))-myFont.getHeight("Couleur secondaire : ")/2);
		Color tmp = g.getColor();
		g.setColor(colors[ColorIndex1]);
		g.fillRect(UIComponent.width*(775.0f/1920.0f), UIComponent.top+(UIComponent.height*(275.0f/1080.0f)), UIComponent.width*(50.0f/1920.0f), UIComponent.height*(50.0f/1080.0f));
		g.setColor(colors[ColorIndex2]);
		g.fillRect(UIComponent.width*(775.0f/1920.0f), UIComponent.top+(UIComponent.height*(330.0f/1080.0f)), UIComponent.width*(50.0f/1920.0f), UIComponent.height*(50.0f/1080.0f));
		g.setColor(tmp);
		g.drawRect(UIComponent.width*(775.0f/1920.0f), UIComponent.top+(UIComponent.height*(275.0f/1080.0f)), UIComponent.width*(50.0f/1920.0f), UIComponent.height*(50.0f/1080.0f));
		g.drawRect(UIComponent.width*(775.0f/1920.0f), UIComponent.top+(UIComponent.height*(330.0f/1080.0f)), UIComponent.width*(50.0f/1920.0f), UIComponent.height*(50.0f/1080.0f));
		
		if(sy < sx) g.scale(sy,sy);
		else g.scale(sx,sx);
	}
	
	private void changedColorIndex()
	{
		LowerColorIndex1.visible = !(ColorIndex1 == 0);
		LowerColorIndex2.visible = !(ColorIndex2 == 0);
		HigherColorIndex1.visible = !(ColorIndex1 == 4);
		HigherColorIndex2.visible = !(ColorIndex2 == 4);
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{
		if(Display.wasResized()) Resized(gc);
		Input input = gc.getInput();
		nomTBox.update(input);
		
		if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON))
		{
			if(BackButton.Hover()) MainMenu.changeMenu(0);
			else if(StartButton.Hover())
			{
				if(true) //Conditions
				{
					//WindowGame.Load(WindowGame.Save(/* Elements */)); //Sauvegarde et charge
					sbg.enterState(1);
				}
			}
			else if(LowerColorIndex1.Hover()){ColorIndex1--; changedColorIndex();}
			else if(HigherColorIndex1.Hover()){ColorIndex1++; changedColorIndex();}
			else if(LowerColorIndex2.Hover()){ColorIndex2--; changedColorIndex();}
			else if(HigherColorIndex2.Hover()){ColorIndex2++; changedColorIndex();}
		}
	}
}
