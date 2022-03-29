package uees.replicacion;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String[] args) {
		try {
			ServerSocket server = new ServerSocket(5000);
			Socket sc;
			
			System.out.println("Servidor Iniciado");
			while(true) {
				
				sc = server.accept();
				
				DataInputStream in = new DataInputStream(sc.getInputStream());
				DataOutputStream out = new DataOutputStream(sc.getOutputStream());
				
				out.writeUTF("Indica tu nombre");
				String name = in.readUTF();
				
				ServerThread hilo = new ServerThread(in, out, name);
				hilo.start();
				
				System.out.println("Creada la conexion con el cliente " + name);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}