package application;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class AddPlayedGame {

	GridPane pane = new GridPane();
	Scene scene = new Scene(pane, 360, 220);

	// creating combo box
	ComboBox<Game> cmbGames = new ComboBox<Game>();
	ComboBox<Player> cmbPlayers = new ComboBox<Player>();

	public AddPlayedGame(Stage stage, Scene backscene) {
		// Create a pane and set its properties
		pane.setAlignment(Pos.CENTER);
		pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
		pane.setHgap(5.5);
		pane.setVgap(5.5);

		DataRepository repo = new DataRepository();
		try {
			cmbPlayers.setItems(FXCollections.observableArrayList(repo.SelectPlayer()));
			cmbGames.setItems(FXCollections.observableArrayList(repo.SelectGame()));
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		// Place nodes in pane
		pane.add(new Label("Select Player"), 0, 0);
		pane.add(cmbPlayers, 1, 0);
		cmbPlayers.setMaxWidth(10000);

		pane.add(new Label("Select Game"), 0, 1);
		pane.add(cmbGames, 1, 1);
		cmbGames.setMaxWidth(10000);

		pane.add(new Label("Enter Points"), 0, 2);
		TextField txtScore = new TextField();

		// Numeric Validation for Score TextField
		txtScore.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("|[-\\+]?|[-\\+]?\\d+\\.?|[-\\+]?\\d+\\.?\\d+")) {
					txtScore.setText(oldValue);
				}
			}
		});
		pane.add(txtScore, 1, 2);

		pane.add(new Label("Enter Date"), 0, 3);
		DatePicker dtpPlayingDate = new DatePicker();
		pane.add(dtpPlayingDate, 1, 3);

		// place nodes in third pane
		Button btCancel = new Button("Cancel");
		btCancel.setOnAction(e -> stage.setScene(backscene));
		pane.add(btCancel, 0, 4);
		GridPane.setValignment(btCancel, VPos.TOP);

		Button btSave = new Button("Save");
		btSave.setOnAction(e -> {
			// Only save if the user has input all the necessary data
			if (cmbPlayers.getValue() != null && cmbGames.getValue() != null && !txtScore.getText().isEmpty()
					&& dtpPlayingDate.getValue() != null) {
				ArrayList<PlayerGame> playedGames = cmbPlayers.getValue().getPlayedGames();
				playedGames.add(new PlayerGame(cmbPlayers.getValue().getId(), cmbGames.getValue().getId(),
						cmbGames.getValue().getTitle(), Date.valueOf(dtpPlayingDate.getValue()),
						Double.parseDouble(txtScore.getText())));
				
				try {
					repo.UpdatePlayer(cmbPlayers.getValue(),true);
					stage.setScene(backscene);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			}
		});
		pane.add(btSave, 1, 4);
		GridPane.setHalignment(btSave, HPos.RIGHT);
	}

	public Scene getScene() {
		return this.scene;
	}

}
