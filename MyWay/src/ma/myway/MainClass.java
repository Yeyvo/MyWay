package ma.myway;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import ma.myway.config.Config;
import ma.myway.dao.BddConnection;
import ma.myway.dao.DAOFactory;
import ma.myway.graph.Edge;
import ma.myway.graph.Graph;
import ma.myway.graph.Node;
import ma.myway.graph.data.Agency;
import ma.myway.graph.data.Route_Service;
import ma.myway.graph.data.Service_Direction;
import ma.myway.graph.data.Stop;
import ma.myway.graph.data.Stop_Trip;
import ma.myway.graph.data.Trips_Directions;

public class MainClass {

	public static void main(String[] args) {
		// testDijkstra();
		long startTime = System.currentTimeMillis();
		Config.load("config.json");
		BddConnection.getInstance();

		// testConnectAndDataRet();

		List<Node> nodes = new ArrayList<Node>();
		Set<Stop> stops = DAOFactory.getStopDAO().all();
		for (Stop stop : stops) {
			nodes.add(new Node(stop));
		}

		Set<Edge> edges = new HashSet<Edge>();
		Set<Stop_Trip> st = DAOFactory.getStopTripDAO().all();

		for (Stop_Trip stopTrip : st) {
			Stop_Trip next = Stop_Trip.getNextStop_sequence(st, stopTrip.getTrip_id(), stopTrip.getArrival_time(),
					stopTrip.getStop_sequence());
			if (next != null) {
				 edges.add(new Edge(Node.getNodeByID(nodes,stopTrip.getStop_id()), Node.getNodeByID(nodes,next.getStop_id()), 1, stopTrip.getTrip_id()));
			}
		}

		Graph g = new Graph(nodes, edges);
		g.toString();
		long endTime = System.currentTimeMillis();
		double timeElapsed = endTime - startTime/ 1000;
        System.out.println("Execution time in seconds : " + timeElapsed );
		
	}

	public static void testConnectAndDataRet() {

		// Simple tests

		Set<Agency> setA = DAOFactory.getAgencyDAO().all();
		for (Agency ag : setA) {
			System.out.println(ag.getAgency_id() + " " + ag.getAgency_name());
		}

		Set<Route_Service> routes = DAOFactory.getRoutesDAO().all();
		for (Route_Service ag : routes) {
			System.out.println("* getRoute_id = " + ag.getRoute_id() + "  :  ");
			for (Service_Direction sd : ag.getServices()) {
				System.out.println("\t ** getService_id = " + sd.getService_id() + "  :  ");
				for (Trips_Directions td : sd.getDirections()) {
					System.out.println("\t\t ***  getDirection_id = " + td.getDirection_id() + "  //   getTrip_id = "
							+ td.getTrip_id());

				}
			}
		}
	}

	public static void testDijkstra() {
		Stop sa, sb, sc;
		sa = new Stop("a");
		sb = new Stop("b");
		sc = new Stop("c");

		Node a, b, c;
		a = new Node(sa);
		b = new Node(sb);
		c = new Node(sc);

		Edge ab1, ab2, ab3, ac, bc1, bc2;
		ab1 = new Edge(a, b, 5, "ab1");
		ab2 = new Edge(a, b, 1, "ab2");
		ac = new Edge(a, c, 4, "ac");
		bc1 = new Edge(b, c, 1, "bc1");
		bc2 = new Edge(b, c, 3, "bc2");
		ab3 = new Edge(a, b, 0.5, "ab3");

		List<Node> nodes = new ArrayList<Node>();
		Set<Edge> edges = new HashSet<Edge>();

		nodes.add(a);
		nodes.add(b);
		nodes.add(c);

		edges.add(ab1);
		edges.add(ab2);
		edges.add(ac);
		edges.add(bc1);
		edges.add(bc2);
		edges.add(ab3);

		Graph g = new Graph(nodes, edges);

		g.dijkstra(a);

		LinkedList<Edge> path = g.getPath(c);

		System.out.println("Shortest path from " + a.getStop().getStop_id() + " to " + c.getStop().getStop_id() + ": ");

		for (Edge edge : path) {
			System.out.println(edge.getTrip_id());
		}
	}
}
