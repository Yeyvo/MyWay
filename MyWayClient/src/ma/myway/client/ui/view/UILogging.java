package ma.myway.client.ui.view;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ma.myway.client.network.Client;
import ma.myway.client.ui.Main;
import ma.myway.client.ui.model.SceneName;

public class UILogging implements ViewMaker {

	private Stage stage;

	public UILogging(Stage stage) {
		this.stage = stage;
	}

	@SuppressWarnings("static-access")
	@Override
	public Scene getScene() {

		VBox pane = new VBox();
		pane.setSpacing(10);
		Scene scene = new Scene(pane, 300, 180);

		VBox usernameVbox = new VBox();
		Label lblusername = new Label("\t Username :");
		TextField username = new TextField();
		//username.setText("");
		usernameVbox.getChildren().addAll(lblusername, username);
		usernameVbox.setSpacing(5);

		VBox passwordVbox = new VBox();
		Label lblpswd = new Label("\t Password :");
		PasswordField pswd = new PasswordField();
		//pswd.setText("");
		passwordVbox.getChildren().addAll(lblpswd, pswd);
		passwordVbox.setSpacing(5);

		Button connection = new Button("Connection");

		connection.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				if (!username.getText().isBlank() && !pswd.getText().isBlank()) {
					if (Client.conn(username.getText(), pswd.getText())) {
						stage.setScene(Main.getScenes().get(SceneName.BASE));
						stage.centerOnScreen();
					}else {
						Alert alert = new Alert(AlertType.ERROR, "USERNAME / PASSWORD invalid");
						alert.show();
					}
				}
			}
		});

		pane.getChildren().setAll(usernameVbox, passwordVbox, connection);
		pane.setMargin(usernameVbox, new Insets(5, 10, 0, 10));
		pane.setMargin(passwordVbox, new Insets(0, 10, 0, 10));
		pane.setMargin(connection, new Insets(5, scene.getWidth() * 0.35, 5, scene.getWidth() * 0.35));

		return scene;
	}

}
