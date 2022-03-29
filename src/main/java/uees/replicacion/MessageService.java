package uees.replicacion;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MessageService {
	
	Integer counter;
	
	private Integer updateCounter() {
		this.counter++;
		return this.counter;
	}
	
	private Map<Integer, Message> messages = Database.getMessages();
	
	public MessageService() {
		messages.put(1, new Message(1,"Pedro",21));
		messages.put(2, new Message(2,"Nicolas",21));
	}
	
	public List<Message> getAllMessages(){
		return new ArrayList<Message>(messages.values());
	}
	
	public Message getMessage(Integer id) {
		return messages.get(id);
	}
	
	public Message addMessage(Message message) {
		message.setId(messages.size()+1);
		messages.put(message.getId(), message);
		return message;
	}
	
	public Message updateMessage(Message message) {
		if (message.getId() <= 0) {
			return null;
		}
		messages.put(message.getId(), message);
		return message;
	}
	
	public Message removeMessage(Integer id) {
		return messages.remove(id);
	}
}
