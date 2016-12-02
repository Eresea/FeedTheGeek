package MainPackage;

import org.newdawn.slick.*;
import org.newdawn.slick.gui.TextField;
import Utility.Item;
import java.util.ArrayList;

public class HUD {
private Play p;
private Bar healthBar;
private Bar hungerBar;
private Button InventoryButton;
private Button ShopButton;
private Button Work;
private int showUIType = 0;

public int Money = 10;

private Inventory Inventory;
private Shop Shop;

private Assiette Assiette;

private GameContainer gc;

	HUD(Play p, GameContainer gc)
	{
		this.p = p;
		this.gc = gc;
		healthBar = new Bar(0,40,250,80,gc);
		healthBar.text= "Health";
		hungerBar = new Bar(0,140,250,80,gc);
		hungerBar.text = "Hunger";
		Assiette = new Assiette(10,5,5,gc);
		
		InventoryButton = new Button(1620,540,300,100,gc);
		InventoryButton.BackGroundColor = Color.black;
		InventoryButton.text = "Inventaire";
		
		ShopButton = new Button(1620,440,300,100,gc);
		ShopButton.BackGroundColor = Color.black;
		ShopButton.text = "Shop";
		
		Work = new Button(1620,640,300,100,gc);
		Work.BackGroundColor = Color.black;
		Work.text = "Travailler";
		
		Inventory = new Inventory(this,gc);
		Shop = new Shop(this, gc);
		
		UpdateShowUIType(0);
		
		addItem(new Item(0,1,"Pomme",null,"L, le sais-tu ? Le dieu de la mort ne mange que des pommes."));
		addItem(new Item(1,1,"Patate",null,"Les patates de Sasha Braus."));
		addItem(new Item(1,1,"Pêche",null,"La pêche."));
		addItem(new Item(0,1,"Salade",null,"C'est vert."));
		addItem(new Item(0,1,"Pomme",null,"L, le sais-tu ? Le dieu de la mort ne mange que des pommes."));
	}

	void render(Graphics g)
	{		
		healthBar.render(g);
		hungerBar.render(g);
		InventoryButton.render(g);
		ShopButton.render(g);
		Work.render(g);
		
		Assiette.render(g);
		
		switch(showUIType)
		{
		case 1:
			Inventory.render(g);
			break;
		case 2:
			Shop.render(g);
			break;
		}
	}
	
	public void addItem(Item i)
	{
		Inventory.AddItem(i);
	}
	
	void updateValues(float health, float hunger)
	{
		healthBar.setValue(health);
		hungerBar.setValue(hunger);
	}

	void update()
	{
		switch(showUIType)
		{
		case 1:
			Inventory.update();
			break;
		case 2:
			Shop.update();
			break;
		}
Input input = gc.getInput();
		
		if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON))
		{
			if(Assiette.Hover())
			{
				p.hunger = Math.max(0,Math.min(1,p.hunger +  (float)(Assiette.bouchee())/100.0f));
			}
			if(InventoryButton.Hover())
			{
				showUIType = 1;
				InventoryButton.visible = false;
			}
			if(ShopButton.Hover())
			{
				showUIType = 2;
				ShopButton.visible = false;
			}
			if(Work.Hover())
			{
				
			}
		}
	}
	
	public void UpdateShowUIType(int newType)
	{
		showUIType = newType;
		InventoryButton.visible = true;
		ShopButton.visible = true;
	}
	
	public void SetAssiette(Assiette a)
	{
		Assiette = a;
	}
}

class Inventory
{
	private ArrayList<Item> Items;
	private ArrayList<Button> ItemButtons;
	private Button returnButton;
	private HUD parent;
	private GameContainer gc;
	private itemDescription descriptions;
	Inventory(HUD parent, GameContainer gc)
	{
		Items = new ArrayList<Item>();
		ItemButtons = new ArrayList<Button>();
		returnButton = new Button(1440,915,455,80,gc);
		returnButton.BackGroundColor = Color.black;
		returnButton.text = "Retour";
		
		for(int i=0;i<Items.size();i++)
		{
			ItemButtons.add(createButton(Items.get(i),-1));
		}
		
		descriptions = new itemDescription(gc,1005,255,430,740,null);
		
		this.gc = gc;
		this.parent = parent;
	}
	
	private Button createButton(Item it,int i)
	{
		if(i==-1) i = ItemButtons.size();
		int x = 1440+(150*(i%3));
		int y = 300+(150*((int)(i/3)));
		
		Button b = new Button(x,y,150,150,gc);
		b.text = it.name;
		switch(it.type)
		{
		case 0:
			b.BackGroundColor = Color.blue;
			break;
		case 1:
			b.BackGroundColor = Color.red;
			break;
		}
		
		return b;
	}
	
	public void AddItem(Item i)
	{
		for(int j=0;j<Items.size();j++)
		{
			if(Items.get(j).name == i.name)
			{
				Items.get(j).number++;
				return;
			}
		}
		Items.add(i);
		ItemButtons.add(createButton(i,-1));
	}
	
