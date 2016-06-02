package meshchatnetwork;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.swing.JTextArea;

public class Server {

	private PrintWriter output;
	private BufferedReader input;
	private ServerSocket server;
	private Socket socket;
	private List<PrintWriter> clients;
	private String myName;
	private JTextArea conversation = null;
	private int port;
	public static HashSet<String> exclusiveTimeIP;
	private Thread thread;
	private ClientHandler clientHandler;

	public Server(String name, int prt) {
		myName = name.toUpperCase();
		port = prt;
		clients = new ArrayList<PrintWriter>();
		exclusiveTimeIP = new HashSet<String>();

	}

	public void startRunning() {

		/**
		 * start thread that listens for new connection requests once new
		 * connection is established add it to clients- socket =
		 * server.accept(); establish I/O Streams for each new client listen for
		 * conversations from clients and when one is found broadcast to all
		 * clients in array make sure no client receives same message twice.
		 */
		if (conversation != null) {
			new Thread(new Runnable() {

				public void run() {
					try {
						server = new ServerSocket(port);

						while (true) {
							try {
								socket = server.accept();
								output = new PrintWriter(socket.getOutputStream());
								input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
								String exactTime = String.valueOf(System.currentTimeMillis()) + getMyIpAddress();
								output.println(exactTime);
								output.println(myName);
								output.flush();
								String name = input.readLine();
								PrintWriter writer = new PrintWriter(socket.getOutputStream());
								clients.clear();
								clients.add(writer);
								clientHandler = new ClientHandler(socket, conversation, clients);
								thread = new Thread(clientHandler);
								thread.start();
								String message = name + " joined the network";
								conversation.append("\t" + message + "\n");
								sendMessage(message, exactTime);
							} catch (Exception e) {
							}
						}
					} catch (IOException e) {

					}
				}
			}).start();
		}
	}

	public Thread getThread() {
		return thread;
	}

	public ClientHandler getClientHandler() {
		return clientHandler;
	}

	public String getMyIpAddress() {
		try {
			return Inet4Address.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return null;
		}
	}

	public PrintWriter getOutput() {
		return output;
	}

	public BufferedReader getInput() {
		return input;
	}

	public String getMyName() {
		return myName;
	}

	public void sendTextArea(JTextArea convo) {
		conversation = convo;
	}

	public void setClientForServer(Client clientForServer) {
		clients.add(clientForServer.getOutput());
		clientForServer.setServerHalf(this);
	}

	public void sendMessage(String outgoing, String exactTimeIPAddress) {
		clientHandler.sendEveryone(outgoing, exactTimeIPAddress);
	}

}