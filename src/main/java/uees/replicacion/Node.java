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
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Node extends Thread {

	private String ip;
	private Integer port;
	private Boolean isLeader;
	private Socket leader;
	private File log;
	private Writer wr;
	//private Timer timer;
	private MessageService mservice;
	private MessageResource mresource;
	private Database database;

	public Node(String ip, Integer port) {
		this.ip = ip;
		this.port = port;
		this.log = new File("logs.txt");
		this.wr = new Writer("logs.txt");
		//this.timer = new Timer();
		this.mservice = new MessageService();
		this.mresource = new MessageResource();
		this.database = new Database();
	}

	@Override
	public void run() {
		try {
			
			while (true) {

				// DETECTAR SI EXISTE UN LIDER
				try {
					Socket try_leader = new Socket(ip, 5000);
					DataInputStream in = new DataInputStream(try_leader.getInputStream());
					String try_beat = in.readUTF();
					this.isLeader = false;
					try_leader.close();
				} catch (Exception trye) {
					this.isLeader = true;
				}
				
				// DETALLES DEL NODO
				System.out.println("Detalles del nodo:");
				System.out.println("Ip: " + this.ip);
				System.out.println("Port: " + this.port);
				System.out.println("isleader: " + this.isLeader);
				System.out.println("");

				////////////////////////////////////////////////////////////////////
				/////////////////////////// NODO LIDER ///////////////////////////
				////////////////////////////////////////////////////////////////////
				if (this.isLeader == true) {
					System.out.println("///////////////////////////////////");
					System.out.println("////Nodo se convirtió en Lider////");
					System.out.println("/////////////////////////////////\n");
				
					// Socket server del nodo
					ServerSocket server = new ServerSocket(5000);

					// Lista para recordar los seguidores
					ArrayList<Socket> seguidores = new ArrayList<>();

					while (true) {
						System.out.println("\n----------------------------------------------");
						// Esperar a que se conecte seguidor
						Socket sc2 = server.accept();
						DataInputStream in = new DataInputStream(sc2.getInputStream());
						DataOutputStream out = new DataOutputStream(sc2.getOutputStream());

						// Agreegar nodo seguidor a la lista de seguidores
						System.out.println(">>> Seguidor conectado: " + sc2.toString());
						if (!seguidores.contains(sc2)){
							seguidores.add(sc2);
						}

						// Lista de seguidores
						System.out.println("\nLista de Seguidores: ");
						for(Socket seguidor : seguidores){
							System.out.println(seguidor.toString());	
						}
						System.out.println("\n");

						// Enviar pulso
						System.out.println("Envio: <3");
						out.writeUTF("<3");

						//Message Resource y Message Service
						//System.out.println("MessagesR: " + mresource.getMessages());
						//System.out.println("MessagesS: " + mservice.getAllMessages());

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
				////////////////////////////////////////////////////////////////////
				/////////////////////////// NODO REPLICA ///////////////////////////
				////////////////////////////////////////////////////////////////////
				} else {
					System.out.println("////////////////////////////////////////");
					System.out.println("////Nodo inicializado como seguidor////");
					System.out.println("//////////////////////////////////////\n");
					System.out.println("");

					// Socket propio del nodo (no cambia)
					Socket sc = new Socket(ip, 5000);

					DataInputStream in = new DataInputStream(sc.getInputStream());
					DataOutputStream out = new DataOutputStream(sc.getOutputStream());

					// Iniciar timer
					Timer timer = new Timer();
					timer.start();
					String pulse = "";

					while (true) {

						// DETECTAR SI EXISTE UN LIDER CUANDO SE DESCONECTO
						if(!timer.isConnected()){
							System.out.println("\nDESCONECTADO........");
						try {
							
							System.out.println("PRUEBA HEARTBEAT");

							Socket try_leader = new Socket(ip, 5000);
							DataInputStream in2 = new DataInputStream(try_leader.getInputStream());
							String try_beat = in2.readUTF();

							System.out.println("SE RECUPERO EL HEARTBEAT\n");
							timer.setConnected(true);

							Socket sc2 = new Socket(ip, 5000);
							in = new DataInputStream(sc2.getInputStream());
							try_leader.close();

						} catch (Exception trye) {
							System.out.println("NO HEARTBEAT\n");
						}}

						// Revisar si ya es TIMEOUT
						if (timer.isTimeout() == true) {
							//TIMEOUT, primero en llegar a estado timeout se convierte en lider
							this.isLeader = true;
							break;
						}

						// HEARTBEAT
						try {
							// SI RECIBIO PULSO <3
							pulse = in.readUTF();
							if (pulse.equals("<3")) {
								timer.setConnected(true);
							}
						} catch (Exception e1) {
							// NO RECIBIO PULSO <3
							pulse = "</3";
							timer.setConnected(false);
						}

						// PRINT HEARTBEAT
						System.out.println("Recibo: " + pulse);
						TimeUnit.SECONDS.sleep(1);
						
					}
					// SECCION DEL TIMEOUT
					TimeUnit.MILLISECONDS.sleep(port + Math.round(Math.random()*3000));
				}
			}

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return("Node (" + ip + ": " + port + ") "); 
	}
}
