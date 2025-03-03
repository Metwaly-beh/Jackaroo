package model.card.standard;

import engine.GameManager;
import engine.board.BoardManager;
import model.card.Card;

public class Standard extends Card{
	int rank;
	Suit suit;
	
	
	public Standard(String name, String description, BoardManager boardManager,
			GameManager gameManager, int rank, Suit suit) {
		super(name, description, boardManager, gameManager);
		this.rank = rank;
		this.suit = suit;
	}
	
	

}
