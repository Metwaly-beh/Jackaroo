package engine.board;

import java.util.ArrayList;
import java.util.Random;

import engine.GameManager;
import exception.CannotFieldException;
import exception.IllegalDestroyException;
import exception.IllegalMovementException;
import exception.IllegalSwapException;
import exception.InvalidMarbleException;
import model.Colour;
import model.player.Marble;


public class Board implements BoardManager {
    private final ArrayList<Cell> track;
    private final ArrayList<SafeZone> safeZones;
	private final GameManager gameManager;
    private int splitDistance;

    public ArrayList<Cell> getTrack() {
        return this.track;
    }

    public ArrayList<SafeZone> getSafeZones() {
        return this.safeZones;
    }
    
    @Override
    public int getSplitDistance() {
        return this.splitDistance;
    }

    public void setSplitDistance(int splitDistance) {
        this.splitDistance = splitDistance;
    }
    public Board(ArrayList<Colour> colourOrder, GameManager gameManager) {
        this.track = new ArrayList<>();
        this.safeZones = new ArrayList<>();
        this.gameManager = gameManager;
        
        for (int i = 0; i < 100; i++) {
            this.track.add(new Cell(CellType.NORMAL));
            
            if (i % 25 == 0) 
                this.track.get(i).setCellType(CellType.BASE);
            
            else if ((i+2) % 25 == 0) 
                this.track.get(i).setCellType(CellType.ENTRY);
        }

        for(int i = 0; i < 8; i++)
            this.assignTrapCell();

        for (int i = 0; i < 4; i++)
            this.safeZones.add(new SafeZone(colourOrder.get(i)));

        splitDistance = 3;
    }

    
   
    private void assignTrapCell() {
        int randIndex = -1;
        
        do
            randIndex = (int)(Math.random() * 100); 
        while(this.track.get(randIndex).getCellType() != CellType.NORMAL || this.track.get(randIndex).isTrap());
        
        this.track.get(randIndex).setTrap(true);
    }
    
    private ArrayList<Cell> getSafeZone(Colour colour) {
        for (SafeZone zone : safeZones) {
            if (zone.getColour().equals(colour)) {
                return zone.getCells();
            }
        }
        return null; // default if colour not found
    }

    private int getPositionInPath(ArrayList<Cell> path, Marble marble) {
        for (int i = 0; i < path.size(); i++) {
            if (path.get(i).getMarble() == marble) {
                return i;
            }
        }
        return -1;
    }

    private int getBasePosition(Colour colour){
        for (int i = 0; i < safeZones.size(); i++) {
            if (safeZones.get(i).getColour().equals(colour)) {
                return i * 25;
            }
        }
        return -1;
    }

    private int getEntryPosition(Colour colour) {
        for (int i = 0; i < 4; i++) {
            if (safeZones.get(i).getColour() == colour) {
                int result = 25 * i - 2;
                return result < 0 ? result + 100 : result;
            }
        }
        return -1;
    }

    
    
    
    
