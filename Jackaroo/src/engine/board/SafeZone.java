package engine.board;

import java.util.ArrayList;

import model.Colour;

public class SafeZone {
	
		private final Colour colour;
		private final ArrayList<Cell> cells; 
		 
		 
		 
		 
		public Colour getColour() {
			return colour;
		}




		public ArrayList<Cell> getCells() {
			return cells;
		}




		public SafeZone(Colour colour) {
			super();
			this.colour = colour;
			cells= new ArrayList<Cell>(4);
			for(int i=0;i<4;i++){
			cells.add(new Cell(CellType.SAFE));
		}}
		
		

		
		

}
