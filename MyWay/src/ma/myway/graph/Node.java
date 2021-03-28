package ma.myway.graph;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
public class Node {
	private Stop stop;
	private List<Node> shortestPath = new LinkedList<>();
	private Integer distance = Integer.MAX_VALUE;
	Map<Node, Integer> adjacentNodes = new HashMap<>();

	/**
	 * for testing purposes only
	 * 
	 * @param stop
	 */
	public Node(Stop stop) {
		this.stop = stop;
	}

	/**
	 * ---
	 * 
	 * @param destination
	 * @param distance
	 */
	public void addDestination(Node destination, int distance) {
		adjacentNodes.put(destination, distance);
	}

	/**
	 * setter
	 * 
	 * @param distance
	 */
	public void setDistance(Integer distance) {
		this.distance = distance;
	}

	/**
	 * getter
	 * 
	 * @return Stop
	 */
	public Stop getStop() {
		return stop;
	}

	/**
	 * getter
	 * 
	 * @return Map<Node, Integer>
	 */
	public Map<Node, Integer> getAdjacentNodes() {
		// change to return only active nodes
		return adjacentNodes;
	}
	
	public static Node getNodeByID(List<Node> nodes, String stop_id) {

		for(Node n : nodes) {
			if(n.getStop().getStop_id().equals(stop_id)) {
				return n;
			}
		}
		
		return null;
		
	}
}
