package engine.board;

public class Cell {
	Marble marble;
	CellType cellType;
	boolean trap;
	
	
	
	
	public Cell(CellType cellType) {
		super();
		this.cellType = cellType;
		this.marble = null;
		this.trap = false;
	}
	
	
	
}
