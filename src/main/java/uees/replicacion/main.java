package uees.replicacion;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.UnknownHostException;

public class main {
public static void main(String[] args) throws InterruptedException, IOException {
		
		try {
		      File myObj = new File("logs.txt");
		      myObj.setWritable(true);
		      if (myObj.createNewFile()) {
		        System.out.println("Creado: " + myObj.getName());
		      } else {
		        System.out.println(myObj.getName()+" ya existe.");
		      }
		    } catch (IOException e) {
		      System.out.println("Ha ocurrido un error creando el log.");
		      e.printStackTrace();
		    }
		
		String ip = args[0];
		int port = Integer.parseInt(args[1]);

		Node nd = new Node(ip, port);
		nd.start();
		
		
}
}
