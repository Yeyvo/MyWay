package ma.myway;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class MainClass {

	public static void main(String[] args) {
		testDijkstra();
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
