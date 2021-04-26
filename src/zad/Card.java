package zad;

/**
 * Card definition method with String value and Color that can be anything but it's preferable
 * to use card symbols from the Unicode Table.
 * Code formatted in UTF-8 to display the symbols properly
 * 
 * @author kiszkot
*/

public class Card {
	
	private String value;
	private String color;
	
	/**
	 * Constructor for Card
	 * @param value String value of the card
	 * @param color String color of the card, usually unicode
	 */
	public Card(String value, String color) {
		this.value = value;
		this.color = color;
	}
	
	/**
	 * Returns a String with the card value and color where the value is formatted to match
	 * the appropriate card symbol:
	 * 0 - Joker
	 * 1 - Ace
	 * 11 - Jack
	 * 12 - Queen
	 * 13 - King
	 * 14 - Ace
	 */
	public String toString() {
		String tmp = "";
		switch(Integer.parseInt(this.value)) {
		case 0:
			tmp = String.join("", "J", this.color);
			break;
		case 1:
			tmp = String.join("", "A", this.color);
			break;
		case 11:
			tmp = String.join("", "J", this.color);
			break;
		case 12:
			tmp = String.join("", "Q", this.color);
			break;
		case 13:
			tmp = String.join("", "K", this.color);
			break;
		case 14:
			tmp = String.join("", "A", this.color);
			break;
		default:
			tmp = String.join("", this.value, this.color);
			break;
		}
		return tmp;
	}
	
	/**
	 * Compare current card with given Card first by color then by value
	 * @param a Card to compare to
	 * @return 1 if Card is less, -1 if Card is greater
	 */
	public int compareTo(Card a) {
		if(this.color.compareTo(a.color) < 0) {
			return 1;
		} else if(this.color.compareTo(a.color) == 0){
			if(Integer.parseInt(this.value) <= Integer.parseInt(a.value)) {
				return 1;
			} else {
				return -1;
			}
		} else {
			return -1;
		}
	}
	
	/**
	 * Returns the value of the card
	 * @return int
	 */
	public int getValue() {
		return Integer.parseInt(this.value);
	} 
	
	/**
	 * Returns the color of the Card
	 * @return String
	 */
	public String getColor() {
		return this.color;
	}
	
}
