package ma.myway.graph;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

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
	private FibHeap priorityQueue;
	private FibonacciHeap<Node> pq;
	private Map<Node, Edge> predecessors; // Map<Node destination - Edge origine> (origin = the edge leading to Node destination)
	private Map<Node, Double> distance;


	public Graph(HashMap<String, Node> nodes) {
		this.nodes = nodes;
		this.edges = new HashMap<String, List<Edge>>();
	}

	public Graph(HashMap<String, Node> node, HashMap<String, List<Edge>> edges) {
		this.nodes = node;
		this.edges = edges;
	}

	/*	
	private void writeObject(ObjectOutputStream oos) throws IOException {
		oos.defaultWriteObject();
		oos.writeObject(this.edges);
		oos.writeObject(this.nodes);
	}

	private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
		ois.defaultReadObject();
		HashMap<String, List<Edge>> edgesSer = (HashMap<String, List<Edge>>) ois.readObject();
		HashMap<String, Node> nodesSer = (HashMap<String, Node>) ois.readObject();
		
		//Graph g = new Graph(nodesSer,edgesSer);
		this.setEdges(edgesSer);
		this.setNodes(nodesSer);

	}*/

	public long getEdgeSize() {
		return edges.size();
	}
	
	public long getEdgeNumber() { // do not use this method !!
		long l =0L;
		for(String key : this.edges.keySet()) {
			l+=edges.get(key).size();
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

	public Node getNodebyID(String stop_id) {
		return  nodes.get(stop_id);
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
	public List<Edge> getNeighbors(Node src) { // done // must return list of edge with origine = src ---needs optimization
		List<Edge> neighbors = new ArrayList<Edge>();
		for (Edge edge : edges) {
			if (edge.getSrc().getStop().getStop_id().equals(src.getStop().getStop_id())
					&& !this.isSettled(edge.getDest()) && edge.isActive())
				neighbors.add(edge);
		}
		return neighbors;
	}
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
	private boolean isSettled(Node node) { // done
		return settledNodes.contains(node);
	}
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
	public LinkedList<Edge> getShortestPath(Node src, Node dest) {
		dijkstraOp2(src);
		return getPath(dest);
	}

	public void dijkstraOp(Node source) { // done
		distance = new HashMap<Node, Double>();
		predecessors = new HashMap<Node, Edge>();
		priorityQueue = new FibHeap();

		for(Node node: nodes.values()){
			distance.put(node, (double) Integer.MAX_VALUE);
		}

		for (Node node : nodes.values()) {
			priorityQueue.insert(node);
		}

		priorityQueue.decrease_key(source, 0);

		distance.put(source, 0.0);

		Node min = priorityQueue.extract_min();

		do {
			List<Edge> neighboors = edges.get(min.getStop().getStop_id());
			if(neighboors != null){
				for (Edge edge : neighboors) {
					double weight = edge.getWeight();
					double newLen = getShortestDistance(edge.getSrc()) + weight;
					if(newLen < getShortestDistance(edge.getDest())){
						priorityQueue.decrease_key(edge.getDest(), newLen);
						distance.put(edge.getDest(), newLen);
						predecessors.put(edge.getDest(), edge);
					}
				}
			}
			min = priorityQueue.extract_min();
		} while ( min != null);
	}

	public void dijkstraOp2(Node source) { // done
		distance = new HashMap<Node, Double>(); // to be changed
		predecessors = new HashMap<Node, Edge>();
		pq = new FibonacciHeap<Node>();

		HashMap<Node, FibonacciHeap.Entry<Node>> entries = new HashMap<Node, FibonacciHeap.Entry<Node>>();

		for(Node node: nodes.values()){
			distance.put(node, (double) Integer.MAX_VALUE);
		}

		distance.put(source, 0.0);


		for (Node node : nodes.values()) {
			entries.put(node, pq.enqueue(node, distance.get(node)));
		}

		Node min = (Node) pq.dequeueMin().mElem;

		do {
			List<Edge> neighboors = edges.get(min.getStop().getStop_id());
			if(neighboors != null){
				for (Edge edge : neighboors) {
					double weight = edge.getWeight();
					double newLen = getShortestDistance(edge.getSrc()) + weight;
					if(newLen < getShortestDistance(edge.getDest())){
						pq.decreaseKey(entries.get(edge.getDest()), newLen);
						distance.put(edge.getDest(), newLen);
						predecessors.put(edge.getDest(), edge);
					}
				}
			}
			try {
				min =  (Node) pq.dequeueMin().mElem;
			} catch (Exception e) {
				min = null;
			}
		} while ( min != null);
	}
}
