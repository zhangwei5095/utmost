package org.utmost.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class InputStreamUtil {
	public static byte[] toByte(InputStream is) throws IOException {
		byte[] b = new byte[is.available()];
		is.read(b, 0, b.length);
		return b;
	}

	public static InputStream toInputStream(byte[] b) {
		InputStream is = new ByteArrayInputStream(b);
		
		return is;
	}
}
