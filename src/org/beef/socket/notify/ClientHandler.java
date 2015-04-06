package org.beef.socket.notify;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

public class ClientHandler extends IoHandlerAdapter
{

	@Override
	public void sessionOpened(IoSession session) throws Exception
	{
		System.out.println ( "incoming client:" + session.getRemoteAddress ( ) );
	}

	// 当客户端发送消息到达时
	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception
	{
		System.out.println ( "message written..."  + message );
	}

	// 当一个客户端连接关闭时
	@Override
	public void sessionClosed(IoSession session) throws Exception
	{
		System.out.println ( "one client closed" );
	}

}