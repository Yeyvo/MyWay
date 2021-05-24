package ma.myway.network;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Logger;

import com.google.gson.stream.JsonWriter;

import ma.myway.dao.DAOFactory;
import ma.myway.graph.Edge;
import ma.myway.graph.Graph;
import ma.myway.graph.Node;
import ma.myway.graph.data.Agency;
import ma.myway.graph.data.CalendarExpComp;
import ma.myway.graph.data.ServiceComp;
import ma.myway.graph.data.Stop;
import ma.myway.graph.data.Stop_Trip;
import ma.myway.graph.data.Transfert;
import ma.myway.graph.data.TripsComp;
import ma.myway.users.User;

public class ClientProcessor implements Runnable {

	private Socket client = null;

	private BufferedOutputStream bos = null;
	private BufferedWriter buffWriter = null;
	private ObjectOutputStream objectOutputStream = null;

	private BufferedInputStream reader = null;
	private BufferedReader buffReader = null;
	private ObjectInputStream objectInputStream = null;

	public ClientProcessor(Socket client) {
		this.client = client;
	}

	@Override
	public void run() {
		Logger.getLogger("SERVER").info("Starting the processing of the client connection " + client.isClosed());
		boolean closeConnection = false;
		try {
			objectOutputStream = new ObjectOutputStream(client.getOutputStream());
			objectInputStream = new ObjectInputStream(client.getInputStream());

		} catch (IOException e1) {
			e1.printStackTrace();
		}

		while (!client.isClosed()) {
			try {

//				bos = new BufferedOutputStream(client.getOutputStream());
//				buffWriter = new BufferedWriter(new OutputStreamWriter(bos, StandardCharsets.UTF_8));
//				objectOutputStream = new ObjectOutputStream(client.getOutputStream());

//				reader = new BufferedInputStream(client.getInputStream());
//				buffReader = new BufferedReader(new InputStreamReader(reader, StandardCharsets.UTF_8));
//				objectInputStream = new ObjectInputStream(client.getInputStream());
				String read = READ();
				String response = read == null ? "" : read;

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
					LinkedList<Edge> path = Graph.getGraph().getShortestPath(Graph.getGraph().getNodebyID(src),
							Graph.getGraph().getNodebyID(dest));
					long b = System.currentTimeMillis();
					Logger.getLogger("BASE").info("path found  time : " + -(a - b) / 1000);
					toSend = JsonParse(path, Graph.getGraph().getNodebyID(src));
//					int lines = 0;
//					for (char data : toSend.toCharArray()) {
//						if (data == '\n' || data == '\r')
//							lines++;
//					}
//					WRITE(String.valueOf(lines));
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
					boolean resultadmin = DAOFactory.getUserDAO().find(new User(username, password, "admin"));
					boolean resultuser = DAOFactory.getUserDAO().find(new User(username, password, "user"));
					if (resultadmin) {
						WRITE(resultadmin);
						WRITE("admin");
					} else if (resultuser) {
						WRITE(resultuser);
						WRITE("user");
					} else {
						WRITE(false);
					}
					if (resultadmin) {
						Logger.getLogger("SERVER").info("User {" + username + "," + password + "} (admin) connected as "
								+ remote.getAddress().getHostAddress());
					} else if (resultuser) {
					
						Logger.getLogger("SERVER").info("User {" + username + "," + password + "} (user) connected as "
								+ remote.getAddress().getHostAddress());
					} else {
						Logger.getLogger("SERVER").info("User {" + username + "," + password + "} failed ip : "
								+ remote.getAddress().getHostAddress());
					}

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

				}

				/* AGENCY */
				else if (response.toUpperCase().startsWith("ADDAGENCY")) {
					boolean res = false;
					try {
						Agency data = (Agency) objectInputStream.readObject();
						res = DAOFactory.getAgencyDAO().create(data);
						System.out.println("->>" + res);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					System.out.println(res);
					WRITE(res);

				} else if (response.toUpperCase().startsWith("EDITAGENCY")) {
					boolean res = false;
					try {
						Agency data = (Agency) objectInputStream.readObject();
						res = DAOFactory.getAgencyDAO().update(data, data);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					WRITE(res);

				} else if (response.toUpperCase().startsWith("REMOVEAGENCY")) {
					boolean res = false;
					try {
						Agency data = (Agency) objectInputStream.readObject();
						res = DAOFactory.getAgencyDAO().delete(data);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					WRITE(res);

				} else if (response.toUpperCase().startsWith("SHOWAGENCY")) {
					Set<Agency> data = DAOFactory.getAgencyDAO().all();
					WRITE(data);
				}

				/* STOPS */
				else if (response.toUpperCase().startsWith("ADDSTOPS")) {
					boolean res = false;
					try {
						Stop data = (Stop) objectInputStream.readObject();
						res = DAOFactory.getStopDAO().create(data);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					WRITE(res);

				} else if (response.toUpperCase().startsWith("EDITSTOPS")) {
					boolean res = false;
					try {
						Stop data = (Stop) objectInputStream.readObject();
						res = DAOFactory.getStopDAO().update(data, data);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					WRITE(res);

				} else if (response.toUpperCase().startsWith("REMOVESTOPS")) {
					boolean res = false;
					try {
						Stop data = (Stop) objectInputStream.readObject();
						res = DAOFactory.getStopDAO().delete(data);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					WRITE(res);
				} else if (response.toUpperCase().startsWith("SHOWSTOPS")) {
					Set<Stop> data = DAOFactory.getStopDAO().all();
					WRITE(data);
				}
				/* TRANSFERS */
				else if (response.toUpperCase().startsWith("ADDTRANSFERT")) {
					boolean res = false;
					try {
						Transfert data = (Transfert) objectInputStream.readObject();
						res = DAOFactory.getTransfertDAO().create(data);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					WRITE(res);

				} else if (response.toUpperCase().startsWith("EDITTRANSFERT")) {
					boolean res = false;
					try {
						Transfert data = (Transfert) objectInputStream.readObject();
						res = DAOFactory.getTransfertDAO().update(data, data);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					WRITE(res);

				} else if (response.toUpperCase().startsWith("REMOVETRANSFERT")) {
					boolean res = false;
					try {
						Transfert data = (Transfert) objectInputStream.readObject();
						res = DAOFactory.getTransfertDAO().delete(data);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					WRITE(res);
				} else if (response.toUpperCase().startsWith("SHOWTRANSFERT")) {
					Set<Transfert> data = DAOFactory.getTransfertDAO().all();
					WRITE(data);
				}

				/* StopTrip / StopTimes */
				else if (response.toUpperCase().startsWith("ADDSTOPTIMES")) {
					boolean res = false;
					try {
						Stop_Trip data = (Stop_Trip) objectInputStream.readObject();
						res = DAOFactory.getStopTripDAO().create(data);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					WRITE(res);

				} else if (response.toUpperCase().startsWith("EDITSTOPTIMES")) {
					boolean res = false;
					try {
						Stop_Trip data = (Stop_Trip) objectInputStream.readObject();
						res = DAOFactory.getStopTripDAO().update(data, data);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					WRITE(res);

				} else if (response.toUpperCase().startsWith("REMOVESTOPTIMES")) {
					boolean res = false;
					try {
						Stop_Trip data = (Stop_Trip) objectInputStream.readObject();
						res = DAOFactory.getStopTripDAO().delete(data);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					WRITE(res);
				} else if (response.toUpperCase().startsWith("SHOWSTOPTIMES")) {
					Set<Stop_Trip> data = DAOFactory.getStopTripDAO().all();
					WRITE(data);
				}

				/* Calendar / Services */
				else if (response.toUpperCase().startsWith("ADDACALENDAR")) {
					boolean res = false;
					try {
						ServiceComp data = (ServiceComp) objectInputStream.readObject();
						res = DAOFactory.getServiceDAO().createComp(data);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					WRITE(res);

				} else if (response.toUpperCase().startsWith("EDITACALENDAR")) {
					boolean res = false;
					try {
						ServiceComp data = (ServiceComp) objectInputStream.readObject();
						res = DAOFactory.getServiceDAO().updateComp(data, data);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					WRITE(res);

				} else if (response.toUpperCase().startsWith("REMOVEACALENDAR")) {
					boolean res = false;
					StringTokenizer str = new StringTokenizer(response, " ");
					str.nextToken();
					ServiceComp data = new ServiceComp(str.nextToken(), 0, 0, 0, 0, 0, 0, 0, null, null);
					res = DAOFactory.getServiceDAO().deleteComp(data);
					WRITE(res);
				} else if (response.toUpperCase().startsWith("SHOWACALENDAR")) {
					Set<ServiceComp> data = DAOFactory.getServiceDAO().allSet();
					WRITE(data);
				}

				/* TRIPS */
				else if (response.toUpperCase().startsWith("ADDTRIPS")) {
					boolean res = false;
					try {
						TripsComp data = (TripsComp) objectInputStream.readObject();
						res = DAOFactory.getRoutesDAO().createComp(data);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					WRITE(res);

				} else if (response.toUpperCase().startsWith("EDITTRIPS")) {
					boolean res = false;
					try {
						TripsComp data = (TripsComp) objectInputStream.readObject();
						res = DAOFactory.getRoutesDAO().updateComp(data, data);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					WRITE(res);

				} else if (response.toUpperCase().startsWith("REMOVETRIPS")) {
					boolean res = false;
					try {
						TripsComp data = (TripsComp) objectInputStream.readObject();
						res = DAOFactory.getRoutesDAO().deleteComp(data);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					WRITE(res);
				} else if (response.toUpperCase().startsWith("SHOWTRIPS")) {
					Set<TripsComp> data = DAOFactory.getRoutesDAO().allSet();
					WRITE(data);
				}

				/* CALENDARDATES / CalendarExpComp */
				else if (response.toUpperCase().startsWith("ADDCALENDARDATES")) {
					boolean res = false;
					try {
						CalendarExpComp data = (CalendarExpComp) objectInputStream.readObject();
						res = DAOFactory.getCalendarExpDAO().createComp(data);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					WRITE(res);

				} else if (response.toUpperCase().startsWith("EDITCALENDARDATES")) {
					boolean res = false;
					try {
						CalendarExpComp data = (CalendarExpComp) objectInputStream.readObject();
						res = DAOFactory.getCalendarExpDAO().updateComp(data, data);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					WRITE(res);

				} else if (response.toUpperCase().startsWith("REMOVECALENDARDATES")) {
					boolean res = false;
					StringTokenizer str = new StringTokenizer(response, " ");
					str.nextToken();

					res = DAOFactory.getCalendarExpDAO()
							.deleteComp(new CalendarExpComp(str.nextToken(), LocalDate.parse(str.nextToken()), 0));
					WRITE(res);
				} else if (response.toUpperCase().startsWith("SHOWCALENDARDATES")) {
					Set<CalendarExpComp> data = DAOFactory.getCalendarExpDAO().allSet();
					WRITE(data);
				}

				/* USERS */
				else if (response.toUpperCase().startsWith("ADDUSER")) {
					boolean res = false;
					try {
						User data = (User) objectInputStream.readObject();
						res = DAOFactory.getUserDAO().create(data);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					WRITE(res);

				} else if (response.toUpperCase().startsWith("EDITUSER")) {
					boolean res = false;
					try {
						User data = (User) objectInputStream.readObject();
						res = DAOFactory.getUserDAO().update(data, data);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					WRITE(res);

				} else if (response.toUpperCase().startsWith("REMOVEUSER")) {
					boolean res = false;
					try {
						User data = (User) objectInputStream.readObject();
						res = DAOFactory.getUserDAO().delete(data);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					WRITE(res);
				} else if (response.toUpperCase().startsWith("SHOWUSER")) {
					Set<User> data = DAOFactory.getUserDAO().all();
					WRITE(data);
				}

				else {
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
		if (path != null)
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

		if (path == null) {
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

//		String response = buffReader.readLine();
//		
//		Logger.getLogger("SERVER").info("\tdata : " + response);
//		
		String response = Testread(i, isconfirmation);

		return response;
	}

	private String Testread(int i, Boolean isconfirmation) throws IOException {

		String response = null;
		try {
			response = (String) objectInputStream.readObject();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}

		Logger.getLogger("SERVER").info("\tdata : " + response);

		return response;
	}

	private String READ() throws IOException {
//		String data = read(-1, false);
		String data = (String) Testread(-1, false);

		return data;
	}

	private void WRITE(Object str) throws IOException {
		objectOutputStream.writeObject(str);
	}
//	private void WRITE(String str) throws IOException {
//		
//		buffWriter.write(str);
//		buffWriter.newLine();
//		buffWriter.flush();
//
//	}

}
