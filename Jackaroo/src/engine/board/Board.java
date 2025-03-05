package engine.board;

import java.util.ArrayList;
import java.util.Random;

import model.Colour;
import engine.GameManager;

public class Board implements GameManager{
	private final GameManager gameManager;
	private final ArrayList<Cell> track;
	private final ArrayList<SafeZone> safeZones;
	private int splitDistance;
	
	
	
	public int getSplitDistance() {
		return splitDistance;
	}




	public void setSplitDistance(int splitDistance) {
		this.splitDistance = splitDistance;
	}




	public ArrayList<Cell> getTrack() {
		return track;
	}




	public ArrayList<SafeZone> getSafeZones() {
		return safeZones;
	}




	public Board(ArrayList<Colour> colourOrder, GameManager gameManager){
		this.gameManager=  	gameManager;
		track=new ArrayList<Cell>();
		safeZones= new ArrayList<SafeZone>();
		splitDistance=3;
		for(int i=0;i<100;i++){
			//first if it was base cell:
			if(i==0||i==25||i==50||i==75){
				track.add(new Cell(CellType.BASE));
			}
			//if it was safe zone entry:
			else if(i==23||i==48||i==73||i==98){
				track.add(new Cell(CellType.ENTRY));}
			//else its just normal
			else track.add(new Cell(CellType.NORMAL));
		
		}
		
		assignTrapCell();
		new SafeZone(Colour.RED);
		new SafeZone(Colour.GREEN);
		new SafeZone(Colour.BLUE);
		new SafeZone(Colour.YELLOW);
		}
		
		
		
	
	private void assignTrapCell(){
		for(int i=0;i<8;i++){
			int	trapCellIndex= new Random().nextInt(99)+1;
			/*the plus one here is because 
			it is from 0-98*/
			while(trapCellIndex==25||trapCellIndex==50||trapCellIndex==75||
					(track.get(trapCellIndex)).isTrap()){
				//make sure not base cell or trap cell already
				trapCellIndex= new Random().nextInt(99)+1;}
			Cell temp= new Cell(CellType.NORMAL);
			temp.setTrap(true);
		track.set(trapCellIndex,temp );
		}
	}
	
	
}
