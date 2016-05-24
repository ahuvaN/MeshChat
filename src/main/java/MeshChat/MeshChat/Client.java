package MeshChat.MeshChat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
	
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private Socket client;
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
			// = new Socket(InetAddress.getByName(IP), port, InetAddress.getByName(myName), port);
			client = new Socket(IP, port);

			input = new ObjectInputStream(client.getInputStream());
			output = new ObjectOutputStream(client.getOutputStream());
			// DISPLAY IN GUI SUCCESSFUL CONNECTION
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}