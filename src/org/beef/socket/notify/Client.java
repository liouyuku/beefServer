package org.beef.socket.notify;

import java.net.InetSocketAddress;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.beef.socket.util.Constant;

@SuppressWarnings("deprecation")
public class Client
{
	private void push(String ip,int port )
	{
		NioSocketConnector connector = new NioSocketConnector ( );

		// 创建接受数据的过滤器
		DefaultIoFilterChainBuilder chain = connector.getFilterChain ( );

		// 设定这个过滤器将一行一行(/r/n)的读取数据
		chain.addLast ( "myChin" , new ProtocolCodecFilter (
				new TextLineCodecFactory ( ) ) );

		// 客户端的消息处理器：一个SamplMinaServerHander对象
		connector.setHandler ( new ClientHandler ( ) );

		// set connect timeout
		connector.setConnectTimeout ( 30 );

		// 连接到服务器：
		ConnectFuture cf = connector.connect ( new InetSocketAddress (ip , port ) );

		// Wait for the connection attempt to be finished.
		cf.awaitUninterruptibly();

        IoSession session =cf.getSession();

        session.write(Constant.NOTIFY);

        session.getCloseFuture().awaitUninterruptibly();

		connector.dispose ( );
	}
	
	

	public void pushNotify(String ip, String title, String context)
	{
		push(ip,3000);
	}

	public void keepLive(String ip)
	{
		push(ip,2012);
	}


}
