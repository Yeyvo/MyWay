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
import ma.myway.users.User;

public class Main extends Application {

	static BorderPane mainLayout1 = null;
	static Pane mainLayout2 = null;
	static int choice = 0;
	/** Holds the various scenes to switch between */
	private static Map<SceneName, Scene> scenes = new HashMap<>();
	public static Client client = null;
	public static String path = System.getProperty("user.home") + "\\AppData\\Roaming\\WebClient\\";
	private ClassLoader classLoader = getClass().getClassLoader();
	public static User user = new User("","","");

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
//		scenes.put(SceneName.ADMINWELC, returnSceneAdminWelc());

		primaryStage.getIcons().add(new Image("img/logo.png"));
		primaryStage.setScene(scenes.get(SceneName.LOGING));
		primaryStage.setTitle("MyWay");
		primaryStage.show();
		primaryStage.setResizable(false);
//		Client.addAgency(new Agency("999", "dfhgjhg,", "pfff", "atay"));
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

	public static void choiceAgency() {
		if (choice != 1) {
			choice = 1;
			mainLayout1.setCenter(null);
		}
	}

	public static void choiceRoutes() {
		if (choice != 2) {
			choice = 2;
			mainLayout1.setCenter(null);
		}
	}

	public static void choiceStops() {
		if (choice != 3) {
			choice = 3;
			mainLayout1.setCenter(null);
		}
	}

	public static void choiceTrips() {
		if (choice != 4) {
			choice = 4;
			mainLayout1.setCenter(null);
		}
	}

	public static void choiceTransfers() {
		if (choice != 5) {
			choice = 5;
			mainLayout1.setCenter(null);
		}
	}

	public static void choiceCalendar() {
		if (choice != 6) {
			choice = 6;
			mainLayout1.setCenter(null);
		}
	}

	public static void choiceCalendarDates() {
		if (choice != 7) {
			choice = 7;
			mainLayout1.setCenter(null);
		}
	}

	public static void choiceStopTimes() {
		if (choice != 8) {
			choice = 8;
			mainLayout1.setCenter(null);
		}
	}
	
	public static void choiceUsers() {
		if (choice != 9) {
			choice = 9;
			mainLayout1.setCenter(null);
		}
	}

	public static Scene returnSceneAdminWelc() {
//		try {
//			FXMLLoader loader = new FXMLLoader();
//			loader.setLocation(Main.class.getResource("viewFxml/UIAdminWelcome.fxml"));
//			mainLayout2 = loader.load();
//			Scene scene = new Scene(mainLayout2);
//			return (scene);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return (null);
//		}
		
		Scene scene = null;
		if(user.getPerm().equals("admin")) {
			scene = returnSceneAdmin();
		} else {
			 openBase();
		}
		return scene;
	}

	public static Scene returnSceneAdmin() {
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

	public void showAjouterScene() {
		if (choice == 1) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("viewFxml/UIAgencyAjout.fxml"));
				Pane agencyAjout = loader.load();
				mainLayout1.setCenter(agencyAjout);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (choice == 2) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("viewFxml/UIRoutesAjout.fxml"));
				Pane routesAjout = loader.load();
				mainLayout1.setCenter(routesAjout);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (choice == 3) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("viewFxml/UIStopsAjout.fxml"));
				Pane stopsAjout = loader.load();
				mainLayout1.setCenter(stopsAjout);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (choice == 4) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("viewFxml/UITripsAjout.fxml"));
				Pane tripsAjout = loader.load();
				mainLayout1.setCenter(tripsAjout);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (choice == 5) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("viewFxml/UITransfersAjout.fxml"));
				Pane transfersAjout = loader.load();
				mainLayout1.setCenter(transfersAjout);
