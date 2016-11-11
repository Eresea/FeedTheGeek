package MainPackage;

import java.util.*;
import Utility.Pair;
import org.newdawn.slick.*;

public class Actor { // A utiliser plus tard pour les éléments dynamiques (persos ...)

	public Pair<Short,Short> Location;
	public float Scale;
	private GameContainer gc;
	private String url = "";
	
	public Actor(Short x,Short y,float s, GameContainer gc,String resourceURL)
	{
		Location.setL(x);
		Location.setR(y);
		Scale = s;
		this.gc = gc;
		url = resourceURL;
	}
	
	
}
