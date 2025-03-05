package model.card;

import java.io.*;
import java.util.*;

import model.card.standard.*;
/*import model.card.standard.Five;
import model.card.standard.Four;
import model.card.standard.Seven;
import model.card.standard.Standard;
import model.card.standard.Suit;
import model.card.standard.Ten;*/
import model.card.wild.*;
//import model.card.wild.Saver;
import engine.GameManager;
import engine.board.BoardManager;


public class Deck {

	private final static String CARDS_FILE="Cards.csv";
	private static ArrayList<Card> cardsPool;
	
	
	/*created a class that reads excel sheet per column aw row (el bel3ard) then separates each
	7aga(el beltool) in array of strings then check if wild card then use specified 
	constructors same thing in standard but in else we just use the standard constructor*/
	public static void loadCardPool(BoardManager boardManager, GameManager gameManager)
			throws IOException{
		
		@SuppressWarnings("resource")
		BufferedReader br=new BufferedReader(new FileReader(CARDS_FILE));
		String s="";
				while((s=br.readLine())!=null){
					String [] w = s.split(",");
					
					int cardCode=Integer.parseInt(w[0]);
					int cardFrequency=Integer.parseInt(w[1]);
					
					if(cardCode==14){
						for(int i=0;i< cardFrequency;i++){
							cardsPool.add(new Burner(w[2],w[3],boardManager,gameManager));};
					}
					else if(cardCode==14){
						for(int i=0;i< cardFrequency;i++){
							cardsPool.add(new Saver(w[2],w[3],boardManager,gameManager));};
					}
						else{
							int cardRank= Integer.parseInt(w[4]);
							if (cardRank==1){
							for(int i=0;i< cardFrequency;i++){
								cardsPool.add(new Ace(w[2],w[3], boardManager,gameManager,
										Suit.valueOf(w[5])));};	
							}
							else if(cardRank==4){cardsPool.add(new Four(w[2],w[3], 
									boardManager,gameManager,
									Suit.valueOf(w[5])));}
							
							
							
							else if(cardRank==5){cardsPool.add(new Five(w[2],w[3], 
									boardManager,gameManager,
									Suit.valueOf(w[5])));}
							
							else if(cardRank==7){cardsPool.add(new Seven(w[2],w[3], 
									boardManager,gameManager,
									Suit.valueOf(w[5])));}
							
							
							else if(cardRank==10){cardsPool.add(new Ten(w[2],w[3],
									boardManager,gameManager,
									Suit.valueOf(w[5])));}
							
							else if(cardRank==11){cardsPool.add(new Jack(w[2],w[3],
									boardManager,gameManager,
									Suit.valueOf(w[5])));}
							
							
							else if(cardRank==12){cardsPool.add(new Queen(w[2],w[3], 
									boardManager,gameManager,
									Suit.valueOf(w[5])));}
							
							
							else if(cardRank==13){cardsPool.add(new King(w[2],w[3],
									boardManager,gameManager,
									Suit.valueOf(w[5])));}
							
							else{
								cardsPool.add(new Standard(w[2],w[3], boardManager,gameManager,
										cardRank,Suit.valueOf(w[5])));};
							}
						
						
						
						}
				
				
				
				
				}
	
	

	public static ArrayList<Card> drawCards(){
		//first to shuffle cards:
		Collections.shuffle(cardsPool);
		//then just return using arrayList class implemented method(easier and faster)
		return (ArrayList<Card>) cardsPool.subList(0, 4);
		
	}
		
	}
	
	

