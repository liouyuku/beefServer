package org.beef.socket.protocol.handler;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.beef.socket.protocol.bean.FileInfo;
import org.beef.socket.protocol.bean.ImBean;
import org.beef.socket.util.Constant;
import org.beef.socket.util.DateStyle;
import org.beef.socket.util.DateUtil;

public class FileReceiverHandle
{
	private Socket connection;

	private String catalog = "D:\\test\\jtwd\\";

	private String currentDatePath;

	public String getCurrentDatePath()
	{
		return currentDatePath;
	}

	public void setCurrentDatePath(String currentDatePath)
	{
		this.currentDatePath = currentDatePath;
	}

	/**
	 * @param incomingSocket
	 * 
	 */
	public List<FileInfo> processConnection(Socket incomingSocket)
	{
		this.connection = incomingSocket;
		receiveFile ( );
		return new ArrayList<FileInfo> ( );
	}

	@SuppressWarnings("resource")
	public ImBean readTxt2ImBean()
	{
		InputStreamReader inputReader = null;
		BufferedReader bufferReader = null;
		try
		{
			inputReader = new InputStreamReader ( new FileInputStream (
					getCurrentDatePath ( ) ) , Constant.ENCODE );
			bufferReader = new BufferedReader ( inputReader );

			// 读取一行
			String line = null;
			StringBuffer strBuffer = new StringBuffer ( );

			while ( ( line = bufferReader.readLine ( ) ) != null)
			{
				strBuffer.append ( line );
			}
			String content = strBuffer.toString ( );
			// return gson.fromJson ( content , ImBean.class );
			ImBean bean = new ImBean ( );
			bean.setContent ( content );
			return bean;
		}
		catch (IOException e)
		{
			e.printStackTrace ( );
		}
		finally
		{

		}
		return null;
	}

	private FileInfo file2FileInfo(File file, String uId)
	{
		FileInfo f = new FileInfo ( );
		f.setFileName ( file.getName ( ) );
		f.setFilePath ( uId + "/" + file.getName ( ) );
		f.setSize ( file.length ( ) );
		f.setFileUrl ( null );
		return f;
	}

	public void receiveFile()
	{
		byte[] buff = new byte[128];
		int length = 0;
		BufferedInputStream socketReader = null;
		BufferedOutputStream fileWriter = null;
		try
		{
			String folderName = DateUtil.DateToString ( new Date ( ) ,DateStyle.YYYYMMDD );
			String fileName = DateUtil.DateToString ( new Date ( ) ,DateStyle.YYYYMMDDHHMMSS );
			currentDatePath = catalog + folderName + "\\" + fileName + ".zip";
			File newFile = new File ( currentDatePath );
			File parent = newFile.getParentFile ( );
			if ( parent != null && !parent.exists ( ) )
			{
				parent.mkdirs ( );
			}
			newFile.createNewFile ( );
			socketReader  = new BufferedInputStream (  connection.getInputStream ( )  );
			fileWriter = new BufferedOutputStream ( new FileOutputStream ( newFile ) );
			
			while ( ( length = socketReader.read ( buff ) ) == buff.length)
			{
				fileWriter.write ( buff , 0 , length );
				
			}
			fileWriter.close ( );
			connection.getOutputStream ( ).write ( "ok".getBytes ( ) );
			System.out.println ( "完成接收" );
		}
		catch (IOException e)
		{
			e.printStackTrace ( );
		}
		finally
		{
			try
			{
				if ( socketReader != null )
				{
					socketReader.close ( );
				}
			}
			catch (IOException e)
			{
				e.printStackTrace ( );
			}
			finally
			{
				try
				{
					if ( connection != null )
					{
						connection.close ( );
					}
				}
				catch (IOException e)
				{
					e.printStackTrace ( );
				}
			}
		}
	}

	/**
	 * 按zip流解码获取接收到的流
	 */
	public List<FileInfo> handlerConnection()
	{
		List<FileInfo> fileList = new ArrayList<FileInfo> ( );
		String dName = UUID.randomUUID ( ).toString ( );
		InputStream inputFromSocket = null;
		BufferedOutputStream fileWriter = null;
		BufferedInputStream socketReader = null;
		byte[] buff = new byte[8192];
		int c = 0;
		try
		{
			inputFromSocket = connection.getInputStream ( );
			ZipInputStream zis = new ZipInputStream ( inputFromSocket );
			socketReader = new BufferedInputStream ( zis );
			ZipEntry e = null;
			String sDate = DateUtil.DateToString ( new Date ( ) ,
					DateStyle.YYYYMMDD );
			this.currentDatePath = catalog + sDate + "\\" + dName
					+ "\\sendDate.txt";
			while ( ( e = zis.getNextEntry ( ) ) != null)
			{
				File file = new File ( catalog + sDate + "\\" + dName + "\\"
						+ e.getName ( ) );

				if ( !file.exists ( ) )
				{
					if ( !file.getParentFile ( ).exists ( ) )
					{
						file.getParentFile ( ).mkdirs ( );
					}
					file.createNewFile ( );
				}

				fileWriter = new BufferedOutputStream ( new FileOutputStream (
						file ) );
				while ( ( c = socketReader.read ( buff ) ) != -1)
				{
					fileWriter.write ( buff , 0 , c );
				}
				try
				{
					fileWriter.close ( );
				}
				catch (IOException e1)
				{
					e1.printStackTrace ( );
				}
				fileList.add ( file2FileInfo ( file , sDate + "/" + dName ) );
			}
		}
		catch (IOException e)
		{
			e.printStackTrace ( );
		}
		finally
		{
			try
			{
				if ( socketReader != null )
				{
					socketReader.close ( );
				}
			}
			catch (IOException e)
			{
				e.printStackTrace ( );
			}
			finally
			{
				try
				{
					if ( connection != null )
					{
						connection.close ( );
					}
				}
				catch (IOException e)
				{
					e.printStackTrace ( );
				}
			}

		}
		return fileList;
	}

}
