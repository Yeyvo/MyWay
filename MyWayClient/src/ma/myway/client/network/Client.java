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

	public static Map<String, String> GetStops()  {
		Map<String, String> stopMap = new HashMap<>();
		String stopnames = null, stopid = null;
		try {
			WRITE("GETSTOPS");
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {
//			String sizename = read(-1);
//			Logger.getLogger("CLIENT").info("sizename : " + sizename);

			stopnames = READ();
			Logger.getLogger("CLIENT").info(stopnames);
			
			
//			String sizeid = read(-1);
//			Logger.getLogger("CLIENT").info("sizeid : " + sizeid);

			stopid = READ();
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


	public static void close() throws IOException {
		WRITE("CLOSE");
		
		try {
			Logger.getLogger("CLIENT").info(READ());
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

	public static String chem(String src, String dep) throws IOException {
		WRITE("CHEM " + src + " " + dep);

		String path = null;
		try {
			
			path = READ();
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
	
	public static boolean conn(String username, String password) throws IOException {
		String commande = "CONN "+username+" " +password;
		Logger.getLogger(commande);
		WRITE(commande);

		
		try {
			return Boolean.parseBoolean(READ());
		} catch (IOException e) {
			e.printStackTrace();
		}
			return false;
	}

	private static String read(int i, Boolean isconfirmation) throws IOException {
		String response = "";
		int stream;
		byte[] b = new byte[i];

//		if (i < 0) {
//			b = new byte[1024];
//		} else {
//			b = new byte[i];
//		}
		stream = reader.read(b);
		response = new String(b, 0, stream);
		
		if (!isconfirmation) {
			writer.write("received");
			writer.flush();
		}
		
		Logger.getLogger("CLIENT").info(response);
		
		return response;
	}
	
	private static String READ() throws IOException {
		String size = read(20,false); /* check the size */
		//received
		
		String data = read(Integer.parseInt(size),false);
		//received

		return data;
	}
	
	private static void WRITE(String str) throws IOException {
		writer.write(String.valueOf(str.getBytes("UTF-8").length));
		writer.flush();
		read(8,true);
		writer.write(str);
		writer.flush();
		read(8,true);
	}
	
}
