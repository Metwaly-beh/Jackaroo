package engine.board;

import java.util.ArrayList;

import model.player.Marble;
import exception.CannotFieldException;
import exception.IllegalDestroyException;
import exception.IllegalMovementException;
import exception.IllegalSwapException;
import exception.InvalidMarbleException;

public interface BoardManager {

	void moveBy(Marble marble, int steps, boolean destroy) throws
	IllegalMovementException, IllegalDestroyException;

	
	void swap(Marble marble_1, Marble marble_2) throws IllegalSwapException;
	
	void destroyMarble(Marble marble) throws IllegalDestroyException;
	
	void sendToBase(Marble marble) throws CannotFieldException,
	IllegalDestroyException;
	
	void sendToSafe(Marble marble) throws InvalidMarbleException;
	
	ArrayList<Marble> getActionableMarbles();
	
	int getSplitDistance();
}
