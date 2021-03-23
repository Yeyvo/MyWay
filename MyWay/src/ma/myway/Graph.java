package ma.myway;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Graph {

	List<Node> sommet = new LinkedList<>();// en cas de mauvaises performances modifier
	private Set<Edge> edges;

	
	public Graph(List<Node> sommet, Set<Edge> edges) {
		super();
		this.sommet = sommet;
		this.edges = edges;
	}

	public void addEdge(Edge edge) {
		edges.add(edge);
	}

	public Set<Node> getNeighbors(Node src) {
		Set<Node> neighbors = new HashSet<Node>();
		for (Edge edge : edges) {
			if(edge.getSource().getStop().getStop_id().equals(src.getStop().getStop_id()))
				neighbors.add(edge.getDest());
		}
		return neighbors;
	}

	public Set<Edge> getEdges() {
		return new HashSet<Edge>(this.edges);
	}

	public static Graph djikstra(Graph graph, Node source) {
		return null; 
	}
}