package ma.myway.client.network;


public class Stop{

	/**
	 * 
	 */
	private String stop_id;
	private String name;
	private String desc;

	/**
	 * for testing purposes only
	 * 
	 * @param stop_id
	 */
	public Stop(String stop_id) {
		this.stop_id = stop_id;
	}

	public Stop(String stop_id, String name, String desc) {
		this.stop_id = stop_id;
		this.name = name;
		this.desc = desc;

	}

	public String getStop_id() {
		return stop_id;
	}

	public String getName() {
		return name;
	}

	public String getDesc() {
		return desc;
	}

}
