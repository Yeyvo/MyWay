package ma.myway.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import ma.myway.graph.data.Agency;
import ma.myway.graph.data.Service;

public class ServiceDAO extends DAO<Service> {

	String[] days = { "mnday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday" };

	public ServiceDAO(Connection conn) {
		super(conn);
	}

	@Override
	public Service find(String service_id) {
		Service stop = null;
		try {
			ResultSet result = this.connect.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)
					.executeQuery("SELECT * FROM calendar WHERE service_id = " + service_id);

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
		}
		return stop;
	}

	@Override
	public Set<Service> all() {
		Set<Service> set_sevices = new HashSet<>();
		try {
			ResultSet result = this.connect.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)
					.executeQuery("SELECT * FROM calendar");
			while (result.next()) {
				Integer dates[] = new Integer[7];
				for (int i = 0; i < 7; i++) {
					dates[i] = result.getInt(days[i]);
				}
				set_sevices.add(new Service(result.getString("service_id"), dates, result.getDate("start_date"),
						result.getDate("end_date")));
			}
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return set_sevices;
	}

}
