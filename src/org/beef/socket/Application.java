package org.beef.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.SocketSessionConfig;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.beef.socket.protocol.CustomProtocalCodecFactory;
import org.beef.socket.protocol.file.UploadListener;
import org.beef.socket.protocol.handler.ServerHandler;
import org.beef.socket.util.Constant;

/**
 * 程序入口
 * 
 * @author penzizi
 */
public class Application
{

	private static final int SERVER_PORT = 10000;

	private static final int SERVER_FILE_PORT = 10001;

	/** 30秒后超时 */
	private static final int IDELTIMEOUT = 5;

	/** 15秒发送一次心跳包 */
	// private static final int HEARTBEATRATE = 15;

	public static void main(String[] args) throws Exception
	{
		//messageListener ( SERVER_KEEP_LIVE_PORT , new ServerHandler ( ), true );
		messageListener ( SERVER_PORT , new ServerHandler ( ), false );
		fileUploadListener ( SERVER_FILE_PORT , null );
	}

	public static void messageListener(int port, IoHandlerAdapter adater,boolean keepLive)
	{
		try
		{
			// 创建一个非阻塞的Server端Socket，用NIO
			SocketAcceptor acceptor = new NioSocketAcceptor ( );

			SocketSessionConfig sessionConfig = acceptor.getSessionConfig ( );
			sessionConfig.setReadBufferSize ( 10 * 2048 );
			// 设置输入缓冲区的大小
			acceptor.getSessionConfig ( ).setReceiveBufferSize ( 10 * 1024 );
			// 设置输出缓冲区的大小
			acceptor.getSessionConfig ( ).setSendBufferSize ( 1024 * 10 );
			// 设置为非延迟发送，为true则不组装成大包发送，收到东西马上发出
			acceptor.getSessionConfig ( ).setTcpNoDelay ( true );
			acceptor.getSessionConfig ( ).setIdleTime ( IdleStatus.BOTH_IDLE ,IDELTIMEOUT );

			DefaultIoFilterChainBuilder chain = acceptor.getFilterChain ( );
			chain.addLast ( "codec" ,new ProtocolCodecFilter ( new CustomProtocalCodecFactory ( Charset.forName ( Constant.ENCODE ) ) ) );

			// chain.addLast("keep-alive", new HachiKeepAliveFilterInMina());
			// 
			//心跳
			chain.addLast ( "logger" , new LoggingFilter ( ) );

			acceptor.setHandler ( adater );
			acceptor.bind ( new InetSocketAddress ( port ) );
		}
		catch (IOException e)
		{
			e.printStackTrace ( );
		}
		System.out.println ( "消息服务已开启:" + port );
	}

	public static void fileUploadListener(int port, IoHandlerAdapter adater)
	{
		try
		{
			new Thread ( new UploadListener ( ) ).start ( );
		}
		catch (IOException e)
		{
			e.printStackTrace ( );
		}
		System.out.println ( "文件服务器已经开启：" + SERVER_FILE_PORT );
	}
}
