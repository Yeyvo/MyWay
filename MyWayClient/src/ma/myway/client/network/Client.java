package ma.myway.client.network;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.Socket;
import java.net.UnknownHostException;
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
	private static BufferedInputStream reader = null;

	public Client(String host, int port) {
		try {
			client = new Socket(host, port);
			writer = new PrintWriter(client.getOutputStream(), true);
			reader = new BufferedInputStream(client.getInputStream());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Map<String, String> GetStops() {
		Map<String, String> stopMap = new HashMap<>();
		String stopnames = null, stopid = null;
		writer.write("GETSTOPS");
		writer.flush();

		try {
			String sizename = read(-1);
			Logger.getLogger("CLIENT").info("sizename : " + sizename);

			stopnames = read(Integer.parseInt(sizename));
			Logger.getLogger("CLIENT").info(stopnames);
			
			
			String sizeid = read(-1);
			Logger.getLogger("CLIENT").info("sizeid : " + sizeid);

			stopid = read(Integer.parseInt(sizeid));
			Logger.getLogger("CLIENT").info(stopid);
		} catch (IOException e) {
			e.printStackTrace();
		}

		StringTokenizer nametok = new StringTokenizer(stopnames, "#");
		StringTokenizer idtok = new StringTokenizer(stopid, "#");
		while (nametok.hasMoreElements() && idtok.hasMoreElements()) {
			stopMap.put(nametok.nextElement().toString(), idtok.nextElement().toString());
//			stopMap.put( idtok.nextElement().toString(),nametok.nextElement().toString());

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
		writer.write("CLOSE");
		writer.flush();
		try {
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
		writer.write("CHEM " + src + " " + dep);
		writer.flush();

		String path = null;
		try {
			String sizepath = read(-1);
			Logger.getLogger("CLIENT").info("sizepath : " + sizepath);
			path = read(Integer.parseInt(sizepath));
			Logger.getLogger("CLIENT").info(path);
			PrintWriter pathjsonwriter = new PrintWriter( new File(Main.path+"test.json"));
			pathjsonwriter.write(path);
			pathjsonwriter.close();
			
			JsonElement tmpJsonElement = Streams.parse(new JsonReader(new StringReader(path)));
			int size = tmpJsonElement.getAsJsonObject().get("path").getAsJsonArray().size();
			System.out.println(size);
			if(size == 1) {
				Alert alert = new Alert(AlertType.WARNING, "Aucun chemin n'as ete trouvez");
				alert.show();
			}
			
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
		return path;
	}
	
	public static boolean conn(String username, String password) {
		writer.write("CONN "+username+" " +password);
		writer.flush();
		
		try {
			return Boolean.parseBoolean(read(-1));
		} catch (IOException e) {
			e.printStackTrace();
		}
			return false;
	}

}
