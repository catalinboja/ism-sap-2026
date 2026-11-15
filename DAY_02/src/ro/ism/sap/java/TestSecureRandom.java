package ro.ism.sap.java;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class TestSecureRandom {

	public static byte[] getRandomValue(int noBytes) throws NoSuchAlgorithmException {
		//create a defasult PRNG - SHA1PRNG
		SecureRandom secureRandom = new SecureRandom();
		//alternative - you choose the algorithm
		SecureRandom secureRandom2 = 
				SecureRandom.getInstance("DRBG");
		
		byte[] result = new byte[noBytes];
		secureRandom2.nextBytes(result);
		return result;
		
	}
	
	public static byte[] getRandomValueWithSeed(int noBytes, byte[] seed) 
			throws NoSuchAlgorithmException {
		SecureRandom secureRandom = 
				SecureRandom.getInstance("SHA1PRNG");
		secureRandom.setSeed(seed);
		
		byte[] result = new byte[noBytes];
		secureRandom.nextBytes(result);
		return result;
		
	}
	
	public static void main(String[] args) throws NoSuchAlgorithmException {
		//use the PRNG to generate a DES key - 64 bits = 8 bytes
		byte[] desKey = getRandomValue(8);
		System.out.println("DES random key: " + 
		     Utility.getHexString(desKey));
		
		//test with seed
		String seedSecret = "ismsecret";
		byte[] authKey = 
				getRandomValueWithSeed(8, seedSecret.getBytes());
		System.out.println("AUTH random value: " + 
			     Utility.getHexString(authKey));
		
	}

}
