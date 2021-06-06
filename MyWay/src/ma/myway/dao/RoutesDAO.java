package ma.myway.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import ma.myway.graph.data.Route_Service;
import ma.myway.graph.data.Service_Direction;
import ma.myway.graph.data.TripsComp;
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
	public Map<String, String> findActiveTrips(Set<String> lstService) {
		Map<String, String> res = new HashMap<>();
		Statement stmt = null;
		try {
			var var = String.format("SELECT trip_id FROM trips where service_id in  (%s)",
					lstService.stream().collect(Collectors.joining(", ")));
			stmt = this.connect.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			ResultSet result = stmt.executeQuery(var);
			while (result.next()) {
				res.put(result.getString(1), result.getString(1));
			}
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		res.put("0", "0");
		return res;
	}
	
	public Set<TripsComp> allSet() {
		Set<TripsComp> data = new HashSet<>();
		Statement stmt = null;
		try {
			stmt = this.connect.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			ResultSet result = stmt.executeQuery("SELECT * FROM trips");
			while (result.next()) {
				
				data.add(new TripsComp(result.getString(1),result.getString(2),result.getString(3),result.getString(4),result.getString(5),result.getString(6),result.getString(7)));
				
			}
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return data;
	}
	/*
	 * retrieving all Route_services data from DataBase
	 */

	@Override
	public Set<Route_Service> all() {
		Set<Route_Service> set_Route_Service = new HashSet<>();
		Statement stmt = null;
		try {
			stmt = this.connect.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			ResultSet result = stmt.executeQuery("SELECT route_id,service_id,trip_id,direction_id FROM trips");
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
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return set_Route_Service;
	}

	@Override
	public boolean create(Route_Service obj) {
		Statement stmt = null;
		try {
			stmt = this.connect.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			int result = stmt.executeUpdate("INSERT INTO trips VALUES(" + obj.toString() + ")");
			System.out.println(result + " Row affected ! ");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public boolean createComp(TripsComp obj) {
		Statement stmt = null;
		try {
			stmt = this.connect.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			System.out.println("INSERT INTO trips VALUES(" + "'"+obj.getRoute_id() + "','" + obj.getService_id() + "','" + obj.getTrip_id() + "'," + "null" + "," + obj.getTrip_short_name() != null? ("'" + obj.getTrip_short_name() + "'") : ("null")  + ",'"
					+ obj.getDirection_id() + "',null" + ")");
			int result = stmt.executeUpdate("INSERT INTO trips VALUES(" + "'"+obj.getRoute_id() + "','" + obj.getService_id() + "','" + obj.getTrip_id() + "'," + "null" + "," + obj.getTrip_short_name() != null? ("'" + obj.getTrip_short_name() + "'") : ("null")  + ",'"
					+ obj.getDirection_id() + "',null" + ")");
			System.out.println(result + " Row affected ! ");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public boolean delete(Route_Service obj) {// fait
		Statement stmt = null;
		try {
			stmt = this.connect.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			int result = stmt.executeUpdate("DELETE FROM trips WHERE route_id = " + obj.getRoute_id());
			System.out.println(result + " Row affected !");
			return (true);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return (false);
	}
	public boolean deleteComp(TripsComp obj) {// fait
		Statement stmt = null;
		try {
			stmt = this.connect.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			int result = stmt.executeUpdate("DELETE FROM trips WHERE route_id = " + obj.getRoute_id());
			System.out.println(result + " Row affected !");
			return (true);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return (false);
	}

	public boolean update(Route_Service obj) {
		Statement stmt = null;
		try {
			stmt = this.connect.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			int result = stmt.executeUpdate("UPDATE agency set  WHERE route_id = " + obj.getRoute_id());
			System.out.println(result + " Row affected !");
			return (true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return (false);
	}
	
	public boolean updateComp(TripsComp oldobj, TripsComp newobj) {
		try {
			deleteComp(oldobj);
			createComp(newobj);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
