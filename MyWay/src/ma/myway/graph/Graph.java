package ma.myway.graph;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.time.temporal.ChronoUnit;

/**
 * <p>
 * C'est la classe represantant le <b>graphe</b> qui modelise le reseau de transport
 * </p>
 * <p>
 * <ul>
 * Elle contient les attributs suivants:
 * <li>nodes</li>
 * <li>edges</li>
 * <li>settednodes</li>
 * <li>unSetteledNodes</li>
 * <li>predecessors</li>
 * <li>distance</li>
 * </ul>
 * </p>
 * 
 * @see Node
 * @see Edge
 */
public class Graph {

	private HashMap<String, Node> nodes = new HashMap<>();// en cas de mauvaises performances modifier
	private Set<Edge> edges;
	private Set<Node> settledNodes;
	private Set<Node> unSettledNodes;
	private FibHeap priorityQueue;

	/**
	 * Map<Node destination - Edge origine> (origin = the edge leading to Node destination)
	 */
	private Map<Node, Edge> predecessors; 
	private Map<Node, Double> distance;

	public Graph(HashMap<String, Node> nodes) {
		this.nodes = nodes;
		this.edges = new HashSet<Edge>();
	}
	
	public Graph(HashMap<String, Node> node, Set<Edge> edges) {
		this.nodes = node;
		this.edges = edges;
	}

	/**
	 * @return les aretes du graphe
	 */
	public Set<Edge> getEdges() { 
		return new HashSet<Edge>(this.edges);
	}

	/**
	 * ajouter une arrete (liaison) au graphe
	 * 
	 * @param egde
	 */
	public void addEdge(Edge edge) {
		edges.add(edge);
	}

	/**
	 * met a jour le cout d'atteinte des noeuds voisins du noeud passé en argument
	 * 
	 * @see Graph#dijsktra(Node)
	 * @param node
	 */
	private void findMinimalDistances(Node node) { // done
		List<Edge> adjacentEdges = getNeighbors(node);
		for (Edge target : adjacentEdges) {
			if (getShortestDistance(target.getDest()) > getShortestDistance(node) + target.getWeight()) { // calcul a changer
				distance.put(target.getDest(), getShortestDistance(node) + target.getWeight());
				predecessors.put(target.getDest(), target);
				unSettledNodes.add(target.getDest());
			}
		}
	}

	/**
	 * retourne le noeud le plus proche (avec le cout minimum) parmis un ensemble de
	 * noeuds passé en argument
	 * 
	 * @param nodes
	 * @return le noeud avec le cout minimum
	 */
	private Node getMinimum(Set<Node> nodes) { // ok //needs optimization
		Node minimum = null;
		for (Node node : nodes) {
			if (minimum == null) {
				minimum = node;
			} else {
				if (getShortestDistance(node) < getShortestDistance(minimum)) {
					minimum = node;
				}
			}
		}
		return minimum;
	}

	/**
	 * @param destination
	 * @return le cout d'atteinte du noeud destination
	 */
	private double getShortestDistance(Node destination) { // ok
		Double d = distance.get(destination);
		if (d == null) {
			return Integer.MAX_VALUE;
		} else {
			return d;
		}
	}

	/**
	 * @param le noeud source
	 * @return la liste des voisins du noeud passé en argument
	 */
	public List<Edge> getNeighbors(Node src) { // done // must return list of edge with origine = src //needs optimization 
		List<Edge> neighbors = new ArrayList<Edge>();
		for (Edge edge : edges) {
			if (edge.getSrc().getStop().getStop_id().equals(src.getStop().getStop_id()) && !this.isSettled(edge.getDest()) && edge.isActive())
				neighbors.add(edge);
		}
		return neighbors;
	}

	private double getCost(Edge edge, Edge previous){
		double toAdd = 0;
		if(previous != null){
			if(previous.getTransferType() != edge.getTransferType() || !previous.getTrip_id().equals(edge.getTrip_id())){
				toAdd = ChronoUnit.SECONDS.between(edge.getNextStopTime(), LocalDate.now());
			}
		}
		return toAdd + edge.getWeight();
	}

	/**
	 * @return boolean
	 * @param node
	 * @see Graph#dijsktra(Node)
	 */
	private boolean isSettled(Node node) { // done
		return settledNodes.contains(node);
	}

	/**
	 * @param graph
	 * @param source
	 */
	public void dijkstra(Node source) { // done
		settledNodes = new HashSet<Node>();
		unSettledNodes = new HashSet<Node>();
		distance = new HashMap<Node, Double>();
		predecessors = new HashMap<Node, Edge>();

		distance.put(source, 0.0);
		unSettledNodes.add(source);
		while (unSettledNodes.size() > 0) {
			Node node = getMinimum(unSettledNodes);
			settledNodes.add(node);
			unSettledNodes.remove(node);
			findMinimalDistances(node);
		}
	}

	/**
	 * @param Node
	 * @return LinkedList<Edge>
	 */
	public LinkedList<Edge> getPath(Node target) { // done
		LinkedList<Edge> path = new LinkedList<Edge>();
		Node step = target;
		Edge next;
		// check if a path exists
		if (predecessors.get(step) == null) {
			return null;
		}
		while (predecessors.get(step) != null) {
			next = predecessors.get(step);
			step = next.getSrc();
			path.add(next);
		}
		// Put it into the correct order
		Collections.reverse(path);
		return path;
	}
	/**
	 * wrapper function for dijkstra
	 * 
	 * @param src
	 * @param dest
	 * @return le plus court chemin du noeud src au noeud dest
	 */
	public LinkedList<Edge> getShortestPath(Node src, Node dest){
		dijkstra(src);
		return getPath(dest);
	}
}
