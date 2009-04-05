package org.utmost.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
@Service("CodecUtil")
public class CodecUtil {
	public static String md5Hex(String data) {
		return DigestUtils.md5Hex(data).toString();
	}

	public static void main(String[] args) {
		System.out.println(CodecUtil.md5Hex("wlm"));
	}
}
