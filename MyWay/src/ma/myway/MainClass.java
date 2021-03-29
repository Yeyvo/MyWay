package ma.myway;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
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

public class MainClass {

	public static void main(String[] args) {
		long StartTime = System.currentTimeMillis();		
		
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
		
		graphGeneration(StartTime);

	    long endTime = System.currentTimeMillis();
		double timeElapsed = endTime - StartTime;
		Logger.getLogger("MyLog").info("Execution time in seconds  :  List : " + timeElapsed/1000 +  " seconde "); 

		
	}
	
	public static Graph graphGeneration(long StartTime) {
		long a ,b ;
		Logger.getLogger("MyLog").info("Config file loading Started ! ");
		Config.load("config.json");
		Logger.getLogger("MyLog").info("Config file loading Finished !  time " + (System.currentTimeMillis()-StartTime)/1000);
		BddConnection.getInstance();
		Logger.getLogger("MyLog").info("Node Generation Started ! ");
		 a = System.currentTimeMillis();
		HashMap<String, Node> node = new HashMap<>();
		Set<Stop> stops = DAOFactory.getStopDAO().all();
		long i =0;
		for (Stop stop : stops) {
			node.put(stop.getStop_id(), new Node(stop));
			Logger.getLogger("MyLog").info("Node : " + stop.getStop_id()); 
			i++;
		}
		 b = System.currentTimeMillis();
		Logger.getLogger("MyLog").info("Node Generation Finished ! ("+i+")  time : " + (b-a)/1000);
		
		
		Logger.getLogger("MyLog").info("StopTrips Data Retrieving started ! ");
		Set<Edge> edges = new HashSet<Edge>();
		List<Stop_Trip> st = DAOFactory.getStopTripDAO().allList();
		a = System.currentTimeMillis();
		Logger.getLogger("MyLog").info("StopTrips Data Retrieving Ended !   time :  " + (a-b)/1000);
		Logger.getLogger("MyLog").info("Edge Generation from Trips started ! ");
		i=0;
		for (int j =0; j<st.size(); j++ ) {
			Stop_Trip next = Stop_Trip.getNextStop_sequence(st, st.get(j).getTrip_id(),st.get(j).getStop_sequence(),j);
			if (next != null) {
				 edges.add(new Edge( node.get(st.get(j).getStop_id()),node.get(next.getStop_id()), 1, st.get(j).getTrip_id()));
				 Logger.getLogger("MyLog").info("Edge Trip : src : " + st.get(j).getStop_id() +" dst : " + next.getStop_id() + " Trip_id : "+st.get(j).getTrip_id() + " Stop_sequence : " + st.get(j).getStop_sequence() ); 
				 i++;
			}
		}
		 b = System.currentTimeMillis();
		Logger.getLogger("MyLog").info("Edge Generation from Trips Finished ! ("+i+")  time : " + (b-a)/1000);
		
		
		
		Logger.getLogger("MyLog").info("Transferts Data Retrieving started ! ");
		Set<Transfert> trans = DAOFactory.getTransfertDAO().all();
		a = System.currentTimeMillis();
		Logger.getLogger("MyLog").info("Transferts Data Retrieving Finished !  time :  " + (a-b)/1000);
		Logger.getLogger("MyLog").info("Edge Generation from Transferts started ! ");
		i = 0;
		for (Transfert tr : trans) {
			edges.add(new Edge(node.get(tr.getSrc_stop_id()), node.get(tr.getDest_stop_id()),tr.getTransfert_time() , "0", tr.getTransfert_type()));
			 Logger.getLogger("MyLog").info("Edge Transferts: src : " + tr.getSrc_stop_id() +" dst : " + tr.getDest_stop_id() + " Trip_id : 0  Transfert Time :  "+tr.getTransfert_time() ); 
			i++;
		}
		 b = System.currentTimeMillis();
		Logger.getLogger("MyLog").info("Edge from Transferts Generation Finished ! ("+i+")  time :  " + (b-a)/1000);
		
		Graph g = new Graph(node, edges);
		
		
		return g;
	}
	
	public static void loggerInit() {
		
	}

}
