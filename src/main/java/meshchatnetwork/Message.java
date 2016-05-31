package meshchatnetwork;

public class Message {
	
	private String sender;
	private String timestamp;
	private String message;
	
	
	public Message(String name, String time, String msg) {
		sender = name;
		timestamp = time;
		message = msg;
	}


	public String getSender() {
		return sender;
	}


	public String getTimestamp() {
		return timestamp;
	}


	public String getMessage() {
		return message;
	}
	
	public String toString(){
		return sender + ": " + message + "/n";
	}
	

}
