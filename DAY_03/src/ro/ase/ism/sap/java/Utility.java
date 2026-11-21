package ro.ase.ism.sap.java;

import java.util.Base64;

public class Utility {
	public static String toHexString(byte[] data) {
		StringBuilder sb = new StringBuilder();
		for(byte value : data) {
			sb.append(String.format("%02X ", value));
		}
		return sb.toString();
	}
	
	public static String encodeB64(byte[] data) {
		return Base64.getEncoder().encodeToString(data);
	}
	
	public static byte[] decodeB64(String data) {
		return Base64.getDecoder().decode(data);
	}
}
