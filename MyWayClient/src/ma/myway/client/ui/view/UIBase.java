package ma.myway.client.ui.view;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import ma.myway.client.network.Client;
import ma.myway.client.ui.Main;

public class UIBase implements ViewMaker {

//	private Stage stage;
//	
//	public UIBase(Stage primaryStage) {
//		this.stage = primaryStage;
//	}


	@Override
	public Scene getScene() {

		Map<String, String> mapStops =  Client.GetStops();
		Set<String> setStops = mapStops.keySet();
//		mapStops.forEach((k, v) -> {
//			setStops.add(v);
//		});
		List<String> stops = List.copyOf(setStops);
		//List<String> stops = new 	ArrayList<>();
		SplitPane splitPane = new SplitPane();
		VBox leftControl = new VBox();
		//VBox rightControl = new VBox();
		//BorderPane rightControl = new BorderPane();
		splitPane.getItems().addAll(leftControl/*, rightControl*/);
		Scene scene = new Scene(splitPane, 824, 618);

		WebView map = new WebView();
		//map.getEngine().load("https://openlayers.org/en/main/examples/simple.html");
		map.getEngine().setJavaScriptEnabled(true);
		try {
			try {
				map.getEngine().load(new File(Main.path+"index.html").toURL().toURI().toString());
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		splitPane.getItems().addAll(map/*, rightControl*/);
		
		VBox srcControl = new VBox();
		Label lblSrc = new Label("\t*Choisisser la gare de depart :");
		AutocompletionlTextField source = new AutocompletionlTextField();
		source.getEntries().addAll(stops);
		srcControl.getChildren().addAll(lblSrc, source);
		srcControl.setSpacing(5);
		leftControl.getChildren().add(srcControl);

		VBox destControl = new VBox();
		Label lblDest = new Label("\t*Choisisser la gare d'arriver :");
		AutocompletionlTextField dest = new AutocompletionlTextField();
		dest.getEntries().addAll(stops);
		destControl.getChildren().addAll(lblDest, dest);
		destControl.setSpacing(5);
		leftControl.getChildren().add(destControl);

		VBox dateControl = new VBox();
		Label lblDate = new Label("Choisisser la date de voyage :");
		DatePicker datePicker = new DatePicker();
		dateControl.getChildren().addAll(lblDate, datePicker);
		dateControl.setAlignment(Pos.CENTER);
		dateControl.setSpacing(5);
		leftControl.getChildren().add(dateControl);

		VBox timeControl = new VBox();
		Label lblTime = new Label("Choisisser l'heure de voyage :");
		TimeSpinner timeSpinner = new TimeSpinner();
		timeControl.getChildren().addAll(lblTime, timeSpinner);
		timeControl.setAlignment(Pos.CENTER);
		timeControl.setSpacing(5);
		leftControl.getChildren().add(timeControl);

		Button rechercher = new Button("Rechercher !");
		rechercher.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				if((mapStops.get(source.getText()) != null && mapStops.get(dest.getText()) !=null ) && !source.getText().equals(dest.getText())) {
					try {
						Client.chem(mapStops.get(source.getText()), mapStops.get(dest.getText()));
					} catch (IOException e) {
						e.printStackTrace();
					}
					map.getEngine().reload();

				}
				
			}
		});
		leftControl.getChildren().add(rechercher);

		splitPane.setDividerPositions(0.25);
		leftControl.setMinWidth(scene.getWidth() * 0.25);
		leftControl.setMaxWidth(scene.getWidth() * 0.25);
		leftControl.setAlignment(Pos.CENTER);
		leftControl.setSpacing(15);

		//rightControl.setFillWidth(true);
		//rightControl.setMaxHeight(Double.MAX_VALUE);
		

		
		/*rightControl.getChildren().add(map);
		//VBox.setVgrow(map, Priority.ALWAYS);
		rightControl.setBackground(new Background(new BackgroundFill(Color.GREY, CornerRadii.EMPTY, Insets.EMPTY)));
*/


		/*
		 * DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		 * timeSpinner.valueProperty().addListener((obs, oldTime, newTime) ->
		 * System.out.println(formatter.format(newTime)));
		 */

		return scene;
	}

}
