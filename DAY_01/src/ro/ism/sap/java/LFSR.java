package ro.ism.sap.java;

import javax.security.auth.RefreshFailedException;

public class LFSR {
	
	byte[] register = new byte[4];
	
	//at register level
	private byte getBitAtIndex(int index) {
		int byteIndex = 3 - index / 8;
		int bitIndex = index % 8;
		
		byte bitByte = this.register[byteIndex];
		byte temp = (byte) (1 << bitIndex);
		byte bitValue = (byte) (bitByte & temp); 
		return (byte) (bitValue != 0 ? 1 : 0);
	}
	
	//shifts a byte from the register by 1 to the left and
	// returns the MSB
	private byte shiftAndReturn(int registerIndex) {
		byte msb = (byte) (this.register[registerIndex] & 
				(1 << 7));
		this.register[registerIndex] = 
				(byte) (0xFF & 
						(this.register[registerIndex] << 1));
		return (byte) (msb != 0 ? 1 : 0);
	}
	
	private void addLSB(int registerIndex, byte lsb) {
		this.register[registerIndex] = 
				(byte) (this.register[registerIndex] | lsb);
	}
	
	
	//apply a LFSR cycle
	public byte cycle() {
		byte temp = (byte) 
		(getBitAtIndex(31) ^ 
		getBitAtIndex(7) ^
		getBitAtIndex(5) ^
		getBitAtIndex(3) ^
		getBitAtIndex(2) ^
		getBitAtIndex(1) ^
		getBitAtIndex(0));
		
		byte outputBit1 = shiftAndReturn(3);
		addLSB(3, temp);
		byte outputBit2 = shiftAndReturn(2);
		addLSB(2, outputBit1);
		byte outputBit3 = shiftAndReturn(1);
		addLSB(1, outputBit2);
		byte randomBit = shiftAndReturn(0);
		addLSB(0, outputBit3);
		return randomBit;
	}
	
	public void init(byte[] seed) {
		for(int i = 0; i < this.register.length; i++) {
			this.register[i] = seed[i];
		}
	}
	
	
	
	//32 bit register LFSR
	//x31+x7+x5+x3+x2+x+1
	
	//store the 32 bits
	//1 - Integer
	//2 - byte array byte[4]
	//3 - BitSet
}
