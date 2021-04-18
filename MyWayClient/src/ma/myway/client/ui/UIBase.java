package ma.myway.client.ui;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class UIBase extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {


		List<String> stops = new ArrayList<>();

		SplitPane splitPane = new SplitPane();
		VBox leftControl = new VBox();
		//VBox rightControl = new VBox();
		//BorderPane rightControl = new BorderPane();
		splitPane.getItems().addAll(leftControl/*, rightControl*/);
		Scene scene = new Scene(splitPane, 824, 618);

		VBox srcControl = new VBox();
		Label lblSrc = new Label("\t*Choisisser la gare de depart :");
		AutocompletionlTextField souce = new AutocompletionlTextField();
		souce.getEntries().addAll(stops);
		srcControl.getChildren().addAll(lblSrc, souce);
		srcControl.setSpacing(5);
		leftControl.getChildren().add(srcControl);

		VBox destControl = new VBox();
		Label lblDest = new Label("\t*Choisisser la gare de depart :");
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
		leftControl.getChildren().add(rechercher);

		splitPane.setDividerPositions(0.25);
		leftControl.setMinWidth(scene.getWidth() * 0.25);
		leftControl.setMaxWidth(scene.getWidth() * 0.25);
		leftControl.setAlignment(Pos.CENTER);
		leftControl.setSpacing(15);

		//rightControl.setFillWidth(true);
		//rightControl.setMaxHeight(Double.MAX_VALUE);
		
		WebView map = new WebView();
		//map.getEngine().load("https://openlayers.org/en/main/examples/simple.html");
		map.getEngine().setJavaScriptEnabled(true);
		map.getEngine().load(UIBase.class.getResource("/web/index.html").toExternalForm());
		splitPane.getItems().addAll(map/*, rightControl*/);
		
		/*rightControl.getChildren().add(map);
		//VBox.setVgrow(map, Priority.ALWAYS);
		rightControl.setBackground(new Background(new BackgroundFill(Color.GREY, CornerRadii.EMPTY, Insets.EMPTY)));
*/
		primaryStage.getIcons().add(new Image("/img/logo.png"));
		primaryStage.setScene(scene);
		primaryStage.setTitle("MyWay");
		primaryStage.show();
		primaryStage.setResizable(false);

		/*
		 * DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		 * timeSpinner.valueProperty().addListener((obs, oldTime, newTime) ->
		 * System.out.println(formatter.format(newTime)));
		 */

	}

	public static void main(String[] args) {
		launch(args);
	}

}
