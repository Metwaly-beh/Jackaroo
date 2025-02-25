package engine.board;

public class Cell {
	private Marble marble; //cell class cant see marble class
	private CellType cellType;
	private boolean trap;
	
	
	
	
	public Cell(CellType cellType) {
		super();
		this.cellType = cellType;
		this.marble = null;
		this.trap = false;
	}
	
	
	
}
