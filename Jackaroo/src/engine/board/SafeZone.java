package engine.board;

import java.util.ArrayList;

import model.Colour;

public class SafeZone {
	
		private final Colour colour; //safezone class cant see colour enum
		private ArrayList<Cell> cells; //needs more explanation
		
		
		public SafeZone(Colour colour) {
			super();
			this.colour = colour;
			//how to create an array of 4 SAFE cells?
		}
		
		

}
