package ma.myway.client.ui.view;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;

public class UIAdmin implements ViewMaker {

	@Override
	public Scene getScene() {

		VBox pane = new VBox();
		pane.setSpacing(10);
		Scene scene = new Scene(pane, 300, 180);

		return scene;
	}

}