//				ShowSceneController.loadTransfertCheck();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (choice == 6) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("viewFxml/UICalendarAjout.fxml"));
				Pane calendarAjout = loader.load();
				mainLayout1.setCenter(calendarAjout);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (choice == 7) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("viewFxml/UICalendarDatesAjout.fxml"));
				Pane calendarDatesAjout = loader.load();
				mainLayout1.setCenter(calendarDatesAjout);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (choice == 8) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("viewFxml/UIStopTimesAjout.fxml"));
				Pane stopTimesAjout = loader.load();
				mainLayout1.setCenter(stopTimesAjout);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (choice == 9) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("viewFxml/UIUsersAjout.fxml"));
				Pane stopTimesAjout = loader.load();
				mainLayout1.setCenter(stopTimesAjout);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void showModifierScene() {
		if (choice == 1) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("viewFxml/UIAgencyModif.fxml"));
				Pane agencyModif = loader.load();
				mainLayout1.setCenter(agencyModif);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (choice == 2) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("viewFxml/UIRoutesModif.fxml"));
				Pane routesModif = loader.load();
				mainLayout1.setCenter(routesModif);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (choice == 3) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("viewFxml/UIStopsModif.fxml"));
				Pane stopsModif = loader.load();
				mainLayout1.setCenter(stopsModif);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (choice == 4) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("viewFxml/UITripsModif.fxml"));
				Pane tripsModif = loader.load();
				mainLayout1.setCenter(tripsModif);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (choice == 5) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("viewFxml/UITransfersModif.fxml"));
				Pane transfersModif = loader.load();
				mainLayout1.setCenter(transfersModif);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (choice == 6) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("viewFxml/UICalendarModif.fxml"));
				Pane calendarModif = loader.load();
				mainLayout1.setCenter(calendarModif);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (choice == 7) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("viewFxml/UICalendarDatesModif.fxml"));
				Pane calendarDatesModif = loader.load();
				mainLayout1.setCenter(calendarDatesModif);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (choice == 8) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("viewFxml/UIStopTimesModif.fxml"));
				Pane stopTimesModif = loader.load();
				mainLayout1.setCenter(stopTimesModif);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if (choice == 9) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("viewFxml/UIUsersModif.fxml"));
				Pane stopTimesModif = loader.load();
				mainLayout1.setCenter(stopTimesModif);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void showSupprimerScene() {
		if (choice == 1) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("viewFxml/UIAgencySuppr.fxml"));
				Pane agencySuppr = loader.load();
				mainLayout1.setCenter(agencySuppr);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (choice == 2) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("viewFxml/UIRoutesSuppr.fxml"));
				Pane routesSuppr = loader.load();
				mainLayout1.setCenter(routesSuppr);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (choice == 3) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("viewFxml/UIStopsSuppr.fxml"));
				Pane stopsSuppr = loader.load();
				mainLayout1.setCenter(stopsSuppr);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (choice == 4) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("viewFxml/UITripsSuppr.fxml"));
				Pane tripsSuppr = loader.load();
				mainLayout1.setCenter(tripsSuppr);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (choice == 5) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("viewFxml/UITransfersSuppr.fxml"));
				Pane transfersSuppr = loader.load();
				mainLayout1.setCenter(transfersSuppr);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (choice == 6) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("viewFxml/UICalendarSuppr.fxml"));
				Pane calendarSuppr = loader.load();
				mainLayout1.setCenter(calendarSuppr);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (choice == 7) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("viewFxml/UICalendarDatesSuppr.fxml"));
				Pane calendarDatesSuppr = loader.load();
				mainLayout1.setCenter(calendarDatesSuppr);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (choice == 8) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("viewFxml/UIStopTimesSuppr.fxml"));
				Pane stopTimesSuppr = loader.load();
				mainLayout1.setCenter(stopTimesSuppr);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if (choice == 9) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("viewFxml/UIUsersSuppr.fxml"));
				Pane stopTimesSuppr = loader.load();
				mainLayout1.setCenter(stopTimesSuppr);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void showAfficherScene() {
		if (choice == 1) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("viewFxml/UIAgencyAffich.fxml"));
				Pane agencyAffich = loader.load();
				mainLayout1.setCenter(agencyAffich);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (choice == 2) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("viewFxml/UIRoutesAffich.fxml"));
				Pane routesAffich = loader.load();
				mainLayout1.setCenter(routesAffich);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (choice == 3) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("viewFxml/UIStopsAffich.fxml"));
				Pane stopsAffich = loader.load();
				mainLayout1.setCenter(stopsAffich);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (choice == 4) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("viewFxml/UITripsAffich.fxml"));
				Pane tripsAffich = loader.load();
				mainLayout1.setCenter(tripsAffich);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (choice == 5) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("viewFxml/UITransfersAffich.fxml"));
				Pane transfersAffich = loader.load();
				mainLayout1.setCenter(transfersAffich);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (choice == 6) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("viewFxml/UICalendarAffich.fxml"));
				Pane calendarAffich = loader.load();
				mainLayout1.setCenter(calendarAffich);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (choice == 7) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("viewFxml/UICalendarDatesAffich.fxml"));
				Pane calendarDatesAffich = loader.load();
				mainLayout1.setCenter(calendarDatesAffich);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (choice == 8) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("viewFxml/UIStopTimesAffich.fxml"));
				Pane stopTimesAffich = loader.load();
				mainLayout1.setCenter(stopTimesAffich);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if (choice == 9) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("viewFxml/UIUsersAffich.fxml"));
				Pane stopTimesAffich = loader.load();
				mainLayout1.setCenter(stopTimesAffich);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void showStatistiqueScene() {

	}
	
	public void showGestionScene() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("viewFxml/UIAdmin.fxml"));
			mainLayout1 = loader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(mainLayout1));
			stage.getIcons().add(new Image("img/logo.png"));
			stage.setTitle("MyWay");
			stage.show();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void showAppScene() {
		Stage stage = new Stage();
		stage.setScene(scenes.get(SceneName.BASE));
		stage.getIcons().add(new Image("img/logo.png"));
		stage.setTitle("MyWay");
		stage.show();
	}
	
	public void quitAppScene() {
		
	}
	
	public void showMenuAdmin() {
		Stage stage = new Stage();
		stage.setScene(scenes.get(SceneName.ADMINWELC));
		stage.getIcons().add(new Image("img/logo.png"));
		stage.setTitle("MyWay");
		stage.show();
	}

	public static void openBase() {
		Stage stage = new Stage();
		stage.setScene(scenes.get(SceneName.BASE));
		stage.getIcons().add(new Image("img/logo.png"));
		stage.setTitle("MyWay");
		stage.show();
		
	}

	public User getUser() {
		return user;
	}

	public static void setUser(User user) {
		user = user;
	}
	
	
	
}
