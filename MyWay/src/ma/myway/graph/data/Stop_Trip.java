package ma.myway.graph.data;

import java.io.Serializable;
import java.sql.Time;
import java.util.List;
import java.util.Set;

public class Stop_Trip implements Comparable<Stop_Trip>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3892778082575559141L;
	String trip_id;
	String stop_id;
	Time arrival_time;
	Time departure_time;
	int stop_sequence;

	public Stop_Trip(String trip_id, String stop_id, Time arrival_time, Time departure_time, int stop_sequence) {
		this.trip_id = trip_id;
		this.stop_id = stop_id;
		this.arrival_time = arrival_time;
		this.departure_time = departure_time;
		this.stop_sequence = stop_sequence;
	}

	public String getStop_id() {
		return stop_id;
	}

	public Time getArrival_time() {
		return arrival_time;
	}

	public Time getDeparture_time() {
		return departure_time;
	}

	public String getTrip_id() {
		return trip_id;
	}

	public int getStop_sequence() {
		return stop_sequence;
	}

	public static Stop_Trip getNextStop_sequence(Set<Stop_Trip> st, String trip_id, int stop_sequence) {

		for (Stop_Trip stopTrip : st) {
			if ((stopTrip.trip_id.equals(trip_id)) && (stopTrip.getStop_sequence() == 1 + stop_sequence)) {
				return stopTrip;
			}
		}

		return null;
	}

	public static Stop_Trip getNextStop_sequence(List<Stop_Trip> st, String trip_id, int stop_sequence, int j) {
		if (j+1 < st.size()) {
			Stop_Trip nxt = st.get(j + 1);
			if (nxt.getTrip_id().equals(trip_id) && nxt.getStop_sequence() == stop_sequence + 1)
				return nxt;
		}
		return null;

	}

	@Override
	public int compareTo(Stop_Trip o) {

		if (Long.parseLong(o.getTrip_id()) - Long.parseLong(this.getTrip_id()) > 0) {
			return -1;
		} else if (Long.parseLong(o.getTrip_id()) - Long.parseLong(this.getTrip_id()) < 0) {
			return 1;
		} else if (Long.parseLong(o.getTrip_id()) - Long.parseLong(this.getTrip_id()) == 0) {
			if (o.getStop_sequence() - this.getStop_sequence() > 0) {
				return -1;
			} else if (o.getStop_sequence() - this.getStop_sequence() < 0) {
				return 1;
			} else if (o.getStop_sequence() - this.getStop_sequence() == 0) {
				return 0;
			}
		}
		return 0;
	}

}
