package ma.myway;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Graph {

	List<Stop> sommet = new LinkedList<>();// en cas de mauvaises performances modifier
	private Set<Edge> edges;

	
	public Graph(List<Stop> sommet, Set<Edge> edges) {
		this.sommet = sommet;
		this.edges = edges;
	}

	public void addEdge(Edge edge) {
		edges.add(edge);
	}

	public Set<Stop> getNeighbors(Stop src) {
		Set<Stop> neighbor = new HashSet<Stop>();
		for (Edge edge : edges) {
			if(edge.getSource().getStop_id().equals(src.getStop_id()))
				neighbor.add(edge.getDest());
		}
		return neighbor;
	}

	public Set<Edge> getEdges() {
		return new HashSet<Edge>(this.edges);
	}
}
