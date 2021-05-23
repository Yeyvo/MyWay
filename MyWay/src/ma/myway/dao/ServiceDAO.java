package ma.myway.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ma.myway.graph.data.CalendarExpComp;
import ma.myway.graph.data.Service;
import ma.myway.graph.data.ServiceComp;

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
			ResultSet result = stmt.executeQuery("SELECT * FROM calendar WHERE service_id = \"" + service_id + "\"");

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

	
	public Set<ServiceComp> allSet() {
		Set<ServiceComp> data = new HashSet<>();
		Statement stmt = null;
		try {
			stmt = this.connect.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			ResultSet result = stmt.executeQuery("SELECT * FROM calendar_dates");
			while (result.next()) {
				data.add(new ServiceComp(result.getString(1), result.getInt(2), result.getInt(3), result.getInt(4), result.getInt(5), result.getInt(6), result.getInt(7), result.getInt(8), result.getDate(9), result.getDate(10)));
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

	@Override
	public boolean delete(Service obj) {// fait
		Statement stmt = null;
		try {
			stmt = this.connect.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			int result = stmt.executeUpdate("DELETE FROM calendar WHERE service_id = " + obj.getService_id());
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

	@Override
	public boolean update(Service oldobj, Service newobj) {// works
		try {
			delete(oldobj);
			create(newobj);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	@Deprecated
	public Set<Service> all() {
		return null;
	}

}
