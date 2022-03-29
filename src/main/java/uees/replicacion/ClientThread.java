package uees.replicacion;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class ClientThread extends Thread {
	
	private DataInputStream in;
	private DataOutputStream out;
	
	public ClientThread(DataInputStream in, DataOutputStream out) {
		this.in = in;
		this.out = out;
	}
	
	@Override
	public void run() {
		
	}
	
}
