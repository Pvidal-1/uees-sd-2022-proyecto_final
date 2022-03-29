package uees.replicacion;

import java.util.concurrent.TimeUnit;

public class Heartbeat extends Thread{
	public boolean connected;
	public boolean timeout;
	public Integer time;
	
	public Heartbeat() {
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
		System.out.println("...Timer iniciado...");
		while(this.timeout == false) {
		 
		if(this.time>=10) {
			this.setTimeout(true);
		}
		
		this.setTime(this.getTime()+1); 
		
		if(this.connected == true) {
			this.setTime(0);
		}
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		System.out.println("...TIMEOUT...");
	}}
