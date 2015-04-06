package org.beef.socket.util;

import java.util.UUID;

public class CommonUtil {

	/**
	 * 获取一个随机的昵称
	 */
	public static String GetRandomNick(){
		StringBuffer sBuffer=new StringBuffer();
		sBuffer.append("jtwd");
		String s = UUID.randomUUID().toString();
		s= s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24); 
		s=s.substring(0,10);
		sBuffer.append(s);
		return sBuffer.toString();
	}
}
