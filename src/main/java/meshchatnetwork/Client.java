package meshchatnetwork;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class Client {

	private PrintWriter output;
	private BufferedReader input;
	private Socket client;
	private JTextArea conversation;
	private String serverTime;

	private Server serverHalf;

	public void setServerHalf(Server server) {
		serverHalf = server;
	}

	public Client(JTextArea chat) throws Exception {
		conversation = chat;
	}

	private int isValidPort(String prt) throws Exception {
		if (prt.length() >= 2 && prt.length() <= 5) {
			try {
				return Integer.parseInt(prt);
			} catch (Exception e) {

			}
		} else {
			JOptionPane.showMessageDialog(null, "Invalid port number. Please try again.");

		}
		throw new Exception();
	}

	public boolean connectToServer(String IP, String prt, String myName) {
		try {
			int port = isValidPort(prt);
			client = new Socket(IP, port);
			input = new BufferedReader(new InputStreamReader(client.getInputStream()));
			output = new PrintWriter(client.getOutputStream());
			output.println(myName);
			output.flush();
			serverTime = input.readLine();
			String name = input.readLine();
			conversation.append("\tSuccessfully connected to server " + name + "\n");
			new Thread(new Runnable() {

				public void run() {
					String exactTime;
					String incoming;
					while (true) {
						try {
							while ((exactTime = input.readLine()) != null) {
								incoming = input.readLine();

								if (Server.exclusiveTimeIP.add(exactTime)) {
									if (!exactTime.equals(serverTime)) {
										conversation.append("\t" + incoming + "\n");
									}
									serverHalf.getClientHandler().sendEveryone(incoming, exactTime);
								}
							}
						} catch (Exception e) {
						}
					}
				}
			}).start();
			return true;
		} catch (IOException e) {
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	public void sendMessage(String message, String exactTime) {
		try {
			output.println(exactTime);
			output.println(message);
			output.flush();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Message could not be sent");

		}
	}

	public Socket getClient() {
		return client;
	}

	public PrintWriter getOutput() {
		return output;
	}

	public BufferedReader getInput() {
		return input;
	}

}