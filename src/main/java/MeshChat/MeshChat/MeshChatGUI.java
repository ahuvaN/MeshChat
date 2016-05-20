package MeshChat.MeshChat;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class MeshChatGUI extends JFrame{
	
	private Server server;
	private Client client;
	
	private JTextArea conversation;
	private JLabel IPaddress;
	private JLabel enterIp;
	private JButton connect, send;
	private JTextField text;
	private JTextField serverIp;
	private BorderLayout layout;
	
	public MeshChatGUI(){
		
		client = new Client();
		server = new Server();
		
		setTitle("Mesh Chat");
		setSize(600, 730);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		layout = new BorderLayout();
		this.setLayout(layout);
		
		setFeatures();
	}

	
	private void setFeatures() {
		conversation = new JTextArea();
		conversation.setEditable(false);
		conversation.setLineWrap(true);
		conversation.setWrapStyleWord(true);
		IPaddress = new JLabel("My IP Address: " + client.getMyIpAddress(), SwingConstants.CENTER);
		enterIp = new JLabel("Enter IP Address: ");
		serverIp = new JTextField(10);
		//connect is for the client to connect to a server
		connect = new JButton("Connect"); //add ActionListener
		//send is a server sending out to all clients in its branches
		send = new JButton("Send"); //Add ActionListener
		text = new JTextField(40);	
		
		conversation.setBounds(0,0,500, 700);

        JScrollPane scrollPane = new JScrollPane(conversation,
        		JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(525, 725));
        scrollPane.setBounds(0, 0, 500, 500);
        
		JPanel top = new JPanel(new BorderLayout());
		JPanel topCenter = new JPanel();
		top.add(IPaddress, BorderLayout.NORTH);
		topCenter.add(enterIp);
		topCenter.add(serverIp);
		topCenter.add(connect);
		top.add(topCenter, BorderLayout.CENTER);
		
		JPanel center = new JPanel();
		center.add(scrollPane);
		
		JPanel bottom = new JPanel();
		bottom.add(text);
		bottom.add(send);
		
		add(top, BorderLayout.PAGE_START);
		add(center, BorderLayout.CENTER);
		add(bottom, BorderLayout.PAGE_END);
		
	}


	public static void main(String[] args){
		new MeshChatGUI().setVisible(true);
	}
}
