package meshchatnetwork;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Iterator;
import java.util.List;

import javax.swing.JTextArea;

public class ClientHandler implements Runnable {

	private Socket clientsocket;
	private JTextArea conversation;
	private BufferedReader reader;
	private List<PrintWriter> clients;

	public ClientHandler(Socket socket, JTextArea convo, List<PrintWriter> list) {
		try {

			clients = list;
			conversation = convo;
			clientsocket = socket;
			reader = new BufferedReader(new InputStreamReader(clientsocket.getInputStream()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void run() {
		String exactTime;
		String message;

		try {
			while ((exactTime = reader.readLine()) != null) {
				message = reader.readLine();
				if (Server.exclusiveTimeIP.add(exactTime)) {
					conversation.append(message + "\n");
					sendEveryone(message, exactTime);
				}
			}
		} catch (Exception e) {
		}

	}

	public void sendEveryone(String message, String exactTime) {

		Iterator<PrintWriter> iter = clients.iterator();
		while (iter.hasNext()) {
			try {

				PrintWriter writer = iter.next();
				writer.println(exactTime);
				writer.println(message);
				writer.flush();

			} catch (Exception e) {
			}
		}
	}
}