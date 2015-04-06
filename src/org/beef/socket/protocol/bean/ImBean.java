package org.beef.socket.protocol.bean;

import java.io.Serializable;

/**
 * 通信实体类
 * 
 */
public class ImBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1867737600018350485L;

	private String type;
	
	private String content;

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}
	
	
	
}
