package ma.myway;

public class Edge {
	private Stop src, dest;
	private double weight;//type a discuter
	private String trip_id;

	public Edge(Stop src, Stop dest, double weight, String trip_id) {
		this.src = src;
		this.dest = dest;
		this.weight = weight;
		this.trip_id = trip_id;
	}

	Stop getSource() {
		return this.src;
	}

	Stop getDest() {
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
