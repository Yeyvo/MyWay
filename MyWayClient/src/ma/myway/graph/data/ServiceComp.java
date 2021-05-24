package ma.myway.graph.data;

import java.io.Serializable;
import java.time.LocalDate;

public class ServiceComp implements Serializable {

	String service_id;
	int monday, tuesday, wednesday, thursday, friday, saturday, sunday;
	LocalDate start_date;
	LocalDate end_date;

	public ServiceComp(String service_id, int monday, int tuesday, int wednesday, int thursday, int friday,
			int saturday, int sunday, LocalDate start_date, LocalDate end_date) {
		this.service_id = service_id;
		this.monday = monday;
		this.tuesday = tuesday;
		this.wednesday = wednesday;
		this.thursday = thursday;
		this.friday = friday;
		this.saturday = saturday;
		this.sunday = sunday;
		this.start_date = start_date;
		this.end_date = end_date;
	}

	public String getService_id() {
		return service_id;
	}

	public void setService_id(String service_id) {
		this.service_id = service_id;
	}

	public int getMonday() {
		return monday;
	}

	public void setMonday(int monday) {
		this.monday = monday;
	}

	public int getTuesday() {
		return tuesday;
	}

	public void setTuesday(int tuesday) {
		this.tuesday = tuesday;
	}

	public int getWednesday() {
		return wednesday;
	}

	public void setWednesday(int wednesday) {
		this.wednesday = wednesday;
	}

	public int getThursday() {
		return thursday;
	}

	public void setThursday(int thursday) {
		this.thursday = thursday;
	}

	public int getFriday() {
		return friday;
	}

	public void setFriday(int friday) {
		this.friday = friday;
	}

	public int getSaturday() {
		return saturday;
	}

	public void setSaturday(int saturday) {
		this.saturday = saturday;
	}

	public int getSunday() {
		return sunday;
	}

	public void setSunday(int sunday) {
		this.sunday = sunday;
	}

	public LocalDate getStart_date() {
		return start_date;
	}

	public void setStart_date(LocalDate start_date) {
		this.start_date = start_date;
	}

	public LocalDate getEnd_date() {
		return end_date;
	}

	public void setEnd_date(LocalDate end_date) {
		this.end_date = end_date;
	}

	@Override
	public String toString() {
//		String str = "";
//		str += ("'"+service_id + "',");
//		for (int jr : dates) {
//			str += (jr + ",");
//		}
		return "'" + service_id + "'," + monday + "," + tuesday + "," + wednesday + "," + thursday + "," + friday + ","
				+ saturday + "," + sunday + ",'" + start_date.toString() + "','" + end_date.toString() + "'";
	}

}
