package ma.myway.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import ma.myway.graph.data.Transfert;

public class TransfertDAO extends DAO<Transfert> {

	public TransfertDAO(Connection conn) {
		super(conn);
	}

	@Override
	public Transfert find(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Transfert> all() {
		Set<Transfert> set_transferts = new HashSet<>();
		try {
			ResultSet result = this.connect.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)
					.executeQuery("SELECT * FROM transfers");
			while (result.next()) {
				set_transferts.add(new Transfert(result.getString("from_stop_id"), result.getString("to_stop_id"),
						result.getInt("transfer_type"), result.getInt("min_transfer_time")));
			}
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return set_transferts;
	}
}
