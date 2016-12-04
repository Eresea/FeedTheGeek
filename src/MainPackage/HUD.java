package MainPackage;

import org.newdawn.slick.*;
import org.newdawn.slick.gui.TextField;
import Utility.Item;
import java.util.*;
import Utility.Dialog;
import org.newdawn.slick.state.*;

public class HUD {
private Play p;
private Bar healthBar;
private Bar hungerBar;
private Button InventoryButton;
private Button ShopButton;
private Button Work;
private int showUIType = 0;
private List<Dialog> Dialogs;

public static int Money = 10;

private Inventory Inventory;
private Shop Shop;

private Assiette Assiette;

private GameContainer gc;
private StateBasedGame sbg;

	HUD(Play p, GameContainer gc,StateBasedGame sbg)
	{
		this.p = p;
		this.gc = gc;
		this.sbg = sbg;
		healthBar = new Bar(0,40,250,80,gc);
		healthBar.text= "Health";
		hungerBar = new Bar(0,140,250,80,gc);
		hungerBar.text = "Hunger";
		Assiette = new Assiette(10,5,5,gc);
		Dialogs = new ArrayList<Dialog>();
		
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
		
		addItem(new Item(0,1,"Pomme",null,"L, le sais-tu ? Le dieu de la mort ne mange que des pommes.",10,25));
		addItem(new Item(0,1,"Patate",null,"Les patates de Sasha Braus.",20,60));
		addItem(new Item(0,1,"P�che",null,"La p�che.",15,35));
		addItem(new Item(0,1,"Salade",null,"C'est vert.",25,30));
		addItem(new Item(0,1,"Pomme",null,"L, le sais-tu ? Le dieu de la mort ne mange que des pommes.",10,25));
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
		
		g.drawString("Argent : " + Integer.toString(Money) + "$", (int)(UIComponent.width*(0.02)), (int)(UIComponent.top+(UIComponent.height*(0.22))));
		g.drawString(WindowGame.saveName, (int)(UIComponent.width*(0.495)-g.getFont().getWidth(WindowGame.saveName)/2), (int)(UIComponent.top+(UIComponent.height*(0.4))));
		
		for(int i=0;i<Dialogs.size();i++)
		{
			Dialogs.get(i).render(g);
		}
	}
	
	public void TakeMeds(Item it)
	{
		p.health = Math.max(0, Math.min(1, p.health+(float)(it.Nutrition/100.0f)));
	}
	
	public void addMoney(int i)
	{
		Money += i;
	}
	
	public void addItem(Item i)
	{
		Inventory.AddItem(i);
	}
	
	public List<String> Save()
	{
		List<String> s = new ArrayList<String>();
		s.add(0, WindowGame.PrimaryColor.toString());
		s.add(1, WindowGame.SecondaryColor.toString());
		s.add(2, "Money:"+Integer.toString(Money));
		s.add(3, "Inventory:"+Inventory.SaveInventory());
		return s;
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
				int bouchee = Assiette.bouchee();
				if(bouchee < 0) p.health = Math.max(0, Math.min(1, p.health+(float)(bouchee/100.0f)));
				else p.hunger = Math.max(0,Math.min(1,p.hunger +  (float)(bouchee)/100.0f));
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
				sbg.enterState(2);
			}
			
