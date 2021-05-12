package ma.myway.users;

import java.util.Date;

public class User {

	private String name;
	private String password;
	private int id;
	private Date creation;

	public User(String name, String password) {
		this.name = name;
		this.password = password;
	}
	
	public User(int id,String name, String password,Date creation) {
		this.name = name;
		this.password = password;
		this.id=id;
		this.creation=creation;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "'"+name + "','" + password+"'" ;
	}
	
	public String toStringall() { //méthode à utiliser avec la méthode all de DAO
		return id+",'"+name+"','"+password+",',"+creation;
	}
	

}
