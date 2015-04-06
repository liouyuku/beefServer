package org.beef.socket.protocol;

import java.nio.charset.Charset;

public class ProtocalPack {
	/**
	 * 数据长度 
	 */
	private int length;
	/**
	 * 数据表示(暂无用)
	 */
	private byte flag;
	/**
	 * 数据内容
	 */
	private String content;

	public ProtocalPack() {

	}

	public ProtocalPack(byte flag, String content) {
		this.flag = flag;
		this.content = content;
		int len = content == null ? 0 : content.getBytes().length;
		this.length = 5 + len;
	}

	public ProtocalPack(byte[] bs) {
		if (bs != null && bs.length >= 5) {
			length = toInt(bs,0,4);
			flag = bs[4];
			content = byteToString(bs,5,length - 5);
		}
	}

	public int toInt(byte[] bRefArr, int start, int end) {
		int iOutcome = 0;
		byte bLoop;
		for (int i = start; i < end; i++) {
			bLoop = bRefArr[i];
			iOutcome += (bLoop & 0xFF) << (8 * i);
		}
		return iOutcome;
	}
	
	public String byteToString(byte[] bRefArr, int start,int length) {
		byte[] str = new byte[length];
		for (int i = start; i < length; i++) {
			str[i] = bRefArr[i];
		}
		return new String(str,Charset.forName("utf-8"));
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public byte getFlag() {
		return flag;
	}

	public void setFlag(byte flag) {
		this.flag = flag;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(" Len:").append(length);
		sb.append(" flag:").append(flag);
		sb.append(" content length :").append(content.getBytes().length);
		sb.append(" content:").append(content);
		return sb.toString();
	}
}
