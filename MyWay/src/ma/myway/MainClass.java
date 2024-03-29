package ma.myway;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import ma.myway.config.Config;
import ma.myway.dao.BddConnection;
import ma.myway.dao.DAOFactory;
import ma.myway.graph.Edge;
import ma.myway.graph.FibonacciHeap;
import ma.myway.graph.Graph;
import ma.myway.graph.Node;
import ma.myway.graph.data.CalendarExp;
import ma.myway.graph.data.Service;
import ma.myway.graph.data.Stop;
import ma.myway.graph.data.Stop_Trip;
import ma.myway.graph.data.Transfert;
import ma.myway.network.Server;

public class MainClass {

	private static int initlenEdges = 14666666;
	private static int initlenNode = 34778;
	// public static Graph g = null;

	public static void main(String[] args) {

		long StartTime = System.currentTimeMillis();

		LoadLogger();
		Logger.getLogger("BASE").info("Config file loading Started ! ");
		Config.load("config.json");
		Logger.getLogger("BASE")
				.info("Config file loading Finished !  time " + (System.currentTimeMillis() - StartTime) / 1000);
		BddConnection.getInstance();
		Graph g = null;
		Graph.setGraph(g);
		File f = new File("graph.bin");
		if (!f.exists() && !f.isDirectory()) {
			g = graphGeneration(StartTime);
			Graph.saveGraph(g);
			
		} else if (f.exists() && !f.isDirectory()) {
			System.out.println("Exist");
			g = Graph.getGraph();
		}
		long endTime = System.currentTimeMillis();
		double timeElapsed = endTime - StartTime;
		Logger.getLogger("BASE").info("Execution time in seconds   : " + timeElapsed / 1000 + " seconde ");

		Server server = new Server(10);
		server.open();


		// server.close();

	}

