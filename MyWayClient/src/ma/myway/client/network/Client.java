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
//		writer.write("GETSTOPS");
//		writer.flush();

		try {
			WRITE("GETSTOPS");
//			String sizename = read(-1);
//			Logger.getLogger("CLIENT").info("sizename : " + sizename);
//			
//			stopnames = read(Integer.parseInt(sizename));
//			Logger.getLogger("CLIENT").info(stopnames);
//			
//			
//			String sizeid = read(-1);
//			Logger.getLogger("CLIENT").info("sizeid : " + sizeid);
//			
//			stopid = read(Integer.parseInt(sizeid));
//			Logger.getLogger("CLIENT").info(stopid);

			stopnames = READ();
			System.out.println("stopnames");
			Logger.getLogger("CLIENT").info(stopnames);

			stopid = READ();
			System.out.println("stopid");
			Logger.getLogger("CLIENT").info(stopid);
		} catch (IOException e) {
			e.printStackTrace();
		}

		StringTokenizer nametok = new StringTokenizer(stopnames, "#");
		StringTokenizer idtok = new StringTokenizer(stopid, "#");
		while (nametok.hasMoreElements() && idtok.hasMoreElements()) {
			stopMap.put(nametok.nextElement().toString(), idtok.nextElement().toString());
			// stopMap.put(idtok.nextElement().toString(),
			// nametok.nextElement().toString());

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
//		writer.write("CLOSE");
//		writer.flush();
		
		try {WRITE("CLOSE");
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
//		writer.write("CHEM " + src + " " + dep);
//		writer.flush();

		String path = null;
		try {
			WRITE("CHEM " + src + " " + dep);

//			String sizepath = read(-1);
//			Logger.getLogger("CLIENT").info("sizepath : " + sizepath);
//			path = read(Integer.parseInt(sizepath));
			path = READ();
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
//		writer.write("CONN " + username + " " + password);
//		writer.flush();

		try {
			WRITE("CONN " + username + " " + password);

			return Boolean.parseBoolean(READ());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	private static String read(int i, Boolean isconfirmation) throws IOException {
//		String response = "";
//		int stream;
//		byte[] b = new byte[i];
//		stream = reader.read(b);
//		response = new String(b, 0, stream);
		String response = buffReader.readLine();

		Logger.getLogger("CLIENT").info("\tdata : " + response);

//		if (!isconfirmation) {
//			writer.write("received");
//			writer.flush();
//		}

		return response;
	}

	private static String READ() throws IOException {
//		String size = read(20, false); /* check the size */

		// received

//		String data = read(Integer.parseInt(size), false);
		String data = read(-1, false);

		// received

		return data;
	}

	private static void WRITE(String str) throws IOException {
//		int size = str.getBytes("UTF-8").length;
//		writer.write(String.valueOf(size));
//		writer.flush();
//		read(8,true);
//		writer.write(str);
//		writer.flush();
//		read(8,true);
//		int size = str.getBytes("UTF-8").length;
//		buffWriter.write(String.valueOf(size));
//		buffWriter.newLine();
//		buffWriter.flush();
//		read(8,true);
		buffWriter.write(str);
		buffWriter.newLine();
		buffWriter.flush();
//		read(8,true);
	}

}
