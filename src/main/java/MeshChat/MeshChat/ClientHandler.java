package MeshChat.MeshChat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.JTextArea;

public class ClientHandler implements Runnable {

	private Socket clientsocket;
	private JTextArea conversation;
	private BufferedReader reader;
	private HashSet<String> exactTimes = new HashSet<String>();
	private Server server = new Server();

	public ClientHandler(Socket socket, JTextArea convo) {
		try {

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
				if (exactTimes.add(message)) {
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
		Iterator<Socket> it = server.getClients().iterator();

		while (it.hasNext()) {
			try {

				OutputStream outStream = (OutputStream) it.next().getOutputStream();
				((PrintStream) outStream).println(message);
				outStream.flush();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}