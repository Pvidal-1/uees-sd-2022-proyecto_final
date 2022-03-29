package uees.replicacion;

import java.util.HashMap;
import java.util.Map;

public class Database {
	private static Map<Integer, Message> messages = new HashMap<>();
	
	public static Map<Integer, Message> getMessages(){
		return messages;
	}
}
