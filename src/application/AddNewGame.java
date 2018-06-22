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


public class AddNewGame {


    GridPane pane = new GridPane();
    Scene scene = new Scene(pane);
    TextField txtGameTitle = new TextField();

    public  AddNewGame(Stage stage, Scene backScene) {
        // Create a pane and set its properties
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
        pane.setHgap(5.5);
        pane.setVgap(5.5);

        // Place nodes in the pane
        pane.add(new Label("Game Title (Eg: Mario)"), 0, 0);
        pane.add(txtGameTitle, 1, 0);

        Button btCancel = new Button("Cancel");
        btCancel.setOnAction(e -> stage.setScene(backScene));
        pane.add(btCancel, 0, 1);
        GridPane.setValignment(btCancel, VPos.TOP);

        Button btSave = new Button("Save");
        btSave.setOnAction(e ->{
        	DataRepository repo = new DataRepository();
			if (!txtGameTitle.getText().isEmpty()) {
				Game gObj = new Game(txtGameTitle.getText());
				try {
					repo.InsertGame(gObj);
					stage.setScene(backScene);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
        });
        pane.add(btSave, 1, 1);
        GridPane.setHalignment(btSave, HPos.RIGHT);
    }


        // Create a scene and place it in the stage
        public Scene getScene()
        {
            return this.scene;
        }
    }





