package uees.replicacion;

import java.util.concurrent.TimeUnit;

public class Timer extends Thread{
	public boolean connected;
	public boolean timeout;
	public Integer time;
	
	public Timer() {
		this.connected = true;
		this.timeout = false;
		this.time = 0;
	}

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}

	public boolean isConnected() {
		return connected;
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
	}

	public boolean isTimeout() {
		return timeout;
	}

	public void setTimeout(boolean timeout) {
		this.timeout = timeout;
	}

	@Override
	public void run() { 
		// INICIO DE TIMER
		System.out.println("...Timer iniciado...");
		while(this.timeout == false) {
		
		// TIEMPO DE ESPERA DE HEARTBEAT
		if(this.time>=20) {
			this.setTimeout(true);
		}
		
		// CONTEO TIMER
		this.setTime(this.getTime()+1); 
		
		// REVISAR CONEXION
		if(this.connected == true) {
			this.setTime(0);
		}

		// FRENAR EL CICLO
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
		// DEMUESTRA EL FIN DEL TIMER
		System.out.println("...TIMEOUT...");
	}}
