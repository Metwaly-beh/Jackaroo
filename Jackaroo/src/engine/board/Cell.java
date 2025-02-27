package engine.board;

public class Cell {
	 Marble marble; //cell class cant see marble class
	 CellType cellType;
	private boolean trap;
	
	
	
	
	public Cell(CellType cellType) {
		super();
		this.cellType = cellType;
		this.marble = null;
		this.trap = false;
	}
	
	
	
}
