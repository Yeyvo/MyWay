package ma.myway.graph.data;

public class Trips_Directions {

	String trip_id;
	String direction_id;
	
	public Trips_Directions(String trip_id, String direction_id) {
		this.trip_id = trip_id;
		this.direction_id = direction_id;
	}

	public String getTrip_id() {
		return trip_id;
	}

	public String getDirection_id() {
		return direction_id;
	}


	public String toString() {
		return  trip_id + ",null,null,"+direction_id +",null";
	}
	
	

}
