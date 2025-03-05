package model.card;

import java.io.*;
import java.util.*;

import engine.GameManager;
import engine.board.BoardManager;


public class Deck {

	String CARDS_FILE;
	ArrayList<Card> cardsPool;
	
	
	
	@SuppressWarnings("unused")
	public static void loadCardPool(BoardManager boardManager, GameManager gameManager)throws IOException{
		@SuppressWarnings("resource")
		BufferedReader br=new BufferedReader(new FileReader("Cards.csv"));
		String s="";
				while((s=br.readLine())!=null){
					String [] a = s.split(",");
					
					int code=Integer.parseInt(a[0]);
					int freq=Integer.parseInt(a[1]);
					
					if(code==14){
						for(){};
					}
					
				}
		
	}
	
	
}
