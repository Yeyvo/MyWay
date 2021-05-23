package ma.myway.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import ma.myway.graph.data.CalendarExp;
import ma.myway.graph.data.CalendarExpComp;

public class CalendarExpDAO extends DAO<CalendarExp> {

	public CalendarExpDAO(Connection conn) {
		super(conn);
	}

	public boolean create(String service_id, Date date,int exception_type) {
		Statement stmt = null;
		try {
			stmt = this.connect.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			int result = stmt.executeUpdate("INSERT INTO calendar_dates VALUES('" + service_id + "','" + date + "','" + exception_type + "')");
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
	public CalendarExp find(String id) {
		return null;
	}

	public HashMap<String, CalendarExp> allMap() {
		HashMap<String, CalendarExp> set_CalendarExp = new HashMap<>();
		Statement stmt = null;
		try {
			stmt = this.connect.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			ResultSet result = stmt.executeQuery("SELECT * FROM calendar_dates");
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
	public boolean delete(CalendarExp obj) {// fait
		Statement stmt = null;
		try {
			stmt = this.connect.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			int result = stmt.executeUpdate("DELETE FROM calendar_dates WHERE service_id = " + obj.getService_id());
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

	public boolean update(CalendarExp obj) {
		Statement stmt = null;
		try {
			stmt = this.connect.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			int result = stmt.executeUpdate("UPDATE agency set  WHERE service_id = " + obj.getService_id());
			System.out.println(result + " Row affected !");
			return (true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return (false);
	}


	public Set<CalendarExpComp> allSet() {
		Set<CalendarExpComp> set_CalendarExp = new HashSet<>();
		Statement stmt = null;
		try {
			stmt = this.connect.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			ResultSet result = stmt.executeQuery("SELECT * FROM calendar_dates");
			while (result.next()) {
				set_CalendarExp.add(new CalendarExpComp(result.getString(1), result.getDate(2), result.getInt(3)));
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
	public boolean create(CalendarExp obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	@Deprecated
	public Set<CalendarExp> all() {
		// TODO Auto-generated method stub
		return null;
	}
}
