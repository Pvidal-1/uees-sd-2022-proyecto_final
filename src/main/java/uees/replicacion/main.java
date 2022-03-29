package uees.replicacion;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Scanner;

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
	
		Scanner sn = new Scanner(System.in);
		
		System.out.println("Ingrese el puerto");
		int port = sn.nextInt();
		
		System.out.println("Ingrese si es lider");
		boolean num = sn.nextBoolean();
		
		Node nd = new Node("127.0.0.1", port, num);
		nd.start();
		
		
}
}
