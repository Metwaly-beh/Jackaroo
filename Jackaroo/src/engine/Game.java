package engine;

import engine.GameManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import engine.board.Board;
import exception.CannotDiscardException;
import exception.CannotFieldException;
import exception.GameException;
import exception.IllegalDestroyException;
import exception.InvalidCardException;
import exception.InvalidMarbleException;
import exception.SplitOutOfRangeException;
import model.Colour;
import model.card.Card;
import model.card.Deck;
import model.player.CPU;
import model.player.Marble;
import model.player.Player;

public class Game implements GameManager {
    private final Board board;
    private final ArrayList<Player> players;
	private int currentPlayerIndex;
    private final ArrayList<Card> firePit;
    private int turn;

    public Game(String playerName) throws IOException {
        turn = 0;
        currentPlayerIndex = 0;
        firePit = new ArrayList<>();

        ArrayList<Colour> colourOrder = new ArrayList<>();
        
        colourOrder.addAll(Arrays.asList(Colour.values()));
        
        Collections.shuffle(colourOrder);
        
        this.board = new Board(colourOrder, this);
        
        Deck.loadCardPool(this.board, (GameManager)this);
        
        this.players = new ArrayList<>();
        this.players.add(new Player(playerName, colourOrder.get(0)));
        
        for (int i = 1; i < 4; i++) 
            this.players.add(new CPU("CPU " + i, colourOrder.get(i), this.board));
        
        for (int i = 0; i < 4; i++) 
            this.players.get(i).setHand(Deck.drawCards());
        
    }
    
    public Board getBoard() {
        return board;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public ArrayList<Card> getFirePit() {
        return firePit;
    }
    void selectCard(Card card) throws InvalidCardException{
    	players.get(currentPlayerIndex).selectCard(card);
    }
    void selectMarble(Marble marble) throws InvalidMarbleException{
    	players.get(currentPlayerIndex).selectMarble(marble);
    }
    void deselectAll(){
    	players.get(currentPlayerIndex).deselectAll();
    }
    void editSplitDistance(int splitDistance) throws SplitOutOfRangeException{
    	if(splitDistance < 1 || splitDistance>6){
    		throw new SplitOutOfRangeException("THE SPLIT DISTANCE IS NOT BETWEEN 1 AND 6");
    		}
    	board.setSplitDistance(splitDistance);
    	
    	
    }
    boolean canPlayTurn(){
    	return players.get(currentPlayerIndex).getHand().size()>=turn;
    }
    void playPlayerTurn() throws GameException{
    	players.get(currentPlayerIndex).play();
    }
    void endPlayerTurn(){
    	Player currentPlayer= players.get(currentPlayerIndex);
    	Card selectedCard = currentPlayer.getSelectedCard();
    	if(selectedCard!=null){
    		firePit.add(selectedCard);
    	}
    	currentPlayer.deselectAll();
    	currentPlayerIndex=currentPlayerIndex  + 1;
    	if(currentPlayerIndex==0){
    		turn++;
    	}
    	if(turn>4){
    		turn=1;
    	}
    	for (int i = 0; i < players.size(); i++) {
    	    Player player = players.get(i);
    	    int cardsNeeded = 4 - player.getHand().size();
    	    if (cardsNeeded > 0 && Deck.getPoolSize() > 0) {
    	        ArrayList<Card> drawn = Deck.drawCards();
    	        int cardsToAdd = cardsNeeded;
    	        if (drawn.size() < cardsNeeded) {
    	            cardsToAdd = drawn.size();
    	        }

    	        for (int j = 0; j < cardsToAdd; j++) {
    	            player.getHand().add(drawn.get(j));
    	        }
    	    }
    	}
    	if(Deck.getPoolSize()<4){
    		Deck.refillPool(firePit);
    		firePit.clear();
    	}
    	
    }
    public Colour checkWin() {
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            ArrayList<engine.board.Cell> safeZone = board.getSafeZone(player.getColour());
            int marblesInSafeZone = 0;

            for (int j = 0; j < safeZone.size(); j++) {
                engine.board.Cell cell = safeZone.get(j);
                Marble marble = cell.getMarble();

                if (marble != null && marble.getColour() == player.getColour()) {
                    marblesInSafeZone++;
                }
            }

            if (marblesInSafeZone == 4) {
                return player.getColour();
            }
        }
        return null;
    }
    public void sendHome(Marble marble) {
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            if (player.getColour().equals(marble.getColour())) {
                player.regainMarble(marble);
                
            }
        }
    }
    
    public void fieldMarble() throws CannotFieldException, IllegalDestroyException {
        Marble marble = players.get(currentPlayerIndex).getOneMarble();

        if (marble == null) {
            throw new CannotFieldException("No marbles available to field.");
        }

        sendHome(marble); 
       players.get(currentPlayerIndex).getMarbles().remove(marble); 
    }
   
    public void discardCard(Colour colour) throws CannotDiscardException {
        Player targetPlayer = null;
        for (Player player : players) {
            if (player.getColour().equals(colour)) {
                targetPlayer = player;
                break;
            }
        }

        if (targetPlayer == null) {
            throw new CannotDiscardException("Player with the given color is not found");
        }

        ArrayList<Card> hand = targetPlayer.getHand(); 

        if (hand.isEmpty()) {
            throw new CannotDiscardException("No cards to discard");
        }
        int index = new Random().nextInt(hand.size());
        Card discarded = hand.remove(index);
        firePit.add(discarded); 
    }
    public void discardCard() throws CannotDiscardException {
        ArrayList<Player> eligiblePlayers = new ArrayList<>();

        Player currentPlayer = players.get(currentPlayerIndex);
        for (Player player : players) {
            if (!player.equals(currentPlayer) && !player.getHand().isEmpty()) {
                eligiblePlayers.add(player);
            }
        }

        if (eligiblePlayers.isEmpty()) {
            throw new CannotDiscardException("No other player has cards to discard");
        }

        Random rand = new Random();
        Player selectedPlayer = eligiblePlayers.get(rand.nextInt(eligiblePlayers.size()));
        ArrayList<Card> hand = selectedPlayer.getHand();

        Card discarded = hand.remove(rand.nextInt(hand.size()));
        firePit.add(discarded); 
    }
    public Colour getActivePlayerColour() {
        return players.get(currentPlayerIndex).getColour();
    }
    public Colour getNextPlayerColour() {
        int nextIndex = currentPlayerIndex + 1;
        return players.get(nextIndex).getColour();
    }
 
    
    
    
    
    
    
    
    
}