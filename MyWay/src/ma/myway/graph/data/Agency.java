package ma.myway.graph.data;

public class Agency {

	private String agency_id;
	private String agency_name;
	private String agency_url;
	private String agency_timezone;

	public Agency(String agency_id, String agency_name, String agency_url, String agency_timezone) {
		this.agency_id = agency_id;
		this.agency_name = agency_name;
		this.agency_url = agency_url;
		this.agency_timezone = agency_timezone;
	}

	public String getAgency_url() {
		return agency_url;
	}

	public void setAgency_url(String agency_url) {
		this.agency_url = agency_url;
	}

	public String getAgency_timezone() {
		return agency_timezone;
	}

	public void setAgency_timezone(String agency_timezone) {
		this.agency_timezone = agency_timezone;
	}

	public String getAgency_id() {
		return agency_id;
	}

	public void setAgency_id(String agency_id) {
		this.agency_id = agency_id;
	}

	public String getAgency_name() {
		return agency_name;
	}

	public void setAgency_name(String agency_name) {
		this.agency_name = agency_name;
	}

	@Override
	public String toString() {
		return "'" + agency_id + "','" + agency_name + "','" + agency_url + "','" + agency_timezone + "',null,null";
	}

}
