package ma.myway;

public class Edge {
	private Node src, dest;
	private double weight;//type a discuter
	private String trip_id;

	public Edge(Node src, Node dest, double weight, String trip_id) {
		this.src = src;
		this.dest = dest;
		this.weight = weight;
		this.trip_id = trip_id;
	}

	Node getSource() {
		return this.src;
	}

	Node getDest() {
		return this.dest;
	}

	double getWeight() {
		return this.weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String getTrip_id() {
		return trip_id;
	}

}
