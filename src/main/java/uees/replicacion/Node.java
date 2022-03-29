package uees.replicacion;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Node extends Thread {

	private String ip;
	private Integer port;
	private Boolean isLeader;
	private Socket leader;
	private File log;
	private Writer wr;
	private Heartbeat timer;
	private MessageService mservice;
	private MessageResource mresource;
	private Database database;

	public Node(String ip, Integer port) {
		this.ip = ip;
		this.port = port;
		this.log = new File("logs.txt");
		this.wr = new Writer("logs.txt");
		this.timer = new Heartbeat();
		this.mservice = new MessageService();
		this.mresource = new MessageResource();
		this.database = new Database();
	}

	@Override
	public void run() {
		try {
			
			// Socket propio del nodo (no cambia)
			// Socket sc = new Socket(ip, port);
			// Socket server del nodo (cambia)
			// ServerSocket server = new ServerSocket(port);

			// Revisa si es lider o no
			while (true) {

				//Determinar si debería ser líder o no
				try {
					Socket try_leader = new Socket(ip, port);
					DataInputStream in = new DataInputStream(try_leader.getInputStream());
					String try_beat = in.readUTF();
					this.isLeader = false;
					try_leader.close();
				} catch (Exception trye) {
					this.isLeader = true;
				}
				
				System.out.println("Ip: " + this.ip + " || " + this.ip.getClass());
				System.out.println("Port: " + this.port + " || " + this.port.getClass());
				System.out.println("isleader: " + this.isLeader + " || " + this.isLeader.getClass());


				if (this.isLeader == true) {
					System.out.println("Nodo (" + this.ip + ") se convirtió en Lider");
					// Socket server del nodo
					ServerSocket server = new ServerSocket(port);

					// Asigno el socket de lider al suyo
					// this.leader = sc;

					// Lista para recordar los seguidores
					ArrayList<Socket> seguidores = new ArrayList<>();

					while (true) {
						// Esperar a que se conecte seguidor
						Socket sc2 = server.accept();
						System.out.println("Seguidor conectado");
						seguidores.add(sc2);

						DataInputStream in = new DataInputStream(sc2.getInputStream());
						DataOutputStream out = new DataOutputStream(sc2.getOutputStream());

						// Enviar pulso
						System.out.println("Envio: <3");
						out.writeUTF("<3");

						System.out.println("MessagesR: " + mresource.getMessages());
						System.out.println("MessagesS: " + mservice.getAllMessages());

						// Get message de message resource (Puerto 8080)
						/*Socket sc = new Socket(ip, 8080);
						ObjectInputStream ois = new ObjectInputStream(sc.getInputStream());
						Message recieved = new Message();
						try {
							recieved = (Message) ois.readObject();
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						mresource.addMessage(recieved);
						
						System.out.println("MessagesR: " + mresource.getMessages());
						System.out.println("MessagesS: " + mservice.getAllMessages());*/

						/*
						 * for(Message msj : mservice.getAllMessages()) { wr.writeToLog("GET", msj); }
						 */

					}
				} else {
					System.out.println("Nodo inicializado como seguidor");

					// Socket propio del nodo (no cambia)
					Socket sc = new Socket(ip, port);

					// Iniciar timer
					timer.start();
					String pulse = "";
					while (true) {

						DataInputStream in = new DataInputStream(sc.getInputStream());
						DataOutputStream out = new DataOutputStream(sc.getOutputStream());

						// Recibio pulso???
						try {
							pulse = in.readUTF();
							if (pulse.equals("<3")) {
								timer.setConnected(true);
							}
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							pulse = "</3";
							timer.setConnected(false);
							//System.out.println("\nTime: " + timer.getTime());

						}

						// Se termino el tiempo???
						if (timer.isTimeout() == true) {
							break;
						}

						try {
							System.out.println("Recibo: " + pulse);
							//System.out.println("Connected? " + timer.isConnected());
							TimeUnit.SECONDS.sleep(1);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					System.out.println("Ya no hay lider... Yo soy el nuevo lider");
					timer.setTimeout(true);
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					this.isLeader = true;
				}
			}

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
