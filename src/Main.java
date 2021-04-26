import java.util.Scanner;
import java.util.InputMismatchException;

/**
 * 
 * Choose a game:
 * Here we go, super Mario
 * 
 * Choose between War and Blackjack
 * 
 * @author kiszkot
 *
 */


public class Main {

	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		int game = 0;
		
		while(true) {
			try {
				System.out.printf("Choose a game: \n 1 - Blackjack\n 2 - War\n 3 - Exit\n");
				game = scan.nextInt();
				scan.nextLine();
				
				switch(game) {
				case 1:
					zad.Main.main(args);
					break;
				case 2:
					zad.war.MainWar.main(args);
					break;
				case 3:
					System.out.println("Exiting...");
					break;
				case 4:
					System.out.println("No egg here");
					continue;
				default:
					System.out.println("Not an option");
					continue;
				}
				
				break;
				
			} catch(InputMismatchException e) {
				System.out.println("Must be integer");
				scan.nextLine();
				continue;
			}
		}
		
		scan.close();
	}

}
