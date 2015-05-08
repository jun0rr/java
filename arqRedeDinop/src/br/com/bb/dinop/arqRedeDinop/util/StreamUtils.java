package br.com.bb.dinop.arqRedeDinop.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class StreamUtils {

	public static final int BUFFER_SIZE = 1024;
	
	
	public static InputStream getInputStream(File f) {
		try {
			return new FileInputStream(f);
		} catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	
	public static void transferAndClose(InputStream is, OutputStream os) {
		try {
			byte[] buf = new byte[BUFFER_SIZE];
			int read = -1;
			while(true) {
				read = is.read(buf);
				if(read <= 0) break;
				os.write(buf, 0, read);
			}
			os.flush();
			is.close();
			os.close();
		} catch(IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
}