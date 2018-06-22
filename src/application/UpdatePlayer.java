package application;

import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class UpdatePlayer {
	GridPane pane = new GridPane();
	Scene scene = new Scene(pane);

	TextField txtFirstName = new TextField(), txtLastName = new TextField(), txtAddress = new TextField(),
			txtPostalCode = new TextField(), txtProvice = new TextField(), txtPhone = new TextField();
	ComboBox<Player> cmbPlayer = new ComboBox<>();

	public UpdatePlayer(Stage stage, Scene backScene) {
		// Create a pane and set its properties
		pane.setAlignment(Pos.CENTER);
		pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
		pane.setHgap(5.5);
		pane.setVgap(5.5);

		DataRepository repo = new DataRepository();

		try {
			cmbPlayer.setItems(FXCollections.observableArrayList(repo.SelectPlayer()));
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		cmbPlayer.setMaxWidth(10000);

		cmbPlayer.setOnAction(e -> {
			if(cmbPlayer.getValue() != null){
				txtFirstName.setText(cmbPlayer.getValue().getFirstName());
				txtLastName.setText(cmbPlayer.getValue().getLastName());
				txtAddress.setText(cmbPlayer.getValue().getAddress());
				txtPostalCode.setText(cmbPlayer.getValue().getPostalCode());
				txtProvice.setText(cmbPlayer.getValue().getProvince());
				txtPhone.setText(cmbPlayer.getValue().getPhoneNumber());
			}
		});

		// Place nodes in the pane
		pane.add(new Label("Select one player:"), 0, 0);
		pane.add(cmbPlayer, 1, 0);
		pane.add(new Label("First Name:"), 0, 1);
		pane.add(txtFirstName, 1, 1);
		pane.add(new Label("Last Name:"), 0, 2);
		pane.add(txtLastName, 1, 2);
		pane.add(new Label("Address:"), 0, 3);
		pane.add(txtAddress, 1, 3);
		pane.add(new Label("Postal Code:"), 0, 4);
		pane.add(txtPostalCode, 1, 4);
		pane.add(new Label("Province:"), 0, 5);
		pane.add(txtProvice, 1, 5);
		pane.add(new Label("Phone:"), 0, 6);
		pane.add(txtPhone, 1, 6);

		Button btCancel = new Button("Cancel");
		btCancel.setOnAction(e -> stage.setScene(backScene));
		pane.add(btCancel, 0, 7);
		GridPane.setValignment(btCancel, VPos.TOP);

		Button btSave = new Button("Save");
		btSave.setOnAction(e -> {
			if (!txtFirstName.getText().isEmpty()) {
				cmbPlayer.getValue().setFirstName(txtFirstName.getText());
				cmbPlayer.getValue().setLastName(txtLastName.getText());
				cmbPlayer.getValue().setAddress(txtAddress.getText());
				cmbPlayer.getValue().setPostalCode(txtPostalCode.getText());
				cmbPlayer.getValue().setProvince(txtProvice.getText());
				cmbPlayer.getValue().setPhoneNumber(txtPhone.getText());

				try {
					repo.UpdatePlayer(cmbPlayer.getValue(), false);
					stage.setScene(backScene);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		pane.add(btSave, 1, 7);
		GridPane.setHalignment(btSave, HPos.RIGHT);

	}

	public Scene getScene() {
		return this.scene;
	}
}
