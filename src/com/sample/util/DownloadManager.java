package com.sample.util;
import java.io.*;
import java.net.*;


public class DownloadManager {

	final static int size=1024;

	public static boolean fileUrl(String fAddress, String   localFileName, String destinationDir) {
		Boolean result=false;
		OutputStream outStream = null;
		URLConnection  uCon = null;
		InputStream is = null;
		try {
			URL Url;
			byte[] buf;
			int ByteRead,ByteWritten=0;
			Url= new URL(fAddress);
			outStream = new BufferedOutputStream(new FileOutputStream(destinationDir+"\\"+localFileName));               
			uCon = Url.openConnection();
			is = uCon.getInputStream();
			buf = new byte[size];
			while ((ByteRead = is.read(buf)) != -1) {
				outStream.write(buf, 0, ByteRead);
				ByteWritten += ByteRead;
			}
			System.out.println("Downloaded Successfully.");
			System.out.println("File name:\""+localFileName+ "\"\nNo ofbytes :" + ByteWritten);
			result=true;
		}
		catch (Exception e) {
			e.printStackTrace();
			result=false;
		}
		finally {
			try {
				is.close();
				outStream.close();
			}
			catch (IOException e) {
				e.printStackTrace();
				result=false;
			}
		}
		return result;
	}

	public static Boolean  fileDownload(String fAddress, String destinationDir) {
		Boolean result=false;
		
		int slashIndex =fAddress.lastIndexOf('/');
		int periodIndex =fAddress.lastIndexOf('.');
		String fileName=fAddress.substring(slashIndex + 1);

		if (periodIndex >=1 &&  slashIndex >= 0  && slashIndex < fAddress.length()-1) {
			result=fileUrl(fAddress,fileName,destinationDir);
		}
		else {
			System.err.println("path or file name.");
			result=false;
		}
		return result;
	}

	public static Boolean downloadFile(String fileDownloadURl) {
		Boolean result=false;
		
		//Download file in below path
		String strDownloadLocation = System.getProperty("user.dir") +"\\downloads";
		File f=new File(strDownloadLocation);
		if(f.exists()==false){
			f.mkdirs();
		}
		
		
		String[] arrDownloadURL = {
				fileDownloadURl
				//Provide URLs here
		};

		if(arrDownloadURL.length>0) {
			for (int i = 0; i < arrDownloadURL.length; i++) {
				result=fileDownload(arrDownloadURL[i], strDownloadLocation);
			}

		}
		else{
			result=false;
			System.out.println("No URLs to download");
		}
		return result;
	}
	/*public static void main(String[] args) {

		String strDownloadLocation = System.getProperty("user.dir") + "\\downloads";//"C:\\Users\\vaya_d\\Downloads";

		String[] arrDownloadURL = {
				//Provide URLs here
		};

		if(arrDownloadURL.length>0) {
			for (int i = 0; i < arrDownloadURL.length; i++) {
				fileDownload(arrDownloadURL[i], strDownloadLocation);
				//System.out.println(arrDownloadURL[i] + "   ==> " + strDownloadLocation);
			}

		}
		else{
			System.out.println("No URLs to download");
		}
	}*/
}

