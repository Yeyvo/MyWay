package ma.myway.network;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

import ma.myway.config.Config;
import ma.myway.dao.BddConnection;

public class Server {

	private int port = Integer.parseInt(Config.getInstance().SERVERPORT);
	private String host = Config.getInstance().SERVERIP;
	private ServerSocket server = null;
	private boolean isRunning = true;

	public Server(int maxConnection) {
		try {
			server = new ServerSocket(this.port, maxConnection, Inet4Address.getByName(this.host));
			Logger.getLogger("SERVER").info("Server launched at  " + InetAddress.getLocalHost() +":"+port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void open() {

		Thread server_t = new Thread(new Runnable() {

			@Override
			public void run() {
				while (isRunning) {
					try {
						Socket client = server.accept();
						Logger.getLogger("SERVER").info("connection established ");
						Thread client_t = new Thread(new ClientProcessor(client));
						client_t.start();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
				try {
					server.close();
					BddConnection.close();
				} catch (IOException e) {
					e.printStackTrace();
					server = null;
				}
			}

		});
		
		Logger.getLogger("SERVER").info("Launching Server ");
		server_t.start();
		
	}

	public void close() {
		isRunning = false;
	}

}
