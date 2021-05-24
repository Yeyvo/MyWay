package ma.myway.graph.data;

import java.io.Serializable;
import java.sql.Time;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public class Service implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5872163854493473938L;
	String service_id;
	Integer dates[] = new Integer[7];
	Date start_date;
	Date end_date; // changement de class necessaire

	private List<Date> added;
	private List<Date> removed;

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

	public List<Date> getRemoved() {
		return removed;
	}

	public void setRemoved(List<Date> removed) {
		this.removed = removed;
	}

	public List<Date> getAdded() {
		return added;
	}

	public void setAdded(List<Date> added) {
		this.added = added;
	}

	@Override
	public String toString() {
		String str = "";
		str += ("'"+service_id + "',");
		for (int jr : dates) {
			str += (jr + ",");
		}
		return str + start_date + "," + end_date;
	}

	public void toStr() {
		String str = "";
		str += ("'"+service_id + "', Normal days = [");
		for (int jr : dates) {
			str += (jr + ",");
		}
		Logger.getLogger("BASE").info(str+"] / " + start_date + "," + end_date + " added=" + Arrays.toString(added.toArray())
				+ ", removed=" + Arrays.toString(removed.toArray()) + "]");
	}

	public static int getDayNumber(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	public static Time datetimeload(String str) {
		Time result = new Time( 0, 0, 0);

		String strtmp[] = str.split(":");
		if (str.equals("24:00:00")) {
			result.setDate(1 + result.getDate());
			result.setHours(0);
			result.setMinutes(0);
			result.setSeconds(0);
		} else if (Integer.parseInt(strtmp[0]) >= 24) {
			result.setDate(1 + result.getDate());
			result.setHours(Integer.parseInt(strtmp[0]) - 24);
			result.setMinutes(Integer.parseInt(strtmp[1]));
			result.setSeconds(Integer.parseInt(strtmp[2]));
		} else {
			result.setHours(Integer.parseInt(strtmp[0]));
			result.setMinutes(Integer.parseInt(strtmp[1]));
			result.setSeconds(Integer.parseInt(strtmp[2]));
		}
		strtmp = null;
		return result;
	}

}
