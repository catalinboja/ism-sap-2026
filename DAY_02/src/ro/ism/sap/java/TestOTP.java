package ro.ism.sap.java;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;

public class TestOTP {
	
	public static byte[] encryptOrDecrypt(byte[] key, byte[] message) {
		
		if(key.length != message.length) {
			throw new UnsupportedOperationException("Must have same size");
		}
		
		byte[] cipher = new byte[key.length];
		for(int i = 0; i < key.length; i++) {
			cipher[i] = (byte) (key[i] ^ message[i]);
		}
		
		return cipher;	
	}
	
	public static void encryptFileOtp(
			String filePath, byte[] key, String outputFilePath) throws IOException {
		Path inputFile = Path.of(filePath);
		if(!inputFile.toFile().exists()) {
			System.out.println("*************** NO INPUT file");
		}
		byte[] fileContent = Files.readAllBytes(inputFile);
		byte[] cihperText = encryptOrDecrypt(key, fileContent);
		Path outputFile = Path.of(outputFilePath);
		if(!outputFile.toFile().exists()) {
			outputFile.toFile().createNewFile();
		}
		OutputStream os = Files.newOutputStream(outputFile);
		os.write(cihperText);
		os.close();
	}
	
	
	public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
		
		File file = new File("Files\\MyData.bin");
		long fileSize = file.length();
		System.out.println("File size: " + fileSize);
		
		byte[] randomKey = 
				TestSecureRandom.getRandomValue((int) fileSize);
		
		encryptFileOtp("Files\\MyData.bin", 
				randomKey, 
				"Files\\MyData.otp");
		
		encryptFileOtp("Files\\MyData.otp", 
				randomKey, 
				"Files\\MyData2.bin");
		
		
	}
	
	

}
