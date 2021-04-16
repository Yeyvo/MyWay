package ma.myway.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import ma.myway.graph.data.Route_Service;
import ma.myway.graph.data.Service_Direction;
import ma.myway.graph.data.Trips_Directions;

public class RoutesDAO extends DAO<Route_Service> {

	public RoutesDAO(Connection conn) {
		super(conn);
	}

	@Override
	public Route_Service find(String id) {
		return null;
	}

	/*
	 * @return a list of all active trips_id
	 */
	public Map<String,String> findActiveTrips(Set<String> lstService) {
		Map<String,String> res = new HashMap<>();
		try {
			var stmt = String.format("SELECT trip_id FROM trips where service_id in  (%s)",
					lstService.stream().collect(Collectors.joining(", ")));
			ResultSet result = this.connect.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)
					.executeQuery(stmt);
			while (result.next()) {
				res.put(result.getString(1),result.getString(1));
			}
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return res;
	}

	/*
	 * retrieving all Route_services data from DataBase
	 */

	@Override
	public Set<Route_Service> all() {
		Set<Route_Service> set_Route_Service = new HashSet<>();
		try {
			ResultSet result = this.connect.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)
					.executeQuery("SELECT * FROM trips");
			while (result.next()) {
				Route_Service resRS = Route_Service.search_by_id(set_Route_Service, result.getString("route_id"));
				if (resRS != null) { // route found
					Service_Direction resSD = Service_Direction.search_by_id(resRS.getServices(),
							result.getString("service_id"));
					if (resSD != null) {// service found
						resSD.getDirections().add(
								new Trips_Directions(result.getString("trip_id"), result.getString("direction_id")));
					} else {

						resSD = new Service_Direction(result.getString("service_id"), new HashSet<Trips_Directions>());
						resSD.getDirections().add(
								new Trips_Directions(result.getString("trip_id"), result.getString("direction_id")));
						resRS.getServices().add(resSD);

					}
				} else {

					resRS = new Route_Service(result.getString("route_id"), new HashSet<Service_Direction>());
					Service_Direction resSD = new Service_Direction(result.getString("service_id"),
							new HashSet<Trips_Directions>());
					resSD.getDirections()
							.add(new Trips_Directions(result.getString("trip_id"), result.getString("direction_id")));
					resRS.getServices().add(resSD);

				}

				set_Route_Service.add(resRS);

			}
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return set_Route_Service;
	}

	@Override
	public boolean create(Route_Service obj) {
		try {
			int result = this.connect.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)
					.executeUpdate("INSERT INTO trips VALUES(" + obj.toString() + ")");
			System.out.println(result + " Row affected ! ");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
