package model.player;

import java.util.ArrayList;


import exception.GameException;
import exception.InvalidCardException;
import exception.InvalidMarbleException;
import model.Colour;
import model.card.Card;

@SuppressWarnings("unused")
public class Player {
    private final String name;
    private final Colour colour;
    private ArrayList<Card> hand;
    private final ArrayList<Marble> marbles;
    private Card selectedCard;
	private final ArrayList<Marble> selectedMarbles;

    public Player(String name, Colour colour) {
        this.name = name;
        this.colour = colour;
        this.hand = new ArrayList<>();
        this.selectedMarbles = new ArrayList<>();
        this.marbles = new ArrayList<>();
        
        for (int i = 0; i < 4; i++) {
            this.marbles.add(new Marble(colour));
        }
        
        //default value
        this.selectedCard = null;
    }

    public String getName() {
        return name;
    }

    public Colour getColour() {
        return colour;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }
    
    public ArrayList<Marble> getMarbles() {
		return marbles;
	}
    
    public Card getSelectedCard() {
        return selectedCard;
    }
    
    public void regainMarble(Marble marble){
    	this.marbles.add(marble);
    	
    }

    public Marble getOneMarble(){
    	for(int i=0;i<marbles.size();i++)
    		if(marbles.get(i)!=null)
    		return marbles.get(i);
    	return null;
    }
    
    public void selectCard(Card card) throws InvalidCardException {
    	for(Card x: hand)
    		if(x==card){
    			this.selectedCard=card;
    			break;
    		}
    	if(this.selectedCard!=card)
    		throw new InvalidCardException("Card Invalid");
    }
    
    public void selectMarble(Marble marble) throws InvalidMarbleException {
    	if (this.selectedMarbles.size() >= 2) 
    		throw new InvalidMarbleException("Cannot select more than two marbles.");
      	 else if (!(selectedMarbles.contains(marble))) this.selectedMarbles.add(marble);
    	}
    		
    
    public void deselectAll(){
        this.selectedCard = null;
        this.selectedMarbles.clear();
    }
    
    
    public void play() throws GameException{
    	if(selectedCard.validateMarbleSize(selectedMarbles)==false || selectedCard.validateMarbleColours(selectedMarbles)==false)
    		throw new InvalidMarbleException("Invalid Marble");
    	else if(selectedCard.validateMarbleColours(selectedMarbles)==false || selectedCard.validateMarbleColours(selectedMarbles)==false)
    		throw new InvalidMarbleException("Invalid Marble");
    	else
    		selectedCard.act(selectedMarbles);
    }
    
}
