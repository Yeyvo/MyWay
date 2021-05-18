package ma.myway.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import ma.myway.users.User;

public class UserDAO extends DAO<User> {

	public UserDAO(Connection conn) {
		super(conn);
	}

	@Override
	public boolean create(User obj) {// works
		Statement stmt = null;
		try {
			stmt = this.connect.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			ResultSet result = stmt.executeQuery("SELECT max(user_id) FROM users");
			result.next();
			boolean result1 = stmt.execute("ALTER TABLE users AUTO_INCREMENT = " + result.getInt(1));
			int result2 = stmt.executeUpdate("INSERT INTO users(username,password) VALUES(" + obj.toString() + ")");
			System.out.println(result2 + " Row affected ! ");
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

	public boolean find(User data) {// works
		Statement stmt = null;
		try {
			stmt = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet result = stmt.executeQuery("select * from users where username = \"" + data.getName()
					+ "\" and password = \"" + data.getPassword() + "\"");

			int size = 0;
			if (result != null) {
				result.last();
				size = result.getRow();
			}

			if (size >= 1) {
				return true;
			} else {
				return false;
			}
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
	public Set<User> all() { // works
		Set<User> set_users = new HashSet<>();
		ResultSet result = null;
		Statement stmt = null;
		try {
			stmt = this.connect.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			result = stmt.executeQuery("SELECT * FROM users");
			while (result.next()) {
				set_users.add(new User(result.getInt("user_id"),
						result.getString("username").toLowerCase().replaceAll("é", "e").replaceAll("è", "e"),
						result.getString("password").toLowerCase(), result.getDate("dateCreation")));
			}
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				result.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return set_users;
	}

	@Override
	@Deprecated
	public User find(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(User obj) { // works
		Statement stmt = null;
		try {
			stmt = this.connect.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			int result = stmt.executeUpdate("DELETE FROM users WHERE username = '" + obj.getName() + "'");
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
	public boolean update(User oldobj, User newobj) {// works
		try {
			delete(oldobj);
			create(newobj);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
