package ro.ase.ism.sap.java;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class TestAESGCM {

	public static byte[] encrypt(
			byte[] data,
			byte[] nounce,
			byte[] key,
			String aadValue
			) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		
		Cipher cipher = 
				Cipher.getInstance("AES/GCM/NoPadding");
		cipher.init(
				Cipher.ENCRYPT_MODE, 
				new SecretKeySpec(key, "AES"), 
				new GCMParameterSpec(128, nounce));
		cipher.updateAAD(aadValue.getBytes());
		return cipher.doFinal(data);
	}
	
	public static byte[] decrypt(
			byte[] data,
			byte[] nounce,
			byte[] key,
			String aadValue
			) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		
		Cipher cipher = 
				Cipher.getInstance("AES/GCM/NoPadding");
		cipher.init(
				Cipher.DECRYPT_MODE, 
				new SecretKeySpec(key, "AES"), 
				new GCMParameterSpec(128, nounce));
		cipher.updateAAD(aadValue.getBytes());
		return cipher.doFinal(data);
	}
	
	public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		String message = "Send 100 to John";
		String key = "passwordpassword";
		byte[] nounce = new byte[16];
		new SecureRandom().nextBytes(nounce);
		
		byte[] ciphertext = encrypt(message.getBytes(), 
				nounce, key.getBytes(), "ISM v1");
		
		System.out.println("Cipher: " + 
		     Utility.toHexString(ciphertext));	
		
		byte[] initial = 
				decrypt(ciphertext, nounce, 
						key.getBytes(), "ISM v1");
		System.out.println(new String(initial));
		
		//attacker changes a byte the in ciphertext
//		ciphertext[0] = (byte) 0xFF;
//		initial = decrypt(ciphertext, nounce, 
//						key.getBytes(), "ISM v1");
		
		//the client gets a message from a different protocol version
		initial = decrypt(ciphertext, nounce, 
						key.getBytes(), "ISM v2");
		
	}

}
