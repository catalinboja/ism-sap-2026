package ro.ism.sap.java;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.*;

public class TestPBKDF {
	
	public static byte[] getPBKDF(
			String password, 
			byte[] salt, 
			int noIterations, 
			int outputSize) throws NoSuchAlgorithmException, InvalidKeySpecException
	{
		SecretKeyFactory pbkdf = 
				SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
		PBEKeySpec algSpec = 
				new  PBEKeySpec(
						password.toCharArray(), 
						salt, 
						noIterations, 
						outputSize);
		double tStart = System.currentTimeMillis();
		
		byte[] result =  pbkdf.generateSecret(algSpec).getEncoded();
		
		double tFinal = System.currentTimeMillis();
		
		System.out.println("PBKDF in " + (tFinal - tStart));
		
		return result;
	}
	

	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException {
		
		byte[] userJohnHash = getPBKDF(
				"12345678", 
				"ashgwer".getBytes(), 
				1000000,
				256);
		
		byte[] randomSalt = new byte[5];
		new SecureRandom().
			getInstance("SHA1PRNG").
			nextBytes(randomSalt);
		byte[] userAnnaHash = getPBKDF(
				"12345678", 
				randomSalt, 
				100000,
				256);
				
		System.out.println("John: " + Utility.getHexString(userJohnHash));
		System.out.println("Anna: " + Utility.getHexString(userAnnaHash));
		
	}

}
