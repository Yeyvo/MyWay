package ma.myway.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import ma.myway.graph.data.Stop;
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
		
		try {
			
			Statement stmt = this.connect.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			stmt.setFetchSize(Integer.MIN_VALUE);
			//stmt.setFetchDirection (ResultSet.FETCH_REVERSE);
			ResultSet result = stmt
					.executeQuery("SELECT trip_id,arrival_time,departure_time,stop_id,stop_sequence FROM stop_times ");// we can use different queries using limit and offset 
			// System.out.println("Starting to retrieve data. Memory Used: " + System.);
			while (result.next()) {
				set_stop_trip.add(new Stop_Trip(result.getString("trip_id"), result.getString("stop_id"),
						/*result.getTime("arrival_time")*/new Time(0, 0, 0),/* result.getTime("departure_time")*/new Time(0, 0, 0),
						result.getInt("stop_sequence"))); //problem in database time
				count++;
			}
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();

		}finally {
			Logger.getLogger("MyLog").info("The amount of data retrieved is " + count + " line!");;

		}

		return set_stop_trip;
	}
	

	public List<Stop_Trip> allList() {
		List<Stop_Trip> set_stop_trip = new ArrayList<>();
		long count = 0;
		try {
			
			Statement stmt = this.connect.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			stmt.setFetchSize(Integer.MIN_VALUE);
			ResultSet result = stmt
					.executeQuery("SELECT trip_id,arrival_time,departure_time,stop_id,stop_sequence FROM stop_times");// we can use different queries using limit and offset 
			while (result.next()) {
				set_stop_trip.add(new Stop_Trip(result.getString("trip_id"), result.getString("stop_id"),
						/*result.getTime("arrival_time")*/new Time(0, 0, 0),/* result.getTime("departure_time")*/new Time(0, 0, 0),
						result.getInt("stop_sequence"))); //problem in database time
				count++;
			}
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();

		}finally {
			Logger.getLogger("MyLog").info("The amount of data retrieved is " + count + " line!");;

		}

		Collections.sort( set_stop_trip);
		return set_stop_trip;
	}
	
	@Override
	public boolean create(Stop_Trip obj) {
		try {
			int result = this.connect.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)
					.executeUpdate("INSERT INTO stop_times VALUES("+obj.toString()+")");
			System.out.println(result +" Row affected ! ");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}



}