			for(int i=0;i<Dialogs.size();i++)
			{
				int tmp = Dialogs.get(i).update();
				if(tmp != -1) { DialogUpdate(Dialogs.get(i).id,tmp); break; }
			}
		}
	}
	
	private void DialogUpdate(int i, int j)
	{
		/*switch(i)
		{
		case 0: // Ecraser la 
			
			break;
		}*/
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
	
	private int xItem;
	Inventory(HUD parent, GameContainer gc)
	{
		Items = new ArrayList<Item>();
		ItemButtons = new ArrayList<Button>();
		returnButton = new Button(1440,915,455,80,gc);
		returnButton.BackGroundColor = Color.black;
		returnButton.text = "Retour";
		
		xItem = 150;
		
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
		int x = (int)(1440+((xItem*(i%3))));
		int y = 300+(xItem*((int)(i/3)));
		
		Button b = new Button(x,y,xItem,xItem,gc);
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
	
	public String SaveInventory()
	{
		String tmp = "";
		for(int i=0;i<Items.size();i++)
		{
			tmp += "["+Items.get(i).Save()+"],";
		}
		return "{"+tmp.substring(0, tmp.length()-1)+"}";
	}
	
	public void AddItem(Item i)
	{
		for(int j=0;j<Items.size();j++)
		{
			if(Items.get(j).name == i.name)
			{
				Items.get(j).number+=i.number;
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
			if(Items.get(i).name == descriptions.i.name) // Trouv� l'item utilis� dans l'inventaire
			{
				Items.get(i).number--;
				switch(Items.get(i).type)
				{
				case 0:
					parent.SetAssiette(new Assiette(Items.get(i).Nutrition,5,Items.get(i).duree,gc)); // Utiliser les stats de l'item
					if(Items.get(i).number < 1)
					{
						Items.remove(i);
						ItemButtons.remove(i);
						reOrganize(i);
						return true;
					}
					break;
				case 1:
					parent.TakeMeds(Items.get(i));
					if(Items.get(i).number < 1)
					{
						Items.remove(i);
						ItemButtons.remove(i);
						reOrganize(i);
						return true;
					}
					break;
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
	private ArrayList<Button> ItemButtons;
	private int xItem;
	
	Shop(HUD parent,GameContainer gc)
	{
		this.gc = gc;
		this.parent = parent;
		
		Items = new ArrayList<Item>();
		Prices = new ArrayList<Integer>();
		ItemButtons = new ArrayList<Button>();
		returnButton = new Button(1440,915,455,80,gc);
		returnButton.BackGroundColor = Color.black;
		returnButton.text = "Retour";
		descriptions = new itemDescription(gc,1005,255,430,740,null);
		xItem = 150;
		
		Items.add(new Item(0,-1,"Pomme",null,"L, le sais-tu ? Le dieu de la mort ne mange que des pommes.",10,25));
		Prices.add(5);
		Items.add(new Item(0,-1,"P�che",null,"Le p�che.",15,35));
		Prices.add(8);
		Items.add(new Item(0,1,"Patate",null,"Les patates de Sasha Braus.",20,60));
		Prices.add(10);
		Items.add(new Item(0,1,"Salade",null,"C'est vert.",25,30));
		Prices.add(14);
		Items.add(new Item(1,1,"Aspirine",null,"Pour les lendemains de cuite. -Maman",20,0));
		Prices.add(10);
		
		for(int i=0;i<Items.size();i++)
		{
			ItemButtons.add(createButton(Items.get(i),i)); //STOPPED HERE
		}
	}
	
	private Button createButton(Item it,int i)
	{
		if(i==-1) i = ItemButtons.size();
		int x = (int)(1440+((xItem*(i%3))));
		int y = 300+(xItem*((int)(i/3)));
		
		Button b = new Button(x,y,xItem,xItem,gc);
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
	
	public void BuyItem(Item it, int price)
	{
		if(parent.Money >= price)
		{
			parent.Money -= price;
			it.number = 1;
			parent.addItem(it);
		}
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
		descriptions.price = Prices.get(i);
	}
	
	public void update()
	{
		Input input = gc.getInput();
		
		if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON))
		{
			if(returnButton.Hover()) parent.UpdateShowUIType(0);
			for(int i=0;i<ItemButtons.size();i++)
			{
				if(ItemButtons.get(i).Hover()) describe(i);
			}
			
			if(descriptions.Used())
			{
				BuyItem(descriptions.i,descriptions.price);
			}
		}
	}
}

class itemDescription
{
	private GameContainer gc;
	private float x,y,w,h;
	private Font myFont;
	private String[] itemTypes = { "Nourriture", "M�dicament" };
	private TextField description;
	private Button UseButton;
	
	public Item i;
	
	public int price = -1;
	// Arr�t� ici
	itemDescription(GameContainer gc,float x, float y, float w, float h,Item i)
	{
		this.gc = gc;
		this.x = x/1920;
		this.y = y/1080;
		this.w = w/1920;
		this.h = h/1080;
		this.i = i;
		UseButton = new Button((int)(x),(int)(y+(h/2)),(int)(w),80,gc);
		UseButton.text = "Acheter"; // Par d�faut
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
			g.drawString(i.name, UIComponent.width*x-myFont.getWidth(i.name)/2+w*UIComponent.width/2, y*UIComponent.height+50); //Nom
			
			UIComponent.drawText(i.description,myFont,g,(int)(UIComponent.width*this.x),(int)(UIComponent.width*this.y),(int)(UIComponent.width*this.w)); //Description
			
			if(price == -1)
			{
				UseButton.text = "Utiliser";
				g.drawString("Nombre : " + Integer.toString(i.number), UIComponent.width*x-myFont.getWidth("Nombre : " + Integer.toString(i.number))/2+w*UIComponent.width/2, y*UIComponent.height*2+50);
			}
			else
			{
				UseButton.text = "Acheter";
				g.drawString(Integer.toString(price) + "$", UIComponent.width*x-myFont.getWidth(Integer.toString(price) + "$")/2+w*UIComponent.width/2, y*UIComponent.height*2+50);
			}
			switch(i.type)
			{
			case 0:
				g.drawString("Nutrition : " + Integer.toString(i.Nutrition), UIComponent.width*x-myFont.getWidth("Nutrition : " + Integer.toString(i.Nutrition))/2+w*UIComponent.width/2, y*UIComponent.height*2);
				break;
			case 1:
				g.drawString("Soins : " + Integer.toString(i.Nutrition), UIComponent.width*x-myFont.getWidth("Soin : " + Integer.toString(i.Nutrition))/2+w*UIComponent.width/2, y*UIComponent.height*2);
				break;
			}
			
			UseButton.render(g);
		}
		g.drawRect(UIComponent.width*x,UIComponent.top+(UIComponent.height*y),UIComponent.width*w,UIComponent.height*h);
	}
	
	public boolean Used()
	{
		if(i != null)
		{
			if(i.number == -1)
			{
				return (UseButton.Hover() && HUD.Money >= price);
			}
			return (UseButton.Hover() && i.number > 0);
		}
		return false;
	}
}
