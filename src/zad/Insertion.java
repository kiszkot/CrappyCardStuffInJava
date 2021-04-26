package zad;

/**
 * Insertion sort for Cards
 * 
 * @author kiszk
 *
 */

public class Insertion {
	
	/**
	 * Sorts the Cards in the given array.
	 * @param arr The array to be sorted
	 * @return Card[] The sorted array
	 */
	public static Card[] sort(Card[] arr) {
		int len = arr.length;
		for(int i=1; i<len; i++) {
			for(int j=i;j>0 && arr[j-1].compareTo(arr[j])<0; j--) {
				Card tmp = arr[j];
				arr[j]=arr[j-1];
				arr[j-1]=tmp;
			}
		}
		return arr;
	}
	
	/**
	 * Sorts the cards in the given array by value
	 * @param arr The array to be sorted
	 * @return Card[] The sorted array
	 */
	public static Card[] sortByValue(Card[] arr) {
		int len = arr.length;
		for(int i=1; i<len; i++) {
			for(int j=i;j>0 && arr[j-1].getValue() < arr[j].getValue(); j--) {
				Card tmp = arr[j];
				arr[j]=arr[j-1];
				arr[j-1]=tmp;
			}
		}
		return arr;
	}
	
	/**
	 * Sorts the cards in the given array by card color.
	 * @param arr The array to be sorted
	 * @return Card[] The sorted array
	 */
	public static Card[] sortByColor(Card[] arr) {
		int len = arr.length;
		for(int i=1; i<len; i++) {
			for(int j=i;j>0 && arr[j-1].getColor().compareTo(arr[j].getColor()) < 0; j--) {
				Card tmp = arr[j];
				arr[j]=arr[j-1];
				arr[j-1]=tmp;
			}
		}
		return arr;
	}
	
}
