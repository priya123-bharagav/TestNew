package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Read {

	public static void main(String[] args) {

		Map<String, String> properties = getKeyandValue();
		String filePath = "src/test/resources/textfile.txt";
		try {
			List<String> content = Arrays
					.asList(Files.lines(Paths.get(filePath)).collect(Collectors.joining()).split("\\W+"));

			Iterator<Map.Entry<String, String>> itr = properties.entrySet().iterator();

			while (itr.hasNext()) {
				AtomicInteger counter = new AtomicInteger(0);
				Map.Entry<String, String> entry = itr.next();
				content.stream().forEach(item -> {
					if (item.equals(entry.getKey())) {
						content.set(content.indexOf(entry.getKey()), entry.getValue());
						counter.getAndIncrement();
					}
				});
				System.out.println(entry.getKey().toUpperCase() + " " + "was replaced" + " " + counter.get() + " " + "times");
				writeIntoFile(content);
			}

		} catch (IOException e) {

			e.printStackTrace();
		}

	}

//Method to write replaced content into new output file.
	public static void writeIntoFile(List<String> content) {
		String fileName = "output.txt";
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(fileName)));
			for (String eachString : content) {
				if (eachString.matches("^[a-zA-Z]*$"))
					eachString = eachString + " ";
				writer.write(eachString);
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