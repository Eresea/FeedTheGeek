package MainPackage;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.font.*;

import java.util.ArrayList;
import java.io.*;
import java.text.SimpleDateFormat;

public class LoadMenu {
	
	public String title = "Menu de Chargement";
	private Font myFont;
	float sx = 1,sy = 1; // Scaling from window
	private Menu MainMenu;
	private SaveDescription sD;
	private listSaves lS;
	
	private Button BackButton;
	private Button LoadButton;
	
	public LoadMenu(GameContainer gc,Menu mM)
	{
		myFont = gc.getDefaultFont();
		MainMenu = mM;
		sD = new SaveDescription(gc);
		lS = new listSaves(gc,sD);
		BackButton = new Button(430,940,500,100,gc);
		BackButton.text = "Back";
		LoadButton = new Button(990,940,500,100,gc);
		LoadButton.text = "Charger";
		
	}
	
	public void reset()
	{
		lS.reset();
		sD.reset();
	}
	/*public void GameStateUpdate(GameContainer gc, StateBasedGame sbg, int arg0) 
	{
		 
	}*/
	private void Resized(GameContainer gc) // Fonction permettant de garder l'aspect des éléments graphiques quel que soit la résolution
	{
		UIComponent.top = gc.getHeight()-Display.getHeight();
		UIComponent.width = Display.getWidth();
		UIComponent.height = Display.getHeight();
		sx = (float)(Display.getWidth())/(float)(gc.getWidth());
		sy = (float)(Display.getHeight())/(float)(gc.getHeight());
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException // Affichage des éléments graphiques
	{
		if(Display.wasResized()) Resized(gc);
		g.drawRect(5, 5+UIComponent.top, UIComponent.width-10, UIComponent.height-10);
		
		g.drawString("Charger Partie", Display.getWidth()/2-(myFont.getWidth("Charger Partie")/2), UIComponent.top+50);
		
		BackButton.render(g);
		LoadButton.render(g);
		sD.render(g);
		lS.render(g);
		
		if(sy < sx) g.scale(sy,sy);
		else g.scale(sx,sx);
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException // Gestion des entrées / sorties
	{
		if(Display.wasResized()) Resized(gc);
		Input input = gc.getInput();
		
		if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON))
		{
			if(BackButton.Hover()) MainMenu.changeMenu(0);
			if(LoadButton.Hover()) if(WindowGame.Load(lS.SelectedFile())) sbg.enterState(1);
			lS.update();
		}
	}
}

class SaveDescription // Classe décrivant un fichier de sauvegarde
{
	private String name = "";
	private String Date = "";
	
	private Font myFont;
	public SaveDescription(GameContainer gc)
	{
		myFont = gc.getDefaultFont();
	}
	
	public void reset()
	{
		name = "";
		Date = "";
	}
	
	public void setDescription(File fi) // Setteur du fichier à décrire
	{
		name = fi.getName().substring(0,fi.getName().length()-4);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date = sdf.format(fi.lastModified());
	}
	
	public void render(Graphics g) // Affichage de l'élément de descripteur
	{
		g.drawString(name, UIComponent.width*(380.0f/1920.0f)-(myFont.getWidth(name)/2), UIComponent.top+(UIComponent.height*(250.0f/1080.0f)));
		g.drawString(Date, UIComponent.width*(380.0f/1920.0f)-(myFont.getWidth(Date)/2), UIComponent.top+(UIComponent.height*(600.0f/1080.0f)));
		
		g.drawRect(UIComponent.width*(100.0f/1920.0f), UIComponent.top+(UIComponent.height*(110.0f/1080.0f)), UIComponent.width*(560.0f/1920.0f), UIComponent.height*(720.0f/1080.0f));
	}
}

class listSaves // Classe listant les sauvegardes de parties différentes
{
	File[] files;
	private int indexPage = 1;
	private int nbPages = 1;
	private Button Current;
	private Button Lower;
	private Button Higher;
	private GameContainer gc;
	private SaveDescription sD;
	
	private int selectedIndex = -1;
	
	private ArrayList<Button> SaveButtons;
	
	public listSaves(GameContainer gc,SaveDescription sD)
	{
		this.gc = gc;
		this.sD = sD;
		SaveButtons = new ArrayList<Button>();
		
		reset();
		
	}
	
	public void reset()
	{
		System.out.print("RESET");
		SaveButtons.clear();
		
		
		File f = new File(System.getProperty("user.dir") + "\\Saves"); 
		files = f.listFiles(new FilenameFilter() { // Récupère la liste de fichiers terminants par l'extension .sav dans le dossier Saves/
		    @Override
		    public boolean accept(File dir, String name) {
		        return name.endsWith(".sav");
		    }
		});
		
		for(int i=0;i<files.length && i < 10;i++)
		{
			Button b = new Button(800,110+(72*i),970,70,gc);
			b.text = files[i].getName();
			b.BackGroundColor = Color.gray;
			SaveButtons.add(b);
		}
		
		nbPages = (int)Math.ceil((float)(files.length)/10.0f); // Définition du nombre de pages de listes
		
		Current = new Button(1260,845,50,50,gc);
		Current.text = Integer.toString(indexPage);
		Lower = new Button(1190,845,50,50,gc);
		Lower.text = "<";
		Lower.visible = false;
		Higher = new Button(1320,845,50,50,gc);
		Higher.text = ">";
		if(nbPages <= indexPage) Higher.visible = false;
	}
	
	private void changePage(int index) // Change l'index de la page et rafraîchit la liste des sauvegardes
	{
		indexPage = index;
		if(index == 1)Lower.visible = false;
		else Lower.visible = true;
		if(index == nbPages)Higher.visible = false;
		else Higher.visible = true;
		
		SaveButtons.clear();
		for(int i=(index-1)*10;i<files.length && i < 10+((index-1)*10);i++)
		{
			Button b = new Button(800,111+(72*(i%10)),970,70,gc);
			b.text = files[i].getName();
			b.BackGroundColor = Color.gray;
			SaveButtons.add(b);
		}
	}
	
	private void updateDescription(int index) // Met à jour la description par rapport à l'index de fichier sélectionné
	{
		if(selectedIndex != -1)SaveButtons.get(selectedIndex%10).BackGroundColor = Color.gray;
		selectedIndex = index+(indexPage-1)*10;
		SaveButtons.get(selectedIndex%10).BackGroundColor = Color.lightGray;
		sD.setDescription(files[selectedIndex]);
	}
	
	public void render(Graphics g) // Affichage de la liste graphique
	{
		g.drawRect(UIComponent.width*(750.0f/1920.0f), UIComponent.top+(UIComponent.height*(110.0f/1080.0f)), UIComponent.width*(1070.0f/1920.0f), UIComponent.height*(720.0f/1080.0f));
		
		for(int i=0;i<SaveButtons.size();i++)
		{
			SaveButtons.get(i).render(g);
		}
		
		if(nbPages > 0)
		{
			Current.render(g);
			Lower.render(g);
			Higher.render(g);
		}
	}
	
	public File SelectedFile() // Retourne le fichier sélectionné
	{
		return files[selectedIndex];
	}
	
	public void update() // Gestion des intéractions I/O
	{
		if(Higher.Hover())
		{
			indexPage++;
			Current.text = Integer.toString(indexPage);
			changePage(indexPage);
		}
		else if(Lower.Hover())
		{
			indexPage--;
			Current.text = Integer.toString(indexPage);
			changePage(indexPage);
		}
		else
		{
			for(int i=0;i<SaveButtons.size();i++)
			{
				if(SaveButtons.get(i).Hover()) updateDescription(i);
			}
		}
	}
}
