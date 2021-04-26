/**
 * Usage java Server port
 * Starts the server on localhost on specified port.
 * Possibility to select up to 4 players still not implemented correctly.
 * Not properly tested, if game goes out of sync please report.
 */

import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.util.InputMismatchException;

import zad.*;

public class Server extends Thread{
	
	private ServerSocket serverSocket;
	private Scanner scan = new Scanner(System.in);
	
	public Server(int port) throws IOException {
		serverSocket = new ServerSocket(port);
		serverSocket.setSoTimeout(10000);
	}
	
	public void run() {
		
		CardSet deck = new CardSet(2,false);
		deck.shuffle();
		
		int players = 0;
		while(players == 0) {
			try {
				System.out.println("Select players: 1-4");
				players = scan.nextInt();
				if(players < 0) {
					System.out.println("Must be greater than 0");
					players = 0;
				} else if (players > 4) {
					System.out.println("Set to max value 4");
					players = 4;
				}
				break;
			} catch (InputMismatchException e) {
				System.out.println("Must be integer");
			}
		}
		
		while(true) {
			try {
				System.out.println("Waiting on " + serverSocket.getLocalPort());
				Socket server = serverSocket.accept(); //wait for connection and connect
				
				System.out.println("Connected to " + server.getRemoteSocketAddress());
				DataInputStream in = new DataInputStream(server.getInputStream()); //receive data
				
				
				DataOutputStream out = new DataOutputStream(server.getOutputStream());
				
				//game
				
				int money = 1000;
				
				Hand house = new Hand();
				Hand player = new Hand();

				int bet;
				
				while(money > 0) {
					
					if(deck.getAvailableCards() < deck.getTotalCards()/2) {
						deck.resetSet();
						deck.shuffle();
					}
					
					System.out.println(money);
					out.writeInt(money);
					
					bet = 0;
					house = new Hand();
					player = new Hand();

					while(true) {
						try {
							System.out.println("Select bet: (input letter to exit)");
							out.writeUTF("Select bet: (input letter to exit)");
							
							bet = (int)in.readDouble();
							
							if(money - bet >= 0) {
								out.writeInt(0);
								break;
							} else {
								System.out.println("Not enough money");
								out.writeInt(1);
								out.writeUTF("Not enough money");
								continue;
							}
						} catch(InputMismatchException e) {
							System.out.println("Closing");
							out.writeInt(-1);
							out.writeUTF("Closing");
							money = -100000;
							break;
						}
					}
					
					if(money < 0) {break;}

					player.addCard(deck.drawCard());
					house.addCard(deck.drawCard());
					player.addCard(deck.drawCard());
					house.addCard(deck.drawCard());

					System.out.println("House = " + house.previewHouse());
					out.writeUTF("House = " + house.previewHouse());

					while(true) {
						System.out.println(player.toString() + "= " + String.valueOf(player.handValue()));
						System.out.println("1 - Draw \nother - Ok");
						out.writeUTF(player.toString() + "= " + String.valueOf(player.handValue()) +
								"\n1 - Draw \nother - Ok");

						int selection = (int) in.readDouble();
						if(selection == 1) {
							player.addCard(deck.drawCard());
							if(player.handValue() >= 21) {out.writeInt(0); break;}
							out.writeInt(1);
						} else {
							out.writeInt(0);
							break;
						}
					}

					while(house.handValue() < 17) {
						house.addCard(deck.drawCard());
					}

					System.out.println(player.toString() + " = " + player.handValue());
					System.out.println("House");
					System.out.println(house.toString()  + " = " + house.handValue());
					
					out.writeUTF(player.toString() + " = " + player.handValue()
					+ "\nHouse \n" + house.toString()  + " = " + house.handValue());

					if(house.handValue() > player.handValue() && house.handValue() <= 21) {
						System.out.println("You lose");
						out.writeUTF("You lose");
						money = money - bet;
					} else if(player.handValue() > 21) {
						System.out.println("You lose");
						out.writeUTF("You lose");
						money = money - bet;
					} else if(player.handValue() == house.handValue()){
						System.out.println("Draw");
						out.writeUTF("Draw");
					} else {
						System.out.println("You win");
						out.writeUTF("You win");
						money = money + bet;
					}
					out.flush();
					out.writeInt(0);
				}
				
				out.writeInt(-1);
				
				server.close();
			} catch(SocketTimeoutException s) {
				System.out.println("Timed out");
				break;
			} catch(IOException e) {
				e.printStackTrace();
				break;
			}
			
		}
		
	}

	public static void main(String[] args) {
		
		int port = Integer.parseInt(args[0]);
		try {
			Thread t = new Server(port);
			t.start();
		} catch(IOException e) {
			e.printStackTrace();
		}

	}

}