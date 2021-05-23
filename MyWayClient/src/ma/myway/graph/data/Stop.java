package ma.myway.graph.data;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

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
 */
public class Stop implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3593100635691617875L;
	private String stop_id;
	private String name;
	private String desc;
	private double lat; // changement en double
	private double lon;
	private int location_type;

	/**
	 * for testing purposes only
	 * 
	 * @param stop_id
	 */
	public Stop(String stop_id) {
		this.stop_id = stop_id;
	}

	public Stop(String stop_id, String name, String desc, double lat, double lon, int location_type) {
		this.stop_id = stop_id;
		this.name = name;
		this.desc = desc;
		this.lat = lat;
		this.lon = lon;
		this.location_type = location_type;
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

	public double getLat() {
		return lat;
	}

	public double getLon() {
		return lon;
	}

	public int getLocation_type() {
		return location_type;
	}

	private void writeObject(ObjectOutputStream oos) throws IOException {
		oos.defaultWriteObject();
		oos.writeObject(this);

	}

	@Override
	public String toString() {
		return "'" + stop_id + "',null,'" + name + "','" + desc + "','" + String.valueOf(lat) + "','"
				+ String.valueOf(lon) + "','" + String.valueOf(location_type) + "',null";
	}

}
