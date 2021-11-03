package gm.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
	public static String enc(String toEnc) throws NoSuchAlgorithmException {
		MessageDigest mdEnc = MessageDigest.getInstance("MD5"); // Encryption algorithm
		mdEnc.update(toEnc.getBytes(), 0, toEnc.length());
		return new BigInteger(1, mdEnc.digest()).toString(16); // Encrypted string
	}
	
	public static void main(String[] args){
		try {
			System.out.println("pwd="+enc("yoyo1706"));
			
			StringBuffer sb = new StringBuffer();
			for(int i=0; i<5; i++){
				sb.append(1+i).append("-").append("map"+i).append(",");
			}
			sb.deleteCharAt(sb.length()-1);
			System.out.println(sb.toString());
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
