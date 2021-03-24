package ma.myway;

/**
 * C'est la classe representant chaque <b>station</b> du reseau de transport.
 * <p>
 * Chaque instance est caractérisée par:
 * <ul>
 * <li>un identifiant <b>stop_id</b></li>
 * <li>un nom <b>name</b></li>
 * <li>une description <b>desc</b></li>
 * <li>Des coordonnées: <b>lat</b> et <b>lon</b></li>
 * <li><b>location_type</b></li>
 * <ul>
 * </p>
 * 
 * @see Node
 * @author Yeyvo
 */
public class Stop {

	private String stop_id;
	private String name;
	private String desc;
	private float lat;
	private float lon;
	private int location_type;

	public Stop(String stop_id, String name, String desc, float lat, float lon, int location_type) {
		super();
		this.stop_id = stop_id;
		this.name = name;
		this.desc = desc;
		this.lat = lat;
		this.lon = lon;
		this.location_type = location_type;
	}

	/**
	 * getter
	 * 
	 * @return String
	 */
	public String getStop_id() {
		return stop_id;
	}

	/**
	 * getter
	 * 
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * getter
	 * 
	 * @return String
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * getter
	 * 
	 * @return float
	 */
	public float getLat() {
		return lat;
	}

	/**
	 * getter
	 * 
	 * @return float
	 */
	public float getLon() {
		return lon;
	}

	/**
	 * getter
	 * 
	 * @return int
	 */
	public int getLocation_type() {
		return location_type;
	}

}
