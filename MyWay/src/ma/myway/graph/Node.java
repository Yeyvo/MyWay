package ma.myway.graph;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import ma.myway.graph.data.Stop;

/**
 * <p>
 * C'est la classe represantant chaque <b>noeud</b> du graph
 * </p>
 * <p>
 * Chaque noeud est lié a une instance Stop (c'est a dire une station)
 * </p>
 * <p>
 * <ul>
 * Elle contient les attributs suivants:
 * <li>stop</li>
 * <li>shortestPath</li>
 * <li>distance</li>
 * <li>adjacentNodes</li>
 * </ul>
 * </p>
 * 
 * @see Stop
 */

public class Node implements Serializable{
	Node parent;
	Node left;
	Node right;
	Node child;
	int degree;
	boolean mark;
	double key;
	private static final long serialVersionUID = 4549578642953851985L;
	private Stop stop;
	private List<Node> shortestPath = new LinkedList<>();
	private Integer distance = Integer.MAX_VALUE;
	Map<Node, Integer> adjacentNodes = new HashMap<>();
	
	//private static Stop NULL_STOP = new Stop("N/A");

	/** 
	 * @param stop
	 */
	public Node(Stop stop) {
		this.stop = stop;
		this.degree = 0;
		this.mark = false;
		this.parent = null;
		this.left = this;
		this.right = this;
		this.child = null;
		this.key = Integer.MAX_VALUE;
	}

	void set_parent(Node x) {
		this.parent = x;
	}

	Node get_parent() {
		return this.parent;
	}

	void set_left(Node x) {
		this.left = x;
	}

	Node get_left() {
		return this.left;
	}

	void set_right(Node x) {
		this.right = x;
	}

	Node get_right() {
		return this.right;
	}

	void set_child(Node x) {
		this.child = x;
	}

	Node get_child() {
		return this.child;
	}

	void set_degree(int x) {
		this.degree = x;
	}

	int get_degree() {
		return this.degree;
	}

	void set_mark(boolean m) {
		this.mark = m;
	}

	boolean get_mark() {
		return this.mark;
	}

	public void set_key(double x) {
		this.key = x;
	}

	double get_key() {
		return this.key;
	}

	/**
	 * @param destination
	 * @param distance
	 */
	public void addDestination(Node destination, int distance) {
		adjacentNodes.put(destination, distance);
	}

	/**
	 * @param distance
	 */
	public void setDistance(Integer distance) {
		this.distance = distance;
	}

	/**
	 * @return Stop
	 */
	public Stop getStop() {
		return stop /*== null ? NULL_STOP : stop */;
	}

	/**
	 * @return Map<Node, Integer>
	 */
	public Map<Node, Integer> getAdjacentNodes() {
		// change to return only active nodes
		return adjacentNodes;
	}
	
	@Deprecated
	public static Node getNodeByID(List<Node> nodes, String stop_id) {

		for(Node n : nodes) {
			if(n.getStop().getStop_id().equals(stop_id)) {
				return n;
			}
		}
		
		return null;
		
	}
}
