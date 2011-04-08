package com.goingkilo.textlayout.net;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import com.goingkilo.textlayout.data.Document;
import com.goingkilo.textlayout.xml.Reader;

public class Puller {

	public Document get(String ip, String port){

		Socket socket 			= null;
		BufferedInputStream bis = null;
		String s = null;
		StringBuffer instr = new StringBuffer();
		
		try {
			socket = new Socket(ip, Integer.parseInt(port));
			bis = new BufferedInputStream(socket.getInputStream());

			InputStreamReader isr = new InputStreamReader(bis, "US-ASCII");
			int c;
			while ( (c = isr.read()) != -1){
				instr.append( (char) c);
			}

//			byte[] b = new byte[bis.available()]; 
//			bis.read(b);
//			bis.close();
//			s = new String(b);
			s = instr.toString();
			System.out.println( "******************************** read string "+s);
		} 
		catch (IOException e) {
			System.out.println( " IOException calling socket" + e.getMessage() );
		} 
		finally {
			try {
				bis.close(); } 
			catch (IOException e) {e.printStackTrace();}
			
			try { 
				socket.close();} 
			catch (IOException e) {e.printStackTrace();}
		}
		
		try {
			FileOutputStream fos  = new FileOutputStream(new File("/sdcard/kr/a.xml"));
			System.out.println("********************************  writing "+s);
			fos.write(s.getBytes());
			fos.flush();
			fos.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		Reader reader = new Reader();
		return reader.readXMLString("file:///sdcard/kr/a.xml");
	}
}
