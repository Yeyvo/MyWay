package ma.myway.users;

import java.util.Date;

public class User {

	private String name;
	private String password;
	private int id;
	private Date creation;
	private String perm;

	public User(String name, String password) {
		this.name = name;
		this.password = password;
	}

	public User(int id, String name, String password, Date creation, String lvl) {
		this.name = name;
		this.password = password;
		this.id = id;
		this.creation = creation;
		this.perm = lvl;
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
		return "'" + name + "','" + password + "','" + perm + "'";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreation() {
		return creation;
	}

	public void setCreation(Date creation) {
		this.creation = creation;
	}

	public String getPerm() {
		return perm;
	}

	public void setPerm(String perm) {
		this.perm = perm;
	}
	

}
