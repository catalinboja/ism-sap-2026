package ro.ase.ism.sap.java;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class TestECB {
	public static void encrypt(
			byte[] key,
			String algorithm,
			String intputFile,
			String outputFile
			) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		
		File input =  new File(intputFile);
		if(!input.exists()) {
			System.out.println("********* NO File");
			throw new FileNotFoundException();
		}
		
		FileInputStream fis = 
				new FileInputStream(input);
		
		File output = new File(outputFile);
		if(!output.exists()) {
			output.createNewFile();
		}
		FileOutputStream fos = new FileOutputStream(output);
		
		//create Cipher object
		Cipher cipher = 
				Cipher.getInstance(algorithm + "/ECB/PKCS5Padding");
		cipher.init(
				Cipher.ENCRYPT_MODE, 
				new SecretKeySpec(key, algorithm));
		
		  byte[] block = new byte[cipher.getBlockSize()];
		  int noBytesWeGot = 0;
		  
		  while((noBytesWeGot = fis.read(block)) > 0) {
			  //encrypt current block
			  byte[] cipherBlock = cipher.update(block, 0, noBytesWeGot);
			  fos.write(cipherBlock);
		  }
		  //don't forget the last one
		  fos.write(cipher.doFinal());
		
		  fis.close();
		  fos.close();			
	}
	
	public static void decrypt(
			byte[] key, 
			String algorithm,
			String inputFile,
			String outputFile) throws IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		
		File input = new File(inputFile);
		File output = new File(outputFile);
		
		if(!input.exists()) {
			throw new FileNotFoundException("NO Input file");
		}
		if(!output.exists()) {
			output.createNewFile();
		}
		
		FileInputStream fis = new FileInputStream(input);
		FileOutputStream fos = new FileOutputStream(output);
		
		Cipher cipher = Cipher.getInstance(
				algorithm + "/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, 
				new SecretKeySpec(key, algorithm));
		
		byte[] block = new byte[cipher.getBlockSize()];
		int noBytes = 0;
		while((noBytes = fis.read(block)) > 0) {
			byte[] plaintext = 
					cipher.update(block,0,noBytes);
			fos.write(plaintext);
		}
		fos.write(cipher.doFinal());
		
		fis.close();
		fos.close();		
	}
	
	
	
	public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IOException {
		String key = "passwordism12345password";
		encrypt(key.getBytes(), "DESede", 
				"message.txt", "message.enc");
		decrypt(key.getBytes(), "DESede", 
				"message.enc", "message2.txt");
		
		System.out.println("Done");
	}
}




