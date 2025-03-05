package engine;

import java.io.*;
import java.util.*;

import model.Colour;
import model.card.Card;
import model.card.Deck;
import model.player.CPU;
import model.player.Player;
import engine.board.Board;
import engine.board.BoardManager;

public class Game {
	private final Board board;
	private final ArrayList<Player> players;
	private final ArrayList<Card> firePit;
		int currentPlayerIndex;
		int turn;
	
	
	
	public Board getBoard() {
			return board;
		}



		public ArrayList<Player> getPlayers() {
			return players;
		}



		public ArrayList<Card> getFirePit() {
			return firePit;
		}



	Game(String playerName) throws IOException{	
		//random order colours:
		ArrayList<Colour> colours= new ArrayList<Colour>();
		colours.add(Colour.RED);
		colours.add(Colour.BLUE);
		colours.add(Colour.GREEN);
		colours.add(Colour.YELLOW);
		Collections.shuffle(colours);
		board= new Board(colours,gax	meManager) ;//page 11?
		Deck.loadCardPool(boardManager, GameManager);//same page
		
		(CPU)new CPU("CPU1", Colour.GREEN, BoardManager boardManager);
		(CPU)new CPU("CPU2", Colour.BLUE, BoardManager boardManager);
		(CPU)new CPU("CPU2", Colour.YELLOW, BoardManager boardManager);
	}

}
