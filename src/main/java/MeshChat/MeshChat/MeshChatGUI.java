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

public class MeshChatGUI extends JFrame{
	
	private Server server;
	private Client client;
	
	private JTextArea conversation;
	private JLabel IPaddress;
	private JButton connect, send;
	private JTextField text;
	private BorderLayout layout;
	
	public MeshChatGUI(){
		setTitle("Mesh Chat");
		setSize(600, 915);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		layout = new BorderLayout();
		this.setLayout(layout);
		
		setFeatures();
	}

	
	private void setFeatures() {
		conversation = new JTextArea();
		IPaddress = new JLabel("My IP Address");
		connect = new JButton("Connect"); //add ActionListener
		send = new JButton("Send"); //Add ActionListener
		text = new JTextField(40);	
		
		conversation.setBounds(0,0,500, 700);

        JScrollPane scrollPane = new JScrollPane(conversation,
        		JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(525, 725));
        scrollPane.setBounds(0, 0, 500, 500);
        
		JPanel top = new JPanel();
		top.add(IPaddress);
		top.add(connect);
		
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
