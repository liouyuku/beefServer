package org.beef.socket.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.mina.core.session.IoSession;

public class GlobalCache
{

	private static HashMap<String, IoSession> SESSION = new HashMap<String, IoSession> ( );

	public static void cacheSession(String id, IoSession session)
	{
		SESSION.put ( id , session );
	}

	public static void cacheSession(IoSession session)
	{
		String id = DateUtil.DateToString ( new Date ( ) ,DateStyle.YYYYMMDDHHMMSS );
		cacheSession ( id , session );
	}

	public static IoSession getSession(String id)
	{
		return SESSION.get ( id );
	}

	public static void removeSession(String id)
	{
		Iterator<String> iterator = SESSION.keySet ( ).iterator ( );
		while (iterator.hasNext ( ))
		{
			String key = iterator.next ( );
			if ( key == null ) return;
			if ( key.equals ( id ) )
			{
				iterator.remove ( );
			}
		}
	}

}
