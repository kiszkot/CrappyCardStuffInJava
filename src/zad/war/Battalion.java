package zad.war;

import java.util.List;
import java.util.ArrayList;
import zad.*;

/**
 * Definition of the cards with which a player plays War called Battalion as a metaphor.
 * 
 * @author kiszkot
 *
 */

public class Battalion {
	
	private List<Card> battalion = new ArrayList<Card>();
	
	/**
	 * Creates a Battalion of given Cards of a Card array.
	 * @param set The {@link zad.Card} array to create a Battalion
	 */
	public Battalion(Card[] set) {
		for(int i=0; i<set.length; i++) {
			if(set[i].getValue() == 1) {
				this.battalion.add(new Card("14", set[i].getColor()));
			} else {
				this.battalion.add(set[i]);
			}
		}
	}
	
	/**
	 * Creates an empty Battalion.
	 */
	public Battalion() {
		battalion.clear();
	}
	
	/**
	 * Gets the next card (Soldier) in the Battalion
	 * @return Card The next Soldier in the Battalion
	 */
	public Card nextSoldier() {
		Card ret = battalion.get(0);
		battalion.remove(0);
		return ret;
	}
	
	/**
	 * Adds a Card (Soldier) to the Battalion.
	 * @param soldier The Soldier to be added
	 */
	public void addSoldier(Card soldier) {
		if(soldier.getValue() == 1) {
			this.battalion.add(new Card("14", soldier.getColor()));
		} else {
			this.battalion.add(soldier);
		}
	}
	
	/**
	 * Adds the Cards (Soldiers) from a Card array to the Battalion.
	 * @param soldiers The Soldiers to be added.
	 */
	public void addSoldiers(Card[] soldiers) {
		for(int i=0; i<soldiers.length; i++) {
			battalion.add(soldiers[i]);
		}
	}
	
	/**
	 * Reverse the order of the Battalion; used at the beginning of a War game
	 */
	public void reverse() {
		int n = battalion.size();
		
		for(int i=0; i<(int)(n/2); i++) {
			Card tmp = battalion.get(i);
			battalion.set(i, tmp);
			battalion.set(n-i-1, tmp);
		}
	}
	
	/**
	 * For debugging purposes, returns a String of the Cards in the Battalion.
	 * @return String The Soldiers in the Battalion
	 */
	public String toString() {
		String ret = "";
		for(int i=0; i<battalion.size(); i++) {
			ret = ret + battalion.get(i).toString() + " - ";
		}
		return ret;
	}
	
	/**
	 * Gets the size of the Battalion
	 * @return int The size of the Battalion
	 */
	public int size() {
		return battalion.size();
	}
	
	/**
	 * Converts the Battalion to a Card array.
	 * @return Card[] Array of Soldiers in the Battalion
	 */
	public Card[] toArray() {
		return battalion.toArray(new Card[0]);
	}
}
