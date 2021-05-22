package ma.myway.client.ui.viewFxml;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.stage.Stage;
import ma.myway.client.ui.Main;


public class ShowSceneController {
	
	
	/*ObservableList<String> OptionList = FXCollections.observableArrayList("Ajouter",
			"Supprimer","Modifier","Afficher liste","Statistique");*/
	
	private Main main = new Main();
	
	/*@FXML
	private ChoiceBox OptionBox;*/
	
	@FXML
	private void showAjout() throws IOException {
		main.showAjouterScene();;
	}
	
	@FXML
	private void showModif() throws IOException {
		main.showModifierScene();
	}
	
	@FXML
	private void showSuppr() throws IOException {
		main.showSupprimerScene();
	}
	
	@FXML
	private void showAffich() throws IOException {
		main.showAfficherScene();
	}
	
	@FXML
	private void showStat() throws IOException {
		main.showStatistiqueScene();
	}
	
	@FXML
	private void setAgency() throws IOException{
		main.choiceAgency();
	}
	
	@FXML
	private void setRoutes() throws IOException{
		main.choiceRoutes();
	}
	
	@FXML
	private void setStops() throws IOException{
		main.choiceStops();
	}
	
	@FXML
	private void setTrips() throws IOException{
		main.choiceTrips();
	}
	
	@FXML
	private void setCalendar() throws IOException{
		main.choiceCalendar();
	}
	
	@FXML
	private void setCalendarDates() throws IOException{
		main.choiceCalendarDates();
	}
	
	@FXML
	private void setStopTimes() throws IOException{
		main.choiceStopTimes();
	}
	
	@FXML
	private void setTransfers() throws IOException{
		main.choiceTransfers();
	}
	
	@FXML
	private void setUsers() throws IOException{
		main.choiceUsers();
	}
	
	@FXML 
	private javafx.scene.control.Button btnGestionAdmin;
	
	@FXML
	private void showGestion() throws IOException{
	    Stage stage = (Stage) btnGestionAdmin.getScene().getWindow();
	    stage.close();
		main.showGestionScene();
	}
	
	@FXML 
	private javafx.scene.control.Button btnUseApp;
	
	@FXML
	private void showApp() throws IOException{
		Stage stage = (Stage) btnUseApp.getScene().getWindow();
	    stage.close();
	    main.showAppScene();
	}
	
	@FXML
	private javafx.scene.control.Button btnQuitApp;
	
	@FXML
	private void quitApp() {
	    System.exit(0);
	    main.quitAppScene();
	}
	
	@FXML
	private javafx.scene.control.Button btnReturnAdmin;
	
	@FXML
	private void returnMenuAdmin() {
		Stage stage = (Stage) btnReturnAdmin.getScene().getWindow();
	    stage.close();
	    main.showMenuAdmin();
	}
	
	@FXML
	private javafx.scene.control.Button btnConfirAjoutAgency;
	
	@FXML
	private void confirmationAjoutAgency() {
		
	}
	@FXML
	private javafx.scene.control.Button btnConfirModifAgency;
	
	@FXML
	private javafx.scene.control.Button btnConfirSupprAgency;
	
	/*@FXML
	private void initialize() {
		OptionBox.setValue("Choisissez une option");
		OptionBox.setItems(OptionList);
	}*/
}
