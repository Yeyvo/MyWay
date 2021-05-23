package ma.myway.client.ui.viewFxml;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Set;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import ma.myway.client.network.Client;
import ma.myway.client.ui.Main;
import ma.myway.graph.data.Agency;
import ma.myway.graph.data.CalendarExpComp;
import ma.myway.graph.data.ServiceComp;
import ma.myway.graph.data.Stop;
import ma.myway.graph.data.Stop_Trip;
import ma.myway.graph.data.Transfert;
import ma.myway.graph.data.TripsComp;
import ma.myway.users.User;

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

	// gestion agency
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
	private javafx.scene.control.TableView agencyAllTable;

	@FXML
	private javafx.scene.control.TableColumn agencyIdColumn;

	@FXML
	private javafx.scene.control.TableColumn agencyNameColumn;

	@FXML
	private javafx.scene.control.TableColumn agencyURLColumn;

	@FXML
	private javafx.scene.control.TableColumn agencyTimeZoneColumn;

	@FXML
	private void confirmAjoutAgency() {
		String agencyId = agencyIdField.getText();
		String agencyName = agencyNameField.getText();
		String agencyTimeZone = agencyTimeZoneField.getText();
//		String agencyLangage = agencyLangageField.getText();
		String agencyURL = agencyURLField.getText();
//		String agencyPhone = agencyPhoneField.getText();
		Agency agency = new Agency(agencyId, agencyName, agencyTimeZone, agencyURL);
		if (!Client.addAgency(agency)) {
			Alert alert = new Alert(AlertType.WARNING, "Modification impossible");
			alert.show();
		}
	}

	@FXML
	private void confirmModifAgency() {
		String agencyId = agencyIdField.getText();
		String agencyName = agencyNameField.getText();
		String agencyTimeZone = agencyTimeZoneField.getText();
//		String agencyLangage = agencyLangageField.getText();
		String agencyURL = agencyURLField.getText();
//		String agencyPhone = agencyPhoneField.getText();
		Agency agency = new Agency(agencyId, agencyName, agencyTimeZone, agencyURL);
		if (!Client.editAgency(agency)) {
			Alert alert = new Alert(AlertType.WARNING, "Modification impossible");
			alert.show();
		}
	}

	@FXML
	private void confirmSupprAgency() {
		String agencyId = agencyIdField.getText();
		if (!Client.removeAgency(new Agency(agencyId, "", "", ""))) {
			Alert alert = new Alert(AlertType.WARNING, "Modification impossible");
			alert.show();
		}
	}

	@FXML
	private void confirmShowAgency() {
		Set<Agency> data = Client.showAgency();
		agencyIdColumn.setCellValueFactory(new PropertyValueFactory("agency_id"));
		agencyNameColumn.setCellValueFactory(new PropertyValueFactory("agency_name"));
		agencyURLColumn.setCellValueFactory(new PropertyValueFactory("agency_url"));
		agencyTimeZoneColumn.setCellValueFactory(new PropertyValueFactory("agency_timezone"));
		agencyAllTable.getItems().addAll(data);
	}

	// gestion User
	@FXML
	private javafx.scene.control.TextField usersIdField;

	@FXML
	private javafx.scene.control.TextField usersUsernameField;

	@FXML
	private javafx.scene.control.TextField usersPasswordField;
	
	@FXML
	private javafx.scene.control.TextField usersPermissionField;

	@FXML
	private javafx.scene.control.DatePicker usersDateCreationField;
	
	@FXML
	private javafx.scene.control.TableView usersAllTable;

	@FXML
	private javafx.scene.control.TableColumn usersIdColumn;

	@FXML
	private javafx.scene.control.TableColumn usersUserNameColumn;

	@FXML
	private javafx.scene.control.TableColumn usersPasswordColumn;

	@FXML
	private javafx.scene.control.TableColumn usersPermissionColumn;
	
	@FXML
	private javafx.scene.control.TableColumn usersDateCreationColumn;

	@FXML
	private void confirmAjoutUsers() {
		String usersUsername = usersUsernameField.getText();
		String usersPassword = usersPasswordField.getText();
		User user = new User(usersUsername, usersPassword);
		
		if (!Client.addUser(user)) {
			Alert alert = new Alert(AlertType.WARNING, "Modification impossible");
			alert.show();
		}
	}

	@FXML
	private void confirmModifUsers() {
		String usersIdString = usersIdField.getText();
		String usersUsername = usersUsernameField.getText();
		String usersPassword = usersPasswordField.getText();
		LocalDate usersDateCreationLocal = usersDateCreationField.getValue();
		String usersPermission = usersPermissionField.getText();
		int usersId = Integer.parseInt(usersIdString);
		Instant instant = Instant.from(usersDateCreationLocal.atStartOfDay(ZoneId.systemDefault()));
		Date usersDateCreation = Date.from(instant);
		User user = new User(usersId,usersUsername, usersPassword, usersDateCreation, usersPermission);
		
		if (!Client.editUser(user)) {
			Alert alert = new Alert(AlertType.WARNING, "Modification impossible");
			alert.show();
		}
	}

	@FXML
	private void confirmSupprUsers() {
		String usersId = usersIdField.getText();
		
		if (!Client.removeUser(usersId)) {
			Alert alert = new Alert(AlertType.WARNING, "Modification impossible");
			alert.show();
		}
	}
	
	@FXML
	private void confirmShowUser() {
		Set<User> data = Client.showUser();
		usersIdColumn.setCellValueFactory(new PropertyValueFactory("id"));
		usersUserNameColumn.setCellValueFactory(new PropertyValueFactory("name"));
		usersPasswordColumn.setCellValueFactory(new PropertyValueFactory("password"));
		usersPermissionColumn.setCellValueFactory(new PropertyValueFactory("perm"));
		usersDateCreationColumn.setCellValueFactory(new PropertyValueFactory("creation"));
		usersAllTable.getItems().addAll(data);
	}

	// gestion Routes
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

	// gestion stops

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
	private javafx.scene.control.TableView stopsAllTable;

	@FXML
	private javafx.scene.control.TableColumn stopsIdColumn;

	@FXML
	private javafx.scene.control.TableColumn stopsCodeColumn;

	@FXML
	private javafx.scene.control.TableColumn stopsNameColumn;

	@FXML
	private javafx.scene.control.TableColumn stopsDescriptionColumn;
	
	@FXML
	private javafx.scene.control.TableColumn stopsLatitudeColumn;
	
	@FXML
	private javafx.scene.control.TableColumn stopsLongitudeColumn;
	
	@FXML
	private javafx.scene.control.TableColumn stopsLocationTypeColumn;
	
	@FXML
	private javafx.scene.control.TableColumn stopsParentStationColumn;

	@FXML
	private void confirmAjoutStops() {
		String stopId = stopIdField.getText();
		String stopCode = stopCodeField.getText();
		String stopName = stopNameField.getText();
		String stopDescription = stopDescriptionField.getText();
		String stopLongitudeString = stopLongitudeField.getText();
		String stopLatitudeString = stopLatitudeField.getText();
		String locationTypeString = locationTypeField.getText();
		String parentStation = parentStationField.getText();

		double stopLongitude = Double.parseDouble(stopLongitudeString);
		double stopLatitude = Double.parseDouble(stopLatitudeString);
		int locationType = Integer.parseInt(locationTypeString);

		Stop stop = new Stop(stopId, stopName, stopDescription, stopLatitude, stopLongitude, locationType);
		if (!Client.addStops(stop)) {
			Alert alert = new Alert(AlertType.WARNING, "Modification impossible");
			alert.show();
		}
	}

	@FXML
	private void confirmModifStops() {
		String stopId = stopIdField.getText();
		String stopCode = stopCodeField.getText();
		String stopName = stopNameField.getText();
		String stopDescription = stopDescriptionField.getText();
		String stopLongitudeString = stopLongitudeField.getText();
		String stopLatitudeString = stopLatitudeField.getText();
		String locationTypeString = locationTypeField.getText();
		String parentStation = parentStationField.getText();

		double stopLongitude = Double.parseDouble(stopLongitudeString);
		double stopLatitude = Double.parseDouble(stopLatitudeString);
		int locationType = Integer.parseInt(locationTypeString);

		Stop stop = new Stop(stopId, stopName, stopDescription, stopLatitude, stopLongitude, locationType);
		if (!Client.editStops(stop)) {
			Alert alert = new Alert(AlertType.WARNING, "Modification impossible");
			alert.show();
		}
	}

	@FXML
	private void confirmSupprStops() {
		String stopId = stopIdField.getText();
		String stopCode = stopCodeField.getText();
		String stopName = stopNameField.getText();
		String stopDescription = stopDescriptionField.getText();
		String stopLongitudeString = stopLongitudeField.getText();
		String stopLatitudeString = stopLatitudeField.getText();
		String locationTypeString = locationTypeField.getText();
		String parentStation = parentStationField.getText();

		double stopLongitude = Double.parseDouble(stopLongitudeString);
		double stopLatitude = Double.parseDouble(stopLatitudeString);
		int locationType = Integer.parseInt(locationTypeString);

		Stop stop = new Stop(stopId, stopName, stopDescription, stopLatitude, stopLongitude, locationType);
		if (!Client.removeStops(stop)) {
			Alert alert = new Alert(AlertType.WARNING, "Modification impossible");
			alert.show();
		}
	}
	
	@FXML
	private void confirmShowStops() {
		Set<Stop> data = Client.showStops();
		stopsIdColumn.setCellValueFactory(new PropertyValueFactory("stop_id"));
//		stopsCodeColumn.setCellValueFactory(new PropertyValueFactory("agency_name"));
		stopsNameColumn.setCellValueFactory(new PropertyValueFactory("name"));
		stopsDescriptionColumn.setCellValueFactory(new PropertyValueFactory("desc"));
		stopsLatitudeColumn.setCellValueFactory(new PropertyValueFactory("lat"));
		stopsLongitudeColumn.setCellValueFactory(new PropertyValueFactory("lon"));
		stopsLocationTypeColumn.setCellValueFactory(new PropertyValueFactory("location_type"));
//		stopsParentStationColumn.setCellValueFactory(new PropertyValueFactory("agency_timezone"));
		stopsAllTable.getItems().addAll(data);
	}

	// gestion StopTimes

	@FXML
	private javafx.scene.control.TextField tripIdField;

	@FXML
	private javafx.scene.control.DatePicker arrivalTimeField;

	@FXML
	private javafx.scene.control.DatePicker departureTimeField;

	@FXML
	private javafx.scene.control.TextField stopSequenceField;

	@FXML
	private javafx.scene.control.TextField stopHeadsignField;

	@FXML
	private javafx.scene.control.TextField shapeDistanceTravelledField;
	
	@FXML
	private javafx.scene.control.TableView stopTimesAllTable;

	@FXML
	private javafx.scene.control.TableColumn stopTimesTripIdColumn;

	@FXML
	private javafx.scene.control.TableColumn stopTimesDepartureTimeColumn;

	@FXML
	private javafx.scene.control.TableColumn stopTimesArrivalTimeColumn;

	@FXML
	private javafx.scene.control.TableColumn stopTimesStopIdColumn;

	@FXML
	private javafx.scene.control.TableColumn stopTimesSequenceColumn;

	@FXML
	private void confirmAjoutStopTimes() {
		String stopId = stopIdField.getText();
		String tripId = tripIdField.getText();
		LocalDate arrivalTimeLocal = arrivalTimeField.getValue();
		LocalDate departureTimeLocal = departureTimeField.getValue();
		String stopSequenceString = stopSequenceField.getText();
		String stopHeadsign = stopHeadsignField.getText();
		String shapeDistanceTravelled = shapeDistanceTravelledField.getText();
		Instant instant = Instant.from(arrivalTimeLocal.atStartOfDay(ZoneId.systemDefault()));
		Date arrivalTime = Date.from(instant);
		instant = Instant.from(departureTimeLocal.atStartOfDay(ZoneId.systemDefault()));
		Date departureTime = Date.from(instant);
		int stopSequence = Integer.parseInt(stopSequenceString);
		Stop_Trip stpt = new Stop_Trip(tripId, stopId, arrivalTime, departureTime, stopSequence);
		if (!Client.addStopTimes(stpt)) {
			Alert alert = new Alert(AlertType.WARNING, "Modification impossible");
			alert.show();
		}
	}

	@FXML
	private void confirmModifStopTimes() {
		String stopId = stopIdField.getText();
		String tripId = tripIdField.getText();
		LocalDate arrivalTimeLocal = arrivalTimeField.getValue();
		LocalDate departureTimeLocal = departureTimeField.getValue();
		String stopSequenceString = stopSequenceField.getText();
		String stopHeadsign = stopHeadsignField.getText();
		String shapeDistanceTravelled = shapeDistanceTravelledField.getText();
		Instant instant = Instant.from(arrivalTimeLocal.atStartOfDay(ZoneId.systemDefault()));
		Date date = Date.from(instant);
		Date arrivalTime = date;
		instant = Instant.from(departureTimeLocal.atStartOfDay(ZoneId.systemDefault()));
		date = Date.from(instant);
		Date departureTime = date;
		int stopSequence = Integer.parseInt(stopSequenceString);
		Stop_Trip stpt = new Stop_Trip(tripId, stopId, arrivalTime, departureTime, stopSequence);
		if (!Client.editStopTimes(stpt)) {
			Alert alert = new Alert(AlertType.WARNING, "Modification impossible");
			alert.show();
		}
	}

	@FXML
	private void confirmSupprStopTimes() {
		String stopId = stopIdField.getText();
		String tripId = tripIdField.getText();
		LocalDate arrivalTimeLocal = arrivalTimeField.getValue();
		LocalDate departureTimeLocal = departureTimeField.getValue();
		String stopSequenceString = stopSequenceField.getText();
		String stopHeadsign = stopHeadsignField.getText();
		String shapeDistanceTravelled = shapeDistanceTravelledField.getText();
		Instant instant = Instant.from(arrivalTimeLocal.atStartOfDay(ZoneId.systemDefault()));
		Date date = Date.from(instant);
		Date arrivalTime = date;
		instant = Instant.from(departureTimeLocal.atStartOfDay(ZoneId.systemDefault()));
		date = Date.from(instant);
		Date departureTime = date;
		int stopSequence = Integer.parseInt(stopSequenceString);
		Stop_Trip stpt = new Stop_Trip(tripId, stopId, arrivalTime, departureTime, stopSequence);
		if (!Client.removeStopTimes(stpt)) {
			Alert alert = new Alert(AlertType.WARNING, "Modification impossible");
			alert.show();
		}
	}

	@FXML
	private void confirmShowStopTimes() {
		Set<Stop_Trip> data = Client.showStopTimes();
		stopTimesTripIdColumn.setCellValueFactory(new PropertyValueFactory("trip_id"));
		stopTimesDepartureTimeColumn.setCellValueFactory(new PropertyValueFactory("departure_time"));
		stopTimesArrivalTimeColumn.setCellValueFactory(new PropertyValueFactory("arrival_time"));
		stopTimesStopIdColumn.setCellValueFactory(new PropertyValueFactory("stop_id"));
		stopTimesSequenceColumn.setCellValueFactory(new PropertyValueFactory("stop_sequence"));
		stopTimesAllTable.getItems().addAll(data);
	}

	// gestion Transfers
	@FXML
	private javafx.scene.control.TextField fromStopIdField;

	@FXML
	private javafx.scene.control.TextField toStopIdField;

	@FXML
	private javafx.scene.control.ChoiceBox transferTypeField;

	@FXML
	private javafx.scene.control.TextField minTransferTimeField;
	
	@FXML
	private javafx.scene.control.TableView transfersAllTable;

	@FXML
	private javafx.scene.control.TableColumn transfersFromStopIdColumn;

	@FXML
	private javafx.scene.control.TableColumn transfersToStopIdColumn;

	@FXML
	private javafx.scene.control.TableColumn transfersTypeColumn;

	@FXML
	private javafx.scene.control.TableColumn transfersTimeColumn;


	@FXML
	private void confirmAjoutTransfers() {
		String fromStopId = fromStopIdField.getText();
		String toStopId = toStopIdField.getText();
		int transferType = (int) transferTypeField.getValue();
		String minTransferTimeString = minTransferTimeField.getText();
		int minTransferTime = Integer.parseInt(minTransferTimeString);
		Transfert trans = new Transfert(fromStopId, toStopId, transferType, minTransferTime);
		if (!Client.addTransfers(trans)) {
			Alert alert = new Alert(AlertType.WARNING, "Modification impossible");
			alert.show();
		}
	}

	@FXML
	private void confirmModifTransfers() {
		String fromStopId = fromStopIdField.getText();
		String toStopId = toStopIdField.getText();
		int transferType = (int) transferTypeField.getValue();
		String minTransferTimeString = minTransferTimeField.getText();
		int minTransferTime = Integer.parseInt(minTransferTimeString);
		Transfert trans = new Transfert(fromStopId, toStopId, transferType, minTransferTime);
		if (!Client.editTransfers(trans)) {
			Alert alert = new Alert(AlertType.WARNING, "Modification impossible");
			alert.show();
		}
	}

	@FXML
	private void confirmSupprTransfers() {
		String fromStopId = fromStopIdField.getText();
		String toStopId = toStopIdField.getText();
		int transferType = (int) transferTypeField.getValue();
		String minTransferTimeString = minTransferTimeField.getText();
		int minTransferTime = Integer.parseInt(minTransferTimeString);
		Transfert trans = new Transfert(fromStopId, toStopId, transferType, minTransferTime);
		if (!Client.removeTransfers(trans)) {
			Alert alert = new Alert(AlertType.WARNING, "Modification impossible");
			alert.show();
		}
	}
	
	@FXML
	private void confirmShowTransfers() {
		Set<Transfert> data = Client.showTransfers();
		transfersFromStopIdColumn.setCellValueFactory(new PropertyValueFactory("src_stop_id"));
		transfersToStopIdColumn.setCellValueFactory(new PropertyValueFactory("dest_stop_id"));
		transfersTypeColumn.setCellValueFactory(new PropertyValueFactory("transfert_type"));
		transfersTimeColumn.setCellValueFactory(new PropertyValueFactory("transfert_time"));
		transfersAllTable.getItems().addAll(data);
	}

	// gestion Trips
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
	private javafx.scene.control.TableView tripsAllTable;

	@FXML
	private javafx.scene.control.TableColumn tripsRouteIdColumn;

	@FXML
	private javafx.scene.control.TableColumn tripsServiceIdColumn;

	@FXML
	private javafx.scene.control.TableColumn tripsTripIdColumn;

	@FXML
	private javafx.scene.control.TableColumn tripsHeadSignColumn;
	
	@FXML
	private javafx.scene.control.TableColumn tripsShortNameColumn;
	
	@FXML
	private javafx.scene.control.TableColumn tripsDirectionIdColumn;
	
	@FXML
	private javafx.scene.control.TableColumn tripsShapeIdColumn;
	

	@FXML
	private void confirmAjoutTrips() {
		String tripId = tripIdField.getText();
		String serviceId = serviceIdField.getText();
		String routesId = routesIdField.getText();
		String directionId = directionIdField.getText();
		String shapeId = shapeIdField.getText();
		String tripHeadSign = tripHeadSignField.getText();
		String tripShortName = tripShortNameField.getText();
		
		TripsComp trip = new TripsComp(routesId,serviceId,tripId,tripHeadSign,tripShortName,directionId,shapeId);
		if (!Client.addTrips(trip)) {
			Alert alert = new Alert(AlertType.WARNING, "Modification impossible");
			alert.show();
		}
	}

	@FXML
	private void confirmModifTrips() {
		String tripId = tripIdField.getText();
		String serviceId = serviceIdField.getText();
		String routesId = routesIdField.getText();
		String directionId = directionIdField.getText();
		String shapeId = shapeIdField.getText();
		String tripHeadSign = tripHeadSignField.getText();
		String tripShortName = tripShortNameField.getText();
		
		TripsComp trip = new TripsComp(routesId,serviceId,tripId,tripHeadSign,tripShortName,directionId,shapeId);
		if (!Client.editTrips(trip)) {
			Alert alert = new Alert(AlertType.WARNING, "Modification impossible");
			alert.show();
		}
	}

	@FXML
	private void confirmSupprTrips() {
		String tripId = tripIdField.getText();
		String serviceId = serviceIdField.getText();
		String routesId = routesIdField.getText();
		String directionId = directionIdField.getText();
		String shapeId = shapeIdField.getText();
		String tripHeadSign = tripHeadSignField.getText();
		String tripShortName = tripShortNameField.getText();
		
		TripsComp trip = new TripsComp(routesId,serviceId,tripId,tripHeadSign,tripShortName,directionId,shapeId);
		if (!Client.removeTrips(trip)) {
			Alert alert = new Alert(AlertType.WARNING, "Modification impossible");
			alert.show();
		}
	}
	
	@FXML
	private void confirmShowTrips() {
		Set<TripsComp> data = Client.showTrips();
		tripsRouteIdColumn.setCellValueFactory(new PropertyValueFactory("route_id"));
		tripsServiceIdColumn.setCellValueFactory(new PropertyValueFactory("service_id"));
		tripsTripIdColumn.setCellValueFactory(new PropertyValueFactory("trip_id"));
		tripsHeadSignColumn.setCellValueFactory(new PropertyValueFactory("trip_headsign"));
		tripsShortNameColumn.setCellValueFactory(new PropertyValueFactory("trip_short_name"));
		tripsDirectionIdColumn.setCellValueFactory(new PropertyValueFactory("direction_id"));
		tripsShapeIdColumn.setCellValueFactory(new PropertyValueFactory("shape_id"));
		tripsAllTable.getItems().addAll(data);
	}

	// gestion Calendar

	@FXML
	private javafx.scene.control.DatePicker startDateField;

	@FXML
	private javafx.scene.control.DatePicker endDateField;

	@FXML
	private javafx.scene.control.CheckBox mondayBox;

	@FXML
	private javafx.scene.control.CheckBox tuesdayBox;

	@FXML
	private javafx.scene.control.CheckBox wednesdayBox;

	@FXML
	private javafx.scene.control.CheckBox thursdayBox;

	@FXML
	private javafx.scene.control.CheckBox fridayBox;

	@FXML
	private javafx.scene.control.CheckBox saturdayBox;

	@FXML
	private javafx.scene.control.CheckBox sundayBox;
	
	@FXML
	private javafx.scene.control.TableColumn calendarServiceIdColumn;

	@FXML
	private javafx.scene.control.TableColumn calendarMondayColumn;

	@FXML
	private javafx.scene.control.TableColumn calendarTuesdayColumn;

	@FXML
	private javafx.scene.control.TableColumn calendarWednesdayColumn;

	@FXML
	private javafx.scene.control.TableColumn calendarThursdayColumn;
	
	@FXML
	private javafx.scene.control.TableColumn calendarFridayColumn;
	
	@FXML
	private javafx.scene.control.TableColumn calendarSaturdayColumn;
	
	@FXML
	private javafx.scene.control.TableColumn calendarSundayColumn;
	
	@FXML
	private javafx.scene.control.TableColumn calendarStartDateColumn;
	
	@FXML
	private javafx.scene.control.TableColumn calendarEndDateColumn;
	
	@FXML
	private javafx.scene.control.TableView calendarAllTable;


	@FXML
	private void confirmAjoutCalendar() {
		String serviceId = serviceIdField.getText();
		LocalDate startDateLocal = startDateField.getValue();
		LocalDate endDateLocal = endDateField.getValue();
		Instant instant = Instant.from(startDateLocal.atStartOfDay(ZoneId.systemDefault()));
		Date startDate = Date.from(instant);
		instant = Instant.from(startDateLocal.atStartOfDay(ZoneId.systemDefault()));
		Date endDate = Date.from(instant);
		int monday, tuesday, wednesday, thursday, friday, saturday, sunday;
		if (mondayBox.isSelected())
			monday = 1;
		else
			monday = 0;

		if (tuesdayBox.isSelected())
			tuesday = 1;
		else
			tuesday = 0;

		if (wednesdayBox.isSelected())
			wednesday = 1;
		else
			wednesday = 0;

		if (thursdayBox.isSelected())
			thursday = 1;
		else
			thursday = 0;

		if (fridayBox.isSelected())
			friday = 1;
		else
			friday = 0;

		if (saturdayBox.isSelected())
			saturday = 1;
		else
			saturday = 0;

		if (sundayBox.isSelected())
			sunday = 1;
		else
			sunday = 0;
		
		ServiceComp cald = new ServiceComp(serviceId,monday,tuesday,wednesday,thursday,friday,saturday,sunday,startDate,endDate);
		if (!Client.addCalendar(cald)) {
			Alert alert = new Alert(AlertType.WARNING, "Modification impossible");
			alert.show();
		}

	}

	@FXML
	private void confirmModifCalendar() {
		String serviceId = serviceIdField.getText();
		LocalDate startDateLocal = startDateField.getValue();
		LocalDate endDateLocal = endDateField.getValue();
		Instant instant = Instant.from(startDateLocal.atStartOfDay(ZoneId.systemDefault()));
		Date startDate = Date.from(instant);
		instant = Instant.from(startDateLocal.atStartOfDay(ZoneId.systemDefault()));
		Date endDate = Date.from(instant);
		int monday, tuesday, wednesday, thursday, friday, saturday, sunday;
		if (mondayBox.isSelected())
			monday = 1;
		else
			monday = 0;

		if (tuesdayBox.isSelected())
			tuesday = 1;
		else
			tuesday = 0;

		if (wednesdayBox.isSelected())
			wednesday = 1;
		else
			wednesday = 0;

		if (thursdayBox.isSelected())
			thursday = 1;
		else
			thursday = 0;

		if (fridayBox.isSelected())
			friday = 1;
		else
			friday = 0;

		if (saturdayBox.isSelected())
			saturday = 1;
		else
			saturday = 0;

		if (sundayBox.isSelected())
			sunday = 1;
		else
			sunday = 0;
		
		ServiceComp cald = new ServiceComp(serviceId,monday,tuesday,wednesday,thursday,friday,saturday,sunday,startDate,endDate);
		if (!Client.editCalendar(cald)) {
			Alert alert = new Alert(AlertType.WARNING, "Modification impossible");
			alert.show();
		}
	}

	@FXML
	private void confirmSupprCalendar() {
		String serviceId = serviceIdField.getText();
		if (!Client.removeCalendar(serviceId)) {
			Alert alert = new Alert(AlertType.WARNING, "Modification impossible");
			alert.show();
		}
	}
	
	@FXML
	private void confirmShowCalendar() {
		Set<ServiceComp> data = Client.showCalendar();
		calendarServiceIdColumn.setCellValueFactory(new PropertyValueFactory("service_id"));
		calendarMondayColumn.setCellValueFactory(new PropertyValueFactory("monday"));
		calendarTuesdayColumn.setCellValueFactory(new PropertyValueFactory("tuesday"));
		calendarWednesdayColumn.setCellValueFactory(new PropertyValueFactory("wednesday"));
		calendarThursdayColumn.setCellValueFactory(new PropertyValueFactory("thursday"));
		calendarFridayColumn.setCellValueFactory(new PropertyValueFactory("friday"));
		calendarSaturdayColumn.setCellValueFactory(new PropertyValueFactory("saturday"));
		calendarSundayColumn.setCellValueFactory(new PropertyValueFactory("sunday"));
		calendarStartDateColumn.setCellValueFactory(new PropertyValueFactory("start_date"));
		calendarEndDateColumn.setCellValueFactory(new PropertyValueFactory("end_date"));
		calendarAllTable.getItems().addAll(data);
	}

	// gestion CalendarDates

	@FXML
	private javafx.scene.control.DatePicker dateField;

	@FXML
	private javafx.scene.control.TextField exceptionTypeField;
	
	@FXML
	private javafx.scene.control.TableColumn calendarDatesDateColumn;
	
	@FXML
	private javafx.scene.control.TableColumn calendarDatesServiceIdColumn;
	
	@FXML
	private javafx.scene.control.TableColumn calendarDatesExceptionTypeColumn;
	
	@FXML
	private javafx.scene.control.TableView calendarDatesAllTable;

	
	
	
	@FXML
	private void confirmAjoutCalendarDates() {
		String serviceId = serviceIdField.getText();
		LocalDate dateLocal = dateField.getValue();
		String exceptionTypeString = exceptionTypeField.getText();
		int exceptionType = Integer.parseInt(exceptionTypeString);
		Instant instant = Instant.from(dateLocal.atStartOfDay(ZoneId.systemDefault()));
		Date date = Date.from(instant);
		
		CalendarExpComp caldates = new CalendarExpComp(serviceId,date,exceptionType);
		if (!Client.addCalendarDates(caldates)) {
			Alert alert = new Alert(AlertType.WARNING, "Modification impossible");
			alert.show();
		}
	}

	@FXML
	private void confirmModifCalendarDates() {
		String serviceId = serviceIdField.getText();
		LocalDate dateLocal = dateField.getValue();
		String exceptionTypeString = exceptionTypeField.getText();
		int exceptionType = Integer.parseInt(exceptionTypeString);
		Instant instant = Instant.from(dateLocal.atStartOfDay(ZoneId.systemDefault()));
		Date date = Date.from(instant);
		
		CalendarExpComp caldates = new CalendarExpComp(serviceId,date,exceptionType);
		if (!Client.editCalendarDates(caldates)) {
			Alert alert = new Alert(AlertType.WARNING, "Modification impossible");
			alert.show();
		}
	}

	@FXML
	private void confirmSupprCalendarDates() {
		String serviceId = serviceIdField.getText();
		LocalDate dateLocal = dateField.getValue();
		Instant instant = Instant.from(dateLocal.atStartOfDay(ZoneId.systemDefault()));
		Date date = Date.from(instant);
		
		if (!Client.removeCalendarDates(serviceId, date)) {
			Alert alert = new Alert(AlertType.WARNING, "Modification impossible");
			alert.show();
		}
	}
	
	@FXML
	private void confirmShowCalendarDates() {
		Set<CalendarExpComp> data = Client.showCalendarDates();
		calendarDatesDateColumn.setCellValueFactory(new PropertyValueFactory("service_id"));
		calendarDatesServiceIdColumn.setCellValueFactory(new PropertyValueFactory("added"));
		calendarDatesExceptionTypeColumn.setCellValueFactory(new PropertyValueFactory("type"));
		calendarDatesAllTable.getItems().addAll(data);
	}
	
	/*
	 * @FXML private void initialize() {
	 * OptionBox.setValue("Choisissez une option"); OptionBox.setItems(OptionList);
	 * }
	 */
}
