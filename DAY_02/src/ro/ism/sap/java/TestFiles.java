package ro.ism.sap.java;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class TestFiles {
	
	// interacting with the file system
	public static void displayContent(String path) {
		File location = new File(path);
		if(location.exists()) {
			//check if the path is a folder
			if(location.isDirectory()) {
				//show content
				File[] content = location.listFiles();
				for(File entry : content) {
					System.out.println(entry.getName());
					if(entry.isFile())
					{
						System.out.println("------- is a file");
					}
					if(entry.isDirectory()) {
						System.out.println("-> DIRECTORY content");
						displayContent(entry.getAbsolutePath());
						//alternative way 
//						String newPath = 
//								path + File.separator + 
//								entry.getName();
//						displayContent(newPath);
					}
				}
			}
		}	
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		displayContent("D:\\2026-2027\\ism-sap-2026");
		
		//create a folder
		File newFolder = new File("Files");
		if(!newFolder.exists()) {
			newFolder.mkdir();
		}
		
		
		//interact with text files
		File textFile = new File(newFolder.getName() +
				File.separator + "Message.txt");
		if(!textFile.exists()) {
			textFile.createNewFile();
		}
		
		//write into text files
		FileWriter fileWriter = new FileWriter(textFile, true);
		fileWriter.write("Log messages:\n");
		PrintWriter printWriter = new PrintWriter(fileWriter);
		printWriter.println("10:00 Authorized access by admin");
		printWriter.println("11:00 Authorized access by admin");
		
		printWriter.close();
		
		//read from text files
		FileReader fileReader = new FileReader(textFile);
		//read it line by line
		BufferedReader bReader = new BufferedReader(fileReader);
		String line = "";
		while((line = bReader.readLine()) != null) {
			System.out.println("Line: " + line);
		}
		bReader.close();
		
		//alternative with nio
		Path path = textFile.toPath();
		ArrayList<String> content = 
				(ArrayList<String>) Files.readAllLines(path);
		System.out.println("=========================");
		for(String row : content) {
			System.out.println(row);
		}
		
		
		//interacting with binary files
		//write into binary files
		File dataFile = 
				new File("Files" + File.separator + "MyData.bin");
		if(!dataFile.exists()) {
			dataFile.createNewFile();
		}
		FileOutputStream fos = new FileOutputStream(dataFile);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		DataOutputStream dos = new DataOutputStream(bos);
		dos.writeInt(23);
		dos.writeDouble(100.4);
		dos.writeUTF("Hello. ISM");
		dos.writeBoolean(true);
		dos.close();
		
		FileInputStream fis = new FileInputStream(dataFile);
		DataInputStream dis = new DataInputStream(fis);
		int intValue = dis.readInt();
		double doubleValue = dis.readDouble();
		String stringValue = dis.readUTF();
		boolean boolValue = dis.readBoolean();
		dis.close();
		
		System.out.println(stringValue);
		
		
	}

}
