package ma.myway.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import ma.myway.graph.data.CalendarExp;

public class CalendarExpDAO extends DAO<CalendarExp> {

	public CalendarExpDAO(Connection conn) {
		super(conn);
	}

	@Override
	@Deprecated
	public boolean create(CalendarExp obj) {
		return false;
	}

	@Override
	@Deprecated
	public CalendarExp find(String id) {
		return null;
	}

	public HashMap<String, CalendarExp> allMap() {
		HashMap<String, CalendarExp> set_CalendarExp = new HashMap<>();
		Statement stmt = null;
		try {
			stmt = this.connect.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
				ResultSet result = 	stmt.executeQuery("SELECT * FROM calendar_dates");
			while (result.next()) {
				if (!set_CalendarExp.containsKey(result.getString(1))) {
					List<Date> added = new LinkedList<Date>();
					List<Date> removed = new LinkedList<Date>();
					set_CalendarExp.put(result.getString(1), new CalendarExp(result.getString(1), added, removed));
				}
				if (result.getInt(3) == 1) {
					set_CalendarExp.get(result.getString(1)).getAdded().add(result.getDate(2));
				} else if (result.getInt(3) == 2) {
					set_CalendarExp.get(result.getString(1)).getRemoved().add(result.getDate(2));
				}
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

		return set_CalendarExp;
	}

	@Override
	@Deprecated
	public Set<CalendarExp> all() {
		// TODO Auto-generated method stub
		return null;
	}
}
