package ma.myway.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import ma.myway.graph.data.Agency;
import ma.myway.graph.data.Stop;

public class AgencyDAO extends DAO<Agency> {

	public AgencyDAO(Connection conn) {
		super(conn);
	}

	@Override
	public Agency find(String agency_id) {//works

		Agency stop = null;
		Statement stmt = null;
		ResultSet result = null;

		try {
			stmt = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			 result = stmt.executeQuery("SELECT * FROM agency WHERE agency_id = '" +agency_id+"'");
			if (result.first())
				stop = new Agency(result.getString("agency_id"), result.getString("agency_name"),result.getString("agency_url"),result.getString("agency_timezone"));
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				stmt.close();
				result.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return stop;
	}

	@Override
	public Set<Agency> all() {//works
		Set<Agency> set_agency = new HashSet<>();
		Statement stmt = null;
		try {
			stmt = this.connect.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			ResultSet result = stmt.executeQuery("SELECT * FROM agency");
			while (result.next()) {
				set_agency.add(new Agency(result.getString("agency_id"), result.getString("agency_name"),result.getString("agency_url"),result.getString("agency_timezone")));
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

		return set_agency;
	}

	@Override
	public boolean create(Agency obj) {//works
		Statement stmt = null;
		try {
			stmt = this.connect.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			int result = stmt.executeUpdate("INSERT INTO agency VALUES(" + obj.toString() + ")");
			System.out.println(result + " Row affected ! ");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public boolean delete(Agency obj) { //works
		Statement stmt=null;
		try {
			stmt = this.connect.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			int result = stmt.executeUpdate("DELETE FROM agency WHERE agency_id = '"+obj.getAgency_id()+"'");
			System.out.println(result + " Row affected !");
			return(true);
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				stmt.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return(false);
	}
	
	public boolean update(Agency oldobj,Agency newobj) {//works
		try {
			delete(oldobj);
			create(newobj);
			return true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/*public boolean update(String id,Agency newobj) {//machakil
		Statement stmt=null;
		try {
			stmt = this.connect.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			int result = stmt.executeUpdate("UPDATE agency set 	agency_id= '"+newobj.getAgency_id()+"' and agency_name = '"+newobj.getAgency_name()
			+"' and agency_url = '"+newobj.getAgency_url()+"' and agency_timezone = '"+newobj.getAgency_timezone()+ "' WHERE agency_id = '"+id+"'");
			System.out.println(result + " Row affected !");
			return(true);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return(false);
	}*/

}
