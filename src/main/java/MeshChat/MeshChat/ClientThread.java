package MeshChat.MeshChat;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;

import javax.swing.JTextArea;

public class ClientThread extends Thread {

	private String clientName = null;
	private ObjectInputStream is = null;
	private ObjectOutputStream os = null;
	private Socket clientSocket = null;
	private final ClientThread[] threads;
	private int maxClientsCount;
	private JTextArea conversation;

	public ClientThread(JTextArea convo, Socket client, ClientThread[] threadArray) {
		conversation = convo;
		clientSocket = client;
		threads = threadArray;
		maxClientsCount = threads.length;
	}

	public void run() {
		int maxClients = maxClientsCount;
		ClientThread[] threads = this.threads;

		try {
			/*
			 * Create input and output streams for this client.
			 */
			is = new ObjectInputStream(clientSocket.getInputStream());
			os = new ObjectOutputStream(clientSocket.getOutputStream());
			String name;
			
			/* Welcome the new the client. */
			conversation.append("Welcome " + name
					+ " to our chat room.\nTo leave enter /quit in a new line.");
			synchronized (this) {
				for (int i = 0; i < maxClientsCount; i++) {
					if (threads[i] != null && threads[i] == this) {
						clientName = "@" + name;
						break;
					}
				}
				for (int i = 0; i < maxClientsCount; i++) {
					if (threads[i] != null && threads[i] != this) {
						threads[i].conversation.append("*** A new user " + name
								+ " entered the chat room !!! ***");
					}
				}
			}
			/* Start the conversation. */
			while (true) {
				String line = is.readLine();
				if (line.startsWith("/quit")) {
					break;
				}
				/* If the message is private sent it to the given client. */
				if (line.startsWith("@")) {
					String[] words = line.split("\\s", 2);
					if (words.length > 1 && words[1] != null) {
						words[1] = words[1].trim();
						if (!words[1].isEmpty()) {
							synchronized (this) {
								for (int i = 0; i < maxClientsCount; i++) {
									if (threads[i] != null
											&& threads[i] != this
											&& threads[i].clientName != null
											&& threads[i].clientName
													.equals(words[0])) {
										threads[i].conversation.append("<" + name + "> "
												+ words[1]);
										/*
										 * Echo this message to let the client
										 * know the private message was sent.
										 */
										this.conversation.append(">" + name + "> "
												+ words[1]);
										break;
									}
								}
							}
						}
					}
				} else {
					/* The message is public, broadcast it to all other clients. */
					synchronized (this) {
						for (int i = 0; i < maxClientsCount; i++) {
							if (threads[i] != null
									&& threads[i].clientName != null) {
								threads[i].conversation.append("<" + name + "> " + line);
							}
						}
					}
				}
			}
			synchronized (this) {
				for (int i = 0; i < maxClientsCount; i++) {
					if (threads[i] != null && threads[i] != this
							&& threads[i].clientName != null) {
						threads[i].conversation.append("*** The user " + name
								+ " is leaving the chat room !!! ***");
					}
				}
			}
			conversation.append("*** Bye " + name + " ***");

			/*
			 * Clean up. Set the current thread variable to null so that a new
			 * client could be accepted by the server.
			 */
			synchronized (this) {
				for (int i = 0; i < maxClientsCount; i++) {
					if (threads[i] == this) {
						threads[i] = null;
					}
				}
			}
			/*
			 * Close the output stream, close the input stream, close the
			 * socket.
			 */
			is.close();
			os.close();
			clientSocket.close();
		} catch (IOException e) {
		}
	}
}