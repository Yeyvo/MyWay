package ma.myway.dao;

import java.sql.Connection;

import ma.myway.graph.data.Agency;
import ma.myway.graph.data.CalendarExp;
import ma.myway.graph.data.Route_Service;
import ma.myway.graph.data.Stop;
import ma.myway.graph.data.Transfert;

public class DAOFactory {
	protected static final Connection conn = BddConnection.getInstance();

	public static DAO<Stop> getStopDAO() {
		return new StopDAO(conn);
	}

	public static DAO<Agency> getAgencyDAO() {
		return new AgencyDAO(conn);
	}

	public static ServiceDAO getServiceDAO() {
		return new ServiceDAO(conn);
	}

	public static DAO<Route_Service> getRoutesDAO() {
		return new RoutesDAO(conn);
	}

	public static StopTripDAO getStopTripDAO() { //MODIFIED
		return new StopTripDAO(conn);
	}

	public static DAO<Transfert> getTransfertDAO() {
		return new TransfertDAO(conn);
	}
	
	public static CalendarExpDAO getCalendarExpDAO() {
		return new CalendarExpDAO(conn);
	}

}
