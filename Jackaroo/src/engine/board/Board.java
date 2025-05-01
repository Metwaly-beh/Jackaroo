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
            
            // Forward movement on track only
            if (steps <= stepsForEntry) {
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

        for (int i = 1; i < fullPath.size(); i++) {
            Cell cell = fullPath.get(i);

            if (cell.getMarble() != null) {
                Colour otherColour = cell.getMarble().getColour();

                // validate Self-Blocking
                if (marble.getColour() == otherColour)
                    throw new IllegalMovementException("Cannot move through or onto own marble");


                // validate Safe Zone Entry
                if (cell.getCellType()==CellType.SAFE)
                    throw new IllegalMovementException("Cannot move through or onto opponent's marble in their Safe Zone");

                // validate Base Cell Blockage
                if (cell.getCellType()==CellType.BASE)
                    throw new IllegalMovementException("Cannot move through or destroy marble in Base Cell");

                if (!destroy && i == fullPath.size() - 1)
                    throw new IllegalMovementException("Cannot land on opponent marble without King");
                
                if (!destroy && i < fullPath.size() - 1)
                    throw new IllegalMovementException("Cannot pass through opponent marble");
            }
        }
    }


    
    private void move(Marble marble, ArrayList<Cell> fullPath, boolean destroy)
    		throws IllegalDestroyException{
    	//remove the marble from the starting cell
    	fullPath.get(0).setMarble(null);
    	for(int i = 1; i < fullPath.size(); i++) {
    		//marble destruction
    		if(fullPath.get(i).getMarble()!=null && destroy!=true)
    			throw new IllegalDestroyException("Illegal Destroy");
    		if(fullPath.get(i).getMarble()!=null && destroy==true){
    			gameManager.sendHome(fullPath.get(i).getMarble());
    			fullPath.get(i).setMarble(null);
    		} 
    			//placing marble at the end
    			if(i==fullPath.size()-1)
    				fullPath.get(i).setMarble(marble);
    	}
    	fullPath.get(fullPath.size() - 1).setMarble(marble);
    	//trap check
    	if (fullPath.get(fullPath.size() - 1).isTrap()){
    		gameManager.sendHome(marble); //get marble at home
    		assignTrapCell();//new trap cell
    		
    		fullPath.get(fullPath.size()-1).setMarble(null); //destroy marble on trap cell
    	}
    	
    	
    	
    }
    private void validateSwap(Marble marble1, Marble marble2) throws IllegalSwapException {
        // Check if marbles are the same
        if (marble1 == marble2) {
            throw new IllegalSwapException("Cannot swap a marble with itself");
        }

        // Check if both marbles are on the track
        
        if (track.indexOf(marble1) == -1 || track.indexOf(marble2) == -1) {
            throw new IllegalSwapException("Both marbles must be on the track");
        }

        // Check if either marble is in a base cell
        if (track.get(track.indexOf(marble1)).getCellType() == CellType.BASE || 
            track.get(track.indexOf(marble2)).getCellType() == CellType.BASE) {
            throw new IllegalSwapException("Cannot swap marbles in base cells");
        }

        // Check if trying to swap with opponent's marble in their safe zone entry
        if ((track.indexOf(marble1) == getEntryPosition(marble2.getColour()) && marble1.getColour() != marble2.getColour()) ||
            (track.indexOf(marble2) == getEntryPosition(marble1.getColour()) && marble1.getColour() != marble2.getColour())) {
            throw new IllegalSwapException("Cannot swap with opponent's marble at their safe zone entry");
        }
    }
    
    
    private void validateDestroy(int positionInPath) throws IllegalDestroyException{
    	if(positionInPath==-1)
    		throw new IllegalDestroyException("Illegal Destroy");
    	if(positionInPath%25==0)
    		throw new IllegalDestroyException("Illegal Destroy");
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
    	 Colour colour1=marble_1.getColour();
    	 Colour colour2=marble_2.getColour();
    	 
    	 Marble marble1= new Marble(colour2);
    	 Marble marble2= new Marble(colour1);
    	 
    	 
    	 marble_1=marble1;
    	 marble_2=marble2;
     }
     public void destroyMarble(Marble marble) throws IllegalDestroyException{
    	 validateDestroy(getPositionInPath(track,marble));
    	 gameManager.sendHome(marble);
     }
     public void sendToBase(Marble marble) throws CannotFieldException, IllegalDestroyException {
    	    int basePos = getBasePosition(marble.getColour());
    	    validateDestroy(basePos);
    	    validateFielding(track.get(basePos));

    	    Cell baseCell = track.get(basePos);
    	    if (baseCell.getMarble() != null)
    	        gameManager.sendHome(baseCell.getMarble());

    	    baseCell.setMarble(marble);
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
