package application;

import java.sql.Date;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;


public class Display {

	GridPane pane = new GridPane();
	Scene scene = new Scene(pane, 450, 400);

	HashMap<Integer, Player> playerList = new HashMap<>();
	Player selectedPlayer = null;
	ComboBox<Player> cmbPlayer = new ComboBox<>();
	TableView<PlayerGame> table = new TableView<>();

	@SuppressWarnings("unchecked")
	public Display(Stage stage, Scene backScene) {
		// Create a pane and set its properties
		pane.setAlignment(Pos.CENTER);
		pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
		pane.setHgap(5.5);
		pane.setVgap(5.5);

		try {
			ArrayList<Player> dataList = new DataRepository().SelectPlayer();
			for (Player p : dataList) {
				playerList.put(p.getId(), p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		cmbPlayer.setItems(FXCollections.observableArrayList(playerList.values()));
		cmbPlayer.setMaxWidth(10000);
		cmbPlayer.setOnAction(e -> {
			if (cmbPlayer.getValue() != null) {
				selectedPlayer = cmbPlayer.getValue();
			}
		});

		// Place nodes in the pane
		pane.add(new Label("Select a player:"), 0, 0);
		pane.add(cmbPlayer, 1, 0);

		Button btFilter = new Button("Search");
		btFilter.setOnAction(e -> {
			if (selectedPlayer != null) {
				table.setItems(FXCollections.observableArrayList(selectedPlayer.getPlayedGames()));
			}
		});

		pane.add(btFilter, 1, 1);
		GridPane.setHalignment(btFilter, HPos.RIGHT);

		TableColumn<PlayerGame, String> c1 = new TableColumn<>("Game");
		TableColumn<PlayerGame, String> c2 = new TableColumn<>("Date");
		TableColumn<PlayerGame, String> c3 = new TableColumn<>("Score");
		TableColumn<PlayerGame, Boolean> colDelete = new TableColumn<>("Delete");

		c1.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<PlayerGame, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<PlayerGame, String> arg0) {
						return new ReadOnlyObjectWrapper<String>(String.valueOf(arg0.getValue().getGameTitle()));
					}
				});
		c1.setPrefWidth(150);

		c2.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<PlayerGame, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<PlayerGame, String> arg0) {
						Date date = arg0.getValue().getPlayingDate();

						return new ReadOnlyObjectWrapper<String>(
								date.toLocalDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));
					}
				});
		c2.setPrefWidth(100);

		c3.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<PlayerGame, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<PlayerGame, String> arg0) {
						return new ReadOnlyObjectWrapper<String>(String.valueOf(arg0.getValue().getScore()));
					}
				});
		c3.setPrefWidth(80);

		colDelete.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<PlayerGame, Boolean>, ObservableValue<Boolean>>() {

					@Override
					public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<PlayerGame, Boolean> p) {
						return new SimpleBooleanProperty(p.getValue() != null);
					}
				});
		// Adding the Button to the cell
		colDelete.setCellFactory(new Callback<TableColumn<PlayerGame, Boolean>, TableCell<PlayerGame, Boolean>>() {

			@Override
			public TableCell<PlayerGame, Boolean> call(TableColumn<PlayerGame, Boolean> p) {
				return new ButtonCell();
			}

		});
		colDelete.setStyle("-fx-alignment: CENTER;");

		table.getColumns().setAll(c1, c2, c3, colDelete);
		table.setPrefWidth(430);
		pane.add(table, 0, 2, 2, 1);

		Button btBack = new Button("Back");
		btBack.setOnAction(e -> stage.setScene(backScene));
		pane.add(btBack, 1, 3);
		GridPane.setHalignment(btBack, HPos.RIGHT);
	}

	public Scene getScene() {
		return this.scene;
	}

	// DeleteButton Inner Class
	private class ButtonCell extends TableCell<PlayerGame, Boolean> {
		final Button cellButton = new Button("X");

		ButtonCell() {
			// Action when the button is pressed
			cellButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent t) {
					// get Selected Item
					PlayerGame pgObj = (PlayerGame) ButtonCell.this.getTableView().getItems()
							.get(ButtonCell.this.getIndex());

					DataRepository repo = new DataRepository();
					// remove selected item from the table list
					try {
						repo.DeletePlayerGame(pgObj);
						table.getItems().remove(pgObj);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			});
		}

		// Display button if the row is not empty
		@Override
		protected void updateItem(Boolean item, boolean empty) {
			super.updateItem(item, empty);

			if (empty || item == null) {
		         setGraphic(null);
		     } else {
		    	 setGraphic(cellButton);
		     }
		}
	}
}
