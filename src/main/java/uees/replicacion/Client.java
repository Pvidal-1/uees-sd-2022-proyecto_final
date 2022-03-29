package uees.replicacion;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
	public static void main(String[] args) throws InterruptedException {
		
		try {
			Scanner sn = new Scanner(System.in);
			sn.useDelimiter("\n");
			
			Socket sc = new Socket("127.0.0.1",5000);
			
			DataInputStream in = new DataInputStream(sc.getInputStream());
			DataOutputStream out = new DataOutputStream(sc.getOutputStream());
			
			String message = in.readUTF();
			System.out.println(message);
			
			String name = sn.next();
			out.writeUTF(name);
			
			ClientThread hilo = new ClientThread(in, out);
			hilo.start();
			hilo.join();
		
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
