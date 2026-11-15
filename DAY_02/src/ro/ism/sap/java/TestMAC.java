package ro.ism.sap.java;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.SecretKeySpec;

public class TestMAC {
	
	public static byte[] getHashMAC(String message, String key) throws NoSuchAlgorithmException, InvalidKeyException {
		Mac mac = Mac.getInstance("HmacSHA1");
		SecretKey secretKey = 
				new SecretKeySpec(key.getBytes(), "HmacSHA1");
		mac.init(secretKey);
		return mac.doFinal(message.getBytes());
	}
	
	public static byte[] getFileHashMac(String filePath, String key) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
		Path path = Path.of(filePath);
		if(!path.toFile().exists()) {
			System.out.println("***************** NO FILE");
			return null;
		}
		
		Mac mac = Mac.getInstance("HmacSHA512");
		SecretKey secretKey = 
				new SecretKeySpec(key.getBytes(), "HmacSHA512");
		mac.init(secretKey);
		
		InputStream fis = Files.newInputStream(path);
		int noOfBytesWeGot = 0;
		byte[] buffer = new byte[5];
		while((noOfBytesWeGot = fis.read(buffer)) != -1) {
			mac.update(buffer, 0, noOfBytesWeGot);
		}
		
		fis.close();
		
		return mac.doFinal();
		
	}
	
	
	public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, IOException {
		String message = "The SAP simulation will be next weekend";
		String key = "ismtopsecret";
		byte[] hmacTag = getHashMAC(message, key);
		
		System.out.println("MAC Tag: " + 
		      Utility.getHexString(hmacTag));
		
		byte[] fileMacTag = 
				getFileHashMac("Files\\MyData.bin", key);
		System.out.println("File MAC: "+ Utility.getHexString(fileMacTag));
	}

}
