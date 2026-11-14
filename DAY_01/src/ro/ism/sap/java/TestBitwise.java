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
		
		
		//bit level shifting << >> >>>
		byte anotherkey = 0b00110111;
		System.out.println(
				Integer.toBinaryString(anotherkey));
		byte changedkey = (byte) (anotherkey << 1);
		System.out.println(
				Integer.toBinaryString(changedkey));
		
		
		// always apply the 0xFF mask after a byte shift
		changedkey = (byte) (anotherkey << 2);
		System.out.println(
				Integer.toBinaryString(0xFF & changedkey));
		
		changedkey = (byte) (anotherkey << 2);
		System.out.println(
				Integer.toBinaryString(changedkey));
	
		
		//shift to right
		changedkey = (byte) (anotherkey >> 1);
		System.out.println(
				Integer.toBinaryString(changedkey));
		changedkey = (byte) (anotherkey >> 4);
		System.out.println(
				Integer.toBinaryString(changedkey));
		
		anotherkey = (byte) 0b10110111;
		System.out.println("Initial key");
		System.out.println(
				Integer.toBinaryString(0xFF & anotherkey));
	
		changedkey = (byte) ((0xFF & anotherkey) >> 4);
		System.out.println(
				Integer.toBinaryString(changedkey));
		
		// >>> shift without preserving sign bit
		// use the 0xFF to cancel Java extension to Integer
		changedkey = (byte) ((0xFF & anotherkey) >>> 4);
		System.out.println(
				Integer.toBinaryString(changedkey));
		
		//how to check a bit value
		byte bitValue = (byte) 0b10110111;
		//check bit 6 value
		byte bitMask = (byte) 0b01000000;
		byte bitResult = (byte) (bitValue & bitMask);
		byte bit6Value = 0;
		if(bitResult == 0) {
			System.out.println("Bit 6 is 0");
		} else {
			System.out.println("Bit 6 is 1");
			bit6Value = 1;
		}
		
		//add the bit value into another byte on any position
		byte outputResult = 0x00;
		//add the previous bit on bit 2
		outputResult = (byte) (outputResult | (bit6Value << 2));
		
		//alternative
		//get bit 6 value
		bitValue = (byte) 0b11110111;
		bit6Value = (byte) ((0xFF & (bitValue << 1)) >>> 7);
		System.out.println(Integer.toBinaryString(bit6Value));
		
		//alternative
		//get bit 6 value
		bitValue = (byte) 0b11110111;
		bit6Value = (byte) (bitValue >>> 6);
		bit6Value = (byte) (bit6Value & 1);
		
		bit6Value = (byte) ((byte) (bitValue >>> 6) & 1);
		System.out.println(Integer.toBinaryString(bit6Value));
		
		//rotate - works only for integers - 32 bits
		//DON'T USE
		bitValue = (byte) 0b10110111;
		byte rotatedValue = 
				(byte) Integer.rotateLeft(bitValue, 3);
		System.out.println(Integer.toBinaryString(0xFF & rotatedValue));
		
		//flip bits
		bitValue = (byte) 0b00110111;
		byte flippedValue = (byte) ~bitValue;
		
		System.out.println("After flip");
		System.out.println(Integer.toBinaryString(0xFF & flippedValue));
		
		//flip bit 2
		bitValue = (byte) 0b10110011;
		flippedValue = (byte) (bitValue ^ (1 << 2));
		System.out.println(
				Integer.toBinaryString(0xFF & flippedValue));
		
		
		//flip bit 2 bit 3 and bit 6
		bitValue = (byte) 0b10110011;
		flippedValue =
				(byte) (bitValue ^ (1 << 2 | 1 << 3 | 1 << 6));
		System.out.println(
				Integer.toBinaryString(0xFF & flippedValue));
		
		
		//testing the LFSR
		byte[] seed = {(byte) 0xF0, 0x1A, 0x05, 0x15};
		LFSR lfsr = new LFSR();
		lfsr.init(seed);
		
		byte random = lfsr.cycle();
		System.out.println(random);
		
		random = lfsr.cycle();
		System.out.println(random);
		random = lfsr.cycle();
		System.out.println(random);
		random = lfsr.cycle();
		System.out.println(random);
		random = lfsr.cycle();
		System.out.println(random);
		
		for(int i= 0 ;i < 32; i++) {
			random = lfsr.cycle();
		}
		
		random = lfsr.cycle();
		System.out.println(random);
		random = lfsr.cycle();
		System.out.println(random);
		random = lfsr.cycle();
		System.out.println(random);
		random = lfsr.cycle();
		System.out.println(random);
		random = lfsr.cycle();
		System.out.println(random);
		random = lfsr.cycle();
		System.out.println(random);
		random = lfsr.cycle();
		System.out.println(random);
		random = lfsr.cycle();
		System.out.println(random);
		random = lfsr.cycle();
		System.out.println(random);
		random = lfsr.cycle();
		System.out.println(random);
		random = lfsr.cycle();
		System.out.println(random);
		random = lfsr.cycle();
		System.out.println(random);
		
		
	}

}





