package zad;

import java.util.List;
import java.util.ArrayList;

/**
 * 
 * Card functionality for a card hand of a Blackjack game
 * 
 * @author kiszkot
 *
 */

public class Hand {
	
	private List<Card> hand = new ArrayList<Card>();
	
	/**
	 * Creates an empty hand with no cards.
	 */
	public Hand() { hand.clear(); }
	
	/**
	 * Creates a Hand with given Cards of the array
	 * @param hand Array of Cards
	 */
	public Hand(Card[] hand) {
		for(int i = 0; i<hand.length; i++) {
			this.hand.add(hand[i]);
		}
	}
	
	/**
	 * Calculates the value of the hand. If Aces are present and value is greater than 21 it recalculates.
	 * @return int The value of the hand
	 */
	public int handValue() {
		int sum = 0;
		for(int i=0; i<hand.size(); i++) {
			if(hand.get(i).getValue() > 10) {
				sum += 10;
			} else if(hand.get(i).getValue() == 1) {
				sum += 11;
			} else {
				sum += hand.get(i).getValue();
			}
		}
		sum = checkAsses(sum);
		return sum;
	}
	
	/**
	 * Adds a card to the hand
	 * @param card The card to be added
	 */
	public void addCard(Card card) {
		hand.add(card);
	}
	
	/**
	 * Checks for the presence of aces and changes the value of the hand to be with aces counted as 1
	 * instead of 11.
	 * @param sum The sum to be recalculated.
	 * @return int The recalculated sum
	 */
	private int checkAsses(int sum) {
		for(int i=0; i<hand.size(); i++) {
			if(hand.get(i).getValue() == 1 && sum > 21) {
				sum -= 10;
			}
		}
		return sum;
	}
	
	/**
	 * Returns a string of the cards in the hand divided by a whitespace.
	 * @return String The cards in the hand
	 */
	public String toString() {
		String ret = "";
		for(int i=0; i<hand.size(); i++) {
			ret += hand.get(i).toString() + " ";
		}
		return ret;
	}
	
	/**
	 * Previews the first card of the hand. Used to show one card of the House in a Blackjack
	 * game
	 * @return String The first card of the hand
	 */
	public String previewHouse() {
		/*String ret = " - ";
		for(int i=1; i<hand.size(); i++) {
			ret += hand.get(i).toString() + " ";
		}*/
		return hand.get(0).toString();
	}
	
	/**
	 * Gets the first card of the hand. Used to get the first card of the House
	 * in a Blackjack game.
	 * @return Card The first card of the hand
	 */
	public Card houseCard() {
		return hand.get(0);
	}
	
	/**
	 * Checks if the first two card in the Hand have the same value.
	 * Used to allow splitting in a Blackjack game.
	 * @return boolean TRUE if same, FALSE if not
	 */
	public boolean isSame() {
		int v = (hand.get(0).getValue() >= 10) ? 10 : hand.get(0).getValue();
		int w = (hand.get(1).getValue() >= 10) ? 10 : hand.get(1).getValue();
		if(v == w) return true;
		else return false;
	}
	
	/**
	 * Splits the hand into two hands. Used to split the first two given cards in
	 * a Blackjack game
	 * @return Hand with the second card of the original Hand
	 */
	public Hand split() {
		Hand split = new Hand();
		split.addCard(hand.get(1));
		hand.remove(1);
		return split;
	}
}
