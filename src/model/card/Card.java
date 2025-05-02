package model.card;

import java.util.ArrayList;

import engine.Game;
import engine.GameManager;
import engine.board.BoardManager;
import exception.ActionException;
import exception.InvalidMarbleException;
import model.player.Marble;

@SuppressWarnings("unused")
public abstract class Card {
	private final String name;
    private final String description;
    protected BoardManager boardManager;
    protected GameManager gameManager;

    public Card(String name, String description, BoardManager boardManager, GameManager gameManager) {
        this.name = name;
        this.description = description;
        this.boardManager = boardManager;
        this.gameManager = gameManager;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
    
    public boolean validateMarbleSize(ArrayList<Marble> marbles){
    	if(marbles.size()==1)
    		return true;
    	return false;
    }
    
    public boolean validateMarbleColours(ArrayList<Marble> marbles){
    	if(marbles.get(0).getColour()==gameManager.getActivePlayerColour())
    		return validateMarbleSize(marbles);
    	return false;
    }
    
    public abstract void act(ArrayList<Marble> marbles) throws ActionException,
    InvalidMarbleException;
}
