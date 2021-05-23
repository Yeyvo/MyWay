package ma.myway.graph.data;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class CalendarExpComp {
	private String service_id;
	private Date added;
	private int type;

	public CalendarExpComp(String service_id, Date added, int type) {
		this.service_id = service_id;
		this.added = added;
		this.type = type;
	}

	public String getService_id() {
		return service_id;
	}

	public void setService_id(String service_id) {
		this.service_id = service_id;
	}

	public Date getAdded() {
		return added;
	}

	public void setAdded(Date added) {
		this.added = added;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
