package ma.myway;

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

	public String getStop_id() {
		return stop_id;
	}

	public String getName() {
		return name;
	}

	public String getDesc() {
		return desc;
	}

	public float getLat() {
		return lat;
	}

	public float getLon() {
		return lon;
	}

	public int getLocation_type() {
		return location_type;
	}
	
	
}
