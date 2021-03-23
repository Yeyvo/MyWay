package ma.myway;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Graph {

	private List<Node> nodes = new LinkedList<>();// en cas de mauvaises performances modifier
	private Set<Edge> edges;
	private Set<Node> settledNodes;
    private Set<Node> unSettledNodes;
    private Map<Node, Node> predecessors; //destination - origine
	//private Map<Node, edge> predecessors;
    private Map<Node, Double> distance;
	
	public Graph(List<Node> nodes, Set<Edge> edges) {
		this.nodes = nodes;
		this.edges = edges;
	}

	public Set<Edge> getEdges() { //why?
		return new HashSet<Edge>(this.edges);
	}

	public void addEdge(Edge edge) {
		edges.add(edge);
	}

	private void findMinimalDistances(Node node) { // a changer
        List<Node> adjacentNodes = getNeighbors(node);
		//List<Edge> adjacentEdges = getNeighbors(node);
        for (Node target : adjacentNodes) {
            if (getShortestDistance(target) > getShortestDistance(node) + getDistance(node, target)) { //calcul a changer
                distance.put(target, getShortestDistance(node) + getDistance(node, target));
                predecessors.put(target, node);
                unSettledNodes.add(target);
            }
        }
    }

	private Node getMinimum(Set<Node> nodes) { //ok
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

	private double getShortestDistance(Node destination) { //ok
        Double d = distance.get(destination);
        if (d == null) {
            return Integer.MAX_VALUE;
        } else {
            return d;
        }
    }

	public List<Node> getNeighbors(Node src) {  //must be changed
		List<Node> neighbors = new ArrayList<Node>();
		for (Edge edge : edges) {
			if(edge.getSource().getStop().getStop_id().equals(src.getStop().getStop_id()) && !isSettled(edge.getDest()))
				neighbors.add(edge.getDest());
		}
		return neighbors;
	}

	private double getDistance(Node node, Node target) { //must be changed
        for (Edge edge : edges) {
            if (edge.getSource().equals(node)
                    && edge.getDest().equals(target)) {
                return edge.getWeight();
            }
        }
        throw new RuntimeException("Should not happen"); //why?
    }

	private boolean isSettled(Node node) {
        return settledNodes.contains(node);
    }

	public void djikstra(Graph graph, Node source) {
		//throw new UnsupportedOperationException("not implemented yet");
		settledNodes = new HashSet<Node>();
        unSettledNodes = new HashSet<Node>();
        distance = new HashMap<Node, Double>();
        predecessors = new HashMap<Node, Node>();
        distance.put(source, 0.0);
        unSettledNodes.add(source);
        while (unSettledNodes.size() > 0) {
            Node node = getMinimum(unSettledNodes);
            settledNodes.add(node);
            unSettledNodes.remove(node);
            findMinimalDistances(node);
        }
	}

	public LinkedList<Node> getPath(Node target) {
        LinkedList<Node> path = new LinkedList<Node>();
        Node step = target;
        // check if a path exists
        if (predecessors.get(step) == null) {
            return null;
        }
        path.add(step);
        while (predecessors.get(step) != null) {
            step = predecessors.get(step);
            path.add(step);
        }
        // Put it into the correct order
        Collections.reverse(path);
        return path;
    }
}
