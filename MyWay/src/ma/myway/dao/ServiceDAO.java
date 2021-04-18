package ma.myway.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import ma.myway.graph.data.Service;

public class ServiceDAO extends DAO<Service> {

	String[] days = { "monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday" };

	public ServiceDAO(Connection conn) {
		super(conn);
	}

	@Override
	public Service find(String service_id) {
		Service stop = null;
		Statement stmt = null;
		try {
			stmt = this.connect.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
					ResultSet result = stmt.executeQuery("SELECT * FROM calendar WHERE service_id = " + service_id);

			if (result.first()) {

				Integer dates[] = new Integer[7];
				for (int i = 0; i < 7; i++) {
					dates[i] = result.getInt(days[i]);
				}

				stop = new Service(result.getString("service_id"), dates, result.getDate("start_date"),
						result.getDate("end_date"));

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
		return stop;
	}

	public Map<String, Service> allMap() {
		Map<String, Service> map_sevices = new HashMap<>();
		Statement stmt = null;

		try {
			stmt = this.connect.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			ResultSet result = stmt.executeQuery("SELECT * FROM calendar");
			while (result.next()) {
				Integer dates[] = new Integer[7];
				for (int i = 0; i < 7; i++) {
					dates[i] = result.getInt(days[i]);
				}
				map_sevices.put(result.getString("service_id"), new Service(result.getString("service_id"), dates,
						result.getDate("start_date"), result.getDate("end_date")));
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

		return map_sevices;
	}

	@Override
	public boolean create(Service obj) {
		Statement stmt = null;
		try {
			stmt = this.connect.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			int result = stmt.executeUpdate("INSERT INTO calendar VALUES(" + obj.toString() + ")");
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
	@Deprecated
	public Set<Service> all() {
		// TODO Auto-generated method stub
		return null;
	}

}
