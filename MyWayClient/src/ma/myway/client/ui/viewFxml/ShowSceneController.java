package ma.myway.client.ui.viewFxml;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import ma.myway.client.ui.Main;

public class ShowSceneController {
	
	
	ObservableList<String> OptionList = FXCollections.observableArrayList("Ajouter",
			"Supprimer","Modifier","Afficher liste","Statistique");
	
	private Main main;
	
	@FXML
	private ChoiceBox OptionBox;
	
	@FXML
	private void showAgencyAjout() throws IOException {
		main.showAgencyAjoutScene();
	}
	
	@FXML
	private void initialize() {
		OptionBox.setValue("Choisissez une option");
		OptionBox.setItems(OptionList);
	}
}
