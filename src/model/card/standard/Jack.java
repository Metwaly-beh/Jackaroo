package model.card.standard;

import java.util.ArrayList;

import model.player.Marble;
import engine.GameManager;
import engine.board.BoardManager;
import exception.ActionException;
import exception.InvalidMarbleException;

public class Jack extends Standard {

    public Jack(String name, String description, Suit suit, BoardManager boardManager, GameManager gameManager) {
        super(name, description, 11, suit, boardManager, gameManager);
    }
    
    public boolean validateMarbleSize(ArrayList<Marble> marbles){
    	if(marbles.size()==1 || marbles.size()==2)
    		return true;
    	return false;
    }
    
    public boolean validateMarbleColours(ArrayList<Marble> marbles){
    	if(marbles.size()==1 && marbles.get(0).getColour()==gameManager.getActivePlayerColour())
    		return true;
    	if(marbles.size()==2){
    		if(marbles.get(0).getColour()==gameManager.getActivePlayerColour() && marbles.get(1).getColour()!=gameManager.getActivePlayerColour())
    			return true;
    		if(marbles.get(1).getColour()==gameManager.getActivePlayerColour() && marbles.get(0).getColour()!=gameManager.getActivePlayerColour())
    			return true;
    	}
    	return false;
    }
    
    public void act(ArrayList<Marble> marbles) throws ActionException,
    InvalidMarbleException{
    	if(marbles.size()==1)
    		super.act(marbles);
    	else if(validateMarbleColours(marbles)==true)
    		boardManager.swap(marbles.get(0),marbles.get(1));
    	else
    		throw new InvalidMarbleException("Invalid Marble");
    		}

}