    //too complicated to explain do'nt touch please
    private ArrayList<Cell> validateSteps(Marble marble, int steps) throws IllegalMovementException {
        ArrayList<Cell> fullPath = new ArrayList<>();
        
        // Find position on track
        int i = getPositionInPath(track,marble); //in track
       
        
        // Find current position in safe zone (if exists)
        ArrayList<Cell> safeZone = getSafeZone(marble.getColour());
        int j = getPositionInPath(safeZone,marble);
        
        // Validate marble exists on board
        if (i == -1 && j == -1) {
            throw new IllegalMovementException("Marble not in track or safezone");
        }
        
        // Handle track movement
        if (i != -1) {
            // Find entry position for this color
            int entryPos =getEntryPosition(marble.getColour()); 
            int stepsForEntry = (entryPos - i + 100) % 100;
            
            // 4 case
            if (steps < 0) {
                for (int k = 0; k >= steps; k--) {
                    int pos = (i + k + 100) % 100;
                    fullPath.add(track.get(pos));
                }
                return fullPath;
            }
            
            if (steps == 0) {
            	if(marble.getColour()==gameManager.getActivePlayerColour()){
                for (int k = 0; k < steps; k++) {
                    int pos = (i + k + 100) % 100;
                    fullPath.add(track.get(pos));}
                }
            	else{
            		
            	}
                return fullPath;
            }
            // Forward movement on track only
            if (steps <= stepsForEntry) {
                for (int k = 0; k <= steps; k++) {
                    fullPath.add(track.get((i + k) % 100));
                }
                return fullPath;
            }
            
            if(marble.getColour()!=gameManager.getActivePlayerColour()){
            	for (int k = 0; k <= steps; k++) {
                    fullPath.add(track.get((i + k) % 100));
                }
            	return fullPath;
            }
            
            // Movement to safe zone
            
            if (safeZone == null) {
                throw new IllegalMovementException("IllegalMovement");
            }
            
            int stepsInSafeZone = steps - stepsForEntry;
            if (stepsInSafeZone > safeZone.size()) {
                throw new IllegalMovementException("IllegalMovement");
            }
            
            // Add track cells
            for (int k = 0; k <= stepsForEntry; k++) {
                fullPath.add(track.get((i + k) % 100));
            }
            
            // Add safe zone cells
            for (int k = 0; k < stepsInSafeZone; k++) {
                fullPath.add(safeZone.get(k));
            }
            
            return fullPath;
        }
        
        // Handle safe zone movement
        if (steps < 0) {
            throw new IllegalMovementException("IllegalMovement");
        }
        
        if (j + steps >= safeZone.size()) {
            throw new IllegalMovementException("IllegalMovement");
        }
        
        for (int k = 0; k <= steps; k++) {
            fullPath.add(safeZone.get(j + k));
        }
        
        return fullPath;
    }
    


    
    private void validatePath(Marble marble, ArrayList<Cell> fullPath, boolean destroy) throws IllegalMovementException {
		if (fullPath.isEmpty()) {
			throw new IllegalMovementException("path empty");
		}

		int Counter = 0;
		for (int i = 1; i < fullPath.size() ; i++) { 
			Cell cell = fullPath.get(i);
			Marble marbleHere = cell.getMarble();

			if (marbleHere == null) continue;

			if (cell.getCellType() != CellType.ENTRY) {
				Counter++;
			}

			if (marbleHere.getColour() == gameManager.getActivePlayerColour() && !destroy) {
				throw new IllegalMovementException("Cannot bypass or destroy your own marble");
			}

			if (Counter > 1 && !destroy) {
				throw new IllegalMovementException("More than one marble blocking movement.");
			}
			if (cell.getCellType() == CellType.ENTRY && getPositionInPath(track, marbleHere) == getEntryPosition(marbleHere.getColour()) ) {

					throw new IllegalMovementException("Cannot bypass or destroy marble in its entry cell");
				
			}

			if (cell.getCellType() == CellType.ENTRY  &&!destroy) {

				if (i + 1 < fullPath.size() && fullPath.get(i + 1).getCellType() == CellType.SAFE) {
					throw new IllegalMovementException("marble at Safe Zone entry");
				}
			}

			if (cell.getCellType() == CellType.BASE && getPositionInPath(track, marbleHere) == getBasePosition(marbleHere.getColour())) {
				throw new IllegalMovementException("Cannot bypass any marble stationed in its base cell.");
			}

			if (cell.getCellType() == CellType.SAFE) {
				throw new IllegalMovementException("Safe Zone cell that holds a marble is included in path.");
			}
		}




	}



    
    private void move(Marble marble, ArrayList<Cell> fullPath, boolean destroy) throws IllegalDestroyException{
    	
    	
		Cell currentCell = fullPath.get(0);
		if(destroy){
			for(int i =0; i<=fullPath.size()-1; i++){
			if(fullPath.get(i).getMarble() != null && fullPath.get(i).getMarble().getColour() != marble.getColour()){
					fullPath.get(i).setMarble(null);
				}
			}

		}
		currentCell.setMarble(null);
		fullPath.get(fullPath.size() - 1).setMarble(null);
		if(fullPath.get(fullPath.size() - 1).isTrap()){
			fullPath.get(fullPath.size() - 1).setTrap(false);
			assignTrapCell();
		}else{
			fullPath.get(fullPath.size() - 1).setMarble(marble);
		}

	}
    
    
    
    
    private void validateSwap(Marble marble_1, Marble marble_2) throws IllegalSwapException{
    	if (marble_1==marble_2){
    		throw new IllegalSwapException("Two marbles are on the same track");
    	}
		if(getPositionInPath(track, marble_1)== -1 || getPositionInPath(track, marble_2) == -1){
			throw new IllegalSwapException("Two marbles are on the same track");
		}
		Marble otherPlayerMarble;
		Marble activePlayerMarble;
		if(marble_1.getColour()==gameManager.getActivePlayerColour()){otherPlayerMarble=marble_2;
		activePlayerMarble=marble_1;}
		else{otherPlayerMarble=marble_1;
		activePlayerMarble=marble_2;}
		if(getPositionInPath(track, activePlayerMarble) == getBasePosition(activePlayerMarble.getColour()) && getPositionInPath(track, otherPlayerMarble)== -1){
			throw new IllegalSwapException("Opponent marble is in base cell");
		}
		if(getPositionInPath(track, otherPlayerMarble) == getBasePosition(otherPlayerMarble.getColour())){
			throw new IllegalSwapException("Opponent marble is in base cell");
		}
	}
    
