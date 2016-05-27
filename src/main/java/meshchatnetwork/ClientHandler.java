package meshchatnetwork;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.swing.JTextArea;

public class ClientHandler implements Runnable {

	private Socket clientsocket;
	private JTextArea conversation;
	private BufferedReader reader;
	private PrintWriter output;
	private HashSet<String> exactTimes;

	private List<PrintWriter> clients;

	public ClientHandler(Socket socket, JTextArea convo, List<PrintWriter> clients, HashSet<String> exactTimes) {
		try {

			this.clients = clients;
			conversation = convo;
			clientsocket = socket;
			reader = new BufferedReader(new InputStreamReader(clientsocket.getInputStream()));
			output = new PrintWriter(clientsocket.getOutputStream());
			this.exactTimes = exactTimes;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public HashSet<String> getExactTimes() {
		return exactTimes;
	}

	public void setExactTimes(HashSet<String> exactTimes) {
		this.exactTimes = exactTimes;
	}

	public void run() {
		String exactTime;
		String message;

		try {
			while ((exactTime = reader.readLine()) != null) {
				if (exactTimes.add(exactTime)) {
					message = reader.readLine();
					conversation.append(message + "\n");
					sendEveryone(message, exactTime);
				} else {
					reader.readLine();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void sendEveryone(String message, String exactTime) {

		Iterator<PrintWriter> it = clients.iterator();
		while (it.hasNext()) {
			try {

				PrintWriter writer = (PrintWriter) it.next();
				writer.write(exactTime);
				writer.println();
				writer.println(message);
				writer.flush();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}