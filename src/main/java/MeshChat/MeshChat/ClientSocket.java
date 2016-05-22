package MeshChat.MeshChat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

public class ClientSocket extends Socket{

	private ObjectOutputStream output;
	private ObjectInputStream input;
	private String myName;
	
	public ClientSocket(String name, String serverIP) throws UnknownHostException, IOException{
		super(serverIP, 5000);
		myName = name;
	}

	/**
	 * @param serverIp
	 * @return true is successfully connected to server
	 */
	public boolean connectToServer() {
		try {
			input = new ObjectInputStream(getInputStream());
			output = new ObjectOutputStream(getOutputStream());
			// DISPLAY IN GUI SUCCESSFUL CONNECTION
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public String getName(){
		return myName;
	}

	
	/** 
	 * @return IP address, null if UnknownHostException
	 */
	public String getMyIpAddress(){
		try {
			return Inet4Address.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * sending IP to server so server can 
	 * connect back as a client without doing
	 * it manually
	 * @throws IOException 
	 */
	
	//unnecessary b/c socket has a getIP method
	/*public void sendIpToServer() throws IOException{
		output.writeBytes(getMyIpAddress());
		output.flush();
	}*/

}
