package MeshChat.MeshChat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

public class Client {

	private Socket socket;
	private PrintWriter writer;
	private BufferedReader reader;
	private final Pattern PATTERN = Pattern
			.compile("^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
	
	public Client(){
		
	}

	/**
	 * @param serverIp
	 * @return true is successfully connected to server
	 */
	public boolean connectToServer(String serverIp) {
		try {
			socket = new Socket(serverIp, 5000);
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new PrintWriter(socket.getOutputStream());
			sendIpToServer();
			// DISPLAY IN GUI SUCCESSFUL CONNECTION
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * @param ipAddress
	 * @return true if valid IP address format
	 */
	public boolean validateIP(String ipAddress) {
		return PATTERN.matcher(ipAddress).matches();
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
	 */
	public void sendIpToServer(){
		writer.println(getMyIpAddress());
		writer.flush();
	}

}
