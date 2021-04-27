package ma.myway.client.ui;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
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

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import ma.myway.client.network.Client;
import ma.myway.client.ui.model.SceneName;
import ma.myway.client.ui.view.UIBase;
import ma.myway.client.ui.view.UILogging;

public class Main extends Application {

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
					return record.getLevel() + logTime.format(cal.getTime()) + " || "
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

		client = new Client("127.0.0.1", 8623);
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		scenes.put(SceneName.BASE, new UIBase().getScene());
		scenes.put(SceneName.LOGING, new UILogging(primaryStage).getScene());

		primaryStage.getIcons().add(new Image("/img/logo.png"));
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

}
