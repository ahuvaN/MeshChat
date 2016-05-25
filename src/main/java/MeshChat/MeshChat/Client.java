package MeshChat.MeshChat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.JTextArea;

public class Client {

	private PrintWriter output;
	private BufferedReader input;
	private Socket client;
	private JTextArea conversation;

	public Client(JTextArea chat) throws Exception {
		conversation = chat;
	}

	/**
	 * @param prt - determines if prt is 4 characters and valid integer  
	 * @return int- if successfully converted
	 * throws exception if not
	 */
	private int isValidPort(String prt) throws Exception {
		if (prt.length() == 4)
			try {
				return Integer.parseInt(prt);
			} catch (Exception e) {
			}
		throw new Exception();
	}

	/**
	 * @param ip - string IP of server 
	 * @param port - the port of server
	 * @return true is successfully connected to server
	 */
	public boolean connectToServer(String IP, String prt) {
		try {
			IP = isValidIP(IP);
			int port = isValidPort(prt);
			client = new Socket(IP, port);
			input = new BufferedReader(new InputStreamReader(
					client.getInputStream()));
			output = new PrintWriter(client.getOutputStream());
			conversation
			.append("\n\t      Successfully connected to server "
					+ IP + "\n");
			return true;
			// TODO create InvalidIPException and InvalidPortException
		}catch(IOException e){
			e.printStackTrace();
			return false;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public PrintWriter getOutput() {
		return output;
	}

	public BufferedReader getInput() {
		return input;
	}

	private String isValidIP(String input) throws Exception {
		if (input == null)
			throw new Exception();
		int length = input.length();
		if (length == 0)
			throw new Exception();
		int i = 0;
		if (input.charAt(0) == '-') {
			if (length == 1)
				throw new Exception();
			i = 1;
		}
		for (; i < length; i++) {
			char c = input.charAt(i);
			if (c <= '/' || c >= ':')
				throw new Exception();
		}
		try {
			int n = Integer.parseInt(input);
			if (n >= 0 && n <= 255)
				return input;
			else
				throw new Exception();
		} catch (Exception e) {
			throw new Exception();
		}
	}

	public void listenerForMessages() {
		Thread readerThread = new Thread(new Runnable() {

			@Override
			public void run() {
				String incoming;
				try{
					while ((incoming = input.readLine()) != null){
						conversation.append(incoming + "/n");
						// TODO send it out from server side, 
						//check if there are clients down the chain who didnt receive message yet
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		});

		// the client is now listening for incoming messages
		readerThread.start();		
	}
}
