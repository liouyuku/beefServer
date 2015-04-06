package org.beef.socket;

import java.io.IOException;
import java.net.UnknownHostException;

import org.beef.socket.notify.Client;

public class Test
{
	public static void main(String[] args) throws UnknownHostException,
			IOException
	{
		Client c = new Client();
		c.pushNotify ( "192.168.1.100" , "00" , "owoew111111111" );
	}
}
