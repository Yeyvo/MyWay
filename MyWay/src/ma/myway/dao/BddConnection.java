package ma.myway.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

import ma.myway.config.Config;

public class BddConnection {

	private static String url = "jdbc:" + Config.getInstance().BDD + "://" + Config.getInstance().IP + ":"
			+ Config.getInstance().PRT + "/" + Config.getInstance().BDDNAME;
	private static String user = Config.getInstance().USR;
	private static String passwd = Config.getInstance().PSWD;
	private static Connection connect;

	public static Connection getInstance() {
		if (connect == null) {
			try {
				long StartTime = System.currentTimeMillis();
				Logger.getLogger("MyLog").info("Connection to BDD Started ! ");
				connect = DriverManager.getConnection(url, user, passwd);
				System.out.println("Connection succesful time : " + (System.currentTimeMillis() - StartTime) / 1000);
			} catch (SQLException e) {
				e.printStackTrace();
				Logger.getLogger("MyLog").info(" Error onnection DBDD " + e.getStackTrace());

//			    System.out.println("SQLException: " + e.getMessage());
//			    System.out.println("SQLState: " + e.getSQLState());
//			    System.out.println("VendorError: " + e.getErrorCode());		
			}
		}
		return connect;
	}
}
