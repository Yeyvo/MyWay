package ma.myway;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import ma.myway.config.Config;
import ma.myway.dao.BddConnection;
import ma.myway.dao.DAOFactory;
import ma.myway.graph.Edge;
import ma.myway.graph.Graph;
import ma.myway.graph.Node;
import ma.myway.graph.data.Stop;
import ma.myway.graph.data.Stop_Trip;
import ma.myway.graph.data.Transfert;
import ma.myway.graph.FibHeap;
public class MainClass {

	public static void main(String[] args) {
		/*
		long StartTime = System.currentTimeMillis();

		loggerInit();
	    graphGeneration();

	    long endTime = System.currentTimeMillis();
		double timeElapsed = endTime - StartTime;
		Logger.getLogger("MyLog").info("Execution time in seconds  :  List : " + timeElapsed/1000 +  " seconde ");
		 */
		//testDijkstra();
		testFibHeap();
	}

	public static Graph graphGeneration() {
		Config.load("config.json");
		BddConnection.getInstance();
		Logger.getLogger("MyLog").info("Node Generation Started ! ");
		HashMap<String, Node> node = new HashMap<>();
		Set<Stop> stops = DAOFactory.getStopDAO().all();
		long i =0;
		for (Stop stop : stops) {
			node.put(stop.getStop_id(), new Node(stop));
			Logger.getLogger("MyLog").info("Node : " + stop.getStop_id()); 
			i++;
		}
		Logger.getLogger("MyLog").info("Node Generation Finished ! ("+i+")");
		
		
		Logger.getLogger("MyLog").info("StopTrips Data Retrieving started ! ");
		Set<Edge> edges = new HashSet<Edge>();
		List<Stop_Trip> st = DAOFactory.getStopTripDAO().allList();
		Logger.getLogger("MyLog").info("StopTrips Data Retrieving Ended !  List");
		Logger.getLogger("MyLog").info("Edge Generation from Trips started ! ");
		i=0;
		for (int j =0; j<st.size(); j++ ) {
			Stop_Trip next = Stop_Trip.getNextStop_sequence(st, st.get(j).getTrip_id(),st.get(j).getStop_sequence(),j);
			if (next != null) {
				 edges.add(new Edge( node.get(st.get(j).getStop_id()),node.get(next.getStop_id()), 1, st.get(j).getTrip_id()));
				 Logger.getLogger("MyLog").info("Edge : src : " + st.get(j).getStop_id() +" dst : " + next.getStop_id() + " Trip_id : "+st.get(j).getTrip_id() + " Stop_sequence : " + st.get(j).getStop_sequence() ); 
				 i++;
			}
		}
		Logger.getLogger("MyLog").info("Edge Generation from Trips Finished ! ("+i+")");
		
		
		
		Logger.getLogger("MyLog").info("Transferts Data Retrieving started ! ");
		Set<Transfert> trans = DAOFactory.getTransfertDAO().all();
		Logger.getLogger("MyLog").info("Transferts Data Retrieving Finished ! ");
		Logger.getLogger("MyLog").info("Edge Generation from Transferts started ! ");
		i = 0;
		for (Transfert tr : trans) {
			edges.add(new Edge(node.get(tr.getSrc_stop_id()), node.get(tr.getDest_stop_id()),tr.getTransfert_time() , "0", tr.getTransfert_type()));
			i++;
		}
		Logger.getLogger("MyLog").info("Edge from Transferts Generation Finished ! ("+i+")");
		
		Graph g = new Graph(node, edges);
		
		
		return g;
	}
	
	public static void loggerInit() {
		Logger logger = Logger.getLogger("MyLog");  
	    FileHandler fh;  
	    try {  
	        fh = new FileHandler("logs.log");  
	        logger.addHandler(fh); 
	        fh.setFormatter(new Formatter() {
	            @Override
	            public String format(LogRecord record) {
	                SimpleDateFormat logTime = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
	                Calendar cal = new GregorianCalendar();
	                cal.setTimeInMillis(record.getMillis());
	                return record.getLevel()
	                        + logTime.format(cal.getTime())
	                        + " || "
	                        + record.getSourceClassName().substring(
	                                record.getSourceClassName().lastIndexOf(".")+1,
	                                record.getSourceClassName().length())
	                        + "."
	                        + record.getSourceMethodName()
	                        + "() : "
	                        + record.getMessage() + "\n";
	            }
	        });  
	    } catch (SecurityException e) {  
	        e.printStackTrace();  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    } 
	}

	public static void testDijkstra(){
		Stop sa, sb, sc;
		sa = new Stop("a");
		sb = new Stop("b");
		sc = new Stop("c");

		Node a, b, c;
		a = new Node(sa);
		b = new Node(sb);
		c = new Node(sc);

		Edge ab1, ab2, ab3,  ac, bc1, bc2;
		ab1 = new Edge(a, b, 5, "ab1");
		ab2 = new Edge(a, b, 1, "ab2");
		ac  = new Edge(a, c, 4, "ac");
		bc1 = new Edge(b, c, 1, "bc1");
		bc2 = new Edge(b, c, 3, "bc2");
		ab3 = new Edge(a, b, 0.5, "ab3");

		HashMap<String, Node> nodes = new HashMap<String, Node>();
		Set<Edge> edges = new HashSet<Edge>();

		nodes.put("a", a);
		nodes.put("b", b);
		nodes.put("c", c);

		edges.add(ab1);
		edges.add(ab2);
		edges.add(ac);
		edges.add(bc1);
		edges.add(bc2);
		edges.add(ab3);

		Graph g = new Graph(nodes, edges);

		LinkedList<Edge> path = g.getShortestPath(a, c);

		System.out.println("Shortest path from " + a.getStop().getStop_id() + " to " + c.getStop().getStop_id() + ": ");

		for (Edge edge : path) {
			System.out.println(edge.getTrip_id());
		}
	}
	
	public static void testFibHeap() {
		FibHeap obj = new FibHeap();
		Node n7, n26, n30, n39, n10;
		n7 = new Node(new Stop("7"));
		n26 = new Node(new Stop("26"));
		n30 = new Node(new Stop("30"));
		n39 = new Node(new Stop("39"));
		n10 = new Node(new Stop("10"));

		n7.set_key(7);
		n26.set_key(26);
		n30.set_key(30);
		n39.set_key(39);
		n10.set_key(10);

		obj.insert(n7);
		obj.insert(n26);
		obj.insert(n30);
		obj.insert(n39);
		obj.insert(n10);

		System.out.println(obj.extract_min().getStop().getStop_id());
		System.out.println(obj.extract_min().getStop().getStop_id());
		obj.decrease_key(n30, 11);
		System.out.println(obj.extract_min().getStop().getStop_id());
		System.out.println(obj.extract_min().getStop().getStop_id());
		System.out.println(obj.extract_min().getStop().getStop_id());
	}
}