    private void validateDestroy(int positionInPath) throws IllegalDestroyException{
    	if(positionInPath==-1)
    		throw new IllegalDestroyException("Illegal Destroy");
    	else if(track.get(positionInPath).getMarble() != null){
			if(track.get(positionInPath).getCellType() == CellType.BASE && getBasePosition(track.get(positionInPath).getMarble().getColour()) == positionInPath){
				throw new IllegalDestroyException("Marble is in its base cell");
			}
		}
    }
    
    private void validateFielding(Cell occupiedBaseCell) throws CannotFieldException{
    	if(occupiedBaseCell.getMarble().getColour()== gameManager.getActivePlayerColour())
    		throw new CannotFieldException("Cannot Field");
    }
    
    private void validateSaving(int positionInSafeZone, int positionOnTrack) throws
    InvalidMarbleException{
    	if(positionInSafeZone!=-1 || positionOnTrack==-1)
    		throw new InvalidMarbleException("Invalid Marble");
    }
    
    
     public void moveBy(Marble marble, int steps, boolean destroy) throws
    IllegalMovementException, IllegalDestroyException{
    	 
    	 ArrayList<Cell> validSteps=validateSteps(marble,steps);
    	 validatePath(marble,validSteps,destroy);
    	 move(marble,validSteps,destroy);
     }
     
     public void swap(Marble marble_1, Marble marble_2) throws IllegalSwapException{
    	 validateSwap(marble_1,marble_2);
    	 int posm1=getPositionInPath(track, marble_1);
    	 int posm2=getPositionInPath(track, marble_2);
    	 
    	 Cell cellm1=track.get(posm1);
    	 Cell cellm2=track.get(posm2);
    	 
    	 track.set(posm1,cellm2);
    	 track.set(posm2,cellm1);
     }
     
     
     
     public void destroyMarble(Marble marble) throws IllegalDestroyException{
    	 validateDestroy(getPositionInPath(track,marble));

    	 track.get(getPositionInPath(track, marble)).setMarble(null);
    	 gameManager.sendHome(marble);
     }
     public void sendToBase(Marble marble) throws CannotFieldException, IllegalDestroyException {
    	 if(this.track.get(getBasePosition(marble.getColour())).getMarble()!=null){
 			validateFielding(track.get(getBasePosition(marble.getColour())));
 			destroyMarble(track.get(getBasePosition(marble.getColour())).getMarble());
 		}
 		this.track.get(getBasePosition(marble.getColour())).setMarble(marble);
 	}
     public void sendToSafe(Marble marble) throws InvalidMarbleException {
    	
    	    validateSaving(getPositionInPath(getSafeZone(marble.getColour()), marble), 
    	                  getPositionInPath(track, marble));

    	    ArrayList<Cell> safeZone = getSafeZone(marble.getColour());
    	    
    	    // Collect all empty slots
    	    ArrayList<Cell> emptySlots = new ArrayList<>();
    	    for (Cell cell : safeZone) {
    	        if (cell.getMarble() == null) {
    	            emptySlots.add(cell);
    	        }
    	    }

    	    if (emptySlots.isEmpty()) {
    	        throw new InvalidMarbleException("Safe Zone is full");
    	    }

    	    // Random place
    	    Random rand = new Random();
    	    Cell targetCell = emptySlots.get(rand.nextInt(emptySlots.size()));
    	    
    	    // Remove from track and place in safe zone
    	    track.get(getPositionInPath(track, marble)).setMarble(null);
    	    targetCell.setMarble(marble);
    	}     
     public ArrayList<Marble> getActionableMarbles(){
    	 ArrayList<Marble> listOfActionableMarbles=new ArrayList<Marble>();
    	 for(int i=0;i<=99;i++){
    		 if(track.get(i).getMarble()!=null)
    			 listOfActionableMarbles.add(track.get(i).getMarble());
    		 
    	 }
    	 ArrayList<Cell> currentSafeZone=getSafeZone(gameManager.getActivePlayerColour());
    	 for(int i=0;i<4;i++){
    		 if(currentSafeZone.get(i).getMarble()!=null)
    			 listOfActionableMarbles.add(currentSafeZone.get(i).getMarble());
    	 }
    	 return listOfActionableMarbles;
     }
}


