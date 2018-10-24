package cn.doublehh.common.utils;

import org.apache.commons.codec.binary.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {

private MD5Utils(){};
	
	public static final MD5Utils instance=new MD5Utils();
	
	public static String generateToken(String token){
		
		//数据指纹 获取摘要 MD5
		try {
			
			MessageDigest md = MessageDigest.getInstance("md5");
			byte md5[] = md.digest(token.getBytes());  //128位 16
			//base64编码
//			BASE64Encoder encoder = new BASE64Encoder();
			return Base64.encodeBase64String(md5);
			
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
}
