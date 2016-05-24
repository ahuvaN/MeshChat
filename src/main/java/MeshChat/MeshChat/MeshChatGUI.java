package MeshChat.MeshChat;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class MeshChatGUI extends JFrame {

	private Server server;
	private JTextArea conversation, text;
	private JLabel IPaddress, enterIp, notifyMsg;
	private JButton connect, send;
	private JTextField serverIP;
	private BorderLayout layout;
	private String myName; // for sent messages
	private int port;
	
	private final Pattern PATTERN = Pattern
			.compile("^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
	

	public MeshChatGUI(String name, int portNum) {
		setTitle("Mesh Chat");
		setSize(600, 730);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		layout = new BorderLayout();
		setLayout(layout);

		myName = name.toUpperCase();
		port = portNum;

		server = new Server(myName, port);

		setFeatures();
		setButtons();

		server.sendTextArea(conversation);
	}
	
	public void startRunning(){
		server.startRunning();
	}

	private void setFeatures() {
		conversation = new JTextArea();
		conversation.setEditable(false);
		conversation.setLineWrap(true);
		conversation.setWrapStyleWord(true);
		IPaddress = new JLabel("My IP Address: " + server.getMyIpAddress()
				+ "      Using port: " + port, SwingConstants.CENTER);
		notifyMsg = new JLabel("");
		enterIp = new JLabel("Enter IP Address: ");
		serverIP = new JTextField(10);
		connect = new JButton("Connect"); // client to connect to a server
		send = new JButton("Send"); // server sending out to all clients in its
									// branches
		text = new JTextArea();

		conversation.setBounds(0, 0, 500, 700);

		JScrollPane scrollPane1 = new JScrollPane(conversation,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane1.setPreferredSize(new Dimension(525, 725));
		scrollPane1.setBounds(0, 0, 500, 500);

		JScrollPane scrollPane2 = new JScrollPane(text,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane2.setPreferredSize(new Dimension(450, 55));

		JPanel top = new JPanel(new BorderLayout());
		JPanel topCenter = new JPanel();
		top.add(IPaddress, BorderLayout.NORTH);
		topCenter.add(enterIp);
		topCenter.add(serverIP);
		topCenter.add(connect);
		topCenter.add(notifyMsg);
		top.add(topCenter, BorderLayout.CENTER);

		JPanel center = new JPanel();
		center.add(scrollPane1);

		JPanel bottom = new JPanel();
		bottom.add(scrollPane2);
		bottom.add(send);

		add(top, BorderLayout.PAGE_START);
		add(center, BorderLayout.CENTER);
		add(bottom, BorderLayout.PAGE_END);

	}

	private void setButtons() {
		connect.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				notifyMsg.setText(""); // clears error message

				// when request to connect to server, then you become a client
				Client client;
				if (validateIP(serverIP.getText())) {
					client = new Client(server.getMyIpAddress(), serverIP.getText(), port);

					if (client.connectToServer()) {
						notifyMsg.setText("Connected");
					} else {
						notifyMsg.setText("Unable to Connect");
					}
				} else {
					notifyMsg.setText("Invalid IP Address Format");
				}
			}

			private boolean validateIP(String ipAddress) {
				return PATTERN.matcher(ipAddress).matches();
			}
		});

		serverIP.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					e.consume();
					connect.doClick();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}
		});

		send.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String txt = text.getText();
				conversation.append("me: " + txt + "\n");
				text.setText("");
				// sendToClients();
			}

		});

		text.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					e.consume();
					send.doClick();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}
		});
	}
}
