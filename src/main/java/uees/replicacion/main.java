package uees.replicacion;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.UnknownHostException;

public class main {
public static void main(String[] args) throws InterruptedException, IOException {
		
	// Crear archivo log o detectar si ya existe uno
	try {
			File myObj = new File("logs.txt");
			myObj.setWritable(true);
			if (myObj.createNewFile()) {
			System.out.println("Creado: " + myObj.getName() + "\n");
			} else {
			System.out.println(myObj.getName()+" ya existe.\n");
			}
		} catch (IOException e) {
			System.out.println("Ha ocurrido un error creando el log.\n");
			e.printStackTrace();
		}
	
	// Argumentos
	String ip = args[0];
	int port = Integer.parseInt(args[1]);

	// Crear Nodo
	Node nd = new Node(ip, port);
	nd.start();
		
		
}
}
