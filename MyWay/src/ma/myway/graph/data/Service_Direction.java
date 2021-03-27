package ma.myway.graph.data;

import java.util.Set;

public class Service_Direction {

	String service_id;
	Set<Trips_Directions> directions;

	public Service_Direction(String service_id, Set<Trips_Directions> directions) {

		this.service_id = service_id;
		this.directions = directions;
	}

	public String getService_id() {
		return service_id;
	}

	public Set<Trips_Directions> getDirections() {
		return directions;
	}

	public static Service_Direction search_by_id(Set<Service_Direction> set_Service_Trips, String service_id) {
		for (Service_Direction serviceTrip : set_Service_Trips) {
			if (serviceTrip.getService_id().equals(service_id)) {
				return serviceTrip;
			}
		}
		return null;
	}

}
