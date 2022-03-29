package uees.replicacion;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class ServerThread extends Thread {
	
	private DataInputStream in;
	private DataOutputStream out;
	private String name;
	
	public ServerThread(DataInputStream in, DataOutputStream out, String name) {
		this.in = in;
		this.out = out;
		this.name = name;
	}
	
	@Override
	public void run() {
		
	}
}
