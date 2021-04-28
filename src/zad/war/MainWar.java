package zad.war;

import zad.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;

import java.time.Instant;
import java.time.Duration;
import java.util.GregorianCalendar;

/**
 * The main code for the card game "War"; https://en.wikipedia.org/wiki/War_(card_game)
 * 
 * @author kiszkot
 *
 */

public class MainWar {
	
	private static Scanner scan = new Scanner(System.in);
	
	private static GregorianCalendar calendar = new GregorianCalendar();
	
	private static String fileName = "";
	
	/**
	 * Saves the game to a file.
	 * @param bot cards of the AI
	 * @param player cards of the player
	 * @param gameTime time of play 
	 * @param gameLoaded Boolean telling if the game was loaded or not
	 */
	public static void saveGame(Battalion bot, Battalion player, Duration gameTime, boolean gameLoaded) {
		
		File fil;
		if(gameLoaded) {
			fil = new File(fileName);
		} else {
			String fileNameTmp = calendar.getTime() + ".txt";
			fileNameTmp = fileNameTmp.replaceAll(":", "-");
			fil = new File(fileNameTmp);
		}
		
		Card tmp = new Card("0","0");
		try {
			FileWriter write = new FileWriter(fil);
			write.write(gameTime.toString() + "\n");
			write.write("Game time : " + gameTime.toMinutes() + "min \n");
			write.write("Bot: " + bot.size() + "\n");
			while(bot.size() > 0) {
				tmp = bot.nextSoldier();
				write.write(tmp.getValue() + " " + tmp.getColor() + "\n");
			}
			
			write.write("Player: " + player.size() + "\n");
			while(player.size() > 0) {
				tmp = player.nextSoldier();
				write.write(tmp.getValue() + " " + tmp.getColor() + "\n");
			}
			write.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Function to load a previously saved game
	 * @param bot The battalion where to save the AI cards
	 * @param player The battalion where to save the player cards
	 * @param start The duration of the saved game to add later on
	 */
	public static void loadGame(Battalion bot, Battalion player, Duration start) {
		while(true) {
			try {

				System.out.println("File to load");
				fileName = scan.nextLine();

				File fil = new File(fileName);
				Scanner read = new Scanner(fil);
				
				start = Duration.parse(read.nextLine());
				read.nextLine();
				
				read.next();
				int a = read.nextInt();
				read.nextLine();
				for(int i = 0; i<a; i++) {
					bot.addSoldier(new Card(read.next(), read.next()));
					read.nextLine();
				}
				
				read.next();
				a = read.nextInt();
				read.nextLine();
				for(int i = 0; i<a; i++) {
					player.addSoldier(new Card(read.next(), read.next()));
					read.nextLine();
				}
				
				read.close();
				break;
			} catch(FileNotFoundException f) {
				System.out.println("File not found");
			} 
		}
	}

	/**
	 * Runs the game
	 * @param args Not used currently
	 */
	public static void main(String[] args) {

		int game = 1;
		
		// Option to display rules, not play or play
		while(true) {
			try {
				System.out.println("Choose an option\n 1 - Rules\n 2 - Play\n 3 - Exit");
				game = scan.nextInt();
				scan.nextLine();
				
				switch(game) {
				case 1:
					System.out.println("The objective of the game is to win all of the cards.\n"
							+ "The deck is divided evenly among the players, giving each a down stack.\n"
							+ "In unison, each player reveals the top card of their deck—this is a \"battle\"—and\n"
							+ "the player with the higher card takes both of the cards played and moves them to their stack.\n"
							+ "Aces are high, and suits are ignored.\n"
							+ "If the two cards played are of equal value, then there is a \"war\".\n"
							+ "Both players place the next three cards face down and then another card face-up.\n"
							+ "The owner of the higher face-up card wins the war and adds all the cards on the table to the bottom of their deck.\n"
							+ "If the face-up cards are again equal then the battle repeats with another set of face-down/up cards.\n"
							+ "This repeats until one player's face-up card is higher than their opponent's.");
					continue;
				case 2:
					game = 1;
					break;
				case 3:
					game = 0;
					break;
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
		
		if(game == 0) {
			return;
		}
		
		boolean gameLoaded = false;
		
		CardSet deck = new CardSet(false);
		deck.shuffle();
		
		Battalion bot = new Battalion();
		Battalion player = new Battalion();
		
		Card veteranBot;
		Card veteranPlayer;
		
		bot.reverse();
		player.reverse();
		
		List<Card> field = new ArrayList<Card>();
		
		Instant start = Instant.now();
		Duration before = Duration.ZERO;
		
		// Option to load a game or not
		while(true) {
			try {
				System.out.println("Load game? 1 = yes, other = no");
				game = scan.nextInt();
				scan.nextLine();
				if(game == 1) {
					loadGame(bot, player, before);
					gameLoaded = true;
				}
				else {
					for(int i=1; i<=deck.getTotalCards(); i++) {
						if(i%2 == 0) {
							player.addSoldier(deck.drawCard());
						} else {
							bot.addSoldier(deck.drawCard());
						}
					}
				}
				break;
			} catch(InputMismatchException e) {
				System.out.println("Must be integer or an error with the save game occured\n"
						+ "starting new game");
				for(int i=1; i<=deck.getTotalCards(); i++) {
					if(i%2 == 0) {
						player.addSoldier(deck.drawCard());
					} else {
						bot.addSoldier(deck.drawCard());
					}
				}
				break;
			}
		}
		
		game = 1;
		
		// Actual game
		while(bot.size() != 0 || player.size() != 0) {
			
			while(true) {
				try {
					System.out.printf("Player: %d Cards left | 0 = Exit , Other Integer = Continue\n",player.size());
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
			
			System.out.printf("Bot : %s - %s Player\n", veteranBot.toString(), veteranPlayer.toString());
			
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
				veteranPlayer = player.nextSoldier();
				System.out.printf("Bot : %s - %s Player\n", veteranBot.toString(), veteranPlayer.toString());
				
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
		
		if(player.size() == 0) System.out.println("You Lost");
		if(bot.size() == 0) System.out.println("You Won");
		
		Instant end = Instant.now();
		System.out.println(Duration.between(start, end).toSeconds() + "seconds game time");
		
		// Saving the game
		saveGame(bot, player, Duration.between(start, end).plus(before), gameLoaded);
		
		scan.close();

	}

}
