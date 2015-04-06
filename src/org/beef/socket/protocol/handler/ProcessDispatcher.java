package org.beef.socket.protocol.handler;

import org.beef.socket.protocol.bean.ImBean;

public class ProcessDispatcher
{

	public void dispatcher(ImBean bean)
	{
		System.out.println ("bean : " + bean.getContent ( ));
	}
}
