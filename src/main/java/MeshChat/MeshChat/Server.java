package MeshChat.MeshChat;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class Server {

	private ObjectOutputStream output;
	private ObjectInputStream input;
	private ServerSocket server;
	private Socket socket;
	private List<Client> clients;
	private String myName;
	private JTextArea conversation = null;
	private static final int maxClientsCount = 25;
	private static final ClientThread[] threads = new ClientThread[maxClientsCount];

	public Server(String name, int port) {
		myName = name.toUpperCase();
		startRunning(port);
	}

	private void startRunning(int port) {
		try {
			server = new ServerSocket(port, 100);
			clients = new ArrayList<Client>();
			if (conversation != null) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {

						/**
						 * start thread that listens for new connection requests
						 * once new connection is established add it to clients-
						 * socket = server.accept(); establish I/O Streams for
						 * each new client listen for conversations from clients
						 * and when one is found broadcast to all clients in
						 * array make sure no client receives same message
						 * twice.
						 */
						while (true) {
							try {
								Socket clientSocket = server.accept();
								int i = 0;
								for (i = 0; i < maxClientsCount; i++) {
									if (threads[i] == null) {
										(threads[i] = new ClientThread(
												conversation, clientSocket, threads)).start();
										break;
									}
								}
								if (i == maxClientsCount) {
									PrintStream os = new PrintStream(
											clientSocket.getOutputStream());
									os.println("Server too busy. Try later.");
									os.close();
									clientSocket.close();
								}
							} catch (IOException e) {
								System.out.println(e);
							} finally {
								// closeUp(); // close all Streams
							}
						}
					}
				});
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				server.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public String getMyIpAddress() {
		try {
			return Inet4Address.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void sendTextArea(JTextArea convo) {
		conversation = convo;
	}
}
