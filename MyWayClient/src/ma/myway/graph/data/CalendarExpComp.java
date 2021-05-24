package ma.myway.graph.data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class CalendarExpComp implements Serializable{
	private String service_id;
	private LocalDate added;
	private int type;

	public CalendarExpComp(String service_id, LocalDate added, int type) {
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

	public LocalDate getAdded() {
		return added;
	}

	public void setAdded(LocalDate added) {
		this.added = added;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "'" + service_id + "','" + added + "'," + type;
	}

}
