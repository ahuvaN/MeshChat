package MeshChat.MeshChat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.JTextArea;

class ClientHandler implements Runnable
{
	
	Socket clientsocket;
	JTextArea conversation;
	BufferedReader reader;
	
	ClientHandler(Socket socket, JTextArea convo)
	{
		try
		{
			conversation = convo;
			clientsocket = socket;
			reader = new BufferedReader(new InputStreamReader(clientsocket.getInputStream()));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	public void run()
	{
		String message;
		System.out.println("run");
		try
		{
			while((message= reader.readLine())!=null)
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