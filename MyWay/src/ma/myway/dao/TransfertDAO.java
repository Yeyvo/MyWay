package ma.myway.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import ma.myway.graph.data.Agency;
import ma.myway.graph.data.Route_Service;
import ma.myway.graph.data.Transfert;

public class TransfertDAO extends DAO<Transfert> {

	public TransfertDAO(Connection conn) {
		super(conn);
	}

	@Override

	public Transfert find(String id) {//still thinking
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Transfert> all() {//works
		Set<Transfert> set_transferts = new HashSet<>();
		Statement stmt = null;
		try {
			stmt = this.connect.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			ResultSet result = stmt.executeQuery("SELECT * FROM transfers");
			while (result.next()) {
				set_transferts.add(new Transfert(result.getString("from_stop_id"), result.getString("to_stop_id"),
						result.getInt("transfer_type"), result.getInt("min_transfer_time")));
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

		return set_transferts;
	}

	@Override
	public boolean create(Transfert obj) {//works
		Statement stmt = null;
		try {
			stmt = this.connect.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			int result = stmt.executeUpdate("INSERT INTO transfers VALUES(" + obj.toString() + ")");
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
	public boolean delete(Transfert obj) {//works
		Statement stmt=null;
		try {
			stmt = this.connect.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			int result = stmt.executeUpdate("DELETE FROM transfers WHERE from_stop_id = "+obj.getSrc_stop_id()+" and to_stop_id = "+obj.getDest_stop_id());
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
	
	public boolean update(Transfert oldobj,Transfert newobj) {//works
		try {
			delete(oldobj);
			create(newobj);
			return true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
