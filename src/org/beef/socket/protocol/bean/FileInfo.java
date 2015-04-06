package org.beef.socket.protocol.bean;

import java.io.Serializable;

public class FileInfo implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7451020948852624985L;

	/**
	 * 文件Id
	 */
	private Integer fileId;

	/**
	 * 文件名称
	 */
	private String fileName;

	/**
	 * 文件路径
	 */
	private String filePath;
	
	/**
	 * 文件网络地址
	 */
	private String fileUrl;

	/**
	 * 文件大小
	 */
	private Long size;
	
	
	private Object fileSize;

	
	public Object getFileSize()
	{
		return fileSize;
	}

	public void setFileSize(Object fileSize)
	{
		this.fileSize = fileSize;
	}

	public String getFileUrl()
	{
		return fileUrl;
	}

	public void setFileUrl(String fileUrl)
	{
		this.fileUrl = fileUrl;
	}

	public Integer getFileId()
	{
		return fileId;
	}

	public void setFileId(Integer fileId)
	{
		this.fileId = fileId;
	}

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	public String getFilePath()
	{
		return filePath;
	}

	public void setFilePath(String filePath)
	{
		this.filePath = filePath;
	}

	public Long getSize()
	{
		return size;
	}

	public void setSize(Long size)
	{
		this.size = size;
	}

}
