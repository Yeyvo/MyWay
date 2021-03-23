package ma.myway;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Node {
	private Stop stop;
	private List<Node> shortestPath = new LinkedList<>();
	private Integer distance = Integer.MAX_VALUE;
	Map<Node, Integer> adjacentNodes = new HashMap<>();

	public Node(Stop stop){
		this.stop = stop;
	}

	public void addDestination(Node destination, int distance) {
		adjacentNodes.put(destination, distance);
	}

	public void setDistance(Integer distance) {
		this.distance = distance;
	}

	public Stop getStop() {
		return stop;
	}

	public Map<Node, Integer> getAdjacentNodes() {
		//change to return only active nodes
		return adjacentNodes;
	}
}
