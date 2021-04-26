package application;

/**
 * Entry point of game GUI.
 * 
 * NOT IMPLEMENTED YET!!!
 * 
 * Plannning on using Server and Client classes to run a multiplyer or singleplayer online game
 */

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.control.*;
import javafx.geometry.*;
//import javafx.event.*;

import java.util.Scanner;
import java.io.File;

import zad.*;


public class Main extends Application {
	
	private static CardSet deck = new CardSet(2, false);
	private static Card drawn;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			//main stage settings
			
			primaryStage.setTitle("Cards");
			primaryStage.setHeight(500);
			primaryStage.setWidth(750);
			//primaryStage.getIcons().add(new Image("file:icon.png"));
			
			//panes settings
			
			BorderPane borderPane = new BorderPane();
			
			GridPane grid = new GridPane();
			grid.setAlignment(Pos.CENTER);
			grid.setHgap(0);
			grid.setVgap(0);
			grid.setPadding(new Insets(10, 25, 25, 25));
			
			GridPane input = new GridPane();
			input.setAlignment(Pos.CENTER);
			input.setHgap(10);
			input.setVgap(10);
			input.setPadding(new Insets(10, 25, 25, 25));
			
			FlowPane titleBox = new FlowPane();
			titleBox.setAlignment(Pos.CENTER);
			titleBox.setHgap(10);
			titleBox.setVgap(10);
			titleBox.setPadding(new Insets(10, 25, 0, 25));
			
			GridPane buttons = new GridPane();
			buttons.setAlignment(Pos.CENTER);
			buttons.setHgap(10);
			buttons.setVgap(10);
			buttons.setPadding(new Insets(10, 25, 0, 25));
			
			//menu
			Text newGame = new Text("New game");
			input.add(newGame, 0, 0);
			
			Text online = new Text("Online");
			input.add(online, 0, 2);
			online.setVisible(false);
			
			Text exitGame = new Text("Exit");
			input.add(exitGame, 0, 1);
			
			Text card = new Text();
			grid.add(card, 0, 0);
			
			Button hit = new Button("Hit");
			hit.setVisible(false);
			Button stand = new Button("Stand");
			stand.setVisible(false);
			Button split = new Button("Split");
			split.setVisible(false);
			
			buttons.add(hit, 0, 0);
			buttons.add(stand, 1, 0);
			buttons.add(split, 2, 0);
			
			newGame.setOnMouseClicked(e -> {
				newGame.setVisible(false);
				exitGame.setVisible(false);
				
				
				
				if(!(deck.getAvailableCards() <= 0)) {
					drawn = deck.drawCard();
					card.setText(drawn.toString());
				}
			});
			
			newGame.setOnMouseEntered(e -> {
				newGame.setFill(Color.RED);
			});
			
			newGame.setOnMouseExited(e -> {
				newGame.setFill(Color.BLACK);
			});
			
			exitGame.setOnMouseClicked(e -> {
				Platform.exit();
			});
			
			exitGame.setOnMouseEntered(e -> {
				exitGame.setFill(Color.RED);
			});
			
			exitGame.setOnMouseExited(e -> {
				exitGame.setFill(Color.BLACK);
			});
			
			hit.setOnMouseClicked(e -> {
				
			});
			
			borderPane.setCenter(grid);
			borderPane.setLeft(input);
			borderPane.setTop(titleBox);
			borderPane.setBottom(buttons);

			Scene scene = new Scene(borderPane, 300, 275);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
	        
	        primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		deck.shuffle();
		launch(args);
	}
}
