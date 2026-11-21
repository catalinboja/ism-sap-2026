package ro.ase.ism.sap.java;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.AEADBadTagException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class TestChaChaPoly {
	
	public static byte[] encrypt(
			byte[] data,
			byte[] key,
			byte[] nounce, 
			String aadValue) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		//validate input values
		if(key == null || key.length != 32) {
			throw new IllegalArgumentException("Key");
		}
		if(nounce == null || nounce.length != 12) {
			throw new IllegalArgumentException("Nounce");
		}
		
		Cipher cipher = 
				Cipher.getInstance("ChaCha20-Poly1305");
		cipher.init(Cipher.ENCRYPT_MODE, 
				new SecretKeySpec(key, "ChaCha20-Poly1305"),
				new IvParameterSpec(nounce)
				);
		//mandatory - add the AAD
		cipher.updateAAD(aadValue.getBytes());
		
		return cipher.doFinal(data);
	}
	
	public static byte[] decrypt(
			byte[] ciphertext,
			byte[] key,
			byte[] nounce,
			String aadValue
			) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException {
		//validate input values
		if(key == null || key.length != 32) {
			throw new IllegalArgumentException("Key");
		}
		if(nounce == null || nounce.length != 12) {
			throw new IllegalArgumentException("Nounce");
		}
		
		Cipher cipher = 
				Cipher.getInstance("ChaCha20-Poly1305");
		cipher.init(Cipher.DECRYPT_MODE, 
				new SecretKeySpec(key, "ChaCha20-Poly1305"),
				new IvParameterSpec(nounce)
				);
		//mandatory - add the AAD
		cipher.updateAAD(aadValue.getBytes());
		
		return cipher.doFinal(ciphertext);
	}
	
	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		String transaction = "Send 100 to John";
		byte[] nounce = new byte[12];
		new SecureRandom().nextBytes(nounce);
		String messageTag = "XBank v1";
		
		//derive using HKDF a new key from the master one
		//TO DO
		
		//Generate a random key
		KeyGenerator kg = 
				KeyGenerator.getInstance("ChaCha20");
		kg.init(256);
		byte[] key = kg.generateKey().getEncoded();
		
		byte[] ciphertext = 
				encrypt(transaction.getBytes(), 
						key, nounce, messageTag);
		
		System.out.println("Cipher with AAD tag:" + 
				Utility.toHexString(ciphertext));
		
		//the destination gets the nounce || ciphertext
		//the destination knows the key and the AAD values
		
		//test normal decryption
		byte[] initialData = 
				decrypt(ciphertext, key, nounce, messageTag);
		System.out.println("Initial message: " + 
				new String(initialData));
		
		//test to see the bit flip attack
		//flip a bit in the ciphertext 
		//- flip bit 4 of byte 2 (count from 0 from left)
		ciphertext[2] = (byte) (ciphertext[2] ^ (1 << 4));
		try {
		initialData = 
				decrypt(ciphertext, key, nounce, messageTag);
		}
		catch(AEADBadTagException ex) {
			System.out.println("Vader altered the ciphertext");
		}
		
	}
	
	
	
}


