package model.player;

import java.util.ArrayList;

import model.Colour;
import model.card.Card;

public class Player {
	
	private final String name;
	private final Colour colour;
	private ArrayList<Card> hand;
	private final ArrayList<Marble> marbles;
	private Card selectedCard;
	private final ArrayList<Marble> selectedMarbles;
	
	
	
	public ArrayList<Card> getHand() {
		return hand;
	}



	public void setHand(ArrayList<Card> hand) {
		this.hand = hand;
	}



	public Card getSelectedCard() {
		return selectedCard;
	}



	public void setSelectedCard(Card selectedCard) {
		this.selectedCard = selectedCard;
	}



	public String getName() {
		return name;
	}



	public Colour getColour() {
		return colour;
	}



	public ArrayList<Marble> getMarbles() {
		return marbles;
	}



	public ArrayList<Marble> getSelectedMarbles() {
		return selectedMarbles;
	}



	public Player(String name, Colour colour) {
		super();
		this.name = name;
		this.colour = colour;
		this.hand= new ArrayList<Card>();
		this.selectedMarbles=  new ArrayList<Marble>();
		this.marbles=  new ArrayList<Marble>();
		marbles.add(new Marble(colour));
		marbles.add(new Marble(colour));
		marbles.add(new Marble(colour));
		marbles.add(new Marble(colour));
		selectedCard=null;
	}
	
	

}
