package meshchatnetwork;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ServerStart extends JFrame {

	private static final long serialVersionUID = 1L;
	private JLabel nameLabel, portLabel;
	private JTextField name, port;
	private JButton start;

	public ServerStart() {
		setTitle("Mesh Chat");
		setSize(600, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

		initializeLayout();

	}

	private void initializeLayout() {

		JPanel top = new JPanel();
		JPanel bottom = new JPanel();

		nameLabel = new JLabel("Enter your name: ");
		portLabel = new JLabel("Choose a port: ");
		name = new JTextField(20);
		port = new JTextField(10);
		start = new JButton("Start");

		top.add(nameLabel);
		top.add(name);

		bottom.add(portLabel);
		bottom.add(port);

		add(top);
		add(bottom);
		add(start);

		addActionListener(this);
	}

	private void addActionListener(final ServerStart me) {
		start.addActionListener(new ActionListener() {

			
			public void actionPerformed(ActionEvent arg0) {
				String serverName = name.getText();
				int serverPort = 0;
				try {
					serverPort = Integer.parseInt(port.getText());
					me.dispose();
					MeshChatGUI g = new MeshChatGUI(serverName, serverPort);
					g.setVisible(true);
					g.startRunning();

				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Invalid port number");
					port.setText("");
				}

			}
		});

		port.addKeyListener(new KeyListener() {

			
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					e.consume();
					start.doClick();
				}
			}

			
			public void keyReleased(KeyEvent e) {
			}

			
			public void keyTyped(KeyEvent e) {
			}
		});
	}

	public static void main(String[] args) {
		ServerStart s = new ServerStart();
		s.setVisible(true);
	}

}