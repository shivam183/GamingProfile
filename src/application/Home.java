package application;

import javafx.application.Application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Home extends Application {

	private static final int BUTTON_MIN_WIDTH = 50;

	@Override // Override the start method in the Application class
	public void start(Stage primaryStage) {
		// Create a pane and set its properties
		GridPane pane = new GridPane();
		Scene scene = new Scene(pane, 320, 210);

		pane.setAlignment(Pos.CENTER);
		pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
		pane.setHgap(5.5);
		pane.setVgap(5.5);

		// Place nodes in the pane
		pane.add(new Label("Add Player"), 0, 0);
		Button btAddPlayer = new Button("GO");
		btAddPlayer.setMinWidth(BUTTON_MIN_WIDTH);
		btAddPlayer.setOnAction(e -> {
			primaryStage.setScene(new AddPlayer(primaryStage, scene).getScene());
		});
		pane.add(btAddPlayer, 1, 0);

		pane.add(new Label("Update Player"), 0, 1);
		Button btUpdatePlayer = new Button("GO");
		btUpdatePlayer.setMinWidth(BUTTON_MIN_WIDTH);
		btUpdatePlayer.setOnAction(e -> {
			primaryStage.setScene(new UpdatePlayer(primaryStage, scene).getScene());
		});
		pane.add(btUpdatePlayer, 1, 1);

		pane.add(new Label("Add Game"), 0, 2);
		Button btAddGame = new Button("GO");
		btAddGame.setMinWidth(BUTTON_MIN_WIDTH);
		btAddGame.setOnAction(e -> {
			primaryStage.setScene(new AddNewGame(primaryStage, scene).getScene());
		});
		pane.add(btAddGame, 1, 2);

		pane.add(new Label("Add Played Games"), 0, 3);
		Button btAddPlayedGame = new Button("GO");
		btAddPlayedGame.setMinWidth(BUTTON_MIN_WIDTH);
		btAddPlayedGame.setOnAction(e -> {
			primaryStage.setScene(new AddPlayedGame(primaryStage, scene).getScene());
		});
		pane.add(btAddPlayedGame, 1, 3);


		pane.add(new Label("Display Info"), 0, 4);
		Button btDisplay = new Button("GO");
		btDisplay.setMinWidth(BUTTON_MIN_WIDTH);
		btDisplay.setOnAction(e -> {
			primaryStage.setScene(new Display(primaryStage, scene).getScene());
		});
		pane.add(btDisplay, 1, 4);

		// Create a scene and place it in the stage
		primaryStage.setTitle("Welcome Fellow Gamers"); // Set the stage title
		primaryStage.setScene(scene); // Place the scene in the stage
		primaryStage.show(); // Display the stage
	}

	public static void main(String[] args) {
		launch(args);
	}

}
