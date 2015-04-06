package org.beef.socket.protocol.file;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.beef.socket.protocol.handler.FileUploadHandler;

public class UploadListener extends ServerSocket implements Runnable
{

	private static final int SERVER_PORT = 10001;

	public UploadListener () throws IOException
	{
		super ( SERVER_PORT );
	}

	@Override
	public void run()
	{
		try
		{
			while (true)
			{
				Socket socket = accept ( );
				new FileUploadHandler ( socket );
			}
		}
		catch (IOException e)
		{
			e.printStackTrace ( );
		}
		finally
		{
			try
			{
				close ( );
			}
			catch (IOException e)
			{
				e.printStackTrace ( );
			}
		}
	}

}
