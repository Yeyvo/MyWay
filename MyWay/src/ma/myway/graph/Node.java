package ma.myway.graph;

import java.io.Serializable;
import java.util.HashMap;
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

public class Node implements Serializable {
	private static final long serialVersionUID = 4549578642953851985L;
	private Stop stop;
	private double distance = Integer.MAX_VALUE;
	private Edge predecessor = null;
	Map<Node, Integer> adjacentNodes = new HashMap<>();

	// private static Stop NULL_STOP = new Stop("N/A");

	/**
	 * @param stop
	 */
	public Node(Stop stop) {
		this.stop = stop;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public double getDistance() {
		return this.distance;
	}

	public void setPredecessor(Edge predecessor) {
		this.predecessor = predecessor;
	}

	public Edge getPredecessor() {
		return predecessor;
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
		return stop /* == null ? NULL_STOP : stop */;
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

		for (Node n : nodes) {
			if (n.getStop().getStop_id().equals(stop_id)) {
				return n;
			}
		}

		return null;

	}
}
