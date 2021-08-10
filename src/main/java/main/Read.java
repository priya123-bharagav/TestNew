package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

public class Read {

	public static void main(String[] args) {
		String arrayOfWords[] = null;
		List<String[]> listOfReplacedStrings = new ArrayList();
		Map<String, String> properties = getKeyandValue();
		String filePath = "src/test/resources/textfile.txt";
		String content = null;

		try {
			content = Files.lines(Paths.get(filePath)).collect(Collectors.joining());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		arrayOfWords = content.split("\\W+");
		for (String key : properties.keySet()) {
			int countKey = 0;
			for (int i = 0; i < arrayOfWords.length; i++) {
				if (arrayOfWords[i].equals(key)) {
					arrayOfWords[i] = properties.get(key);
					countKey++;
				}
			}
			System.out.println(key.toUpperCase() + " " + "is replaced" + " " + countKey + " " + "time");
		}
		listOfReplacedStrings.add(arrayOfWords);
		writeIntoFile(listOfReplacedStrings);
	}

//Method to write replaced content into new output file.
	public static void writeIntoFile(List<String[]> listOfArray) {
		String fileName = "output.txt";
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(fileName)));
			for (String[] eachArray : listOfArray) {
				for (String eachString : eachArray) {
					if (eachString.matches("^[a-zA-Z]*$"))
						eachString = eachString + " ";
					writer.write(eachString);
				}
			}
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

//Method to read keys and values from properties file.
	public static Map<String, String> getKeyandValue() {
		FileReader file;
		Map<String, String> properties = new LinkedHashMap<String, String>();
		Set<String> keys = null;
		try {
			file = new FileReader("src/test/resources/replacement.properties");
			Properties p = new Properties();
			p.load(file);
			keys = p.stringPropertyNames();
			for (String str : keys) {
				String value = p.getProperty(str);
				properties.put(str, value);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;
	}

}
