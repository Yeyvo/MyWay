package ma.myway.graph.data;

import java.io.Serializable;

public class TripsComp implements Serializable {
	
	String route_id, service_id, trip_id, trip_headsign, trip_short_name, direction_id, shape_id;

	public TripsComp(String route_id, String service_id, String trip_id, String trip_headsign, String trip_short_name,
			String direction_id, String shape_id) {
		super();
		this.route_id = route_id;
		this.service_id = service_id;
		this.trip_id = trip_id;
		this.trip_headsign = "null";
		this.trip_short_name = trip_short_name;
		this.direction_id = direction_id;
		this.shape_id = "null";
	}

	public String getRoute_id() {
		return route_id;
	}

	public void setRoute_id(String route_id) {
		this.route_id = route_id;
	}

	public String getService_id() {
		return service_id;
	}

	public void setService_id(String service_id) {
		this.service_id = service_id;
	}

	public String getTrip_id() {
		return trip_id;
	}

	public void setTrip_id(String trip_id) {
		this.trip_id = trip_id;
	}

	public String getTrip_headsign() {
		return trip_headsign;
	}

	public void setTrip_headsign(String trip_headsign) {
		this.trip_headsign = trip_headsign;
	}

	public String getTrip_short_name() {
		return trip_short_name;
	}

	public void setTrip_short_name(String trip_short_name) {
		this.trip_short_name = trip_short_name;
	}

	public String getDirection_id() {
		return direction_id;
	}

	public void setDirection_id(String direction_id) {
		this.direction_id = direction_id;
	}

	public String getShape_id() {
		return shape_id;
	}

	public void setShape_id(String shape_id) {
		this.shape_id = shape_id;
	}

	@Override
	public String toString() {
		return "'"+route_id + "," + service_id + "','" + trip_id + "'," + trip_headsign + "," + trip_short_name != null? ("'" + trip_short_name + "'") : ("null")  + "','"
				+ direction_id + "'," + shape_id + "";
	}
	
	
	
	

}