	public void render(Graphics g)
	{
		Color tmp = g.getColor();
		g.setColor(Color.gray);
		g.fillRect(UIComponent.width*1000/1920,UIComponent.top+(UIComponent.height*250/1080),UIComponent.width*900/1920,UIComponent.height*750/1080);
		g.setColor(tmp);
		g.drawRect(UIComponent.width*1000/1920, UIComponent.top+(UIComponent.height*250/1080), UIComponent.width*900/1920, UIComponent.height*750/1080);
		
		for(int i=0;i<ItemButtons.size();i++)
		{
			ItemButtons.get(i).render(g);
		}
		
		descriptions.render(g);
		returnButton.render(g);
	}
	
	private void describe(int i)
	{
		descriptions.changeItem(Items.get(i));
	}
	
	private void reOrganize(int i)
	{
		for(int j=i;j<ItemButtons.size();j++)
		{
			ItemButtons.set(j, createButton(Items.get(j),j));
		}
	}
	
	private boolean useItem()
	{
		for(int i=0;i<Items.size();i++)
		{
			if(Items.get(i).name == descriptions.i.name) // Trouvé l'item utilisé
			{
				
				Items.get(i).number--;
				parent.SetAssiette(new Assiette(10,5,5,gc)); // Utiliser les stats de l'item
				if(Items.get(i).number < 1)
				{
					Items.remove(i);
					ItemButtons.remove(i);
					reOrganize(i);
					return true;
				}
				return false;
			}
		}
		return false;
	}
	
	public void update()
	{
		Input input = gc.getInput();
		
		if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON))
		{
			if(returnButton.Hover()) parent.UpdateShowUIType(0);
			
			for(int i=0;i<ItemButtons.size();i++)
			{
				if(ItemButtons.get(i).Hover())
				{
					describe(i);
					break;
				}
			}
			
			if(descriptions.Used())
			{
				if(useItem())
				{
					descriptions.clear();
				}
			}
		}
	}
}

class Shop
{
	private ArrayList<Item> Items;
	private ArrayList<Integer> Prices;
	private HUD parent;
	private Button returnButton;
	private GameContainer gc;
	private itemDescription descriptions;
	
	Shop(HUD parent,GameContainer gc)
	{
		Items = new ArrayList<Item>();
		Prices = new ArrayList<Integer>();
		returnButton = new Button(1440,915,455,80,gc);
		returnButton.BackGroundColor = Color.black;
		returnButton.text = "Retour";
		descriptions = new itemDescription(gc,1005,255,430,740,null);
		
		this.gc = gc;
		this.parent = parent;
	}
	
	public Item BuyItem(int index)
	{
		if(parent.Money >= Prices.get(index))
		{
			parent.Money -= Prices.get(index);
			return Items.get(index);
		}
		return null;
	}
	
	public void render(Graphics g)
	{
		Color tmp = g.getColor();
		g.setColor(Color.gray);
		g.fillRect(UIComponent.width*1000/1920,UIComponent.top+(UIComponent.height*250/1080),UIComponent.width*900/1920,UIComponent.height*750/1080);
		g.setColor(tmp);
		g.drawRect(UIComponent.width*1000/1920, UIComponent.top+(UIComponent.height*250/1080), UIComponent.width*900/1920, UIComponent.height*750/1080);
		
		descriptions.render(g);
		returnButton.render(g);
	}
	
	public void update()
	{
		Input input = gc.getInput();
		
		if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON))
		{
			if(returnButton.Hover()) parent.UpdateShowUIType(0);
		}
	}
}

class itemDescription
{
	private GameContainer gc;
	private float x,y,w,h;
	private Font myFont;
	private String[] itemTypes = { "Nourriture", "Médicament" };
	private TextField description;
	private Button UseButton;
	
	public Item i;
	
	public int price = -1;
	// Arrêté ici
	itemDescription(GameContainer gc,float x, float y, float w, float h,Item i)
	{
		this.gc = gc;
		this.x = x/1920;
		this.y = y/1080;
		this.w = w/1920;
		this.h = h/1080;
		this.i = i;
		UseButton = new Button((int)(x),(int)(y+(h/2)),(int)(w),80,gc);
		UseButton.text = "Acheter"; // Par défaut
		myFont = gc.getDefaultFont();
		
	}
	
	public void clear()
	{
		i = null;
	}
	
	public void changeItem(Item i)
	{
		this.i = i;
	}
	
	public void render(Graphics g)
	{
		Color tmp = g.getColor();
		g.setColor(Color.darkGray);
		g.fillRect(UIComponent.width*x,UIComponent.top+(UIComponent.height*y),UIComponent.width*w,UIComponent.height*h); // Fond
		g.setColor(tmp);
		if(i != null)
		{
			g.drawString(i.name, UIComponent.width*x-myFont.getWidth(i.name)/2+w*UIComponent.width/2, y*UIComponent.height+50);
			
			UIComponent.drawText(i.description,myFont,g,(int)(UIComponent.width*this.x),(int)(UIComponent.width*this.y),(int)(UIComponent.width*this.w));
			
			if(price == -1)
			{
				UseButton.text = "Utiliser";
			}
			else
			{
				UseButton.text = "Acheter";
			}
			UseButton.render(g);
		}
		g.drawRect(UIComponent.width*x,UIComponent.top+(UIComponent.height*y),UIComponent.width*w,UIComponent.height*h);
	}
	
	public boolean Used()
	{
		return (UseButton.Hover() && i.number > 0);
	}
}
