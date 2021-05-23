package ma.myway.graph.data;

import java.util.List;

public class Trip {

	String trip_id;
	List<Stop> stops;

	public Trip(String trip_id, List<Stop> stops) {
		this.trip_id = trip_id;
		this.stops = stops;
	}

}
