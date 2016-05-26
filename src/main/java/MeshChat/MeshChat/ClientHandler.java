package MeshChat.MeshChat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.swing.JTextArea;

public class ClientHandler implements Runnable {

	private Socket clientsocket;
	private JTextArea conversation;
	private BufferedReader reader;
	private HashSet<Long> exactTimes = new HashSet<Long>();
	// private Server server;// = new Server();
	private List<PrintWriter> clients;

	public ClientHandler(Socket socket, JTextArea convo, List<PrintWriter> clients) {
		try {

			this.clients = clients;
			conversation = convo;
			clientsocket = socket;
			reader = new BufferedReader(new InputStreamReader(clientsocket.getInputStream()));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void run() {
		String message;
		System.out.println("run");

		try {
			while ((message = reader.readLine()) != null) {
				if (exactTimes.add(Long.parseLong(message))) {
					message = reader.readLine();
					conversation.append(message + "\n");
					sendEveryone(message);
				} else {
					reader.readLine();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void sendEveryone(String message) {
		Iterator<PrintWriter> it = clients.iterator();

		while (it.hasNext()) {
			try {
				PrintWriter writer = (PrintWriter) it.next();
				writer.println(message);
				writer.flush();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}