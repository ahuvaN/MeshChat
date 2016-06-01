package meshchatnetwork;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class MeshChatGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private Server server;
	private Client client;
	private JTextArea conversation, text;
	private JLabel notifyMsg;
	private JPanel top, topCenter;
	private JButton connect, send, save;
	private JTextField serverIP, serverPort;
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
		addComponents();

		server.sendTextArea(conversation);
	}

	public void startRunning() {
		server.startRunning();
	}

	private void setFeatures() {
		conversation = new JTextArea();
		conversation.setEditable(false);
		conversation.setLineWrap(true);
		conversation.setWrapStyleWord(true);
		notifyMsg = new JLabel("");
		serverIP = new JTextField(10);
		serverPort = new JTextField(4);
		connect = new JButton("Connect"); // client to connect to a server
		send = new JButton("Send"); // server sending out to all clients in its
		// branches
		text = new JTextArea();
		save = new JButton("Save Chat");
		conversation.setBounds(0, 0, 500, 700);

	}

	private void addComponents() {
		JScrollPane scrollPane1 = new JScrollPane(conversation, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane1.setPreferredSize(new Dimension(525, 725));
		scrollPane1.setBounds(0, 0, 500, 500);

		JScrollPane scrollPane2 = new JScrollPane(text, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane2.setPreferredSize(new Dimension(450, 55));

		top = new JPanel(new BorderLayout());
		topCenter = new JPanel();
		top.add(new JLabel(myName + "'s IP Address: " + server.getMyIpAddress() + "      Using port: " + port,
				SwingConstants.CENTER), BorderLayout.NORTH);
		topCenter.add(new JLabel("Enter IP Address: "));
		topCenter.add(serverIP);
		topCenter.add(new JLabel("Enter Server Port: "));
		topCenter.add(serverPort);
		topCenter.add(connect);
		topCenter.add(notifyMsg);
		top.add(topCenter, BorderLayout.CENTER);

		JPanel center = new JPanel();
		center.add(scrollPane1);

		JPanel bottom = new JPanel(new BorderLayout());
		JPanel bottomCenter = new JPanel();
		JPanel bottomSouth = new JPanel();
		bottomCenter.add(scrollPane2);
		bottomCenter.add(send);
		bottomSouth.add(save);
		bottom.add(bottomCenter, BorderLayout.CENTER);
		bottom.add(bottomSouth, BorderLayout.SOUTH);

		add(top, BorderLayout.PAGE_START);
		add(center, BorderLayout.CENTER);
		add(bottom, BorderLayout.PAGE_END);
	}

	private void setButtons() {
		connect.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				notifyMsg.setText(""); // clears error message

				// first check that serverIP and serverPort are not null
				if (serverIP.getText().isEmpty() || serverPort.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please enter a valid IP and valid port");
				} else {
					// when you request to connect to server, then you become a
					// client
					try {
						client = new Client(conversation);
						boolean valid = validateIP(serverIP.getText());
						if (valid) {
							if (client.connectToServer(serverIP.getText(), serverPort.getText())) {
								notifyMsg.setText("Connected");
<<<<<<< HEAD
								server.setClientForServer(client);
=======
								clientForServer = client;
								server.setClientForServer(clientForServer);
>>>>>>> f63dbe063865706eea1bff90de904e8a73c9031b
								connect.setEnabled(false);
								top.remove(topCenter);
							} else {
								notifyMsg.setText("Unable to Connect");
							}
						} else {
							JOptionPane.showMessageDialog(null, "Invalid IP Address. Please try again.");
							serverIP.setText("");
						}
					} catch (Exception e) {
					}
				}
			}
		});

		serverPort.addKeyListener(new KeyListener() {

			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					e.consume();
					connect.doClick();
				}
			}

			public void keyReleased(KeyEvent e) {
			}

			public void keyTyped(KeyEvent e) {
			}
		});

		serverIP.addKeyListener(new KeyListener() {

			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					e.consume();
					connect.doClick();
				}
			}

			public void keyReleased(KeyEvent e) {
			}

			public void keyTyped(KeyEvent e) {
			}
		});

		send.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				String outgoing = myName + ": " + text.getText();
				String exactTimeIPAddress = String.valueOf(System.currentTimeMillis()) + serverIP.getText();
				try {
					if (client != null) {
						client.sendMessage(outgoing, exactTimeIPAddress);
					} else if (client == null) {
						server.sendMessage(outgoing, exactTimeIPAddress);
					}
					text.setText("");
					text.requestFocus();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "You have no connections - find a friend to chat");
					text.setText("");
				}
			}
		});

		text.addKeyListener(new KeyListener() {

			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					e.consume();
					send.doClick();
				}
			}

			public void keyReleased(KeyEvent e) {
			}

			public void keyTyped(KeyEvent e) {
			}
		});

		save.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					JFileChooser filesave = new JFileChooser();
					filesave.showSaveDialog(getContentPane());
					FileOutputStream fos = new FileOutputStream(new File(filesave.getSelectedFile() + ".txt"));
					BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

					String ln = System.getProperty("line.separator");
					String text = conversation.getText();
					String singleLine = text.replaceAll("\n", ln);

					bw.write(singleLine.toString(), 0, singleLine.length());
					bw.flush();
					bw.close();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "The log was not saved successfully.");
				}
			}
		});
	}

	public boolean validateIP(String ipAddress) {
		return PATTERN.matcher(ipAddress).matches();
	}

}