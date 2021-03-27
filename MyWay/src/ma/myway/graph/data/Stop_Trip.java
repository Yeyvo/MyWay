package ma.myway.graph.data;

import java.sql.Time;
import java.util.Set;

public class Stop_Trip {

	
	String trip_id;
	String stop_id;
	Time arrival_time;
	Time departure_time;
	int stop_sequence;

	public Stop_Trip(String trip_id,String stop_id, Time arrival_time, Time departure_time, int stop_sequence) {
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
	
	public static Stop_Trip getNextStop_sequence(Set<Stop_Trip> st, String trip_id , Time arrival_time, int stop_sequence ) {
		
		for( Stop_Trip stopTrip : st) {
			if( (stopTrip.trip_id == trip_id)  && (stopTrip.getStop_sequence() == 1 + stop_sequence)  && arrival_time.compareTo(stopTrip.getDeparture_time())>=0) {
				return stopTrip;
			}
		}
		
		return null;
	}
	

}
