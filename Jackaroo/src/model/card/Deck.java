package model.card;

import java.io.*;
import java.util.*;

import model.card.standard.*;
import model.card.wild.*;
import engine.GameManager;
import engine.board.BoardManager;

public class Deck {

    private final static String CARDS_FILE = "Cards.csv";
    private static ArrayList<Card> cardsPool;

    public static void loadCardPool(BoardManager boardManager, GameManager gameManager) throws IOException {
        cardsPool = new ArrayList<>();
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(CARDS_FILE));
            String s;

            while ((s = br.readLine()) != null) {
                String[] w = s.split(",");
                int cardCode = Integer.parseInt(w[0]);
                int cardFrequency = Integer.parseInt(w[1]);
                Card c = null;

                switch (cardCode) {
                    case 14:
                        c = new Burner(w[2], w[3], boardManager, gameManager);
                        break;
                    case 15:
                        c = new Saver(w[2], w[3], boardManager, gameManager);
                        break;
                    case 1:
                        c = new Ace(w[2], w[3], Suit.valueOf(w[5]), boardManager, gameManager);
                        break;
                    case 4:
                        c = new Four(w[2], w[3], Suit.valueOf(w[5]), boardManager, gameManager);
                        break;
                    case 5:
                        c = new Five(w[2], w[3], Suit.valueOf(w[5]), boardManager, gameManager);
                        break;
                    case 7:
                        c = new Seven(w[2], w[3], Suit.valueOf(w[5]), boardManager, gameManager);
                        break;
                    case 10:
                        c = new Ten(w[2], w[3], Suit.valueOf(w[5]), boardManager, gameManager);
                        break;
                    case 11:
                        c = new Jack(w[2], w[3], Suit.valueOf(w[5]), boardManager, gameManager);
                        break;
                    case 12:
                        c = new Queen(w[2], w[3], Suit.valueOf(w[5]), boardManager, gameManager);
                        break;
                    case 13:
                        c = new King(w[2], w[3], Suit.valueOf(w[5]), boardManager, gameManager);
                        break;
                    default:
                        int cardRank = Integer.parseInt(w[4]);
                        c = new Standard(w[2], w[3], cardRank, Suit.valueOf(w[5]), boardManager, gameManager);
                        break;
                }
                
                for (int i = 0; i < cardFrequency; i++) {
                    cardsPool.add(c);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                br.close();
            }
        }
    }

    public static ArrayList<Card> drawCards() {
        Collections.shuffle(cardsPool);
        ArrayList<Card> drawnCards = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            drawnCards.add(cardsPool.remove(0));
        }
        return drawnCards;
    }
}