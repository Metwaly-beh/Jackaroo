package model.card.standard;

import java.util.ArrayList;

import model.player.Marble;
import engine.GameManager;
import engine.board.BoardManager;
import exception.ActionException;
import exception.InvalidMarbleException;

public class Ace extends Standard {

    public Ace(String name, String description, Suit suit, BoardManager boardManager, GameManager gameManager) {
        super(name, description, 1, suit, boardManager, gameManager);
    }
    
    public boolean validateMarbleSize(ArrayList<Marble> marbles){
    	if(marbles.size()==1 || marbles.size()==0)
    		return true;
    	return false;
    }
    
    public boolean validateMarbleColours(ArrayList<Marble> marbles){
    	if(marbles.size()==0)
    	    return true;
    	if(marbles.get(0).getColour()==gameManager.getActivePlayerColour())
    		return validateMarbleSize(marbles);
    	return false;
    }
    
    public void act(ArrayList<Marble> marbles) throws ActionException,
    InvalidMarbleException{
    	if(marbles.size()==1)
        		super.act(marbles);
    	else if(marbles.size()==0)
        		gameManager.fieldMarble();
    		}

}
