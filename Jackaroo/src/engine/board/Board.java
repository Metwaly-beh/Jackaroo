package engine.board;

import java.util.ArrayList;

import model.Colour;
import engine.GameManager;

public class Board implements GameManager{
	GameManager gameManager;
	ArrayList<Cell> track;
	ArrayList<SafeZone> safeZones;
	int splitDistance;
	
	
	
	public Board(ArrayList<Colour> colourOrder, GameManager gameManager){
		//this.gameManager=  	;   enta meen ya 3am?
		track=new ArrayList<Cell>();
		safeZones= new ArrayList<SafeZone>();
		splitDistance=3;
		
	}

}
