package zad;

import java.util.Random; 

/**
 * Deck creation method, uses unicode card characters as card colors.
 * 
 * possibility to create decks with multiple card sets, with or without jokers.
 * Jokers are set to be 2 per card set.
 * 
 * @author kiszkot
 */

public class CardSet {
	
	private Card[] cards;
	private String[] colors = {"\u2660","\u2661","\u2662","\u2663"}; //u+2660, u+2661, u+2662, u+2663 wartosc unicode znakow
	private int available;
	private int drawn;
	private Random random = new Random(java.time.LocalTime.now().hashCode()); //losowy seed dla funkcji random
	
	/**
	 * Creates a deck of one 52 or 54 card set.
	 * @param jokers boolean TRUE for jokers, FALSE for no jokers
	 */
	public CardSet(boolean jokers) {
		Card[] set;
		if(jokers) {
			set = new Card[54];
			set[52] = new Card(String.valueOf(0),"R");
			set[53] = new Card(String.valueOf(0),"B");
			this.available = 54;
		} else {
			set = new Card[52];
			this.available = 52;
		}
		for(int i=0; i<4; i++) {
			for(int j=0; j<13; j++) {
				set[13*i+j] = new Card(String.valueOf(j+1),colors[i]);
			}
		}
		
		this.drawn = 0;
		this.cards = Insertion.sort(set);
	}
	
	/**
	 * Creates a deck of given card sets;
	 * Possibility to add jokers same as in main constructor {@link CardSet}
	 * @param sets int Number of sets to create
	 * @param jokers boolean TRUE for jokers, FALSE for no jokers
	 */
	public CardSet(int sets, boolean jokers) {
		Card[] set;
		int n;
		if(jokers) {
			set = new Card[54*sets];
			n = 54;
			this.available = sets*54;
			this.drawn = 0;
		} else {
			set = new Card[52*sets];
			n = 52;
			this.available = sets*52;
			this.drawn = 0;
		}
		for(int k=0; k<sets; k++) {
			for(int i=0; i<4; i++) {
				for(int j=0; j<13; j++) {
					set[n*k+13*i+j] = new Card(String.valueOf(j+1),colors[i]);
				}
			}
			if(jokers) {
				set[54*k+52] = new Card(String.valueOf(0),"R");
				set[54*k+53] = new Card(String.valueOf(0),"B");
			}
		}
		
		this.cards = Insertion.sort(set);
	}
	
	/**
	 * Shuffles the current available cards in the deck
	 */
	public void shuffle() {
		int n = this.available;
		for(int i=this.drawn; i<n; i++) {
			int r = i + random.nextInt(n-i);
			Card tmp = cards[i];
			cards[i] = cards[r];
			cards[r] = tmp;
		}
	}
	
	/**
	 * Draws a card from the deck.
	 * @return Card Card drawn from the deck.
	 */
	public Card drawCard() {
		if(this.drawn >= this.available) {
			return null;
		}
		else {
			Card drawn = this.cards[this.drawn++];
			return drawn;
		}
	}
	
	/**
	 * Reorders the set as in original creation and resets available cards to draw
	 */
	public void resetSet() {
		Card[] set = Insertion.sort(cards);
		this.drawn = 0;
		this.cards = set;
	}
	
	/**
	 * For debugging purposes
	 * @return String A string of the available cards in the deck
	 */
	public String toString() {
		String ret = "";
		for(int i = 0; i<this.drawn; i++) {
			ret = ret + this.cards[i].toString() + "-";
		}
		return ret;
	}
	
	/**
	 * This is an easter egg, ignore
	 */
	public void peek() {
		System.out.println("No peeking allowed!!!!");
	}
	
	/**
	 * Returns the number of available cards to draw in the deck
	 * @return int The number of available cards
	 */
	public int getAvailableCards() {
		return this.available - this.drawn;
	}
	
	/**
	 * Converts the deck to a Card array
	 * @return Card[] Array of cards in the deck
	 */
	public Card[] toCardArray() {
		return cards;
	}
	
	/**
	 * Gets the total cards of the deck, differs from available cards.
	 * @return int Number of cards in the deck.
	 */
	public int getTotalCards() {
		return cards.length;
	}
	
}
