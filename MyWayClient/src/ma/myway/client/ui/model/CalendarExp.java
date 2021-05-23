package ma.myway.client.ui.model;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class CalendarExp {

	private String service_id;
	private List<Date> added;
	private List<Date> removed;

	public CalendarExp(String service_id, List<Date> added, List<Date> removed) {
		this.service_id = service_id;
		this.added = added;
		this.removed = removed;
	}

	public String getService_id() {
		return service_id;
	}

	public void setService_id(String service_id) {
		this.service_id = service_id;
	}

	public List<Date> getAdded() {
		return added;
	}

	public void setAdded(List<Date> added) {
		this.added = added;
	}

	public List<Date> getRemoved() {
		return removed;
	}

	public void setRemoved(List<Date> removed) {
		this.removed = removed;
	}

	@Override
	public String toString() {
		return "CalendarExp [service_id=" + service_id + ", added=" + Arrays.toString(added.toArray()) + ", removed="
				+ Arrays.toString(removed.toArray()) + "]";
	}

}
