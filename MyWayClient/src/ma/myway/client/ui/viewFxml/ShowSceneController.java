package ma.myway.client.ui.viewFxml;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import ma.myway.client.network.Client;
import ma.myway.client.ui.Main;
import ma.myway.graph.data.Agency;

public class ShowSceneController {

	/*
	 * ObservableList<String> OptionList =
	 * FXCollections.observableArrayList("Ajouter",
	 * "Supprimer","Modifier","Afficher liste","Statistique");
	 */

	private Main main = new Main();

	/*
	 * @FXML private ChoiceBox OptionBox;
	 */

	@FXML
	private void showAjout() throws IOException {
		main.showAjouterScene();
		;
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
	private void setAgency() throws IOException {
		main.choiceAgency();
	}

	@FXML
	private void setRoutes() throws IOException {
		main.choiceRoutes();
	}

	@FXML
	private void setStops() throws IOException {
		main.choiceStops();
	}

	@FXML
	private void setTrips() throws IOException {
		main.choiceTrips();
	}

	@FXML
	private void setCalendar() throws IOException {
		main.choiceCalendar();
	}

	@FXML
	private void setCalendarDates() throws IOException {
		main.choiceCalendarDates();
	}

	@FXML
	private void setStopTimes() throws IOException {
		main.choiceStopTimes();
	}

	@FXML
	private void setTransfers() throws IOException {
		main.choiceTransfers();
	}

	@FXML
	private void setUsers() throws IOException {
		main.choiceUsers();
	}

	@FXML
	private javafx.scene.control.Button btnGestionAdmin;

	@FXML
	private void showGestion() throws IOException {
		Stage stage = (Stage) btnGestionAdmin.getScene().getWindow();
		stage.close();
		main.showGestionScene();
	}

	@FXML
	private javafx.scene.control.Button btnUseApp;

	@FXML
	private void showApp() throws IOException {
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
	
	
	//gestion agency
	@FXML
	private javafx.scene.control.TextField agencyIdField;
	
	@FXML
	private javafx.scene.control.TextField agencyNameField;
	
	@FXML
	private javafx.scene.control.TextField agencyTimeZoneField;
	
	@FXML
	private javafx.scene.control.TextField agencyLangageField;
	
	@FXML
	private javafx.scene.control.TextField agencyURLField;
	
	@FXML
	private javafx.scene.control.TextField agencyPhoneField;
	
	@FXML
	private void confirmAjoutAgency() {
		String agencyId = agencyIdField.getText();
		String agencyName = agencyNameField.getText();
		String agencyTimeZone = agencyTimeZoneField.getText();
		String agencyLangage = agencyLangageField.getText();
		String agencyURL = agencyURLField.getText();
		String agencyPhone = agencyPhoneField.getText();
		Agency agency = new Agency(agencyId,agencyName,agencyTimeZone,agencyURL);
		Client.addAgency(agency);
	}
	
	@FXML
	private void confirmModifAgency() {
		String agencyId = agencyIdField.getText();
		String agencyName = agencyNameField.getText();
		String agencyTimeZone = agencyTimeZoneField.getText();
		String agencyLangage = agencyLangageField.getText();
		String agencyURL = agencyURLField.getText();
		String agencyPhone = agencyPhoneField.getText();
		Agency agency = new Agency(agencyId,agencyName,agencyTimeZone,agencyURL);
		Client.editAgency(agency);
	}
	
	@FXML
	private void confirmSupprAgency() {
		String agencyId = agencyIdField.getText();
		Client.removeAgency(agencyId);
	}
	
	//gestion User
	@FXML
	private javafx.scene.control.TextField usersIdField;
	
	@FXML
	private javafx.scene.control.TextField usersUsernameField;
	
	@FXML
	private javafx.scene.control.TextField usersPasswordField;
	
	@FXML
	private javafx.scene.control.TextField usersDateCreationField;
	
	@FXML
	private void confirmAjoutUsers() {
		String usersId = usersIdField.getText();
		String usersUsername = usersUsernameField.getText();
		String usersPassword = usersPasswordField.getText();
		String usersDateCreation = usersDateCreationField.getText();
	}
	
	@FXML
	private void confirmModifUsers() {
		String usersId = usersIdField.getText();
		String usersUsername = usersUsernameField.getText();
		String usersPassword = usersPasswordField.getText();
		String usersDateCreation = usersDateCreationField.getText();
	}
	
	@FXML
	private void confirmSupprUsers() {
		String usersId = usersIdField.getText();
	}
	
	//gestion Routes
	@FXML
	private javafx.scene.control.TextField routesIdField;
	
	@FXML
	private javafx.scene.control.TextField routesShortNameField;
	
	@FXML
	private javafx.scene.control.TextField routesLongNameField;
	
	@FXML
	private javafx.scene.control.TextField routesDescriptionField;
	
	@FXML
	private javafx.scene.control.TextField routesTypeField;
	
	@FXML
	private void confirmAjoutRoutes() {
		String agencyId = agencyIdField.getText();
		String routesId = routesIdField.getText();
		String routesShortName = routesShortNameField.getText();
		String routesLongName = routesLongNameField.getText();
		String routesDescription = routesDescriptionField.getText();
		String routesType = routesTypeField.getText();
	}
	
	@FXML
	private void confirmModifRoutes() {
		String agencyId = agencyIdField.getText();
		String routesId = routesIdField.getText();
		String routesShortName = routesShortNameField.getText();
		String routesLongName = routesLongNameField.getText();
		String routesDescription = routesDescriptionField.getText();
		String routesType = routesTypeField.getText();
	}
	
	@FXML
	private void confirmSupprRoutes() {
		String routesId = routesIdField.getText();
	}
	
	//gestion stops
	
	@FXML
	private javafx.scene.control.TextField stopIdField;
	
	@FXML
	private javafx.scene.control.TextField stopCodeField;
	
	@FXML
	private javafx.scene.control.TextField stopNameField;
	
	@FXML
	private javafx.scene.control.TextField stopDescriptionField;
	
	@FXML
	private javafx.scene.control.TextField stopLongitudeField;
	
	@FXML
	private javafx.scene.control.TextField stopLatitudeField;
	
	@FXML
	private javafx.scene.control.TextField locationTypeField;
	
	@FXML
	private javafx.scene.control.TextField parentStationField;
	
	@FXML
	private void confirmAjoutStops() {
		String stopId = stopIdField.getText();
		String stopCode = stopCodeField.getText();
		String stopName = stopNameField.getText();
		String stopDescription = stopDescriptionField.getText();
		String stopLongitude = stopLongitudeField.getText();
		String stopLatitude= stopLatitudeField.getText();
		String locationType = locationTypeField.getText();
		String parentStation = parentStationField.getText();
	}
	
	@FXML
	private void confirmModifStops() {
		String stopId = stopIdField.getText();
		String stopCode = stopCodeField.getText();
		String stopName = stopNameField.getText();
		String stopDescription = stopDescriptionField.getText();
		String stopLongitude = stopLongitudeField.getText();
		String stopLatitude= stopLatitudeField.getText();
		String locationType = locationTypeField.getText();
		String parentStation = parentStationField.getText();
	}
	
	@FXML
	private void confirmSupprStops() {
		String stopId = stopIdField.getText();
		String stopCode = stopCodeField.getText();
		String stopName = stopNameField.getText();
		String stopDescription = stopDescriptionField.getText();
		String stopLongitude = stopLongitudeField.getText();
		String stopLatitude= stopLatitudeField.getText();
		String locationType = locationTypeField.getText();
		String parentStation = parentStationField.getText();
	}
	
	//gestion StopTimes
	
	@FXML
	private javafx.scene.control.TextField tripIdField;
	
	@FXML
	private javafx.scene.control.TextField arrivalTimeField;
	
	@FXML
	private javafx.scene.control.TextField departureTimeField;
	
	@FXML
	private javafx.scene.control.TextField stopSequenceField;
	
	@FXML
	private javafx.scene.control.TextField stopHeadsignField;
	
	@FXML
	private javafx.scene.control.TextField shapeDistanceTravelledField;
	
	
	@FXML
	private void confirmAjoutStopTimes() {
		String stopId = stopIdField.getText();
		String tripId = tripIdField.getText();
		String arrivalTime = arrivalTimeField.getText();
		String departureTime = departureTimeField.getText();
		String stopSequence = stopSequenceField.getText();
		String stopHeadsign= stopHeadsignField.getText();
		String shapeDistanceTravelled = shapeDistanceTravelledField.getText();
	}
	
	@FXML
	private void confirmModifStopTimes() {
		String stopId = stopIdField.getText();
		String tripId = tripIdField.getText();
		String arrivalTime = arrivalTimeField.getText();
		String departureTime = departureTimeField.getText();
		String stopSequence = stopSequenceField.getText();
		String stopHeadsign= stopHeadsignField.getText();
		String shapeDistanceTravelled = shapeDistanceTravelledField.getText();
	}
	
	@FXML
	private void confirmSupprStopTimes() {
		String stopId = stopIdField.getText();
		String tripId = tripIdField.getText();
		String arrivalTime = arrivalTimeField.getText();
		String departureTime = departureTimeField.getText();
		String stopSequence = stopSequenceField.getText();
		String stopHeadsign= stopHeadsignField.getText();
		String shapeDistanceTravelled = shapeDistanceTravelledField.getText();
	}
	
	//gestion Transfers
	@FXML
	private javafx.scene.control.TextField fromStopIdField;
	
	@FXML
	private javafx.scene.control.TextField toStopIdField;
	
	@FXML
	private javafx.scene.control.TextField transferTypeField;
	
	@FXML
	private javafx.scene.control.TextField minTransferTimeField;
	
	
	@FXML
	private void confirmAjoutTransfers() {
		String fromStopId = fromStopIdField.getText();
		String toStopId = toStopIdField.getText();
		String transferType = transferTypeField.getText();
		String minTransferTime = minTransferTimeField.getText();
	}
	
	@FXML
	private void confirmModifTransfers() {
		String fromStopId = fromStopIdField.getText();
		String toStopId = toStopIdField.getText();
		String transferType = transferTypeField.getText();
		String minTransferTime = minTransferTimeField.getText();
	}
	
	@FXML
	private void confirmSupprTransfers() {
		String fromStopId = fromStopIdField.getText();
		String toStopId = toStopIdField.getText();
		String transferType = transferTypeField.getText();
		String minTransferTime = minTransferTimeField.getText();
	}
	
	//gestion Trips
	@FXML
	private javafx.scene.control.TextField serviceIdField;
	
	@FXML
	private javafx.scene.control.TextField directionIdField;
	
	@FXML
	private javafx.scene.control.TextField shapeIdField;
	
	@FXML
	private javafx.scene.control.TextField tripShortNameField;
	
	@FXML
	private javafx.scene.control.TextField tripHeadSignField;
	
	
	@FXML
	private void confirmAjoutTrips() {
		String fromStopId = tripIdField.getText();
		String toStopId = serviceIdField.getText();
		String transferType = routesIdField.getText();
		String minTransferTime = directionIdField.getText();
		String shapeId = shapeIdField.getText();
		String tripHeadSign = tripHeadSignField.getText();
		String tripShortName = tripShortNameField.getText();
	}
	
	@FXML
	private void confirmModifTrips() {
		String fromStopId = tripIdField.getText();
		String toStopId = serviceIdField.getText();
		String transferType = routesIdField.getText();
		String minTransferTime = directionIdField.getText();
		String shapeId = shapeIdField.getText();
		String tripHeadSign = tripHeadSignField.getText();
		String tripShortName = tripShortNameField.getText();
	}
	
	@FXML
	private void confirmSupprTrips() {
		String fromStopId = tripIdField.getText();
		String toStopId = serviceIdField.getText();
		String transferType = routesIdField.getText();
		String minTransferTime = directionIdField.getText();
		String shapeId = shapeIdField.getText();
		String tripHeadSign = tripHeadSignField.getText();
		String tripShortName = tripShortNameField.getText();
	}
	
	/*
	 * @FXML private void initialize() {
	 * OptionBox.setValue("Choisissez une option"); OptionBox.setItems(OptionList);
	 * }
	 */
}
