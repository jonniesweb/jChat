package util;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5 {
	
	public static String generateMD5(String text) {
		
		text = DigestUtils.md5Hex(text);
		
		return text;
	}
	
	public static String generateMD5(byte[] text) {
		
		String output = DigestUtils.md5Hex(text);
		
		return output;
	}


}
