package zad.war;

import zad.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;

import java.io.File;
import java.io.FileWriter;

import java.time.Instant;
import java.time.Duration;
import java.util.GregorianCalendar;

/**
 * The main code for the card game "War".
 * 
 * @author kiszk
 *
 */

public class MainWar {
	
	private static GregorianCalendar calendar = new GregorianCalendar();
	
	/**
	 * Saves the game to a file.
	 * @param bot cards of the AI
	 * @param player cards of the player
	 * @param gameTime time of play 
	 */
	public static void saveGame(Battalion bot, Battalion player, Duration gameTime) {
		String fileName = calendar.getTime() + ".txt";
		fileName = fileName.replaceAll(":", "-");
		File fil = new File(fileName);
		try {
			FileWriter write = new FileWriter(fil);
			write.write("Game time : " + gameTime.toMinutes() + "min \n");
			write.write("Bot: " + bot.size() + "\n");
			for(int i=0; i<bot.size(); i++) {
				write.write(bot.nextSoldier().toString() + "\n");
			}
			write.write("Player: " + player.size() + "\n");
			for(int i=0; i<player.size(); i++) {
				write.write(player.nextSoldier().toString() + "\n");
			}
			write.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Runs the game
	 * @param args Not used currently
	 */
	public static void main(String[] args) {
		//Yarrr
		System.out.println("Yarrrr");
		
		Scanner scan = new Scanner(System.in);
		
		CardSet deck = new CardSet(false);
		deck.shuffle();
		
		Battalion bot = new Battalion();
		Battalion player = new Battalion();
		
		Card veteranBot;
		Card veteranPlayer;
		
		for(int i=1; i<=deck.getTotalCards(); i++) {
			if(i%2 == 0) {
				player.addSoldier(deck.drawCard());
			} else {
				bot.addSoldier(deck.drawCard());
			}
		}
		
		bot.reverse();
		player.reverse();
		
		//game
		
		List<Card> field = new ArrayList<Card>();
		int game = 1;
		
		Instant start = Instant.now();
		
		while(bot.size() != 0 || player.size() != 0) {
			
			while(true) {
				try {
					System.out.printf("Player: %d | 0 = Exit , Other Integer = Continue\n",player.size());
					game = scan.nextInt();
					scan.nextLine();
					break;
				} catch(InputMismatchException e) {
					System.out.println("Must be integer");
					scan.nextLine();
				}
			}
			
			if(game == 0) {
				break;
			}
			
			veteranBot = bot.nextSoldier();
			veteranPlayer = player.nextSoldier();
			
			field.add(veteranBot);
			field.add(veteranPlayer);
			
			System.out.printf("Bot : %s\n", veteranBot.toString());
			System.out.printf("Player : %s\n", veteranPlayer.toString());
			
			while(veteranBot.getValue() == veteranPlayer.getValue()) {
				
				if(player.size() < 3) {
					game = 0;
					System.out.println("You Lost");
					break;
				} else if(bot.size() < 3) {
					game = 0;
					System.out.println("You Won");
					break;
				}
				
				System.out.println("War!!!");
				
				field.add(bot.nextSoldier());
				field.add(player.nextSoldier());
				
				veteranBot = bot.nextSoldier();
				System.out.printf("Bot : %s\n", veteranBot.toString());
				veteranPlayer = player.nextSoldier();
				System.out.printf("Player : %s\n", veteranPlayer.toString());
				
				field.add(veteranBot);
				field.add(veteranPlayer);
			}
			
			if(game == 0) {
				break;
			}
			
			if(veteranBot.getValue() > veteranPlayer.getValue()) {
				bot.addSoldiers(field.toArray(new Card[0]));
				field.clear();
			} else {
				player.addSoldiers(field.toArray(new Card[0]));
				field.clear();
			}
		}
		
		Instant end = Instant.now();
		
		saveGame(bot, player, Duration.between(start, end));
		
		scan.close();

	}

}
