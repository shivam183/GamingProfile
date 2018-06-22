package application;

import java.sql.SQLException;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class AddPlayer {
	GridPane pane = new GridPane();
	Scene scene = new Scene(pane);

	TextField txtFirstName = new TextField(), txtLastName = new TextField(), txtAddress = new TextField(),
			txtPostalCode = new TextField(), txtProvice = new TextField(), txtPhone = new TextField();

	public AddPlayer(Stage stage, Scene backScene) {
		// Create a pane and set its properties
		pane.setAlignment(Pos.CENTER);
		pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
		pane.setHgap(5.5);
		pane.setVgap(5.5);

		// Place nodes in the pane
		pane.add(new Label("First Name:"), 0, 0);
		pane.add(txtFirstName, 1, 0);
		pane.add(new Label("Last Name:"), 0, 1);
		pane.add(txtLastName, 1, 1);
		pane.add(new Label("Address:"), 0, 2);
		pane.add(txtAddress, 1, 2);
		pane.add(new Label("Postal Code:"), 0, 3);
		pane.add(txtPostalCode, 1, 3);
		pane.add(new Label("Province:"), 0, 4);
		pane.add(txtProvice, 1, 4);
		pane.add(new Label("Phone:"), 0, 5);
		pane.add(txtPhone, 1, 5);

		Button btCancel = new Button("Cancel");
		btCancel.setOnAction(e -> stage.setScene(backScene));
		pane.add(btCancel, 0, 6);
		GridPane.setValignment(btCancel, VPos.TOP);

		Button btSave = new Button("Save");
		btSave.setOnAction(e -> {
			DataRepository repo = new DataRepository();
			if (!txtFirstName.getText().isEmpty()) {
				Player pObj = new Player(txtFirstName.getText(), txtLastName.getText(), txtAddress.getText(),
						txtPostalCode.getText(), txtProvice.getText(), txtPhone.getText());
				try {
					repo.InsertPlayer(pObj);
					stage.setScene(backScene);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		pane.add(btSave, 1, 6);
		GridPane.setHalignment(btSave, HPos.RIGHT);

	}

	public Scene getScene() {
		return this.scene;
	}
}
