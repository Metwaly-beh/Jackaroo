package model.card;

import engine.GameManager;
import engine.board.BoardManager;

public abstract class Card{
	final String name;
	final String description;
	protected BoardManager boardManager;
	protected GameManager gameManager;
	
	
	public Card(String name, String description, BoardManager boardManager,
			GameManager gameManager) {
		super();
		this.name = name;
		this.description = description;
		this.boardManager = boardManager;
		this.gameManager = gameManager;
	}
	
	

}
