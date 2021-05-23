package ma.myway.client.network;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Logger;

import com.google.gson.JsonElement;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import ma.myway.client.ui.Main;
import ma.myway.graph.data.Agency;
import ma.myway.graph.data.CalendarExpComp;
import ma.myway.graph.data.ServiceComp;
import ma.myway.graph.data.Stop;
import ma.myway.graph.data.Stop_Trip;
import ma.myway.graph.data.Transfert;
import ma.myway.graph.data.TripsComp;

public class Client {

	private static Socket client = null;

//	private static PrintWriter writer = null;
//	private static BufferedOutputStream bos = null;
//	private static BufferedWriter buffWriter = null;
	private static ObjectOutputStream objectOutputStream = null;

//	private static BufferedInputStream reader = null;
//	private static BufferedReader buffReader = null;
	private static ObjectInputStream objectInputStream = null;

	public Client(String host, int port) {
		try {
			client = new Socket(host, port);
//			reader = new BufferedInputStream(client.getInputStream());
//			buffReader = new BufferedReader(new InputStreamReader(reader, StandardCharsets.UTF_8));

//			writer = new PrintWriter(client.getOutputStream(), true);
//			bos = new BufferedOutputStream(client.getOutputStream());
//			buffWriter = new BufferedWriter(new OutputStreamWriter(bos, StandardCharsets.UTF_8));

			objectInputStream = new ObjectInputStream(client.getInputStream());
			objectOutputStream = new ObjectOutputStream(client.getOutputStream());

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
			stopnames = (String) READ();
			System.out.println("stopnames");
			Logger.getLogger("CLIENT").info(stopnames);

			stopid = (String) READ();
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

	public static String chem(String src, String dep) {

		String path = null;
		try {
			WRITE("CHEM " + src + " " + dep);

			path = (String) READ();

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

	/* AGENCY */
	public static boolean addAgency(Agency agency) {

		try {
			WRITE("addAgency");
			objectOutputStream.writeObject(agency);
			return (boolean) READ();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	public static boolean editAgency(Agency agency) {

		try {
			WRITE("editAgency");
			objectOutputStream.writeObject(agency);

			return (boolean) READ();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	public static boolean removeAgency(Agency agency) {

		try {
			WRITE("removeAgency");
			objectOutputStream.writeObject(agency);
			return (boolean) READ();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	public static Set<Agency> showAgency() {
		Set<Agency> lst = null;
		try {
			WRITE("showAgency");
			lst = (Set<Agency>) READ();
			return lst;

		} catch (IOException e) {
			e.printStackTrace();
		}

		return lst;
	}

	/* STOPS */
	public static boolean addStops(Stop stop) {

		try {
			WRITE("addStops");
			objectOutputStream.writeObject(stop);

			return (boolean) READ();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	public static boolean editStops(Stop stop) {

		try {
			WRITE("editStops");
			objectOutputStream.writeObject(stop);

			return (boolean) READ();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	public static boolean removeStops(Stop stop) {

		try {
			WRITE("removeStops");

			return (boolean) READ();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	public static Set<Stop> showStops() {
		Set<Stop> lst = null;
		try {
			WRITE("showStops");
			lst = (Set<Stop>) READ();
			return lst;

		} catch (IOException e) {
			e.printStackTrace();
		}

		return lst;
	}

	/* TRANSFERS */
	public static boolean addTransfers(Transfert trans) {

		try {
			WRITE("addTransfert");
			objectOutputStream.writeObject(trans);

			return (boolean) READ();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	public static boolean editTransfers(Transfert trans) {

		try {
			WRITE("editTransfert");
			objectOutputStream.writeObject(trans);

			return (boolean) READ();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	public static boolean removeTransfers(Transfert trans) {

		try {
			WRITE("removeTransfert");

			return (boolean) READ();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	public static Set<Transfert> showTransfers() {
		Set<Transfert> lst = null;
		try {
			WRITE("showTransfert");
			lst = (Set<Transfert>) READ();
			return lst;

		} catch (IOException e) {
			e.printStackTrace();
		}

		return lst;
	}
	
	/* StopTrip / StopTimes */
	public static boolean addStopTimes(Stop_Trip stoptrip) {

		try {
			WRITE("addStopTimes");
			objectOutputStream.writeObject(stoptrip);

			return (boolean) READ();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	public static boolean editStopTimes(Stop_Trip stoptrip) {

		try {
			WRITE("editStopTimes");
			objectOutputStream.writeObject(stoptrip);

			return (boolean) READ();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	public static boolean removeStopTimes(Stop_Trip stoptrip) {

		try {
			WRITE("removeStopTimes");

			return (boolean) READ();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}
	
	public static Set<Stop_Trip> showStopTimes() {
		Set<Stop_Trip> lst = null;
		try {
			WRITE("showStopTimes");
			lst = (Set<Stop_Trip>) READ();
			return lst;

		} catch (IOException e) {
			e.printStackTrace();
		}

		return lst;
	}
	
	/* Calendar / Services */
	public static boolean addCalendar(ServiceComp cal) {

		try {
			WRITE("addCalendar");
			objectOutputStream.writeObject(cal);

			return (boolean) READ();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	public static boolean editCalendar(ServiceComp cal) {

		try {
			WRITE("editCalendar");
			objectOutputStream.writeObject(cal);

			return (boolean) READ();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	public static boolean removeCalendar(String id) {

		try {
			WRITE("removeCalendar");

			return (boolean) READ();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}
	
	public static Set<ServiceComp> showCalendar() {
		Set<ServiceComp> lst = null;
		try {
			WRITE("showCalendar");
			lst = (Set<ServiceComp>) READ();
			return lst;

		} catch (IOException e) {
			e.printStackTrace();
		}

		return lst;
	}
	
	/* TRIPS */
	
	public static boolean addTrips(TripsComp trip) {

		try {
			WRITE("addTrips");
			objectOutputStream.writeObject(trip);

			return (boolean) READ();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	public static boolean editTrips(TripsComp trip) {

		try {
			WRITE("editTrips");
			objectOutputStream.writeObject(trip);

			return (boolean) READ();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	public static boolean removeTrips(TripsComp trip) {

		try {
			WRITE("removeTrips");

			return (boolean) READ();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}
	
	public static Set<TripsComp> showTrips() {
		Set<TripsComp> lst = null;
		try {
			WRITE("showTrips");
			lst = (Set<TripsComp>) READ();
			return lst;

		} catch (IOException e) {
			e.printStackTrace();
		}

		return lst;
	}
	
	/* CALENDARDATES / CalendarExpComp */
	
	public static boolean addCalendarDates(CalendarExpComp cald) {

		try {
			WRITE("addCalendarDates");
			objectOutputStream.writeObject(cald);

			return (boolean) READ();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	public static boolean editCalendarDates(CalendarExpComp cald) {

		try {
			WRITE("editCalendarDates");
			objectOutputStream.writeObject(cald);

			return (boolean) READ();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	public static boolean removeCalendarDates(String id,Date date) {

		try {
			WRITE("removeCalendarDates");

			return (boolean) READ();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}
	
	public static Set<CalendarExpComp> showCalendarDates() {
		Set<CalendarExpComp> lst = null;
		try {
			WRITE("showCalendarDates");
			lst = (Set<CalendarExpComp>) READ();
			return lst;

		} catch (IOException e) {
			e.printStackTrace();
		}

		return lst;
	}


	public static boolean conn(String username, String password) {
		try {
			WRITE("CONN " + username + " " + password);

			return (boolean) READ();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

//	private static String read(Boolean isjson) throws IOException {
//		String response = "";
//		if (!isjson) {
//			response = buffReader.readLine();
//		} else {
//			int lines = Integer.parseInt(buffReader.readLine());
//			for (int i = 0; i <= lines; i++) {
//				response += buffReader.readLine() + "\n";
//			}
//		}
//		Logger.getLogger("CLIENT").info("\tdata : " + response);
//
//		return response;
//	}

	private static void WRITE(String str) throws IOException {
		objectOutputStream.writeObject(str);
	}

	private static Object READ() throws IOException {
		Object data = null;
		try {
			data = objectInputStream.readObject();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}

		return data;
	}
//	private static String READ(boolean isjson) throws IOException {
////		String data = read(isjson);
//		String data = TestREAD (isjson);
//		
//		return data;
//	}
//	

//	private static String TestREAD(boolean isjson) throws IOException {
//		String data = null;
//		try {
//			data = (String) objectInputStream.readObject();
//		} catch (ClassNotFoundException | IOException e) {
//			e.printStackTrace();
//		}
//
//		return data;
//	}

//	private static void WRITE(String str) throws IOException {
//		buffWriter.write(str);
//		buffWriter.newLine();
//		buffWriter.flush();
//		objectOutputStream.writeObject(str);
//	}

//	private static void Testwrite(String str) throws IOException {
////		buffWriter.write(str);
////		buffWriter.newLine();
////		buffWriter.flush();
//		objectOutputStream.writeObject(str);
//	}

	/*
	 * private static String read(int i) throws IOException { String response = "";
	 * int stream; byte[] b;
	 * 
	 * if (i < 0) { b = new byte[1024]; } else { b = new byte[i]; } stream =
	 * reader.read(b); response = new String(b, 0, stream);
	 * 
	 * writer.write("received"); writer.flush();
	 * 
	 * return response; }
	 */

	public static void close() {

		try {
			WRITE("CLOSE");

			Logger.getLogger("CLIENT").info((String) READ());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
//			reader.close();
//			writer.close();
			objectInputStream.close();
			objectOutputStream.close();
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
