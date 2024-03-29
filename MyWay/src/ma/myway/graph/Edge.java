package ma.myway.graph;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import ma.myway.graph.data.Stop;

/**
 * <p>
 * C'est la classe represantant chaque <b>arête</b> du graph
 * </p>
 * <p>
 * Chaque arête est composé de 2 noeuds: source et destination (l'ordre des
 * noeuds est important (graph orienté))
 * </p>
 * <p>
 * <ul>
 * Elle contient les attributs suivants:
 * <li>le noeud source <b>src</b></li>
 * <li>le noeud destination <b>dest</b></li>
 * <li>le poids (cout) de parcours de l'arete <b>weight</b></li>
 * <li><b>trip_id</b></li>
 * </ul>
 * </p>
 * 
 * @see Node
 */
public class Edge implements Serializable {
	private static final long serialVersionUID = 160843439856388648L;
	private Node src, dest;
	private long weight; // type a discuter
	private String trip_id;
	private int transfer_type;
	private boolean isTransfert;
	private boolean isActive;

	public Edge(Node src, Node dest, long weight, String trip_id) {
		this.src = src;
		this.dest = dest;
		this.weight = weight;
		this.trip_id = trip_id;
		this.isTransfert = false;
	}

	public Edge(Node src, Node dest, long weight, String trip_id, int transfer_type) {
		this.src = src;
		this.dest = dest;
		this.weight = weight;
		this.trip_id = trip_id;
		this.transfer_type = transfer_type;
		this.isTransfert = true;
		// this.isActive = activity();
	}

	public boolean isTransfert() {
		return isTransfert;
	}

	public int getTransfer_type() {
		return transfer_type;
	}

	public Node getSrc() {
		return src;
	}

	public Node getDest() {
		return this.dest;
	}

	public long getWeight() {
		return this.weight;
	}

	public void setWeight(long weight) {
		this.weight = weight;
	}

	public String getTrip_id() {
		return trip_id;
	}

	public boolean isActive() {
		return isActive;
	}

	@Override
	public String toString() {
		return "Edge [src=" + src + ", dest=" + dest.getStop().getStop_id() + ", weight=" + weight + ", trip_id="
				+ trip_id + ", transfer_type=" + transfer_type + ", isTransfert=" + isTransfert + "]";
	}

	public int getTransferType() {
		throw new UnsupportedOperationException();
	}

	public LocalDate getNextStopTime() {
		throw new UnsupportedOperationException();
	}

	private static boolean activity(Date dateDep, Graph g) {

		return false;
	}

	public static List<Stop> edgesToStops(LinkedList<Edge> edges) {
		List<Stop> stops = new LinkedList<>();

		for (Edge edge : edges) {
			stops.add(edge.src.getStop());
		}
		stops.add(edges.getLast().dest.getStop());

		return edges.size() >= 1 ? stops : null;

	}

}
