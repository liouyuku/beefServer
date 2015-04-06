package org.beef.socket.protocol.handler;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.beef.socket.protocol.ProtocalPack;
import org.beef.socket.protocol.bean.ImBean;
import org.beef.socket.util.Constant;
import org.beef.socket.util.GlobalCache;

import com.google.gson.Gson;

public class ServerHandler extends IoHandlerAdapter
{

	private ProcessDispatcher dispatcher = new ProcessDispatcher ( );

	private Gson gson = new Gson ( );

	@Override
	public void sessionCreated(IoSession session) throws Exception
	{
		super.sessionCreated ( session );
		session.getConfig ( ).setUseReadOperation ( true );
		session.getConfig ( ).setIdleTime ( IdleStatus.BOTH_IDLE , 5 );
		
		System.out.println ( "client Created : " + session.getRemoteAddress ( ) );
	}

	/**
	 * 当一个客户端连接进入时
	 */
	@Override
	public void sessionOpened(IoSession session) throws Exception
	{
		GlobalCache.cacheSession ( session );
		System.out.println ( "client connection : " + session.getRemoteAddress ( ) );
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception
	{
		super.sessionIdle ( session , status );
		System.out.println ( "sessionIdle " );
		session.write ( "heartbeat" );
	}

	/**
	 * 当一个客户端关闭时
	 */
	@Override
	public void sessionClosed(IoSession session) throws Exception
	{
		System.out.println ( "session Closed" );
	}

	/**
	 * 当接收到客户端的信息
	 * 
	 * @param session
	 * @param message
	 * @throws Exception
	 */
	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception
	{

		ProtocalPack pack = (ProtocalPack) message;
		ImBean bean = pack2ImBean ( pack.getContent ( ) );
		if ( bean != null )
		{
			String clientIP = ( (InetSocketAddress) session.getRemoteAddress ( ) ).getAddress ( ).getHostAddress ( );
			System.out.println ( "ip : " + clientIP + " |     content : " + pack.getContent ( ) );
			dispatcher.dispatcher ( bean );
			session.write ( imBean2Pack ( bean , 1 ) );
		}

	}

	private ProtocalPack imBean2Pack(ImBean bean, int flag)
	{
		String content = gson.toJson ( bean );
		ProtocalPack pack = new ProtocalPack ( 
				(byte) flag , 
				new String (content.getBytes ( Charset.forName ( Constant.ENCODE ) ) ) );
		return pack;
	}

	private ImBean pack2ImBean(String content)
	{
		try
		{
			ImBean bean = new ImBean ( );
			bean.setContent ( content );
			bean.setType ( "d" );
			return bean;
		}
		catch (Exception e)
		{
			e.printStackTrace ( );
		}
		return null;
	}

}