package ma.myway.graph.data;

public class Transfert {

	String src_stop_id;
	String dest_stop_id;
	int transfert_type;
	int transfert_time;
	
	public Transfert(String src_stop_id, String dest_stop_id, int transfert_type, int transfert_time) {
		this.src_stop_id = src_stop_id;
		this.dest_stop_id = dest_stop_id;
		this.transfert_type = transfert_type;
		this.transfert_time = transfert_time;
	}

	public String getSrc_stop_id() {
		return src_stop_id;
	}

	public String getDest_stop_id() {
		return dest_stop_id;
	}

	public int getTransfert_type() {
		return transfert_type;
	}

	public int getTransfert_time() {
		return transfert_time;
	}

	@Override
	public String toString() {
		return  src_stop_id + "," + dest_stop_id + ","
				+ transfert_type + "," + transfert_time ;
	}
	
	

}
