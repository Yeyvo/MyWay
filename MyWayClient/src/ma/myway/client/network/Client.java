package ma.myway.client.network;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Logger;

import com.google.gson.JsonElement;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import ma.myway.client.ui.Main;

public class Client {

	private static Socket client = null;
	private static PrintWriter writer = null;
	private static BufferedOutputStream bos = null;
	private static BufferedWriter buffWriter = null;
	private static BufferedInputStream reader = null;
	private static BufferedReader buffReader = null;

	public Client(String host, int port) {
		try {
			client = new Socket(host, port);
			writer = new PrintWriter(client.getOutputStream(), true);
			reader = new BufferedInputStream(client.getInputStream());
			buffReader = new BufferedReader(new InputStreamReader(reader, StandardCharsets.UTF_8));

			bos = new BufferedOutputStream(client.getOutputStream());
			buffWriter = new BufferedWriter(new OutputStreamWriter(bos, StandardCharsets.UTF_8));

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Map<String, String> GetStops() {
		Map<String, String> stopMap = new HashMap<>();
		String stopnames = null, stopid = null;


		try {
			WRITE("GETSTOPS");
			stopnames = READ(false);
			System.out.println("stopnames");
			Logger.getLogger("CLIENT").info(stopnames);

			stopid = READ(false);
			System.out.println("stopid");

			Logger.getLogger("CLIENT").info(stopid);
		} catch (IOException e) {
			e.printStackTrace();
		}

		StringTokenizer nametok = new StringTokenizer(stopnames, "#");
		StringTokenizer idtok = new StringTokenizer(stopid, "#");
		while (nametok.hasMoreElements() && idtok.hasMoreElements()) {
			stopMap.put(nametok.nextElement().toString(), idtok.nextElement().toString());
			//stopMap.put(idtok.nextElement().toString(), nametok.nextElement().toString());
		}

		return stopMap;
	}

	private static String read(int i) throws IOException {
		String response = "";
		int stream;
		byte[] b;

		if (i < 0) {
			b = new byte[1024];
		} else {
			b = new byte[i];
		}
		stream = reader.read(b);
		response = new String(b, 0, stream);

		writer.write("received");
		writer.flush();

		return response;
	}

	public static void close() {

		try {
			WRITE("CLOSE");

			Logger.getLogger("CLIENT").info(read(-1));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			reader.close();
			writer.close();
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String chem(String src, String dep) {

		String path = null;
		try {
			WRITE("CHEM " + src + " " + dep);

			path = READ(true);

			Logger.getLogger("CLIENT").info(path);
			PrintWriter pathjsonwriter = new PrintWriter(new File(Main.path + "test.json"));
			pathjsonwriter.write(path);
			pathjsonwriter.close();

			JsonElement tmpJsonElement = Streams.parse(new JsonReader(new StringReader(path)));
			int size = tmpJsonElement.getAsJsonObject().get("path").getAsJsonArray().size();
			System.out.println(size);
			if (size == 1) {
				Alert alert = new Alert(AlertType.WARNING, "Aucun chemin n'as ete trouvez");
				alert.show();
			}

		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
		return path;
	}


	public static boolean conn(String username, String password) {
		try {
			WRITE("CONN " + username + " " + password);

			return Boolean.parseBoolean(READ(false));

		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	private static String read(Boolean isjson) throws IOException {
		String response = "";
		if (!isjson) {
			response = buffReader.readLine();
		} else {
			int lines = Integer.parseInt(buffReader.readLine());
			for (int i = 0; i <= lines; i++) {
				response += buffReader.readLine() + "\n";
			}
		}
		Logger.getLogger("CLIENT").info("\tdata : " + response);

		return response;
	}

	private static String READ(boolean isjson) throws IOException {
		String data = read(isjson);

		return data;
	}

	private static void WRITE(String str) throws IOException {
		buffWriter.write(str);
		buffWriter.newLine();
		buffWriter.flush();
	}


}
