package model.player;

import java.util.ArrayList;

import model.Colour;
import model.card.Card;

public class Player {
	
	String name;
	Colour colour;
	ArrayList<Card> hand;
	ArrayList<Marble> marbles;
	Card selectedCard;
	ArrayList<Marble> selectedMarbles;
	
	
	
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
