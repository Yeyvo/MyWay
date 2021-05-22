package ma.myway.client.ui.viewFxml;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import ma.myway.client.ui.Main;

public class ShowSceneController {
	
	
	/*ObservableList<String> OptionList = FXCollections.observableArrayList("Ajouter",
			"Supprimer","Modifier","Afficher liste","Statistique");*/
	
	private Main main = new Main();
	
	/*@FXML
	private ChoiceBox OptionBox;*/
	
	@FXML
	private void setAjout() throws IOException {
		main.choiceAjouter();
	}
	
	@FXML
	private void setModif() throws IOException {
		main.choiceModifier();
	}
	
	@FXML
	private void setSuppr() throws IOException {
		main.choiceSupprimer();
	}
	
	@FXML
	private void setAffich() throws IOException {
		main.choiceAfficher();
	}
	
	@FXML
	private void setStat() throws IOException {
		main.choiceStatistique();
	}
	
	@FXML
	private void showAgency() throws IOException{
		main.showAgencyScene();
	}
	
	@FXML
	private void showRoutes() throws IOException{
		main.showRoutesScene();
	}
	
	@FXML
	private void showStops() throws IOException{
		main.showStopsScene();
	}
	
	@FXML
	private void showTrips() throws IOException{
		main.showTripsScene();
	}
	
	@FXML
	private void showCalendar() throws IOException{
		main.showCalendarScene();
	}
	
	@FXML
	private void showCalendarDates() throws IOException{
		main.showCalendarDatesScene();
	}
	
	@FXML
	private void showStopTimes() throws IOException{
		main.showStopTimesScene();
	}
	
	@FXML
	private void showTransfers() throws IOException{
		main.showTransfersScene();
	}
	
	/*@FXML
	private void initialize() {
		OptionBox.setValue("Choisissez une option");
		OptionBox.setItems(OptionList);
	}*/
}
