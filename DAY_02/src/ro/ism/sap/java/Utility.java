package ro.ism.sap.java;

public class Utility {
	public static String getHexString(byte[] value) {
		StringBuilder sb = new StringBuilder();
		for(byte byteValue : value) {
			sb.append(String.format("%02X ", byteValue));
		}
		return sb.toString();
	}
}
