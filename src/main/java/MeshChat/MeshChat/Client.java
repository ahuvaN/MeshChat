package MeshChat.MeshChat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Client {
	
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private ClientSocket client;
	private String myName, IP;
	private int port;
	
	public Client(String name, String ip, int portNum){
		myName = name;
		IP = ip;
		port = portNum;
		
	}
	
	/**
	 * @param serverIp
	 * @return true is successfully connected to server
	 */
	public boolean connectToServer() {
		try {
			client = new ClientSocket(myName, IP, port);

			input = new ObjectInputStream(client.getSocket().getInputStream());
			output = new ObjectOutputStream(client.getSocket().getOutputStream());
			// DISPLAY IN GUI SUCCESSFUL CONNECTION
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}
