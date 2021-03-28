package ma.myway.graph.data;

import java.util.Set;

public class Route_Service {

	String route_id;
	Set<Service_Direction> services;
	
	public Route_Service(String route_id, Set<Service_Direction> services) {
		this.route_id = route_id;
		this.services = services;
	}

	public String getRoute_id() {
		return route_id;
	}

	public Set<Service_Direction> getServices() {
		return services;
	}
		
	public static Route_Service search_by_id(Set<Route_Service> set_Route_Service, String stop_id) {
		for(Route_Service route : set_Route_Service) {
			if(route.getRoute_id().equals(stop_id)) {
				return route;
			}
		}
		return null;
	}

}
