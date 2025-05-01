package model.card.standard;

import java.util.ArrayList;

import model.player.Marble;
import engine.GameManager;
import engine.board.BoardManager;

public class Five extends Standard {

    public Five(String name, String description, Suit suit, BoardManager boardManager, GameManager gameManager) {
        super(name, description, 5, suit, boardManager, gameManager);
    }
    
    public boolean validateMarbleColours(ArrayList<Marble> marbles){
    	if(marbles.get(0).getColour()!=gameManager.getActivePlayerColour())
    		return validateMarbleSize(marbles);
    	return false;
    }

}
