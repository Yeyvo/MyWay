package ma.myway.network;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.nio.Buffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Logger;

import com.google.gson.stream.JsonWriter;
import ma.myway.MainClass;
import ma.myway.dao.DAOFactory;
import ma.myway.graph.Edge;
import ma.myway.graph.Graph;
import ma.myway.graph.Node;
import ma.myway.graph.data.Stop;
import ma.myway.users.User;

public class ClientProcessor implements Runnable {

	private Socket client = null;
	private PrintWriter writer = null;
	private BufferedInputStream reader = null;

	public ClientProcessor(Socket client) {
		this.client = client;
	}

	@Override
	public void run() {
		Logger.getLogger("SERVER").info("Starting the processing of the client connection " + client.isClosed());
		boolean closeConnection = false;
		while (!client.isClosed()) {
			try {
				//writer = new PrintWriter(client.getOutputStream());
				writer = new PrintWriter(new OutputStreamWriter(
					    client.getOutputStream(), StandardCharsets.UTF_8), true);
				reader = new BufferedInputStream(client.getInputStream());
				String response = read();
				InetSocketAddress remote = (InetSocketAddress) client.getRemoteSocketAddress();
				String debug = "Thread : " + Thread.currentThread().getName() + ". ";
				debug += "Client ADRESSE : " + remote.getAddress().getHostAddress() + ".";
				debug += " Port : " + remote.getPort() + ".\n";
				debug += "\t -> Commande : *" + response + "*\n";
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
					
					write(String.valueOf(toSend.getBytes("UTF-8").length));
					
					write(toSend);
				} else if (response.toUpperCase().startsWith("CLOSE")) {
					toSend = "Client disconnected !";
					closeConnection = true;
					write(toSend);
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
					
					write(String.valueOf(result));
				} else if (response.toUpperCase().startsWith("GETSTOPS")) {

					String stop_names = "";
					String Strop_id = "";
//				    Iterator it = Graph.getGraph().getNodes().entrySet().iterator();
//				    while (it.hasNext()) {
//				        Map.Entry pair = (Map.Entry)it.next();
//				        stop_names += ((Node) pair.getValue()).getStop().getName();
//				        Strop_id += ((Node) pair.getValue()).getStop().getName();
//				       // System.out.println(pair.getKey() + " = " + pair.getValue());
//				        it.remove(); // avoids a ConcurrentModificationException
//				    }

					Collection<Node> c = Graph.getGraph().getNodes().values();
					for (Node n : c) {
						stop_names += n.getStop().getName() + "#";
						Strop_id += n.getStop().getStop_id() + "#";
					}
					Logger.getLogger("SERVER").info(stop_names);
					Logger.getLogger("SERVER").info(Strop_id);
					
					write( String.valueOf(stop_names.getBytes("UTF-8").length));
					write(stop_names);

					write(String.valueOf(Strop_id.getBytes("UTF-8").length));
					write(Strop_id);

				} else {
					toSend = "UNKOWN Command ";
					write(toSend);
				}

				if (closeConnection) {
					writer.close();
					reader.close();
					client.close();
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

	private String read() throws IOException {
		String response = "";
		int stream;
		byte[] b = new byte[100];
		stream = reader.read(b);
		response = new String(b, 0, stream);
		return response;
	}

	
	private void write(String str) throws IOException {
		writer.write(str);
		writer.flush();
		Logger.getLogger("SERVER").info(read());
	}

}
