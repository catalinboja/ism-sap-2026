package ro.ase.ism.sap.java;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class TestHKDF {
	
	public static byte[] hkdf(
			byte[] ikm, 
			byte[] salt,
			int sizeInBytes,
			String info) throws InvalidKeyException, NoSuchAlgorithmException {
		
		//validate the length 
		if(sizeInBytes <=0 || sizeInBytes > 255 * 32) {
			throw new IllegalArgumentException("length");
		}
		//validate the salt
		if(salt == null) {
			salt = new byte[32]; //0 byte salt - accepted
		}
		
		//phase 1 - extract
		byte[] prk = HMAC.getHmac(salt, ikm);
		
		//phase 2 - extension
		byte[] previous = new byte[0];
		byte[] generatedKey = new byte[sizeInBytes];
		int position = 0;
		byte[] infoData = info.getBytes(StandardCharsets.UTF_8);
		
		for(int counter = 1; position < sizeInBytes; counter += 1)
		{
			byte[] input = 
					new byte[previous.length + 
					         info.getBytes().length + 1];
			//fill info with previous || info || counter
			System.arraycopy(previous, 0, input, 0, previous.length);
			System.arraycopy(infoData, 0, input, 
					previous.length, infoData.length);
			input[input.length -1] = (byte) counter;
			
			//for testing
			System.out.println("Input: " + Utility.toHexString(input));
			
			byte[] currentBlock = HMAC.getHmac(prk, input);
			
			//copy into the output
			int requiredSize = sizeInBytes - position;
			requiredSize = requiredSize > 32 ? 32 : requiredSize;
			
			//sau
			//int requiredSize = 
			//	Math.min(32, sizeInBytes - position);
			
			System.arraycopy(currentBlock, 0, generatedKey, position, requiredSize);
			position += requiredSize;
			previous = currentBlock;
		}
		
		return generatedKey;
	}

	public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException {
		
		byte[] key = {(byte) 0xA4, 0x22, (byte) 0xF3, (byte) 0xFF};
		
		System.out.println("Small key: " + 
				Utility.toHexString(key));
		
		String keyB64 = Utility.encodeB64(key);
		System.out.println("Base 64 small key: " + keyB64);
		System.out.println("Initial small key: " + 
				Utility.toHexString(Utility.decodeB64(keyB64)));
		
		
		//Test HKDF
		//Generate a random salt and save it
		byte[] salt = new byte[32];
		salt[0] = (byte) 0xFF;
		//new SecureRandom().nextBytes(salt);
		
		System.out.println("Salt:" + Utility.toHexString(salt));
		String info = "ISM Exam Session Key";
		
		byte[] derivedKey = hkdf(key, salt, 250, info);
		
		System.out.println("Derived key: " + 
				Utility.toHexString(derivedKey));
		
			
	}

}
