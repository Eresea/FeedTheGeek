package MainPackage;
import java.util.Calendar;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;


public class Assiette extends UIComponent{ // Classe d'élément graphique affichant l'assiette dans le jeu
	public float value = 1; //0-1
	public Color foodColor = Color.yellow;
	private int nutrition;
	private int poison;
	private int durabilite;
	private Calendar dateCreation;
	private GameContainer gc;
	
	public Assiette(int nutrition, int poison,int durabilite,GameContainer gc)
	{
		super(1200,670,100,50,gc);
		this.nutrition = nutrition;
		this.poison = poison;
		this.gc = gc;
		this.durabilite = durabilite;
		dateCreation = Calendar.getInstance();
	}
	
	public int bouchee() // Fonction retournant la valeur nutritionelle d'une bouchée de l'objet et réduit la quantité de nouriture de l'objet
	{
		if(value > 0)
		{
			int food = calculNutrition();
			value -= 0.2;
			if(value < 0) value = 0;
			return food;
		}
		return 0;
	}
	
	private int calculNutrition() // Retourne la valeur nutritionelle actuelle de la nourriture dans l'assiette
	{
		if(perime()) return(-poison);
		return nutrition;
	}
	
	private boolean perime() // Retourne vrai si le produit a dépassé sa date de péremption
	{
		dateCreation.add(Calendar.SECOND, durabilite);
		if(Calendar.getInstance().after(dateCreation))
		{
			dateCreation.add(Calendar.SECOND,-durabilite);
			return(true);
		}
		dateCreation.add(Calendar.SECOND,-durabilite);
		return false;
	}
	
	public void render(Graphics g) // Affichage de l'élément graphique
	{
		Color tmp = g.getColor();
		if(value > 0)
		{
			if(perime()) g.setColor(Color.green);
			else g.setColor(foodColor);
			g.fillOval(width*x,top+(height*y), width*(100.0f/1920), value*-(height*(25.0f/1080)));
			g.setColor(tmp);
			//g.fillRect(0,25,100,50); // Assiette
			g.fillRect(width*x,top+(height*y),width*(100.0f/1920),height*(25.0f/1080));
		}
	}
	
	public boolean Hover() // Retourne vrai si l'élément est actuellement survolé par la souris
	{
			gc.getInput();
			int mouseX = Mouse.getX();
			int mouseY = gc.getHeight()-Mouse.getY();
			
			return (mouseX > width*x && mouseX < width*x+width*w) &&(mouseY < top+(height*y)+height*h && mouseY > top+(height*y));
	}
}
