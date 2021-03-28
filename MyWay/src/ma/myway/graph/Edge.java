package ma.myway.graph;

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
public class Edge {
	private Node src, dest;
	private double weight;// type a discuter
	private String trip_id;
	private int transfer_type;
	private boolean isTransfert ;

	public Edge(Node src, Node dest, double weight, String trip_id) {
		this.src = src;
		this.dest = dest;
		this.weight = weight;
		this.trip_id = trip_id;
		this.isTransfert = false;
	}
	public Edge(Node src, Node dest, double weight, String trip_id, int transfer_type) {
		this.src = src;
		this.dest = dest;
		this.weight = weight;
		this.trip_id = trip_id;
		this.transfer_type = transfer_type;
		this.isTransfert = true;
	}
	
	/**
	 * getter
	 * 
	 * @return boolean
	 */
	public boolean isTransfert() {
		return isTransfert;
	}
	/**
	 * getter
	 * 
	 * @return int
	 */
	public int getTransfer_type() {
		return transfer_type;
	}
	/**
	 * getter
	 * 
	 * @return Node
	 */
	public Node getSrc() {
		return src;
	}

	/**
	 * getter
	 * 
	 * @return Node
	 */
	public Node getDest() {
		return this.dest;
	}

	/**
	 * getter
	 * 
	 * @return double
	 */
	public double getWeight() {
		return this.weight;
	}

	/**
	 * setter
	 * 
	 * @param weight
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}

	/**
	 * getter
	 * 
	 * @return String
	 */
	public String getTrip_id() {
		return trip_id;
	}

	/**
	 * exprime si l'arete est active ou non, c'est a dire si le voyage est actuellement disponible
	 * 
	 * @return boolean
	 */
	public boolean isActive() {
		//a implementer (service_id ?)
		return true;
	}

}
