package MeshChat.MeshChat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Iterator;

import javax.swing.JTextArea;

class ClientHandler implements Runnable
{
	ObjectInputStream reader;
	Socket clientsocket;
	JTextArea conversation;
	
	ClientHandler(Socket socket, JTextArea convo)
	{
		try
		{
			conversation = convo;
			clientsocket = socket;
			reader = new ObjectInputStream(socket.getInputStream());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	public void run()
	{
		String message;
		try
		{
			while((message= (String)reader.readObject())!=null)
			{
				conversation.append(message+"\n");
				sendEveryone(message);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	private void sendEveryone(String message)
	{
		//replace with arrayList<Client>
			/*Iterator it=clientOutputStreams.iterator();
			
			
			while(it.hasNext())
			{
				try
				{
					PrintWriter writer=(PrintWriter)it.next();
					writer.println(message);
					writer.flush();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			*/
	}
}