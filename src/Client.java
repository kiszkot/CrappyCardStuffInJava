/**
 * Usage java Client host port
 * 
 * Connects to a running game on host:port. If no games are running the connection will fail.
 * If game goes out of sync please report.
 * 
 * Possible to run the game on the internet if port forwarding is set correctly.
 */

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client {
	
	static Scanner scan = new Scanner(System.in);
	
	public static void main(String[] args) {
		
		String serverName = args[0];
		int port = Integer.parseInt(args[1]);
		
		try {
			System.out.println("Connecting " + serverName + " on " + port);
			Socket client = new Socket(serverName, port);
			
			System.out.println("Connected " + client.getRemoteSocketAddress());
			OutputStream outToServer = client.getOutputStream();
			DataOutputStream out = new DataOutputStream(outToServer);
			
			InputStream inFromServer = client.getInputStream();
			DataInputStream in = new DataInputStream(inFromServer);
			
			//game
			int exit = 0;
			while(exit != -1) {
				
				System.out.println(in.readInt());
				
				//bet selecting
				System.out.println(in.readUTF());
				out.writeDouble(scan.nextDouble());
				scan.nextLine();

				int a = in.readInt();
				while(a != 0) {
					if(a == -1) {
						client.close();
						return;
					}
					System.out.println(in.readUTF());
					System.out.println(in.readUTF());
					out.writeDouble(scan.nextDouble());
					scan.nextLine();
					a = in.readInt();
				}

				//if(a == -1) {client.close(); return; }
				//card drawing
				System.out.print(in.readUTF());
				System.out.print(in.readUTF());
				out.writeDouble(scan.nextDouble());
				scan.nextLine();

				a = in.readInt();
				while(a != 0) {
					System.out.println(in.readUTF());
					out.writeDouble(scan.nextDouble());
					scan.nextLine();
					a = in.readInt();
				}
				
				//results
				System.out.println(in.readUTF());
				System.out.println(in.readUTF());
				exit = in.readInt();
				out.flush();
			}
			
			client.close();
			scan.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
