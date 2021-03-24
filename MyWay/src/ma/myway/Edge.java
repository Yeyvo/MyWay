package ma.myway;

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
 * @author hamza-boudouche
 */
public class Edge {
	private Node src, dest;
	private double weight;// type a discuter
	private String trip_id;

	public Edge(Node src, Node dest, double weight, String trip_id) {
		this.src = src;
		this.dest = dest;
		this.weight = weight;
		this.trip_id = trip_id;
	}

	/**
	 * getter
	 * 
	 * @return Node
	 */
	Node getSource() {
		return this.src;
	}

	/**
	 * getter
	 * 
	 * @return Node
	 */
	Node getDest() {
		return this.dest;
	}

	/**
	 * getter
	 * 
	 * @return double
	 */
	double getWeight() {
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
