package zad;

import java.util.Scanner;
import java.util.InputMismatchException;

/**
 * Blackjack game with split and insurance.
 */

public class Main {

	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		
		CardSet set = new CardSet(false);
		
		//game testing Blackjack
		
		set.resetSet();
		set.shuffle();
		
		int money = 1000;
		
		Hand house = new Hand();
		Hand player[] = new Hand[2];

		int bet;
		int insurance;
		int s;
		
		while(money > 0) {
			
			if(set.getAvailableCards() < set.getTotalCards()/2) {
				set.resetSet();
				set.shuffle();
			}
			
			System.out.println(money + "$");
			
			bet = 0;
			insurance = 0;
			s = 0;
			house = new Hand();
			player[0] = new Hand();

			while(true) {
				try {
					System.out.println("Select bet: (input letter to exit)");
					bet = scan.nextInt();
					scan.nextLine();
					if(money - bet >= 0) {
						break;
					} else {
						System.out.println("Not enough money");
						continue;
					}
				} catch(InputMismatchException e) {
					System.out.println("Closing");
					money = -100000;
					break;
				}
			}
			
			if(money < 0) {break;}

			player[0].addCard(set.drawCard());
			house.addCard(set.drawCard());
			player[0].addCard(set.drawCard());
			house.addCard(set.drawCard());

			System.out.println("House = " + house.previewHouse());
			
			//insurance
			if(house.houseCard().getValue() == 1) {
				while(true) {
					try {
						System.out.println("Choose insurance, 0 or less for no insurance");
						insurance = (int)scan.nextDouble();
						scan.nextLine();
						if(insurance <= 0) break;
						if(insurance > (int)bet/2) {
							System.out.printf("Max half of current bet - %d$\n",bet);
							continue;
						} if(money - bet - insurance < 0) {
							System.out.printf("Not enough money - %d$\n", money - bet);
							continue;
						} else break;
					} catch (InputMismatchException e) {
						System.out.println("Must be a number");
						scan.nextLine();
					}
				}
			}
			if(insurance > 0) {
				if(house.handValue() == 21) {
					System.out.println("Insurance lost");
					money = money - insurance;
				}
				else {
					System.out.println("Insurance won");
					money = money + insurance;
				}
			}
			
			
			//split
			int choice = 0;
			if(player[0].isSame()) {
				System.out.println("Player - " + player[0].toString());
				while(true) {
					try {
						System.out.println("Split? 1 = yes - other = no");
						choice = (int)scan.nextDouble();
						scan.nextLine();
						if(choice == 1) {
							s = 1;
							player[1] = player[0].split();
							player[0].addCard(set.drawCard());
							player[1].addCard(set.drawCard());
						} else s = 0;
						break;
					}catch(InputMismatchException e) {
						System.out.println("Must be a number");
						scan.nextLine();
					}
				}
			}
			
			for(int i = 0; i<=s; i++) {
				System.out.printf("Hand %d\n",i+1);
				while(true) {
					System.out.println(player[i].toString() + "= " + String.valueOf(player[i].handValue()));
					System.out.println("1 - hit \nother - stand");
	
					if(player[i].handValue() >= 21) {
						break;
					}
	
					int selection = scan.nextInt();
					if(selection == 1) {
						player[i].addCard(set.drawCard());
					} else {
						break;
					}
				}
			}

			while(house.handValue() < 17) {
				house.addCard(set.drawCard());
			}
			
			for(int i = 0; i <= s; i++) {
				System.out.printf("Player hand %d - ", i+1);
				System.out.println(player[i].toString() + " = " + player[i].handValue());
			}
			System.out.println("House - " + house.toString()  + " = " + house.handValue());
			
			for(int i = 0; i <= s; i++) {
				if(house.handValue() > player[i].handValue() && house.handValue() <= 21) {
					System.out.println("You lose");
					money = money - bet;
				} else if(player[i].handValue() > 21) {
					System.out.println("You lose");
					money = money - bet;
				} else if(player[i].handValue() == house.handValue()){
					System.out.println("Draw");
				} else {
					System.out.println("You win");
					money = money + bet;
				}
			}
		}
		
		scan.close();
		
	}

}
