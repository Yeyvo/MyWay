package ma.myway.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import ma.myway.graph.data.Stop;

public class StopDAO extends DAO<Stop> {

	public StopDAO(Connection conn) {
		super(conn);
	}

	@Override
	public Stop find(String stop_id) {

		Stop stop = null;
		try {
			ResultSet result = this.connect
					.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
					.executeQuery("SELECT * FROM stops WHERE stop_id = " + stop_id);
			if (result.first())
				stop = new Stop(stop_id, result.getString("stop_name"), result.getString("stop_desc"),
						result.getFloat("stop_lat"), result.getFloat("stop_lon"), result.getInt("location_type"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return stop;
	}

	@Override
	public Set<Stop> all() {
		Set<Stop> set_stops = new HashSet<>();
		try {
			ResultSet result = this.connect.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)
					.executeQuery("SELECT * FROM stops");
			while (result.next()) {
				set_stops.add(new Stop(result.getString("stop_id"), result.getString("stop_name"),
						result.getString("stop_desc"), /* result.getFloat("stop_lat") */ 3, result.getFloat("stop_lon"),
						result.getInt("location_type")));
			}
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return set_stops;
	}

}
