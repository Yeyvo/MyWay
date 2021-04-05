package ma.myway.graph.data;

public class Agency {
	
	private String agency_id ;
	private String agency_name ;
	
	
	public Agency(String agency_id, String agency_name) {
		this.agency_id = agency_id;
		this.agency_name = agency_name;
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
		return agency_id + "," + agency_name + ",null,null,null,";
	}
	
	


}
