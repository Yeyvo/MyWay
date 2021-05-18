package ma.myway.network;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Logger;

import com.google.gson.stream.JsonWriter;
import ma.myway.dao.DAOFactory;
import ma.myway.graph.Edge;
import ma.myway.graph.Graph;
import ma.myway.graph.Node;
import ma.myway.graph.data.Stop;
import ma.myway.users.User;

public class ClientProcessor implements Runnable {

	private Socket client = null;
	private BufferedOutputStream bos = null;
	private BufferedWriter buffWriter = null;
	private BufferedInputStream reader = null;
	private BufferedReader buffReader = null;

	public ClientProcessor(Socket client) {
		this.client = client;
	}

	@Override
	public void run() {
		Logger.getLogger("SERVER").info("Starting the processing of the client connection " + client.isClosed());
		boolean closeConnection = false;
		while (!client.isClosed()) {
			try {

				bos = new BufferedOutputStream(client.getOutputStream());
				buffWriter = new BufferedWriter(new OutputStreamWriter(bos, StandardCharsets.UTF_8));

				reader = new BufferedInputStream(client.getInputStream());
				buffReader = new BufferedReader(new InputStreamReader(reader, StandardCharsets.UTF_8));
				String response = READ();

				InetSocketAddress remote = (InetSocketAddress) client.getRemoteSocketAddress();
				String debug = "Thread : " + Thread.currentThread().getName() + ". ";
				debug += "Client ADRESSE : " + remote.getAddress().getHostAddress() + ".";
				debug += " Port : " + remote.getPort() + ".\n";
				debug += "\t -> Commande : " + response + "\n";
				Logger.getLogger("SERVER").info(debug);

				String toSend = "";
				if (response.toUpperCase().startsWith("CHEM")) {
					/*
					 * commande que le client envoi pour demander un path Syntaxe : CHEM
					 * {stop_id_dep} {stop_id_dest}
					 */

					StringTokenizer str = new StringTokenizer(response, " ");
					str.nextToken();
					long a = System.currentTimeMillis();
					String src = str.nextToken();
					String dest = str.nextToken();
					LinkedList<Edge> path = Graph.getGraph().getShortestPath(
							Graph.getGraph().getNodebyID(src),
							Graph.getGraph().getNodebyID(dest));
					long b = System.currentTimeMillis();
					Logger.getLogger("BASE").info("path found  time : " + -(a - b) / 1000);
					toSend = JsonParse(path, Graph.getGraph().getNodebyID(src));
					int lines = 0;
					for (char data : toSend.toCharArray()) {
						if (data == '\n' || data == '\r')
							lines++;
					}
					WRITE(String.valueOf(lines));
					WRITE(toSend);

				} else if (response.toUpperCase().startsWith("CLOSE")) {
					toSend = "Client disconnected !";
					closeConnection = true;
					WRITE(toSend);
				} else if (response.toUpperCase().startsWith("CONN")) {
					StringTokenizer str = new StringTokenizer(response, " ");
					str.nextToken();

					String username = str.nextToken();
					String password = str.nextToken();
					boolean result = DAOFactory.getUserDAO().find(new User(username,password));
					
					if(result) {
						Logger.getLogger("SERVER").info("User {"+username+","+password+"} connected as "+ remote.getAddress().getHostAddress());
					}else {
						Logger.getLogger("SERVER").info("User {"+username+","+password+"} failed ip : "+ remote.getAddress().getHostAddress());

					}
					
					WRITE(String.valueOf(result));
				} else if (response.toUpperCase().startsWith("GETSTOPS")) {

					String stop_names = "";
					String Strop_id = "";

					Collection<Node> c = Graph.getGraph().getNodes().values();
					for (Node n : c) {
						stop_names += n.getStop().getName() + "#";
						Strop_id += n.getStop().getStop_id() + "#";
					}


					WRITE(stop_names);
					Logger.getLogger("BASE").info(stop_names);
					WRITE(Strop_id);
					Logger.getLogger("BASE").info(Strop_id);


				} else {
					toSend = "UNKOWN Command ";
					WRITE(toSend);
				}

				if (closeConnection) {
					buffWriter.close();
					bos.close();
					reader.close();
					client.close();
					buffReader.close();
					break;
				}

			} catch (SocketException e) {
				Logger.getLogger("SERVER").info("Connection interupted !!");
				break;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public static String JsonParse(LinkedList<Edge> path, Node src) throws IOException {
		List<Stop> stops = new LinkedList<>();
		if(path != null)
			stops = Edge.edgesToStops(path);

		StringWriter stringWriter = new StringWriter();
		JsonWriter jsonWriter = new JsonWriter(stringWriter);
		jsonWriter.setIndent("        ");

		jsonWriter.beginObject();

		jsonWriter.name("path");
		jsonWriter.beginArray();

		for (Stop stop : stops) {
			jsonWriter.beginObject();
			jsonWriter.name("lat").value(stop.getLat());
			jsonWriter.name("lon").value(stop.getLon());
			jsonWriter.name("desc").value(stop.getName() + " " + stop.getDesc());
			jsonWriter.endObject();
		}
		
		if(path == null) {
			jsonWriter.beginObject();
			jsonWriter.name("lat").value(src.getStop().getLat());
			jsonWriter.name("lon").value(src.getStop().getLon());
			jsonWriter.name("desc").value(src.getStop().getName() + " " + src.getStop().getDesc());
			jsonWriter.endObject();
		}
		
		jsonWriter.endArray();
		jsonWriter.endObject();
		jsonWriter.close();

		return stringWriter.toString();

	}


	private String read(int i, Boolean isconfirmation) throws IOException {

		String response = buffReader.readLine();

		Logger.getLogger("SERVER").info("\tdata : " + response);

		return response;
	}

	private String READ() throws IOException {
		String data = read(-1, false);

		return data;
	}

	private void WRITE(String str) throws IOException {

		buffWriter.write(str);
		buffWriter.newLine();
		buffWriter.flush();

	}

}
