package MeshChat.MeshChat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;

public class Server {

	private ObjectOutputStream output;
	private ObjectInputStream input;
	private ServerSocket server;
	private Socket socket;
	private List<Socket> clients;
	private String myName;
	private JTextArea conversation = null;
	private int port;

	public Server(String name, int prt) {
		myName = name.toUpperCase();
		port = prt;
		clients = new ArrayList<Socket>();

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

				@Override
				public void run() {
					try {
						server = new ServerSocket(port);

						while (true) {
							try {
								socket = server.accept();
								output = new ObjectOutputStream(socket
										.getOutputStream());
								String clientAddress = socket.getInetAddress()
										.toString();

								clients.add(socket);

								Thread t = new Thread(new ClientHandler(socket,
										conversation));
								t.start();
								conversation
										.append("\n\t     Got a new connection from "
												+ clientAddress + "\n");
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
					}

				}
			}).start();
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

	// option 2
	/*
	 * if (conversation != null) { SwingUtilities.invokeLater(new Runnable() {
	 * public void run() {
	 * 
	 * while (true) { try { Socket clientSocket = server.accept(); int i = 0;
	 * for (i = 0; i < maxClientsCount; i++) { if (threads[i] == null) {
	 * (threads[i] = new ClientThread( conversation, clientSocket,
	 * threads)).start(); break; } } if (i == maxClientsCount) {
	 * ObjectOutputStream os = new ObjectOutputStream(
	 * clientSocket.getOutputStream());
	 * os.writeObject("Server too busy. Try later."); os.close();
	 * clientSocket.close(); } } catch (IOException e) { System.out.println(e);
	 * } finally { // closeUp(); // close all Streams } } } }); }
	 */
}