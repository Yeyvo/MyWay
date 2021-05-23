package ma.myway.dao;

import java.sql.Connection;

import ma.myway.graph.data.Agency;
import ma.myway.graph.data.Stop;
import ma.myway.graph.data.Transfert;

public class DAOFactory {
	protected static final Connection conn = BddConnection.getInstance();

	public static StopDAO getStopDAO() {
		return new StopDAO(conn);
	}

	public static AgencyDAO getAgencyDAO() {
		return new AgencyDAO(conn);
	}

	public static ServiceDAO getServiceDAO() {
		return new ServiceDAO(conn);
	}

	public static RoutesDAO getRoutesDAO() {
		return new RoutesDAO(conn);
	}

	public static StopTripDAO getStopTripDAO() {
		return new StopTripDAO(conn);
	}

	public static TransfertDAO getTransfertDAO() {
		return new TransfertDAO(conn);
	}

	public static CalendarExpDAO getCalendarExpDAO() {
		return new CalendarExpDAO(conn);
	}

	public static UserDAO getUserDAO() {
		return new UserDAO(conn);
	}

}
