package MainPackage;

import org.newdawn.slick.*;
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
				p.hunger +=  (float)(Assiette.bouchee())/100.0f;
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
}

class Inventory
{
	private ArrayList<Item> Items;
	private Button returnButton;
	private HUD parent;
	private GameContainer gc;
	Inventory(HUD parent, GameContainer gc)
	{
		Items = new ArrayList<Item>();
		returnButton = new Button(1340,880,520,100,gc);
		returnButton.BackGroundColor = Color.black;
		returnButton.text = "Retour";
		
		this.gc = gc;
		this.parent = parent;
	}
	
	public void AddItem(Item i)
	{
		Items.add(i);
	}
	
	public void render(Graphics g)
	{
		Color tmp = g.getColor();
		g.setColor(Color.gray);
		g.fillRect(UIComponent.width*1300/1920,UIComponent.top+(UIComponent.height*250/1080),UIComponent.width*600/1920,UIComponent.height*750/1080);
		g.setColor(tmp);
		g.drawRect(UIComponent.width*1300/1920, UIComponent.top+(UIComponent.height*250/1080), UIComponent.width*600/1920, UIComponent.height*750/1080);
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

class Shop
{
	private ArrayList<Item> Items;
	private ArrayList<Integer> Prices;
	private HUD parent;
	private Button returnButton;
	private GameContainer gc;
	
	Shop(HUD parent,GameContainer gc)
	{
		Items = new ArrayList<Item>();
		Prices = new ArrayList<Integer>();
		returnButton = new Button(1040,880,820,100,gc);
		returnButton.BackGroundColor = Color.black;
		returnButton.text = "Retour";
		
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
		g.setColor(tmp);
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
	// Arrêté ici
	itemDescription(GameContainer gc)
	{
		this.gc = gc;
	}
}
