package ma.myway.client.ui.viewFxml;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;
import java.util.regex.Pattern;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import ma.myway.client.network.Client;
import ma.myway.client.network.Security;
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
//	static ObservableList<Integer> datatransferTypeField = FXCollections.observableArrayList();

	/*
	 * @FXML private ChoiceBox OptionBox;
	 */

	@FXML
	private void showAjout() throws IOException {
		main.showAjouterScene();
	}

	@FXML
	private void openBase() throws IOException {
		main.openBase();
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
	private Button btnGestionAdmin;

	@FXML
	private void showGestion() throws IOException {
		Stage stage = (Stage) btnGestionAdmin.getScene().getWindow();
		stage.close();
		main.showGestionScene();
	}

	@FXML
	private Button btnUseApp;

	@FXML
	private void showApp() throws IOException {
		Stage stage = (Stage) btnUseApp.getScene().getWindow();
		stage.close();
		main.showAppScene();
	}

	@FXML
	private Button btnQuitApp;

	@FXML
	private void quitApp() {
		System.exit(0);
		main.quitAppScene();
	}

	@FXML
	private Button btnReturnAdmin;

	@FXML
	private void returnMenuAdmin() {
		Stage stage = (Stage) btnReturnAdmin.getScene().getWindow();
		stage.close();
		main.showMenuAdmin();
	}

	// gestion agency
	@FXML
	private TextField agencyIdField;

	@FXML
	private TextField agencyNameField;

	@FXML
	private TextField agencyTimeZoneField;

	@FXML
	private TextField agencyLangageField;

	@FXML
	private TextField agencyURLField;

	@FXML
	private TextField agencyPhoneField;

	@FXML
	private TableView agencyAllTable;

	@FXML
	private TableColumn agencyIdColumn;

	@FXML
	private TableColumn agencyNameColumn;

	@FXML
	private TableColumn agencyURLColumn;

	@FXML
	private TableColumn agencyTimeZoneColumn;

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
	private TextField usersIdField;

	@FXML
	private TextField usersUsernameField;

	@FXML
	private TextField usersPasswordField;

	@FXML
	private ChoiceBox<String> usersPermissionField;

	@FXML
	private TableView usersAllTable;

	@FXML
	private TableColumn usersIdColumn;

	@FXML
	private TableColumn usersUserNameColumn;

	@FXML
	private TableColumn usersPasswordColumn;

	@FXML
	private TableColumn usersPermissionColumn;

	@FXML
	private TableColumn usersDateCreationColumn;

	@FXML
	private void initialize() {

		if (usersPermissionField != null) {
			usersPermissionField.getItems().addAll("admin", "user");
		}

	}

	@FXML
	private void confirmAjoutUsers() {
		String usersUsername = usersUsernameField.getText();
		String usersPassword = usersPasswordField.getText();
		String usersPermission = usersPermissionField.getValue();

		if (!usersUsername.isBlank() && !usersPassword.isBlank() && !usersPermission.isBlank()) {
			if (!Client.addUser(usersUsername, Security.get_SHA_256_SecurePassword(usersPassword), usersPermission)) {
				Alert alert = new Alert(AlertType.WARNING, "Modification impossible");
				alert.show();
			}
		} else {
			Alert alert = new Alert(AlertType.WARNING, "Veuillez remplir tous les champs");
			alert.show();
		}
	}

	@FXML
	private void confirmModifUsers() {
		String usersIdString = usersIdField.getText();
		String usersUsername = usersUsernameField.getText();
		String usersPassword = usersPasswordField.getText();
		String usersPermission = usersPermissionField.getValue();

		if (!usersUsername.isBlank() && !usersPassword.isBlank() && !usersPermission.isBlank()) {
			if (Pattern.matches("^[1-9]*$", usersIdString)) {
				int usersId = Integer.parseInt(usersIdString);
				User user = new User(usersId, usersUsername, Security.get_SHA_256_SecurePassword(usersPassword),
						new Date(), usersPermission);

				if (!Client.editUser(user)) {
					Alert alert = new Alert(AlertType.WARNING, "Modification impossible");
					alert.show();
				}
			} else {
				Alert alert = new Alert(AlertType.WARNING, "Veuillez verifier le type de userId");
				alert.show();
			}
		} else {
			Alert alert = new Alert(AlertType.WARNING, "Veuillez remplir tous les champs");
			alert.show();
		}
	}

	@FXML
	private void confirmSupprUsers() {
		String usersId = usersIdField.getText();
		if (!usersId.isBlank() && Pattern.matches("^[1-9]*$", usersId)) {
			if (!Client.removeUser(usersId)) {
				Alert alert = new Alert(AlertType.WARNING, "Modification impossible");
				alert.show();
			}
		} else {
			Alert alert = new Alert(AlertType.WARNING, "Veuillez remplir et verrifier le type de tous les champs");
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
	private TextField routesIdField;

	@FXML
	private TextField routesShortNameField;

	@FXML
	private TextField routesLongNameField;

	@FXML
	private TextField routesDescriptionField;

	@FXML
	private TextField routesTypeField;

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
	private TextField stopIdField;

	@FXML
	private TextField stopCodeField;

	@FXML
	private TextField stopNameField;

	@FXML
	private TextField stopDescriptionField;

	@FXML
	private TextField stopLongitudeField;

	@FXML
	private TextField stopLatitudeField;

	@FXML
	private TextField locationTypeField;

	@FXML
	private TextField parentStationField;

	@FXML
	private TableView stopsAllTable;

	@FXML
	private TableColumn stopsIdColumn;

	@FXML
	private TableColumn stopsCodeColumn;

	@FXML
	private TableColumn stopsNameColumn;

	@FXML
	private TableColumn stopsDescriptionColumn;

	@FXML
	private TableColumn stopsLatitudeColumn;

	@FXML
	private TableColumn stopsLongitudeColumn;

	@FXML
	private TableColumn stopsLocationTypeColumn;

	@FXML
	private TableColumn stopsParentStationColumn;

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
	private TextField tripIdField;

	@FXML
	private TextField arrivalTimeField;

	@FXML
	private TextField departureTimeField;

	@FXML
	private TextField stopSequenceField;

	@FXML
	private TextField stopHeadsignField;

	@FXML
	private TextField shapeDistanceTravelledField;

	@FXML
	private TableView stopTimesAllTable;

	@FXML
	private TableColumn stopTimesTripIdColumn;

	@FXML
	private TableColumn stopTimesDepartureTimeColumn;

	@FXML
	private TableColumn stopTimesArrivalTimeColumn;

	@FXML
	private TableColumn stopTimesStopIdColumn;

	@FXML
	private TableColumn stopTimesSequenceColumn;

	@FXML
	private void confirmAjoutStopTimes() {
		String stopId = stopIdField.getText();
		String tripId = tripIdField.getText();
		String arrivalTimeLocal = arrivalTimeField.getText();
		String departureTimeLocal = departureTimeField.getText();
		String stopSequenceString = stopSequenceField.getText();
		String stopHeadsign = stopHeadsignField.getText();
		String shapeDistanceTravelled = shapeDistanceTravelledField.getText();
		DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		int stopSequence = Integer.parseInt(stopSequenceString);
		Stop_Trip stpt = null;
		try {
			stpt = new Stop_Trip(tripId, stopId, formatter.parse(arrivalTimeLocal), formatter.parse(departureTimeLocal),
					stopSequence);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (!Client.addStopTimes(stpt)) {
			Alert alert = new Alert(AlertType.WARNING, "Modification impossible");
			alert.show();
		}
	}

	@FXML
	private void confirmModifStopTimes() {
		String stopId = stopIdField.getText();
		String tripId = tripIdField.getText();
		String arrivalTimeLocal = arrivalTimeField.getText();
		String departureTimeLocal = departureTimeField.getText();
		String stopSequenceString = stopSequenceField.getText();
		String stopHeadsign = stopHeadsignField.getText();
		String shapeDistanceTravelled = shapeDistanceTravelledField.getText();

		DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		int stopSequence = Integer.parseInt(stopSequenceString);
		Stop_Trip stpt = null;
		try {
			stpt = new Stop_Trip(tripId, stopId, formatter.parse(arrivalTimeLocal), formatter.parse(departureTimeLocal),
					stopSequence);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (!Client.editStopTimes(stpt)) {
			Alert alert = new Alert(AlertType.WARNING, "Modification impossible");
			alert.show();
		}
	}

	@FXML
	private void confirmSupprStopTimes() {
		String stopId = stopIdField.getText();
		String tripId = tripIdField.getText();
		String arrivalTimeLocal = arrivalTimeField.getText();
		String departureTimeLocal = departureTimeField.getText();
		String stopSequenceString = stopSequenceField.getText();
		String stopHeadsign = stopHeadsignField.getText();
		String shapeDistanceTravelled = shapeDistanceTravelledField.getText();

		DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		int stopSequence = Integer.parseInt(stopSequenceString);
		Stop_Trip stpt = null;
		try {
			stpt = new Stop_Trip(tripId, stopId, formatter.parse(arrivalTimeLocal), formatter.parse(departureTimeLocal),
					stopSequence);
		} catch (ParseException e) {
			e.printStackTrace();
		}
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
	private TextField fromStopIdField;

	@FXML
	private TextField toStopIdField;

	@FXML
	private TextField transferTypeField;

	@FXML
	private TextField minTransferTimeField;

	@FXML
	private TableView transfersAllTable;

	@FXML
	private TableColumn transfersFromStopIdColumn;

	@FXML
	private TableColumn transfersToStopIdColumn;

	@FXML
	private TableColumn transfersTypeColumn;

	@FXML
	private TableColumn transfersTimeColumn;

	@FXML
	private void confirmAjoutTransfers() {
		String fromStopId = fromStopIdField.getText();
		String toStopId = toStopIdField.getText();
		int transferType = Integer.parseInt(transferTypeField.getText());
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
		int transferType = Integer.parseInt(transferTypeField.getText());
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
		int transferType = Integer.parseInt(transferTypeField.getText());
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
	private TextField serviceIdField;

	@FXML
	private TextField directionIdField;

	@FXML
	private TextField tripShortNameField;

	@FXML
	private TableView tripsAllTable;

	@FXML
	private TableColumn tripsRouteIdColumn;

	@FXML
	private TableColumn tripsServiceIdColumn;

	@FXML
	private TableColumn tripsTripIdColumn;

	@FXML
	private TableColumn tripsHeadSignColumn;

	@FXML
	private TableColumn tripsShortNameColumn;

	@FXML
	private TableColumn tripsDirectionIdColumn;

	@FXML
	private TableColumn tripsShapeIdColumn;

	@FXML
	private void confirmAjoutTrips() {
		String tripId = tripIdField.getText();
		String serviceId = serviceIdField.getText();
		String routesId = routesIdField.getText();
		String directionId = directionIdField.getText();

		String tripShortName = tripShortNameField.getText();

		TripsComp trip = new TripsComp(routesId, serviceId, tripId, "", tripShortName, directionId, "");
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
		String tripShortName = tripShortNameField.getText();

		TripsComp trip = new TripsComp(routesId, serviceId, tripId, "", tripShortName, directionId, "");
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
		String tripShortName = tripShortNameField.getText();

		TripsComp trip = new TripsComp(routesId, serviceId, tripId, "", tripShortName, directionId, "");
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
		tripsShortNameColumn.setCellValueFactory(new PropertyValueFactory("trip_short_name"));
		tripsDirectionIdColumn.setCellValueFactory(new PropertyValueFactory("direction_id"));
		tripsAllTable.getItems().addAll(data);
	}

	// gestion Calendar

	@FXML
	private DatePicker startDateField;

	@FXML
	private DatePicker endDateField;

	@FXML
	private CheckBox mondayBox;

	@FXML
	private CheckBox tuesdayBox;

	@FXML
	private CheckBox wednesdayBox;

	@FXML
	private CheckBox thursdayBox;

	@FXML
	private CheckBox fridayBox;

	@FXML
	private CheckBox saturdayBox;

	@FXML
	private CheckBox sundayBox;

	@FXML
	private TableColumn calendarServiceIdColumn;

	@FXML
	private TableColumn calendarMondayColumn;

	@FXML
	private TableColumn calendarTuesdayColumn;

	@FXML
	private TableColumn calendarWednesdayColumn;

	@FXML
	private TableColumn calendarThursdayColumn;

	@FXML
	private TableColumn calendarFridayColumn;

	@FXML
	private TableColumn calendarSaturdayColumn;

	@FXML
	private TableColumn calendarSundayColumn;

	@FXML
	private TableColumn calendarStartDateColumn;

	@FXML
	private TableColumn calendarEndDateColumn;

	@FXML
	private TableView calendarAllTable;

	@FXML
	private void confirmAjoutCalendar() {
		String serviceId = serviceIdField.getText();
		LocalDate startDateLocal = startDateField.getValue();
		LocalDate endDateLocal = endDateField.getValue();

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

		ServiceComp cald = new ServiceComp(serviceId, monday, tuesday, wednesday, thursday, friday, saturday, sunday,
				startDateLocal, endDateLocal);
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

		ServiceComp cald = new ServiceComp(serviceId, monday, tuesday, wednesday, thursday, friday, saturday, sunday,
				startDateLocal, endDateLocal);
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
	private DatePicker dateField;

	@FXML
	private TextField exceptionTypeField;

	@FXML
	private TableColumn calendarDatesDateColumn;

	@FXML
	private TableColumn calendarDatesServiceIdColumn;

	@FXML
	private TableColumn calendarDatesExceptionTypeColumn;

	@FXML
	private TableView calendarDatesAllTable;

	@FXML
	private void confirmAjoutCalendarDates() {
		String serviceId = serviceIdField.getText();
		LocalDate dateLocal = dateField.getValue();
		String exceptionTypeString = exceptionTypeField.getText();
		int exceptionType = Integer.parseInt(exceptionTypeString);

		CalendarExpComp caldates = new CalendarExpComp(serviceId, dateLocal, exceptionType);
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

		CalendarExpComp caldates = new CalendarExpComp(serviceId, dateLocal, exceptionType);
		if (!Client.editCalendarDates(caldates)) {
			Alert alert = new Alert(AlertType.WARNING, "Modification impossible");
			alert.show();
		}
	}

	@FXML
	private void confirmSupprCalendarDates() {
		String serviceId = serviceIdField.getText();
		LocalDate dateLocal = dateField.getValue();

		if (!Client.removeCalendarDates(serviceId, dateLocal)) {
			Alert alert = new Alert(AlertType.WARNING, "Modification impossible");
			alert.show();
		}
	}

	@FXML
	private void confirmShowCalendarDates() {
		Set<CalendarExpComp> data = Client.showCalendarDates();
		calendarDatesDateColumn.setCellValueFactory(new PropertyValueFactory("added"));
		calendarDatesServiceIdColumn.setCellValueFactory(new PropertyValueFactory("service_id"));
		calendarDatesExceptionTypeColumn.setCellValueFactory(new PropertyValueFactory("type"));
		calendarDatesAllTable.getItems().addAll(data);
	}

//	public static void loadTransfertCheck() {
//		datatransferTypeField.add(0);
//		datatransferTypeField.add(1);
//		datatransferTypeField.add(2);
//		datatransferTypeField.add(3);
//		datatransferTypeField.add(4);
//		transferTypeField.getItems().addAll(datatransferTypeField);
//	}

}
