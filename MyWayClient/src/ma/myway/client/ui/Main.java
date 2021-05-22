package ma.myway.client.ui;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ma.myway.client.network.Client;
import ma.myway.client.ui.model.SceneName;
import ma.myway.client.ui.view.UIBase;
import ma.myway.client.ui.view.UILogging;

public class Main extends Application {

	static BorderPane mainLayout1 = null;
	static Pane mainLayout2 = null;
	static int choice = 0;
	/** Holds the various scenes to switch between */
	private static Map<SceneName, Scene> scenes = new HashMap<>();
	public static Client client = null;
	public static String path = System.getProperty("user.home") + "\\AppData\\Roaming\\WebClient\\";
	private ClassLoader classLoader = getClass().getClassLoader();

	public static void main(String[] args) {
		Logger loggerServer = Logger.getLogger("CLIENT");
		try {
			FileHandler fh = new FileHandler("Logs_Client.log");
			loggerServer.addHandler(fh);
			fh.setFormatter(new Formatter() {

				@Override
				public String format(LogRecord record) {
					SimpleDateFormat logTime = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
					Calendar cal = new GregorianCalendar();
					cal.setTimeInMillis(record.getMillis());
					return "CLIENT " + record.getLevel() + " " + logTime.format(cal.getTime()) + " || "
							+ record.getSourceClassName().substring(record.getSourceClassName().lastIndexOf(".") + 1,
									record.getSourceClassName().length())
							+ "." + record.getSourceMethodName() + "() : " + record.getMessage() + "\n";
				}
			});
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<File> files = new ArrayList<>();
		try {
			files.add(Paths.get(ClassLoader.getSystemResource("web/index.html").toURI()).toFile());
			files.add(Paths.get(ClassLoader.getSystemResource("web/test.json").toURI()).toFile());

		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}
		try {
			Path pa = Paths.get(path);
			if (!Files.exists(pa, LinkOption.NOFOLLOW_LINKS))
				Files.createDirectory(pa);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		for (File file : files) {
			try {
				File aa = (new File(path + file.getName()));
				Files.copy(file.toPath(), aa.toPath(), StandardCopyOption.REPLACE_EXISTING);
				System.out.println(aa.getAbsolutePath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		client = new Client("127.0.0.1", 9325);
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		scenes.put(SceneName.BASE, new UIBase().getScene());
		scenes.put(SceneName.LOGING, new UILogging(primaryStage).getScene());
		scenes.put(SceneName.ADMIN, returnSceneAdmin());
		scenes.put(SceneName.FIRSTADMIN, returnSceneFirstAdmin());

		primaryStage.getIcons().add(new Image("img/logo.png"));
		primaryStage.setScene(scenes.get(SceneName.LOGING));
		primaryStage.setTitle("MyWay");
		primaryStage.show();
		primaryStage.setResizable(false);
	}

	public static Map<SceneName, Scene> getScenes() {
		return scenes;
	}

	public static void setScenes(Map<SceneName, Scene> scenes) {
		Main.scenes = scenes;
	}

	public static Client getClient() {
		return client;

	}

	public static void showAgencyScene() {
		if (choice == 1) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("viewFxml/UIAgencyAjout.fxml"));
				Pane agencyAjout = loader.load();
				mainLayout1.setCenter(agencyAjout);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if (choice == 2) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("viewFxml/UIAgencyModif.fxml"));
				Pane agencyModif = loader.load();
				mainLayout1.setCenter(agencyModif);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void showRoutesScene() {
		if (choice == 1) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("viewFxml/UIRoutesAjout.fxml"));
				Pane routesAjout = loader.load();
				mainLayout1.setCenter(routesAjout);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if (choice == 2) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("viewFxml/UIRoutesModif.fxml"));
				Pane routesModif = loader.load();
				mainLayout1.setCenter(routesModif);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void showStopsScene() {
		if (choice == 1) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("viewFxml/UIStopsAjout.fxml"));
				Pane stopsAjout = loader.load();
				mainLayout1.setCenter(stopsAjout);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if (choice == 2) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("viewFxml/UIStopsModif.fxml"));
				Pane stopsModif = loader.load();
				mainLayout1.setCenter(stopsModif);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void showTripsScene() {
		if (choice == 1) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("viewFxml/UITripsAjout.fxml"));
				Pane tripsAjout = loader.load();
				mainLayout1.setCenter(tripsAjout);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if (choice == 2) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("viewFxml/UITripsModif.fxml"));
				Pane tripsModif = loader.load();
				mainLayout1.setCenter(tripsModif);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void showTransfersScene() {
		if (choice == 1) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("viewFxml/UITransfersAjout.fxml"));
				Pane transfersAjout = loader.load();
				mainLayout1.setCenter(transfersAjout);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if (choice == 2) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("viewFxml/UITransfersModif.fxml"));
				Pane transfersModif = loader.load();
				mainLayout1.setCenter(transfersModif);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void showCalendarScene() {
		if (choice == 1) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("viewFxml/UICalendarAjout.fxml"));
				Pane calendarAjout = loader.load();
				mainLayout1.setCenter(calendarAjout);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if (choice == 2) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("viewFxml/UICalendarModif.fxml"));
				Pane calendarModif = loader.load();
				mainLayout1.setCenter(calendarModif);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void showCalendarDatesScene() {
		if (choice == 1) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("viewFxml/UICalendarDatesAjout.fxml"));
				Pane calendarDatesAjout = loader.load();
				mainLayout1.setCenter(calendarDatesAjout);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if (choice == 2) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("viewFxml/UICalendarDatesModif.fxml"));
				Pane calendarDatesModif = loader.load();
				mainLayout1.setCenter(calendarDatesModif);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void showStopTimesScene() {
		if (choice == 1) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("viewFxml/UIStopTimesAjout.fxml"));
				Pane stopTimesAjout = loader.load();
				mainLayout1.setCenter(stopTimesAjout);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if (choice == 2) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("viewFxml/UIStopTimesModif.fxml"));
				Pane stopTimesModif = loader.load();
				mainLayout1.setCenter(stopTimesModif);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public Scene returnSceneFirstAdmin() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("viewFxml/FirstUIAdmin.fxml"));
			mainLayout2 = loader.load();
			Scene scene = new Scene(mainLayout2);
			return (scene);
		} catch (Exception e) {
			e.printStackTrace();
			return (null);
		}
	}

	public Scene returnSceneAdmin() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("viewFxml/UIAdmin.fxml"));
			mainLayout1 = loader.load();
			Scene scene = new Scene(mainLayout1);
			return (scene);
		} catch (Exception e) {
			e.printStackTrace();
			return (null);
		}
	}

	public void choiceAjouter() {
		if (choice != 1)
		{
			choice = 1;
			mainLayout1.setCenter(null);
		}
	}

	public void choiceModifier() {
		if (choice != 2)
		{
			choice = 2;
			mainLayout1.setCenter(null);
		}
	}

	public void choiceSupprimer() {
		if (choice != 3)
		{
			choice = 3;
			mainLayout1.setCenter(null);
		}
	}

	public void choiceAfficher() {
		if (choice != 4)
		{
			choice = 4;
			mainLayout1.setCenter(null);
		}
	}

	public void choiceStatistique() {
		if (choice != 5)
		{
			choice = 5;
			mainLayout1.setCenter(null);
		}
	}

}
