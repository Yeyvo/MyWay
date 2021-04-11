package ma.myway.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Config {

	public String BDD;
	public String IP;
	public String PRT;
	public String BDDNAME;
	public String USR;
	public String PSWD;

	public Config() {
		this.BDD = "mysql";
		this.IP = "127.0.0.1";
		this.PRT = "3306";
		this.BDDNAME = "gtfs";
		this.USR = "test";
		this.PSWD = "test";
	}

	private static Config instance;

	public static Config getInstance() {
		if (instance == null) {
			instance = fromDefaults();
		}
		return instance;
	}

	public static void load(File file) {
		instance = fromFile(file);

		if (instance == null) {
			instance = fromDefaults();
		}
	}

	public static void load(String file) {
		load(new File(file));
	}

	private static Config fromDefaults() {
		Config config = new Config();
		return config;
	}

	public void toFile(String file) {
		toFile(new File(file));
	}

	public void toFile(File file) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String jsonConfig = gson.toJson(this);
		FileWriter writer;
		try {
			writer = new FileWriter(file);
			writer.write(jsonConfig);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static Config fromFile(File configFile) {
		try {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(configFile)));
			return gson.fromJson(reader, Config.class);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String toString() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(this);
	}
}
