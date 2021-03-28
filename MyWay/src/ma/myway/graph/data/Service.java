package ma.myway.graph.data;

import java.util.Date;

public class Service {

	String service_id;
	Integer dates[] = new Integer[7];
	Date start_date;
	Date end_date;

	public Service(String service_id, Integer[] dates, Date start_date, Date end_date) {
		this.service_id = service_id;
		this.dates = dates;
		this.start_date = start_date;
		this.end_date = end_date;
	}

	public String getService_id() {
		return service_id;
	}

	public Integer[] getDates() {
		return dates;
	}

	public Date getStart_date() {
		return start_date;
	}

	public Date getEnd_date() {
		return end_date;
	}
	
	

}
