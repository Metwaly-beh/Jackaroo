package engine.board;

import model.player.Marble;

public class Cell {
	 Marble marble; //cell class cant see marble class #fixed
	 CellType cellType;
	 boolean trap;
	
	
	
	
	public Cell(CellType cellType) {
		super();
		this.cellType = cellType;
		this.marble = null;
		this.trap = false;
	}
	
	
	
}
