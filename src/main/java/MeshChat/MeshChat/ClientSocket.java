package MeshChat.MeshChat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

public class ClientSocket extends Socket{

	
	private String myName;
	
	public ClientSocket(String name, String serverIP, int port) throws UnknownHostException, IOException{
		//Socket(InetAddress address, int port, InetAddress localAddr, int localPort)
		//Creates a socket and connects it to the specified remote address on the specified remote port.
		//super(InetAddress.getByName(serverIP), port);
		super(InetAddress.getByName(serverIP), port);
		myName = name;
	}

	public Socket getSocket(){
		return this;
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

}
