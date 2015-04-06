package org.beef.socket.protocol.handler;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class FileUploadHandler extends Thread
{

	private Socket client;

	public FileUploadHandler ( Socket socket ) throws SocketException
	{
		this.client = socket;
		this.client.setSoTimeout ( 30000 );
		start ( );
	}

	public void run()
	{
		while (client != null && !client.isClosed ( ))
		{
			try
			{
				sleep ( 1 );
				handlerConnection ( client );
			}
			catch (Exception e)
			{
				e.printStackTrace ( );
			}
		}
	}

	private void handlerConnection(Socket incomingSocket) throws IOException,
			Exception
	{
		FileReceiverHandle f = new FileReceiverHandle ( );
		f.processConnection ( incomingSocket );
		System.out.println ("send file sucess");
	}

}
