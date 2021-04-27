package ma.myway.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import ma.myway.graph.data.Service;
import ma.myway.graph.data.Stop_Trip;

public class StopTripDAO extends DAO<Stop_Trip> {

	public StopTripDAO(Connection conn) {
		super(conn);
	}

	@Override
	public Stop_Trip find(String id) {
		return null;
	}

	@Override
	@Deprecated
	public Set<Stop_Trip> all() {
		Set<Stop_Trip> set_stop_trip = new HashSet<>();
		long count = 0;
		Statement stmt = null;
		try {

			stmt = this.connect.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			stmt.setFetchSize(Integer.MIN_VALUE);
			// stmt.setFetchDirection (ResultSet.FETCH_REVERSE);
			ResultSet result = stmt
					.executeQuery("SELECT trip_id,arrival_time,departure_time,stop_id,stop_sequence FROM stop_times ");

			// System.out.println("Starting to retrieve data. Memory Used: " + System.);
			while (result.next()) {
				set_stop_trip.add(new Stop_Trip(result.getString("trip_id"), result.getString("stop_id"),
						Service.datetimeload(result.getString("arrival_time")),
						Service.datetimeload(result.getString("departure_time")), result.getInt("stop_sequence")));
				count++;
			}
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			Logger.getLogger("BASE").info("The amount of data retrieved is " + count + " line!");
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		return set_stop_trip;
	}

	public List<Stop_Trip> allList() {
		List<Stop_Trip> set_stop_trip = new ArrayList<>();
		long count = 0;
		Statement stmt = null;
		try {

			stmt = this.connect.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			stmt.setFetchSize(Integer.MIN_VALUE);
			// stmt.setFetchSize(100);
			ResultSet result = stmt.executeQuery(
					"SELECT trip_id,arrival_time,departure_time,stop_id,stop_sequence FROM stop_times ");
			while (result.next()) {
				set_stop_trip.add(new Stop_Trip(result.getString("trip_id"), result.getString("stop_id"),
						Service.datetimeload(result.getString("arrival_time")),
						Service.datetimeload(result.getString("departure_time")), result.getInt("stop_sequence")));
				count++;
			}
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			Logger.getLogger("BASE").info("The amount of data retrieved is " + count + " line!");
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		Collections.sort(set_stop_trip);
		return set_stop_trip;
	}

	@Override
	public boolean create(Stop_Trip obj) {
		Statement stmt = null;
		try {
			stmt = this.connect.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			int result = stmt.executeUpdate("INSERT INTO stop_times VALUES(" + obj.toString() + ")");
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

}
