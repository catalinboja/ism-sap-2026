package ro.ism.sap.java;

import java.util.Arrays;
import java.util.Base64;

public class TestBitwise {
	
	public static String getHexString(byte[] value) {
		StringBuilder sb = new StringBuilder();
		for(byte byteValue : value) {
			sb.append(String.format("%02X ", byteValue));
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
		
		//define a 1 byte key with bit 5 = 1
		byte key1 = 32;
		byte key2 = (byte) 0b00100000;
		byte key3 = (byte) 1 << 5; 
		
		System.out.println("Key = " + key1);
		System.out.println("Key = " + key2);
		System.out.println("Key = " + key3);
		
		//define a 1 byte key with bit 6 = 1 and bit 3 = 1
		byte key4 = (byte) 0b01001000;
		byte key5 = 1 << 6 | 1 << 3;
		
		//defining values in hexa
		byte nounce = (byte) 0xA3;
		
		// byte arrays
		// define a DES key on 8 bytes where byte 3 has all 
		// bits 1 and the rest are 0
		byte[] desKey = 
			{0, 0, 0, 0, (byte) 0b11111111, 0, 0, 0};
		byte[] desKey2 = 
			{0, 0, 0, 0, (byte) 0xFF, 0, 0, 0};
		//define an AES key on 128 bits where byte 5 = AA and
		//	byte 7 is 3F
		byte[] aesKey = new byte[16];
		//aesKey[5] //BIG MISTAKE
		aesKey[10] = (byte) 0xAA;
		aesKey[8] = (byte) 0x3F;
		
		//printing values
		System.out.printf("\n The 1 byte key is %x", key4);
		System.out.printf("\n The 1 byte noune is %x", nounce);
		System.out.printf("\n The 1 byte noune is %X", nounce);
		
		System.out.println("\nThe AES key is " + 
		getHexString(aesKey));
		
		//string conversions from byte representation
		//DON'T DO THIS
		byte[] desKey3 = {0x30, (byte) 0x89, 0x00, 0x01, 0x02, 
				(byte) 0xD3, 0x13, (byte) 0xFF};
		
		System.out.println("DES key = " + 
		getHexString(desKey3));
		
		String desPass = new String(desKey3); // you just lost the values
		System.out.println("Same DES key: " + desPass);
		
		//storing binary values as Base64 - this is encoding
		// alternative is an hex string
		
		String base64Representation= 
				Base64.getEncoder().encodeToString(desKey3);
		System.out.println("DES key as Base64 string " + base64Representation);
		byte[] desKey3Copy = 
				Base64.getDecoder().decode(base64Representation);
		System.out.println("DES key copy " + getHexString(desKey3Copy));
		
		
		//comparing byte arrays
		byte[] desKey4 = {0x30, (byte) 0x89, 0x00, 0x01, 0x02, 
				(byte) 0xD3, 0x13, (byte) 0xFF};
		
		//don't do this
		if(desKey3 == desKey4) {
			System.out.println("Are equal");
		}
		else {
			System.out.println("DIFFERENT");
		}
		
		//do a deep check - check the values not the references
		if(Arrays.equals(desKey3, desKey4)) {
			System.out.println("Are equal");
		}else {
			System.out.println("DIFFERENT");
		}
		
		String userName = "John";
		String userName2 = "John";
		
		//DON'T do this
		if(userName == userName2) {
			System.out.println("Same user");
		}
		
		userName = new String("John");
		userName2 = new String("John");
		
		if(userName.equals(userName2)) {
			System.out.println("Same user");
		} else {
			System.out.println("DIFFERENT users");
		}
		
		
	}

}





