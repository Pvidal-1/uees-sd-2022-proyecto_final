package uees.replicacion;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketThread extends Thread {
	private String ip;
	private int port;
	private boolean isObject;
	private String string;
	private Socket sc;
	private ServerSocket server;
	
	public SocketThread(String ip, int port, boolean isObject, String string) throws UnknownHostException, IOException {
		this.ip = ip;
		this.port = port;
		this.isObject = isObject;
		this.sc = new Socket(this.ip, this.port);
		this.server = new ServerSocket(this.port);
		this.string = string;
	}
	
	@Override
	public void run() {
		
		try {

			DataInputStream in = new DataInputStream(sc.getInputStream());
			DataOutputStream out = new DataOutputStream(sc.getOutputStream());
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		
	}
}