	private static void LoadLogger() {
		Logger loggerBASE = Logger.getLogger("BASE");
		
		try {
			FileHandler fh = new FileHandler("logs_BASE.log");
			loggerBASE.addHandler(fh);
			fh.setFormatter(new Formatter() {

				@Override
				public String format(LogRecord record) {
					SimpleDateFormat logTime = new SimpleDateFormat(" MM-dd-yyyy HH:mm:ss");
					Calendar cal = new GregorianCalendar();
					cal.setTimeInMillis(record.getMillis());
					return "BASE : " + record.getLevel() + logTime.format(cal.getTime()) + " || "
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

		Logger loggerServer = Logger.getLogger("SERVER");
		try {
			FileHandler fh = new FileHandler("Logs_Server.log");
			loggerServer.addHandler(fh);
			fh.setFormatter(new Formatter() {

				@Override
				public String format(LogRecord record) {
					SimpleDateFormat logTime = new SimpleDateFormat(" MM-dd-yyyy HH:mm:ss");
					Calendar cal = new GregorianCalendar();
					cal.setTimeInMillis(record.getMillis());
					return "SERVER : " + record.getLevel() + logTime.format(cal.getTime()) + " || "
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
	}

	public static Graph graphGeneration(long StartTime) {
		long a, b;

		HashMap<String, List<Edge>> edges = new HashMap<String, List<Edge>>(initlenEdges);
		HashMap<String, Node> node = new HashMap<>(initlenNode);
		Map<String, Service> services = new HashMap<>();
		Graph g = new Graph(node, edges, services);

		Logger.getLogger("BASE").info("Node Generation Started ! ");
		a = System.currentTimeMillis();

		Set<Stop> stops = DAOFactory.getStopDAO().all();
		long i = 0;
		for (Stop stop : stops) {
			g.addNode(new Node(stop));
			Logger.getLogger("BASE").info("Node : " + stop.getStop_id());
			i++;
		}
		stops = null;
		b = System.currentTimeMillis();
		Logger.getLogger("BASE").info("Node Generation Finished ! (" + i + ")  time : " + (b - a) / 1000);

		Logger.getLogger("BASE").info("Service data retrieving started ! ");
		services = DAOFactory.getServiceDAO().allMap();
		HashMap<String, CalendarExp> calExp = DAOFactory.getCalendarExpDAO().allMap();
		merge(calExp, services);
		g.setServices(services);
		calExp = null;
		a = System.currentTimeMillis();
		Logger.getLogger("BASE").info("Service Data Retrieving Finished !  time :  " + (a - b) / 1000);

		Logger.getLogger("BASE").info("StopTrips Data Retrieving started ! ");
		List<Stop_Trip> st = DAOFactory.getStopTripDAO().allList();
		a = System.currentTimeMillis();
		Logger.getLogger("BASE").info("StopTrips Data Retrieving Ended !   time :  " + (a - b) / 1000);
		Logger.getLogger("BASE").info("Edge Generation from Trips started ! ");
		i = 0;
		for (int j = 0; j < st.size(); j++) {
			Stop_Trip next = Stop_Trip.getNextStop_sequence(st, st.get(j).getTrip_id(), st.get(j).getStop_sequence(),
					j);
			if (next != null) {
				Edge e = new Edge(node.get(st.get(j).getStop_id()), node.get(next.getStop_id()),
						Stop_Trip.calculateWeight(st.get(j), next), st.get(j).getTrip_id());
				g.addEdge(e);
				Logger.getLogger("BASE")
						.info("Edge Trip (" + ++i + ") : src : " + st.get(j).getStop_id() + " dst : "
								+ next.getStop_id() + " Trip_id : " + st.get(j).getTrip_id() + " Stop_sequence : "
								+ st.get(j).getStop_sequence() + "   poid : " + e.getWeight());
			}
		}
		st = null;
		b = System.currentTimeMillis();
		Logger.getLogger("BASE").info("Edge Generation from Trips Finished ! (" + i + ")  time : " + (b - a) / 1000);

		Logger.getLogger("BASE").info("Transferts Data Retrieving started ! ");
		Set<Transfert> trans = DAOFactory.getTransfertDAO().all();
		a = System.currentTimeMillis();
		Logger.getLogger("BASE").info("Transferts Data Retrieving Finished !  time :  " + (a - b) / 1000);

		Logger.getLogger("BASE").info("Edge Generation from Transferts started ! ");
		i = 0;
		for (Transfert tr : trans) {

			Node src, dst;
			if (node.get(tr.getSrc_stop_id()) == null) {
				src = new Node(new Stop(tr.getSrc_stop_id()));
				node.put(tr.getSrc_stop_id(), src);
			}
			if (node.get(tr.getDest_stop_id()) == null) {
				dst = new Node(new Stop(tr.getDest_stop_id()));
				node.put(tr.getDest_stop_id(), dst);
			}

			g.addEdge(new Edge(node.get(tr.getSrc_stop_id()), node.get(tr.getDest_stop_id()), tr.getTransfert_time(),
					"0", tr.getTransfert_type()));
			Logger.getLogger("BASE").info("Edge Transferts: src : " + tr.getSrc_stop_id() + " dst : "
					+ tr.getDest_stop_id() + " Trip_id : 0  Transfert Time :  " + tr.getTransfert_time());
			i++;
		}
		trans = null;
		b = System.currentTimeMillis();
		Logger.getLogger("BASE")
				.info("Edge from Transferts Generation Finished ! (" + i + ")  time :  " + (b - a) / 1000);
		
		Graph.setGraph(g);

		return g;
	}

	private static void merge(HashMap<String, CalendarExp> calExp, Map<String, Service> services) {

		for (String calExpKey : calExp.keySet()) {

			services.get(calExp.get(calExpKey).getService_id()).setAdded(calExp.get(calExpKey).getAdded());
			services.get(calExp.get(calExpKey).getService_id()).setRemoved(calExp.get(calExpKey).getRemoved());
			services.get(calExp.get(calExpKey).getService_id()).toStr();
		}

	}

	public static void loggerInit() {

	}

	public static void testDijkstra2() {
		Stop sa, sb, sc, sd;
		sa = new Stop("a");
		sb = new Stop("b");
		sc = new Stop("c");
		sd = new Stop("d");

		Node a, b, c, d;
		a = new Node(sa);
		b = new Node(sb);
		c = new Node(sc);
		d = new Node(sd);

		Edge aa, bb, cc, dd;
		aa = new Edge(a, a, 0, "aa");
		bb = new Edge(b, b, 0, "bb");
		cc = new Edge(c, c, 0, "cc");
		dd = new Edge(d, d, 0, "dd");

		Edge ab1, ab2, ac1, ac2, ad1, ad2;
		ab1 = new Edge(a, b, 2, "ab1");
		ab2 = new Edge(a, b, 1, "ab2");
		ac1 = new Edge(a, c, 3, "ac1");
		ac2 = new Edge(a, c, 0, "ac2");
		ad1 = new Edge(a, d, 7, "ad1");
		ad2 = new Edge(a, d, 3, "ad2");

		Edge ba1, ba2, bc1, bc2, bd1, bd2;
		ba1 = new Edge(b, a, 1, "ba1");
		ba2 = new Edge(b, a, 0, "ba2");
		bc1 = new Edge(b, c, 6, "bc1");
		bc2 = new Edge(b, c, 4, "bc2");
		bd1 = new Edge(b, d, 2, "bd1");
		bd2 = new Edge(b, d, 3, "bd2");

		Edge ca1, ca2, cb1, cb2, cd1, cd2;
		ca1 = new Edge(c, a, 3, "ca1");
		ca2 = new Edge(c, a, 5, "ca2");
		cb1 = new Edge(c, b, 2, "cb1");
		cb2 = new Edge(c, b, 2, "cb2");
		cd1 = new Edge(c, d, 1, "cd1");
		cd2 = new Edge(c, d, 4, "cd2");

		Edge da1, da2, db1, db2, dc1, dc2;
		da1 = new Edge(d, a, 3, "da1");
		da2 = new Edge(d, a, 5, "da2");
		db1 = new Edge(d, b, 1, "db1");
		db2 = new Edge(d, b, 1, "db2");
		dc1 = new Edge(d, c, 0, "dc1");
		dc2 = new Edge(d, c, 4, "dc2");

		HashMap<String, Node> nodes = new HashMap<String, Node>();
		HashMap<String, List<Edge>> edges = new HashMap<String, List<Edge>>();

		nodes.put("a", a);
		nodes.put("b", b);
		nodes.put("c", c);
		nodes.put("d", d);

		edges.put("a", new ArrayList<Edge>(Arrays.asList(aa, ab1, ab2, ac1, ac2, ad1, ad2)));
		edges.put("b", new ArrayList<Edge>(Arrays.asList(bb, ba1, ba2, bc1, bc2, bd1, bd2)));
		edges.put("c", new ArrayList<Edge>(Arrays.asList(cc, ca1, ca2, cb1, cb2, cd1, cd2)));
		edges.put("d", new ArrayList<Edge>(Arrays.asList(dd, da1, da2, db1, db2, dc1, dc2)));

		Graph g = new Graph(nodes, edges);

		LinkedList<Edge> path = g.getShortestPath(b, c);

		System.out.println("Shortest path from " + a.getStop().getStop_id() + " to " + c.getStop().getStop_id() + ": ");

		for (Edge edge : path) {
			System.out.println(edge.getTrip_id());
		}
	}

	public static void testFibonecciHeap() {
		FibonacciHeap<Node> obj = new FibonacciHeap<Node>();
		Node n7, n26, n30, n39, n10;
		n7 = new Node(new Stop("7"));
		n26 = new Node(new Stop("26"));
		n30 = new Node(new Stop("30"));
		n39 = new Node(new Stop("39"));
		n10 = new Node(new Stop("10"));

		obj.enqueue(n7, 7);
		obj.enqueue(n26, 26);
		FibonacciHeap.Entry<Node> n3030 = obj.enqueue(n30, 30);
		obj.enqueue(n39, 39);
		obj.enqueue(n10, 10);

		System.out.println(obj.dequeueMin().mElem.getStop().getStop_id());
		System.out.println(obj.dequeueMin().mElem.getStop().getStop_id());
		obj.decreaseKey(n3030, 11);
		System.out.println(obj.dequeueMin().mElem.getStop().getStop_id());
		System.out.println(obj.dequeueMin().mElem.getStop().getStop_id());
		System.out.println(obj.dequeueMin().mElem.getStop().getStop_id());
		System.out.println(obj.dequeueMin().mElem.getStop().getStop_id());
	}
}
