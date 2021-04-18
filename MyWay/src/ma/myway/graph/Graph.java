package ma.myway.graph;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import ma.myway.dao.DAOFactory;
import ma.myway.graph.data.Service;

/**
 * <p>
 * C'est la classe represantant le <b>graphe</b> qui modelise le reseau de
 * transport
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

public class Graph implements Serializable {

	private static final long serialVersionUID = 3220075418287104362L;
	private HashMap<String, Node> nodes = new HashMap<>();// en cas de mauvaises performances modifier
	private HashMap<String, List<Edge>> edges;
	private Set<Node> settledNodes;
	private Set<Node> unSettledNodes;
	private FibonacciHeap<Node> pq;
	private Map<Node, Edge> predecessors; // Map<Node destination - Edge origine> (origin = the edge leading to Node
											// destination)

	private Map<Node, Double> distance;

	private Map<String, Service> services;

	public Graph(HashMap<String, Node> nodes) {
		this.nodes = nodes;
		this.edges = new HashMap<String, List<Edge>>();
	}

	public Graph(HashMap<String, Node> node, HashMap<String, List<Edge>> edges) {
		this.nodes = node;
		this.edges = edges;
	}

	public Graph(HashMap<String, Node> node, HashMap<String, List<Edge>> edges, Map<String, Service> services) {
		this.nodes = node;
		this.edges = edges;
		this.services = services;
	}

	public long getEdgeSize() {
		return edges.size();
	}

	public long getEdgeNumber() { // do not use this method !!
		long l = 0L;
		for (String key : this.edges.keySet()) {
			l += edges.get(key).size();
		}
		return l;
	}

	public long getNodeSize() {
		return nodes.size();
	}

	/**
	 * @return les aretes du graphe
	 */
	public HashMap<String, List<Edge>> getEdges() {
		return new HashMap<String, List<Edge>>(this.edges);
	}

	/**
	 * ajouter une arrete (liaison) au graphe
	 * 
	 * @param egde
	 */
	public void addEdge(Edge edge) {
		if (edge.getSrc() == null || edge.getDest() == null) {
			Logger.getLogger("MyLog").info("NULL source or destination Node");
		}
		if (edges.containsKey(edge.getSrc().getStop().getStop_id())) {
			edges.get(edge.getSrc().getStop().getStop_id()).add(edge);
			return;
		}
		List<Edge> lst = new LinkedList<Edge>();
		lst.add(edge);
		edges.put(edge.getSrc().getStop().getStop_id(), lst);
	}

	/**
	 * ajouter un noeud au graphe
	 * 
	 * @param egde
	 */
	public void addNode(Node node) {
		nodes.put(node.getStop().getStop_id(), node);
	}

	/**
	 * met a jour le cout d'atteinte des noeuds voisins du noeud passé en argument
	 * 
	 * @see Graph#dijsktra(Node)
	 * @param node
	 */
	private void findMinimalDistances(Node node) { // done
		List<Edge> adjacentEdges = edges.get(node.getStop().getStop_id());
		for (Edge target : adjacentEdges) {
			if (getShortestDistance(target.getDest()) > getShortestDistance(node) + target.getWeight()) { // calcul a
																											// changer
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

	public Node getNodebyID(String stop_id) {
		return nodes.get(stop_id);
	}

	private void setNodes(HashMap<String, Node> nodes) {
		this.nodes = nodes;
	}

	private void setEdges(HashMap<String, List<Edge>> edges) {
		this.edges = edges;
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
	/*
	 * public List<Edge> getNeighbors(Node src) { // done // must return list of
	 * edge with origine = src ---needs optimization List<Edge> neighbors = new
	 * ArrayList<Edge>(); for (Edge edge : edges) { if
	 * (edge.getSrc().getStop().getStop_id().equals(src.getStop().getStop_id()) &&
	 * !this.isSettled(edge.getDest()) && edge.isActive()) neighbors.add(edge); }
	 * return neighbors; }
	 */

	private double getCost(Edge edge, Edge previous) {
		double toAdd = 0;
		if (previous != null) {
			if (previous.getTransferType() != edge.getTransferType()
					|| !previous.getTrip_id().equals(edge.getTrip_id())) {
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
	/*
	 * private boolean isSettled(Node node) { // done return
	 * settledNodes.contains(node); }
	 */

	private boolean isSettled(Node node) { // done
		return settledNodes.contains(node);
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
		if (step.getPredecessor() == null) {
			return null;
		}
		while (step.getPredecessor() != null) {
			next = step.getPredecessor();
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
	public LinkedList<Edge> getShortestPath(Node src, Node dest) {
		dijkstra(src, LocalDate.now());
		return getPath(dest);
	}

	public Map<String, Service> getServices() {
		return services;
	}

	public void setServices(Map<String, Service> services) {
		this.services = services;
	}

	public  Set<String> Service_Date(Date date) {
		Set<String> result = new HashSet<String>();

		for (Service service : services.values()) {// if it's runing slow whe can use MutableMap of Eclipse (CS)

			if (((service.getStart_date().compareTo(date) * date.compareTo(service.getEnd_date()) >= 0)
					&& (service.getRemoved().indexOf(date) == -1)) || (service.getAdded().indexOf(date) != -1)) {
				result.add(service.getService_id());

			}

		}

		return result;
	}

	// a modifier si il y'as des problemmes
	public  Map<String, String> Trip_Date(Date date) {
		Set<String> lstService = Service_Date(date);
		return DAOFactory.getRoutesDAO().findActiveTrips(lstService);
	}

	/**
	 * @param graph
	 * @param source
	 */
	public void dijkstra(Node source, LocalDate localDate) { // done
		pq = new FibonacciHeap<Node>();

		HashMap<Node, FibonacciHeap.Entry<Node>> entries = new HashMap<Node, FibonacciHeap.Entry<Node>>();

		source.setDistance(0.0);

		for (Node node : nodes.values()) {
			entries.put(node, pq.enqueue(node, node.getDistance()));
		}

		Node min = pq.dequeueMin().mElem;
		Map<String,String> tripDate = Trip_Date(Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
		do {
			List<Edge> neighboors = edges.get(min.getStop().getStop_id());
			if (neighboors != null) {
				for (Edge edge : neighboors) {
					if(tripDate.containsKey(edge.getTrip_id())){
						double weight = edge.getWeight();
						double newLen = edge.getSrc().getDistance() + weight;
						if (newLen < edge.getDest().getDistance()) {
							pq.decreaseKey(entries.get(edge.getDest()), newLen);
							edge.getDest().setDistance(newLen);
							edge.getDest().setPredecessor(edge);
						}
					}
				}
			}
			try {
				min = pq.dequeueMin().mElem;
			} catch (Exception e) {
				min = null;
			}
		} while (min != null);
	}
}
